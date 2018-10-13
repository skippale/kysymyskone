/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skippale.kysymyskone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aleksi
 */
public class KysymysDao implements Dao<Kysymys, Integer>{
    
    public KysymysDao() {
        
    }
    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet tulos = stmt.executeQuery();
        
        tulos.next();
        Kysymys palautus = new Kysymys(tulos.getInt("id"), tulos.getString("kurssi").trim(), 
        tulos.getString("aihe").trim(), tulos.getString("kysymysteksti").trim());
        stmt.close();
        conn.close();    
        return palautus;
    }

    @Override
    public ArrayList<Kysymys> findAll() throws SQLException {
        ArrayList<Kysymys> palautus = new ArrayList();
        
        Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys");
        ResultSet tulos = stmt.executeQuery();
        
        while (tulos.next()) {
            String kysymysteksti = tulos.getString("kysymysteksti").trim();
            String aihe = tulos.getString("aihe").trim();
            String kurssi = tulos.getString("kurssi").trim();
            int id = tulos.getInt("id");
            
            Kysymys kysymys = new Kysymys(id, kurssi, aihe, kysymysteksti);
            palautus.add(kysymys);
        }
        
        tulos.close();
        stmt.close();
        conn.close();
        return palautus;
    }

    @Override
    public void saveOrUpdate(Kysymys object) throws SQLException {
        Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys VALUES (kurssi, aihe, kysymysteksti) VALUES (?, ?, ?)");
        stmt.setString(1, object.getKurssi());
        stmt.setString(2, object.getAihe());
        stmt.setString(3, object.getKysymysteksti());
        
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");
        stmt.setInt(1, key);
        
        
        stmt.executeUpdate();
        stmt.close();
        conn.close();    
    }
    
    
    
    public static Connection getConnection() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection("jdbc:sqlite:kysymykset.db");
    }
    
    public ArrayList<String> findKurssinAiheet(String kurssi) throws SQLException {
        ArrayList<String> palautus = new ArrayList();

        Connection conn = getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT aihe FROM Kysymys WHERE kurssi = ?");
        stmt.setString(1, kurssi);

        ResultSet tulos = stmt.executeQuery();

        while (tulos.next()) {
            String aihe = tulos.getString("aihe").trim();
            palautus.add(aihe);
            }


        tulos.close();
        stmt.close();
        conn.close();
        return palautus;
    }
        
    public ArrayList<Kysymys> findAiheenKysymykset(String haku) throws SQLException {
        ArrayList<Kysymys> palautus = new ArrayList();

        Connection conn = getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE aihe = ?");
        stmt.setString(1, haku);
        ResultSet tulos = stmt.executeQuery();

        while (tulos.next()) {
            String kysymysteksti = tulos.getString("kysymysteksti").trim();
            String aihe = tulos.getString("aihe").trim();
            String kurssi = tulos.getString("kurssi").trim();
            int id = tulos.getInt("id");

            Kysymys kysymys = new Kysymys(id, kurssi, aihe, kysymysteksti);
            palautus.add(kysymys);
        }

        tulos.close();
        stmt.close();
        conn.close();
        return palautus;    
    }
    
    public ArrayList<String> findKurssit() throws SQLException {
        ArrayList<String> palautus = new ArrayList();

        Connection conn = getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT kurssi FROM Kysymys");

        ResultSet tulos = stmt.executeQuery();

        while (tulos.next()) {
            String kurssi = tulos.getString("kurssi").trim();
            palautus.add(kurssi);
        }


        tulos.close();
        stmt.close();
        conn.close();
        return palautus;
    }
}
