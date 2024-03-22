package org.xrgames.ruta.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.client.ActiveClient;

public class JugadorControllerTest {
	
	static ActiveClient client;
	
	@BeforeAll
	public void beforeAll() throws Exception {
		client = new ActiveClient();
		client.auth();
		client.post(Endpoint.build(Endpoint.ENDPOINT_JUEGO_CREATE));
	}
	
	@Test
	public void logoutTest() {
		
	}
	
	@Test
	public void joinTest() {
		var client = new ActiveClient();
	}
	
	
}