package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.logic.Juego;
import org.xrgames.logic.ModoJuego;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.Endpoint.Route;
import org.xrgames.ruta.services.client.HttpClient;

import jakarta.ws.rs.core.Response;

public class EquipoResourceTest {

	@BeforeEach
	void beforeEach() throws Exception {
		var http = HttpClient.make();
		var response = http.post(Endpoint.of(Route.SERVER_RESET));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	@DisplayName("Deben listarse los equipos de un juego.")
	void getAll() throws Exception {
		// Crear un juego por equipos.
		var config = new ConfiguracionJuego();
		config.jugadores = Juego.MAX_JUGADORES;
		config.modo = ModoJuego.Equipo;

		// Usuario 1 crea juego 1.
		var creator = TestUtil.crearJuego(config);
		var http = creator.http; 
		var juegoId = creator.juegoId; 
		
		var response = http.get(Endpoint.of(Route.EQUIPO, juegoId));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		
		var equipos = response.readEntity(ArrayList.class);
		assertEquals(3, equipos.size());
	}
}
