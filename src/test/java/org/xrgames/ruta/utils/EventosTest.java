package org.xrgames.ruta.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.logic.ModoJuego;
import org.xrgames.ruta.api.TestUtil;
import org.xrgames.ruta.entity.Event;
import org.xrgames.ruta.entity.TipoEvento;
import org.xrgames.ruta.services.Debug;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.Endpoint.Route;
import org.xrgames.ruta.services.client.HttpClient;
import org.xrgames.ruta.util.Events;

import jakarta.ws.rs.core.Response;

/**
 * Comprueba los requisitos de la cola de eventos.
 */
public class EventosTest {

	@Test
	@DisplayName("La lista asigna ids consecutivos.")
	void addTest() {
		var eventos = new Events();

		eventos.add(new Event(TipoEvento.JUGADOR_ABANDONA));
		eventos.add(new Event(TipoEvento.JUGADOR_ABANDONA));
		eventos.add(new Event(TipoEvento.JUGADOR_ABANDONA));

		var evento = new Event(TipoEvento.JUGADOR_ABANDONA);
		eventos.add(evento);

		assertEquals(4, evento.getEventId());
	}
}
