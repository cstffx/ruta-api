package org.xrgames.ruta.logic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.xrgames.logic.EquipoCollection;
import org.xrgames.logic.Juego;
import org.xrgames.logic.Equipo;

public class EquipoCollectionTest {
	
	@Test
	void existsTest() {
		var collection = new EquipoCollection(); 
		collection.add(1);
		collection.add(2);
		
		assertTrue(collection.exists(1));
		assertTrue(collection.exists(2));
		assertFalse(collection.exists(3));
	}
	
	@Test
	void indexOfTest() {
		var collection = new EquipoCollection(); 
		collection.add(1);
		collection.add(2);
		collection.add(3); 
		
		assertEquals(2, collection.indexOf(3));
		assertEquals(1, collection.indexOf(3));
		assertEquals(2, collection.indexOf(3));
	}
	
	@Test 
	void addEquipoTest() {
		// Probar cantidad por defecto.
		var collection = new EquipoCollection();
		int i = Juego.MAX_EQUIPOS;
		while(i > 0) {
			collection.add(new Equipo(i));
		}
		
		assertEquals(Juego.MAX_EQUIPOS, collection.size());
	
	
		// Agregar un equipo adicional debe fallar. 
		assertFalse(collection.add());
	}
}
