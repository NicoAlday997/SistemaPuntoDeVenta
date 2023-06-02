/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import java.sql.Connection;
import database.Conexion;
import datos.interfaces.CrudVentaInterface;
import entidades.DetalleVentaRuta;
import entidades.VentaRuta;  
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
public class VentaRutaDAO implements CrudVentaInterface<VentaRuta, DetalleVentaRuta>{

    private final Conexion CON;
    private java.sql.PreparedStatement ps;
    private java.sql.ResultSet rs;
    private CallableStatement stmt=null;
    private boolean resp;
        
    public VentaRutaDAO()
    {
        CON=Conexion.getInstancia();
    }
    
    @Override
    public List<VentaRuta> listar(String texto, int totalPorPagina, int numPagina) {
        List<VentaRuta> registros=new ArrayList();
		
		try {   //ps=CON.conectar().prepareStatement("SELECT v.id,v.usuario_id,u.nombre as usuario_nombre, v.ruta_id, r.nombre as ruta_nombre,v.vendedor_id, e1.nombre as vendedor_nombre,v.ayudante_id, e2.nombre as ayudante_nombre,v.serie_comprobante,v.num_comprobante, v.fecha, v.otros_productos,v.creditos_cobrados, v.creditos_otorgados,v.gastos_medicos, v.refacciones, v.combustible, otros_gastos ,v.total_liquidar,(select sum(utilidad) from detalle_venta_ruta where detalle_venta_ruta.venta_ruta_id=v.id) as utilidad,v.efectivo, v.estado FROM venta_ruta v INNER JOIN ruta r ON v.ruta_id=r.id INNER JOIN usuario u ON v.usuario_id=u.id INNER JOIN empleado e1 ON v.vendedor_id=e1.id INNER JOIN empleado e2 ON v.ayudante_id=e2.id WHERE v.num_comprobante LIKE ? ORDER BY v.id ASC LIMIT ?,?");
			ps=CON.conectar().prepareStatement("SELECT v.id,v.usuario_id,u.nombre as usuario_nombre, v.ruta_id, r.nombre as ruta_nombre,v.vendedor_id, e1.nombre as vendedor_nombre,v.ayudante_id, e2.nombre as ayudante_nombre,v.serie_comprobante,v.num_comprobante, v.fecha, v.otros_productos,v.creditos_cobrados, v.creditos_otorgados,v.gastos_medicos, v.refacciones, v.combustible, otros_gastos ,(select sum(descuento) from detalle_venta_ruta where detalle_venta_ruta.venta_ruta_id=v.id) as descuento_producto,v.total_liquidar,(select sum(utilidad) from detalle_venta_ruta where detalle_venta_ruta.venta_ruta_id=v.id) as utilidad,v.efectivo, v.estado FROM venta_ruta v INNER JOIN ruta r ON v.ruta_id=r.id INNER JOIN usuario u ON v.usuario_id=u.id INNER JOIN empleado e1 ON v.vendedor_id=e1.id INNER JOIN empleado e2 ON v.ayudante_id=e2.id WHERE v.num_comprobante LIKE ? ORDER BY v.id ASC LIMIT ?,?");

                        ps.setString(1,"%" + texto +"%");
                        ps.setInt(2, (numPagina-1)*totalPorPagina);
                        ps.setInt(3, totalPorPagina);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
                                registros.add(new VentaRuta(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4),rs.getString(5),rs.getInt(6),rs.getString(7),rs.getInt(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getDate(12), rs.getDouble(13),rs.getDouble(14),rs.getDouble(15), rs.getDouble(16),rs.getDouble(17),rs.getDouble(18),rs.getDouble(19),rs.getDouble(20),rs.getDouble(21),rs.getDouble(22),rs.getDouble(23),rs.getString(24)));

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
    public List<DetalleVentaRuta> listarDetalle(int id) {
        List<DetalleVentaRuta> registros=new ArrayList();
		
		try { 
                   ps=CON.conectar().prepareStatement("SELECT a.id,a.codigo,a.nombre,a.formato,a.stock,a.piezas,d.cantidad,d.piezas,d.precio,d.descuento,(((d.cantidad*precio)+(d.piezas*(precio/a.formato)))-d.descuento) as sub_total, c.comision,d.comision,d.utilidad FROM detalle_venta_ruta d INNER JOIN articulo a ON d.articulo_id=a.id INNER JOIN categoria c ON a.categoria_id=c.id WHERE d.venta_ruta_id=?");

                    //ps=CON.conectar().prepareStatement("SELECT a.id,a.codigo,a.nombre,a.formato,a.stock,a.piezas,d.cantidad,d.piezas,d.precio,d.descuento,((d.cantidad*precio)+(d.piezas*(precio/a.formato))) as sub_total, c.comision,d.comision,d.utilidad FROM detalle_venta_ruta d INNER JOIN articulo a ON d.articulo_id=a.id INNER JOIN categoria c ON a.categoria_id=c.id WHERE d.venta_ruta_id=?");

			ps.setInt(1,id);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new DetalleVentaRuta(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getDouble(9), rs.getDouble(10), rs.getDouble(11), rs.getDouble(12),rs.getDouble(13), rs.getDouble(14)));
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
    public boolean insertar(VentaRuta obj) {
        resp=false;
        Connection conn=null;
		try { 
                    conn=CON.conectar();
                    conn.setAutoCommit(false);
                    System.out.println("primero");
                    String sqlInsertVentaR="INSERT INTO venta_ruta (usuario_id, ruta_id, vendedor_id, ayudante_id, serie_comprobante,num_comprobante, fecha, otros_productos, creditos_cobrados, creditos_otorgados, gastos_medicos, refacciones, combustible, otros_gastos, efectivo, total_liquidar, estado)VALUES (?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?,?)";
  
                    ps = conn.prepareStatement(sqlInsertVentaR, Statement.RETURN_GENERATED_KEYS);
                    
                    ps.setInt(1, obj.getUsuarioId());
                    ps.setInt(2, obj.getRutaId());
                    ps.setInt(3, obj.getVendedorId());
                    ps.setInt(4, obj.getAyudanteId());
                    ps.setString(5, obj.getSerieComprobante());
                    ps.setString(6, obj.getNumComprobante());
                    ps.setDouble(7, obj.getOtrosProductos());
                    ps.setDouble(8, obj.getCreditosCobrados());
                    ps.setDouble(9, obj.getCreditosOtorgados());
                    ps.setDouble(10, obj.getGastosMedicos());
                    ps.setDouble(11, obj.getRefacciones());
                    ps.setDouble(12, obj.getCombustible());
                    ps.setDouble(13, obj.getOtrosGastos()); 
                    ps.setDouble(14, obj.getEfectivo());
                    ps.setDouble(15, obj.getTotalLiquidar());
                    ps.setString(16, "Aceptado");
                    
                    int filasAfectadas=ps.executeUpdate();
                    rs=ps.getGeneratedKeys();
                    int idGenerado=0;
                    
                    if(rs.next()){
                        idGenerado=rs.getInt(1);
                    }
                    
                    if(filasAfectadas==1){
                        String sqlInsertDetalleR="INSERT INTO detalle_venta_ruta (venta_ruta_id, articulo_id, cantidad, piezas,precio,descuento,utilidad,comision) VALUES (?,?,?,?,?,?,?,?)";
                        ps=conn.prepareStatement(sqlInsertDetalleR);
                        
                                             
                        for(DetalleVentaRuta item: obj.getDetalles()){

                            ps.setInt(1, idGenerado);
                            ps.setInt(2,item.getArticuloId());
                            ps.setInt(3, item.getCantidad());
                            ps.setInt(4, item.getPiezas());
                            ps.setDouble(5, item.getPrecio());
                            ps.setDouble(6, item.getDescuento());

                            String sql3="{CALL decremento(?,?,?,?,?,?)}";
                           
                            stmt=conn.prepareCall(sql3);
                            
                            stmt.setInt(1, item.getArticuloId());
                            stmt.setInt(2, item.getCantidad());
                            stmt.setInt(3, item.getPiezas());
                            stmt.setDouble(4, item.getPrecio());
                            stmt.setDouble(5, item.getDescuento());
                            stmt.registerOutParameter(6, java.sql.Types.DECIMAL);

                            stmt.execute();
                            
                            ps.setDouble(7, stmt.getDouble(6));
                            ps.setDouble(8, item.getComisionD());
                            
                            
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
                Logger.getLogger(VentaRutaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
		}finally{

            try {
                if(rs!=null) rs.close();
                if(ps!=null) ps.close();
                if(conn!=null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(VentaRutaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
		}
		
		return resp;
    }
    
   

    @Override
    public boolean anular(int id) {

        resp=false;
		try {
			ps=CON.conectar().prepareStatement("UPDATE venta_ruta SET estado='Anulado' WHERE id=?");
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
			ps=CON.conectar().prepareStatement("SELECT COUNT(id) FROM venta_ruta");

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
			
                        ps=CON.conectar().prepareStatement("SELECT id FROM venta_ruta WHERE serie_comprobante=? AND num_comprobante=?", java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_UPDATABLE);

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
    
    
     public String ultimoSerie() {
        String serieComprobante="";
        try {
            ps=CON.conectar().prepareStatement("SELECT serie_comprobante FROM venta_ruta order by cast(serie_comprobante as decimal) desc limit 1");            

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
    
    public String ultimoNumero(String serieComprobante) {
        String numComprobante="";
        try {
            ps=CON.conectar().prepareStatement("SELECT num_comprobante FROM venta_ruta WHERE serie_comprobante=? order by cast(num_comprobante as decimal) desc limit 1");            
            ps.setString(1, serieComprobante);
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
    
    
    public List<VentaRuta> consultaFechas(Date fechaInicio, Date fechaFin) {
        List<VentaRuta> registros=new ArrayList();
        try {
            //ps=CON.conectar().prepareStatement("SELECT v.id,v.usuario_id,u.nombre as usuario_nombre,v.ruta_id,r.nombre as ruta_nombre,v.num_comprobante,v.fecha,v.total_liquidar,(select sum(utilidad) from detalle_venta_ruta where detalle_venta_ruta.venta_ruta_id=v.id) as utilidad,v.efectivo,v.estado FROM venta_ruta v INNER JOIN ruta r ON v.ruta_id=r.id INNER JOIN usuario u ON v.usuario_id=u.id WHERE v.fecha>=? AND v.fecha<=?");
            ps=CON.conectar().prepareStatement("SELECT v.id,v.usuario_id,u.nombre as usuario_nombre, v.ruta_id, r.nombre as ruta_nombre,v.vendedor_id, e1.nombre as vendedor_nombre,v.ayudante_id, e2.nombre as ayudante_nombre,v.serie_comprobante,v.num_comprobante, v.fecha, v.otros_productos,v.creditos_cobrados, v.creditos_otorgados,v.gastos_medicos, v.refacciones, v.combustible, otros_gastos ,(select sum(descuento) from detalle_venta_ruta where detalle_venta_ruta.venta_ruta_id=v.id) as descuento_producto,v.total_liquidar,(select sum(utilidad) from detalle_venta_ruta where detalle_venta_ruta.venta_ruta_id=v.id) as utilidad,v.efectivo, v.estado FROM venta_ruta v INNER JOIN ruta r ON v.ruta_id=r.id INNER JOIN usuario u ON v.usuario_id=u.id INNER JOIN empleado e1 ON v.vendedor_id=e1.id INNER JOIN empleado e2 ON v.ayudante_id=e2.id WHERE v.fecha>=? AND v.fecha<=?");

            ps.setDate(1,fechaInicio);            
            ps.setDate(2,fechaFin);
            rs=ps.executeQuery();
            while(rs.next()){
                //registros.add(new VentaRuta(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6),rs.getDate(7),rs.getDouble(8),rs.getDouble(9),rs.getDouble(10), rs.getString(11)));
                registros.add(new VentaRuta(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4),rs.getString(5),rs.getInt(6),rs.getString(7),rs.getInt(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getDate(12), rs.getDouble(13),rs.getDouble(14),rs.getDouble(15), rs.getDouble(16),rs.getDouble(17),rs.getDouble(18),rs.getDouble(19),rs.getDouble(20),rs.getDouble(21),rs.getDouble(22),rs.getDouble(23),rs.getString(24)));

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
