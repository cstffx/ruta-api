package org.xrgames.ruta.services;

import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.logic.Juego;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JuegoService {
	
	private Juego juego = new Juego();
	
	public void create(ConfiguracionJuego config) {
		juego = new Juego(config);
	}
	
	public Juego current() {
		return juego;
	}
}