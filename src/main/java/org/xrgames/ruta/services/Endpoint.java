package org.xrgames.ruta.services;

import java.text.MessageFormat;

public class Endpoint {
	
	static public final String LOGIN = "jugador/login";
	static public final String LOGOUT = "jugador/logout";
	static public final String JUEGO_CREATE = "juego/create";
	static public final String JUEGO_JOIN = "jugador/join";
	static public final String EQUIPO_CREATE = "equipo/create";
	
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
	
	public static String build(String ...arguments) {
		var builder = new StringBuilder();
		for(String arg: arguments) {
			arg = removeSlashAtStart(arg);
			builder.append(arg);
		}
		var base = builder.toString();
		return build(base);
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
	
	/**
	 * @param value
	 * @return
	 */
	private static String removeSlashAtStart(String value) {
		if(value.substring(0, 1).equals("/")) {
			value = value.substring(1);
		}
		return value;
	}
}