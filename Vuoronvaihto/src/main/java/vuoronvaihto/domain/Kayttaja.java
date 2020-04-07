/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author pontus
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Kayttaja extends AbstractPersistable<Long> {
    private String tunnus;    
}
