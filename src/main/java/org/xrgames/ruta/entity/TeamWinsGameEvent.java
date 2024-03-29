package org.xrgames.ruta.entity;

import java.util.HashMap;

import org.xrgames.logic.Equipo;

/**
 * Ocurre cuando un equipo gana un juego.
 */
public class TeamWinsGameEvent extends Event {
	
	final Equipo equipo;
	
	public TeamWinsGameEvent(Equipo equipo) {
		super(EventType.TEAM_WINS_GAME);
		this.equipo = equipo;
	}
	
	public HashMap<String, Object> toUserMap(Usuario reader) {
		var map = super.toUserMap(reader);
		map.put("equipoId", equipo.id);
		map.put("equipoNombre", equipo.nombre);
		return map;
	}
}
