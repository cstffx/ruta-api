package org.xrgames.ruta.entity;

import org.xrgames.logic.Carta;
import org.xrgames.logic.CartaSubtipo;
import org.xrgames.logic.Direccion;
import org.xrgames.logic.Tipo;

/**
 * Ocurre cuando un jugador roba una carta.
 */
public class PlayerDrawsCardEvent extends Event {
	
	public String username;
	public Tipo tipo;
	public CartaSubtipo subtipo;
	public Direccion direccion;
	
	public PlayerDrawsCardEvent(String username, Carta carta) {
		super(EventType.PLAYER_DRAWS_CARD);
		this.username = username;
		
		this.tipo = carta.getTipo();
		this.subtipo= carta.getSubtipo();
		this.direccion = carta.getDireccion();
	}
	
	public String getUsername() {
		return this.username;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public CartaSubtipo getSubtipo() {
		return subtipo;
	}

	public Direccion getDireccion() {
		return direccion;
	}
}
