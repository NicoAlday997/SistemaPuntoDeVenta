/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author alday
 */

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import datos.CategoriaDAO;
import entidades.Categoria;

public class CategoriaControl
{

	private final CategoriaDAO DATOS;
	private Categoria obj;
	private DefaultTableModel modeloTabla;
	public int registrosMostrados;
	
	public CategoriaControl()
	{
		this.DATOS=new CategoriaDAO();
		this.obj=new Categoria();
		this.registrosMostrados=0;
	}
	
	public DefaultTableModel listar(String texto)
	{
		List<Categoria> lista=new ArrayList();
		lista.addAll(DATOS.listar(texto));
		
		String[] titulos= {"Id", "Nombre", "Descripcion", "Comision","Estado"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
		
		String estado;
		
		String[] registro=new String[5];
		
		this.registrosMostrados=0;
		
		for(Categoria item:lista)
		{
			if(item.isActivo())
			{
				estado="Activo";
			}else
			{
				estado="Inactivo";
			}
			
			registro[0]=Integer.toString(item.getId());
			registro[1]=item.getNombre();
                        
                        if(item.getDescripcion()==null){
                            registro[2]="";
                        }else{
                            registro[2]=item.getDescripcion();
                        }
			
                        registro[3]=String.valueOf(item.getComision());
			registro[4]=estado;
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
	
	public String insertar(String nombre, String descripcion, String comision)
	{
		if(DATOS.existe(nombre))
		{
			return "El registro ya existe";
			
		}else
		{
			obj.setNombre(nombre);
			obj.setDescripcion(descripcion);
                        obj.setComision(Double.parseDouble(comision));
			if(DATOS.insertar(obj))
			{
				return "OK";
			}else
			{
				return "Error en el registro.";
			}
			
		}
	}
	
	public String actualizar(int id, String nombre, String nombreAnt, String descripcion, String comision)
	{
		if(nombre.equals(nombreAnt))
		{
			obj.setId(id);
			obj.setNombre(nombre);
			obj.setDescripcion(descripcion);
                        obj.setComision(Double.parseDouble(comision));
			
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
				obj.setNombre(nombre);
				obj.setDescripcion(descripcion);
                                obj.setComision(Double.parseDouble(comision));
				
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
}

