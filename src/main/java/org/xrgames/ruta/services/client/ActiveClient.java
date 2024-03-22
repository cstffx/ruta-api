package org.xrgames.ruta.services.client;

import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.LoginFormData;
import org.xrgames.ruta.services.AuthResult;
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

	public static final String SESSION_ID_KEY = "JSESSIONID";
	
	private String sessionId; 
	private AuthResult result;
	private Client client;

	/**
	 * Contructor por defecto para satisfacer al inyector de dependencias.
	 */
	public ActiveClient() {
		result = new AuthResult();
	}
	
	/**
	 * Envia una peticion al servidor con las credenciales actuales.
	 * @param url
	 * @param entity
	 * @return
	 * @throws Exception 
	 */
	public Response post(String url) throws Exception {
		return post(url, null);
	}
	
	/**
	 * Envia una peticion al servidor con las credenciales actuales.
	 * @param url
	 * @param entity
	 * @return
	 * @throws Exception 
	 */
	public Response post(String url, Object entity) throws Exception {
		if(null == this.sessionId || null == client) {
			throw new Exception("Debe autenticarse antes de hacer peticiones");
		}
		
		WebTarget webTarget = client.target(url);
		var postData = Entity.entity(entity, MediaType.APPLICATION_JSON);
		Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		updateCookies(builder);
		return builder.post(postData);
	}
	
	/**
	 * Envia una peticion al servidor con las credenciales actuales.
	 * @param url
	 * @param entity
	 * @return
	 * @throws Exception 
	 */
	public Response get(String url) throws Exception {
		if(null == this.sessionId || null == client) {
			throw new Exception("Debe autenticarse antes de hacer peticiones");
		}
		
		WebTarget webTarget = client.target(url);
		Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		
		updateCookies(builder);
		
		return builder.get();
	}
	
	/**
	 * Autentica un usuario de nombre test 
	 * @return
	 */
	public AuthResult auth() {
		return auth("test");
	}

	/**
	 * Obtiene una sesion para el usuario con el username actual.
	 * @return
	 */
	public AuthResult auth(String username) {
		var url = Endpoint.build(Endpoint.ENDPOINT_LOGIN);
		result = new AuthResult();

		client = ClientBuilder.newClient();

		WebTarget webTarget = client.target(url);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

		var formData = new LoginFormData(username);
		var entity = Entity.entity(formData, MediaType.APPLICATION_JSON);
		var res = invocationBuilder.post(entity);
		
		if (res.getStatus() == Response.Status.OK.getStatusCode()) {
			var token = res.readEntity(SessionToken.class);
			token = token != null ? token : new SessionToken();
			
			if(!token.isEmpty()) {
				var cookies = res.getCookies(); 
				sessionId = cookies.get(SESSION_ID_KEY).getValue();
			}
			
			this.result.setToken(token);
		}

		return result;
	}

	/**
	 * Cierra la sesión del usuario actual.
	 * @return
	 * @throws Exception 
	 */
	public boolean logout() throws Exception {
		if(result.isErr()) {
			return false;
		}
		
		var url = Endpoint.build(Endpoint.ENDPOINT_LOGOUT);
		var res = post(url, null);
		
		if(res.getStatus() == Response.Status.OK.getStatusCode()) {
			this.result = new AuthResult();
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param builder
	 */
	public void updateCookies(Invocation.Builder builder) {
		builder.cookie(SESSION_ID_KEY, sessionId);
	}
}