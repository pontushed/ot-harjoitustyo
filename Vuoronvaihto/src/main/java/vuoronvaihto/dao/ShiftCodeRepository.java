/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.dao;

/**
 * JPA-rajapinta Vuorokoodi-luokalle.
 * @author pontus
 */
import java.time.LocalTime;
import java.util.List;
import vuoronvaihto.domain.Shiftcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftCodeRepository extends JpaRepository<Shiftcode, Long> {
    
    /**
     * Etsi vuoro koodin perusteella. koodi on määritelty unique-parametrilla
     * tietokannassa.
     * @param code Haettu vuorokoodi
     * @return Shiftcode, jonka vuorokoodi=code.
     */
    public Shiftcode findByCode(String code);
    
    /**
     * Etsi vuoroja alkamisajan perusteella.
     * @param startTime Aika, jolloin vuoro alkaa.
     * @return Lista vuoroista, jotka alkavat haettuna aikana.
     */
    public List<Shiftcode> findByStartTime(LocalTime startTime);
}
