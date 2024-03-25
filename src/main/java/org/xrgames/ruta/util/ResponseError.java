package org.xrgames.ruta.util;

import org.xrgames.ruta.services.AccessDeniedException;
import org.xrgames.ruta.services.AlreadyJoined;
import org.xrgames.ruta.services.FullGameObjectException;
import org.xrgames.ruta.services.NotFoundException;

import jakarta.ws.rs.core.Response;

/**
 * Construye respuestas de error.
 */
public class ResponseError {

	/**
	 * Genera una respuesta a partir de una excepcion.
	 * @param exception
	 * @return
	 */
	public static Response status(Exception exception) {
		if(exception instanceof AlreadyJoined) {
			return found();
		}
		
		if (exception instanceof NotFoundException) {
			return notFound();
		}

		if (exception instanceof AccessDeniedException) {
			return unauthorized();
		}
		
		if (exception instanceof FullGameObjectException) {
			return Response.status(Response.Status.PRECONDITION_FAILED.getStatusCode(), exception.getMessage()).build();
		}

		return error();
	}

	/**
	 * Acorta la creación de una respuesta a partir de un Response.Status.
	 * Provisto con conveniencia.
	 * @param status
	 * @return
	 */
	public static Response status(Response.Status status) {
		return Response.status(status).build();
	}


	public static Response forbiden() {
		return status(Response.Status.FORBIDDEN);
	}

	public static Response notAcceptable() {
		return status(Response.Status.NOT_ACCEPTABLE);
	}

	public static Response notFound() {
		return status(Response.Status.NOT_FOUND);
	}

	public static Response found() {
		return status(Response.Status.FOUND);
	}
	
	public static Response unauthorized() {
		return status(Response.Status.UNAUTHORIZED);
	}
	
	public static Response error() {
		return status(Response.Status.INTERNAL_SERVER_ERROR);
	}
}
