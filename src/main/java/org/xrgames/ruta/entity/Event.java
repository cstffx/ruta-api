package org.xrgames.ruta.entity;

/**
 * Clase padre de todos los eventos del juego.
 */
public class Event {
	
	long timestap; 
	
	EventType tipo;
	
	public Event(EventType tipo) {
		this.tipo = tipo;
		this.timestap = System.currentTimeMillis();
	}
	
	public EventType getTipoEvento() {
		return this.tipo;
	}
	
	public long getTimestamp() {
		return this.timestap;
	}
}
