package org.xrgames.ruta;

import org.xrgames.ruta.services.JuegoService;
import org.xrgames.ruta.services.Security;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
		if (sec.isAnonimous()) {
			return Result.forbiden();
		}
		juego.create(form);
		return Response.ok().build();
	}
	
	
}