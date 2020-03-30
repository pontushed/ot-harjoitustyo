/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.logiikka;

/**
 *
 * @author pontus
 */
public class Kayttaja {
    private final int ID;
    private final String tunnus;
    
    public Kayttaja(int ID, String tunnus) {
        this.ID = ID;
        this.tunnus = tunnus;
    }

    public int getID() {
        return ID;
    }

    public String getTunnus() {
        return tunnus;
    }
    
    
}
