package org.xrgames.ruta;

import org.xrgames.ruta.form.EquipoForm;
import org.xrgames.ruta.services.JuegoService;
import org.xrgames.ruta.services.session.UserSession;
import org.xrgames.ruta.util.ResponseSuccess;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/equipo")
public class EquipoResource {

	@Inject
	public UserSession session;

	@Inject
	public JuegoService service;

	/**
	 * Retorna una lista de todos los equipos disponibles.
	 * 
	 * @return
	 */
	@GET
	public Response getAll() {
		return ResponseSuccess.json(null);
	}

	/**
	 * Crea un nuevo equipo
	 * 
	 * @param nombre
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@Valid EquipoForm form) throws Exception {
		/**
		if (session.isAnonimous()) {
			return ResponseError.forbiden();
		}

		var juego = service.current();
		var equipos = juego.getEquipos();

		var result = equipos.put(form.nombre);
		if (result.isOk()) {
			var id = result.unwrap();
			return ResponseSuccess.json(new EquipoCreateResponse(id));
		}

		var error = result.getError();
		if (error instanceof AlreadyExistsException) {
			return Response.status(Response.Status.FOUND).build();
		}

		return Response.status(Response.Status.REQUEST_ENTITY_TOO_LARGE).build();
		*/
		throw new Exception("No implemented");
	}

	@DELETE
	public Response delete(@PathParam("id") int id) throws Exception {
		/*
		if (session.isAnonimous()) {
			return ResponseError.forbiden();
		}

		var juego = service.current();
		var equipos = juego.getEquipos();
		var equipo = equipos.get(String.valueOf(id));

		if (null == equipo) {
			return ResponseError.notFound();
		}

		equipos.remove(equipo);

		return Response.ok().build();
		*/
		throw new Exception("No implemented");
	}
}