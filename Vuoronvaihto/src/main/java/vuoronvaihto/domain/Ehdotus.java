/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.domain;

/**
 * Ehdotus-olion toteuttava luokka.
 * @author pontus
 */
public class Ehdotus {
    private Shift vuoro;
    private UserObject tekija;
    private UserObject uusiTekija;

    /**
     * Konstruktori. tekijä ehdottaa vuoroa vuoro tekijälle uusiTekijä. 
     * @param vuoro Mitä vuoroa ehdotus koskee
     * @param tekija Alkuperäinen tekijä
     * @param uusiTekija Ehdotuksen saava tekijä
     */
    public Ehdotus(Shift vuoro, UserObject tekija, UserObject uusiTekija) {
        this.vuoro = vuoro;
        this.tekija = tekija;
        this.uusiTekija = uusiTekija;
    }
    
}
