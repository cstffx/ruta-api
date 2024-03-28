package org.xrgames.ruta.util;

import java.util.LinkedList;

import org.xrgames.ruta.entity.Event;

/**
 * Representan una colección de eventos del juego.
 */
public class Events extends LinkedList<Event> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Agrega un evento a la lista.
	 */
	public synchronized boolean add(Event event) {
		event.setEventId(size() + 1);
		return super.add(event);
	}
	
	/**
	 * Retorna todos los eventos ocurridos a partir de un punto del tiempo.
	 * @param timestamp
	 * @return
	 */
	Events getAllFrom(int eventId){
		return (Events) this.subList(eventId - 1, size());
	}
}
