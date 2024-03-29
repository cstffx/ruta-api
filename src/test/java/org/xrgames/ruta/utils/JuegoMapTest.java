package org.xrgames.ruta.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.xrgames.logic.Juego;
import org.xrgames.ruta.entity.Usuario;
import org.xrgames.ruta.util.Juegos;
import org.xrgames.ruta.util.UsernameFactory;

public class JuegoMapTest {

	@Test
	public void getTest() throws Exception {
		Juegos map = new Juegos();
		var usuario1 = Usuario.of(UsernameFactory.next());
		var juego1 = new Juego(usuario1);

		map.put(juego1);

		// Debe encontrar el juego por propietario.
		var result = map.get(usuario1);
		assertTrue(result.isSome());
		assertEquals(usuario1, result.unwrap().getOwner());

		// Debe indicar que tiene un juego registrado.
		assertTrue(map.hasOne(usuario1));
	}

	@Test
	public void hasOneTest() throws Exception {
		Juegos map = new Juegos();
		var usuario1 = Usuario.of(UsernameFactory.next());
		var juego1 = new Juego(usuario1);

		map.put(juego1);

		// Debe indicar que tiene un juego registrado.
		assertTrue(map.hasOne(usuario1));
	}
}
