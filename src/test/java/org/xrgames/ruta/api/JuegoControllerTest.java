package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.xrgames.ruta.services.ActiveClient;
import org.xrgames.ruta.services.JuegoClient;

public class JuegoControllerTest {

	@Test
	public void createTest() {
		ActiveClient client = new ActiveClient("test");
		assertTrue(client.auth().isSome());
		
		JuegoClient juego = new JuegoClient(client);
		assertTrue(juego.create());
	}
}