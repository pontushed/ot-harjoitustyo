/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.domain;

/**
 *
 * @author pontus
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vuoro extends AbstractPersistable<Long> {
    
    @ManyToOne
    private Vuorokoodi vuorokoodi;
    private LocalDate paiva;
    
    @OneToOne
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
        return LocalDateTime.of(this.paiva, this.vuorokoodi.getAloitusAika());
    }
    
    public LocalDateTime getLopetusAika() {        
        return this.vuorokoodi.getLopetusAika(paiva);
    }

    @Override
    public String toString() {
        return "Vuoro: vuorokoodi=" + vuorokoodi.getKoodi() + ", paiva=" + paiva.toString() + ", tekija=" + tekija.getTunnus();
    }
                
}
