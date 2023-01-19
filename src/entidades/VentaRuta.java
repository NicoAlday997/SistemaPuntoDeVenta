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
    private String numComprobante;
    private Date fecha;
    private double otrosProductos;
    private double creditosCobrados;
    private double creditosOtorgados;
    private double gastosMedicos;
    private double refacciones;
    private double combustible;
    private double promocion;
    private double efectivo;
    private double totalLiquidar;
    private String estado;
    
    private List<DetalleVentaRuta> detalles;

    public VentaRuta() {
    }

    public VentaRuta(int id, int usuarioId, String usuarioNombre, int rutaId, String rutaNombre, int vendedorId, String vendedorNombre, int ayudanteId, String ayudanteNombre, String numComprobante, Date fecha, double otrosProductos, double creditosCobrados, double creditosOtorgados, double gastosMedicos, double refacciones, double combustible, double promocion, double efectivo, double totalLiquidar, String estado, List<DetalleVentaRuta> detalles) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.rutaId = rutaId;
        this.rutaNombre = rutaNombre;
        this.vendedorId = vendedorId;
        this.vendedorNombre = vendedorNombre;
        this.ayudanteId = ayudanteId;
        this.ayudanteNombre = ayudanteNombre;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.otrosProductos = otrosProductos;
        this.creditosCobrados = creditosCobrados;
        this.creditosOtorgados = creditosOtorgados;
        this.gastosMedicos = gastosMedicos;
        this.refacciones = refacciones;
        this.combustible = combustible;
        this.promocion = promocion;
        this.efectivo = efectivo;
        this.totalLiquidar = totalLiquidar;
        this.estado = estado;
        this.detalles = detalles;
    }

    public VentaRuta(int id, int usuarioId, String usuarioNombre, int rutaId, String rutaNombre, int vendedorId, String vendedorNombre, int ayudanteId, String ayudanteNombre, String numComprobante, Date fecha, double otrosProductos, double creditosCobrados, double creditosOtorgados, double gastosMedicos, double refacciones, double combustible, double promocion, double efectivo, double totalLiquidar, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.rutaId = rutaId;
        this.rutaNombre = rutaNombre;
        this.vendedorId = vendedorId;
        this.vendedorNombre = vendedorNombre;
        this.ayudanteId = ayudanteId;
        this.ayudanteNombre = ayudanteNombre;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.otrosProductos = otrosProductos;
        this.creditosCobrados = creditosCobrados;
        this.creditosOtorgados = creditosOtorgados;
        this.gastosMedicos = gastosMedicos;
        this.refacciones = refacciones;
        this.combustible = combustible;
        this.promocion = promocion;
        this.efectivo = efectivo;
        this.totalLiquidar = totalLiquidar;
        this.estado = estado;
    }

    public VentaRuta(int id, int usuarioId, String usuarioNombre, int rutaId, String rutaNombre, String numComprobante, Date fecha, double totalLiquidar, double efectivo, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.rutaId = rutaId;
        this.rutaNombre = rutaNombre;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.efectivo = efectivo;
        this.totalLiquidar=totalLiquidar;
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

    public double getPromocion() {
        return promocion;
    }

    public void setPromocion(double promocion) {
        this.promocion = promocion;
    }

    public double getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(double efectivo) {
        this.efectivo = efectivo;
    }

    public double getTotalLiquidar() {
        return totalLiquidar;
    }

    public void setTotalLiquidar(double totalLiquidar) {
        this.totalLiquidar = totalLiquidar;
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
