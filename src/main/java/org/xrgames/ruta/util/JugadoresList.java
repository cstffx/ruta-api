package org.xrgames.ruta.util;

import java.util.LinkedList;

import org.xrgames.logic.Jugador;
import org.xrgames.ruta.entity.Usuario;

/**
 * HashMap de jugadores.
 */
public class JugadoresList extends LinkedList<Jugador> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Encuentra un jugador por su usuario asociado.
	 * @param usuario
	 * @return
	 */
	public Option<Jugador> buscarPorUsuario(Usuario usuario) {
		for(Jugador jugador: this) {
			if(usuario == jugador.getUsuario()) {
				return Option.of(jugador);
			}
		}
		return Option.none();
	}
}
