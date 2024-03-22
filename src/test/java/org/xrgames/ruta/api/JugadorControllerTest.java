package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.ResponseUtil;
import org.xrgames.ruta.services.client.ActiveClient;
import org.xrgames.ruta.services.client.JuegoClient;

public class JugadorControllerTest {
	
	static ActiveClient client;
	
	@BeforeEach
	public void beforeEach() throws Exception {
		// Autenticar un usuario de pruebas.
		client = new ActiveClient();
		if(client.auth().isErr()) {
			throw new Exception("Error de autenticacion");
		}
		
		// Crear un nuevo juego.
		var juegoClient = new JuegoClient(client);
		if(!juegoClient.create()) {
			throw new Exception("Error al crear un nuevo juego");
		}
	}
	
	@Test
	public void joinTest() throws Exception {
		var res = client.post(Endpoint.build(Endpoint.EQUIPO_CREATE));
		if(!ResponseUtil.isOk(res)) {
			throw new Exception("Error al crear el juego");
		}
		
		res = client.post(Endpoint.build(Endpoint.JUEGO_JOIN, String.valueOf(1)));
		assertTrue(ResponseUtil.isOk(res));
	}	
}