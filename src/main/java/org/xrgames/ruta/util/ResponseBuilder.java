package org.xrgames.ruta.util;

import jakarta.ws.rs.core.Response;

/**
 * Construye respuestas. 
 * Provisto por conveniencia.
 */
public class ResponseBuilder {	
	public static <T> Response of(Result<T, Exception> result) throws Exception {
		if(result.isOk()) {
			return ResponseSuccess.json(result.unwrap());
		}
		return ResponseError.status(result.getError());
	}
}
