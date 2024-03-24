package org.xrgames.ruta.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.xrgames.logic.Juego;
import org.xrgames.ruta.entity.Usuario;
import org.xrgames.ruta.util.JuegoMap;
import org.xrgames.ruta.util.UsernameFactory;

public class JuegoMapTest {
	@Test
	public void getTest() throws Exception {
		JuegoMap map = new JuegoMap();
		var usuario1 = Usuario.of(UsernameFactory.next());
		var juego1 = new Juego();
		
		juego1.setOwner(usuario1);
		map.put(UUID.randomUUID().toString(), juego1);
		
		// Debe encontrar el juego por propietario.
		var result = map.get(usuario1);
		assertTrue(result.isSome());
		assertEquals(usuario1, result.unwrap().getOwner().unwrap());
		
		// Debe indicar que tiene un juego registrado.
		assertTrue(map.hasOne(usuario1));
	}
	
	@Test
	public void hasOneTest() throws Exception {
		JuegoMap map = new JuegoMap();
		var usuario1 = Usuario.of(UsernameFactory.next());
		var juego1 = new Juego();
		
		juego1.setOwner(usuario1);
		map.put(UUID.randomUUID().toString(), juego1);
		
		// Debe indicar que tiene un juego registrado.
		assertTrue(map.hasOne(usuario1));
	}
}
