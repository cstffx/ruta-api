package org.xrgames.logic;

/**
 *
 * @author user
 */
public class ConfiguracionJuego {
    
	public ModoJuego modo;
    
    public int jugadores;
    
	/**
	 * Calcula y retorna la cantidad posible de equipos
	 * atendiendiendo a la configuración actual del juego.
	 * @return
	 */
	public int getCantidadEquiposPosibles() {
		if(modo == ModoJuego.Individual) {
			return Juego.MAX_EQUIPOS;
		}
		return jugadores / 2;
	}
}
