package org.xrgames.ruta.entity;

/**
 * Lista los tipos de eventos del juego.
 */
public enum TipoEvento {
	JUGADOR_SE_UNE,
	// El usuario abandona un equipo (reservado)
	JUGADOR_ABANDONA,
	// Inicia una nueva partida
	// El juego comienza
	INICIO_JUEGO,
	// El usuario se une a un equipo
	INICIO_PARTIDA,
	// Notifica la repartición inicial de cartas
	// al comienzo del juego.
	MANO_INICIALIZADA,
	// Jugador juega carta
	JUGADOR_JUEGA_CARTA,
	// Un equipo gana el juego
	EQUIPO_GANA_JUEGO,
	// Un equipo gana una ronda
	EQUIPO_GANA_RONDA,
	// Jugador roba carta
	JUGADOR_ROBA_CARTA
}
