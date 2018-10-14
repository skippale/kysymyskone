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
public class Kysymys {
    private Integer id ;
    private String kysymysteksti;
    private String aihe;
    private String kurssi;
    
    

    
    public Kysymys(int id, String kurssi, String aihe, String kysymysteksti) {
        this.id = id;
        this.kysymysteksti = kysymysteksti.trim();
        this.aihe = aihe.trim();
        this.kurssi = kurssi.trim();
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getKurssi() {
        return kurssi;
    }
    
    public String getAihe() {
        return aihe;
    }
    public String getKysymysteksti() {
        return kysymysteksti;
    }

}
