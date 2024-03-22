package org.xrgames.ruta.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class Result {
	
	public static Response status(Response.Status status) {
		return Response.status(status).build();
	}
	
	public static Response forbiden() {
		return status(Response.Status.FORBIDDEN);
	}
	
	public static Response json(Object entity) {
		var status = Response.Status.OK;
		return Response.status(status).entity(entity).build();
	}

	public static Response ok() {
		return status(Response.Status.OK);
	}
	
	public static Response notAcceptable() {
		return status(Response.Status.NOT_ACCEPTABLE);
	}

	public static Response notFound() {
		return status(Response.Status.NOT_FOUND);
	}
}