/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.dao;

/**
 * JPA-interface for Shiftcode class.
 * @author pontus
 */
import java.time.LocalTime;
import java.util.List;
import vuoronvaihto.domain.Shiftcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftCodeRepository extends JpaRepository<Shiftcode, Long> {
    
    /**
     * Find shiftcode entity by code string.
     * @param code The code string.
     * @return Shiftcode, where the shiftcode=code.
     */
    public Shiftcode findByCode(String code);
    
    /**
     * Find shiftcodes by start time.
     * @param startTime Starting time of the shift.
     * @return List of shiftcodes that represent the startTime.
     */
    public List<Shiftcode> findByStartTime(LocalTime startTime);
}
