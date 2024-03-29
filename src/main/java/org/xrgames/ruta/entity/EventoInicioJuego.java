package org.xrgames.ruta.entity;

/**
 * Ocurre cuando inicia un juego.
 */
public class EventoInicioJuego extends Evento {
	public EventoInicioJuego() {
		super(TipoEvento.INICIO_JUEGO);
	}
}
