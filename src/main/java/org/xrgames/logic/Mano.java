package org.xrgames.logic;

/**
 *
 * @author user
 */
public class Mano extends Cartas {

	@Override
	public Carta extract(int i) {
		if (this.size() == 7) {
			return super.extract(i);
		}
		return null;
	}

	/**
	 * Envía una carta al pozo
	 * 
	 * @param index
	 * @throws Exception
	 */
	void enviarAPozo(int index) throws Exception {
		this.extract(index);
	}
}
