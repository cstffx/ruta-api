package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.Endpoint.Route;
import org.xrgames.ruta.services.LoginForm;
import org.xrgames.ruta.services.client.HttpClient;

import jakarta.ws.rs.core.Response;

public class JugadorResourceTest {

	@BeforeEach
	void beforeEach() throws Exception {
		TestUtil.resetServer();
	}

	@Test
	@DisplayName("getAll")
	void getAll() throws Exception {
		Response response;
		response = HttpClient.forUser().unwrap().get(Endpoint.of(Route.JUGADOR));
		assertEquals(200, response.getStatus());
	}

	@Test
	@DisplayName("login")
	public void loginTest() throws Exception {
		Response response;
		var username = "test";
		var http = HttpClient.forUser(username).unwrap();

		// 400 cuando el usuario está vacío.
		response = http.post(Endpoint.of(Route.JUGADOR_LOGIN), new LoginForm(""));
		assertEquals(400, response.getStatus());

		// 400 cuando el usuario es null.
		response = http.post(Endpoint.of(Route.JUGADOR_LOGIN), new LoginForm(null));
		assertEquals(400, response.getStatus());

		// 200 cuando el usuario es una cadena válida
		response = http.post(Endpoint.of(Route.JUGADOR_LOGIN), new LoginForm(username));
		assertEquals(200, response.getStatus());

		// Se puede acceder a un endpoint protegido.
		response = http.get(Endpoint.of(Route.STATUS_PROTECTED));
		assertEquals(200, response.getStatus());

		response = http.get(Endpoint.of(Route.JUGADOR));
		// Comprobar que el usuario existe en el listado de usuarios.
		var users = response.readEntity(ArrayList.class);
		var exists = false;
		var iterator = users.listIterator();
		while (iterator.hasNext()) {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> user = (LinkedHashMap<String, Object>) iterator.next();
			if (user.get("username").equals(username)) {
				exists = true;
				break;
			}
		}
		assertTrue(exists);

		// Salir.
		response = http.post(Endpoint.of(Route.JUGADOR_LOGOUT));
		assertEquals(200, response.getStatus());

		// No podremos acceder a un endpoint protegido.
		response = http.get(Endpoint.of(Route.STATUS_PROTECTED));
		assertEquals(403, response.getStatus());
	}
}
