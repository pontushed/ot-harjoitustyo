/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.domain;

/**
 *
 * @author pontus
 */
public class Ehdotus {
    private Vuoro vuoro;
    private Kayttaja tekija;
    private Kayttaja uusiTekija;

    public Ehdotus(Vuoro vuoro, Kayttaja tekija, Kayttaja uusiTekija) {
        this.vuoro = vuoro;
        this.tekija = tekija;
        this.uusiTekija = uusiTekija;
    }
    
}
