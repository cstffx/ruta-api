package org.xrgames.ruta.entity;

import java.util.HashMap;

/**
 * Ocurre cuando un jugador abandona una partida.
 */
public class PlayerAbandon extends Event {

	final Usuario usuario;

	public PlayerAbandon(Usuario usuario) {
		super(TipoEvento.JUGADOR_ABANDONA);
		this.usuario = usuario;
	}

	public HashMap<String, Object> toUserMap(Usuario reader) {
		var map = super.toUserMap(reader);
		map.put("username", usuario.getUsername());
		return map;
	}
}
