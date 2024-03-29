package org.xrgames.ruta.entity;

import java.util.HashMap;

import org.xrgames.logic.Jugador;
import org.xrgames.logic.Partida;

/**
 * Ocurre cuando el jugador recibe una nueva mano de cartas al comienzo de una partida.
 */
public class EventoManoInicializada extends Evento {

	final Partida partida;

	public EventoManoInicializada(Partida partida) {
		super(TipoEvento.MANO_INICIALIZADA);
		this.partida = partida;
	}

	/**
	 * Al crear el map que se envía al usuario tiene en cuenta solo incluir las
	 * cartas del usuario que solicita leer el evento.
	 * 
	 * @throws Exception
	 */
	public HashMap<String, Object> toInformacion(Usuario lector) {
		var map = super.toInformacion(lector);
		var jugadorEncontrado = partida.getJugadores().buscarPorUsuario(lector);

		if (jugadorEncontrado.isNone()) {
			return map;
		}

		Jugador jugador = null;
		try {
			jugador = jugadorEncontrado.unwrap();
		} catch (Exception e) {
			// Nunca se ejecuta.
		}
		
		var mano = jugador.getMano();
		
		map.put("mano", mano.toArray());
		
		return map;
	}
}
