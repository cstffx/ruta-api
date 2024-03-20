package org.xrgames.ruta.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class SecurityClient {

	/**
	 * Autentica al usuario con un nombre de usuario.
	 * 
	 * @param username
	 * @return
	 */
	public LoginResult login(String username) {
		var url = Endpoint.build("jugador/login");
		var result = new LoginResult();
		
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(url);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

		var formData = new LoginFormData(username);
		var postData = Entity.entity(formData, MediaType.APPLICATION_JSON);
		var res = invocationBuilder.post(postData);

		if (res.getStatus() == Response.Status.OK.getStatusCode()) {
			var token = res.readEntity(SessionToken.class);
		
			token = token != null ? token : new SessionToken();
			result.token = token;
		}

		result.builder = invocationBuilder;

		return result;
	}
}