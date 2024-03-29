package org.xrgames.ruta.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.logic.Juego;
import org.xrgames.logic.Jugador;
import org.xrgames.logic.ModoJuego;
import org.xrgames.ruta.entity.Usuario;
import org.xrgames.ruta.util.Equipos;

public class EquiposTest {

	@Test
	void getTest() throws Exception {
		var collection = new Equipos();
		collection.put(1);
		collection.put(2);

		// 0 y 1 se refieren al id del equipo.
		var equipo1 = collection.get(String.valueOf(1));
		var equipo2 = collection.get(String.valueOf(2));

		assertTrue(equipo1.isSome());
		assertTrue(equipo2.isSome());

		assertTrue(equipo1.unwrap().nombre.equals(String.valueOf(1)));
		assertTrue(equipo2.unwrap().nombre.equals(String.valueOf(2)));
	}

	@Test
	void removeTest() {
		var collection = new Equipos();
		collection.put("1");
		collection.put("2");

		assertTrue(collection.exists("1"));
		assertTrue(collection.exists("2"));
		assertFalse(collection.exists("3"));
	}

	@Test
	void existsTest() {
		var collection = new Equipos();
		collection.put("1");
		collection.put("2");

		assertTrue(collection.exists("1"));
		assertTrue(collection.exists("2"));
		assertFalse(collection.exists("3"));
	}

	@Test
	void putEquipoTest() {
		// Probar cantidad por defecto.
		var collection = new Equipos();
		int i = Juego.MAX_EQUIPOS;
		while (i > 0) {
			collection.put(String.valueOf(i));
			--i;
		}

		assertEquals(Juego.MAX_EQUIPOS, collection.size());

		// Agregar un equipo adicional debe fallar.
		assertTrue(collection.put(String.valueOf(Juego.MAX_EQUIPOS)).isErr());
	}

	@Test
	@DisplayName("Distribución para juego por equipo")
	public void getJugadoresDistribuidosIndividualTest() throws Exception {

		var config = new ConfiguracionJuego();
		config.jugadores = Juego.MAX_JUGADORES;
		config.modo = ModoJuego.Individual;

		var usuarios = new ArrayList<Usuario>(6);
		for (int i = 0; i < config.jugadores; i++) {
			usuarios.add(new Usuario("Usuario" + i + 1));
		}

		// Unir usuarios al juego.
		var juego = new Juego(usuarios.get(0), config);
		for (int i = 0; i < config.jugadores; i++) {
			var usuario = usuarios.get(i);
			var jugador = new Jugador(usuario);
			var result = juego.joinJugador(0, jugador);
			assertTrue(result.isOk());
		}

		// Para un juego individual la distribución coincide con la entrada.
		var equipos = juego.getEquipos();

		var distribucion = equipos.getJugadoresDistribuidos(config.modo);
		assertEquals(Juego.MAX_JUGADORES, distribucion.size());

		for (int i = 0; i < Juego.MAX_JUGADORES; i++) {
			var usuario = usuarios.get(i);
			var distResult = distribucion.get(i);
			var sameId = usuario.id.equals(distResult.getUsuario().id);

			assertTrue(sameId);
		}
	}

	@Test
	@DisplayName("Dos jugadores del mismo equipo no pueden ser adyacentes")
	public void getJugadoresDistribuidosPorEquipoTest() throws Exception {
		var config = new ConfiguracionJuego();
		config.jugadores = Juego.MAX_JUGADORES;
		config.modo = ModoJuego.Equipo;

		var usuarios = new ArrayList<Usuario>(6);
		for (int i = 0; i < config.jugadores; i++) {
			usuarios.add(new Usuario("Usuario" + i + 1));
		}

		var juego = new Juego(usuarios.get(0), config);
		var equipos = juego.getEquipos();

		assertEquals(3, equipos.size(), "Deben crearse 3 equipos");

		// Unir usuarios al juego por equipos.
		juego.joinJugador(equipos.get(1).unwrap().id, usuarios.get(0).toJugador());
		juego.joinJugador(equipos.get(1).unwrap().id, usuarios.get(1).toJugador());
		juego.joinJugador(equipos.get(2).unwrap().id, usuarios.get(2).toJugador());
		juego.joinJugador(equipos.get(2).unwrap().id, usuarios.get(3).toJugador());
		juego.joinJugador(equipos.get(3).unwrap().id, usuarios.get(4).toJugador());
		juego.joinJugador(equipos.get(3).unwrap().id, usuarios.get(5).toJugador());

		var distribucion = equipos.getJugadoresDistribuidos(config.modo);
		assertEquals(Juego.MAX_JUGADORES, distribucion.size());

		assertTrue(equipos.get(1).unwrap().jugadores.containsKey(distribucion.get(0).getUsuario().id));
		assertTrue(equipos.get(2).unwrap().jugadores.containsKey(distribucion.get(1).getUsuario().id));
		assertTrue(equipos.get(3).unwrap().jugadores.containsKey(distribucion.get(2).getUsuario().id));
		assertTrue(equipos.get(1).unwrap().jugadores.containsKey(distribucion.get(3).getUsuario().id));
		assertTrue(equipos.get(2).unwrap().jugadores.containsKey(distribucion.get(4).getUsuario().id));
		assertTrue(equipos.get(3).unwrap().jugadores.containsKey(distribucion.get(5).getUsuario().id));
	}
}
