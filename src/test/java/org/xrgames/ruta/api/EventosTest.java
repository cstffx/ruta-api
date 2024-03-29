package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.logic.ModoJuego;
import org.xrgames.ruta.api.TestUtil;
import org.xrgames.ruta.entity.Event;
import org.xrgames.ruta.entity.TipoEvento;
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
	@DisplayName("Se recibe listado de eventos ocurridos")
	void getAllEventsTest() throws Exception {
		
		// Crear un juego para dos jugadores.
		var config = new ConfiguracionJuego(ModoJuego.Individual, 2);
		var creator = TestUtil.crearJuego(config);

		// Unir jugador 1
		var response = creator.http.post(Endpoint.of(Route.JUEGO_JOIN, creator.juegoId, 1));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		comprobarCantidadEventos(creator.http, creator.juegoId, 1);
		
		// Unir jugador 2
		var http2 = HttpClient.forUser().unwrap();
		response = http2.post(Endpoint.of(Route.JUEGO_JOIN, creator.juegoId, 2));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		comprobarCantidadEventos(creator.http, creator.juegoId, 2);

		// Jugador 1 inicia el juego.
		response = creator.http.post(Endpoint.of(Route.JUEGO_START, creator.juegoId));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		comprobarCantidadEventos(creator.http, creator.juegoId, 3);
	}
	
	/**
	 * Lee la lista de eventos desde el comienzo y comprueba la cantidad de 
	 * eventos acumulados.
	 * @param http
	 * @param juegoId
	 * @param cantidadEsperada
	 * @throws Exception 
	 */
	private void comprobarCantidadEventos(HttpClient http, String juegoId, int cantidadEsperada) throws Exception {
		var response = http.get(Endpoint.of(Route.EVENTO, juegoId, 0));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		var items = response.readEntity(ArrayList.class);
		Debug.debug(items);
		assertEquals(cantidadEsperada, items.size());
	}
}
