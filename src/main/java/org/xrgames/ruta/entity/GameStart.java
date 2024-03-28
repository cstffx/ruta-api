package org.xrgames.ruta.entity;

/**
 * Ocurre cuando inicia un juego.
 */
public class GameStart extends Event {
	
	public String username;
	
	public GameStart() {
		super(EventType.GAME_START);
	}
}
