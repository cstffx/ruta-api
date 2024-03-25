package org.xrgames.ruta.services;

import java.text.MessageFormat;

public class Endpoint {

	private String url = "";

	static final String PROTOCOL = "http";
	static final String HOST = "localhost";
	static final int PORT = 8080;
	static final String CONTEXT = "ruta/api";
	static final String PATTERN = "{0}://{1}:{2}/{3}/{4}";

	public enum Route {

		JUGADOR("jugador"), 
		JUGADOR_LOGIN("jugador/login"), 
		JUGADOR_LOGOUT("jugador/logout"),
		JUEGO("juego"), 
		JUEGO_JOIN("juego/join/{0}/{1}"),
		JUEGO_CREATE("juego"), 
		JUEGO_END("juego/end/{0}"), 
		EQUIPO_JOIN("equipo/join/{0}"),
		EQUIPO_CREATE("equipo"), 
		STATUS("status"),
		STATUS_PROTECTED("status/protected"), 
		SERVER_RESET("server/reset");

		public final String path;

		private Route(String path) {
			this.path = Route.removeSlashAtStart(path);
		}

		/**
		 * @param value
		 * @return
		 */
		private static String removeSlashAtStart(String value) {
			if (value.substring(0, 1).equals("/")) {
				value = value.substring(1);
			}
			return value;
		}

		public long countArgs() {
			return path.chars().filter(ch -> ch == '{').count();
		}

		public String toString() {
			return path;
		}
	}

	/**
	 * @param name
	 * @param arguments
	 * @throws Exception
	 */
	public Endpoint(Route name, Object... arguments) throws Exception {
		var argsCount = name.countArgs();
		
		if(arguments.length != argsCount) {
			var url = name.toString();
			var requeridos = String.valueOf(argsCount);
			var recibidos = String.valueOf(arguments.length);
			var message = "La ruta {0} require {1} agumento(s) pero se recibió {2}.";
			message = MessageFormat.format(message, url, requeridos, recibidos);
			throw new Exception(message);
		}
		
		url = MessageFormat.format(name.toString(), arguments);
		url = MessageFormat.format(PATTERN, PROTOCOL, HOST, String.valueOf(PORT), CONTEXT, url);
	}

	/**
	 * @param name
	 * @param arguments
	 * @throws Exception
	 */
	public static String of(Route name, Object... arguments) throws Exception {
		return (new Endpoint(name, arguments)).toString();
	}
	
	public String toString() {
		return url;
	}
}