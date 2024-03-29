package org.xrgames.logic;

/**
 * Crea los equipos de un juego dependiendo de la modalidad y cantidad de
 * jugadores.
 */
public class EquipoFactory {

	/**
	 * Crea los equipos del juego según la configuración.
	 * 
	 * @param juego
	 */
	public static void build(Juego juego) {

		var config = juego.getConfig();
		var cantidad = config.getCantidadEquiposPosibles();
		var equipos = juego.getEquipos();

		// Limpiamos cualquier asignación anterior.
		equipos.clear();

		for (int i = 0; i < cantidad; i++) {
			var equipoId = i + 1;
			var equipo = new Equipo(equipoId, "Equipo " + i);
			equipos.put(equipoId, equipo);
		}
	}
}