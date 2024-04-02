package org.xrgames.ruta;

import java.util.HashMap;

import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.ruta.services.JuegoService;
import org.xrgames.ruta.services.UserRegistry;
import org.xrgames.ruta.services.session.UserSession;
import org.xrgames.ruta.util.ResponseBuilder;
import org.xrgames.ruta.util.ResponseError;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/juego")
public class JuegoResource {

	@Inject
	UserSession session;

	@Inject
	JuegoService juegoService;

	@Inject
	UserRegistry registry;
	
	/**
	 * Retorna una lista de los juegos disponibles.
	 * 
	 * @return
	 * @throws Exception
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAll() throws Exception {
		if (session.isAnonimous()) {
			return ResponseError.forbiden();
		}
		var items = juegoService.getAll();
		return Response.ok(items).build();
	}

	/**
	 * Crea un nuevo juego.
	 * 
	 * @param login
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(ConfiguracionJuego form) throws Exception {
		if (session.isAnonimous()) {
			return ResponseError.forbiden();
		}

		var owner = registry.currentUser().unwrap();
		var gameId = juegoService.create(owner, form);

		if (gameId.isNone()) {
			return ResponseError.found();
		}

		var result = new HashMap<String, String>();
		result.put("gameId", gameId.unwrap());
		
		return Response.ok(result).build();
	}

	/**
	 * Termina un juego en curso por su propietario.
	 * 
	 * @param id Id del juego
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/end/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response end(@PathParam("id") String id) throws Exception {
		if (session.isAnonimous()) {
			return ResponseError.forbiden();
		}

		var performer = registry.currentUser().unwrap();
		var result = juegoService.end(id, performer);

		return ResponseBuilder.of(result);
	}

	/**
	 * Permite al usuario actual unirse a un juego.
	 * 
	 * @param id Id del juego.
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/join/{juego}/{equipo}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response join(@PathParam("juego") String juego, @PathParam("equipo") int equipo) throws Exception {
		if (session.isAnonimous()) {
			return ResponseError.forbiden();
		}

		var user = registry.currentUser().unwrap();
		var result = juegoService.unirse(juego, equipo, user);

		return ResponseBuilder.of(result);
	}

	/**
	 * Permite al propietario de un juego iniciarlo.
	 * 
	 * @param id Id del juego.
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/start/{juego}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response start(@PathParam("juego") String juego) throws Exception {
		if (session.isAnonimous()) {
			return ResponseError.forbiden();
		}

		var user = registry.currentUser().unwrap();
		var result = juegoService.iniciar(juego, user);

		return ResponseBuilder.of(result);
	}
	


	/**
	 * Realiza una jugada en la partida actual.
	 * 
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("play")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response play() throws Exception {
		if (session.isAnonimous()) {
			return ResponseError.forbiden();
		}

		return Response.ok(true).build();
	}
}