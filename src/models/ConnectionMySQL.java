package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {

    private String database_name = "pharmacy_database";
    private String user = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost:3306/" + database_name;
    Connection conn = null;

    //Constructor de la clase 
    public Connection getConnection() {
        try {
            //Obtener valor del driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Obtener la conexión
            conn = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            System.err.println("Ha ocurrido una ClassNotFoundException " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Ha ocurrido una SQLException " + e.getMessage());
        }
        return conn;
    }

}
