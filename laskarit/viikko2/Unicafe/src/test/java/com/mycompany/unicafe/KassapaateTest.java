/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pontus
 */
public class KassapaateTest {
    
    Kassapaate kassa;
           
    @Before
    public void setUp() {
        kassa = new Kassapaate();
    }
    
    
    // luodun kassapäätteen rahamäärä ja myytyjen lounaiden määrä on oikea (rahaa 1000, lounaita myyty 0)
    @Test
    public void tarkistaEttaOletusArvotOK() {
        boolean kaikkiOk;
        kaikkiOk = (kassa.kassassaRahaa() == 100000 &&
                kassa.maukkaitaLounaitaMyyty() == 0 &&
                kassa.edullisiaLounaitaMyyty() == 0);
        assertEquals(true,kaikkiOk);
    }
    
    // käteisosto toimii sekä edullisten että maukkaiden lounaiden osalta
    
    // Edulliset:
    // jos maksu riittävä: kassassa oleva rahamäärä kasvaa lounaan hinnalla ja
    // vaihtorahan suuruus on oikea
    @Test
    public void kateisOstoToimii1() {
        int vaihtoraha = kassa.syoEdullisesti(300);
        assertEquals(true, (kassa.kassassaRahaa() == 100240 && vaihtoraha == 60));
    }
    
    // jos maksu on riittävä: myytyjen lounaiden määrä kasvaa
    @Test
    public void kateisOstoToimii2() {
        kassa.syoEdullisesti(240);
        assertEquals(1,kassa.edullisiaLounaitaMyyty());
    }
    // jos maksu ei ole riittävä: kassassa oleva rahamäärä ei muutu, kaikki rahat
    // palautetaan vaihtorahana ja myytyjen lounaiden määrässä ei muutosta
    @Test
    public void kateisOstoToimii3() {
        int vaihtoraha = kassa.syoEdullisesti(200);
        assertEquals(true,(kassa.kassassaRahaa() == 100000 && 
                vaihtoraha == 200 && 
                kassa.edullisiaLounaitaMyyty() == 0));
    }
    // Maukkaat:
    // jos maksu riittävä: kassassa oleva rahamäärä kasvaa lounaan hinnalla ja
    // vaihtorahan suuruus on oikea
    @Test
    public void kateisOstoToimii4() {
        int vaihtoraha = kassa.syoMaukkaasti(500);
        assertEquals(true, (kassa.kassassaRahaa() == 100400 && vaihtoraha == 100));
    }
    // jos maksu on riittävä: myytyjen lounaiden määrä kasvaa
    @Test
    public void kateisOstoToimii5() {
        kassa.syoMaukkaasti(400);
        assertEquals(1,kassa.maukkaitaLounaitaMyyty());
    }
    // jos maksu ei ole riittävä: kassassa oleva rahamäärä ei muutu, kaikki rahat
    // palautetaan vaihtorahana ja myytyjen lounaiden määrässä ei muutosta
    @Test
    public void kateisOstoToimii6() {
        int vaihtoraha = kassa.syoMaukkaasti(300);
        assertEquals(true,(kassa.kassassaRahaa() == 100000 && 
                vaihtoraha == 300 && 
                kassa.maukkaitaLounaitaMyyty() == 0));
    }
    // korttiosto toimii sekä edullisten että maukkaiden lounaiden osalta
    
    // Edulliset:
    //jos kortilla on tarpeeksi rahaa, veloitetaan summa kortilta ja palautetaan true
    @Test
    public void korttiOstoToimii1() {
        Maksukortti kortti = new Maksukortti(1000);
        boolean ostoOK = kassa.syoEdullisesti(kortti);
        assertEquals(true, (ostoOK && kortti.saldo() == 760));
    }
    //jos kortilla on tarpeeksi rahaa, myytyjen lounaiden määrä kasvaa
    @Test
    public void korttiOstoToimii2() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.syoEdullisesti(kortti);
        assertEquals(1,kassa.edullisiaLounaitaMyyty());
    }
    //jos kortilla ei ole tarpeeksi rahaa, kortin rahamäärä ei muutu, myytyjen     
    //lounaiden määrä muuttumaton ja palautetaan false
    @Test
    public void korttiOstoToimii3() {
        Maksukortti kortti = new Maksukortti(200);
        boolean ostoOK = kassa.syoEdullisesti(kortti);
        assertEquals(true, (!ostoOK && kortti.saldo() == 200));
    }
    //kassassa oleva rahamäärä ei muutu kortilla ostettaessa
    @Test
    public void korttiOstoToimii4() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.syoEdullisesti(kortti);
        assertEquals(100000,kassa.kassassaRahaa());
    }
    
    // Maukkaat:
    //jos kortilla on tarpeeksi rahaa, veloitetaan summa kortilta ja palautetaan true
    @Test
    public void korttiOstoToimii5() {
        Maksukortti kortti = new Maksukortti(1000);
        boolean ostoOK = kassa.syoMaukkaasti(kortti);
        assertEquals(true, (ostoOK && kortti.saldo() == 600));
    }
    //jos kortilla on tarpeeksi rahaa, myytyjen lounaiden määrä kasvaa
    @Test
    public void korttiOstoToimii6() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.syoMaukkaasti(kortti);
        assertEquals(1,kassa.maukkaitaLounaitaMyyty());
    }
    //jos kortilla ei ole tarpeeksi rahaa, kortin rahamäärä ei muutu, myytyjen
    // lounaiden määrä muuttumaton ja palautetaan false
    @Test
    public void korttiOstoToimii7() {
        Maksukortti kortti = new Maksukortti(200);
        boolean ostoOK = kassa.syoMaukkaasti(kortti);
        assertEquals(true, (!ostoOK && kortti.saldo() == 200));
    }
    //kassassa oleva rahamäärä ei muutu kortilla ostettaessa
    @Test
    public void korttiOstoToimii8() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000,kassa.kassassaRahaa());
    }
    //kortille rahaa ladattaessa kortin saldo muuttuu ja kassassa oleva rahamäärä 
    // kasvaa ladatulla summalla
    @Test
    public void kortilleLatausToimii() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.lataaRahaaKortille(kortti, 1000);
        assertEquals(101000,kassa.kassassaRahaa());
        assertEquals(2000,kortti.saldo());
    }
    // Kortille ei voi ladata negatiivista saldoa
    @Test
    public void kortilleEiNegatiivista() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.lataaRahaaKortille(kortti, -1000);
        assertEquals(100000,kassa.kassassaRahaa());
        assertEquals(1000,kortti.saldo());
    }    
}
