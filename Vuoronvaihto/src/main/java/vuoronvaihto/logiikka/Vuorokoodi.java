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

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Vuorokoodi {
    private final String koodi;
    private final LocalTime aloitusAika;
    private final long pituus;
    
    public Vuorokoodi(String koodi, String aloitusAika, double pituus ) {
        this.koodi = koodi;
        this.aloitusAika = LocalTime.parse(aloitusAika);
        this.pituus = (long)pituus*60;
    }
    
    public long getPituus() {
        return pituus;
    }

    public String getKoodi() {
        return koodi;
    }

    public LocalTime getAloitusAika() {
        return aloitusAika;
    }

    public LocalDateTime getLopetusAika(LocalDate pvm) {
        LocalDateTime lopetusAika = LocalDateTime.of(pvm,aloitusAika);
        return lopetusAika.plusMinutes(pituus);
    }
        
}
