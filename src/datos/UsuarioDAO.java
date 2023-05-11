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
import entidades.Usuario;

public class UsuarioDAO implements CrudPaginadoInterface<Usuario>
{
	
	private final Conexion CON;
	private PreparedStatement ps;
	private ResultSet rs;
	private boolean resp;
	
	public UsuarioDAO()
	{
		CON=Conexion.getInstancia();
	}
	

	@Override
	public List<Usuario> listar(String texto,int totalPorPagina, int numPagina) 
	{
		List<Usuario> registros=new ArrayList();
                
		try {
                    ps=CON.conectar().prepareStatement("SELECT u.id, u.rol_id, r.nombre as rol_nombre, u.nombre, u.direccion, u.telefono, u.email, u.clave, u.activo FROM usuario u inner join rol r ON u.rol_id=r.id WHERE u.nombre LIKE ? ORDER BY u.id ASC LIMIT ?,?");
			ps.setString(1,"%" + texto +"%");
                        ps.setInt(2, (numPagina-1)*totalPorPagina);
                        ps.setInt(3, totalPorPagina);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new Usuario(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8), rs.getBoolean(9)));
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
        
        public Usuario login(String email, String clave)
        {
            Usuario usu=null;
            try {

                ps = CON.conectar().prepareStatement("SELECT u.id, u.rol_id, r.nombre as rol_nombre, u.nombre, u.direccion, u.telefono, u.email, u.activo FROM usuario u inner join rol r ON u.rol_id=r.id WHERE u.email=? AND clave=?",  ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setString(1,email);
                ps.setString(2, clave);
                rs = ps.executeQuery();

                if(rs.first())
                {
                    usu=new Usuario(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getBoolean(8));
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
        }
	
	

	@Override
	public boolean insertar(Usuario obj) {
	
		resp=false;
		try {
			ps=CON.conectar().prepareStatement("INSERT INTO usuario (rol_id,nombre,direccion, telefono, email, clave, activo) VALUES (?,?,?,?,?,?,1)");
			ps.setInt(1, obj.getRolId());
                        ps.setString(2,obj.getNombre());
                        ps.setString(3, obj.getDireccion());
			ps.setString(4, obj.getTelefono());
                        ps.setString(5, obj.getEmail());
                        ps.setString(6, obj.getClave());
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
    public boolean actualizar(Usuario obj) {
        resp=false;
        try {

            ps = CON.conectar().prepareStatement("UPDATE usuario SET rol_id=?, nombre=?, direccion=?, telefono=?, email=?, clave=?  WHERE id=?");
            ps.setInt(1, obj.getRolId());
            ps.setString(2, obj.getNombre());
            ps.setString(3, obj.getDireccion());
            ps.setString(4, obj.getTelefono());
            ps.setString(5, obj.getEmail());
            ps.setString(6, obj.getClave());
            ps.setInt(7, obj.getId());
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
			ps=CON.conectar().prepareStatement("UPDATE usuario SET activo=0 WHERE id=?");
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
			ps=CON.conectar().prepareStatement("UPDATE usuario SET activo=1 WHERE id=?");
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
			ps=CON.conectar().prepareStatement("SELECT COUNT(id) FROM usuario");

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
                        ps=CON.conectar().prepareStatement("SELECT email FROM usuario WHERE email=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

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
