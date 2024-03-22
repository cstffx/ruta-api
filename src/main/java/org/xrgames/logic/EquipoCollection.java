package org.xrgames.logic;

import java.util.LinkedList;

import org.xrgames.logic.Equipo;

public class EquipoCollection extends LinkedList<Equipo> {
	
	private int cantidadMaxima = Juego.MAX_EQUIPOS;
	private static final long serialVersionUID = 1L;
	
	public EquipoCollection() {
		
	}
	
	public EquipoCollection(int cantidadMaxima) {
		this.cantidadMaxima = cantidadMaxima;
	}
	
	/**
	 * Agrega un equipo a la colección.
	 * @param nombre
	 * @return
	 */
	public boolean add(String nombre) {
		int size = this.size();
		if(size < cantidadMaxima) {
			this.push(new Equipo(size + 1, nombre));
			return true;
		}
		
		return false;
	}
}
