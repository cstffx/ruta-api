package org.xrgames.ruta.entity;

/**
 * Ocurre cuando un jugador se une a un equipo.
 */
public class PlayerJoinEvent extends Event {
	
	public String username;
	
	public PlayerJoinEvent(String username) {
		super(EventType.PLAYER_JOIN);
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
}
