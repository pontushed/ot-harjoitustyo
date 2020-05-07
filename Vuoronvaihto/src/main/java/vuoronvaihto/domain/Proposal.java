/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Proposal-olion toteuttava luokka.
 * @author pontus
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Proposal {
            
    @Id
    @GeneratedValue
    private Long id;
    
    @OneToOne
    private Shift shift;
            
    @OneToOne
    private UserObject replacingWorker;

    /**
     * Proposal class constructor.
     * @param shift Shift object
     * @param replacingWorker The replacing worker for the shift
     */
    public Proposal(Shift shift, UserObject replacingWorker) {
        this.shift = shift;
        this.replacingWorker = replacingWorker;
    }
    
    @Override
    public String toString() {
        return this.replacingWorker.getHandle() + ": " + this.shift.toString();
    }
                
}
