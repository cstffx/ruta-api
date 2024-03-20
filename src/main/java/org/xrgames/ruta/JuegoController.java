package org.xrgames.ruta;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;

@Path("/juego")
public class JuegoController {
	
	@Inject 
	Security sec;

	@Context
	public HttpServletRequest request;

	/**
	 * @POST() @Path("/logout") public Response logout(@Context HttpServletRequest
	 *         request) { HttpSession session = request.getSession(); Object id =
	 *         session.getAttribute(Juego.SESSION_ID_ATTRIBUTE); if(id != null) { //
	 *         TODO: Leave Juego.
	 *         session.removeAttribute(Juego.SESSION_ID_ATTRIBUTE); } return
	 *         Response.status(Response.Status.OK).build(); }
	 */
}