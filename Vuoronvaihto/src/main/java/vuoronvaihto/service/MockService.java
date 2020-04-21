/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.service;

import java.io.File;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuoronvaihto.dao.*;
import vuoronvaihto.domain.*;

/**
 *
 * Luokka tarjoaa ohjelman testaamiseen tarkoitettuja metodeja. Alustettava data
 * löytyy kansiosta /data. 
 */
@Service
public class MockService {
    
    @Autowired
    private UserObjectRepository workerRepository;
    
    @Autowired
    private ShiftCodeRepository shiftcodeRepository;
    
    @Autowired
    private ShiftRepository shiftRepository;
            
    /**
     * Näytä tietokantaan tallennetut vuorot.
     */
    public void naytaTallennetutVuorot() {
        System.out.println("Tallennetut vuorot:");
        for (Shift v : shiftRepository.findAll()) {
            System.out.println(v);
        }        
    }
    
    /**
     * Näytä tietokantaan tallennetut käyttäjät.    
     */
    public void naytaKayttajat() {
        System.out.println("Tallennetut käyttäjät:");
        for (UserObject k : workerRepository.findAll()) {
            System.out.println(k);
        }        
    }
    
    /**
     * Näytä tietokantaan tallennetut vuorokoodit.     
     */
    public void naytaVuorokoodit() {
        System.out.println("Tallennetut vuorokoodit:");
        for (Shiftcode v : shiftcodeRepository.findAll()) {
            System.out.println(v);
        }        
    }
    
    /**
     * Tuo käyttäjät CSV-tiedostosta.
     */
    private void tuoKayttajat() {
        int c = 0;
        try (Scanner tiedostonLukija = new Scanner(new File("data/kayttajat.csv"))) {
            while (tiedostonLukija.hasNextLine()) {                
                String rivi = tiedostonLukija.nextLine();               
                workerRepository.save(new UserObject(rivi));
                c++;
            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        System.out.println("Käyttäjiä tuotu: " + c + " kpl.");
    }
    
    /**
     * Tuo vuorokoodit CSV-tiedostosta.
     */
    private void tuoVuorokoodit() {
        int c = 0;
        try (Scanner tiedostonLukija = new Scanner(new File("data/vuorokoodit.csv"))) {
            while (tiedostonLukija.hasNextLine()) {
                String rivi = tiedostonLukija.nextLine();
                String[] palat = rivi.split(",");                
                shiftcodeRepository.save(new Shiftcode(palat[0], palat[1], Double.valueOf(palat[2])));
                c++;
            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        System.out.println("Vuorokoodeja tuotu: " + c + " kpl.");
    }
    
    /**
     * Tuo vuorot CSV-tiedostosta.
     */
    private void tuoVuorot() {
        int c = 0;
        try (Scanner tiedostonLukija = new Scanner(new File("data/vuorot.csv"))) {
            while (tiedostonLukija.hasNextLine()) {
                String rivi = tiedostonLukija.nextLine();
                String[] palat = rivi.split(",");
                Shiftcode v = shiftcodeRepository.findByCode(palat[0]);
                UserObject k = workerRepository.findByHandle(palat[2]).get(0);
                shiftRepository.save(new Shift(v, palat[1], k));
                c++;
            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        System.out.println("Vuoroja tuotu: " + c + " kpl.");
    }
    
    /**
     * Alusta tietokanta tarvittaessa testidatalla.
     */
    public void initializeDatabase() {
        if (workerRepository.count() == 0) {       
            System.out.println("Alustetaan tietokanta...");
            // Tyhjennä taulut
            shiftRepository.deleteAll();
            shiftcodeRepository.deleteAll();
            workerRepository.deleteAll();
            System.out.println("Taulut on tyhjennetty.");
            // Tuo käyttäjät
            tuoKayttajat();

            // Tuo vuorokoodit
            tuoVuorokoodit();

            // Tuo vuorot
            tuoVuorot();
        }
    }
    
}
