package org.xrgames.ruta.services.client;

import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.ResponseUtil;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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
	 * @param client
	 */
	public JuegoClient(ActiveClient client) {
		activeClient = client;
	}
	
	/**
	 * Crea un juego con la configuracion por defecto.
	 * @return
	 * @throws Exception 
	 */
	public boolean create() throws Exception {
		return create(new ConfiguracionJuego());
	}

	/**
	 * Crea un nuevo juego
	 * @return
	 * @throws Exception 
	 */
	public boolean create(ConfiguracionJuego config) throws Exception {
		var url = Endpoint.build(Endpoint.JUEGO_CREATE);
		var res = activeClient.post(url, config);
		
		System.err.println(res.readEntity(String.class));
		
		return ResponseUtil.isOk(res);
	}
}