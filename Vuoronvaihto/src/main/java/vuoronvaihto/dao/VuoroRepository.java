/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.dao;

/**
 *
 * @author pontus
 */
import vuoronvaihto.domain.Vuoro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VuoroRepository extends JpaRepository<Vuoro, Long> {
    
}
