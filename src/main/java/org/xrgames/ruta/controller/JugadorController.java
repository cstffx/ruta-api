package org.xrgames.ruta.controller;

import java.util.LinkedList;

import org.xrgames.ruta.services.JuegoService;
import org.xrgames.ruta.services.LoginFormData;
import org.xrgames.ruta.services.Security;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/jugador")
public class JugadorController {
	
	@Inject 
	public JuegoService juegoService;
	
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(LoginFormData login) throws Exception {
		if(login.username.isEmpty()) {
			return Response.notAcceptable(null).build();
		}
		var token = sec.login(login.username);
		return Result.json(token);
	}
	
	@POST
	@Path("/logout")
	public Response logout() throws Exception {
		if(sec.logout()) {
			return Response.ok().build();
		}
		return Response.serverError().build();
	}
	
	/**
	 * Une al jugador a un juego.
	 * @param id
	 * @return
	 */
	@POST
	@Path("/join/{id}")
	public Response join(@QueryParam("id") int id) {
		if (sec.isAnonimous()) {
			return Result.forbiden();
		}
		
		var juego = juegoService.current(); 
		var equipos = juego.getEquipos();
		var equipo = equipos.get(String.valueOf(id));
		
		if(null == equipo) {
			return Result.notFound();
		}
		
		return null;
	}
}