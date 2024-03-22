package org.xrgames.ruta.services;

import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.logic.Juego;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Servicio especializado en crear y mantener la instancia de juego actual.
 */
@ApplicationScoped
public class JuegoService {
	
	/**
	 * Juego actual.
	 */
	private Juego juego = new Juego();
	
	/**
	 * Crea un juego con una configuracion de partidad.
	 * @param config
	 */
	public void create(ConfiguracionJuego config) {
		juego = new Juego(config);
	}
	
	/**
	 * @return El juego actual.
	 */
	public Juego current() {
		return juego;
	}
}