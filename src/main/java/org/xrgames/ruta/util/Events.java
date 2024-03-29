package org.xrgames.ruta.util;

import java.util.ArrayList;
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
	public Events getAllAfter(int eventId){
		
		if(eventId == 0) {
			return this;
		}
		
		var events = new Events(); 
		if(eventId == size()) {
			return new Events();
		}
		
		for(int i = eventId; i < size(); i++) {
			events.add(this.get(i));
		}
		
		return events;
	}
}
