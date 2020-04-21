/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.domain;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author pontus
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@ComponentScan("vuoronvaihto.service")
public class DomainTest {
        
    public DomainTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
        
    /**
     * Testaa UserObject-luokka.
     */
    
    @Test
    public void kayttajaLuokkaTest() {
        UserObject u = new UserObject("kayttaja");
        assertEquals(true,u.getHandle().equals("kayttaja"));
        assertEquals(true,u.toString().equals("kayttaja"));
        u.setHandle("user");
        assertEquals(true,u.getHandle().equals("user"));
        assertEquals(true,u.toString().equals("user"));
        assertEquals(true,u.equals(new UserObject("user")));
    }
    
    /**
     * Testaa Shiftcode-luokka.
     */
    
    @Test
    public void vuorokoodiLuokkaTest() {
        Shiftcode v = new Shiftcode("AA0600","06:00",7);
        assertEquals(true,v.getStartTime().toString().equals("06:00"));
        assertEquals(true,v.getDurationInMinutes() == 420);
        assertEquals(true,v.getCode().equals("AA0600"));
        assertEquals(true,v.equals(new Shiftcode("AA0600","06:00",7)));
    }
    
    /**
     * Testaa Shift-luokka.
     */
    @Test
    public void vuoroLuokkaTest() {
        Shiftcode vk = new Shiftcode("AA0600","06:00",7);
        UserObject k = new UserObject("kayttaja");
        Shift v = new Shift(vk,"2020-04-01",k);
        assertEquals(true,v.getWorker().equals(k));
        assertEquals(true,v.getShiftCode().equals(vk));
        assertEquals(true,v.equals(new Shift(vk,"2020-04-01",k)));
    }
    
    /**
     * Testaa Contract-luokka. (TES)
     */
    
    @Test
    public void tarkistaTESLuokka() {
        Contract obj = new Contract();
        assertEquals(true,obj.toString().equals("TES-luokka"));
    }
    
    @Test
    public void tarkistaTESLepoAika1() {
        UserObject k = new UserObject("kayttaja1");
        Shiftcode vk1 = new Shiftcode("AA1400","14:00",7);
        Shiftcode vk2 = new Shiftcode("AA0545","05:45",7);
        Shift vuoro1 = new Shift(vk1,"2020-03-30",k);
        Shift vuoro2 = new Shift(vk2,"2020-03-31",k);
        assertEquals(true,Contract.checkRestTime(vuoro1, vuoro2));
    }
    
    @Test
    public void tarkistaTESLepoAika2() {
        UserObject k = new UserObject("kayttaja1");
        Shiftcode vk1 = new Shiftcode("AA1800","18:00",7);
        Shiftcode vk2 = new Shiftcode("AA0545","05:45",7);
        Shift vuoro1 = new Shift(vk1,"2020-03-30",k);
        Shift vuoro2 = new Shift(vk2,"2020-03-31",k);
        assertEquals(false,Contract.checkRestTime(vuoro1, vuoro2));
    }
    
    @Test 
    public void tarkistaTESViikkoLepo1() {
        ArrayList<Shift> vuorot = new ArrayList<>();
        UserObject k = new UserObject("kayttaja1");
        Shiftcode vk1 = new Shiftcode("AA1400","14:00",7);
        vuorot.add(new Shift(vk1,"2020-04-01",k));
        vuorot.add(new Shift(vk1,"2020-04-02",k));
        vuorot.add(new Shift(vk1,"2020-04-03",k));
        vuorot.add(new Shift(vk1,"2020-04-04",k));
        vuorot.add(new Shift(vk1,"2020-04-05",k));
        vuorot.add(new Shift(vk1,"2020-04-06",k));
        vuorot.add(new Shift(vk1,"2020-04-07",k));
        vuorot.add(new Shift(vk1,"2020-04-08",k));
        assertEquals(false,Contract.checkWeeklyRestTime(vuorot));
    }
    
    @Test
    public void tarkistaTESViikkoLepo2() {
        ArrayList<Shift> vuorot = new ArrayList<>();
        UserObject k = new UserObject("kayttaja1");
        Shiftcode vk1 = new Shiftcode("AA1400","14:00",7);
        vuorot.add(new Shift(vk1,"2020-04-01",k));
        vuorot.add(new Shift(vk1,"2020-04-02",k));
        vuorot.add(new Shift(vk1,"2020-04-03",k));
        vuorot.add(new Shift(vk1,"2020-04-04",k));
        vuorot.add(new Shift(vk1,"2020-04-05",k));
        vuorot.add(new Shift(vk1,"2020-04-07",k));
        vuorot.add(new Shift(vk1,"2020-04-08",k));
        assertEquals(true,Contract.checkWeeklyRestTime(vuorot));
    }
    
    @Test
    public void tarkistaTESViikkoLepo3() {
        ArrayList<Shift> vuorot = new ArrayList<>();
        assertEquals(true,Contract.checkWeeklyRestTime(vuorot));
    }
        
}
