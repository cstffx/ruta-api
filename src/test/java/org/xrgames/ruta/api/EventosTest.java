package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.logic.ModoJuego;
import org.xrgames.ruta.entity.TipoEvento;
import org.xrgames.ruta.services.Debug;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.Endpoint.Route;
import org.xrgames.ruta.services.client.HttpClient;

import jakarta.ws.rs.core.Response;

/**
 * Comprueba los requisitos de la cola de eventos.
 */
public class EventosTest {

	@BeforeEach
	void beforeEach() throws Exception {
		TestUtil.resetServer();
	}

	@Test
	@DisplayName("Se recibe listado de eventos ocurridos")
	void getAllEventsTest() throws Exception {

		// Crear un juego para dos jugadores.
		var config = new ConfiguracionJuego(ModoJuego.Individual, 2);
		var creator = TestUtil.crearJuego(config);

		// Unir jugador 1
		var response = creator.http.post(Endpoint.of(Route.JUEGO_JOIN, creator.juegoId, 1));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		// Unir jugador 2
		var http2 = HttpClient.forUser().unwrap();
		response = http2.post(Endpoint.of(Route.JUEGO_JOIN, creator.juegoId, 2));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		// Jugador 1 inicia el juego.
		response = creator.http.post(Endpoint.of(Route.JUEGO_START, creator.juegoId));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		// Leemos todos los eventos desde el comienzo.
		var eventos = leerEventos(creator.http, creator.juegoId, 0);

		// Comprobamos cada uno de los tipos.

		LinkedHashMap<Object, Object> evento = eventos.get(0);
		assertEquals("JUGADOR_SE_UNE", evento.get("tipo"));

		evento = eventos.get(1);
		assertEquals("JUGADOR_SE_UNE", evento.get("tipo"));

		evento = eventos.get(2);
		assertEquals("INICIO_JUEGO", evento.get("tipo"));

		evento = eventos.get(3);
		assertEquals("INICIO_PARTIDA", evento.get("tipo"));

		var jugadores = (ArrayList<?>) evento.get("jugadores");
		assertEquals(2, jugadores.size());

		evento = eventos.get(4);
		assertEquals("MANO_INICIALIZADA", evento.get("tipo"));

		Debug.debug(eventos);
	}

	@SuppressWarnings("unchecked")
	private ArrayList<LinkedHashMap<Object, Object>> leerEventos(HttpClient http, String juegoId, int cantidadEsperada)
			throws Exception {
		var response = http.get(Endpoint.of(Route.EVENTO, juegoId, 0));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		return response.readEntity(ArrayList.class);
	}
}
