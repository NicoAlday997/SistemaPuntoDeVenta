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
import entidades.Articulo;
import entidades.Comprobacion;

public class ComprobacionDAO 
{
	
	private final Conexion CON;
	private PreparedStatement ps;
	private ResultSet rs;
	private boolean resp;
	
	public ComprobacionDAO()
	{
		CON=Conexion.getInstancia();
	}
	
        
        public Comprobacion obtenerComprobacion(){
            Comprobacion comp=new Comprobacion();
            try {
                    ps=CON.conectar().prepareStatement("select sum((stock*formato)+piezas) from articulo");

			rs=ps.executeQuery();
			
			while(rs.next())
			{
				comp.setPzsArticulo(rs.getInt("sum((stock*formato)+piezas)"));
			}
                        
                    ps=CON.conectar().prepareStatement("select sum((d.existencia*a.formato)+d.existencia_piezas) from detalle_ingreso d, articulo a, ingreso i where d.articulo_id=a.id and d.ingreso_id=i.id and i.estado='aceptado'");
                        
                        rs=ps.executeQuery();
			
			while(rs.next())
			{
				comp.setPzsDetalle(rs.getInt("sum((d.existencia*a.formato)+d.existencia_piezas)"));
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
            
            return comp;
        }
        
        /*
	public Comprobacion total() {

		int totalRegistros=0;
		try {
			ps=CON.conectar().prepareStatement("SELECT COUNT(id) FROM articulo");

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
        
        
        public int total() {

		int totalRegistros=0;
		try {
			ps=CON.conectar().prepareStatement("SELECT COUNT(id) FROM articulo");

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
	}*/

}
