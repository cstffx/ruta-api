package org.xrgames.ruta.logic;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.logic.Juego;
import org.xrgames.logic.Jugador;
import org.xrgames.logic.Partida;
import org.xrgames.ruta.entity.Usuario;

/**
 * Comprueba las condiciones de la partida.
 */
public class PartidaTest {
	@Test
	@DisplayName("Conseguir un jugador aleatorio")
	void randomPlayerTest() {
		
		var partida = new Partida();
		
		for(int i = 0; i < Juego.MAX_JUGADORES; i++) {
			var usuario = new Usuario("Username " + i);
			partida.agregarJugador(new Jugador(usuario));
		}
		
		for(int i = 0; i < 100; i++) {
			assertNotNull(partida.getJugadorAlAzar());
		}
		
	}
}
