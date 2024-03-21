package org.xrgames.ruta.services;

import jakarta.ws.rs.core.Response;

public class ResponseUtil {
	static boolean isOk(Response response) {
		return Response.Status.OK.getStatusCode() == response.getStatus();
	}
}
