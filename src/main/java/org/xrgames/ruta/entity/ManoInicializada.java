package org.xrgames.ruta.entity;

import java.util.HashMap;

import org.xrgames.logic.Jugador;
import org.xrgames.logic.Partida;

/**
 * Ocurre cuando el jugador recibe una nueva mano de cartas al comienzo de una partida.
 */
public class ManoInicializada extends Event {

	final Partida partida;

	public ManoInicializada(Partida partida) {
		super(TipoEvento.INICIO_JUEGO);
		this.partida = partida;
	}

	/**
	 * Al crear el map que se envía al usuario tiene en cuenta solo incluir las
	 * cartas del usuario que solicita leer el evento.
	 * 
	 * @throws Exception
	 */
	public HashMap<String, Object> toUserMap(Usuario reader) {
		var map = super.toUserMap(reader);
		var jugadorOption = partida.getJugadores().findByUsuario(reader);

		if (jugadorOption.isNone()) {
			return map;
		}

		Jugador jugador = null;
		try {
			jugador = jugadorOption.unwrap();
		} catch (Exception e) {
			// Nunca se ejecuta.
		}
		
		var mano = jugador.getMano();
		
		map.put("mano", mano);
		
		return map;
	}
}
