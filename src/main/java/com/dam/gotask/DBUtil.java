package com.dam.gotask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para la gestión de la base de datos.
 */
public class DBUtil {

    private Connection conn;
    private String cadenaConexion = "jdbc:mysql://localhost:3306/gotask";
    private String user = "root";
    private String password = "1234";

    /**
     * Obtiene una conexión a la base de datos.
     *
     * @return La conexión establecida.
     */
    public Connection getConexion() {

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            this.conn = DriverManager.getConnection(this.cadenaConexion, this.user, this.password);
            return this.conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Cierra la conexión a la base de datos.
     */
    public void cerrarConexion() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
