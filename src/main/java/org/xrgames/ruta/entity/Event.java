package org.xrgames.ruta.entity;

import java.util.HashMap;

/**
 * Clase padre de todos los eventos del juego.
 */
public class Event {

	long eventId = 0;

	final TipoEvento tipo;

	public Event(TipoEvento tipo) {
		this.tipo = tipo;
	}

	public TipoEvento getTipoEvento() {
		return this.tipo;
	}

	public void setEventId(long id) {
		this.eventId = id;
	}

	public long getEventId() {
		return this.eventId;
	}

	public HashMap<String, Object> toUserMap(Usuario reader) {
		var map = new HashMap<String, Object>();
		map.put("tipo", tipo);
		return map;
	}
}
