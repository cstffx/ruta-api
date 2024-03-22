package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.xrgames.ruta.services.client.ActiveClient;
import org.xrgames.ruta.services.client.JuegoClient;

public class JuegoControllerTest {
	@Test
	public void createTest() throws Exception {
		ActiveClient client = new ActiveClient();
		assertTrue(client.auth("test").isSome());
		
		// Crear un juego sin configuracion
		JuegoClient service = new JuegoClient(client);
		assertTrue(service.create());
	}
}