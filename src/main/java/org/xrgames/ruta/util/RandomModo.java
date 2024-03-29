package org.xrgames.ruta.util;

import java.util.Random;

import org.xrgames.logic.ModoJuego;

public class RandomModo {

	static ModoJuego[] modos = { ModoJuego.Equipo, ModoJuego.Individual };
	static Random rand = new Random();

	public static ModoJuego next() {
		return modos[rand.nextInt(0, 2)];
	}
}
