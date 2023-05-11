/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author alday
 */
public class DetalleIngreso {
    private int id;
    private int ingresoId;
    private int articuloId;
    private String articuloCodigo;
    private int articuloFormato;
    private String articuloNombre;
    private int cantidad;
    private int piezas;
    private double precio;
    private double subtotal;
    private int existencia;
    private int existenciaPiezas;

    public DetalleIngreso() {
    }

    
    public DetalleIngreso(int id, int ingresoId, int articuloId, String articuloCodigo, String articuloNombre, int cantidad, int piezas, double precio, double subtotal, int existencia, int existenciaPiezas) {
        this.id = id;
        this.ingresoId = ingresoId;
        this.articuloId = articuloId;
        this.articuloCodigo = articuloCodigo;
        this.articuloNombre = articuloNombre;
        this.cantidad = cantidad;
        this.piezas=piezas;
        this.precio = precio;
        this.subtotal = subtotal;
        this.existencia = existencia;
        this.existenciaPiezas = existenciaPiezas;
    }

    public DetalleIngreso(int articuloId, int cantidad, int piezas, double precio, int existencia, int existenciaPiezas) {
        this.articuloId = articuloId;
        this.cantidad = cantidad;
        this.piezas = piezas;
        this.precio = precio;
        this.existencia=existencia;
        this.existenciaPiezas=existenciaPiezas;
    }

    public DetalleIngreso(int articuloId, String articuloCodigo, int articuloFormato, String articuloNombre, int cantidad, int piezas, double precio, double subtotal) {
        this.articuloId = articuloId;
        this.articuloCodigo = articuloCodigo;
        this.articuloFormato = articuloFormato;
        this.articuloNombre = articuloNombre;
        this.cantidad = cantidad;
        this.piezas = piezas;
        this.precio = precio;
        this.subtotal = subtotal;
        
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIngresoId() {
        return ingresoId;
    }

    public void setIngresoId(int ingresoId) {
        this.ingresoId = ingresoId;
    }

    public int getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(int articuloId) {
        this.articuloId = articuloId;
    }

    public String getArticuloCodigo() {
        return articuloCodigo;
    }

    public void setArticuloCodigo(String articuloCodigo) {
        this.articuloCodigo = articuloCodigo;
    }

    public int getArticuloFormato() {
        return articuloFormato;
    }

    public void setArticuloFormato(int articuloFormato) {
        this.articuloFormato = articuloFormato;
    }

     
    public String getArticuloNombre() {
        return articuloNombre;
    }

    public void setArticuloNombre(String articuloNombre) {
        this.articuloNombre = articuloNombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPiezas() {
        return piezas;
    }

    public void setPiezas(int piezas) {
        this.piezas = piezas;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getExistenciaPiezas() {
        return existenciaPiezas;
    }

    public void setExistenciaPiezas(int existenciaPiezas) {
        this.existenciaPiezas = existenciaPiezas;
    }
    
    
    
    
    
}
