/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Käyttäjä-olion toteuttava luokka.
 * @author pontus
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserObject extends AbstractPersistable<Long> {
    
    @Column(unique = true)
    private String handle;

    @Override
    public String toString() {
        return this.handle;
    }

    /**
     * equals-metodi luokalle.
     * @param k kayttaja, johon verrataan
     * @return true, mikäli sama.
     */
    public boolean equals(UserObject k) {
        return handle.equals(k.getHandle());
    }        
}
