package org.xrgames.logic;

import java.util.LinkedList;

public class Juego {
	
	public static int MAX_EQUIPOS = 3;
	public final static int MAX_JUGADORES = 6;
	
	public LinkedList<Equipo> equipos = new LinkedList<>();
	
	/**
	 * Agrega un equipo al juego.
	 * @param nombre
	 * @return
	 */
	public boolean addEquipo(String nombre) {
		int size = equipos.size();
		if(size < Juego.MAX_EQUIPOS) {
			equipos.push(new Equipo(size + 1, nombre));
		}
		return false;
	}
	
	/**
	 * Retorna un equipo por su id
	 * @param nombre
	 * @return
	 */
	public Equipo getEquipo(int id) {
		for(var equipo: equipos) {
			if(equipo.id == id) {
				return equipo;
			}
		}
		return null;
	}
	
	/**
	 * Retorna todos los equipos del juego.
	 * @return
	 */
	public LinkedList<Equipo> getEquipos() {
		return equipos;
	}

	/**
	 * Elimina un equipo por su id.
	 * @param id
	 */
	public boolean deleteEquipo(int id) {
		for(var equipo: equipos) {
			if(id == equipo.id) {
				equipos.remove(equipo);
				return true;
			}
		}
		return false;
	}
}