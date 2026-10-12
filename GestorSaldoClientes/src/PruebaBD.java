import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//PARA COMBROBAR SI SE HA INSERTADO USUARIO Y CONTRASENA DE ADMINISTRADOR

public class PruebaBD {
	
    public static void main(String[] args) {
        String url = "jdbc:sqlite:gestorSaldo.db";
        try(Connection conn = DriverManager.getConnection(url)){
            String sql = "SELECT * FROM usuarios";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            System.out.println("=== Usuarios en la tabla usuarios ===");
            while(rs.next()){
                String user = rs.getString("usuario");
                String pass = rs.getString("contrasena");
                System.out.println("usuario: "+user+" | contrasena: "+pass);
            }
            rs.close();
            pst.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
