/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vuoronvaihto.dao.KayttajaRepository;
import vuoronvaihto.dao.VuoroRepository;
import vuoronvaihto.dao.VuorokoodiRepository;
import vuoronvaihto.domain.Kayttaja;
/**
 *
 * @author pontus
 */

@Component
public class VuoronvaihtoApplication implements CommandLineRunner {
    
    @Autowired
    private KayttajaRepository kayttajaRepository;
    
    @Autowired
    private VuorokoodiRepository vuorokoodiRepository;
    
    @Autowired
    private VuoroRepository vuoroRepository;
    
    private void tervehdys() {
        System.out.println("Vuoronvaihtosovellus");
        System.out.println("--------------------");
        System.out.println("Ohjelmistotekniikka, kevät 2020.");
        System.out.println("Pontus Hedlund");
        System.out.println("Viikko 4. Tässä on vain tekstipohjainen käyttöliittymä tällä hetkellä.");
        System.out.println("- Ohjelma käyttää nyt Spring Boot-sovelluskehystä ja JPA:ta H2-tietokannalla.");
    }
    
    private void naytaTallennetutVuorot() {
        System.out.println("Tallennetut vuorot:");
        for (Vuoro v : vuoroRepository.findAll()) {
            System.out.println(v);
        }
    }
    
    public void run(String... args) throws Exception {
        tervehdys();

        Kayttaja k = kayttajaRepository.save(new Kayttaja("ansponhed"));        
        Vuorokoodi koodiAA1400 = vuorokoodiRepository.save(new Vuorokoodi("AA1400", "14:00", 7));
        Vuorokoodi koodiAA1800 = vuorokoodiRepository.save(new Vuorokoodi("AA1800", "18:00", 7));
        Vuorokoodi koodiAA0545 = vuorokoodiRepository.save(new Vuorokoodi("AA0545", "05:45", 7));
        Vuoro vuoro = vuoroRepository.save(new Vuoro(koodiAA1400, "2020-03-30", k));

        System.out.println(vuoro);
        System.out.println("Vuoro alkaa: " + vuoro.getAloitusAika().toString());
        System.out.println("Vuoro loppuu: " + vuoro.getLopetusAika().toString());

        Vuoro vuoroB = vuoroRepository.save(new Vuoro(koodiAA0545, "2020-03-31", k));

        System.out.println(vuoroB);
        System.out.println("Vuoro alkaa: " + vuoroB.getAloitusAika().toString());
        System.out.println("Vuoro loppuu: " + vuoroB.getLopetusAika().toString());

        System.out.println("Lepoaika toteutuu: " + Tes.tarkistaLepoaika(vuoro, vuoroB));

        Vuoro vuoroC = vuoroRepository.save(new Vuoro(koodiAA1800, "2020-03-30", k));
        System.out.println(vuoroC);
        System.out.println("Lepoaika toteutuu: " + Tes.tarkistaLepoaika(vuoroC, vuoroB));
        
        naytaTallennetutVuorot();
    }
}
