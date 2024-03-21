package org.xrgames.ruta.services;

import java.text.MessageFormat;

public class Endpoint {
	static final String ENDPOINT_LOGIN = "jugador/login";
	static final String ENDPOINT_LOGOUT = "jugador/logout";
	static final String ENDPOINT_JUEGO_CREATE = "juego/create";
	
	static final String PROTOCOL = "http";
	static final String HOST = "localhost";
	static final int PORT = 8080;
	static final String CONTEXT = "ruta/api";
	static final String PATTERN = "{0}://{1}:{2}/{3}/{4}";

	private String url = "";
	private String base = "";
	
	public Endpoint(String base) {
		url = Endpoint.build(base);
		this.base = base;
	}
	
	public static String build(String base) {
		if(base.substring(0, 1).equals("/")) {
			base = base.substring(1);
		}
		return MessageFormat.format(PATTERN, PROTOCOL, HOST, String.valueOf(PORT), CONTEXT, base);
	}
	
	public String getBase() {
		return base;
	}
	
	public String toString() {
		return url;
	}
}