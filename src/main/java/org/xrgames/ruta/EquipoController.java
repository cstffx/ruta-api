package org.xrgames.ruta;

import org.xrgames.ruta.services.JuegoService;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

@Path("/equipo")
public class EquipoController {
	
	@Inject
	private JuegoService service;

	@Inject
	private Security sec;
	
	@Context()
	public HttpServletRequest request;

	@GET
	public Response getAll() {
		if (sec.isAnonimous()) {
			return Result.forbiden();
		}
		var juego = service.current(); 
		var entity = juego.getEquipos();
		return Result.json(entity);
	}

	@POST
	public Response create(String nombre) {
		if (sec.isAnonimous()) {
			return Result.forbiden();
		}
		
		var juego = service.current(); 
		var equipos = juego.getEquipos();
		
		var result = equipos.add(nombre);
		if (result) {
			return Result.ok();
		}
		return Result.notAcceptable();
	}
	
	@POST
	@Path("/join/{id}")
	public Response join(@QueryParam("id") int id) {
		if (sec.isAnonimous()) {
			return Result.forbiden();
		}
		
		var juego = service.current(); 
		var equipos = juego.getEquipos();
		var equipo = equipos.get(String.valueOf(id));
		
		if(null == equipo) {
			return Result.notFound();
		}
		
		return null;
	}

	@DELETE
	public Response delete(@QueryParam("id") int id) {
		if (sec.isAnonimous()) {
			return Result.forbiden();
		}
		
		var juego = service.current(); 
		var equipos = juego.getEquipos();
		var equipo = equipos.get(String.valueOf(id));
		
		if(null == equipo) {
			return Result.notFound();
		}
		
		equipos.remove(equipo); 
		
		return Result.ok();
	}
}