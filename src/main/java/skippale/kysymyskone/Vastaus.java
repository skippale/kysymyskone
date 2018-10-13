/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skippale.kysymyskone;

/**
 *
 * @author Aleksi
 */
public class Vastaus {
    private int id;
    private int kysymys_id;
    private String vastausteksti;
    private int oikeinInt;
    private String oikein = "Vaarin!";
    
    public Vastaus(int id, int kysymys_id, String vastaus, int oikeinInt) {
        this.id = id;
        this.kysymys_id = kysymys_id;
        this.vastausteksti = vastaus;
        this.oikeinInt = oikeinInt;
        if (oikeinInt == 1) {
            this.oikein = "Oikein!";
        }
        
    }

    public int getId() {
        return id;
    }

    public int getKysymys_id() {
        return kysymys_id;
    }

    public String getVastausteksti() {
        return vastausteksti;
    }

    public int getOikeinInt() {
        return oikeinInt;
    }

    public String getOikein() {
        return oikein;
    }
}
