package org.xrgames.ruta.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.xrgames.logic.EquipoMap;
import org.xrgames.logic.Juego;

public class EquipoCollectionTest {
	
	@Test
	void getTest() throws Exception {
		var collection = new EquipoMap(); 
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
		var collection = new EquipoMap(); 
		collection.put("1");
		collection.put("2");
		
		assertTrue(collection.exists("1"));
		assertTrue(collection.exists("2"));
		assertFalse(collection.exists("3"));
	}
	
	@Test
	void existsTest() {
		var collection = new EquipoMap(); 
		collection.put("1");
		collection.put("2");
		
		assertTrue(collection.exists("1"));
		assertTrue(collection.exists("2"));
		assertFalse(collection.exists("3"));
	}
	
	@Test 
	void putEquipoTest() {
		// Probar cantidad por defecto.
		var collection = new EquipoMap();
		int i = Juego.MAX_EQUIPOS;
		while(i > 0) {
			collection.put(String.valueOf(i));
			--i;
		}
		
		assertEquals(Juego.MAX_EQUIPOS, collection.size());
	
		// Agregar un equipo adicional debe fallar. 
		assertTrue(collection.put(String.valueOf(Juego.MAX_EQUIPOS)).isErr());
	}
}
