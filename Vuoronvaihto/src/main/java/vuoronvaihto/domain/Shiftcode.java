/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.domain;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * 
 * Tämä luokka kuvaa Vuorokoodia.
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Shiftcode extends AbstractPersistable<Long> {
    
    @Column(unique = true)
    private String code;
    
    private LocalTime startTime;
    private long durationInMinutes;
    
    /**
     * Konstruktori.
     * @param code esim. AA0600
     * @param startTime esim. 06:00
     * @param durationInHours esim. 7.5
     */
    public Shiftcode(String code, String startTime, double durationInHours) {
        this.code = code;
        this.startTime = LocalTime.parse(startTime);
        this.durationInMinutes = (long) (durationInHours * 60);
    }
    
    public long getDurationInMinutes() {
        return durationInMinutes;
    }

    public String getCode() {
        return code;
    }

    /**
     * Palauta vuoron alkamisaika.
     * @return alkamisaika LocalTimena
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Palauta vuoron loppumisaika LocalDateTimena, kun tiedetään
     * alkamispäivämäärä. Käytä tätä, kun lasket vuorojen välistä lepoaikaa.
     * @param date Vuoron alkamisajan päivämäärä LocalDatena
     * @return loppumisaika LocalDateTimena
     */
    public LocalDateTime getFinishTime(LocalDate date) {
        LocalDateTime finishTime = LocalDateTime.of(date, startTime);
        return finishTime.plusMinutes(durationInMinutes);
    }
    
    @Override
    public String toString() {
        if (code.equals("Vapaa")) { 
            return "Vapaa"; 
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String s = code + " (" +
                startTime.format(formatter) + "-" + 
                startTime.plusMinutes(durationInMinutes).format(formatter) + ")";
        return s;
    }
    
    /**
     * equals-metodi luokalle.
     * @param v vuorokoodi, johon verrataan
     * @return true, mikäli sama
     */
    public boolean equals(Shiftcode v) {
        return (this.code.equals(v.getCode()) && this.startTime == v.getStartTime() && (this.durationInMinutes == v.getDurationInMinutes()));
    }
        
}
