/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuoronvaihto.domain.Shift;
import vuoronvaihto.domain.Proposal;
import vuoronvaihto.domain.UserObject;

/**
 * JPA-rajapinta Proposal-luokalle.
 * @author pontus
 */
@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
        
    /**
     * Spring JPA-automatisoitu tietokantahaku.
     * SQL: SELECT * FROM ProposalRepository WHERE replacingWorker=u
     * @param u Käyttäjä
     * @return Lista vuoronvaihtoehdotuksista
     */
    public List<Proposal> findByReplacingWorker(UserObject u);
    
    /**
     * Spring JPA-automatisoitu tietokantahaku.
     * SQL: SELECT * FROM ProposalRepository WHERE shift=s
     * @param s Vuoro
     * @return Lista vuoronvaihtoehdotuksista
     */
    public List<Proposal> findByShift(Shift s);
    
    /**
     * Spring JPA-automatisoitu tietokantahaku.
     * SQL: SELECT * FROM ProposalRepository WHERE replacingWorker=u AND shift=s LIMIT 1
     * @param u Uusi työntekijä
     * @param s Vuoro
     * @return Lista vuoronvaihtoehdotuksista
     */
    public Proposal findOneByReplacingWorkerAndShift(UserObject u, Shift s);
            
    /**
     * Spring JPA-automatisoitu tietokantakomento.
     * SQL: DELETE FROM ProposalRepository WHERE shift=s AND replacingWorker = u
     * @param s Vuoro
     * @param u Nykyinen työntekijä
     */  
    public void deleteByShiftAndReplacingWorker(Shift s, UserObject u);
    
    /**
     * Spring JPA-automated database method.
     * SQL: DELETE FROM Proposal WHERE shift=s
     * @param s Shift      
     */
    public void deleteByShift(Shift s);
}
