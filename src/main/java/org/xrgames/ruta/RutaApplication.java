package org.xrgames.ruta;

import org.xrgames.logic.Juego;

import jakarta.ws.rs.ApplicationPath;

import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class RutaApplication extends Application {
	private static Juego juego = new Juego();
	
	public static Juego getJuego() {
		return juego;
	}
}