/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author alday
 */
public class DetalleVenta {
    private int id;
    private int ventaId;
    private int articuloId;
    private String articuloCodigo;
    private String articuloNombre;
    private int articuloFormato;
    private int articuloStock;
    private int articuloStockPzs;
    private int cantidad;
    private int piezas;
    private double precio;
    private double descuento;
    private double subTotal;
    private double utilidad;

    public DetalleVenta() {
    }

    public DetalleVenta(int id, int ventaId, int articuloId, String articuloCodigo, String articuloNombre, int articuloStock, int cantidad, int piezas, double precio, double descuento, double subTotal, double utilidad) {
        this.id = id;
        this.ventaId = ventaId;
        this.articuloId = articuloId;
        this.articuloCodigo = articuloCodigo;
        this.articuloNombre = articuloNombre;
        this.articuloStock = articuloStock;
        this.cantidad = cantidad;
        this.piezas = piezas;
        this.precio = precio;
        this.descuento = descuento;
        this.subTotal = subTotal;
        this.utilidad=utilidad;
    }

    public DetalleVenta(int articuloId, String articuloCodigo, String articuloNombre, int articuloFormato,int articuloStock, int articuloStockPzs, int cantidad, int piezas, double precio, double descuento, double subTotal, double utilidad) {
        this.articuloId = articuloId;
        this.articuloCodigo = articuloCodigo;
        this.articuloNombre = articuloNombre;
        this.articuloFormato = articuloFormato;
        this.articuloStock = articuloStock;
        this.articuloStockPzs = articuloStockPzs;
        this.cantidad = cantidad;
        this.piezas = piezas;
        this.precio = precio;
        this.descuento = descuento;
        this.subTotal = subTotal;
        this.utilidad=utilidad;
    }

    public DetalleVenta(int articuloId, int cantidad, int piezas, double precio, double descuento, double utilidad) {
        this.articuloId = articuloId;
        this.cantidad = cantidad;
        this.piezas = piezas;
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

    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
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

    public int getArticuloFormato() {
        return articuloFormato;
    }

    public void setArticuloFormato(int articuloFormato) {
        this.articuloFormato = articuloFormato;
    }
    
    

    public int getArticuloStock() {
        return articuloStock;
    }

    public void setArticuloStock(int articuloStock) {
        this.articuloStock = articuloStock;
    }

    public int getArticuloStockPzs() {
        return articuloStockPzs;
    }

    public void setArticuloStockPzs(int articuloStockPzs) {
        this.articuloStockPzs = articuloStockPzs;
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
