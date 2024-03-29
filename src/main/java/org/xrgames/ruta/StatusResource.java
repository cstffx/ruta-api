package org.xrgames.ruta;

import org.xrgames.ruta.services.session.UserSession;
import org.xrgames.ruta.util.ResponseError;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/status")
public class StatusResource {
	@Inject
	UserSession session;

	@GET
	@Path("/protected")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProtectedStatus() {
		if (session.isAnonimous()) {
			return ResponseError.forbiden();
		}
		return Response.ok("online").build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatusGet() {
		return Response.ok("online").build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatusPost() {
		return Response.ok("online").build();
	}
}