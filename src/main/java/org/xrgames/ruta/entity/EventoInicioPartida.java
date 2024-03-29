package org.xrgames.ruta.entity;

import java.util.ArrayList;
import java.util.HashMap;

import org.xrgames.logic.Jugador;
import org.xrgames.logic.Partida;

/**
 * Ocurre cuando se comienza una nueva partida.
 * Informa los jugadores presentes.
 */
public class EventoInicioPartida extends Evento {

	final Partida partida;
	
	public class JugadorInfo {
		public String username;
		public int equipoId;
	}

	public EventoInicioPartida(Partida partida) {
		super(TipoEvento.INICIO_PARTIDA);
		this.partida = partida;
	}
 
	/**
	 * Informa los jugadores presentes en la partida. 
	 * @throws Exception
	 */
	public HashMap<String, Object> toInformacion(Usuario reader) {
		var map = super.toInformacion(reader);
		var jugadores = partida.getJugadores();
		var resultado = new ArrayList<JugadorInfo>();
		
		for(Jugador jugador: jugadores) {
			var info =new JugadorInfo();
			info.username = jugador.getUsuario().username;
			info.equipoId = jugador.getEquipo();
			resultado.add(info);
		}
		
		map.put("jugadores", resultado);
		return map;
	} 
}
