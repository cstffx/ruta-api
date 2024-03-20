package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.xrgames.ruta.services.SecurityClient;

import jakarta.ws.rs.core.Response;

public class EquipoControllerTest extends RestTest {
	
	public void getAllTest() {
		SecurityClient sc = new SecurityClient();
		
		// Test security
		var response = get("/equipo/");
		assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
	
		/*
		var token = sc.login("random_name");
		assertNotNull(token);
		response = get("/equipo/");
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		*/
	}
}