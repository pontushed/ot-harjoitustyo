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
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vuoronvaihto.dao.KayttajaRepository;
import vuoronvaihto.dao.VuoroRepository;
import vuoronvaihto.dao.VuorokoodiRepository;
import vuoronvaihto.domain.Kayttaja;
import vuoronvaihto.domain.Tes;
import vuoronvaihto.domain.Vuoro;
import vuoronvaihto.domain.Vuorokoodi;

/**
 *
 * @author pontus
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class VuoronvaihtoTest {
    
    @Autowired
    private KayttajaRepository kayttajaRepository;
    
    @Autowired
    private VuorokoodiRepository vuorokoodiRepository;
    
    @Autowired
    private VuoroRepository vuoroRepository;
    
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
        Kayttaja k = kayttajaRepository.save(new Kayttaja("ansponhed"));
        Vuorokoodi koodi_AA1400 = vuorokoodiRepository.save(new Vuorokoodi("AA1400","14:00",7));
        Vuorokoodi koodi_AA0545 = vuorokoodiRepository.save(new Vuorokoodi("AA0545","05:45",7));
        Vuoro vuoro = vuoroRepository.save(new Vuoro(koodi_AA1400,"2020-03-30",k));
        Vuoro vuoroB = vuoroRepository.save(new Vuoro(koodi_AA0545,"2020-03-31",k));
        assertEquals(true,Tes.tarkistaLepoaika(vuoro, vuoroB));
    }
    
    @Test
    public void tarkistaLepoAikaToiminto2() {
        Kayttaja k = kayttajaRepository.save(new Kayttaja("ansponhed"));
        Vuorokoodi koodi_AA1800 = vuorokoodiRepository.save(new Vuorokoodi("AA1800","18:00",7));
        Vuorokoodi koodi_AA0545 = vuorokoodiRepository.save(new Vuorokoodi("AA0545","05:45",7));
        Vuoro vuoroA = vuoroRepository.save(new Vuoro(koodi_AA1800,"2020-03-30",k));
        Vuoro vuoroB = vuoroRepository.save(new Vuoro(koodi_AA0545,"2020-03-31",k));
        assertEquals(false,Tes.tarkistaLepoaika(vuoroA, vuoroB));
    }
}
