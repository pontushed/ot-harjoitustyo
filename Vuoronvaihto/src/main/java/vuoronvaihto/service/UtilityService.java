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
 * This class is used to initialize the database for testing the application.
 */
@Service
public class UtilityService {
    
    @Autowired
    private UserObjectRepository workerRepository;
    
    @Autowired
    private ShiftCodeRepository shiftcodeRepository;
    
    @Autowired
    private ShiftRepository shiftRepository;
                    
    /**
     * Import workers (users).
     */
    private void importWorkers() {
        int c = 0;
        try (Scanner fileReader = new Scanner(new File("data/kayttajat.csv"))) {
            while (fileReader.hasNextLine()) {                
                String line = fileReader.nextLine();               
                workerRepository.save(new UserObject(line));
                c++;
            }
        } catch (Exception e) {
            System.out.println("Error during worker import: " + e.getMessage());
        }
        System.out.println("Workers imported: " + c);
    }
    
    /**
     * Import shift codes.
     */
    private void importShiftcodes() {
        int c = 0;
        try (Scanner fileReader = new Scanner(new File("data/vuorokoodit.csv"))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] pieces = line.split(",");                
                shiftcodeRepository.save(new Shiftcode(pieces[0], pieces[1], Double.valueOf(pieces[2])));
                c++;
            }
        } catch (Exception e) {
            System.out.println("Error during shiftcode import: " + e.getMessage());
        }
        System.out.println("Shiftcodes imported: " + c);
    }
    
    /**
     * Import shifts.
     */
    private void importShifts() {
        int c = 0;
        try (Scanner fileReader = new Scanner(new File("data/vuorot.csv"))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] pieces = line.split(",");
                Shiftcode v = shiftcodeRepository.findByCode(pieces[0]);
                UserObject k = workerRepository.findByHandle(pieces[2]).get(0);
                shiftRepository.save(new Shift(v, pieces[1], k));
                c++;
            }
        } catch (Exception e) {
            System.out.println("Error during shift import: " + e.getMessage());
        }
        System.out.println("Shifts imported: " + c);
    }
    
    /**
     * Initialize database with imported data from CSV files in /data.
     */
    public void initializeDatabase() {
        if (workerRepository.count() == 0) {       
            System.out.println("Initalizing database...");
            // Tyhjennä taulut
            shiftRepository.deleteAll();
            shiftcodeRepository.deleteAll();
            workerRepository.deleteAll();
            System.out.println("Tables initialized.");
            // Tuo käyttäjät
            importWorkers();

            // Tuo vuorokoodit
            importShiftcodes();

            // Tuo vuorot
            importShifts();
        }
    }
    
}
