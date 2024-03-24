package org.xrgames.ruta.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.ruta.services.Endpoint.Route;

public class EndpointTest {
	
	@Test
	@DisplayName("Endpoint simple")
	public void testEndpoint() throws Exception {
		Endpoint.of(Route.JUGADOR_LOGIN).toString();
	}
	
	@Test
	@DisplayName("Endpoint con un parámetros")
	public void testSingleParamEndpoint() throws Exception {
		Endpoint.of(Route.JUEGO_END, 1).toString();
	}

	@Test
	@DisplayName("Endpoint con dos parámetros")
	public void testDoubleParamEndpoint() throws Exception {
		Endpoint.of(Route.JUEGO_JOIN, 1, 2).toString();
	}
}