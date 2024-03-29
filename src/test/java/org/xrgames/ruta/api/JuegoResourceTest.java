package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

public class JuegoResourceTest {

	@BeforeEach
	void beforeEach() throws Exception {
		TestUtil.resetServer();
	}

	@Test
	@DisplayName("El servicio debe mostrar una lista de juegos activos.")
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
		var result = http.post(Endpoint.of(Route.JUEGO_JOIN, "invalid_id", "0"));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getStatus());
	}

	@Test
	@DisplayName("El usuario puede unirse a un juego en modalidad individual.")
	void joinTest() throws Exception {
		// Creamos un juego.
		var http = HttpClient.forUser().unwrap();
		var formData = new ConfiguracionJuego(ModoJuego.Individual, Juego.MAX_JUGADORES);
		var response = http.post(Endpoint.of(Route.JUEGO_CREATE), formData);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		var juegoId = response.readEntity(String.class);
		assertNotNull(juegoId);

		// Usamos 0 cuando el id del equipo no importa.
		// Los jugadores en un juego individual se unen al siguiente equipo disponible.
		var result = http.post(Endpoint.of(Route.JUEGO_JOIN, juegoId, "0"));
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), "Unirse a un juego individual");
	}

	@Test
	@DisplayName("El usuario puede unirse a un juego en modalidad por equipo.")
	void joinTeamTest() throws Exception {
		// Creamos un juego.
		var http = HttpClient.forUser().unwrap();
		var formData = new ConfiguracionJuego(ModoJuego.Equipo, Juego.MAX_JUGADORES);
		var response = http.post(Endpoint.of(Route.JUEGO_CREATE), formData);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		var juegoId = response.readEntity(String.class);
		assertNotNull(juegoId);

		var equipoId = "1";

		// Los jugadores en un juego por equipo se unen a un equipo por su id.
		var result = http.post(Endpoint.of(Route.JUEGO_JOIN, juegoId, equipoId));
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(),
				"Unirse a un juego por equipo retorna 200 OK");

		// Intentar unirse a un equipo que no existe debe fallar.
		http = HttpClient.forUser().unwrap();
		result = http.post(Endpoint.of(Route.JUEGO_JOIN, juegoId, "20"));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getStatus(),
				"Unirse a un equipo que no existe retorna 404 Not Found");

		// Intentar unirse a un equipo completo debe fallar.

		// Unimos a jugador 2
		http = HttpClient.forUser().unwrap();
		result = http.post(Endpoint.of(Route.JUEGO_JOIN, juegoId, equipoId));
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());

		// Unir a jugador 3 debe fallar. Equipo completo
		http = HttpClient.forUser().unwrap();
		result = http.post(Endpoint.of(Route.JUEGO_JOIN, juegoId, equipoId));
		assertEquals(Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE.getStatusCode(), result.getStatus());
	}

	@Test
	@DisplayName("El usuario no puede unirse a dos juegos simultáneamente.")
	void joinDuplicatedTest() throws Exception {
		var formData = new ConfiguracionJuego();
		formData.jugadores = Juego.MAX_JUGADORES;
		formData.modo = ModoJuego.Individual;

		// Usuario 1 crea juego 1.
		var http = HttpClient.forUser().unwrap();
		var response = http.post(Endpoint.of(Route.JUEGO_CREATE), formData);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Usuario 1 debe crear juego individual");
		var juego1Id = response.readEntity(String.class);

		// Usuario 2 crea juego 2.
		http = HttpClient.forUser().unwrap();
		response = http.post(Endpoint.of(Route.JUEGO_CREATE), formData);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Usuario 2 debe crear juego individual");
		var juego2Id = response.readEntity(String.class);

		// Usuario 3 se registra.
		http = HttpClient.forUser().unwrap();

		// Usuario 3 se une correctamente al juego 1.
		var result = http.post(Endpoint.of(Route.JUEGO_JOIN, juego1Id, 0));
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), "Usuario 3 se une al juego 1");

		// Usuario 3 se une al juego 2 y falla.
		result = http.post(Endpoint.of(Route.JUEGO_JOIN, juego2Id, 0));
		assertEquals(Response.Status.FOUND.getStatusCode(), result.getStatus(), "Usuario 3 no podrá unirse al juego 2");
	}

	@Test
	@DisplayName("No se puede iniciar un juego donde faltan jugadores.")
	void startIncompleteGameTest() throws Exception {
		var formData = new ConfiguracionJuego();
		formData.jugadores = 2;
		formData.modo = ModoJuego.Individual;

		// Usuario 1 crea juego 1.
		var http1 = HttpClient.forUser().unwrap();
		var response = http1.post(Endpoint.of(Route.JUEGO_CREATE), formData);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
				"Usuario 1 debe crear juego individual.");
		var juegoId = response.readEntity(String.class);

		// Usuario 1 se une a juego 1.
		response = http1.post(Endpoint.of(Route.JUEGO_JOIN, juegoId, "0"));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Usuario 1 debe unirse a juego 1.");

		// Usuario 1 inicia el juego.
		response = http1.post(Endpoint.of(Route.JUEGO_START, juegoId));
		assertEquals(Response.Status.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatus(),
				"No se puede iniciar un juego donde faltan jugadores.");
	}

	@Test
	@DisplayName("El usuario puede iniciar un juego que ha creado y está completo.")
	void joinStarGameTest() throws Exception {
		var formData = new ConfiguracionJuego();
		formData.jugadores = 2;
		formData.modo = ModoJuego.Individual;

		// Usuario 1 crea juego 1.
		var http1 = HttpClient.forUser().unwrap();
		var response = http1.post(Endpoint.of(Route.JUEGO_CREATE), formData);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Usuario 1 debe crear juego individual");
		var juegoId = response.readEntity(String.class);

		// Usuario 1 se une a juego 1.
		response = http1.post(Endpoint.of(Route.JUEGO_JOIN, juegoId, "0"));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Usuario 1 debe unirse a juego 1");

		// Usuario 2 se une a juego 1.
		var http2 = HttpClient.forUser().unwrap();
		response = http2.post(Endpoint.of(Route.JUEGO_JOIN, juegoId, "0"));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Usuario 2 debe unirse a juego 1");
	}

	@Test
	@DisplayName("El usuario no puede iniciar un juego iniciado.")
	void startGameTest() throws Exception {
		var formData = new ConfiguracionJuego();
		formData.jugadores = 2;
		formData.modo = ModoJuego.Individual;

		// Usuario 1 crea juego 1.
		var http1 = HttpClient.forUser().unwrap();
		var response = http1.post(Endpoint.of(Route.JUEGO_CREATE), formData);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
				"Usuario 1 debe crear juego individual.");
		var juegoId = response.readEntity(String.class);

		// Usuario 1 se une a juego 1.
		response = http1.post(Endpoint.of(Route.JUEGO_JOIN, juegoId, "0"));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Usuario 1 debe unirse a juego 1.");

		// Usuario 2 se une a juego 1.
		var http2 = HttpClient.forUser().unwrap();
		response = http2.post(Endpoint.of(Route.JUEGO_JOIN, juegoId, "0"));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Usuario 2 debe unirse a juego 1.");

		// Usuario 1 inicia el juego.
		response = http1.post(Endpoint.of(Route.JUEGO_START, juegoId));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Usuario 1 inicia el juego.");

		// Usuario 1 intenta volverlo a iniciar el juego.
		response = http1.post(Endpoint.of(Route.JUEGO_START, juegoId));
		assertEquals(Response.Status.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatus(),
				"Usuario 1 inicia el juego.");
	}
}
