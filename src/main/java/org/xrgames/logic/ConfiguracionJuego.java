package org.xrgames.logic;

/**
 * Representa los parámetros de configuración del juego.
 */
public class ConfiguracionJuego {

	public ModoJuego modo;

	public int jugadores;

	/**
	 * Crea una configuracion por defecto de juego individual con el maximo de
	 * jugadores permitidos.
	 */
	public ConfiguracionJuego() {
		modo = ModoJuego.Individual;
		jugadores = Juego.MAX_JUGADORES;
	}

	/**
	 * Construtor para un modo y cantidad de jugadores.
	 * 
	 * @param modo
	 * @param jugadores
	 */
	public ConfiguracionJuego(ModoJuego modo, int jugadores) {
		this.modo = modo;
		this.jugadores = jugadores;
	}

	/**
	 * @return El modo de juego
	 */
	public ModoJuego getModo() {
		return modo;
	}

	/**
	 * @return La cantidad máxima de jugadores.
	 */
	public int getJugadores() {
		return jugadores;
	}

	/**
	 * Calcula y retorna la cantidad posible de equipos atendiendiendo a la
	 * configuración actual del juego.
	 * 
	 * @return
	 */
	public int getCantidadEquiposPosibles() {
		if (jugadores == 2 || jugadores == 3) {
			return jugadores;
		}

		if (modo == ModoJuego.Individual) {
			return jugadores;
		}

		// caso de 4 y 6 jugadores por equipo.
		return jugadores / Juego.MAX_JUGADOR_POR_EQUIPO;
	}

	/**
	 * @return True si el modo de juego es individual.
	 */
	public boolean isIndividual() {
		return modo == ModoJuego.Individual;
	}
}
