/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto;
import vuoronvaihto.logiikka.*;
/**
 *
 * @author pontus
 */

public class Main {
    public static void main(String[] args) {

        System.out.println("Vuoronvaihtosovellus");
        System.out.println("--------------------");
        System.out.println("Ohjelmistotekniikka, kevät 2020.");
        System.out.println("Pontus Hedlund");
        System.out.println("Viikko 3. Tässä on vain tekstipohjainen käyttöliittymä tällä hetkellä.");
        System.out.println("Main ajaa muutaman komennon eri luokille.");
        System.out.println("Testeissä on pari testiä.");
        
        Kayttaja k = new Kayttaja(1,"ansponhed");
        Vuorokoodi koodi_AA1400 = new Vuorokoodi("AA1400","14:00",7);
        Vuorokoodi koodi_AA1800 = new Vuorokoodi("AA1800","18:00",7);
        Vuorokoodi koodi_AA0545 = new Vuorokoodi("AA0545","05:45",7);
        Vuoro vuoro = new Vuoro(koodi_AA1400,"2020-03-30",k);
        
        System.out.println(vuoro);
        System.out.println("Vuoro alkaa: " + vuoro.getAloitusAika().toString());
        System.out.println("Vuoro loppuu: " + vuoro.getLopetusAika().toString());
        
        Vuoro vuoroB = new Vuoro(koodi_AA0545,"2020-03-31",k);
      
        System.out.println(vuoroB);
        System.out.println("Vuoro alkaa: " + vuoroB.getAloitusAika().toString());
        System.out.println("Vuoro loppuu: " + vuoroB.getLopetusAika().toString());
        
        System.out.println("Lepoaika toteutuu: " + Tes.tarkistaLepoaika(vuoro, vuoroB));
        
        Vuoro vuoroC = new Vuoro(koodi_AA1800,"2020-03-30",k);
        System.out.println(vuoroC);
        System.out.println("Lepoaika toteutuu: " + Tes.tarkistaLepoaika(vuoroC, vuoroB));
        
        //VuoronvaihtoUI.main(args);
    }
}
