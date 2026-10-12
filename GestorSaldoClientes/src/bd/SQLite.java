package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLite {

    private static final String URL = "jdbc:sqlite:gestorSaldo.db";

    public Connection conectar() {
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(URL);
            System.out.println("Conexión realizada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
        }

        return conexion;
    }

    public void cerrarConectar(){
    }

  }


