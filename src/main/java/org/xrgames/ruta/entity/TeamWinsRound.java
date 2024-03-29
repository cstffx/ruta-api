package org.xrgames.ruta.entity;

import java.util.HashMap;

import org.xrgames.logic.Equipo;

/**
 * Ocurre cuando un equipo gana un juego.
 */
public class TeamWinsRound extends Event {

	final Equipo equipo;

	public TeamWinsRound(Equipo equipo) {
		super(TipoEvento.EQUIPO_GANA_JUEGO);
		this.equipo = equipo;
	}

	public HashMap<String, Object> toUserMap(Usuario reader) {
		var map = super.toUserMap(reader);
		map.put("equipoId", equipo.id);
		map.put("equipoNombre", equipo.nombre);
		return map;
	}
}
