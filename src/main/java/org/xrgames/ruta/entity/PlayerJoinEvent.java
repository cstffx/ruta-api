package org.xrgames.ruta.entity;

import java.util.HashMap;

/**
 * Ocurre cuando un jugador se une a un equipo.
 */
public class PlayerJoinEvent extends Event {

	final Usuario usuario;

	public PlayerJoinEvent(Usuario usuario) {
		super(EventType.PLAYER_JOIN);
		this.usuario = usuario;
	}

	public HashMap<String, Object> toUserMap(Usuario reader) {
		var map = new HashMap<String, Object>();
		map.put("username", usuario.getUsername());
		return map;
	}
}
