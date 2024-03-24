package org.xrgames.ruta.util;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Construye respuestas de satisfactorias.
 */
public class ResponseSuccess {
	public static Response json(Object entity) {
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();
	}
}
