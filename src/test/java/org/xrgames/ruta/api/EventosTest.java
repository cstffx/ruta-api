package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.logic.ModoJuego;
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
		
		Debug.debug(eventos);

		// Comprobamos cada uno de los tipos.

		LinkedHashMap<Object, Object> evento = eventos.get(0);
		assertEquals("JUGADOR_SE_UNE", evento.get("eventoTipo"));

		evento = eventos.get(1);
		assertEquals("JUGADOR_SE_UNE", evento.get("eventoTipo"));

		evento = eventos.get(2);
		assertEquals("INICIO_JUEGO", evento.get("eventoTipo"));

		evento = eventos.get(3);
		assertEquals("INICIO_PARTIDA", evento.get("eventoTipo"));

		var jugadores = (ArrayList<?>) evento.get("jugadores");
		assertEquals(2, jugadores.size());

		evento = eventos.get(4);
		assertEquals("MANO_INICIALIZADA", evento.get("eventoTipo"));
		assertEquals(6, ((ArrayList<?>)evento.get("mano")).size());
		assertNotNull(evento.get("repartidor"));
		
		evento = eventos.get(5);
		assertEquals("CAMBIO_DE_JUGADOR", evento.get("eventoTipo"));
		assertNotNull(evento.get("jugador"));
		
		// Leemos todos los eventos desde el comienzo.
		eventos = leerEventos(creator.http, creator.juegoId, 6);
		assertEquals(0, eventos.size());
	}

	@SuppressWarnings("unchecked")
	private ArrayList<LinkedHashMap<Object, Object>> leerEventos(HttpClient http, String juegoId, int idInicial)
			throws Exception {
		var response = http.get(Endpoint.of(Route.EVENTO, juegoId, idInicial));
		if(response.getStatus() == Response.Status.OK.getStatusCode()) {
			return response.readEntity(ArrayList.class);
		}else {
			Debug.debug(response);
			throw new Exception("Falló la lectura de eventos.");
		}
	}
}
