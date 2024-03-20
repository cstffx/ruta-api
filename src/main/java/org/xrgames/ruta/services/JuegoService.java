package org.xrgames.ruta.services;

import org.xrgames.logic.Juego;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Application;

@ApplicationScoped
public class JuegoService {
	
	private Juego juego = new Juego();
	
	public Juego current() {
		return juego;
	}
}