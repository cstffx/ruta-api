package org.xrgames.logic;

import java.util.LinkedList;

/**
 * @author user
 */
public final class Juego {

    public static int CARTAS_TOTALES = 108;
    public static int CARTAS_EN_MANO = 6;
    public static int CARTAS_AL_ROBAR = 7;
    public static int CANTIDAD_SEGURIDAD_ESCUDO = 4;

    public static int PUNTOS_POR_UNA_SEGURIDAD = 100;
    public static int PUNTOS_POR_TODAS_SEGURIDAD = 300;
    public static int PUNTOS_POR_CONTRATAQUE = 300;

    public static int KM_INSEGUROS = 200;
    public static int PUNTOS_POR_KM = 1;
    public static int PUNTOS_POR_VIAJE_COMPLETO = 400;
    public static int KILOMETROS_POR_VIAJE_COMPLETO = 1000;
    public static int PUNTOS_POR_ACCION_RETARDADA = 300;
    public static int PUNTOS_POR_VIAJE_SEGURO = 300;
    public static int PUNTOS_POR_ELIMINACION = 500;
    public static int PUNTOS_MIN_PARA_GANAR = 5000;

    // Cantidad maxima de equipos
	public static int MAX_EQUIPOS = 3;
	// Cantidad maxima de jugadores
	public final static int MAX_JUGADORES = 6;
	// Cantidad máxima de jugadores por equipo
	public static int MAX_JUGADOR_POR_EQUIPO = 2;
	
    private final Partida partida;
	public EquipoCollection equipos = new EquipoCollection();
	private ConfiguracionJuego config;

    public Juego() {
    	config = new ConfiguracionJuego();
    	config.jugadores = 6;
    	config.modo = ModoJuego.Individual;
        partida = new Partida();
    }
    
    public Juego(ConfiguracionJuego config) {
    	this.config = config;
        partida = new Partida();
    }

    public void nuevaPartida() {
        partida.nuevaPartida();
    }

    public Carta robarCarta() {
        return partida.robarCarta();
    }
    
    public void contabilizarPuntos() {
        partida.contabilizarPuntos();
    }

    public void jugada(ConfiguracionJugada jugada) throws Exception {
        partida.jugada(jugada);
    }
        
    public boolean esFinal() {
        return -1 != partida.getEquipoGanador();
    }
  
    // TODO: Utilizar eventos
    public boolean esFinalParcial() {
        return partida.esFinal();
    }

    /**
     * @return La partida actual.
     */
    public Partida getPartida() {
        return partida;
    }
    
	/**
	 * Retorna un equipo por su id
	 * @param nombre
	 * @return
	 */
	public Equipo getEquipo(int id) {
		for(var equipo: equipos) {
			if(equipo.id == id) {
				return equipo;
			}
		}
		return null;
	}
	
	/**
	 * Retorna todos los equipos del juego.
	 * @return
	 */
	public EquipoCollection  getEquipos() {
		return equipos;
	}

	/**
	 * Elimina un equipo por su id.
	 * @param id
	 */
	public boolean deleteEquipo(int id) {
		for(var equipo: equipos) {
			if(id == equipo.id) {
				equipos.remove(equipo);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return La configuración del juego.
	 */
	public ConfiguracionJuego getConfig() {
		return config;
	}
}
