package org.xrgames.ruta.entity;

/**
 * Ocurre cuando inicia un juego.
 */
public class GameStartEvent extends Event {
	public GameStartEvent() {
		super(EventType.GAME_START);
	}
}
