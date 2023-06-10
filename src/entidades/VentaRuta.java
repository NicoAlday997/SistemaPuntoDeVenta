/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author alday
 */
public class VentaRuta {
    private int id;
    private int usuarioId;
    private String usuarioNombre;
    private int rutaId;
    private String rutaNombre;
    private int vendedorId;
    private String vendedorNombre;
    private int ayudanteId;
    private String ayudanteNombre;
    private String serieComprobante;
    private String numComprobante;
    private Date fecha;
    private double otrosProductos;
    private double creditosCobrados;
    private double creditosOtorgados;
    private double gastosMedicos;
    private double refacciones;
    private double combustible;
    private double otrosGastos;
    
    private double ventaBruta;
    private double descuentoCigarro;
    private double descuentoRefresco;
    private double costoVenta;
    private double utilidadBruta;
    private double utilidadNeta;
    private double fs;  
   
    private double totalLiquidar;
    private double efectivo;
    private String estado;
    
    private List<DetalleVentaRuta> detalles;

    public VentaRuta() {
    }

    public VentaRuta(int id, int usuarioId, String usuarioNombre, int rutaId, String rutaNombre, int vendedorId, String vendedorNombre, int ayudanteId, String ayudanteNombre, String serieComprobante, String numComprobante, Date fecha, double otrosProductos, double creditosCobrados, double creditosOtorgados, double gastosMedicos, double refacciones, double combustible, double otrosGastos, double descuentoCigarro, double descuentoRefresco, double utilidadBruta, double utilidadNeta, double fs, double totalLiquidar, double efectivo, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.rutaId = rutaId;
        this.rutaNombre = rutaNombre;
        this.vendedorId = vendedorId;
        this.vendedorNombre = vendedorNombre;
        this.ayudanteId = ayudanteId;
        this.ayudanteNombre = ayudanteNombre;
        this.serieComprobante = serieComprobante;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.otrosProductos = otrosProductos;
        this.creditosCobrados = creditosCobrados;
        this.creditosOtorgados = creditosOtorgados;
        this.gastosMedicos = gastosMedicos;
        this.refacciones = refacciones;
        this.combustible = combustible;
        this.otrosGastos = otrosGastos;
        this.descuentoCigarro = descuentoCigarro;
        this.descuentoRefresco = descuentoRefresco;
        this.utilidadBruta = utilidadBruta;
        this.utilidadNeta = utilidadNeta;
        this.fs = fs;
        this.totalLiquidar = totalLiquidar;
        this.efectivo = efectivo;
        this.estado = estado;
    }



    
    
    /*
    public VentaRuta(int id, int usuarioId, String usuarioNombre, int rutaId, String rutaNombre, int vendedorId, String vendedorNombre, int ayudanteId, String ayudanteNombre, String serieComprobante,String numComprobante, Date fecha, double otrosProductos, double creditosCobrados, double creditosOtorgados, double gastosMedicos, double refacciones, double combustible, double otrosGastos, double descuentoProducto, double totalLiquidar, double totalUtilidad, double efectivo, String estado, List<DetalleVentaRuta> detalles) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.rutaId = rutaId;
        this.rutaNombre = rutaNombre;
        this.vendedorId = vendedorId;
        this.vendedorNombre = vendedorNombre;
        this.ayudanteId = ayudanteId;
        this.ayudanteNombre = ayudanteNombre;
        this.serieComprobante = serieComprobante;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.otrosProductos = otrosProductos;
        this.creditosCobrados = creditosCobrados;
        this.creditosOtorgados = creditosOtorgados;
        this.gastosMedicos = gastosMedicos;
        this.refacciones = refacciones;
        this.combustible = combustible;
        this.otrosGastos = otrosGastos;
        
        this.efectivo = efectivo;
        this.descuentoProducto=descuentoProducto;
        this.totalLiquidar = totalLiquidar;
        this.totalUtilidad=totalUtilidad;
        this.estado = estado;
        this.detalles = detalles;
    }*/

    public VentaRuta(int id, int usuarioId, String usuarioNombre, int rutaId, String rutaNombre, int vendedorId, String vendedorNombre, int ayudanteId, String ayudanteNombre, String serieComprobante, String numComprobante, Date fecha, double otrosProductos, double creditosCobrados, double creditosOtorgados, double gastosMedicos, double refacciones, double combustible, double otrosGastos, double descuentoCigarro, double descuentoRefresco, double utilidadBruta, double utilidadNeta, double fs, double totalLiquidar, double efectivo, String estado, List<DetalleVentaRuta> detalles) {  
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.rutaId = rutaId;
        this.rutaNombre = rutaNombre;
        this.vendedorId = vendedorId;
        this.vendedorNombre = vendedorNombre;
        this.ayudanteId = ayudanteId;
        this.ayudanteNombre = ayudanteNombre;
        this.serieComprobante = serieComprobante;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.otrosProductos = otrosProductos;
        this.creditosCobrados = creditosCobrados;
        this.creditosOtorgados = creditosOtorgados;
        this.gastosMedicos = gastosMedicos;
        this.refacciones = refacciones;
        this.combustible = combustible;
        this.otrosGastos = otrosGastos;
        this.descuentoCigarro = descuentoCigarro;
        this.descuentoRefresco = descuentoRefresco;
        this.utilidadBruta = utilidadBruta;
        this.utilidadNeta = utilidadNeta;
        this.fs = fs;
        this.totalLiquidar = totalLiquidar;
        this.efectivo = efectivo;
        this.estado = estado;
        this.detalles = detalles;
    }

    /* public VentaRuta(int id, int usuarioId, String usuarioNombre, int rutaId, String rutaNombre, int vendedorId, String vendedorNombre, int ayudanteId, String ayudanteNombre, String serieComprobante,String numComprobante, Date fecha, double otrosProductos, double creditosCobrados, double creditosOtorgados, double gastosMedicos, double refacciones, double combustible, double otrosGastos, double descuentoProducto ,double totalLiquidar , double totalUtilidad,double efectivo, String estado) {
    this.id = id;
    this.usuarioId = usuarioId;
    this.usuarioNombre = usuarioNombre;
    this.rutaId = rutaId;
    this.rutaNombre = rutaNombre;
    this.vendedorId = vendedorId;
    this.vendedorNombre = vendedorNombre;
    this.ayudanteId = ayudanteId;
    this.ayudanteNombre = ayudanteNombre;
    this.serieComprobante = serieComprobante;
    this.numComprobante = numComprobante;
    this.fecha = fecha;
    this.otrosProductos = otrosProductos;
    this.creditosCobrados = creditosCobrados;
    this.creditosOtorgados = creditosOtorgados;
    this.gastosMedicos = gastosMedicos;
    this.refacciones = refacciones;
    this.combustible = combustible;
    this.otrosGastos = otrosGastos;
    this.descuentoProducto=descuentoProducto;
    this.efectivo = efectivo;
    this.totalLiquidar = totalLiquidar;
    this.totalUtilidad=totalUtilidad;
    this.estado = estado;
    }*/
    public VentaRuta(int id, int usuarioId, String usuarioNombre, int rutaId, String rutaNombre, int vendedorId, String vendedorNombre, int ayudanteId, String ayudanteNombre, String serieComprobante, String numComprobante, Date fecha, double otrosProductos, double creditosCobrados, double creditosOtorgados, double gastosMedicos, double refacciones, double combustible, double otrosGastos, double ventaBruta, double descuentoCigarro, double descuentoRefresco, double costoVenta, double utilidadBruta, double utilidadNeta, double fs, double totalLiquidar, double efectivo, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.rutaId = rutaId;
        this.rutaNombre = rutaNombre;
        this.vendedorId = vendedorId;
        this.vendedorNombre = vendedorNombre;
        this.ayudanteId = ayudanteId;
        this.ayudanteNombre = ayudanteNombre;
        this.serieComprobante = serieComprobante;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.otrosProductos = otrosProductos;
        this.creditosCobrados = creditosCobrados;
        this.creditosOtorgados = creditosOtorgados;
        this.gastosMedicos = gastosMedicos;
        this.refacciones = refacciones;
        this.combustible = combustible;
        this.otrosGastos = otrosGastos;
        this.ventaBruta = ventaBruta;
        this.descuentoCigarro = descuentoCigarro;
        this.descuentoRefresco = descuentoRefresco;
        this.costoVenta = costoVenta;
        this.utilidadBruta = utilidadBruta;
        this.utilidadNeta = utilidadNeta;
        this.fs = fs;
        this.totalLiquidar = totalLiquidar;
        this.efectivo = efectivo;
        this.estado = estado;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public int getRutaId() {
        return rutaId;
    }

    public void setRutaId(int rutaId) {
        this.rutaId = rutaId;
    }

    public String getRutaNombre() {
        return rutaNombre;
    }

    public void setRutaNombre(String rutaNombre) {
        this.rutaNombre = rutaNombre;
    }

    public int getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(int vendedorId) {
        this.vendedorId = vendedorId;
    }

    public String getVendedorNombre() {
        return vendedorNombre;
    }

    public void setVendedorNombre(String vendedorNombre) {
        this.vendedorNombre = vendedorNombre;
    }

    public int getAyudanteId() {
        return ayudanteId;
    }

    public void setAyudanteId(int ayudanteId) {
        this.ayudanteId = ayudanteId;
    }

    public String getAyudanteNombre() {
        return ayudanteNombre;
    }

    public void setAyudanteNombre(String ayudanteNombre) {
        this.ayudanteNombre = ayudanteNombre;
    }

    public String getSerieComprobante() {
        return serieComprobante;
    }

    public void setSerieComprobante(String serieComprobante) {
        this.serieComprobante = serieComprobante;
    }
    

    public String getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(String numComprobante) {
        this.numComprobante = numComprobante;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getOtrosProductos() {
        return otrosProductos;
    }

    public void setOtrosProductos(double otrosProductos) {
        this.otrosProductos = otrosProductos;
    }

    public double getCreditosCobrados() {
        return creditosCobrados;
    }

    public void setCreditosCobrados(double creditosCobrados) {
        this.creditosCobrados = creditosCobrados;
    }

    public double getCreditosOtorgados() {
        return creditosOtorgados;
    }

    public void setCreditosOtorgados(double creditosOtorgados) {
        this.creditosOtorgados = creditosOtorgados;
    }

    public double getGastosMedicos() {
        return gastosMedicos;
    }

    public void setGastosMedicos(double gastosMedicos) {
        this.gastosMedicos = gastosMedicos;
    }

    public double getRefacciones() {
        return refacciones;
    }

    public void setRefacciones(double refacciones) {
        this.refacciones = refacciones;
    }

    public double getCombustible() {
        return combustible;
    }

    public void setCombustible(double combustible) {
        this.combustible = combustible;
    }

    public double getOtrosGastos() {
        return otrosGastos;
    }

    public void setOtrosGastos(double otrosGastos) {
        this.otrosGastos = otrosGastos;
    }

    public double getVentaBruta() {
        return ventaBruta;
    }

    public void setVentaBruta(double ventaBruta) {
        this.ventaBruta = ventaBruta;
    }

    public double getDescuentoCigarro() {
        return descuentoCigarro;
    }

    public void setDescuentoCigarro(double descuentoCigarro) {
        this.descuentoCigarro = descuentoCigarro;
    }

    public double getDescuentoRefresco() {
        return descuentoRefresco;
    }

    public void setDescuentoRefresco(double descuentoRefresco) {
        this.descuentoRefresco = descuentoRefresco;
    }

    public double getCostoVenta() {
        return costoVenta;
    }

    public void setCostoVenta(double costoVenta) {
        this.costoVenta = costoVenta;
    }

    public double getUtilidadBruta() {
        return utilidadBruta;
    }

    public void setUtilidadBruta(double utilidadBruta) {
        this.utilidadBruta = utilidadBruta;
    }

    public double getUtilidadNeta() {
        return utilidadNeta;
    }

    public void setUtilidadNeta(double utilidadNeta) {
        this.utilidadNeta = utilidadNeta;
    }

    public double getFs() {
        return fs;
    }

    public void setFs(double fs) {
        this.fs = fs;
    }
    
     public double getTotalLiquidar() {
        return totalLiquidar;
    }

    public void setTotalLiquidar(double totalLiquidar) {
        this.totalLiquidar = totalLiquidar;
    }
    

    public double getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(double efectivo) {
        this.efectivo = efectivo;
    }
    


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetalleVentaRuta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaRuta> detalles) {
        this.detalles = detalles;
    } 
    
    
}
