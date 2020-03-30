/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.logiikka;

/**
 *
 * @author pontus
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Vuoro {
    private final Vuorokoodi vuorokoodi;
    private final LocalDate paiva;
    private Kayttaja tekija;

    public Vuoro(Vuorokoodi vuorokoodi, String paiva, Kayttaja tekija) {
        this.vuorokoodi = vuorokoodi;
        this.paiva = LocalDate.parse(paiva);
        this.tekija = tekija;
    }

    public Kayttaja getTekija() {
        return tekija;
    }

    public void setTekija(Kayttaja tekija) {
        this.tekija = tekija;
    }
    
    public LocalDateTime getAloitusAika() {
        return LocalDateTime.of(this.paiva,this.vuorokoodi.getAloitusAika());
    }
    
    public LocalDateTime getLopetusAika() {        
        return this.vuorokoodi.getLopetusAika(paiva);
    }

    @Override
    public String toString() {
        return "Vuoro: vuorokoodi=" + vuorokoodi.getKoodi() + ", paiva=" + paiva.toString() + ", tekija=" + tekija.getTunnus();
    }
                
}
