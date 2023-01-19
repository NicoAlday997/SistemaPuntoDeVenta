/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import java.sql.Connection;
import database.Conexion;
import datos.interfaces.CrudVentaInterface;
import entidades.DetalleVenta;
import entidades.Venta;  
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane; 
import java.sql.Statement;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.CallableStatement;

/**
 *
 * @author alday
 */
public class VentaDAO implements CrudVentaInterface<Venta, DetalleVenta>{

    private final Conexion CON;
    private java.sql.PreparedStatement ps;
    //private java.sql.PreparedStatement ps2;
    private java.sql.ResultSet rs;
   // private java.sql.ResultSet rs2;
    private CallableStatement stmt=null;
    private boolean resp;
        
    public VentaDAO()
    {
        CON=Conexion.getInstancia();
    }
    
    @Override
    public List<Venta> listar(String texto, int totalPorPagina, int numPagina) {
        List<Venta> registros=new ArrayList();
		
		try {
                    ps=CON.conectar().prepareStatement("SELECT v.id,v.usuario_id,u.nombre as usuario_nombre, v.persona_id, p.nombre as persona_nombre, v.tipo_comprobante, v.serie_comprobante,v.num_comprobante, v.fecha,v.impuesto,v.total, v.total_utilidad, v.estado FROM venta v INNER JOIN persona p ON v.persona_id=p.id INNER JOIN usuario u ON v.usuario_id=u.id WHERE v.num_comprobante LIKE ? ORDER BY v.id ASC LIMIT ?,?");
			ps.setString(1,"%" + texto +"%");
                        ps.setInt(2, (numPagina-1)*totalPorPagina);
                        ps.setInt(3, totalPorPagina);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new Venta(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4),rs.getString(5),rs.getString(6), rs.getString(7),rs.getString(8),rs.getDate(9), rs.getDouble(10), rs.getDouble(11),rs.getDouble(12),rs.getString(13)));
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
    public List<DetalleVenta> listarDetalle(int id) {
        List<DetalleVenta> registros=new ArrayList();
		
		try {
                    ps=CON.conectar().prepareStatement("SELECT a.id,a.codigo,a.nombre,a.stock,d.cantidad,d.precio,d.descuento,((d.cantidad*precio)-d.descuento) as sub_total,d.utilidad FROM detalle_venta d INNER JOIN articulo a ON d.articulo_id=a.id WHERE d.venta_id=?");
			ps.setInt(1,id);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new DetalleVenta(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),rs.getInt(5),rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9)));
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
    public boolean insertar(Venta obj) {
        resp=false;
        Connection conn=null;
		try { 
                    conn=CON.conectar();
                    conn.setAutoCommit(false);
                    String sqlInsertVenta="INSERT INTO venta (persona_id, usuario_id, fecha, tipo_comprobante, serie_comprobante, num_comprobante, impuesto, total, total_utilidad,estado)VALUES (?,?,now(),?,?,?,?,?,?,?)";
                    String sqlUtilidad="";
                    
                    //Cuando insertamos venta, hacemos calculo para la utilidad
  
                    ps = conn.prepareStatement(sqlInsertVenta, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, obj.getPersonaId());
                    ps.setInt(2, obj.getUsuarioId());
                    ps.setString(3, obj.getTipoComprobante());
                    ps.setString(4, obj.getSerieComprobante());
                    ps.setString(5, obj.getNumComprobante());
                    ps.setDouble(6, obj.getImpuesto());
                    ps.setDouble(7, obj.getTotal()); 
                    ps.setDouble(8, obj.getTotUtilidad());
                    ps.setString(9, "Aceptado");
                    
                    int filasAfectadas=ps.executeUpdate();
                    rs=ps.getGeneratedKeys();
                    int idGenerado=0;
                    
                    if(rs.next()){
                        idGenerado=rs.getInt(1);
                    }
                    
                    if(filasAfectadas==1){
                        String sqlInsertDetalle="INSERT INTO detalle_venta (venta_id, articulo_id, cantidad,precio,descuento,utilidad) VALUES (?,?,?,?,?,?)";
                        ps=conn.prepareStatement(sqlInsertDetalle);
                        
                                             
                        for(DetalleVenta item: obj.getDetalles()){
                            
                            ps.setInt(1, idGenerado);
                            ps.setInt(2,item.getArticuloId());
                            ps.setInt(3, item.getCantidad());
                            ps.setDouble(4, item.getPrecio());
                            ps.setDouble(5, item.getDescuento());
                            
                            System.out.println(item.getArticuloId()+", "+item.getCantidad()+", " + item.getPrecio()+" ,"+item.getDescuento());
                            //Hacer calculo de la utilidad por producto
                            //double uti=25;
                           
                            String sql2="{ CALL decremento(?,?,?,?,?)}";
                           
                            stmt=conn.prepareCall(sql2);
                            
                            stmt.setInt(1, item.getArticuloId());
                            stmt.setInt(2, item.getCantidad());
                            stmt.setDouble(3, item.getPrecio());
                            stmt.setDouble(4, item.getDescuento());
                            stmt.registerOutParameter(5, java.sql.Types.DECIMAL);

                            // stmt.r
                           
                            stmt.execute();
                            
         
                           // System.out.println("utilidad de: "+stmt.getDouble(5));
                            ps.setDouble(6, stmt.getDouble(5));
                            
                            
                            resp=ps.executeUpdate()>0;                     
                        }
                        conn.commit();
                    }else{
                        conn.rollback();
                    }
			
		} catch (SQLException e) {

            try {
                if(conn!=null){
                conn.rollback();    
                }
                conn.rollback();
                JOptionPane.showMessageDialog(null, e.getMessage());
            } catch (SQLException ex) {
                Logger.getLogger(VentaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
		}finally{

            try {
                if(rs!=null) rs.close();
                if(ps!=null) ps.close();
                if(conn!=null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(VentaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
		}
		
		return resp;
    }

    @Override
    public boolean anular(int id) {

        resp=false;
		try {
			ps=CON.conectar().prepareStatement("UPDATE venta SET estado='Anulado' WHERE id=?");
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
			ps=CON.conectar().prepareStatement("SELECT COUNT(id) FROM venta");

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
    public boolean existe(String texto1, String texto2) {
        resp=false;
		
		try {
			//ps=CON.conectar().prepareStatement("SELECT nombre FROM categoria WHERE nombre=?");
                        ps=CON.conectar().prepareStatement("SELECT id FROM venta WHERE serie_comprobante=? AND num_comprobante=?", java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_UPDATABLE);

			ps.setString(1, texto1);
                        ps.setString(2, texto2);

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
    
    public String ultimoSerie(String tipoComprobante) {
        String serieComprobante="";
        try {
            ps=CON.conectar().prepareStatement("SELECT serie_comprobante FROM venta where tipo_comprobante=? order by serie_comprobante desc limit 1");            
            ps.setString(1, tipoComprobante);
            rs=ps.executeQuery();
            
            while(rs.next()){
                serieComprobante=rs.getString("serie_comprobante");
            }            
            ps.close();
            rs.close();
        }  catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return serieComprobante;
    }
    
    public String ultimoNumero(String tipoComprobante,String serieComprobante) {
        String numComprobante="";
        try {
            ps=CON.conectar().prepareStatement("SELECT num_comprobante FROM venta WHERE tipo_comprobante=? AND serie_comprobante=? order by num_comprobante desc limit 1");            
            ps.setString(1, tipoComprobante);
            ps.setString(2, serieComprobante);
            rs=ps.executeQuery();
            
            while(rs.next()){
                numComprobante=rs.getString("num_comprobante");
            }            
            ps.close();
            rs.close();
        }  catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return numComprobante;
    }
    
    public List<Venta> consultaFechas(Date fechaInicio, Date fechaFin) {
        List<Venta> registros=new ArrayList();
        try {
            ps=CON.conectar().prepareStatement("SELECT v.id,v.usuario_id,u.nombre as usuario_nombre,v.persona_id,p.nombre as persona_nombre,v.tipo_comprobante,v.serie_comprobante,v.num_comprobante,v.fecha,v.impuesto,v.total,v.total_utilidad,v.estado FROM venta v INNER JOIN persona p ON v.persona_id=p.id INNER JOIN usuario u ON v.usuario_id=u.id WHERE v.fecha>=? AND v.fecha<=?");
            ps.setDate(1,fechaInicio);            
            ps.setDate(2,fechaFin);
            rs=ps.executeQuery();
            while(rs.next()){
                registros.add(new Venta(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getDate(9),rs.getDouble(10),rs.getDouble(11),rs.getDouble(12),rs.getString(13)));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return registros;
    }
}
