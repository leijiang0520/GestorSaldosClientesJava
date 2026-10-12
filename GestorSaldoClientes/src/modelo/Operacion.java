package modelo;

import java.math.BigDecimal;

public class Operacion {
    private int id;
    private int idCliente;
    private String tipo; // RECARGAR o GASTO 
    private BigDecimal montoBase; //RECARGAR 100EUR
    private BigDecimal bonificacion;  // REGALA	20 EUR
    private String fecha;

    public int getId() {
    	return id;
    	}
    public void setId(int id) {
    	this.id = id;
    	}
    public int getIdCliente() {
    	return idCliente;
    	}
    public void setIdCliente(int idCliente) {
    	this.idCliente = idCliente;
    	}
    public String getTipo() {
    	return tipo;
    	}
    public void setTipo(String tipo) {
    	this.tipo = tipo;
    	}
    public BigDecimal getMontoBase() {
    	return montoBase;
    	}
    public void setMontoBase(BigDecimal montoBase) {
    	this.montoBase = montoBase;
    	}
    public BigDecimal getBonificacion() {
    	return bonificacion;
    	}
    public void setBonificacion(BigDecimal bonificacion) {
    	this.bonificacion = bonificacion;
    	}
    public String getFecha() {
    	return fecha;
    	}
    public void setFecha(String fecha) {
    	this.fecha = fecha;
    	}
}
