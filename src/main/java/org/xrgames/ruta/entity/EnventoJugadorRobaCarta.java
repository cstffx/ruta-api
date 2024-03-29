package org.xrgames.ruta.entity;

import java.util.HashMap;

import org.xrgames.logic.Carta;

/**
 * Ocurre cuando un jugador roba una carta.
 */
public class EnventoJugadorRobaCarta extends Evento {

	final Usuario usuario;
	final Carta carta;

	public EnventoJugadorRobaCarta(Usuario usuario, Carta carta) {
		super(TipoEvento.JUGADOR_ROBA_CARTA);
		this.usuario = usuario;
		this.carta = carta;
	}

	public HashMap<String, Object> toInformacion(Usuario reader) {
		var map = super.toInformacion(reader);
		// Damos acceso a los detalles solo al propietario de la accion
		if (usuario == reader) {
			var direccion = carta.getDireccion();
			var tipo = carta.getTipo();
			var subtipo = carta.getSubtipo();
			var kilometros = carta.getKilometros();
			map.put("direccion", direccion);
			map.put("tipo", tipo);
			map.put("subtipo", subtipo);
			map.put("kilometros", kilometros);
		}
		return map;
	}
}
