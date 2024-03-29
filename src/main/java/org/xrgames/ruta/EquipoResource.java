package org.xrgames.ruta;

import org.xrgames.ruta.services.JuegoService;
import org.xrgames.ruta.services.session.UserSession;
import org.xrgames.ruta.util.ResponseSuccess;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
	@Path("/{juegoId}")
	public Response getAll(@PathParam("juegoId") String juegoId) {
		var juego = service.getAllEquipos(juegoId);
		return ResponseSuccess.json(juego);
	}
}