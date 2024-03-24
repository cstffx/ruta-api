package org.xrgames.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.xrgames.ruta.response.EquipoDetalle;
import org.xrgames.ruta.services.AlreadyExistsException;
import org.xrgames.ruta.services.MaxTeamCountReached;
import org.xrgames.ruta.util.Option;
import org.xrgames.ruta.util.Result;

/**
 * Map que almacena los equipos del juego.
 */
public class EquipoMap extends HashMap<Integer, Equipo> {

	// Límite de equipos admitidos en la colección.
	private int cantidadMaxima = Juego.MAX_EQUIPOS;

	// Uso en serialización
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto que establece a Juego.MAX_EQUIPOS la cantida máxima
	 * de equipos aceptados.
	 */
	public EquipoMap() {
		cantidadMaxima = Juego.MAX_EQUIPOS;
	}

	/**
	 * Retorna un equipo por su nombre.
	 * @param nombre
	 * @return
	 */
	public Option<Equipo> get(String nombre){
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
	 * @param id
	 * @return
	 */
	public Option<Equipo> get(int id){
		var equipo = super.get(id);
		if(null == equipo) {
			return Option.none();
		}
		return Option.of(equipo);
	}
	
	/**
	 * Constructor que permite establecer manualmente la cantidad máxima de equipos
	 * aceptados
	 */
	public EquipoMap(int cantidadMaxima) {
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
}
