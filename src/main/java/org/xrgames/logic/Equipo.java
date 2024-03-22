package org.xrgames.logic;

public class Equipo {
	public int id;
	public String nombre;
	
	public Equipo() {
		nombre = "";
	}
	
	public Equipo(int i) {
		nombre = String.valueOf(i);
	}
	
	public Equipo(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
}