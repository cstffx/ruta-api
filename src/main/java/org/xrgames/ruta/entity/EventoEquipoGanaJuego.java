package org.xrgames.ruta.entity;

import java.util.HashMap;

import org.xrgames.logic.Equipo;

/**
 * Ocurre cuando un equipo gana un juego.
 */
public class EventoEquipoGanaJuego extends Evento {

	final Equipo equipo;

	public EventoEquipoGanaJuego(Equipo equipo) {
		super(TipoEvento.EQUIPO_GANA_JUEGO);
		this.equipo = equipo;
	}

	public HashMap<String, Object> toInformacion(Usuario reader) {
		var map = super.toInformacion(reader);
		map.put("equipoId", equipo.id);
		map.put("equipoNombre", equipo.nombre);
		return map;
	}
}
