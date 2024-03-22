package org.xrgames.ruta.controller;

import org.xrgames.ruta.services.Security;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/status")
public class StatusController {
	
	@Inject 
	Security sec;
	
	@Context()
	public HttpServletRequest request;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatus() {
		if(sec.isAnonimous()) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		
		return Response.ok("online").build();
	}
}