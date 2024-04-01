package org.xrgames.ruta.dto;

import org.xrgames.logic.ModoJuego;

/**
 * Representa información pública de un juego.
 */
public class JuegoInfo {

	public String id;
	public String owner;
	public boolean iniciado;
	public int jugadores;
	public ModoJuego modo;
	public int jugadoresMaximos;

	public JuegoInfo() {
	}
}
