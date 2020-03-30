/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.logiikka;

import java.time.*;

/**
 *
 * @author pontus
 */
public class Tes {
    public static boolean tarkistaLepoaika(Vuoro a,Vuoro b) {
        Duration duration = Duration.between(a.getLopetusAika(), b.getAloitusAika());
        long diff = Math.abs(duration.toMinutes());
        if (diff < 7*60) return false;
        return true;
    }
}
