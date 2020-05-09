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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Shift extends AbstractPersistable<Long> {
        
    @ManyToOne
    @Getter
    private Shiftcode shiftCode;
    
    @Getter
    private LocalDate dateOfShift;
    
    @OneToOne
    @Getter
    private UserObject worker;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "PROPOSAL",
        joinColumns =
            @JoinColumn(name = "SHIFT_ID", referencedColumnName = "ID"),
        inverseJoinColumns =
            @JoinColumn(name = "REPLACING_SHIFT_ID", referencedColumnName = "ID")
        )
    List<Shift> proposals;
       
    @ManyToMany(mappedBy = "proposals", fetch = FetchType.EAGER, cascade = CascadeType.ALL)    
    List<Shift> proposed;
        
    /**
     * Constructor.
     * @param shiftCode shift code, including start time and duration.
     * @param dateOfShift The date in format YYYY-MM-DD
     * @param worker User object
     */
    public Shift(Shiftcode shiftCode, String dateOfShift, UserObject worker) {
        this.shiftCode = shiftCode;
        this.dateOfShift = LocalDate.parse(dateOfShift);
        this.worker = worker;
        this.proposals = new ArrayList<>();
        this.proposed = new ArrayList<>();
    }
        
    public LocalDateTime getStartTime() {
        return LocalDateTime.of(this.dateOfShift, this.shiftCode.getStartTime());
    }
    
    public LocalDateTime getFinishTime() {        
        return this.shiftCode.getFinishTime(dateOfShift);
    }
    
    public String getTextCode() {
        return this.shiftCode.toString();
    }

    @Override
    public String toString() {
        return "Vuoro: vuorokoodi=" + shiftCode.getCode() + ", paiva=" + dateOfShift.toString() + ", tekija=" + worker.getHandle();
    }
    
    /**
     * equals-method for the class.
     * @param s shift to compare to.
     * @return true Mikäli sama
     */
    public boolean equals(Shift s) {
        return (s.getStartTime().equals(this.getStartTime()))
                && (s.getWorker().equals(this.worker))
                && (s.shiftCode.equals(this.shiftCode));
    }
              
}
