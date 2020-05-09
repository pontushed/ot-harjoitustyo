/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Silly name for this test class, because NetBeans didn't like the name 'DomainTest'
 * @author pontus
 */

public class DomainiTest {
            
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
    
    @Test
    public void vuorokoodiLuokkaTest2() {
        Shiftcode a = new Shiftcode("Vapaa","06:00",7);
        assertEquals(true,a.toString().equals("Vapaa"));
        Shiftcode b = new Shiftcode("AA0600","06:00",7);
        assertEquals(true,b.toString().equals("AA0600 (06:00-13:00)"));        
    }
    
    @Test
    public void vuorokoodiLuokkaTest3() {
        Shiftcode a = new Shiftcode("Vapaa","06:00",7);        
        Shiftcode b = new Shiftcode("AA0600","07:00",4);
        assertFalse(a.equals(b));        
    }
    
    @Test
    public void vuorokoodiLuokkaTest4() {
        Shiftcode a = new Shiftcode("AA0600","06:00",7);        
        Shiftcode b = new Shiftcode("AA0600","07:00",7);
        assertFalse(a.equals(b));        
    }
    
    @Test
    public void vuorokoodiLuokkaTest5() {
        Shiftcode a = new Shiftcode("AA0600","06:00",7);        
        Shiftcode b = new Shiftcode("AA0700","06:00",7);
        assertFalse(a.equals(b));        
    }
    
    @Test
    public void vuorokoodiLuokkaTest6() {
        Shiftcode a = new Shiftcode("AA0600","06:00",7);        
        Shiftcode b = new Shiftcode("AA0600","06:00",8);
        assertFalse(a.equals(b));        
    }
    
    @Test
    public void vuorokoodiLuokkaTest7() {
        Shiftcode a = new Shiftcode("AA0600","06:00",7);        
        a.setCode("TA0600");
        assertTrue(a.getCode().equals("TA0600"));
        a.setDurationInMinutes(300);
        assertTrue(a.getDurationInMinutes() == 300);
        long d = 420;
        Shiftcode b = new Shiftcode("AA0700",LocalTime.parse("07:00"), d);
        assertTrue(b.getDurationInMinutes() == 420);
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
    public void tarkistaTESLepoAika3() {
        UserObject k = new UserObject("kayttaja1");
        Shiftcode vk1 = new Shiftcode("AA1800","18:00", 7);
        Shiftcode vk2 = new Shiftcode("Vapaa","12:00", 0);
        Shift vuoro1 = new Shift(vk1,"2020-03-30",k);
        Shift vuoro2 = new Shift(vk2,"2020-03-31",k);
        assertEquals(true,Contract.checkRestTime(vuoro1, vuoro2));
    }
    
    @Test
    public void checkRestTimeBeforeAndAfterTest1() {
        UserObject k = new UserObject("kayttaja1");
        Shiftcode vk1 = new Shiftcode("AA1800","18:00", 7);
        Shiftcode vk2 = new Shiftcode("Vapaa","12:00", 0);
        Shiftcode vk3 = new Shiftcode("AA0545","05:45",7);
        Shift vuoro1 = new Shift(vk1,"2020-03-29",k);
        Shift vuoro2 = new Shift(vk2,"2020-03-30",k);
        Shift vuoro3 = new Shift(vk3,"2020-03-31",k);
        assertTrue(Contract.checkRestTimeBeforeAndAfter(vuoro1, vuoro2, vuoro3));
    }
    
    @Test
    public void checkRestTimeBeforeAndAfterTest2() {
        UserObject k = new UserObject("kayttaja1");
        Shiftcode vk1 = new Shiftcode("AA1800","18:00", 7);
        Shiftcode vk2 = new Shiftcode("Vapaa","12:00", 0);
        Shiftcode vk3 = new Shiftcode("AA0545","05:45",7);
        Shift vuoro1 = new Shift(vk2,"2020-03-29",k);
        Shift vuoro2 = new Shift(vk1,"2020-03-30",k);
        Shift vuoro3 = new Shift(vk3,"2020-03-31",k);
        assertFalse(Contract.checkRestTimeBeforeAndAfter(vuoro1, vuoro2, vuoro3));    
    }
    
    @Test
    public void checkRestTimeBeforeAndAfterTest3() {
        UserObject k = new UserObject("kayttaja1");
        Shiftcode vk1 = new Shiftcode("AA1800","18:00", 7);
        Shiftcode vk2 = new Shiftcode("Vapaa","12:00", 0);
        Shiftcode vk3 = new Shiftcode("AA0545","05:45",7);
        Shift vuoro1 = new Shift(vk1,"2020-03-29",k);
        Shift vuoro2 = new Shift(vk3,"2020-03-30",k);
        Shift vuoro3 = new Shift(vk2,"2020-03-31",k);
        assertFalse(Contract.checkRestTimeBeforeAndAfter(vuoro1, vuoro2, vuoro3));
    }
    
    @Test
    public void checkRestTimeBeforeAndAfterTest4() {
        UserObject k = new UserObject("kayttaja1");
        Shiftcode vk3 = new Shiftcode("AA0545","05:45",7);
        Shift vuoro1 = new Shift(vk3,"2020-03-29",k);
        Shift vuoro2 = new Shift(vk3,"2020-03-30",k);
        Shift vuoro3 = new Shift(vk3,"2020-03-31",k);
        assertTrue(Contract.checkRestTimeBeforeAndAfter(vuoro1, vuoro2, null));
        assertTrue(Contract.checkRestTimeBeforeAndAfter(null, vuoro2, vuoro3));
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
