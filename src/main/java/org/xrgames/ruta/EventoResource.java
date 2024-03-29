package org.xrgames.ruta;

import org.xrgames.ruta.services.JuegoService;
import org.xrgames.ruta.services.session.UserSession;
import org.xrgames.ruta.util.ResponseBuilder;
import org.xrgames.ruta.util.ResponseError;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/evento")
public class EventoResource {

	@Inject
	public UserSession session;

	@Inject
	public JuegoService juegoService;

	/**
	 * Retorna una lista de los eventos de un juego.
	 * @return
	 * @throws Exception 
	 */
	@GET
	@Path("/{juegoId}/{eventoId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@PathParam("juegoId") String juegoId, @PathParam("eventoId") int eventoId) throws Exception {
		if(session.isAnonimous()){
			return ResponseError.forbiden();
		}
		
		var user = session.getUser().unwrap();
		var result = juegoService.getEventosForGame(juegoId, eventoId, user);
	
		return ResponseBuilder.of(result);
	}
}