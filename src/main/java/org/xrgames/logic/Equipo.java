package org.xrgames.logic;

import org.xrgames.ruta.util.Jugadores;

/**
 * Representa un equipo del juego.
 */
public class Equipo {
	
	public int id;
	
	public String nombre;
	
	public Jugadores jugadores = new Jugadores();
	
	public Equipo() {		
		id = 0;
		nombre = "";
	}
	
	/**
	 * @param id
	 * @param nombre
	 */
	public Equipo(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	/**
	 * Retorna los jugadores que pertenecen al equipo.
	 * @return
	 */
	public Jugadores getJugadores() {
		return jugadores;
	}
	
	/**
	 * @return True si el equipo no contiene jugadores.
	 */
	public boolean isEmpty() {
		return jugadores.size() == 0;
	}
}