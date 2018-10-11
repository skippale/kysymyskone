/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skippale.kysymyskone;

import spark.Spark;
import java.sql.*;

/**
 *
 * @author Aleksi
 */
public class Main {
    public static void main(String[] args) {
        if (System.getenv("PORT") != null) {
        Spark.port(Integer.valueOf(System.getenv("PORT")));
    }

        Spark.get("/*", (req, res) -> {
        return "hei maailma";
    });
    }
}
