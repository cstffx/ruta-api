package org.xrgames.ruta.services;

public class Debug {
	public static void debug(Object ...arguments) {
		StringBuilder builder = new StringBuilder();
		for(Object arg: arguments) {
			builder.append(arg.toString());
		}
		System.err.println(builder.toString());
	}
}
