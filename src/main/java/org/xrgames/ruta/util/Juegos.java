package org.xrgames.ruta.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.xrgames.logic.Juego;
import org.xrgames.ruta.dto.JuegoInfo;
import org.xrgames.ruta.entity.Usuario;

/**
 * HashMap para contener todos los juegos registrados.
 */
public class Juegos extends HashMap<String, Juego> {

	private static final long serialVersionUID = 1L;

	/**
	 * Retorna true si un usuario posee un juego registrado en este HashMap.
	 * 
	 * @param owner
	 * @return
	 * @throws Exception
	 */
	public boolean hasOne(Usuario owner) throws Exception {
		return get(owner).isSome();
	}

	/**
	 * Coloca un juego en el HashMap con un id aleatorio.
	 * 
	 * @param juego
	 * @return
	 */
	public Juego put(Juego juego) {
		var key = UUID.randomUUID().toString();
		put(key, juego);
		return juego;
	}

	/**
	 * Retorna un juego por su propietario.
	 * 
	 * @param owner
	 * @return El juego se es encontrado.
	 * @throws Exception
	 */
	public Option<Juego> get(Usuario owner) throws Exception {
		for (Map.Entry<String, Juego> entry : entrySet()) {
			Juego juego = entry.getValue();
			var juegoOwner = juego.getOwner();
			if (juegoOwner == owner) {
				return Option.of(juego);
			}
		}
		return Option.none();
	}

	/**
	 * Retorna información de todos los juegos registrados.
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<JuegoInfo> getAllInfo() throws Exception {
		ArrayList<JuegoInfo> items = new ArrayList<>(size());
		for (Map.Entry<String, Juego> entry : entrySet()) {
			var juego = entry.getValue();
			var info = new JuegoInfo();
			var partida = juego.getPartida();
			var configuracion = juego.getConfig();
			var owner = juego.getOwner();

			info.owner = owner.getUsername();
			info.jugadores = partida.getJugadores().size();
			info.modo = configuracion.getModo();
			info.jugadoresMaximos = configuracion.getJugadores();
			info.iniciado = juego.getIniciado();

			items.add(info);
		}
		return items;
	}
}
