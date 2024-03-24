package org.xrgames.ruta;

import org.xrgames.ruta.services.JuegoService;
import org.xrgames.ruta.services.UserRegistry;
import org.xrgames.ruta.services.session.UserSession;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/server")
public class ServerResource {

	@Inject
	public UserSession session;

	@Inject
	public JuegoService juegoService;
	
	@Inject
	public UserRegistry userRegistry;

	/**
	 * Restablece el estado del servidor.
	 * @return
	 */
	@POST
	@Path("reset")
	public Response reset() {
		juegoService.clear();
		userRegistry.clear();
		return Response.ok().build();
	}
}