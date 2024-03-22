package org.xrgames.ruta.services.client;

import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.LoginFormData;
import org.xrgames.ruta.services.LoginResult;
import org.xrgames.ruta.services.Security;
import org.xrgames.ruta.services.SessionToken;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Utilidad para realizar peticiones manteniendo una sesion
 */
@ApplicationScoped
public class ActiveClient {

	private String username;
	private LoginResult loginResult;
	private boolean debug = false;

	/**
	 * Contructor por defecto para satisfacer al 
	 * inyector de dependencias.
	 */
	public ActiveClient() {
		username = "";
		loginResult = new LoginResult();
	}
	
	/**
	 * @param username
	 */
	public ActiveClient(String username) {
		this.loginResult = new LoginResult();
		this.username = username;
	}
	
	/**
	 * Envia una peticion al servidor con las credenciales actuales.
	 * @param url
	 * @param entity
	 * @return
	 */
	public Response post(String url, Object entity) {
		var client = loginResult.getClient();
		WebTarget webTarget = client.target(url);
		var postData = Entity.entity(entity, MediaType.APPLICATION_JSON);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		var token = loginResult.getToken();
		invocationBuilder.cookie(Security.SESSION_ID_ATTRIBUTE, token.id);
		return invocationBuilder.post(postData);
	}
	
	/**
	 * Envia una peticion al servidor con las credenciales actuales.
	 * @param url
	 * @param entity
	 * @return
	 */
	public Response get(String url) {
		var client = loginResult.getClient();
		WebTarget webTarget = client.target(url);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		var token = loginResult.getToken();
		invocationBuilder.cookie(Security.SESSION_ID_ATTRIBUTE, token.id);
		return invocationBuilder.get();
	}

	/**
	 * Obtiene una sesion para el usuario con el username actual.
	 * @return
	 */
	public LoginResult auth() {
		var url = Endpoint.build(Endpoint.ENDPOINT_LOGIN);
		loginResult = new LoginResult();

		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client.target(url);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

		var formData = new LoginFormData(username);
		var postData = Entity.entity(formData, MediaType.APPLICATION_JSON);
		var res = invocationBuilder.post(postData);

		if(debug) {
			System.err.println(res.readEntity(String.class));
		}
		
		if (res.getStatus() == Response.Status.OK.getStatusCode()) {
			var token = res.readEntity(SessionToken.class);
			token = token != null ? token : new SessionToken();
			this.loginResult.setToken(token);
		}

		loginResult.setClient(client);

		return loginResult;
	}

	/**
	 * Cierra la sesión del usuario actual.
	 * @return
	 */
	public boolean logout() {
		if(loginResult.isErr()) {
			return false;
		}
		
		var url = Endpoint.build(Endpoint.ENDPOINT_LOGOUT);
		var res = post(url, null);
		if(res.getStatus() == Response.Status.OK.getStatusCode()) {
			this.loginResult = new LoginResult();
			return true;
		}
		
		return false;
	}

	/**
	 * @return Nombre de usuario actual
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * @return Resultado de la autenticacion
	 */
	public LoginResult getLoginResult() {
		return loginResult;
	}
	
	public Client getClient() {
		return loginResult.isSome() ? loginResult.getClient() : null;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}