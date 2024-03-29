package org.xrgames.ruta.entity;

import java.util.HashMap;

import org.xrgames.logic.Carta;

/**
 * Ocurre cuando un jugador roba una carta.
 */
public class PlayerDrawsCardEvent extends Event {

	final Usuario usuario;
	final Carta carta;

	public PlayerDrawsCardEvent(Usuario usuario, Carta carta) {
		super(EventType.PLAYER_DRAWS_CARD);
		this.usuario = usuario;
		this.carta = carta;
	}

	public HashMap<String, Object> toUserMap(Usuario reader) {
		var map = super.toUserMap(reader);
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
