package org.xrgames.logic;

import java.util.LinkedList;

/**
 * Representa una colección que almacena los equipos
 * del juego.
 */
public class EquipoCollection extends LinkedList<Equipo> {
	
	// Límite de equipos admitidos en la colección.
	private int cantidadMaxima = Juego.MAX_EQUIPOS;
	
	// Uso en serialización
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor por defecto que establece a 
	 * Juego.MAX_EQUIPOS la cantida máxima de equipos 
	 * aceptados.
	 */
	public EquipoCollection() {
		cantidadMaxima = Juego.MAX_EQUIPOS;
	}
	
	/**
	 * Constructor que permite establecer manualmente 
	 * la cantidad máxima de equipos aceptados
	 */
	public EquipoCollection(int cantidadMaxima) {
		this.cantidadMaxima = cantidadMaxima;
	}
	
	/**
	 * Agrega un equipo a la coleccion
	 * @param i Será convertido en el nombre del equipo
	 * @return
	 */
	public boolean add(int i) {
		return add(String.valueOf(i));
	}
	
	/**
	 * Obtiene el indice en la coleccion de un equipo
	 * @param i
	 * @return
	 */
	public int indexOf(int i) {
		return indexOf(String.valueOf(i));
	}
	
	/**
	 * Obtiene el indice en la colección de un equipo
	 * por su nombre.
	 * @param nombre
	 * @return
	 */
	public int indexOf(String nombre) {
		int i = 0;
		int index = -1;
		var iterator = this.iterator();
		while(iterator.hasNext()) {
			var item = iterator.next();
			if(item.nombre.equals(nombre)) {
				index = i;
				break;
			}
			++i;
		}
		return index;
	}
	
	/**
	 * Remueve un equipo por su nombre.
	 * @param nombre
	 */
	public void remove(String nombre) {
		
	}
	
	/**
	 * Retorna true si el nombre de un equipo ya existe. 
	 * @param nombre
	 * @return
	 */
	public boolean exists(int nombre) {
		return indexOf(String.valueOf(nombre)) != -1;
	}
	
	/**
	 * Retorna true si el nombre de un equipo ya existe. 
	 * @param nombre
	 * @return
	 */
	public boolean exists(String nombre) {
		return indexOf(nombre) != -1;
	}
	
	/**
	 * Agrega un equipo a la colección.
	 * @param nombre
	 * @return true si el equipo fue agregado. False si se excede la cantidad máxima permitida.
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
