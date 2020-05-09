/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
     * Generate shift list for demo.
     */
    private void generateShifts() {
        List<UserObject> workers = workerRepository.findAll();
        List<Shiftcode> shiftcodes = shiftcodeRepository.findAll();
        int offset = 0, i = 0;        
        LocalDate startDate = LocalDate.now();
        LocalDate d = startDate;
        while (!d.equals(startDate.plusDays(21))) {
            List<Shift> shifts = new ArrayList<>();
            for (UserObject u : workers) {
                Shift s = getAutoShift(shiftcodes, i + offset, u, d);
                if (s != null) {
                    shifts.add(s);
                }                                
                offset = (offset > 5) ? 0 : offset + 1;
            }
            shiftRepository.saveAll(shifts);
            d = d.plusDays(1);
            i = i > 4 ? 0 : i + 1;
        }
    }
    
    /** Select shiftcode from the list of available shifts.
     * Used by generateShifts()
     * @param codes List of codes to choose from
     * @param i index number
     * @return Shift according to pattern
     */
    private Shift getAutoShift(List<Shiftcode> codes, int i, UserObject u, LocalDate d) {
        Random r = new Random(1337);        
        Shiftcode code = null; // free day
        if (i < 3) {
            code = codes.get(r.nextInt(6)); // morning shift
        } else if (i < 5) {
            code = codes.get(5 + r.nextInt(5)); // evening shift
        } else if (i == 6) {
            code = codes.get(codes.size() - 2); // night shift
        }    
        return (code == null ? null : new Shift(code, d.toString(), u));
    }
    
    /**
     * Initialize database with imported data from CSV files in /data.
     */
    public void initializeDatabase() {
        if (workerRepository.count() == 0) {       
            System.out.println("Initalizing database...");
            // Truncate the tables
            shiftRepository.deleteAll();
            shiftcodeRepository.deleteAll();
            workerRepository.deleteAll();
            System.out.println("Tables initialized.");
            // Import workers
            importWorkers();

            // Import shiftcodes
            importShiftcodes();

            // Generate shift list
            generateShifts();
        }
    }
    
}
