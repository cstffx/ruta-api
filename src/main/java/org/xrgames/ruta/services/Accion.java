package org.xrgames.ruta.services;

import org.xrgames.logic.Jugador;

public class Accion {
	
	public Jugador jugador;
	public TipoAccion tipo;
	public Long time = 0L; 
	
	public Accion() {
		
	}
	
	public Accion(Jugador jugador, TipoAccion tipo) {
		this.jugador = jugador;
		this.tipo = tipo;
	}
}