package org.xrgames.ruta.entity;

import java.util.HashMap;

/**
 * Ocurre cuando un jugador se une a un equipo.
 */
public class EventoJugadorSeUne extends Evento {

	final Usuario usuario;

	public EventoJugadorSeUne(Usuario usuario) {
		super(TipoEvento.JUGADOR_SE_UNE);
		this.usuario = usuario;
	}

	public HashMap<String, Object> toInformacion(Usuario reader) {
		var map = super.toInformacion(reader);
		map.put("username", usuario.getUsername());
		return map;
	}
}
