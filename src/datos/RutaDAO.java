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
import entidades.Ruta;

public class RutaDAO implements CrudPaginadoInterface<Ruta>
{
	
	private final Conexion CON;
	private PreparedStatement ps;
	private ResultSet rs;
	private boolean resp;
	
	public RutaDAO()
	{
		CON=Conexion.getInstancia();
	}
	

	@Override
	public List<Ruta> listar(String texto,int totalPorPagina, int numPagina) 
	{
		List<Ruta> registros=new ArrayList();
                
		
		try {
                    ps=CON.conectar().prepareStatement("SELECT r.id,  r.nombre, r.cobertura, r.activo FROM ruta r WHERE r.nombre LIKE ? ORDER BY r.id ASC LIMIT ?,?");
			ps.setString(1,"%" + texto +"%");
                        ps.setInt(2, (numPagina-1)*totalPorPagina);
                        ps.setInt(3, totalPorPagina);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new Ruta(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
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
        
        public List<Ruta> listarTipo(String texto,int totalPorPagina, int numPagina, String tipoPersona) 
	{
		List<Ruta> registros=new ArrayList();

		
		try {
                    ps=CON.conectar().prepareStatement("SELECT p.id, p.tipo_persona, p.nombre, p.tipo_documento,p.num_documento, p.direccion, p.telefono, p.email, p.activo FROM persona p WHERE p.nombre LIKE ? AND tipo_persona=? ORDER BY p.id ASC LIMIT ?,?");
			ps.setString(1,"%" + texto +"%");
                        ps.setString(2,tipoPersona);
                        ps.setInt(3, (numPagina-1)*totalPorPagina);
                        ps.setInt(4, totalPorPagina);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new Ruta(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
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
	public boolean insertar(Ruta obj) {
	
		resp=false;
		try {
			ps=CON.conectar().prepareStatement("INSERT INTO ruta (nombre, cobertura, activo) VALUES (?,?,1)");
                        ps.setString(1, obj.getNombre());
                        ps.setString(2, obj.getCobertura());
                  
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
    public boolean actualizar(Ruta obj) {
        resp=false;
        try {
            //ps=CON.conectar().prepareStatement("UPDATE categoria SET nombre=?, descripcion=? WHERE id=?");

            ps = CON.conectar().prepareStatement("UPDATE ruta SET nombre=?, cobertura=? WHERE id=?");
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getCobertura());
            ps.setInt(3, obj.getId());
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
			ps=CON.conectar().prepareStatement("UPDATE ruta SET activo=0 WHERE id=?");
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
			ps=CON.conectar().prepareStatement("UPDATE ruta SET activo=1 WHERE id=?");
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
			ps=CON.conectar().prepareStatement("SELECT COUNT(id) FROM ruta");

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
                        ps=CON.conectar().prepareStatement("SELECT nombre FROM ruta WHERE nombre=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

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




/*

*/
