package org.xrgames.ruta.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.ruta.entity.Event;
import org.xrgames.ruta.entity.EventType;
import org.xrgames.ruta.util.Events;

/**
 * Comprueba los requisitos de la cola de eventos.
 */
public class EventosTest {
	
	@Test
	@DisplayName("La lista asigna ids consecutivos.")
	void addTest() {
		var eventos = new Events();
		
		eventos.add(new Event(EventType.PLAYER_ABANDON));
		eventos.add(new Event(EventType.PLAYER_ABANDON));
		eventos.add(new Event(EventType.PLAYER_ABANDON));
		
		var evento = new Event(EventType.PLAYER_ABANDON);
		eventos.add(evento);
		
		assertEquals(4, evento.getEventId());
	}
}
