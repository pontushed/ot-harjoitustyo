/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.domain;

/**
 * Shift-olion toteuttava luokka.
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
public class Shift extends AbstractPersistable<Long> {
    
    @ManyToOne
    private Shiftcode shiftCode;
    private LocalDate dateOfShift;
    
    @OneToOne
    private UserObject worker;

    /**
     * Konstruktori.
     * @param shiftCode vuoron koodi, sisältää alkamisajan ja keston.
     * @param dateOfShift Vuoron päivämäärä muodossa YYYY-MM-DD
     * @param worker Kayttaja-olio
     */
    public Shift(Shiftcode shiftCode, String dateOfShift, UserObject worker) {
        this.shiftCode = shiftCode;
        this.dateOfShift = LocalDate.parse(dateOfShift);
        this.worker = worker;
    }

    public LocalDateTime getStartTime() {
        return LocalDateTime.of(this.dateOfShift, this.shiftCode.getStartTime());
    }
    
    public LocalDateTime getFinishTime() {        
        return this.shiftCode.getFinishTime(dateOfShift);
    }

    @Override
    public String toString() {
        return "Vuoro: vuorokoodi=" + shiftCode.getCode() + ", paiva=" + dateOfShift.toString() + ", tekija=" + worker.getHandle();
    }
    
    /**
     * equals-metodi luokalle.
     * @param v vuoro, johon verrataan
     * @return true Mikäli sama
     */
    public boolean equals(Shift v) {
        return (v.getStartTime().equals(this.getStartTime()))
                && (v.getWorker().equals(this.worker))
                && (v.shiftCode.equals(this.shiftCode));
    }
                
}
