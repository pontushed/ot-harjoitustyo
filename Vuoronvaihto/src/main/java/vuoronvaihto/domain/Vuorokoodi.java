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

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vuorokoodi extends AbstractPersistable<Long> {
    private String koodi;
    private LocalTime aloitusAika;
    private long pituus;
    
    public Vuorokoodi(String koodi, String aloitusAika, double pituus) {
        this.koodi = koodi;
        this.aloitusAika = LocalTime.parse(aloitusAika);
        this.pituus = (long) pituus * 60;
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
        LocalDateTime lopetusAika = LocalDateTime.of(pvm, aloitusAika);
        return lopetusAika.plusMinutes(pituus);
    }
        
}
