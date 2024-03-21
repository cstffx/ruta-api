package org.xrgames.ruta;

import java.util.LinkedList;

import org.xrgames.ruta.services.LoginFormData;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/jugador")
public class JugadorController {
	
	@Inject
	public Security sec;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Result.json(new LinkedList<String>());
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(LoginFormData login) throws Exception {
		if(login.username.isEmpty()) {
			return Response.notAcceptable(null).build();
		}
		var token = sec.login(login.username);
		return Result.json(token);
	}
	
	@POST
	@Path("/logout")
	public Response logout(LoginFormData login) throws Exception {
		if(sec.logout()) {
			return Response.ok().build();
		}
		return Response.serverError().build();
	}
}