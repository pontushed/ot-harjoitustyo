/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.dao;

import java.time.LocalDate;
import java.util.List;
import vuoronvaihto.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import vuoronvaihto.domain.UserObject;

/**
 * JPA-interface for class Shift.
 * @author pontus
 */
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    
    /** Spring automated database method.
     * @param u Worker
     * @return List
     */
    public List<Shift> findByWorker(UserObject u);
    
    /**
     * Spring JPA-automatisoitu tietokantahaku.
     * SQL: SELECT * FROM ShiftRepository WHERE worker=u and dateOfShift AFTER d
     * @param u Käyttäjä
     * @param d LocalDate, should be now()
     * @return List
     */
    public List<Shift> findByWorkerAndDateOfShiftAfter(UserObject u, LocalDate d);
    
    /**
     * Spring JPA-automatisoitu tietokantahaku.
     * SQL: SELECT * FROM ShiftRepository WHERE worker=u AND dateOfShift BETWEEN a,b
     * @param u Käyttäjä
     * @param a Alkupäivämäärä, esim "2020-05-01"
     * @param b Loppupäivämäärä, esim "2020-05-05"
     * @return Lista vuoroista
     */
    public List<Shift> findByWorkerAndDateOfShiftBetween(UserObject u, LocalDate a, LocalDate b);
    
    
    /**
     * Spring JPA-automatisoitu tietokantahaku.
     * SQL: SELECT * FROM ShiftRepository WHERE dateOfShift=d AND worker=u
     * @param d Päivämäärä, esim. "2020-05-01". Huom. Tyyppi LocalDate
     * @param u Käyttäjä
     * @return Lista vuoroista
     */
    public List<Shift> findByDateOfShiftAndWorkerNot(LocalDate d, UserObject u);

    /**
     * Spring JPA-automatisoitu tietokantahaku.
     * SQL: SELECT * FROM ShiftRepository
     *      WHERE dateOfShift BEFORE shiftDate AND worker=u
     *      ORDER BY dateOfShift DESC LIMIT 1
     * @param shiftDate Päivämäärä, esim. "2020-05-01" Huom. Tyyppi LocalDate
     * @param worker Käyttäjä
     * @return Shift tai null
     */    
    public Shift findFirstByDateOfShiftBeforeAndWorkerOrderByDateOfShiftDesc(LocalDate shiftDate, UserObject worker);
    
    /**
     * Get previous shift for a worker.
     * @param shiftDate Date to examine, eg. "2020-05-01"
     * @param worker The worker
     * @return Shift or null
     */
    default public Shift getPrevShift(LocalDate shiftDate, UserObject worker) {
        return findFirstByDateOfShiftBeforeAndWorkerOrderByDateOfShiftDesc(shiftDate, worker);
    }

    /**
     * Spring JPA-automatisoitu tietokantahaku.
     * SQL: SELECT * FROM ShiftRepository
     *      WHERE dateOfShift AFTER shiftDate AND worker=u
     *      ORDER BY dateOfShift DESC LIMIT 1
     * @param shiftDate Päivämäärä, esim "2020-05-01" Huom. Tyyppi LocalDate
     * @param worker Käyttäjä
     * @return Shift tai null
     */
    public Shift findFirstByDateOfShiftAfterAndWorkerOrderByDateOfShiftAsc(LocalDate shiftDate, UserObject worker);
    
    /**
     * Get Next shift for a worker.
     * @param shiftDate Date to examine, eg. "2020-05-01"
     * @param worker The worker
     * @return Shift or null
     */
    default public Shift getNextShift(LocalDate shiftDate, UserObject worker) {
        return findFirstByDateOfShiftAfterAndWorkerOrderByDateOfShiftAsc(shiftDate, worker);
    }

    /**
     * Get a list of Shifts for a range of dates.
     * @param startDate range start
     * @param endDate range end
     * @return List of shifts
     */
    public List<Shift> findByDateOfShiftBetweenOrderByDateOfShift(LocalDate startDate, LocalDate endDate);
    /**
     * Get a list of Shifts for a range of dates.
     * @param startDate range start
     * @param endDate range end
     * @return List of shifts
     */
    default public List<Shift> getShiftsForRange(LocalDate startDate, LocalDate endDate) {
        return findByDateOfShiftBetweenOrderByDateOfShift(startDate, endDate);
    }
}
