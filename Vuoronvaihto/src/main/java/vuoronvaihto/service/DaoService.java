/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    
    @Autowired
    private ProposalRepository proposalRepository;
    
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
    
    /** Get Shift schedule for a date range for a selected worker.
     * @param startDate starting date
     * @param endDate ending date
     * @param w Worker
     * @return List of dates and shifts. If there is no shift on a date, then there will a "free" shift
     */
    public List<Shift> getSchedule(LocalDate startDate, LocalDate endDate, UserObject w) {
        List<Shift> shifts = shiftRepository.findByWorkerAndDateOfShiftBetween(w, startDate, endDate);        
        return shifts;
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
        Shift prevShift = shiftRepository.getPrevShift(shiftDate, worker);
        Shift nextShift = shiftRepository.getNextShift(shiftDate, worker);        
        shiftRepository.findByDateOfShiftAndWorkerNot(shiftDate, worker)
                .stream()
                .filter((shift) -> (Contract.checkRestTimeBeforeAndAfter(prevShift, shift, nextShift)))
                .forEachOrdered((shift) -> {
                    approvedShifts.add(shift);
                });
        return approvedShifts.stream().filter((shift) -> {
            Shift prev = shiftRepository.getPrevShift(shiftDate, shift.getWorker());
            Shift next = shiftRepository.getNextShift(shiftDate, shift.getWorker());
            boolean reverseOK = Contract.checkRestTimeBeforeAndAfter(prev, s, next);                        
            return reverseOK;
        }).collect(Collectors.toList());
    }            

    /**
     * Add a new proposal.
     * @param origS Original shift
     * @param newS New shift
     */
    public void addProposal(Shift origS, Shift newS) {
        Proposal pOut = new Proposal(origS, newS.getWorker());
        proposalRepository.save(pOut);        
    }

    /**
     * Get proposals for a certain shift.
     * @param s Shift
     * @return List of Proposal objects
     */
    public List<Proposal> getProposals(Shift s) {
        return proposalRepository.findByShift(s);
    }
    
    /**
     * Get proposals for the replacing worker. Use this to find if there are proposals
     * for a certain shift.
     * @param u Worker concerned
     * @param s Shift of worker concerned
     * @return List of Proposal objects
     */
    public Proposal getProposalIn(UserObject u, Shift s) {
        return proposalRepository.findOneByReplacingWorkerAndShift(u, s);
    }
    
    /**
     * Delete proposals for a certain shift and user.
     * @param s Shift
     * @param u Worker
     */
    @Transactional
    public void deleteProposal(Shift s, UserObject u) {        
        proposalRepository.deleteByShiftAndReplacingWorker(s, u);
    }

    /**
     * Swap shifts and delete all proposals concerning these shifts.
     * @param a Shift A
     * @param b Shift B
     */
    @Transactional
    public void swap(Shift a, Shift b) {
        proposalRepository.deleteByShift(a);
        proposalRepository.deleteByShift(b);
        UserObject tmp = a.getWorker();
        a.setWorker(b.getWorker());
        b.setWorker(tmp);
        shiftRepository.save(a);
        shiftRepository.save(b);        
    }
}
