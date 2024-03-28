package org.xrgames.ruta;

import org.xrgames.ruta.services.JuegoService;
import org.xrgames.ruta.services.session.UserSession;
import org.xrgames.ruta.util.ResponseSuccess;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/evento")
public class EventoResource {

	@Inject
	public UserSession session;

	@Inject
	public JuegoService service;

	/**
	 * Retorna una lista de los eventos de un juego.
	 * @return
	 */
	@GET
	@Path("/{juegoId}/{timestamp}")
	public Response getAll(@PathParam("juegoId") String juegoId, @PathParam("timestamp") long timestap) {
		return Response.ok().build();	
	}
}