package dao;

import bd.SQLite;
import modelo.Cliente;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public boolean agregarCliente(Cliente c){
    	
        SQLite db = new SQLite();
        Connection conexion = db.conectar();
        
        boolean ok = false;
        
        String sql = "INSERT INTO clientes(nombre,telefono,saldo) VALUES (?,?,?)";
        
        try{
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, c.getNombre());
            pst.setString(2, c.getTelefono());
            pst.setString(3, c.getSaldo().toString());
            
            int filas = pst.executeUpdate();
            if(filas>0) ok = true; //VERIFICAR SI SE HA AGREGADO CORRECTO
            pst.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            db.cerrarConectar();
        }
        return ok;
    }

    public List<Cliente> listarTodosClientes(){
    	
        List<Cliente> lista = new ArrayList<>();//PREPARAR UN LIST VACIO
        
        SQLite db = new SQLite();
        Connection conexion = db.conectar();
        
        String sql = "SELECT * FROM clientes";
        
        try{
            PreparedStatement pst = conexion.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setTelefono(rs.getString("telefono"));
                c.setSaldo(new BigDecimal(rs.getString("saldo")));
                lista.add(c);
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
        return lista;
    }

    public boolean actualizarSaldo(int idCliente, BigDecimal nuevoSaldo){
    	
    	SQLite db = new SQLite();
        Connection conexion = db.conectar();
        
        boolean ok = false;
        
        String sql = "UPDATE clientes SET saldo=? WHERE id=?";
        try{
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, nuevoSaldo.toString());
            pst.setInt(2, idCliente);
            
            int filas = pst.executeUpdate();
            if(filas>0) ok = true;
            pst.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            db.cerrarConectar();
        }
        return ok;
    }
}
