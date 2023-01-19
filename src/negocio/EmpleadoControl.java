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
import datos.PuestoDAO;
import datos.EmpleadoDAO;
import entidades.Puesto;
import entidades.Empleado;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import java.sql.Date;

public class EmpleadoControl
{

	private final EmpleadoDAO DATOS;
        private final PuestoDAO DATOSPUESTO;
	private Empleado obj;
	private DefaultTableModel modeloTabla;
	public int registrosMostrados;
       // public SimpleDateFormat sdf;
	
	public EmpleadoControl()  
	{
		this.DATOS=new EmpleadoDAO();
                this.DATOSPUESTO=new PuestoDAO();
		this.obj=new Empleado();
		this.registrosMostrados=0;
                //sdf=new SimpleDateFormat("dd/MM/yyyy");
	}
	
	public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina)
	{
		List<Empleado> lista=new ArrayList();
		lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));
		
		String[] titulos= {"Id","Puesto ID","Puesto","Empleado","Apellidos", "Sexo", "Fecha entrada", "Fecha nacimiento","direccion","telefono", "Estado"};
		//String[] titulos= {"Holaaaa","RolID","Usuario","Documento", "# Documento", "Dirección", "Telefono","Email","Clave", "Estado"};

		this.modeloTabla=new DefaultTableModel(null, titulos);
		
		String estado;
		
		String[] registro=new String[11];
		
		this.registrosMostrados=0;
		
		for(Empleado item:lista)
		{
			if(item.isActivo())
			{
				estado="Activo";
			}else
			{
				estado="Inactivo";
			}
                        
                        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			
			registro[0]=Integer.toString(item.getId());
                        registro[1]=Integer.toString(item.getPuestoId());
                        registro[2]=item.getPuestoNombre();
                        registro[3]=item.getNombre();
			registro[4]=item.getApellidos();
                        registro[5]=item.getSexo();
                        registro[6]=sdf.format(item.getFecha_entrada());              
			registro[7]=sdf.format(item.getFecha_nacimiento());
                        
                        
                        registro[8]=item.getDireccion();
                        registro[9]=item.getTelefono();
			registro[10]=estado;
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        
        /*
        public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina)
	{
		List<Empleado> lista=new ArrayList();
		lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));
		
		String[] titulos= {"Id","Rol ID","Rol","Usuario","Documento", "# Documento", "Dirección", "Telefono","Email","Clave", "Estado"};
		//String[] titulos= {"Holaaaa","RolID","Usuario","Documento", "# Documento", "Dirección", "Telefono","Email","Clave", "Estado"};

		this.modeloTabla=new DefaultTableModel(null, titulos);
		
		String estado;
		
		String[] registro=new String[11];
		
		this.registrosMostrados=0;
		
		for(Empleado item:lista)
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
			registro[4]=item.getTipoDocumento();
                        registro[5]=item.getNumDocumento();
                        registro[6]=item.getDireccion();              
			registro[7]=item.getTelefono();
                        registro[8]=item.getEmail();
                        registro[9]=item.getClave();
			registro[10]=estado;
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        */
        
        /*public String login(String email, String clave)
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
        }*/
        
        public DefaultComboBoxModel seleccionar()
        {
            DefaultComboBoxModel items=new DefaultComboBoxModel();
            List<Puesto> lista=new ArrayList();
            lista=DATOSPUESTO.seleccionar();
            for(Puesto item: lista)
            {
                items.addElement(new Puesto(item.getId(), item.getNombre()));
                
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
	
	public String insertar(int PuestoId, String nombre, String apellidos, String sexo, Date fecha_entrada, Date fecha_nacimiento,String direccion, String telefono )
	{
		if(DATOS.existe(nombre))
		{
			return "El registro ya existe";
			
		}else
		{
                    
                    obj.setPuestoId(PuestoId);
                    obj.setNombre(nombre);
                    obj.setApellidos(apellidos);
                    obj.setSexo(sexo);
                    obj.setFecha_entrada(fecha_entrada);
                    obj.setFecha_nacimiento(fecha_nacimiento);
                    obj.setDireccion(direccion);
                    obj.setTelefono(telefono);
                    
			if(DATOS.insertar(obj))
			{
				return "OK";
			}else
			{
				return "Error en el registro.";
			}
			
		}
	}
	
	public String actualizar(int id, int puestoId, String nombre, String apellidos, String sexo, Date fecha_entrada, Date fecha_nacimiento,String direccion, String telefono, String telefonoAnt)
	{
		if(telefono.equals(telefonoAnt))
		{
                    obj.setId(id);
                    obj.setPuestoId(puestoId);
                    obj.setNombre(nombre);
                    obj.setApellidos(apellidos);
                    obj.setSexo(sexo);
                    obj.setFecha_entrada(fecha_entrada);
                    obj.setFecha_nacimiento(fecha_nacimiento);
                    obj.setDireccion(direccion);
                    obj.setTelefono(telefono);
                    String encriptado;
                    
                    if(telefono.length()==64)
                    {
                        encriptado=telefono;
                    }else
                    {
                        encriptado=this.encriptar(telefono);
                    }
                    
                    //obj.setClave(encriptado);
			
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
			if(DATOS.existe(telefono))
			{
				return "El registro ya existe";
			}else
			{
		            obj.setId(id);
                            obj.setPuestoId(puestoId);
                            obj.setNombre(nombre);
                            obj.setApellidos(apellidos);
                            obj.setSexo(sexo);
                            obj.setFecha_entrada(fecha_entrada);
                            obj.setFecha_nacimiento(fecha_nacimiento);
                            obj.setDireccion(direccion);
                            obj.setTelefono(telefono);
                            String encriptado;

                            if (telefono.length() == 64) {
                                encriptado = telefono;
                            } else {
                                encriptado = this.encriptar(telefono);
                            }
                            //obj.setClave(encriptado);
				
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
        
        /*
        public String actualizar(int id, int RolId, String nombre, String tipoDocumento, String numDocumento, String direccion, String telefono,String email,String emailAnt, String clave)
	{
		if(email.equals(emailAnt))
		{
                    obj.setId(id);
                    obj.setRolId(RolId);
                    obj.setNombre(nombre);
                    obj.setTipoDocumento(tipoDocumento);
                    obj.setNumDocumento(numDocumento);
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
                            obj.setTipoDocumento(tipoDocumento);
                            obj.setNumDocumento(numDocumento);
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
        */
	
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

