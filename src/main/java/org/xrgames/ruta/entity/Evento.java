package org.xrgames.ruta.entity;

import java.util.HashMap;

/**
 * Clase padre de todos los eventos del juego.
 */
public class Evento {

	long eventId = 0;

	final TipoEvento tipo;

	public Evento(TipoEvento tipo) {
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

	public HashMap<String, Object> toInformacion(Usuario reader) {
		var map = new HashMap<String, Object>();
		map.put("eventoId", eventId);
		map.put("eventoTipo", tipo);
		return map;
	}
}
