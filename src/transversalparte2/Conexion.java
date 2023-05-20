/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transversalparte2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ariana
 */
public class Conexion {
    private static final String url = "jdbc:mysql://localhost/";
    private static final String usuario = "root";
    private static final String password = "";
    private static final String databaseName = "universidaddb";
    
    private static Connection connection;

    private Conexion(){
        
    }
    
    public static Connection getConnection() {
        if(connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mariadb://localhost/universidaddb", "root", "");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return connection;
    }
}
