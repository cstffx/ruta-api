package org.xrgames.logic;

import org.xrgames.ruta.entity.Usuario;
import org.xrgames.ruta.services.Debug;
import org.xrgames.ruta.services.FullGameObjectException;
import org.xrgames.ruta.services.NotFoundException;
import org.xrgames.ruta.services.OperationNotAllowed;
import org.xrgames.ruta.util.Equipos;
import org.xrgames.ruta.util.Option;
import org.xrgames.ruta.util.Result;

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
	public Equipos equipos = new Equipos();

	private final Usuario owner;
	private ConfiguracionJuego config;

	private boolean iniciado;

	/**
	 * Construye un juego con propietario y configuracion por defecto.
	 * 
	 * @param owner
	 */
	public Juego(Usuario owner) {
		this(owner, new ConfiguracionJuego());
	}

	/**
	 * Construye un juego con un propietario y configuración.
	 * 
	 * @param owner  Propietario del juego.
	 * @param config Configuración de la partida.
	 */
	public Juego(Usuario owner, ConfiguracionJuego config) {
		this.config = config;
		this.owner = owner;

		// Construir los equipos del juego
		EquipoFactory.build(this);

		// Construir la partida.
		partida = new Partida();
	}

	/**
	 * Inicia una nueva partida.
	 */
	public void nuevaPartida() {
		partida.nuevaPartida();
	}
	
	/**
	 * Inicia un juego si es posible.
	 * Un juego solo puede iniciar cuando:
	 * - Los jugadores están completos 
	 * - No ha sido ya iniciado.
	 * - Lo inicia su propietario
	 * @return
	 * @throws FullGameObjectException 
	 */
	public Result<Boolean,Exception> iniciar() {
		
		if(iniciado) {
			return Result.of(new OperationNotAllowed());
		}
		
		if(!equipos.isFull(config.modo)) {
			return Result.of(new OperationNotAllowed());
		}
		
		// Crear la lista de jugadores. 
		var jugadoresDistribuidos = equipos.getJugadoresDistribuidos(config.modo);
		var jugadoresPartida = partida.getJugadores();
		
		jugadoresPartida.clear();
		jugadoresPartida.addAll(jugadoresDistribuidos);
		
		// Juego marcado como iniciado.
		iniciado = true;
		
		return Result.of(true); 	
	}

	/**
	 * Roba una carta para el jugador actual.
	 * 
	 * @return
	 */
	public Carta robarCarta() {
		return partida.robarCarta();
	}

	/**
	 * Contabiliza los puntos de la partida actual para todos los jugadores.
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
	 * 
	 * @param nombre
	 * @return
	 */
	public Option<Equipo> getEquipo(int id) {
		return equipos.get(id);
	}

	/**
	 * Retorna todos los equipos del juego.
	 * 
	 * @return
	 */
	public Equipos getEquipos() {
		return equipos;
	}

	/**
	 * @return La configuración del juego.
	 */
	public ConfiguracionJuego getConfig() {
		return config;
	}

	/**
	 * @return Usuario propietario del juego.
	 */
	public Usuario getOwner() {
		return owner;
	}

	/**
	 * @return True si el juego ya ha sido iniciado.
	 */
	public boolean getIniciado() {
		return this.iniciado;
	}

	/**
	 * Unir a un jugador a un equipo del juego.
	 * 
	 * @param equipoId
	 * @param jugador
	 * @throws Exception
	 */
	public Result<Integer, Exception> joinJugador(int equipoId, Jugador jugador) throws Exception {
		var config = getConfig();

		// El modo determinará el equipo.
		Equipo equipo;

		// Unir un jugador en una partida individual.
		if (config.isIndividual()) {
			var equipos = getEquipos();

			// Recuperamos el siguiente equipo vacío.
			var siguienteVacio = equipos.buscarVacio();
			if (siguienteVacio.isNone()) {
				// Juego lleno.
				return Result.of(new FullGameObjectException());
			}
			
			equipo = siguienteVacio.unwrap();
		
		} else {
			
			// Unir jugador a una partida por equipos.
			var equipos = getEquipos();
			var equipoOption = equipos.get(equipoId);
			
			if (equipoOption.isNone()) {
				return Result.of(new NotFoundException("No se encuentra el equipo."));
			}
			
			equipo = equipoOption.unwrap();

			if (equipo.isFull()) {
				return Result.of(new FullGameObjectException("Equipo completo."));
			}
		}

		var usuario = jugador.getUsuario();
		var usuarioId = usuario.getId();
		
		// Establecemos el equipo del jugador.
		jugador.setEquipo(equipoId);
		
		equipo.getJugadores().put(usuarioId, jugador);

		return Result.of(equipo.id);
	}
}
