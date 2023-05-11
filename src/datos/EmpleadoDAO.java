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
