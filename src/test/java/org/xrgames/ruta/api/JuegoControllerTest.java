package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.xrgames.ruta.services.ActiveClient;
import org.xrgames.ruta.services.JuegoClient;

import jakarta.inject.Inject;

public class JuegoControllerTest {
	
	@Inject 
	ActiveClient client;
	
	@Inject
	JuegoClient juego;
	
	@Test
	public void createTest() {
		// client.logout();
		// client.auth();

		assertTrue(juego.create());		
	}
}