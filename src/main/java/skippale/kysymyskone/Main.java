/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skippale.kysymyskone;

import spark.Spark;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 *
 * @author Aleksi
 */
public class Main {
    public static void main(String[] args) {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        VastausDao vastaukset = new VastausDao();
        KysymysDao kysymykset = new KysymysDao();

        Spark.get("/:kurssi", (req, res) -> {
            HashMap map = new HashMap<>();
            String kurssi = req.params(":kurssi");
            ArrayList<String> aiheet = kysymykset.findKurssinAiheet(req.params(":kurssi").trim());
            map.put("aiheet", aiheet);
            map.put("kurssi", kurssi);
            
            return new ModelAndView(map, "kurssisivu");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/kaikki", (req, res) -> {
            HashMap map = new HashMap<>();
            
            ArrayList<Kysymys> kaikki = kysymykset.findAll();
            map.put("kysymyksia", kaikki);
            
            return new ModelAndView(map, "kaikkikysymykset");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            
            ArrayList<String> kurssit = kysymykset.findKurssit();
            map.put("kurssit", kurssit);
            
            return new ModelAndView(map, "etusivu");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/:kurssi/:aihe", (req, res) -> {
            HashMap map = new HashMap<>();
            
            ArrayList<Kysymys> kysymyksia = kysymykset.findAiheenKysymykset(req.params(":aihe"));
            map.put("kysymyksia", kysymyksia);
            String kurssisivu = req.params(":kurssi");
            String aiheensivu = req.params(":aihe");
            map.put("kurssisivu", kurssisivu);
            map.put("aiheensivu", aiheensivu);
            
            return new ModelAndView(map, "aihesivu");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/:kurssi/:aihe/:kysymys/:vastaus", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer vastausId = Integer.parseInt(req.params(":vastaus"));
            Integer kysymysId = Integer.parseInt(req.params(":kysymys"));
            map.put("vastaukset", vastaukset.findOne(vastausId));
            map.put("kysymys", kysymykset.findOne(kysymysId));
            String kurssisivu = req.params(":kurssi");
            String aihesivu = req.params(":aihe");
            String kysymyssivu = req.params(":kysymys");
            map.put("kysymyssivu", kysymyssivu);
            map.put("kurssisivu", kurssisivu);
            map.put("aihesivu", aihesivu);
            
            
            return new ModelAndView(map, "vastaussivu");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/:kurssi/:aihe/:kysymys", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer kysymysId = Integer.parseInt(req.params(":kysymys"));
            map.put("kysymys", kysymykset.findOne(kysymysId));
            map.put("vastaukset", vastaukset.findVastaukset(kysymysId));
            String kurssisivu = req.params(":kurssi");
            String aihesivu = req.params(":aihe").trim();
            map.put("kurssisivu", kurssisivu);
            map.put("aihesivu", aihesivu);
            
            return new ModelAndView(map, "kysymyssivu");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/uusikysymys", (req, res) -> {
            String kurssi = req.queryParams("kurssi");
            String aihe = req.queryParams("aihe");
            String kysymysteksti = req.queryParams("kysymysteksti");
            Kysymys kysymys = new Kysymys(-1, kurssi, aihe, kysymysteksti);
            kysymykset.saveOrUpdate(kysymys);
            
            res.redirect("/");
            return "";
        });
        
        Spark.post("/uusivastaus", (req, res) -> {
            int kysymys_id = Integer.parseInt(req.queryParams("kysymys"));
            String vastausteksti = req.queryParams("vastausteksti");
            int oikein = Integer.parseInt(req.queryParams("oikein"));
            Vastaus vastaus = new Vastaus(-1, kysymys_id, vastausteksti, oikein);
            vastaukset.saveOrUpdate(vastaus);
            
            res.redirect("/:kurssi/:aihe/:kysymys");
            return "";
        });
        
        Spark.post("/:kurssi/:aihe/:kysymys/:vastaus/poista", (req, res) -> {
            int key = Integer.parseInt(req.params(":vastaus"));
            vastaukset.delete(key);
            
            res.redirect("/:kurssi/:aihe/:kysymys");
            return "";
        });
        
        Spark.post("/:kurssi/:aihe/:kysymys/poista", (req, res) -> {
            int key = Integer.parseInt(req.params(":kysymys"));
            kysymykset.delete(key);
            vastaukset.deleteAll(key);
            res.redirect("/:kurssi/:aihe");
            return "";
        });
        
    }
}
