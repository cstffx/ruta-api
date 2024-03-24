package org.xrgames.logic;

import org.xrgames.ruta.entity.Usuario;
import org.xrgames.ruta.util.Option;

/**
 * Mantiene el estado actual del juego
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
	public EquipoMap equipos = new EquipoMap();
	
	private Option<Usuario> owner;
	private ConfiguracionJuego config;
	
	private boolean iniciado;

	/**
	 * Constructor por defecto.
	 */
    public Juego() {
    	config = new ConfiguracionJuego();
    	config.jugadores = 6;
    	config.modo = ModoJuego.Individual;
        partida = new Partida();
        owner = Option.none();
    }
    
    /**
     * Construye un juego con un propietario y configuración.
     * @param owner Propietario del juego.
     * @param config Configuración de la partida. 
     */
    public Juego(Usuario owner, ConfiguracionJuego config) {
    	this.config = config;
    	this.owner = Option.of(owner);
    	
    	partida = new Partida();
    }

    /**
     * Inicia una nueva partida.
     */
    public void nuevaPartida() {
        partida.nuevaPartida();
    }

    /**
     * Roba una carta para el jugador actual.
     * @return
     */
    public Carta robarCarta() {
        return partida.robarCarta();
    }
    
    /**
     * Contabiliza los puntos de la partida actual 
     * para todos los jugadores.
     */
    public void contabilizarPuntos() {
        partida.contabilizarPuntos();
    }

    /**
     * @param jugada
     * @throws Exception
     */
    public void jugada(ConfiguracionJugada jugada) throws Exception {
        partida.jugada(jugada);
    }
        
    /**
     * @return True si es un estado final.
     */
    public boolean esFinal() {
        return -1 != partida.getEquipoGanador();
    }
  
    /**
     * @return True si el estado actual es un final parcial.
     */
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
	public Option<Equipo> getEquipo(int id) {
		return equipos.get(id);
	}
	
	/**
	 * Retorna todos los equipos del juego.
	 * @return
	 */
	public EquipoMap  getEquipos() {
		return equipos;
	}
	
	/**
	 * @return La configuración del juego.
	 */
	public ConfiguracionJuego getConfig() {
		return config;
	}
	
	/**
	 * Establece el propietario del juego.
	 * @param owner
	 */
	public void setOwner(Usuario owner) {
		this.owner = Option.of(owner);
	}
	
	/**
	 * @return Usuario propietario del juego.
	 */
	public Option<Usuario> getOwner() {
		return owner;
	}
	
	/**
	 * Establece si el juego ya ha sido iniciado.
	 * @param iniciado
	 */
	public void setIniciado(boolean iniciado) {
		this.iniciado = iniciado;
	}
	
	/**
	 * @return True si el juego ya ha sido iniciado.
	 */
	public boolean getIniciado() {
		return this.iniciado;
	}
}
