package org.xrgames.ruta.util;

import java.util.Random;

public class RandomPlayers {
	
	public static int[] jugadores = { 2, 3, 4, 6 };
	static Random rand = new Random();

	public static int next() {
		return jugadores[rand.nextInt(0, 4)];
	}
}
