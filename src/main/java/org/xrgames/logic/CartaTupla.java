package org.xrgames.logic;

/**
 *
 * @author user
 */
public class CartaTupla {
	public Tipo tipo;
	public CartaSubtipo subtipo;
	public int cantidad;

	public CartaTupla(Tipo tipo, CartaSubtipo subtipo, int cantidad) {
		this.tipo = tipo;
		this.subtipo = subtipo;
		this.cantidad = cantidad;
	}
}
