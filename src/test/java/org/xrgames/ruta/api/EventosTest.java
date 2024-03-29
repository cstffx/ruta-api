package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.logic.ModoJuego;
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
	
	@Test
	@DisplayName("Lista de eventos progresiva")
	void getAllEventsProgressiveTest() throws Exception {
		
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
		
		// Usuario 1 lee la lista de eventos a partir del 4er evento, pero no hay resultado.
		response = creator.http.get(Endpoint.of(Route.EVENTO, creator.juegoId, 3));
		var items = response.readEntity(ArrayList.class);
		assertTrue(items.isEmpty());

		// Usuario 1 lee la lista de eventos a partir del 3er evento y encuentra 1 resultado.
		response = creator.http.get(Endpoint.of(Route.EVENTO, creator.juegoId, 2));
		items = response.readEntity(ArrayList.class);
		assertEquals(1, items.size());
		
		// Usuario 1 lee la lista de eventos a partir del 2do evento y encuentra 2 resultado.
		response = creator.http.get(Endpoint.of(Route.EVENTO, creator.juegoId, 1));
		items = response.readEntity(ArrayList.class);
		assertEquals(2, items.size());
		
		// Usuario 1 lee la lista de eventos a partir del 1er evento y encuentra 3 resultados.
		response = creator.http.get(Endpoint.of(Route.EVENTO, creator.juegoId, 0));
		items = response.readEntity(ArrayList.class);
		assertEquals(3, items.size());
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<Object> leerEventos(HttpClient http, String juegoId, int cantidadEsperada) throws Exception {
		var response = http.get(Endpoint.of(Route.EVENTO, juegoId, 0));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		return (ArrayList<Object>)response.readEntity(ArrayList.class);
	}
	
	/**
	 * Lee la lista de eventos desde el comienzo y comprueba la cantidad de 
	 * eventos acumulados.
	 * @param http
	 * @param juegoId
	 * @param cantidadEsperada
	 * @throws Exception 
	 */
	private ArrayList<Object> comprobarCantidadEventos(HttpClient http, String juegoId, int cantidadEsperada) throws Exception {
		var items = leerEventos( http,  juegoId, cantidadEsperada);
		assertEquals(cantidadEsperada, items.size());
		return items;
	}
}
