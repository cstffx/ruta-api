package org.xrgames.logic;

import org.xrgames.ruta.util.JugadoresMap;

/**
 * Representa un equipo del juego.
 */
public class Equipo {

	public int id;

	public String nombre;

	public JugadoresMap jugadores = new JugadoresMap();

	public Equipo() {
		id = 0;
		nombre = "";
	}

	/**
	 * @param id
	 * @param nombre
	 */
	public Equipo(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	/**
	 * Retorna los jugadores que pertenecen al equipo.
	 * 
	 * @return
	 */
	public JugadoresMap getJugadores() {
		return jugadores;
	}

	/**
	 * @return True si el equipo tiene espacio para uno o dos jugadores.
	 */
	public boolean haveSpace() {
		return Juego.MAX_JUGADOR_POR_EQUIPO > jugadores.size();
	}

	/**
	 * @return True si el equipo no contiene jugadores.
	 */
	public boolean isEmpty() {
		return jugadores.size() == 0;
	}

	/**
	 * @return True si el equipo está lleno
	 */
	public boolean isFull() {
		return jugadores.size() == Juego.MAX_JUGADOR_POR_EQUIPO;
	}

	/**
	 * Agrega un jugador al equipo.
	 * 
	 * @param jugador
	 */
	public void addJugador(Jugador jugador) {
		jugadores.put(jugador.getUsuario().id, jugador);
	}
}