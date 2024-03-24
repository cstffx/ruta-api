package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.logic.ModoJuego;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.Endpoint.Route;
import org.xrgames.ruta.services.client.HttpClient;
import org.xrgames.ruta.util.RandomModo;
import org.xrgames.ruta.util.RandomPlayers;

import jakarta.ws.rs.core.Response;

public class JuegoResourceTest {

	@Test
	@DisplayName("El servicio debe mostrar una lista de juegos activos")
	void getAll() throws Exception {
		// Cantidad de juegos que se van a crear
		int createCount = 10;

		// Eliminar todos los juegos y usuarios activos.
		var http = HttpClient.make();
		var response = http.post(Endpoint.of(Route.SERVER_RESET));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		// Registrar 10 juegos.
		for (int i = 0; i < createCount; i++) {
			TestUtil.crearJuego();
		}

		// Obtener información para otro usuario registrado
		http = HttpClient.forUser().unwrap();
		response = http.get(Endpoint.of(Route.JUEGO));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		// Comprobar la cantidad de juegos creados.
		var juegos = response.readEntity(ArrayList.class);
		assertEquals(createCount, juegos.size());
	}

	@Test
	@DisplayName("Comprobar creación del juego")
	void createTest() throws Exception {
		var http = HttpClient.forUser().unwrap();
		var formData = new ConfiguracionJuego();

		formData.jugadores = 3;
		formData.modo = ModoJuego.Individual;

		var response = http.post(Endpoint.of(Route.JUEGO_CREATE), formData);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		var content = response.readEntity(String.class);
		assertTrue(content.length() > 0);
	}

	@Test
	@DisplayName("Un jugador no puede tener dos juegos registrados")
	void createMoreThanOne() throws Exception {
		var http = HttpClient.forUser().unwrap();
		var formData = new ConfiguracionJuego();

		formData.jugadores = 3;
		formData.modo = ModoJuego.Individual;

		// Crear el primer juego para el usuario.
		var response = http.post(Endpoint.of(Route.JUEGO_CREATE), formData);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		// Intentar crear otro juego para el mismo usuario.
		response = http.post(Endpoint.of(Route.JUEGO_CREATE), formData);
		assertEquals(Response.Status.FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	@DisplayName("Debe terminarse un juego en curso solo por su propietario.")
	public void endTest() throws Exception {
		var http = HttpClient.forUser().unwrap();
		var formData = new ConfiguracionJuego();

		formData.jugadores = 3;
		formData.modo = ModoJuego.Individual;

		var response = http.post(Endpoint.of(Route.JUEGO_CREATE), formData);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		var juegoId = response.readEntity(String.class);
		assertTrue(juegoId.length() > 0);

		// Terminar el juego creado.
		response = http.post(Endpoint.of(Route.JUEGO_END, juegoId), formData);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		// Crear otro juego con usuario 2.
		var http2 = HttpClient.forUser().unwrap();
		response = http2.post(Endpoint.of(Route.JUEGO_CREATE), formData);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		var juego2Id = response.readEntity(String.class);
		assertTrue(juego2Id.length() > 0);

		// Intentar que el usuario 1 lo termine.
		response = http.post(Endpoint.of(Route.JUEGO_END, juego2Id), formData);
		assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());

		// El jugador 2 termina el juego creado.
		response = http2.post(Endpoint.of(Route.JUEGO_END, juego2Id), formData);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		// El jugador 2 intenta terminar un juego ya terminado.
		response = http2.post(Endpoint.of(Route.JUEGO_END, juego2Id), formData);
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

		// El jugador 1 intenta terminar un juego yar terminado.
		response = http.post(Endpoint.of(Route.JUEGO_END, juegoId), formData);
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	@DisplayName("El usuario no puede unirse a un juego que no existe.")
	void joinNotFoundTest() throws Exception {
		// Registramos un usuario.
		var http = HttpClient.forUser().unwrap();

		// Unimos al usuario a un juego que no existe.
		var result = http.post(Endpoint.of(Route.JUEGO_JOIN, "invalid_id"));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getStatus());
	}

	
	@Test
	@DisplayName("El usuario puede unirse a un juego.")
	void joinTest() throws Exception {
		var creator = TestUtil.crearJuego();
		var result = creator.http.post(Endpoint.of(Route.JUEGO_JOIN, creator.juegoId));
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
	}


	/*
	 * @Test
	 * 
	 * @DisplayName("El usuario no puede unirse a dos juegos simultáneamente.") void
	 * joinDuplicatedTest() throws Exception { // Usuario 1 crea juego 1. var
	 * juego1Creator = TestUtil.crearJuego(); // Usuario 2 crea juego 2. var
	 * juego2Creator = TestUtil.crearJuego();
	 * 
	 * // Usuario 3 se registra. var http = HttpClient.forUser().unwrap();
	 * 
	 * // Usuario 3 se une correctamente al juego 1. var result =
	 * http.post(Endpoint.of(Route.JUEGO_JOIN, juego1Creator.juegoId));
	 * assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
	 * 
	 * // Usuario 3 se une al juego 2 y falla. result =
	 * http.post(Endpoint.of(Route.JUEGO_JOIN, juego2Creator.juegoId));
	 * assertEquals(Response.Status.FOUND.getStatusCode(), result.getStatus()); }
	 */
}
