package org.xrgames.ruta.entity;

/**
 * Ocurre cuando un jugador abandona una partida.
 */
public class PlayerAbandon extends Event {
	
	public String username;
	
	public PlayerAbandon(String username) {
		super(EventType.PLAYER_ABANDON);
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
}
