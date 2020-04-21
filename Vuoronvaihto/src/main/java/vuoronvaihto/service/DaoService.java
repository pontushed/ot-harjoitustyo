/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vuoronvaihto.domain.*;
import vuoronvaihto.dao.*;

/**
 * Aputoiminnot tietokantatoimintoihin.
 * @author pontus
 */
@Service
public class DaoService {
    
    @Autowired
    private UserObjectRepository userobjectRepository;
    
    @Autowired
    private ShiftCodeRepository shiftcodeRepository;
    
    @Autowired
    private ShiftRepository shiftRepository;
    
    private UserObject u;
    
    
    /**
     * Hae käyttäjä. Luo uusi käyttäjä jos käyttäjää ei löydy.
     * @param handle Käyttäjän tunnus.
     * @return UserObject Kayttaja-olio
     */
    public UserObject getOrCreateUser(String handle) {
        List<UserObject> users = userobjectRepository.findByHandle(handle);
        if (users.isEmpty()) {
            return userobjectRepository.save(new UserObject(handle));
        } else {
            return users.get(0);
        }        
    }
    
    /**
     * Hae vuorokoodi. Jos vuorokoodia ei löydy, luo uusi.
     * @param v Shiftcode-olio
     * @return Shiftcode-olio
     */
    public Shiftcode getOrCreateShiftcode(Shiftcode v) {
        if (shiftcodeRepository.findByCode(v.getCode()) == null) {
            shiftcodeRepository.save(v);
        }
        return v;        
    }

    /**
     * Try to log in.
     * @param handle The username.
     * @return true if log in ok, false if not.
     */
    public boolean login(String handle) {
        List<UserObject> users = userobjectRepository.findByHandle(handle);
        if (users.isEmpty()) {
            return false;
        }
        this.u = users.get(0);
        return true;
    }

    /**
     * Get the current logged in user.
     * @return UserObject current user, null if no user logged in.
     */
    public UserObject getCurrentUser() {
        return u;
    }
    
    /**
     * Log out the current user.
     */    
    public void logout() {
        this.u = null;
    }
    
    /**
     * Get all applicable shifts, taking into consideration the contract constraints.
     * Constraints : endTime &lt;= next shift for current worker - restTime
     *               startTime &gt;= previous shift for current worker + restTime
     * First examine the proposed shifts for the current worker, then reverse examine 
     * how the shift that is proposed by the worker is applicable for the others.
     * @param s shift to examine
     * @return List of shifts that are available for exchange
     */
    public List<Shift> getApplicableShifts(Shift s) {
        LocalDate shiftDate = s.getDateOfShift();
        UserObject worker = s.getWorker();
        List<Shift> approvedShifts = new ArrayList<>();
        Shift prevShift = shiftRepository.findFirstByDateOfShiftBeforeAndWorkerOrderByDateOfShiftDesc(shiftDate, worker);
        Shift nextShift = shiftRepository.findFirstByDateOfShiftAfterAndWorkerOrderByDateOfShiftAsc(shiftDate, worker);
        System.out.println(prevShift);
        System.out.println(nextShift);
        shiftRepository.findByDateOfShiftAndWorkerNot(shiftDate, worker)
                .stream()
                .filter((shift) -> (Contract.checkRestTimeBeforeAndAfter(prevShift, shift, nextShift)))
                .forEachOrdered((shift) -> {
                    approvedShifts.add(shift);
                });        
        return approvedShifts;
    }            
}
