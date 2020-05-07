/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.domain;

import java.time.*;
import java.util.List;

/**
 * Työehtosopimuksen rajoitteet kuvaava luokka.
 * @author pontus
 */
public class Contract {
    
    /**
     * Tarkista, että TES:n mukainen lepoaika toteutuu kahden vuoron välillä.
     * Lepoajan on oltava vähintään 7 tuntia.
     * @param a Ensimmäinen vuoro
     * @param b Seuraava vuoro
     * @return True, jos lepoaika toteutuu.
     */
    public static boolean checkRestTime(Shift a, Shift b) {
        if (a.getShiftCode().getCode().equals("Vapaa") ||
            b.getShiftCode().getCode().equals("Vapaa")) {
            return true;
        }
        Duration duration = Duration.between(a.getFinishTime(), b.getStartTime());
        long diff = Math.abs(duration.toMinutes());
        if (diff < 7 * 60) {
            return false;
        }
        return true;
    }
    
    /**
     * Tarkista, että TES:n mukainen lepoaika toteutuu kolmen vuoron välillä.
     * Lepoajan on oltava vähintään 7 tuntia.
     * @param a Ensimmäinen vuoro
     * @param b Seuraava vuoro
     * @param c Viimeinen vuoro
     * @return True, jos lepoaika toteutuu.
     */
    public static boolean checkRestTimeBeforeAndAfter(Shift a, Shift b, Shift c) {
        if (a != null && a.getShiftCode().getCode() != "Vapaa") {
            Duration duration = Duration.between(a.getFinishTime(), b.getStartTime());
            long diff = Math.abs(duration.toMinutes());
            if (diff < 7 * 60) {
                return false;
            }
        }
        if (c != null && c.getShiftCode().getCode() != "Vapaa") {
            Duration duration = Duration.between(b.getFinishTime(), c.getStartTime());
            long diff = Math.abs(duration.toMinutes());
            if (diff < 7 * 60) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Tarkista, että TES:n mukainen viikkolepo toteutuu useamman vuoron kohdalla.
     * @param shifts Syötä 7 vuorokauden shifts. Tässä pätkässä pitäisi löytyä yksi
 vähintään 24 tunnin lepoaika.
     * @return True, jos viikkolepo toteutuu.
     */
    public static boolean checkWeeklyRestTime(List<Shift> shifts) {
        boolean weeklyRestFound = false;
        if (shifts.isEmpty()) {
            return true;
        }
        for (int i = 1; i < shifts.size(); i++) {
            Duration timeBetween = Duration.between(shifts.get(i).getStartTime(),
                    shifts.get(i - 1).getFinishTime()
            );
            long diff = Math.abs(timeBetween.toHours());
            if (diff >= 24) {
                weeklyRestFound = true;
            }
        }
        return weeklyRestFound;
    }
    
    @Override
    public String toString() {
        return "TES-luokka";
    }
}
