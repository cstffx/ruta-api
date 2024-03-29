package org.xrgames.ruta.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.logic.ModoJuego;
import org.xrgames.ruta.api.TestUtil;
import org.xrgames.ruta.entity.Event;
import org.xrgames.ruta.entity.EventType;
import org.xrgames.ruta.services.Debug;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.Endpoint.Route;
import org.xrgames.ruta.services.client.HttpClient;
import org.xrgames.ruta.util.Events;

import jakarta.ws.rs.core.Response;

/**
 * Comprueba los requisitos de la cola de eventos.
 */
public class EventosTest {

	@Test
	@DisplayName("La lista asigna ids consecutivos.")
	void addTest() {
		var eventos = new Events();

		eventos.add(new Event(EventType.PLAYER_ABANDON));
		eventos.add(new Event(EventType.PLAYER_ABANDON));
		eventos.add(new Event(EventType.PLAYER_ABANDON));

		var evento = new Event(EventType.PLAYER_ABANDON);
		eventos.add(evento);

		assertEquals(4, evento.getEventId());
	}

	@Test
	@DisplayName("Se recibe listado de eventos ocurridos")
	void getAllEventsTest() throws Exception {
		// Crear un juego para dos jugadores.
		var config = new ConfiguracionJuego(ModoJuego.Individual, 2);
		var currentId = 0;
		var creator = TestUtil.crearJuego(config);

		// Unir jugador 1
		var response = creator.http.post(Endpoint.of(Route.JUEGO_JOIN, creator.juegoId, 1));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		// Unir jugador 2
		var http2 = HttpClient.forUser().unwrap();
		response = http2.post(Endpoint.of(Route.JUEGO_JOIN, creator.juegoId, 2));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		// Iniciar el juego.
		response = creator.http.post(Endpoint.of(Route.JUEGO_START, creator.juegoId));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		response = creator.http.get(Endpoint.of(Route.EVENTO, creator.juegoId, currentId));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		var items = response.readEntity(ArrayList.class);
		Debug.debug(items);
		assertEquals(1, items.size());
	}
}
