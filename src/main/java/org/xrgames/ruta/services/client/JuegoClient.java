package org.xrgames.ruta.services.client;

import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.ResponseUtil;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;

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

	public JuegoClient(ActiveClient client) {
		activeClient = client;
	}

	/**
	 * Crea un nuevo juego
	 * 
	 * @return
	 */
	public boolean create(ConfiguracionJuego config) {
		var url = Endpoint.build(Endpoint.ENDPOINT_JUEGO_CREATE);
		var client = activeClient.getClient();
		var target = client.target(url);
		var entity = Entity.entity(config, MediaType.APPLICATION_JSON);
		var response = target.request(MediaType.APPLICATION_JSON).post(entity);
		return ResponseUtil.isOk(response);
	}
}