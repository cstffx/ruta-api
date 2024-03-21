package org.xrgames.ruta.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Cliente para operaciones con la entidad Juego.
 */
@ApplicationScoped
public class JuegoClient {
	
	@Inject 
	ActiveClient activeClient;

	/**
	 * @param username
	 */
	public JuegoClient() {
		
	}

	/**
	 * Crea un nuevo juego
	 * @return
	 */
	public boolean create() {
		var url = Endpoint.build(Endpoint.ENDPOINT_JUEGO_CREATE);
		var client = activeClient.getClient(); 
		var target = client.target(url);
		var response = target.request(MediaType.APPLICATION_JSON).post(null);
		return ResponseUtil.isOk(response);
	}
}