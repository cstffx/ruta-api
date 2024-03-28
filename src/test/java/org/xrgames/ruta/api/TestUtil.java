package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.Endpoint.Route;
import org.xrgames.ruta.services.client.HttpClient;
import org.xrgames.ruta.util.RandomModo;
import org.xrgames.ruta.util.RandomPlayers;

import jakarta.ws.rs.core.Response;

/**
 * Funciones de uso frecuente en los test.
 */
public class TestUtil {
	
	/**
	 * Reinicia el estado del servidor.
	 * @throws Exception
	 */
	public static void resetServer() throws Exception {
		var http = HttpClient.make();
		var response = http.post(Endpoint.of(Route.SERVER_RESET));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	/**
	 * Crea un nuevo juego.
	 * 
	 * @return El id del juego creado.
	 * @throws Exception
	 */
	public static CreateJuegoResult crearJuego() throws Exception {
		var http = HttpClient.forUser().unwrap();
		return crearJuego(http);
	}

	/**
	 * Crea un juego con la configuración.
	 * 
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public static CreateJuegoResult crearJuego(ConfiguracionJuego config) throws Exception {
		var http = HttpClient.forUser().unwrap();
		return crearJuego(http, config);
	}

	/**
	 * Crea un juego utilizando un HttpClient existente.
	 * 
	 * @param http
	 * @return
	 * @throws Exception
	 */
	public static CreateJuegoResult crearJuego(HttpClient http) throws Exception {
		var config = new ConfiguracionJuego();
		config.jugadores = RandomPlayers.next();
		config.modo = RandomModo.next();
		return crearJuego(http, config);
	}

	/**
	 * Crea un juego utilizando un HttpClient existente y una configuración.
	 * 
	 * @param http
	 * @return
	 * @throws Exception
	 */
	public static CreateJuegoResult crearJuego(HttpClient http, ConfiguracionJuego config) throws Exception {
		var response = http.post(Endpoint.of(Route.JUEGO_CREATE), config);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		var juegoId = response.readEntity(String.class);

		return new CreateJuegoResult(juegoId, http);
	}
}
