package org.xrgames.ruta.services;

import jakarta.ws.rs.core.Response;

public class Debug {
	
	public static void debug(Response response) {
		debug(response.readEntity(String.class));
		System.exit(1);
	}
	
	public static void debug(Object ...arguments) {
		StringBuilder builder = new StringBuilder();
		for(Object arg: arguments) {
			builder.append(arg.toString());
		}
		System.err.println(builder.toString());
	}
}
