package org.xrgames.logic;

/**
 *
 * @author user
 */
public class PilaKilometros extends Cartas {
    
    public boolean esViajeSeguro() {
        var cartas = this.getAll(); 
        for(Carta carta: cartas){
            if(Juego.KM_INSEGUROS == carta.getKilometros()){
                return false;
            }
        }
        return true;
    }
    
    public boolean esViajeCompleto() {
        return this.getKilometrosTotales() == Juego.KILOMETROS_POR_VIAJE_COMPLETO;
    }
    
    public int getKilometrosTotales() {
        var total = 0;
        var cartas = this.getAll();
        for(Carta carta: cartas){
            total += carta.getKilometros();
        }
        return total;
    }
    
    public int getPuntosTotales(){
        var total = this.getKilometrosTotales();
        return Juego.PUNTOS_POR_KM * total;
    }
}
