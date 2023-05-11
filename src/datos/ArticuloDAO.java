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

public class ArticuloDAO implements CrudPaginadoInterface<Articulo>
{
	
	private final Conexion CON;
	private PreparedStatement ps;
	private ResultSet rs;
	private boolean resp;
	
	public ArticuloDAO()
	{
		CON=Conexion.getInstancia();
	}
	

	@Override
	public List<Articulo> listar(String texto,int totalPorPagina, int numPagina) 
	{
         
		List<Articulo> registros=new ArrayList();
		try {

                    ps=CON.conectar().prepareStatement("SELECT a.id,a.categoria_id, c.nombre as categoria_nombre, a.codigo, a.nombre, a.formato, a.precio_venta, a.stock, a.piezas, a.descripcion, a.imagen, c.comision, a.activo FROM articulo a inner join categoria c ON a.categoria_id=c.id WHERE a.nombre LIKE ? ORDER BY cast(a.codigo as DECIMAL) LIMIT ?,?");
			ps.setString(1,"%" + texto +"%");
                        ps.setInt(2, (numPagina-1)*totalPorPagina);
                        ps.setInt(3, totalPorPagina);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new Articulo(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getInt(6), rs.getDouble(7),rs.getInt(8),rs.getInt(9),rs.getString(10),rs.getString(11), rs.getDouble(12),rs.getBoolean(13)));
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
        
        public List<Articulo> listarCompra(String texto,int totalPorPagina, int numPagina) 
	{
         
		List<Articulo> registros=new ArrayList();
		try {

                    ps=CON.conectar().prepareStatement(" SELECT a.id idA,a.categoria_id, c.nombre as categoria_nombre, a.codigo, a.nombre, a.formato, (select precio from articulo a, detalle_ingreso di, ingreso i where a.id=di.articulo_id and i.id=di.ingreso_id and a.id=idA and i.estado='Aceptado'" +
                                                           " order by di.id desc limit 1) precio_compra,a.stock, a.piezas, a.descripcion, a.imagen, a.activo FROM articulo a inner join categoria c ON a.categoria_id=c.id WHERE a.nombre LIKE ? ORDER BY cast(a.codigo as DECIMAL) LIMIT ?,?");
			ps.setString(1,"%" + texto +"%");
                        ps.setInt(2, (numPagina-1)*totalPorPagina);
                        ps.setInt(3, totalPorPagina);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new Articulo(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getInt(6), rs.getDouble(7),rs.getInt(8),rs.getInt(9),rs.getString(10),rs.getString(11), rs.getBoolean(12)));
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
        
        public List<Articulo> listarArticuloVenta(String texto,int totalPorPagina, int numPagina) 
	{
		List<Articulo> registros=new ArrayList();
		try {
                    ps=CON.conectar().prepareStatement("SELECT a.id,a.categoria_id, c.nombre as categoria_nombre, a.codigo, a.nombre, a.formato,a.precio_venta, a.stock, a.piezas, a.descripcion, a.imagen, a.activo FROM articulo a inner join categoria c ON a.categoria_id=c.id WHERE a.nombre LIKE ? AND (a.stock>0 or a.piezas>0) AND a.activo=true ORDER BY cast(a.codigo as DECIMAL) LIMIT ?,?");
			ps.setString(1,"%" + texto +"%");
                        ps.setInt(2, (numPagina-1)*totalPorPagina);
                        ps.setInt(3, totalPorPagina);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new Articulo(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getInt(6), rs.getDouble(7),rs.getInt(8),rs.getInt(9),rs.getString(10),rs.getString(11), rs.getBoolean(12)));
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
        
        public List<Articulo> listarArticuloVentaRuta() 
	{
		List<Articulo> registros=new ArrayList();
		try {
                      ps=CON.conectar().prepareStatement("SELECT a.id,a.codigo, a.nombre,a.formato, a.precio_venta, a.stock, a.piezas, c.comision FROM articulo a inner join categoria c ON a.categoria_id=c.id WHERE a.activo=true ORDER BY cast(a.codigo as DECIMAL)");

			rs=ps.executeQuery();
			
			while(rs.next())
			{
				registros.add(new Articulo(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getInt(4), rs.getDouble(5), rs.getInt(6), rs.getInt(7), rs.getDouble(8)));
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
        
	
	public Articulo obtenerArticuloCodigoIngreso(String codigo){
            Articulo art=null;
            try {
                    ps=CON.conectar().prepareStatement("SELECT id,codigo,nombre, formato,precio_venta, stock, piezas FROM articulo WHERE codigo=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setString(1,codigo);
                        rs=ps.executeQuery();
			
                        if(rs.first()){
                            art=new Articulo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4), rs.getDouble(5), rs.getInt(6), rs.getInt(7));
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
            
            return art;
        }
        
        public Articulo obtenerArticuloCodigoVenta(String codigo){
            Articulo art=null;
            try {
                    ps=CON.conectar().prepareStatement("SELECT id,codigo,nombre, formato,precio_venta, stock, piezas, comision FROM articulo WHERE codigo=? AND stock>0 AND activo=true", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setString(1,codigo);
                        rs=ps.executeQuery();
			
                        if(rs.first()){
                            art=new Articulo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4), rs.getDouble(5), rs.getInt(6), rs.getInt(7), rs.getDouble(8));
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
            
            return art;
        }

	@Override
	public boolean insertar(Articulo obj) {
	
		resp=false;
		try { 
			ps=CON.conectar().prepareStatement("INSERT INTO articulo (categoria_id, codigo, nombre, precio_venta, formato, descripcion,imagen, activo) VALUES (?,?,?,?,?,?,?,1)");
			ps.setInt(1, obj.getCategoriaId());
                        ps.setString(2,obj.getCodigo());
                        ps.setString(3, obj.getNombre());
                        ps.setDouble(4, obj.getPrecioVenta());
                        ps.setInt(5, obj.getFormato());
			ps.setString(6, obj.getDescripcion());
                        ps.setString(7, obj.getImagen());
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
    public boolean actualizar(Articulo obj) {
        resp=false;
        try {

            ps = CON.conectar().prepareStatement("UPDATE articulo SET categoria_id=?,codigo=?, nombre=?,precio_venta=?, formato=? ,descripcion=?, imagen=? WHERE id=?");
            ps.setInt(1, obj.getCategoriaId());
            ps.setString(2, obj.getCodigo());
            ps.setString(3, obj.getNombre());
            ps.setDouble(4, obj.getPrecioVenta());
            ps.setInt(5, obj.getFormato());
            ps.setString(6, obj.getDescripcion());
            ps.setString(7, obj.getImagen());
            ps.setInt(8, obj.getId());
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
			ps=CON.conectar().prepareStatement("UPDATE articulo SET activo=0 WHERE id=?");
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
			ps=CON.conectar().prepareStatement("UPDATE articulo SET activo=1 WHERE id=?");
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

	@Override
	public boolean existe(String texto) {
	

		resp=false;
		
		try {
                        ps=CON.conectar().prepareStatement("SELECT nombre FROM articulo WHERE nombre=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

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
        /*
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
    }*/
    
        /*
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
    }*/
    
    public String ultimoNumero() {
        String codigo="";
        try {
            ps=CON.conectar().prepareStatement("SELECT codigo FROM articulo order by cast(codigo as decimal) desc limit 1;");            
            rs=ps.executeQuery();
            
            while(rs.next()){
                codigo=rs.getString("codigo");
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
        return codigo;
    }
    

}
