/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author alday
 */

import database.Conexion;
import java.util.ArrayList;
import java.util.List;
import datos.CategoriaDAO;
import javax.swing.table.DefaultTableModel;

import datos.ArticuloDAO;
import entidades.Articulo;
import entidades.Categoria;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class ArticuloControl
{

	private final ArticuloDAO DATOS;
        private final CategoriaDAO DATOSCAT;
	private Articulo obj;
	private DefaultTableModel modeloTabla;
	public int registrosMostrados;
	
	public ArticuloControl()
	{
		this.DATOS=new ArticuloDAO();
                this.DATOSCAT=new CategoriaDAO();
		this.obj=new Articulo();
		this.registrosMostrados=0;
	}
	
	public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina)
	{
		List<Articulo> lista=new ArrayList();
		lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));
		
		String[] titulos= {"Id","Categoria Id","Categoria","Código", "Nombre","Formato","Precio Venta","Unidades", "Piezas","Descripcion","Imagen", "Estado"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
		
		String estado;
		
		String[] registro=new String[12];
		
		this.registrosMostrados=0;
		
		for(Articulo item:lista)
		{
			if(item.isActivo())
			{
				estado="Activo";
			}else
			{
				estado="Inactivo";
			}
			
			registro[0]=Integer.toString(item.getId());
                        registro[1]=Integer.toString(item.getCategoriaId());
                        registro[2]=item.getCategoriaNombre();
                        registro[3]=item.getCodigo();
			registro[4]=item.getNombre();
                        registro[5]=Integer.toString(item.getFormato());
                        registro[6]=Double.toString(item.getPrecioVenta());
                        registro[7]=Integer.toString(item.getStock()); 
                        registro[8]=Integer.toString(item.getPiezas());
			registro[9]=item.getDescripcion();
                        registro[10]=item.getImagen();
			registro[11]=estado;
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        
        public DefaultTableModel listarArticuloCompra(String texto, int totalPorPagina, int numPagina)
	{
		List<Articulo> lista=new ArrayList();
		lista.addAll(DATOS.listarCompra(texto, totalPorPagina, numPagina));
		
		String[] titulos= {"Id","Categoria Id","Categoria","Código", "Nombre","Formato","Precio Compra","Unidades", "Piezas","Descripcion","Imagen", "Estado"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
		
		String estado;
		
		String[] registro=new String[12];
		
		this.registrosMostrados=0;
		
		for(Articulo item:lista)
		{
			if(item.isActivo())
			{
				estado="Activo";
			}else
			{
				estado="Inactivo";
			}
			
			registro[0]=Integer.toString(item.getId());
                        registro[1]=Integer.toString(item.getCategoriaId());
                        registro[2]=item.getCategoriaNombre();
                        registro[3]=item.getCodigo();
			registro[4]=item.getNombre();
                        registro[5]=Integer.toString(item.getFormato());
                        registro[6]=Double.toString(item.getPrecioVenta());
                        registro[7]=Integer.toString(item.getStock()); 
                        registro[8]=Integer.toString(item.getPiezas());
			registro[9]=item.getDescripcion();
                        registro[10]=item.getImagen();
			registro[11]=estado;
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        
        public DefaultTableModel listarArticuloVenta(String texto, int totalPorPagina, int numPagina)
	{
		List<Articulo> lista=new ArrayList();
		lista.addAll(DATOS.listarArticuloVenta(texto, totalPorPagina, numPagina));
		String[] titulos= {"Id","Categoria Id","Categoria","Código", "Nombre","Formato","Precio Venta","Stock","Piezas", "Descripcion","Imagen", "Estado"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
		
		String estado;
		
		String[] registro=new String[12];
		
		this.registrosMostrados=0;
		
		for(Articulo item:lista)
		{
			if(item.isActivo())
			{
				estado="Activo";
			}else
			{
				estado="Inactivo";
			}
			
			registro[0]=Integer.toString(item.getId());
                        registro[1]=Integer.toString(item.getCategoriaId());
                        registro[2]=item.getCategoriaNombre();
                        registro[3]=item.getCodigo();
			registro[4]=item.getNombre();
                        registro[5]=Integer.toString(item.getFormato());
                        registro[6]=Double.toString(item.getPrecioVenta());
                        registro[7]=Integer.toString(item.getStock());  
                        registro[8]=Integer.toString(item.getPiezas());       
			registro[9]=item.getDescripcion();
                        registro[10]=item.getImagen();
			registro[11]=estado;
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        
        public DefaultTableModel listarArticuloVentaRuta()
	{
		List<Articulo> lista=new ArrayList();
		lista.addAll(DATOS.listarArticuloVentaRuta());
		
		String[] titulos= {"Id","Código", "Nombre","formato","Precio Venta","Stock","Piezas","Comision"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
		
		String estado;
		
		String[] registro=new String[8];
		
		this.registrosMostrados=0;
		
		for(Articulo item:lista)
		{
			/*if(item.isActivo())
			{
				estado="Activo";
			}else
			{
				estado="Inactivo";
			}*/
			
			registro[0]=Integer.toString(item.getId());
                        registro[1]=item.getCodigo();
			registro[2]=item.getNombre();
                        registro[3]=Integer.toString(item.getFormato());
                        registro[4]=Integer.toString(item.getStock());
                        registro[5]=Integer.toString(item.getPiezas());
                        registro[6]=Double.toString(item.getPrecioVenta());
                        registro[7]=Double.toString(item.getComision());
			//registro[9]=estado;
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        
        
        public DefaultComboBoxModel seleccionar()
        {
            DefaultComboBoxModel items=new DefaultComboBoxModel();
            List<Categoria> lista=new ArrayList();
            lista=DATOSCAT.seleccionar();
            for(Categoria item: lista)
            {
                items.addElement(new Categoria(item.getId(), item.getNombre()));
                
            }
            return items;
            
        }
	
	public String insertar(int categoriaId,String codigo, String nombre,double precioVenta, int formato, String descripcion, String imagen)
	{
		if(DATOS.existe(nombre))
		{
			return "El registro ya existe";
			
		}else
		{
                    obj.setCategoriaId(categoriaId);
                    obj.setCodigo(codigo);
                    obj.setNombre(nombre);
                    obj.setPrecioVenta(precioVenta);
                    obj.setFormato(formato);
                    obj.setDescripcion(descripcion);
                    obj.setImagen(imagen);
			if(DATOS.insertar(obj))
			{
				return "OK";
			}else
			{
				return "Error en el registro.";
			}
			
		}
	}
	
	public String actualizar(int id,int categoriaId,String codigo, String nombre, String nombreAnt,double precioVenta, int formato, String descripcion, String imagen)
	{
		if(nombre.equals(nombreAnt))
		{
                    obj.setId(id);
                    obj.setCategoriaId(categoriaId);
                    obj.setCodigo(codigo);
                    obj.setNombre(nombre);
                    obj.setPrecioVenta(precioVenta);
                    obj.setFormato(formato);
                    obj.setDescripcion(descripcion);
                    obj.setImagen(imagen);
			
			if(DATOS.actualizar(obj))
			{
				return "OK";
			}
			else
			{
				return "Error en la actualización";
			}
			
		}else
		{
			if(DATOS.existe(nombre))
			{
				return "El registro ya existe";
			}else
			{
		            obj.setId(id);
                            obj.setCategoriaId(categoriaId);
                            obj.setCodigo(codigo);
                            obj.setNombre(nombre);
                            obj.setPrecioVenta(precioVenta);
                            obj.setFormato(formato);
                            obj.setDescripcion(descripcion);
                            obj.setImagen(imagen);
				
				if(DATOS.actualizar(obj))
				{
					return "OK";
				}
				else
				{
					return "Error en la actualizacion";
				}
			}
			
		}
	}
	
	public String desactivar(int id)
	{
		if(DATOS.desactivar(id))
		{
			return "OK";
		}else {
			return "No se puede desactivar el registro";
		}
		
	}
	
	public String activar(int id)
	{
		if(DATOS.activar(id))
		{
			return "OK";
		}else {
			return "No se puede activar el registro";
		}
	}
	
	public int total()
	{
		return DATOS.total();
	}
	
	public int totalMostrados()
	{
		return this.registrosMostrados;
	}
        
        
        public String ultimoNumero() {
        return this.DATOS.ultimoNumero();
        }
        
        public void reporteArticulos()
        {
            Map p=new HashMap();
            JasperReport report;
            JasperPrint print;
            
            Conexion cnn=Conexion.getInstancia();
            
            try {
                report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+
                        "/src/reportes/RptArticulos.jrxml");
                print=JasperFillManager.fillReport(report, p, cnn.conectar());
                JasperViewer view=new JasperViewer(print,false);
                view.setTitle("Reporte de articulos");
                view.setVisible(true);
            } catch (JRException e) {
                e.getMessage();
            }
        }
        
        public void reporteArticulosBarras()
        {
            Map p=new HashMap();
            JasperReport report;
            JasperPrint print;
            
            Conexion cnn=Conexion.getInstancia();
            
            try {
                report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+
                        "/src/reportes/RptArticulosBarras.jrxml");
                print=JasperFillManager.fillReport(report, p, cnn.conectar());
                JasperViewer view=new JasperViewer(print,false);
                view.setTitle("Reporte de articulos");
                view.setVisible(true);
            } catch (JRException e) {
                e.getMessage();
            }
        }
}

