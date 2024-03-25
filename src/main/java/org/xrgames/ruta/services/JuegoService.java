package org.xrgames.ruta.services;

import java.util.ArrayList;
import java.util.UUID;

import org.xrgames.logic.ConfiguracionJuego;
import org.xrgames.logic.Juego;
import org.xrgames.logic.Jugador;
import org.xrgames.logic.ModoJuego;
import org.xrgames.ruta.dto.JuegoInfo;
import org.xrgames.ruta.entity.Usuario;
import org.xrgames.ruta.util.Juegos;
import org.xrgames.ruta.util.Option;
import org.xrgames.ruta.util.Result;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Servicio especializado en crear y mantener los juegos activos.
 */
@ApplicationScoped
public class JuegoService {
	
	/**
	 * HashMap de juegos registrados.
	 */
	private Juegos juegos = new Juegos();
	
	/**
	 * Encuentra un juego por su propietario.
	 * @param owner
	 * @return
	 * @throws Exception
	 */
	public Option<Juego> findOneByOwner(Usuario owner) throws Exception{
		return juegos.get(owner);
	}
	
	/**
	 * Crea un juego con una configuracion de partida.
	 * @param config
	 * @throws Exception 
	 */
	public Option<String> create(Usuario owner, ConfiguracionJuego config) throws Exception {
		var juego = new Juego(owner, config);
		var key = UUID.randomUUID().toString();
		
		if(juegos.hasOne(owner)) {
			return Option.none();
		}

		juegos.put(key, juego);
		
		return Option.of(key);
	}
	
	/**
	 * Termina un juego en curso.
	 * @param id Id del juego en curso.
	 * @param performer Usuario que intenta realizar la acción.
	 * @return
	 * @throws Exception 
	 */
	public Result<Boolean, Exception> end(String id, Usuario performer) throws Exception{
		var juego = juegos.get(id);
		if(null == juego) {
			return Result.of(new NotFoundException());
		}
		
		if(juego.getOwner() != performer) {
			return Result.of(new AccessDeniedException());
		}
		
		juegos.remove(id);

		return Result.of(true);
	}

	/**
	 * Retorna información de todos los juegos registrados.
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<JuegoInfo> getAll() throws Exception {
		return juegos.getAllInfo();
	}
	
	/**
	 * Elimina todos los juegos activos.
	 */
	public void clear() {
		juegos.clear();
	}

	/**
	 * Une al usuario a un juego.
	 * @param juegoId Id del juego
	 * @param user Usuario
	 * @return
	 * @throws Exception 
	 */
	public Result<String, Exception> join(String juegoId, int equipoId, Usuario usuario) throws Exception {
		
		if(usuario.getJuego().isSome()) {
			return Result.of(new AlreadyJoined());
		}
		
		var juego = juegos.get(juegoId);
		if(null == juego) {
			return Result.of(new NotFoundException());
		}
		
		var jugador = new Jugador(usuario);
		var result = juego.joinJugador(equipoId, jugador);
		
		if(result.isOk()) {
			usuario.setJuego(Option.of(juego));
		}
		
		return Result.of(juegoId);
	}
}