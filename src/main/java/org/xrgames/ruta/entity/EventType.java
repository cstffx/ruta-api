package org.xrgames.ruta.entity;

/**
 * Lista los tipos de eventos del juego.
 */
public enum EventType {
	// El juego comienza
	GAME_START,
	// El usuario se une a un equipo
	PLAYER_JOIN,
	// El usuario abandona un equipo (reservado)
	PLAYER_ABANDON,
	// Un equipo gana el juego
	TEAM_WINS_GAME,
	// Un equipo gana una ronda
	TEAM_WINS_ROUND,
	// Jugador roba carta
	PLAYER_DRAWS_CARD,
	// Jugador juega carta
	PLAYER_PLAYS_CARD
}
