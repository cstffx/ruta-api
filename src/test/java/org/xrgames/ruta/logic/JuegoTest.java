package org.xrgames.ruta.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.xrgames.logic.Equipo;
import org.xrgames.logic.EquipoCollection;
import org.xrgames.logic.Juego;

public class JuegoTest {
	@Test 
	void addEquipoTest() {
		// Probar cantidad por defecto.
		var collection = new EquipoCollection();
		int i = Juego.MAX_EQUIPOS;
		while(i > 0) {
			collection.add(new Equipo(i));
			--i;
		}
		
		assertEquals(Juego.MAX_EQUIPOS, collection.size());
	
		// Agregar un equipo adicional debe fallar. 
		assertFalse(collection.add(Juego.MAX_EQUIPOS));
	}
}
