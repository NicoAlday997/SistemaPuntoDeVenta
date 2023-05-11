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
import datos.RolDAO;
import datos.UsuarioDAO;
import entidades.Rol;
import entidades.Usuario;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.DefaultComboBoxModel;

public class UsuarioControl
{

	private final UsuarioDAO DATOS;
        private final RolDAO DATOSROL;
	private Usuario obj;
	private DefaultTableModel modeloTabla;
	public int registrosMostrados;
	
	public UsuarioControl()  
	{
		this.DATOS=new UsuarioDAO();
                this.DATOSROL=new RolDAO();
		this.obj=new Usuario();
		this.registrosMostrados=0;
	}
	
	public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina)
	{
		List<Usuario> lista=new ArrayList();
		lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));
		
		String[] titulos= {"Id","Rol ID","Rol","Usuario", "Dirección", "Telefono","Email","Clave", "Estado"};

		this.modeloTabla=new DefaultTableModel(null, titulos);
		
		String estado;
		
		String[] registro=new String[9];
		
		this.registrosMostrados=0;
		
		for(Usuario item:lista)
		{
			if(item.isActivo())
			{
				estado="Activo";
			}else
			{
				estado="Inactivo";
			}
			
			registro[0]=Integer.toString(item.getId());
                        registro[1]=Integer.toString(item.getRolId());
                        registro[2]=item.getRolNombre();
                        registro[3]=item.getNombre();
                        registro[4]=item.getDireccion();              
			registro[5]=item.getTelefono();
                        registro[6]=item.getEmail();
                        registro[7]=item.getClave();
			registro[8]=estado;
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        
        public String login(String email, String clave)
        {
            String resp="0";
            Usuario usu=this.DATOS.login(email,this.encriptar(clave));
            if(usu!=null)
            {
               if(usu.isActivo())
               {
                   Variables.usuarioId=usu.getId();
                   Variables.rolId=usu.getRolId();
                   Variables.rolNombre=usu.getRolNombre();
                   Variables.usuarioNombre=usu.getNombre();
                   Variables.usuarioEmail=usu.getEmail();
                   
                   resp="1";
               }else{
                   resp="2";
               }
            }
            
            return resp;
        }
        
        public DefaultComboBoxModel seleccionar()
        {
            DefaultComboBoxModel items=new DefaultComboBoxModel();
            List<Rol> lista=new ArrayList();
            lista=DATOSROL.seleccionar();
            for(Rol item: lista)
            {
                items.addElement(new Rol(item.getId(), item.getNombre()));
                
            }
            return items;
            
        }
        
        private static String encriptar(String valor){
            
            MessageDigest md;
            try {
                md=MessageDigest.getInstance("SHA-256");
                

            } catch (NoSuchAlgorithmException e) {
                return null;
            }
            
            byte[] hash = md.digest(valor.getBytes());
            StringBuilder sb=new StringBuilder();
            
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
            
        }
	
	public String insertar(int RolId, String nombre, String direccion, String telefono,String email, String clave )
	{
		if(DATOS.existe(email))
		{
			return "El registro ya existe";
			
		}else
		{
                    obj.setRolId(RolId);
                    obj.setNombre(nombre);
                    obj.setDireccion(direccion);
                    obj.setTelefono(telefono);
                    obj.setEmail(email);
                    obj.setClave(this.encriptar(clave));
			if(DATOS.insertar(obj))
			{
				return "OK";
			}else
			{
				return "Error en el registro.";
			}
			
		}
	}
	
	public String actualizar(int id, int RolId, String nombre, String direccion, String telefono,String email,String emailAnt, String clave)
	{
		if(email.equals(emailAnt))
		{
                    obj.setId(id);
                    obj.setRolId(RolId);
                    obj.setNombre(nombre);
                    obj.setDireccion(direccion);
                    obj.setTelefono(telefono);
                    obj.setEmail(email);
                    String encriptado;
                    
                    if(clave.length()==64)
                    {
                        encriptado=clave;
                    }else
                    {
                        encriptado=this.encriptar(clave);
                    }
                    obj.setClave(encriptado);
			
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
			if(DATOS.existe(email))
			{
				return "El registro ya existe";
			}else
			{
		            obj.setId(id);
                            obj.setRolId(RolId);
                            obj.setNombre(nombre);
                            obj.setDireccion(direccion);
                            obj.setTelefono(telefono);
                            obj.setEmail(email);
                            String encriptado;

                            if (clave.length() == 64) {
                                encriptado = clave;
                            } else {
                                encriptado = this.encriptar(clave);
                            }
                            obj.setClave(encriptado);
				
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

