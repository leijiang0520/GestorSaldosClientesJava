package modelo;

import java.math.BigDecimal;

public class Cliente {
    private int id;     //int numero entero
    private String nombre;   
    private String telefono;
    private BigDecimal saldo;

    public int getId() {
    	return id;
    	}
    public void setId(int id) {
    	this.id = id;
    	}
    public String getNombre() {
    	return nombre;
    	}
    public void setNombre(String nombre) {
    	this.nombre = nombre;
    	}
    public String getTelefono() {
    	return telefono;
    	}
    public void setTelefono(String telefono) {
    	this.telefono = telefono;
    	}
    public BigDecimal getSaldo() {
    	return saldo;
    	}
    public void setSaldo(BigDecimal saldo) {
    	this.saldo = saldo;
    	}

    @Override
    public String toString(){
        return this.getNombre() + " | Tel: " + this.getTelefono();
    }
}
