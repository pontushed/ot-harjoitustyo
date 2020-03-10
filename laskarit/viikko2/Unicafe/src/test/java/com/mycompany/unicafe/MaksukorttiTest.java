package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void lisaaRahaaToimiiOikein() {
        kortti.lataaRahaa(200);
        assertEquals("saldo: 12.0", kortti.toString());
    }
    
    //    rahan ottaminen toimii
    //        saldo vähenee oikein, jos rahaa on tarpeeksi
    //        saldo ei muutu, jos rahaa ei ole tarpeeksi
    //        metodi palauttaa true, jos rahat riittivät ja muuten false
    @Test
    public void josRahatRiittaaNiinSaldoVaheneeOikein() {
        kortti.otaRahaa(250);
        assertEquals("saldo: 7.50", kortti.toString());
    }
    
    @Test
    public void josRahatEiRiitaNiinSaldoEiMuutu() {
        kortti.otaRahaa(1200);
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void metodiPalauttaaOikein() {
        boolean palautus=kortti.otaRahaa(1000);
        assertEquals(true,palautus);
        palautus=kortti.otaRahaa(1000);
        assertEquals(false,palautus);
    }
    
    @Test
    public void testaaOnkoOikeaSaldo() {
        int saldo = kortti.saldo();
        assertEquals(1000,saldo);
    }
}
