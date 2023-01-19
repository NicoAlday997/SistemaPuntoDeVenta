/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author alday
 */
public class DetalleVentaRuta {
    private int id;
    private int ventaRutaId;
    private int articuloId;
    private String articuloCodigo;
    private String articuloNombre;
    private int articuloStock;
    private int cantidad;
    private double precio;
    private double descuento;
    private double subTotal;
    private double utilidad;

    public DetalleVentaRuta() {
    }

    //Se agrego utilidad a 2 constructores
    public DetalleVentaRuta(int id, int ventaRutaId, int articuloId, String articuloCodigo, String articuloNombre, int articuloStock, int cantidad, double precio, double descuento, double subTotal, double utilidad) {
        this.id = id;
        this.ventaRutaId = ventaRutaId;
        this.articuloId = articuloId;
        this.articuloCodigo = articuloCodigo;
        this.articuloNombre = articuloNombre;
        this.articuloStock = articuloStock;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
        this.subTotal = subTotal;
        this.utilidad=utilidad;
    }

    public DetalleVentaRuta(int articuloId, String articuloCodigo, String articuloNombre, int articuloStock, int cantidad, double precio, double descuento, double subTotal, double utilidad) {
        this.articuloId = articuloId;
        this.articuloCodigo = articuloCodigo;
        this.articuloNombre = articuloNombre;
        this.articuloStock = articuloStock;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
        this.subTotal = subTotal;
        this.utilidad=utilidad;
    }

    public DetalleVentaRuta(int articuloId, int cantidad, double precio, double descuento, double utilidad) {
        this.articuloId = articuloId;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
        this.utilidad=utilidad;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVentaRutaId() {
        return ventaRutaId;
    }

    public void setVentaRutaId(int ventaRutaId) {
        this.ventaRutaId = ventaRutaId;
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

    public String getArticuloNombre() {
        return articuloNombre;
    }

    public void setArticuloNombre(String articuloNombre) {
        this.articuloNombre = articuloNombre;
    }

    public int getArticuloStock() {
        return articuloStock;
    }

    public void setArticuloStock(int articuloStock) {
        this.articuloStock = articuloStock;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(double utilidad) {
        this.utilidad = utilidad;
    }
    
    
}
