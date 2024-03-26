package org.xrgames.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.xrgames.ruta.util.Equipos;

/**
 * @author user
 */
public class Partida {

    private int actual = 0;
    private final Mazo mazo;
    
    private final LinkedList<Jugador> jugadores = new LinkedList<>();

    /**
     * Contruye una nueva partidad con un mazo.
     */
    public Partida() {
        mazo = new Mazo();
    }

    /**
     * @return Lista de jugadores.
     */
    public LinkedList<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Retorna la cantidad de jugadores en un equipo.
     * @param id
     */
    public int contarJugadoresPorEquipo(int id) {
    	int result = 0;
    	for(Jugador jugador: jugadores) {
    		if(jugador.getEquipo() == id) {
    			++result;
    		}
    	}
    	return result;
    }
    
    /**
     * @return El mazo actual de la partida.
     */
    public Mazo getMazo() {
        return mazo;
    }

    public void agregarJugador(Jugador j) {
        jugadores.add(j);
    }

    /**
     * Contabiliza los puntos al final de la partida. 
     * Aumenta los puntos acumulados de cada jugador 
     * con los puntos de la partida y los puntos ganados 
     * por bonificaciones al final de la partida.
     */
    public void contabilizarPuntos() {
        for (Jugador jugador : jugadores) {
            var puntos = 0;
            
            // Puntos del jugador. 
            // Km
            // Seguridad
            // Viaje completo 
            // Accion retardada
            puntos += jugador.getPuntuacionPartida();

            var seguro = jugador.esViajeSeguro();
            var completo = jugador.esViajeCompleto();
           
            if (seguro) {
                puntos += Juego.PUNTOS_POR_VIAJE_SEGURO;
            }

            if (completo) {
                // Verificar eliminacion de oponentes.             
                var eliminacion = true;
                for (Jugador otroJugador : jugadores) {
                    if (otroJugador == jugador || otroJugador.mismoEquipo(jugador)) {
                        continue;
                    }
                    if (jugador.tieneCartaDistancia()) {
                        eliminacion = false;
                        break;
                    }
                }
                if (eliminacion) {
                    puntos += Juego.PUNTOS_POR_ELIMINACION;
                }
            }

            jugador.anotarPuntosAcumulados(puntos);
        }
    }

    /**
     * Retorna el jugador actual
     * @return 
     */
    public Jugador getJugadorActual() {
        return jugadores.get(actual);
    }
    
    /**
     * Establece el jugador actual
     * @param index 
     */
    public void setJugadorActual(int index){
        actual = index;
    }

    /**
     * Avanza al siguiente jugador
     * @return 
     */
    public Jugador avanzarJugador() {
        actual = (actual + 1) % jugadores.size();
        return getJugadorActual();
    }

    /**
     * Retorna el ganador de un juego parcial. 
     * @return Ganador del parcial o null
     */
    public Jugador getJugadorGanadorParcial() {
        for (Jugador jugador : jugadores) {
            var pila = jugador.getPilaKilometrica();
            var km = pila.getKilometrosTotales();
            if (Juego.KILOMETROS_POR_VIAJE_COMPLETO == km) {
                return jugador;
            }
        }
        return null;
    }

    /**
     * Retorna true si todas las manos estan vacias.
     *
     * @return true si todas las manos estan vacias
     */
    public boolean getManosVacias() {
        for (Jugador jugador : jugadores) {
            if (false == jugador.getMano().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retorna true si se alzanzó el final parcial.
     *
     * @return true si se alzanzó el final parcial.
     */
    public boolean esFinal() {
        // Un jugador alcanza exactamente 1000 KM. 
        var ganador = getJugadorGanadorParcial();
        if (null != ganador) {
            return true;
        }

        var mazoVacio = mazo.isEmpty();
        var manoVacia = getManosVacias();

        return mazoVacio && manoVacia;
    }

    /**
     * Inicia una nueva partida 
     * En una nueva partida se reinicia el mazo, así como las manos,
     * las pilas y los puntos parciales de los jugadores.
     */
    public void nuevaPartida() {

        // Nuevo mazo con cartas mezcladas
        mazo.reiniciar();

        for (Jugador jugador : jugadores) {
            Mano mano = jugador.getMano();

            // Quitar cartas de las pilas.
            jugador.vaciarPilas();

            // Retablecer los puntos de la partida a 0.
            jugador.reiniciarPuntuacionPartida();
            
            jugador.restablecerContabilizoTodasSeguridad();

            // Nueva mano
            mano.clear();
            Cartas.transferMany(mazo, mano, Juego.CARTAS_EN_MANO);
        }
    }

    /**
     * Roba una carta del mazo para el jugador actual. 
     * En algunos casos es necesario cambiar el jugador actual,
     * por ejemplo en un contrataque, utilice setJugadorActual
     * para establecer el jugador el contrataque.
     * @return 
     */
    public Carta robarCarta() {
        var jugador = getJugadorActual();
        var cartas = jugador.tomarDelMazo(mazo, 1);
        if (cartas.isEmpty()) {
            return null;
        }
        return cartas.get(0);
    }

    /**
     * Realiza una jugada
     * @param jugada
     * @return
     * @throws Exception 
     */
    public int jugada(ConfiguracionJugada jugada) throws Exception {
        Cartas pila = null;
        var puntosJugada = 0;
        var index = jugada.indiceCarta;
        var jugador = getJugadorActual();
        var mano = jugador.getMano();

        if (jugada.enviarAPozo) {
            mano.enviarAPozo(index);
            return puntosJugada;
        }

        var carta = mano.get(index);

        switch (carta.getTipo()) {
            case DistanciaKilometrica:
                pila = jugador.getPilaKilometrica();
                puntosJugada += carta.getPuntuacionPorKilometros();
                
                // Verificar viaje completo. 
                // Seríá la ultima jugada de la partida. 
                var recorridoCompleto = false;
                var mazoVacio = mazo.isEmpty();
                var pilaKm = jugador.getPilaKilometrica();
                var kmRecorridos = pilaKm.getKilometrosTotales();
                var kmTotales = carta.getKilometros() + kmRecorridos;
                if (Juego.KILOMETROS_POR_VIAJE_COMPLETO == kmTotales){
                    puntosJugada += Juego.PUNTOS_POR_VIAJE_COMPLETO;
                    recorridoCompleto = true;
                }

                // Puntos por accion retardada 
                if(recorridoCompleto && mazoVacio){
                    puntosJugada += Juego.PUNTOS_POR_ACCION_RETARDADA;
                }
                
                break;
                
            case PeligroAtaque:
                jugador = jugada.jugadorDestino;
                pila = jugador.getPilaAtaqueDefensa();
                // Limite de velocidad se juega sobre pila kilometrica.
                if (CartaSubtipo.LimiteVelocidad == carta.getSubtipo()) {
                    pila = jugador.getPilaVelocidad();
                }
                break;
                
            case SolucionesDefensa:
                pila = jugador.getPilaAtaqueDefensa();
                // Fin de limite de velocidad se juega sobre pila kilometrica.
                if (CartaSubtipo.FinLimiteVelocidad == carta.getSubtipo()) {
                    pila = jugador.getPilaVelocidad();
                }
                break;
                
            case SeguridadEscudo:
                
                // Contabilizar puntos por jugar carta de seguridad.
                puntosJugada += Juego.PUNTOS_POR_UNA_SEGURIDAD;
                pila = jugador.getPilaEscudo();
                var cantidad = pila.size();
                var cantidadParaBonificacion = Juego.CANTIDAD_SEGURIDAD_ESCUDO;
                var contabilizo = jugador.getContabilizoTodasSeguridad();
               
                // Nos aseguramos de contar esta bonificacion solo una vez.
                if (cantidad == cantidadParaBonificacion && !contabilizo) {
                    puntosJugada += Juego.PUNTOS_POR_TODAS_SEGURIDAD;
                    jugador.anotarContabilizoTodasSeguridad();
                }
                
                carta.setDireccion(jugada.direccion);
             
                // Contabilizar puntos por contrataque 
                if(jugada.direccion == Direccion.Horizontal){
                    // Es un contrataque
                    puntosJugada += Juego.PUNTOS_POR_CONTRATAQUE;
                }
                
                break;
                
            case SemaforoVerde:
                pila = jugador.getPilaAtaqueDefensa();
                break;
                
            case SemaforoRojo:
                jugador = jugada.jugadorDestino;
                pila = jugador.getPilaAtaqueDefensa();
                break;
            default:
        }
        
        jugador.anotarPuntosPartida(puntosJugada);

        if (pila != null) {
            // Mover una carta especifica de la mano a una pila.
            Cartas.transfer(mano, pila, index);
        }

        return puntosJugada;
    }

    /**
     * Obtinene el numero total de equipos.
     * @return 
     */
    public int getCantidadEquipos() {
        int max = 0;
        for (Jugador jugador : jugadores) {
            var equipo = jugador.getEquipo();
            if (equipo > max) {
                max = equipo;
            }
        }
        return max;
    }

    /**
     * En juegos por equipo calcula la puntuacion total de un equipo como 
     * la suma de los puntos de los jugadores de cada equipo. 
     * El resultado es una lista donde el indice indica el numero del equipo
     * en base 0. Es decir, al equipo 1 le corresponde el indice 0.
     * @return 
     */
    public ArrayList<Integer> getPuntosPorEquipo() {
        var cantidadEquipos = getCantidadEquipos();
        ArrayList<Integer> puntuaciones = new ArrayList<>(cantidadEquipos);
        Collections.fill(puntuaciones, 0);
        for (Jugador j : jugadores) {
            var equipo = j.getEquipo();
            var acumulada = j.getPuntuacionAcumulada();
            var puntuacionEquipo = puntuaciones.get(equipo);
            puntuaciones.set(equipo - 1, acumulada + puntuacionEquipo);
        }
        return puntuaciones;
    }

    /**
     * Determina el numero del equipo ganador.
     * @return -1 si no hay equipo ganador, de lo contrario el numero del equipo en base 1.
     */
    public int getEquipoGanador() {
        var equipoGanador = -1;
        var puntosPorEquipo = getPuntosPorEquipo();
        for (int i = 0; i < puntosPorEquipo.size(); i++) {
            if (puntosPorEquipo.get(i) >= Juego.PUNTOS_MIN_PARA_GANAR) {
                equipoGanador = i + 1;
                break;
            }
        }
        return equipoGanador;
    }
}
