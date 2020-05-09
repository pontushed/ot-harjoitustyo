/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import vuoronvaihto.domain.UserObject;
import vuoronvaihto.domain.Contract;
import vuoronvaihto.domain.Shift;
import vuoronvaihto.domain.Shiftcode;
import vuoronvaihto.dao.UserObjectRepository;
import vuoronvaihto.dao.ShiftRepository;
import vuoronvaihto.dao.ShiftCodeRepository;

/**
 *
 * @author pontus
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("vuoronvaihto.service")
public class ServiceTest {
    
    @Autowired
    private UserObjectRepository userRepository;
    
    @Autowired
    private ShiftCodeRepository shiftcodeRepository;
    
    @Autowired
    private ShiftRepository shiftRepository;
    
    @Autowired
    private DaoService daoService;
    
    @Autowired
    private UtilityService utilService;
    
    public ServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        utilService.initializeDatabase();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void checkRestTimeTest1() {
        UserObject k = daoService.getOrCreateUser("ansponhed");
        Shiftcode koodi_AA1400 = daoService.getOrCreateShiftcode(new Shiftcode("AA1400","14:00",7));
        Shiftcode koodi_AA0545 = daoService.getOrCreateShiftcode(new Shiftcode("AA0545","05:45",7));
        Shift vuoro = shiftRepository.save(new Shift(koodi_AA1400,"2020-03-30",k));
        Shift vuoroB = shiftRepository.save(new Shift(koodi_AA0545,"2020-03-31",k));
        assertEquals(true,Contract.checkRestTime(vuoro, vuoroB));
    }
    
    @Test
    public void checkRestTimeTest2() {
        UserObject k = daoService.getOrCreateUser("ansponhed");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800","18:00",7));
        Shiftcode koodi_AA0545 = daoService.getOrCreateShiftcode(new Shiftcode("AA0545","05:45",7));
        Shift vuoroA = shiftRepository.save(new Shift(koodi_AA1800,"2020-03-30",k));
        Shift vuoroB = shiftRepository.save(new Shift(koodi_AA0545,"2020-03-31",k));
        assertEquals(false,Contract.checkRestTime(vuoroA, vuoroB));
    }
    
    
    @Test
    public void countWorkersAfterImportTest() {
        long c = 0;
        try (Scanner tiedostonLukija = new Scanner(new File("data/kayttajat.csv"))) {
            while (tiedostonLukija.hasNextLine()) {                
                String rivi = tiedostonLukija.nextLine();
                c++;
            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        long count = userRepository.count();
        assertEquals(true,(c == count));
    }
    
    @Test
    public void countShiftCodesAfterImportTest() {
        long c = 0;
        try (Scanner tiedostonLukija = new Scanner(new File("data/vuorokoodit.csv"))) {
            while (tiedostonLukija.hasNextLine()) {                
                String rivi = tiedostonLukija.nextLine();
                c++;
            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        long count = shiftcodeRepository.count();
        assertEquals(true,(c == count));
    }        
            
    @Test
    public void loginTest() {
        daoService.getOrCreateUser("test");
        assertTrue(daoService.login("test"));        
    }
    
    @Test
    public void loginTest2() {
        assertFalse(daoService.login("notfound"));
    }
    
    @Test
    public void getOrCreateTest() {
        UserObject a = daoService.getOrCreateUser("test");
        UserObject b = daoService.getOrCreateUser("test");
        assertEquals(a,b);
    }
    
    @Test
    public void logoutTest() {
        daoService.login("kayttaja1");
        daoService.logout();
        assertTrue(daoService.getCurrentUser() == null);
    }
    
    @Test
    public void shiftCodeTest() {
        Shiftcode a = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        Shiftcode b = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        assertTrue(a.equals(b));
    }
    
    //@Test
    public void testApplicableShifts() {
        UserObject u = userRepository.findByHandle("kayttaja1").get(0);
        Shift s = shiftRepository.findByWorker(u).get(1);
        assertNotNull(s);
        List<Shift> l = daoService.getApplicableShifts(s);
        assertTrue(l.size() != 0);
    }
       
    //@Test
    public void testApplicableShifts2() {
        UserObject a = daoService.getOrCreateUser("test2");
        UserObject b = daoService.getOrCreateUser("test3");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        Shiftcode koodi_AA0545 = daoService.getOrCreateShiftcode(new Shiftcode("AA0545", "05:45", 7));
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));
        Shiftcode koodi_AA1400 = daoService.getOrCreateShiftcode(new Shiftcode("AA1400", "14:00", 7));
        Shift b1 = shiftRepository.save(new Shift(koodi_AA1800, "2020-02-01", b));
        Shift b2 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-02", b));
        Shift b3 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-03", b));
        Shift a1 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-01", a));
        Shift a2 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-02", a));
        Shift a3 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-03", a));
        assertNotNull(a2);
        List<Shift> l = daoService.getApplicableShifts(a2);
        if (!l.isEmpty()) {
            fail("Shift list should be empty. Now it contains: " + l);
        }
    }
    
    //@Test
    public void testApplicableShifts3() {
        UserObject a = daoService.getOrCreateUser("test2");
        UserObject b = daoService.getOrCreateUser("test3");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        Shiftcode koodi_AA0545 = daoService.getOrCreateShiftcode(new Shiftcode("AA0545", "05:45", 7));
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));
        Shiftcode koodi_AA1400 = daoService.getOrCreateShiftcode(new Shiftcode("AA1400", "14:00", 7));
        Shift b1 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-01", b));
        Shift b2 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-02", b));
        Shift b3 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-03", b));
        Shift a1 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-01", a));
        Shift a2 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-02", a));
        Shift a3 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-03", a));
        assertNotNull(a2);
        List<Shift> l = daoService.getApplicableShifts(a2);
        if (!l.contains(b2) || l.size() != 1) {
            fail("Shift list should contain " + b2 + ". Now it contains: " + l);
        }
    }
    
    //@Test
    public void testApplicableShifts4() {
        UserObject a = daoService.getOrCreateUser("test2");
        UserObject b = daoService.getOrCreateUser("test3");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        Shiftcode koodi_AA0545 = daoService.getOrCreateShiftcode(new Shiftcode("AA0545", "05:45", 7));
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));
        Shiftcode koodi_AA1400 = daoService.getOrCreateShiftcode(new Shiftcode("AA1400", "14:00", 7));
        Shift b1 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-01", b));
        Shift b2 = shiftRepository.save(new Shift(koodi_AA1800, "2020-02-02", b));
        Shift b3 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-03", b));
        Shift a1 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-01", a));
        Shift a2 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-02", a));
        Shift a3 = shiftRepository.save(new Shift(koodi_AA0545, "2020-02-03", a));
        assertNotNull(a2);
        List<Shift> l = daoService.getApplicableShifts(a2);
        if (!l.isEmpty()) {
            fail("Shift list should be empty. Now it contains: " + l);
        }
    }
    
    //@Test
    public void testApplicableShifts5() {
        UserObject a = daoService.getOrCreateUser("test2");
        UserObject b = daoService.getOrCreateUser("test3");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        Shiftcode koodi_AA0545 = daoService.getOrCreateShiftcode(new Shiftcode("AA0545", "05:45", 7));
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));
        Shiftcode koodi_AA1400 = daoService.getOrCreateShiftcode(new Shiftcode("AA1400", "14:00", 7));        
        Shift b2 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-02", b));
        Shift b3 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-03", b));
        Shift a1 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-01", a));
        Shift a2 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-02", a));
        Shift a3 = shiftRepository.save(new Shift(koodi_AA0545, "2020-02-03", a));
        assertNotNull(b2);
        List<Shift> l = daoService.getApplicableShifts(b2);
        if (!l.contains(a2)) {
            fail("Shift list should contain " + a2 + ". Now it contains: " + l);
        }
    }    
    
    @Test
    public void addProposalTest1() {
        UserObject a = daoService.getOrCreateUser("test2");
        UserObject b = daoService.getOrCreateUser("test3");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        Shiftcode koodi_AA0545 = daoService.getOrCreateShiftcode(new Shiftcode("AA0545", "05:45", 7));
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));
        Shiftcode koodi_AA1400 = daoService.getOrCreateShiftcode(new Shiftcode("AA1400", "14:00", 7));        
        Shift b2 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-02", b));
        Shift b3 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-03", b));
        Shift a1 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-01", a));
        Shift a2 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-02", a));
        Shift a3 = shiftRepository.save(new Shift(koodi_AA0545, "2020-02-03", a));
        daoService.addProposal(a2, b2);
        daoService.swap(a2,b2);
        assertTrue(a2.getWorker().equals(b) && b2.getWorker().equals(a));
    }
    
    @Test
    public void addProposalTest2() {
        UserObject a = daoService.getOrCreateUser("test2");
        UserObject b = daoService.getOrCreateUser("test3");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        Shiftcode koodi_AA0545 = daoService.getOrCreateShiftcode(new Shiftcode("AA0545", "05:45", 7));
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));
        Shiftcode koodi_AA1400 = daoService.getOrCreateShiftcode(new Shiftcode("AA1400", "14:00", 7));        
        Shift b2 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-02", b));
        Shift b3 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-03", b));
        Shift a1 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-01", a));
        Shift a2 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-02", a));
        Shift a3 = shiftRepository.save(new Shift(koodi_AA0545, "2020-02-03", a));
        daoService.addProposal(a2, b2);
        daoService.addProposal(a2, b2);
        List<Shift> props = daoService.getProposals(a2);
        assertTrue(props.size() == 1);
    }
    
    @Test
    public void addProposalTest3() {
        UserObject a = daoService.getOrCreateUser("test2");
        UserObject b = daoService.getOrCreateUser("test3");
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));
        Shiftcode noWork = daoService.getFreeShift();        
        Shift a1 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-01", a));
        Shift b1 = shiftRepository.save(new Shift(noWork, "2020-02-02", b));        
        daoService.addProposal(a1, b1);        
        List<Shift> props = daoService.getProposals(a1);
        assertTrue(props.size() == 1);
    }
    
    @Test
    public void addProposalTest4() {
        UserObject a = daoService.getOrCreateUser("test2");
        UserObject b = daoService.getOrCreateUser("test3");
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));
        Shiftcode noWork = daoService.getFreeShift();        
        Shift a1 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-01", a));
        Shift b1 = shiftRepository.save(new Shift(noWork, "2020-02-02", b));        
        daoService.addProposal(b1, a1);        
        List<Shift> props = daoService.getProposals(b1);
        assertTrue(props.size() == 1);
    }
    
    @Test
    public void swapTest1() {
        UserObject a = daoService.getOrCreateUser("test2");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        Shift a1 = shiftRepository.save(new Shift(koodi_AA1800, "2020-02-01", a));
        assertFalse(daoService.swap(a1, a1));
    }
    
    @Test
    public void swapTest2() {
        UserObject a = daoService.getOrCreateUser("test2");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        Shift a1 = shiftRepository.save(new Shift(koodi_AA1800, "2020-02-01", a));
        assertFalse(daoService.swap(new Shift(koodi_AA1800, "2020-02-01", a), a1));
    }
    
    @Test
    public void swapTest3() {
        UserObject a = daoService.getOrCreateUser("test2");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        Shift a1 = shiftRepository.save(new Shift(koodi_AA1800, "2020-02-01", a));
        assertTrue(daoService.swap(new Shift(koodi_AA1800, "2020-02-02", a), a1));
    }
    
    @Test
    public void swapTest4() {
        UserObject a = daoService.getOrCreateUser("test2");
        Shiftcode noWork = shiftcodeRepository.findByCode("Vapaa");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        Shift a1 = shiftRepository.save(new Shift(koodi_AA1800, "2020-02-01", a));
        assertTrue(daoService.swap(new Shift(noWork, "2020-02-02", a), a1));
    }
    
    @Test
    public void swapTest5() {        
        UserObject a = daoService.getOrCreateUser("test2");
        Shiftcode noWork = shiftcodeRepository.findByCode("Vapaa");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        Shift a1 = shiftRepository.save(new Shift(koodi_AA1800, "2020-02-01", a));
        assertTrue(daoService.swap(a1, new Shift(noWork, "2020-02-02", a)));
    }
    
    @Test
    public void deleteProposalTest1() {
        UserObject a = daoService.getOrCreateUser("test2");
        UserObject b = daoService.getOrCreateUser("test3");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800", "18:00", 7));
        Shiftcode koodi_AA0545 = daoService.getOrCreateShiftcode(new Shiftcode("AA0545", "05:45", 7));
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));
        Shiftcode koodi_AA1400 = daoService.getOrCreateShiftcode(new Shiftcode("AA1400", "14:00", 7));        
        Shift b2 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-02", b));
        Shift b3 = shiftRepository.save(new Shift(koodi_AA1400, "2020-02-03", b));
        Shift a1 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-01", a));
        Shift a2 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-02", a));
        Shift a3 = shiftRepository.save(new Shift(koodi_AA0545, "2020-02-03", a));
        daoService.addProposal(a2, b2);
        if (daoService.getProposals(a2).size() != 1) {
            fail("incorrect number of proposals");
        }
        daoService.deleteProposal(a2, b2);
        assertTrue(daoService.getProposals(a2).isEmpty());
    }
    
    @Test
    public void scheduleTest() {
        UserObject a = daoService.getOrCreateUser("test2");
        Shiftcode koodi_AA0545 = daoService.getOrCreateShiftcode(new Shiftcode("AA0545", "05:45", 7));
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));       
        Shift a1 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-01", a));
        Shift a2 = shiftRepository.save(new Shift(koodi_AA0700, "2020-02-02", a));
        Shift a3 = shiftRepository.save(new Shift(koodi_AA0545, "2020-02-03", a));
        Shift a4 = shiftRepository.save(new Shift(koodi_AA0545, "2020-02-04", a));
        List<Shift> shifts = daoService.getSchedule(LocalDate.parse("2020-02-01"), LocalDate.parse("2020-02-04"), a);
        assertTrue(shifts.size() == 4);
    }
    
    @Test    
    public void inboundProposalTest1() {
        String tomorrow = LocalDate.now().plusDays(3).toString();
        UserObject a = daoService.getOrCreateUser("test2");
        UserObject b = daoService.getOrCreateUser("test3");
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));
        Shiftcode koodi_AA1400 = daoService.getOrCreateShiftcode(new Shiftcode("AA1400", "14:00", 7));
        Shift b2 = new Shift(koodi_AA1400, tomorrow, b);
        Shift a2 = new Shift(koodi_AA0700, tomorrow, a);
        a2 = shiftRepository.save(a2);
        b2 = shiftRepository.save(b2);
        HashMap<Shift,List<Shift>> props = daoService.getInboundProposals(a);
        assertTrue(props.isEmpty());      
    }
    
    @Test    
    public void inboundProposalTest2() {
        String tomorrow = LocalDate.now().plusDays(3).toString();
        UserObject a = daoService.getOrCreateUser("test2");
        UserObject b = daoService.getOrCreateUser("test3");
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));
        Shiftcode koodi_AA1400 = daoService.getOrCreateShiftcode(new Shiftcode("AA1400", "14:00", 7));
        Shift b2 = new Shift(koodi_AA1400, tomorrow, b);
        Shift a2 = new Shift(koodi_AA0700, tomorrow, a);
        a2 = shiftRepository.save(a2);
        b2 = shiftRepository.save(b2);
        daoService.addProposal(a2, b2);
        HashMap<Shift,List<Shift>> props = daoService.getInboundProposals(b);
        assertTrue(props.isEmpty());      
    }
    
    @Test
    public void outboundProposalTest1() {
        String tomorrow = LocalDate.now().plusDays(3).toString();
        UserObject a = daoService.getOrCreateUser("test2");
        UserObject b = daoService.getOrCreateUser("test3");
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));
        Shiftcode koodi_AA1400 = daoService.getOrCreateShiftcode(new Shiftcode("AA1400", "14:00", 7));        
        Shift b2 = new Shift(koodi_AA1400, tomorrow, b);
        Shift a2 = new Shift(koodi_AA0700, tomorrow, a);
        a2 = shiftRepository.save(a2);
        b2 = shiftRepository.save(b2);
        daoService.addProposal(a2, b2);
        HashMap<Shift,List<Shift>> props = daoService.getOutboundProposals(a);
        if (props.isEmpty() || !props.get(a2).contains(b2)) {
            fail(props.toString());
        }
    }
    
    @Test
    public void outboundProposalTest2() {
        String tomorrow = LocalDate.now().plusDays(3).toString();
        UserObject a = daoService.getOrCreateUser("test2");
        UserObject b = daoService.getOrCreateUser("test3");
        Shiftcode koodi_AA0700 = daoService.getOrCreateShiftcode(new Shiftcode("AA0700", "07:00", 7));
        Shiftcode koodi_AA1400 = daoService.getOrCreateShiftcode(new Shiftcode("AA1400", "14:00", 7));        
        Shift b2 = new Shift(koodi_AA1400, tomorrow, b);
        Shift a2 = new Shift(koodi_AA0700, tomorrow, a);
        a2 = shiftRepository.save(a2);
        b2 = shiftRepository.save(b2);
        daoService.addProposal(a2, b2);
        daoService.deleteProposal(a2, b2);
        HashMap<Shift,List<Shift>> props = daoService.getOutboundProposals(a);
        assertTrue(props.isEmpty());
    }
        
    @Test
    public void getFreeShiftTest() {
        assertTrue(daoService.getFreeShift().equals(new Shiftcode("Vapaa", "12:00", 0)));
    }        
}
