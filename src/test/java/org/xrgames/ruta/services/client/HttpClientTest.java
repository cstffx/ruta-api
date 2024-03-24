package org.xrgames.ruta.services.client;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.Endpoint.Route;

public class HttpClientTest {
	@Test
	void getTest() throws Exception {
		var endpoint = Endpoint.of(Route.STATUS);
		var response = HttpClient.make().get(endpoint);
		assertTrue(response.readEntity(String.class).equals("online"));
	}
	
	@Test
	void postTest() throws Exception {
		var endpoint = Endpoint.of(Route.STATUS);
		var response = HttpClient.make().post(endpoint);
		assertTrue(response.readEntity(String.class).equals("online"));
	}
}
