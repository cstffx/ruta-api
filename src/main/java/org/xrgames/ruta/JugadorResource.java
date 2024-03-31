package org.xrgames.ruta;

import java.util.HashMap;

import org.xrgames.ruta.services.JuegoService;
import org.xrgames.ruta.services.LoginForm;
import org.xrgames.ruta.services.UserRegistry;
import org.xrgames.ruta.services.session.UserSession;
import org.xrgames.ruta.util.ResponseError;
import org.xrgames.ruta.util.ResponseSuccess;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/jugador")
public class JugadorResource {

	@Inject
	public UserSession session;

	@Inject
	public JuegoService juegoService;

	@Inject
	UserRegistry registry;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return ResponseSuccess.json(registry.getAll());
	}

	@GET
	@Path("/status")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response status() throws Exception {
		var token = session.getToken();
		if (token.isEmpty()) {
			var result = new HashMap<String, Boolean>();
			result.put("anonimous", true);
			return Response.status(Response.Status.OK)
					.entity(result)
					.build();			
		}
		var result = new HashMap<String, String>();
		result.put("username", token.username);
		return Response.ok(result).build();
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(@Valid LoginForm login) throws Exception {
		var result = session.login(login.username);
		if (result.isOk()) {
			return Response.ok(result.unwrap()).build();
		}
		return Response.status(Response.Status.FOUND).build();
	}

	@POST
	@Path("/logout")
	public Response logout() throws Exception {
		session.logout();
		return Response.ok().build();
	}
}