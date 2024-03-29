package org.xrgames.ruta.entity;

import java.util.HashMap;

import org.xrgames.logic.Jugador;

/**
 * Ocurre cuando se comienza una nueva partida. Informa los jugadores presentes.
 */
public class EventoCambioJugador extends Evento {

	final Jugador jugador;

	public class JugadorInfo {
		public String username;
		public int equipoId;
	}

	public EventoCambioJugador(Jugador jugador) {
		super(TipoEvento.CAMBIO_DE_JUGADOR);
		this.jugador = jugador;
	}

	/**
	 * Informa los jugadores presentes en la partida.
	 * 
	 * @throws Exception
	 */
	public HashMap<String, Object> toInformacion(Usuario reader) {
		var map = super.toInformacion(reader);
		var info = new JugadorInfo();
		info.username = jugador.getUsuario().username;
		info.equipoId = jugador.getEquipo();
		map.put("jugador", info);
		return map;
	}
}
