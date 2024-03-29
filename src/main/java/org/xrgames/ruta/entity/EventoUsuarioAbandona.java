package org.xrgames.ruta.entity;

import java.util.HashMap;

/**
 * Ocurre cuando un jugador abandona una partida.
 */
public class EventoUsuarioAbandona extends Evento {

	final Usuario usuario;

	public EventoUsuarioAbandona(Usuario usuario) {
		super(TipoEvento.JUGADOR_ABANDONA);
		this.usuario = usuario;
	}

	public HashMap<String, Object> toInformacion(Usuario reader) {
		var map = super.toInformacion(reader);
		map.put("username", usuario.getUsername());
		return map;
	}
}
