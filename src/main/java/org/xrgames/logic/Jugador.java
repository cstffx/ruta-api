package org.xrgames.logic;

import java.util.LinkedList;

import org.xrgames.ruta.entity.Usuario;

/**
 * Representa un jugador asociado a un equipo 
 * con su mano sus puntos actuales y acumulados.
 */
public class Jugador {

	private final Usuario usuario;
	
    /**
     * ID del equipo del jugador
     */
    private int equipo = 0;
    
    /**
     * Puntos ganados en la partidad actual.
     */
    private int puntuacionPartida = 0;
    
    /**
     * Puntos acumulados 
     */
    private int puntuacionAcumulada = 0;
    
    /**
     * True si ya se contabilizo la bonificacion 
     * por jugar todas las cartas de seguridad.
     */
    private boolean contabilizoTodasSeguridad = false;
    
    /**
     * La pila de cartas del jugador/equipo
     */
    private Pila pila = new Pila(); 
    
    /**
     * La mano del jugador.
     */
    private final Mano mano = new Mano();

    /**
     * Cantidad de victorias del jugador.
     */
	private int juegosGanados;

    /**
     * Constructor que asocia al jugador con un equipo.
     * @param equipo
     */
    public Jugador(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Deprecated
    /**
     * Constructor que asocia al jugador con un equipo.
     * @param equipo
     */
    public Jugador(int equipo) {
    	this.equipo = equipo;
		this.usuario = new Usuario();
    }
    
    /**
     * @return La pila de cartas del jugador.
     */
    Pila getPila(){
        return pila;
    }
    
    /**
     * Establece la pila actual. 
     * Permite compartir una pila entre varios jugadores.
     * @param pila
     */
    void setPila(Pila pila){
        this.pila = pila;
    }
    
    /**
     * @param jugador
     * @return True si el jugador recibido es del mismo equipo que el jugador actual.
     */
    public boolean mismoEquipo(Jugador jugador){
        return equipo == jugador.getEquipo();
    }
    
    /**
     * @return El id del equipo del jugador
     */
    public int getEquipo(){
        return equipo;
    }
    
    /**
     * @return True si la pila de cartas kilométricas tiene al menos una carta.
     */
    public boolean tieneCartaDistancia() {
        return pila.kilometrica.size() > 0;
    }
    
    /**
     * True si la pila kilométrica del jugador/equipo no contine cartas de 200 Km
     * @return
     */
    public boolean esViajeSeguro() {
        return pila.kilometrica.esViajeSeguro();
    }
    
    /**
     * @return True si la pila kilométrica del jugador/equipo es un viaje completo
     */
    public boolean esViajeCompleto() {
        return pila.kilometrica.esViajeCompleto();
    }

    /**
     * Transfiere una cantidad de cartas desde el mazo a la 
     * mano del jugador.
     * @param mazo
     * @param cantidad
     * @return
     */
    public LinkedList<Carta> tomarDelMazo(Mazo mazo, int cantidad) {
        LinkedList<Carta> cartasTomadas = new LinkedList<>();
        for (int i = 0; i < cantidad; i++) {
            // Pasamos la ultima carta del mazo a la mano
            var carta = Cartas.transfer(mazo, this.mano);
            if (carta != null) {
                cartasTomadas.push(carta);
            }
        }
        return cartasTomadas;
    }

    /**
     * @return La mano del jugador
     */
    public Mano getMano() {
        return this.mano;
    }
    
    /**
     * Establece en false la bandera que indica que el jugador 
     * contabilizó todas las cartas de seguridad.
     */
    public void restablecerContabilizoTodasSeguridad(){
        contabilizoTodasSeguridad = true;
    }
    
    /**
     * Establece en true la bandera que indica que el jugador 
     * contabilizó todas las cartas de seguridad.
     */
    public void anotarContabilizoTodasSeguridad(){
        contabilizoTodasSeguridad = true;
    }
    
    /**
     * Bandera para evitar contabilizar más de una vez 
     * que el jugador contabilizó todas las cartas de seguridad.
     * @return True si el jugador contabilizó todas las cartas de seguridad.
     */
    public boolean getContabilizoTodasSeguridad(){
        return contabilizoTodasSeguridad;
    }
    
    /**
     * @return La pila kilométrica
     */
    public PilaKilometros getPilaKilometrica() {
        return pila.kilometrica;
    }
    
    /**
     * @return Pila de velocidad.
     */
    public Cartas getPilaVelocidad() {
        return pila.velocidad;
    }

    /**
     * @return Pila de ataques y defensas.
     */
    public Cartas getPilaAtaqueDefensa() {
        return pila.ataqueDefensa;
    }
    
    /**
     * @return La pila de escudo.
     */
    public Cartas getPilaEscudo() {
        return pila.ataqueDefensa;
    }

    /**
     * Aumenta en uno las victorias del jugador.
     */
    public void anotarVictoria() {
        ++this.juegosGanados;
    }
    
    /**
     * Adiciona puntos a la partida
     * @param puntos
     */
    public void anotarPuntosPartida(int puntos) {
        this.puntuacionPartida += puntos;
    }

    /**
     * Adiciona puntos a los puntos acumulados
     * @param puntos 
     */
    public void anotarPuntosAcumulados(int puntos) {
        this.puntuacionAcumulada += puntos;
    }

    /**
     * @return La pila de escudos.
     */
    public Cartas getPilaSeguridadEscudo() {
        return pila.seguridadEscudo;
    }
    
    /**
     * @return La puntuación acumulada de todas las partidas.
     */
    public int getPuntuacionAcumulada(){
        return puntuacionAcumulada;
    }

    /**
     * @return La puntucación de la partida actual.
     */
    public int getPuntuacionPartida() {
        return puntuacionPartida;
    }

    /**
     * Vacía la mano del jugador
     */
    public void vaciarMano() {
        this.mano.clear();
    }

    /**
     * Vacía la pila del equipo/jugador.
     */
    public void vaciarPilas() {
        pila.vaciar();
    }
    
    /**
     * Restablece la puntuación de la partida
     */
    public void reiniciarPuntuacionPartida(){
        puntuacionPartida = 0;
    }
    
    /**
     * Determina si el jugador gana la partida.
     * @return
     */
    public boolean ganaPartida() {
        return puntuacionAcumulada >= Juego.PUNTOS_MIN_PARA_GANAR;
    }
   
    /**
     * Contabiliza los puntos por jugar una carta de seguridad.
     * @return
     */
    public int getPuntosSeguridad() {
        int cantidad = pila.seguridadEscudo.size();
        int puntos = cantidad * Juego.PUNTOS_POR_UNA_SEGURIDAD;

        if (cantidad == Juego.CANTIDAD_SEGURIDAD_ESCUDO) {
            puntos += Juego.PUNTOS_POR_TODAS_SEGURIDAD;
        }

        return puntos;
    }
    
    /**
     * @return La cantidad de juegos ganados del jugador.
     */
    public int getCantidadJuegosGanados() {
    	return juegosGanados;
    }
    
    /**
     * @return El usuario asociado al jugador.
     */
    public Usuario getUsuario() {
    	return usuario;
    }
}
