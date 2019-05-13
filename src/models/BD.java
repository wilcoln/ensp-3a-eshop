/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nivekiba
 */
public class BD {
    private static String urlPilote = "com.mysql.jdbc.Driver";
    private static String urlBD = "jdbc:mysql://localhost:3306/shopDB";
    private static String passwordBD = "";
    private static String userBD = "root";
    public static Connection connex = null;
    private Statement statement = null;

    public static Connection ConnexionMySql() {
        try {
            Class.forName(urlPilote);
            connex = DriverManager.getConnection(urlBD, userBD, passwordBD);
            return connex;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Statement getStatement()
    {
        try {
            return ConnexionMySql().createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
