package org.xrgames.ruta.dto;

/**
 * Representa información pública de un equipo.
 */
public class EquipoInfo {

	public int id;
	public String nombre;
	public int cantidadJugadores;

	public EquipoInfo() {
	}

	public EquipoInfo(int id, String nombre, int cantidadJugadores) {
		this.id = id;
		this.nombre = nombre;
		this.cantidadJugadores = cantidadJugadores;
	}
}
