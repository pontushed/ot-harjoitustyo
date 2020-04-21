/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.dao;

import java.util.List;
import vuoronvaihto.domain.UserObject;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA-rajapinta UserObject-luokalle.
 * @author pontus
 */
public interface UserObjectRepository extends JpaRepository<UserObject, Long> {
    
    /**
     * Hae käyttäjiä tunnuksen perusteella. Tunnus on määritelty Unique-parametrilla,
     * joten ota Kayttaja-olio talteen lisäämällä metodikutsun perään .get(0) .
     * @param handle Haettu käyttäjätunnus
     * @return Lista UserObject-olioista
     */
    public List<UserObject> findByHandle(String handle);
}
