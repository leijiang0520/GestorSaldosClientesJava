package dao;
import bd.SQLite;
import modelo.Operacion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperacionDAO {

    public boolean guardarOperacion(Operacion op){  //保存一笔操作
    	
    	SQLite db = new SQLite();
        Connection conexion = db.conectar();
        
        boolean ok = false;
       
        String sql = "INSERT INTO operaciones(idCliente, tipo, montoBase, bonificacion, fecha) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setInt(1, op.getIdCliente());
            pst.setString(2, op.getTipo());
            pst.setString(3, op.getMontoBase().toString());
            pst.setString(4, op.getBonificacion().toString());
            pst.setString(5, op.getFecha());
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

    public List<Operacion> listarHistorialCliente(int idCliente){  //找某个客户的操作记录
    	
        List<Operacion> lista = new ArrayList<>();
        
        SQLite db = new SQLite();
        Connection conexion = db.conectar();
        
        String sql = "SELECT * FROM operaciones WHERE idCliente=?";
        
        try{
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setInt(1, idCliente);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Operacion op = new Operacion();
                op.setId(rs.getInt("id"));
                op.setIdCliente(rs.getInt("idCliente"));
                op.setTipo(rs.getString("tipo"));
                op.setMontoBase(new BigDecimal(rs.getString("montoBase")));
                op.setBonificacion(new BigDecimal(rs.getString("bonificacion")));
                op.setFecha(rs.getString("fecha"));
                lista.add(op);
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

    public String obtenerFechaActual(){  //得到现在的日期和时间
    	
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date());
    }
}
