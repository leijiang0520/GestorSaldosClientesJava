package dao;

import bd.SQLite;
import modelo.Usuario;
// HERRAMIENTAS
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// DAO = DATA ACCESS OBJECT 

public class UsuarioDAO {
	
    public Usuario login(String nombreUsuario, String pass){
    	
    	SQLite db = new SQLite();
        Connection conexion = db.conectar();
        Usuario usuario = null;
        
        String sql = "SELECT * FROM usuarios WHERE usuario=? AND contrasena=?";
        
        try{
        	
            PreparedStatement pst = conexion.prepareStatement(sql);
            
            pst.setString(1, nombreUsuario);
            pst.setString(2, pass);
            
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
            	
                usuario = new Usuario();
                
                usuario.setId(rs.getInt("id"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setContrasena(rs.getString("contrasena"));
            }
            rs.close();
            pst.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            db.cerrarConectar();
        }
        return usuario;
    }
}
