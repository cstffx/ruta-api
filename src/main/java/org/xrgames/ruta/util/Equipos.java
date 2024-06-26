package org.xrgames.ruta.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.xrgames.logic.Equipo;
import org.xrgames.logic.Juego;
import org.xrgames.logic.Jugador;
import org.xrgames.logic.ModoJuego;
import org.xrgames.ruta.response.EquipoDetalle;
import org.xrgames.ruta.services.AlreadyExistsException;
import org.xrgames.ruta.services.MaxTeamCountReached;

/**
 * Map que almacena los equipos del juego.
 */
public class Equipos extends HashMap<Integer, Equipo> {

	// Límite de equipos admitidos en la colección.
	private int cantidadMaxima = Juego.MAX_EQUIPOS;

	// Uso en serialización
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto que establece a Juego.MAX_EQUIPOS la cantida máxima
	 * de equipos aceptados.
	 */
	public Equipos() {
		cantidadMaxima = Juego.MAX_EQUIPOS;
	}

	/**
	 * Retorna un equipo por su nombre.
	 * 
	 * @param nombre
	 * @return
	 */
	public Option<Equipo> get(String nombre) {
		for (Map.Entry<Integer, Equipo> entry : entrySet()) {
			var equipo = entry.getValue();
			if (equipo.nombre.equals(nombre)) {
				return Option.of(equipo);
			}
		}
		return Option.none();
	}

	/**
	 * Retorna un equipo por su id.
	 * 
	 * @param id
	 * @return
	 */
	public Option<Equipo> get(int id) {
		var equipo = super.get(id);
		if (null == equipo) {
			return Option.none();
		}
		return Option.of(equipo);
	}

	/**
	 * Constructor que permite establecer manualmente la cantidad máxima de equipos
	 * aceptados
	 */
	public Equipos(int cantidadMaxima) {
		this.cantidadMaxima = cantidadMaxima;
	}

	/**
	 * Retorna true si un equipo existe.
	 * 
	 * @param nombre
	 * @return
	 */
	public boolean exists(String nombre) {
		var equipo = get(nombre);
		return equipo.isSome();
	}

	/**
	 * Agrega un equipo a la coleccion
	 * 
	 * @param i Será convertido en el nombre del equipo
	 * @return
	 */
	public Result<Integer, Exception> put(int i) {
		return put(String.valueOf(i));
	}

	/**
	 * Agrega un equipo a la colección.
	 * 
	 * @param nombre
	 * @return
	 * @return true si el equipo fue agregado. False si se excede la cantidad máxima
	 *         permitida.
	 */
	public Result<Integer, Exception> put(String nombre) {
		int size = this.size();

		if (exists(nombre)) {
			return Result.of(new AlreadyExistsException());
		}

		if (size == cantidadMaxima) {
			return Result.of(new MaxTeamCountReached());
		}

		var id = size + 1;
		super.put(id, new Equipo(id, nombre));

		return Result.of(id);
	}

	/**
	 * Retorna una lista de todos los equipos disponibles
	 * 
	 * @param juego
	 * @return
	 */
	public ArrayList<EquipoDetalle> getAllDetails(Juego juego) {
		ArrayList<EquipoDetalle> result = new ArrayList<>(size());
		for (Map.Entry<Integer, Equipo> entry : entrySet()) {
			var item = new EquipoDetalle();
			var equipo = entry.getValue();
			item.id = equipo.id;
			item.nombre = equipo.nombre;
			item.jugadores = juego.getPartida().contarJugadoresPorEquipo(equipo.id);
			result.add(item);
		}
		return result;
	}

	/**
	 * Encuentra el primer equipo vacío.
	 * 
	 * @return El equipo vacío encontrado.
	 */
	public Option<Equipo> buscarVacio() {
		for (Map.Entry<Integer, Equipo> entry : entrySet()) {
			var equipo = entry.getValue();
			if (equipo.isEmpty()) {
				return Option.of(equipo);
			}
		}
		return Option.none();
	}

	/**
	 * Determina si todos los equipos están completos.
	 */
	public boolean isFull(ModoJuego modo) {

		// En el modo individual vasta comprobar si existe
		// al menos un equipo vacío.
		if (ModoJuego.Individual == modo) {
			return buscarVacio().isNone();
		}

		// Buscar si aun existe al menos un equipo con miembros
		// faltantes.
		for (Map.Entry<Integer, Equipo> entry : entrySet()) {
			var equipo = entry.getValue();
			if (equipo.haveSpace()) {
				return false;
			}
		}

		// Todos los equipos completos.
		return true;
	}

	/**
	 * Retorna los jugadores distribuidos de tal modo que los jugadores de un mismo
	 * equipo no se encuentren uno a continuación de otro.
	 * 
	 * @param modo
	 * @return
	 */
	public LinkedList<Jugador> getJugadoresDistribuidos(ModoJuego modo) {
		LinkedList<Jugador> l1 = new LinkedList<Jugador>();
		LinkedList<Jugador> l2 = new LinkedList<Jugador>();

		for (Map.Entry<Integer, Equipo> entry : entrySet()) {
			Equipo equipo = entry.getValue();
			var jugadores = equipo.getJugadores();
			var keys = jugadores.keySet().toArray();

			l1.add(jugadores.get(keys[0]));
			if (keys.length > 1) {
				l2.add(jugadores.get(keys[1]));
			}
		}

		l1.addAll(l2);
		return l1;
	}
}
