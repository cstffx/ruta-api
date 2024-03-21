package org.xrgames.ruta;

import org.xrgames.ruta.services.JuegoService;
import org.xrgames.ruta.services.LoginFormData;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/juego")
public class JuegoController {
	
	@Inject 
	Security sec;

	@Inject 
	JuegoService juego;
	
	/**
	 * Crea un nuevo juego.
	 * @param login
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(JuegoCreateFormData form) {
		juego.create();
		return Response.ok().build();
	}
}