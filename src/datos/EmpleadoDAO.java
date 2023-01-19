/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

/**
 *
 * @author alday
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import database.Conexion;
import datos.interfaces.CrudPaginadoInterface;
import entidades.Empleado;



public class EmpleadoDAO implements CrudPaginadoInterface<Empleado>
{
	
	private final Conexion CON;
	private PreparedStatement ps;
	private ResultSet rs;
	private boolean resp;
	
	public EmpleadoDAO()
	{
		CON=Conexion.getInstancia();
	}
	

	@Override
	public List<Empleado> listar(String texto,int totalPorPagina, int numPagina) 
	{
		List<Empleado> registros=new ArrayList();
                
               // Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

               // ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY
               // Declaración stmt = dbConn.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		try {
                    ps=CON.conectar().prepareStatement("SELECT e.id, e.puesto_id, p.nombre as puesto_nombre, e.nombre, e.apellidos, e.sexo, e.fecha_entrada, e.fecha_nacimiento, e.direccion, e.telefono, e.activo FROM empleado e inner join puesto p ON e.puesto_id=p.id WHERE e.nombre LIKE ? ORDER BY e.id ASC LIMIT ?,?");
			ps.setString(1,"%" + texto +"%");
                        ps.setInt(2, (numPagina-1)*totalPorPagina);
                        ps.setInt(3, totalPorPagina);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new Empleado(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6), rs.getDate(7),rs.getDate(8),rs.getString(9),rs.getString(10), rs.getBoolean(11)));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, e.getMessage());
		}finally{
			ps=null;
			rs=null;
			CON.desconectar();
		}
		
		return registros;
	}
        
        /*public Usuario login(String email, String clave)
        {
            Usuario usu=null;
            try {
               // ps=CON.conectar().prepareStatement("SELECT nombre FROM categoria WHERE nombre=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps = CON.conectar().prepareStatement("SELECT u.id, u.rol_id, r.nombre as rol_nombre, u.nombre, u.tipo_documento, u.num_documento, u.direccion, u.telefono, u.email, u.activo FROM usuario u inner join rol r ON u.rol_id=r.id WHERE u.email=? AND clave=?",  ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setString(1,email);
                ps.setString(2, clave);
                rs = ps.executeQuery();

                if(rs.first())
                {
                    usu=new Usuario(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getBoolean(10));
                }
                ps.close();
                rs.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());

            }finally
            {
                ps=null;
                rs=null;
                CON.desconectar();
            }
            
            return usu;    
        }*/
	
	 public List<Empleado> seleccionar() 
	{
		List<Empleado> registros=new ArrayList();

		try {
			
                   // ps=CON.conectar().prepareStatement("SELECT id, nombre FROM empleado,puesto where  ORDER BY nombre asc"); 
                    ps=CON.conectar().prepareStatement("SELECT e.id, e.nombre FROM empleado e,puesto p where e.puesto_id=p.id and p.nombre='vendedor' ORDER BY nombre asc;"); 
                    
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new Empleado(rs.getInt(1), rs.getString(2)));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, e.getMessage());
		}finally{
			ps=null;
			rs=null;
			CON.desconectar();
		}
		
		return registros;
	}
         
         public List<Empleado> seleccionarAyudante() 
	{
		List<Empleado> registros=new ArrayList();

		try {
			
                    ps=CON.conectar().prepareStatement("SELECT e.id, e.nombre FROM empleado e,puesto p where e.puesto_id=p.id and p.nombre in('chofer','ayudante') ORDER BY nombre asc;"); 
                    
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new Empleado(rs.getInt(1), rs.getString(2)));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, e.getMessage());
		}finally{
			ps=null;
			rs=null;
			CON.desconectar();
		}
		
		return registros;
	}

	@Override
	public boolean insertar(Empleado obj) {
	
		resp=false;
		try {
			ps=CON.conectar().prepareStatement("INSERT INTO empleado (puesto_id,nombre,apellidos, sexo, fecha_entrada, fecha_nacimiento, direccion, telefono, activo) VALUES (?,?,?,?,?,?,?,?,1)");
			ps.setInt(1, obj.getPuestoId());
                        ps.setString(2,obj.getNombre());
                        ps.setString(3, obj.getApellidos());
                        ps.setString(4, obj.getSexo());
                        ps.setDate(5, obj.getFecha_entrada());
			ps.setDate(6, obj.getFecha_nacimiento());
                        ps.setString(7, obj.getDireccion());
                        ps.setString(8, obj.getTelefono());
			if(ps.executeUpdate()>0)
			{
				resp=true;
			}
			
			ps.close();
			
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, e.getMessage());
		}finally{

			ps=null;
			CON.desconectar();
		}
		
		return resp;
	}

        /*
	@Override
	public boolean actualizar(Categoria obj) {

		resp=false;
		try {
			ps=CON.conectar().prepareStatement("UPDATE categoria SET nombre=?, descripcion=? WHERE id=?");
			ps.setString(1, obj.getNombre());
			ps.setString(2, obj.getDescripcion());
			ps.setInt(3, obj.getId());
			if(ps.executeUpdate()>0)
			{
				resp=true;
			}
			
			ps.close();
			
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, e.getMessage());
		}finally{

			ps=null;
			CON.desconectar();
		}
		
		return resp;
	}
*/
        
        @Override
    public boolean actualizar(Empleado obj) {
        resp=false;
        try {
            //ps=CON.conectar().prepareStatement("UPDATE categoria SET nombre=?, descripcion=? WHERE id=?");

            ps = CON.conectar().prepareStatement("UPDATE empleado SET puesto_id=?, nombre=?, apellidos=?, sexo=?, fecha_entrada=?, fecha_nacimiento=?, direccion=?, telefono=?  WHERE id=?");
            ps.setInt(1, obj.getPuestoId());
            ps.setString(2, obj.getNombre());
            ps.setString(3, obj.getApellidos());
            ps.setString(4, obj.getSexo());
            ps.setDate(5, obj.getFecha_entrada());
            ps.setDate(6, obj.getFecha_nacimiento());
            ps.setString(7, obj.getDireccion());
            ps.setString(8, obj.getTelefono());
            ps.setInt(9, obj.getId());
            if (ps.executeUpdate()>0){
                resp=true;
            }
            ps.close();
        }  catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            CON.desconectar();
        }
        return resp;
    }
    
    /*
         @Override
    public boolean actualizar(Empleado obj) {
        resp=false;
        try {
            //ps=CON.conectar().prepareStatement("UPDATE categoria SET nombre=?, descripcion=? WHERE id=?");

            ps = CON.conectar().prepareStatement("UPDATE usuario SET rol_id=?, nombre=?, tipo_documento=?, num_documento=?, direccion=?, telefono=?, email=?, clave=?  WHERE id=?");
            ps.setInt(1, obj.getRolId());
            ps.setString(2, obj.getNombre());
            ps.setString(3, obj.getTipoDocumento());
            ps.setString(4, obj.getNumDocumento());
            ps.setString(5, obj.getDireccion());
            ps.setString(6, obj.getTelefono());
            ps.setString(7, obj.getEmail());
            ps.setString(8, obj.getClave());
            ps.setInt(9, obj.getId());
            if (ps.executeUpdate()>0){
                resp=true;
            }
            ps.close();
        }  catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            CON.desconectar();
        }
        return resp;
    }
    */
        

	@Override
	public boolean desactivar(int id) {

		resp=false;
		try {
			ps=CON.conectar().prepareStatement("UPDATE empleado SET activo=0 WHERE id=?");
			ps.setInt(1, id);
			if(ps.executeUpdate()>0)
			{
				resp=true;
			}
			
			ps.close();
			
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, e.getMessage());
		}finally{

			ps=null;
			CON.desconectar();
		}
		
		return resp;
	}

	@Override
	public boolean activar(int id) {

		resp=false;
		try {
			ps=CON.conectar().prepareStatement("UPDATE empleado SET activo=1 WHERE id=?");
			ps.setInt(1, id);
			if(ps.executeUpdate()>0)
			{
				resp=true;
			}
			
			ps.close();
			
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, e.getMessage());
		}finally{

			ps=null;
			CON.desconectar();
		}
		
		return resp;
	}

	@Override
	public int total() {

		int totalRegistros=0;
		try {
			ps=CON.conectar().prepareStatement("SELECT COUNT(id) FROM empleado");

			rs=ps.executeQuery();
			
			while(rs.next())
			{
				totalRegistros=rs.getInt("COUNT(id)");
			}
			
			ps.close();
			rs.close();
			
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, e.getMessage());
		}finally{

			ps=null;
			rs=null;
			CON.desconectar();
		}
		
		return totalRegistros;
	}

	@Override
	public boolean existe(String texto) {
	

		resp=false;
		
		try {
			//ps=CON.conectar().prepareStatement("SELECT nombre FROM categoria WHERE nombre=?");
                        ps=CON.conectar().prepareStatement("SELECT telefono FROM empleado WHERE telefono=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			ps.setString(1, texto);
			rs=ps.executeQuery();
			
			rs.last();
			
			if(rs.getRow()>0)
			{
				resp=true;
			}
			
			ps.close();
			rs.close();
			
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, e.getMessage());
		}finally{

			ps=null;
			rs=null;
			CON.desconectar();
		}
		
		return resp;
	}

}
