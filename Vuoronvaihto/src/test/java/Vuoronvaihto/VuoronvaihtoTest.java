/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vuoronvaihto;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vuoronvaihto.logiikka.Kayttaja;
import vuoronvaihto.logiikka.Tes;
import vuoronvaihto.logiikka.Vuoro;
import vuoronvaihto.logiikka.Vuorokoodi;

/**
 *
 * @author pontus
 */
public class VuoronvaihtoTest {
    
    public VuoronvaihtoTest() {
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

    @Test
    public void tarkistaLepoAikaToiminto1() {
        Kayttaja k = new Kayttaja(1,"ansponhed");
        Vuorokoodi koodi_AA1400 = new Vuorokoodi("AA1400","14:00",7);
        Vuorokoodi koodi_AA0545 = new Vuorokoodi("AA0545","05:45",7);
        Vuoro vuoro = new Vuoro(koodi_AA1400,"2020-03-30",k);
        Vuoro vuoroB = new Vuoro(koodi_AA0545,"2020-03-31",k);
        assertEquals(true,Tes.tarkistaLepoaika(vuoro, vuoroB));
    }
    
    @Test
    public void tarkistaLepoAikaToiminto2() {
        Kayttaja k = new Kayttaja(1,"ansponhed");
        Vuorokoodi koodi_AA1800 = new Vuorokoodi("AA1800","18:00",7);
        Vuorokoodi koodi_AA0545 = new Vuorokoodi("AA0545","05:45",7);
        Vuoro vuoroA = new Vuoro(koodi_AA1800,"2020-03-30",k);
        Vuoro vuoroB = new Vuoro(koodi_AA0545,"2020-03-31",k);
        assertEquals(false,Tes.tarkistaLepoaika(vuoroA, vuoroB));
    }
}
