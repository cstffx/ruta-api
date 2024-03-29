package org.xrgames.ruta.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.logic.Juego;
import org.xrgames.logic.ModoJuego;
import org.xrgames.ruta.entity.Usuario;
import org.xrgames.ruta.util.RandomPlayers;
import org.xrgames.ruta.util.UsernameFactory;

public class JuegoTest {
	@Test
	@DisplayName("Debe crearse un juego con equipos según configuracion")
	void crearTest() {
		var owner = new Usuario(UsernameFactory.next());

		// Las variantes individuales deben tener misma cantidad
		// de equipos por jugador.
		for (int cantidad : RandomPlayers.jugadores) {
			var config = new ConfiguracionJuego(ModoJuego.Individual, cantidad);
			var juego = new Juego(owner, config);
			assertEquals(juego.getEquipos().size(), cantidad);
		}

		// Variantes por equipo de 4 jugadores.
		var config = new ConfiguracionJuego(ModoJuego.Equipo, 4);
		var juego = new Juego(owner, config);
		assertEquals(juego.getEquipos().size(), 2);

		// Variantes por equipo de 6 jugadores.
		config = new ConfiguracionJuego(ModoJuego.Equipo, 6);
		juego = new Juego(owner, config);
		assertEquals(juego.getEquipos().size(), 3);
	}
}
