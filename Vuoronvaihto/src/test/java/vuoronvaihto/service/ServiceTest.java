/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.service;

import java.io.File;
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
    private MockService mockService;
    
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
        mockService.initializeDatabase();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void tarkistaLepoAikaToiminto1() {
        UserObject k = daoService.getOrCreateUser("ansponhed");
        Shiftcode koodi_AA1400 = daoService.getOrCreateShiftcode(new Shiftcode("AA1400","14:00",7));
        Shiftcode koodi_AA0545 = daoService.getOrCreateShiftcode(new Shiftcode("AA0545","05:45",7));
        Shift vuoro = shiftRepository.save(new Shift(koodi_AA1400,"2020-03-30",k));
        Shift vuoroB = shiftRepository.save(new Shift(koodi_AA0545,"2020-03-31",k));
        assertEquals(true,Contract.checkRestTime(vuoro, vuoroB));
    }
    
    @Test
    public void tarkistaLepoAikaToiminto2() {
        UserObject k = daoService.getOrCreateUser("ansponhed");
        Shiftcode koodi_AA1800 = daoService.getOrCreateShiftcode(new Shiftcode("AA1800","18:00",7));
        Shiftcode koodi_AA0545 = daoService.getOrCreateShiftcode(new Shiftcode("AA0545","05:45",7));
        Shift vuoroA = shiftRepository.save(new Shift(koodi_AA1800,"2020-03-30",k));
        Shift vuoroB = shiftRepository.save(new Shift(koodi_AA0545,"2020-03-31",k));
        assertEquals(false,Contract.checkRestTime(vuoroA, vuoroB));
    }
    
    
    @Test
    public void laskeKayttajat() {
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
    public void laskeVuorokoodit() {
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
    public void laskeVuorot() {
        long c = 0;
        try (Scanner tiedostonLukija = new Scanner(new File("data/vuorot.csv"))) {
            while (tiedostonLukija.hasNextLine()) {                
                String rivi = tiedostonLukija.nextLine();
                c++;
            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        long count = shiftRepository.count();
        assertEquals(true,(c == count));
    }
    
    @Test
    public void naytaVuorotTest() {        
        mockService.naytaTallennetutVuorot();
        mockService.naytaKayttajat();
        mockService.naytaVuorokoodit();
        mockService.initializeDatabase();
        mockService.naytaTallennetutVuorot();
        mockService.naytaKayttajat();
        mockService.naytaVuorokoodit();
        assertFalse(false);
    }
    
    @Test
    public void loginTest() {
        daoService.getOrCreateUser("test");
        assertTrue(daoService.login("test"));        
    }
    
    @Test
    public void logoutTest() {
        daoService.login("kayttaja1");
        daoService.logout();
        assertTrue(daoService.getCurrentUser() == null);
    }
    
    @Test
    public void testApplicableShifts() {
        UserObject u = userRepository.findByHandle("kayttaja1").get(0);
        Shift s = shiftRepository.findByWorker(u).get(1);
        List<Shift> l = daoService.getApplicableShifts(s);
        assertTrue(l.size() != 0);
    }
    
    
}
