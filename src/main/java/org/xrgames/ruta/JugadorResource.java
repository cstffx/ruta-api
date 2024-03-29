package org.xrgames.ruta;

import org.xrgames.ruta.services.JuegoService;
import org.xrgames.ruta.services.LoginForm;
import org.xrgames.ruta.services.UserRegistry;
import org.xrgames.ruta.services.session.UserSession;
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

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(@Valid LoginForm login) throws Exception {
		var result = session.login(login.username);
		if (result.isOk()) {
			return Response.ok().build();
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