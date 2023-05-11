/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Objects;

/**
 *
 * @author alday
 */
public class Articulo {
    private int Id;
    private int categoriaId;
    private String categoriaNombre;
    private String codigo;
    private String nombre;
    private int formato;
    private double precioVenta;
    private int stock;
    private int piezas; 
    private String descripcion;
    private String imagen;
    private double comision;
    private boolean activo;
   

    public Articulo() {
    }

    public Articulo(int Id, int categoriaId, String categoriaNombre, String codigo, String nombre,int formato, double precioVenta, int stock, int piezas, String descripcion, String imagen, double comision, boolean activo) {
        this.Id = Id;
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.piezas = piezas;
        this.formato = formato;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.comision = comision;
        this.activo = activo;
    }
    
    public Articulo(int Id, int categoriaId, String categoriaNombre, String codigo, String nombre,int formato, double precioVenta, int stock, int piezas, String descripcion, String imagen, boolean activo) {
        this.Id = Id;
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.piezas = piezas;
        this.formato = formato;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.activo = activo;
    }

    public Articulo(int Id, String codigo, String nombre, int formato, double precioVenta, int stock, int piezas) {
        this.Id = Id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.formato = formato;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.piezas = piezas;
        this.formato = formato;
    }
    
    public Articulo(int Id, String codigo, String nombre, int formato, double precioVenta, int stock, int piezas, double comision) {
        this.Id = Id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.formato = formato;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.piezas = piezas;
        this.formato = formato;
        this.comision = comision;
    
    }
    

    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPiezas() {
        return piezas;
    }

    public void setPiezas(int piezas) {
        this.piezas = piezas;
    }

    public int getFormato() {
        return formato;
    }

    public void setFormato(int formato) {
        this.formato = formato;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }
    
    
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    

    @Override
    public String toString() {
        return "Articulo{" + "Id=" + Id + ", categoriaId=" + categoriaId + ", categoriaNombre=" + categoriaNombre + ", codigo=" + codigo + ", nombre=" + nombre + ", precioVenta=" + precioVenta + ", stock=" + stock + ", descripcion=" + descripcion + ", imagen=" + imagen + ", activo=" + activo + '}';
    }
    
    
     
    

    
}
