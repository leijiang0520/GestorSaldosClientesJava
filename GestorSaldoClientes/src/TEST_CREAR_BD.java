import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TEST_CREAR_BD {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:gestorSaldo.db";
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){

            // 1. 创建三张表
            String crearTablaUsuarios = "CREATE TABLE IF NOT EXISTS usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, usuario TEXT, contrasena TEXT);";
            String crearTablaClientes = "CREATE TABLE IF NOT EXISTS clientes(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, telefono TEXT, saldo DECIMAL);";
            String crearTablaOperaciones = "CREATE TABLE IF NOT EXISTS operaciones(id INTEGER PRIMARY KEY AUTOINCREMENT, idCliente INTEGER, tipo TEXT, montoBase DECIMAL, bonificacion DECIMAL, fecha TEXT);";

            stmt.execute(crearTablaUsuarios);
            stmt.execute(crearTablaClientes);
            stmt.execute(crearTablaOperaciones);

            //2. 只在admin不存在的时候插入，防止重复报错
            String insertAdmin = "INSERT OR IGNORE INTO usuarios(usuario,contrasena) VALUES ('admin','1234');";
            stmt.execute(insertAdmin);

            System.out.println("✅ Base de datos creada, usuario admin / 1234 creado!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
