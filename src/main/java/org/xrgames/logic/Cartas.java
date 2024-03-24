package org.xrgames.logic;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author user
 */
public class Cartas {
	
	public final static int ULTIMA_CARTA = -1;
    private final LinkedList<Carta> cartas = new LinkedList<>();

    public static LinkedList<Carta> transferMany(Cartas a, Cartas b, int size){
         LinkedList<Carta> cartas = new LinkedList<>();
         for(int i =0; i < size; i++){
             var carta = transfer(a, b);
             if(null != carta){
                cartas.add(carta);                 
             }
         }
         return cartas;
    }
    
    public static Carta transfer(Cartas a, Cartas b, int index) {
        Carta carta = a.extract(index);
        if( carta != null){
            b.add(carta);
        }
        return carta;
    }

    public static Carta transfer(Cartas a, Cartas b) {
        return Cartas.transfer(a, b, Cartas.ULTIMA_CARTA);
    }

    public int size() {
        return cartas.size();
    }

    public boolean isEmpty() {
        return cartas.isEmpty();
    }

    public Carta extract(int index) {
        if (index == Cartas.ULTIMA_CARTA) {
            return cartas.removeLast();
        }
        return cartas.remove(index);
    }

    public void add(Carta carta) {
        cartas.add(carta);
    }

    public void addMany(Carta carta, int cantidad) {
        var tipo = carta.getTipo();
        var subtipo = carta.getSubtipo();
        for(int i = 0; i < cantidad; i++){
            Carta newCarta = new Carta(tipo, subtipo);
            this.add(newCarta);
        }
    }

    public void clear() {
        cartas.clear();
    }

    public void shuffle() {
        Collections.shuffle(cartas);
    }
    
        
    public Carta get(int index){
        return cartas.get(index);
    } 
    
    public LinkedList<Carta> getAll() {
        return cartas;
    }
}
