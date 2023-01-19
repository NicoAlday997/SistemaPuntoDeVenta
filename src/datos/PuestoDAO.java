/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import database.Conexion;
import entidades.Puesto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author alday
 */
public class PuestoDAO {
    
        private final Conexion CON;
	private java.sql.PreparedStatement ps;
	private java.sql.ResultSet rs;
	private boolean resp;
        
        public PuestoDAO()
        {
            CON=Conexion.getInstancia();
        }
    
    public List<Puesto> listar() 
	{ 
		List<Puesto> registros=new ArrayList();
                
               // Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

               // ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY
               // Declaración stmt = dbConn.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		try {
			
                    ps=CON.conectar().prepareStatement("SELECT * FROM puesto");
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new Puesto(rs.getInt(1), rs.getString(2), rs.getString(3)));
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
        
        public List<Puesto> seleccionar() 
	{
		List<Puesto> registros=new ArrayList();

		try {
			
                    ps=CON.conectar().prepareStatement("SELECT id, nombre FROM puesto ORDER BY nombre asc");
                    
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new Puesto(rs.getInt(1), rs.getString(2)));
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
        
        public int total() {

		int totalRegistros=0;
		try {
			ps=CON.conectar().prepareStatement("SELECT COUNT(id) FROM puesto");

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
}
