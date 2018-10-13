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
public class VastausDao implements Dao<Vastaus, Integer>{

    public VastausDao() {
        
    }
    
    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
        stmt.setInt(1, key); 
        ResultSet tulos = stmt.executeQuery();
        tulos.next();
        String vastausteksti = tulos.getString("vastausteksti").trim();
        int kysymys_id = tulos.getInt("kysymys_id");
        int oikein = tulos.getInt("oikein");
            
        Vastaus vastaus = new Vastaus(key, kysymys_id, vastausteksti, oikein);
        tulos.close();
        stmt.close();
        conn.close();
        return vastaus; 
    }

    
    public List<Vastaus> findVastaukset(int key) throws SQLException {
        ArrayList<Vastaus> palautus = new ArrayList();
        
        Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE kysymys_id = ?");
        stmt.setInt(1, key); 
        ResultSet tulos = stmt.executeQuery();
        int kysymys_id = key;
        while (tulos.next()) {
            String vastausteksti = tulos.getString("vastausteksti").trim();
            int id = tulos.getInt("id");
            int oikein = tulos.getInt("oikein");
            
            Vastaus vastaus = new Vastaus(id, kysymys_id, vastausteksti, oikein);
            palautus.add(vastaus);
        }
        
        tulos.close();
        stmt.close();
        conn.close();
        return palautus;    
    }

    @Override
    public void saveOrUpdate(Vastaus object) throws SQLException {
        Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus "
                + "(kysymys_id, vastausteksti, oikein) VALUES (?, ?, ?)");
        stmt.setInt(1, object.getKysymys_id());
        stmt.setString(2, object.getVastausteksti());
        stmt.setInt(3, object.getOikeinInt());
        
        stmt.executeUpdate();
        stmt.close();
        conn.close();    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE id = ?");
        stmt.setInt(1, key);
        
        
        stmt.executeUpdate();
        stmt.close();
        conn.close();    
    }
    
        
    public void deleteAll(Integer key) throws SQLException {
        Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE kysymys_id = ?");
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

    @Override
    public List<Vastaus> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
