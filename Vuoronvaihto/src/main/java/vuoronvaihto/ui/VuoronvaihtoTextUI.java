/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.ui;

import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vuoronvaihto.dao.*;
import vuoronvaihto.domain.*;
import vuoronvaihto.service.MockService;

/**
 * Tekstikäyttöliittymä.
 * @author pontus
 */

// Kommentoi pois seuraava rivi jos haluat tekstikäyttöliittymän.
//@Component
public class VuoronvaihtoTextUI implements CommandLineRunner {
    
    private UserObject k;
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static final String TESTIVALIKKO = ANSI_RED + "Testivalikko:\n"
            + "a    Alusta tietokanta\n"
            + "n    Nayta tallennetut vuorot\n"
            + "k    Nayta kayttajat\n"
            + "v    Nayta vuorokoodit\n"
            + "x    Palaa päävalikkoon" + ANSI_RESET;
    
    @Autowired
    private UserObjectRepository userobjectRepository;
    
    @Autowired
    private ShiftCodeRepository vuorokoodiRepository;
    
    @Autowired
    private ShiftRepository vuoroRepository;
    
    @Autowired
    MockService ts;
    
    private void tervehdys() {
        System.out.println("Vuoronvaihtosovellus");
        System.out.println("--------------------");
        System.out.println("Ohjelmistotekniikka, kevät 2020.");
        System.out.println("Pontus Hedlund");
        System.out.println("Viikko 5. Tässä on vain tekstipohjainen käyttöliittymä tällä hetkellä.");
        System.out.println("- Ohjelma käyttää nyt Spring Boot-sovelluskehystä ja JPA:ta H2-tietokannalla.");
        System.out.println("- Ohjelmassa on nyt perustoiminnallisuus.");
    }
    
    
    private void naytaApu() {
        System.out.println("Komennot:");
        System.out.println("t    Testivalikko");
        System.out.println("x    Lopeta");
        System.out.println("?    Apu");
    }
    
    private UserObject haeKayttaja(String tunnus) {        
        List<UserObject> kayttajat = userobjectRepository.findByHandle(tunnus);
        if (kayttajat.isEmpty()) {
            System.out.println("Käyttäjää ei löydy.");
            return null;
        }
        return kayttajat.get(0);
    }
    
    private void checkDBinitialized() {
        if (userobjectRepository.count() == 0) {            
            ts.initializeDatabase();
        }
    }
    
    private UserObject login(Scanner s) {
        checkDBinitialized();
        System.out.println("Kirjaudu sisään");
        System.out.println("-------------------------------");
        UserObject k = null;
        int yrityksiaJaljella = 3;
        while (k == null && yrityksiaJaljella > 0) {
            System.out.print("Tunnus (Yrityksiä jäljellä: " + yrityksiaJaljella + "): ");
            String tunnus = s.nextLine();
            k = haeKayttaja(tunnus);            
            if (k  == null) {
                yrityksiaJaljella--;                
            } else {
                break;
            }
        }
        return k;
    }
    
    private void suoritaTestiValikonKomento(String komento) {
        switch (komento) {
            case "a":
                ts.initializeDatabase();
                break;
            case "n":
                ts.naytaTallennetutVuorot();
                break;
            case "k":
                ts.naytaKayttajat();
                break;
            case "v":
                ts.naytaVuorokoodit();
                break;
            default:
                break;
        }
    }
    
    private void testiValikko(Scanner s) {                        
        while (true) {
            System.out.println(TESTIVALIKKO);
            String komento = s.nextLine();
            if (komento.equals("x")) {
                break;
            }
            suoritaTestiValikonKomento(komento);
        }
    }
    
    private void paaValikko(Scanner s) {
        while (true) {
            System.out.print("Komento (Apu = ?): ");
            String komento = s.nextLine();
            if (komento.equals("x")) {
                break;
            }
            switch (komento) {
                case "?":
                    naytaApu();
                    break;
                case "t":
                    testiValikko(s);
                    break;
                default:
                    break;                
            }
        }
    }
    
    /**
     * Käyttöliittymän päämetodi.
     * @param args tulee Mainin kautta
     * @throws Exception Virhetilanne Spring Bootista
     */
    public void run(String... args) throws Exception {
        tervehdys();
        Scanner s = new Scanner(System.in);
        k = login(s);
        if (k == null) {
            System.out.println("Kirjautuminen epäonnistui. Sovellus lopetetaan.");
            return;
        } else {
            System.out.println("Tervetuloa, " + k.getHandle());
        }
        paaValikko(s);
        System.out.println("\nKirjauduit ulos. Sovellus lopetetaan.");
    }
}
