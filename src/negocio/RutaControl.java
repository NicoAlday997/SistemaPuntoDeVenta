/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author alday
 */

import datos.PersonaDAO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import entidades.Ruta;
import datos.RutaDAO;


public class RutaControl
{

	private final RutaDAO DATOS;
	private Ruta obj;
	private DefaultTableModel modeloTabla;
	public int registrosMostrados;
	
	public RutaControl()  
	{
		this.DATOS=new RutaDAO();
		this.obj=new Ruta();
		this.registrosMostrados=0;
	}
	
	public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina)
	{
		List<Ruta> lista=new ArrayList();
		lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));
		
		String[] titulos= {"Id","Nombre","Cobertura", "Estado"};

		this.modeloTabla=new DefaultTableModel(null, titulos);
		
		String estado;
		
		String[] registro=new String[4];
		
		this.registrosMostrados=0;
		
		for(Ruta item:lista)
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
			registro[2]=item.getCobertura();
			registro[3]=estado;
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        
        public DefaultTableModel listarTipo(String texto, int totalPorPagina, int numPagina, String tipoPersona)
	{
		List<Ruta> lista=new ArrayList();
		lista.addAll(DATOS.listarTipo(texto, totalPorPagina, numPagina,tipoPersona));
		
		String[] titulos= {"Id","Nombre","Cobertura", "Estado"};

		this.modeloTabla=new DefaultTableModel(null, titulos);
		
		String estado;
		
		String[] registro=new String[4];
		
		this.registrosMostrados=0;
		
		for(Ruta item:lista)
		{
			if(item.isActivo())
			{
				estado="Activo";
			}else
			{
				estado="Inactivo";
			}
			
			registro[0]=Integer.toString(item.getId());
                        registro[2]=item.getNombre();
		        registro[3]=item.getCobertura();
			registro[4]=estado;
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        
	
	public String insertar(String nombre, String cobertura)
	{
		if(DATOS.existe(nombre))
		{
			return "El registro ya existe";
			
		}else
		{
        
                    obj.setNombre(nombre);
                    obj.setCobertura(cobertura);

			if(DATOS.insertar(obj))
			{
				return "OK";
			}else
			{
				return "Error en el registro.";
			}
			
		}
	}
	
	public String actualizar(int id,String nombre, String nombreAnt, String cobertura)
	{
		if(nombre.equals(nombreAnt))
		{
                    obj.setId(id);
                    obj.setNombre(nombre);
                    obj.setCobertura(cobertura);
     
			
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
                            obj.setCobertura(cobertura);
           

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

