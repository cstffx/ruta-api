package org.xrgames.logic;

import java.util.LinkedList;

/**
 * @author user
 */
public class Jugador {

    private int juegosGanados = 0;
    private int equipo = 0;
    
    private int puntuacionPartida = 0;
    private int puntuacionAcumulada = 0;
    
    // True si ya se contabilizó la bonificacion 
    // por todas las de seguridad.
    private boolean contabilizoTodasSeguridad = false;
    
    private Pila pila = new Pila(); 
    
    private final Mano mano = new Mano();

    public Jugador(int equipo) {
        this.equipo = equipo;
    }
    
    Pila getPila(){
        return pila;
    }
    
    void setPila(Pila pila){
        this.pila = pila;
    }
    
    public boolean mismoEquipo(Jugador jugador){
        return equipo == jugador.getEquipo();
    }
    
    public int getEquipo(){
        return equipo;
    }
    
    public boolean tieneCartaDistancia() {
        return pila.kilometrica.size() > 0;
    }
    
    public boolean esViajeSeguro() {
        return pila.kilometrica.esViajeSeguro();
    }
    
    public boolean esViajeCompleto() {
        return pila.kilometrica.esViajeCompleto();
    }

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

    public Mano getMano() {
        return this.mano;
    }
    
        
    public void restablecerContabilizoTodasSeguridad(){
        contabilizoTodasSeguridad = true;
    }
    
    public void anotarContabilizoTodasSeguridad(){
        contabilizoTodasSeguridad = true;
    }
    
    public boolean getContabilizoTodasSeguridad(){
        return contabilizoTodasSeguridad;
    }

    public PilaKilometros getPilaKilometrica() {
        return pila.kilometrica;
    }
    
    public Cartas getPilaVelocidad() {
        return pila.velocidad;
    }

    public Cartas getPilaAtaqueDefensa() {
        return pila.ataqueDefensa;
    }
    
    public Cartas getPilaEscudo() {
        return pila.ataqueDefensa;
    }

    public void anotarVictoria() {
        ++this.juegosGanados;
    }
    
    public void anotarPuntosPartida(int puntos) {
        this.puntuacionPartida += puntos;
    }

    public void anotarPuntosAcumulados(int puntos) {
        this.puntuacionAcumulada += puntos;
    }

    public Cartas getPilaSeguridadEscudo() {
        return pila.seguridadEscudo;
    }
    
    public int getPuntuacionAcumulada(){
        return puntuacionAcumulada;
    }

    public int getPuntuacionPartida() {
        return puntuacionPartida;
    }

    public void vaciarMano() {
        this.mano.clear();
    }

    public void vaciarPilas() {
        pila.vaciar();
    }
    
    public void reiniciarPuntuacionPartida(){
        puntuacionPartida = 0;
    }
    
    public boolean ganaPartida() {
        return puntuacionAcumulada >= Juego.PUNTOS_MIN_PARA_GANAR;
    }
   
    public int getPuntosSeguridad() {
        int cantidad = pila.seguridadEscudo.size();
        int puntos = cantidad * Juego.PUNTOS_POR_UNA_SEGURIDAD;

        if (cantidad == Juego.CANTIDAD_SEGURIDAD_ESCUDO) {
            puntos += Juego.PUNTOS_POR_TODAS_SEGURIDAD;
        }

        return puntos;
    }
}
