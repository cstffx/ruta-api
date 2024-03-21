package org.xrgames.logic;

import java.util.LinkedList;

/**
 *
 * @author user
 */
public final class Mazo extends Cartas {
    
    public Mazo() {
        this.reiniciar();
    }
    
    public LinkedList<Carta> reiniciar() {
        
        // Quitamos todas las cartas que puedan quedar.
        clear();
        
        this.addMany(new Carta(Tipo.PeligroAtaque, CartaSubtipo.Siga), 3);
        this.addMany(new Carta(Tipo.PeligroAtaque, CartaSubtipo.Pinchazo), 3);
        this.addMany(new Carta(Tipo.PeligroAtaque, CartaSubtipo.Accidente), 3);
        this.addMany(new Carta(Tipo.PeligroAtaque, CartaSubtipo.LimiteVelocidad), 4);
        this.addMany(new Carta(Tipo.PeligroAtaque, CartaSubtipo.Pare), 5);
        
        this.addMany(new Carta(Tipo.SolucionesDefensa, CartaSubtipo.Gasolina), 6);
        this.addMany(new Carta(Tipo.SolucionesDefensa, CartaSubtipo.LlantaRepuesto), 6);
        this.addMany(new Carta(Tipo.SolucionesDefensa, CartaSubtipo.Reparacion), 6);
        this.addMany(new Carta(Tipo.SolucionesDefensa, CartaSubtipo.FinLimiteVelocidad), 6);
        this.addMany(new Carta(Tipo.SolucionesDefensa, CartaSubtipo.Siga), 14);
        
             
        this.addMany(new Carta(Tipo.SeguridadEscudo, CartaSubtipo.Sistema), 1);
        this.addMany(new Carta(Tipo.SeguridadEscudo, CartaSubtipo.LlantaIrrompible), 1);
        this.addMany(new Carta(Tipo.SeguridadEscudo, CartaSubtipo.ViaLibre), 1);
        this.addMany(new Carta(Tipo.SeguridadEscudo, CartaSubtipo.AzAlVolante), 1);
               
        this.addMany(new Carta(Tipo.DistanciaKilometrica, CartaSubtipo.Distancia200Km), 4);
        this.addMany(new Carta(Tipo.DistanciaKilometrica, CartaSubtipo.Distancia100Km),12);
        this.addMany(new Carta(Tipo.DistanciaKilometrica, CartaSubtipo.Distancia75Km),10);
        this.addMany(new Carta(Tipo.DistanciaKilometrica, CartaSubtipo.Distancia50Km),10);
        this.addMany(new Carta(Tipo.DistanciaKilometrica, CartaSubtipo.Distancia25Km),10);
        
        this.addMany(new Carta(Tipo.SemaforoVerde, CartaSubtipo.Ninguno), 1);
        this.addMany(new Carta(Tipo.SemaforoRojo, CartaSubtipo.Ninguno), 1);
         
        this.shuffle();
                
        return this.getAll();
    }
}
