package org.xrgames.ruta.services;

import java.util.LinkedList;
import java.util.List;

public class HistorialService {

	private LinkedList<Accion> acciones = new LinkedList<>();

	/**
	 * Agrega una accion a la lista
	 * 
	 * @param accion
	 */
	public void addAccion(Accion accion) {
		this.acciones.add(accion);
	}

	/**
	 * Retorna todas las acciones ocurridas despues de time.
	 * 
	 * @param time
	 * @return
	 */
	public List<Accion> getFrom(Long time) {
		int index;
		for (index = 0; index < acciones.size(); index++) {
			var accion = acciones.get(index);
			if (accion.time > time) {
				break;
			}
		}
		return acciones.subList(index, acciones.size() - 1);
	}
}