/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author alday
 */

import database.Conexion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import datos.ArticuloDAO;
import datos.VentaDAO;
import entidades.Articulo;
import entidades.DetalleVenta;
import entidades.Venta;
import java.io.File;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class VentaControl
{

	private final VentaDAO DATOS;
        private final ArticuloDAO DATOSART;
	private Venta obj;
	private DefaultTableModel modeloTabla;
	public int registrosMostrados;
	
	public VentaControl()
	{
		this.DATOS=new VentaDAO();
                this.DATOSART=new ArticuloDAO();
		this.obj=new Venta();
		this.registrosMostrados=0;
	}
	
	public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina)
	{
		List<Venta> lista=new ArrayList();
		lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));
		
		String[] titulos= {"Id","Usuario Id","Usuario","Cliente ID", "Cliente","Tipo Comprobante","Serie", "Numero","Fecha", "Impuesto","Descuento P.","Total","Utilidad", "Estado"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
				
		String[] registro=new String[14];
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		this.registrosMostrados=0;
		
		for(Venta item:lista)
		{
			registro[0]=Integer.toString(item.getId());
                        registro[1]=Integer.toString(item.getUsuarioId());
                        registro[2]=item.getUsuarioNombre();
                        registro[3]=Integer.toString(item.getPersonaId());
                        registro[4]=item.getPersonaNombre();
                        registro[5]=item.getTipoComprobante();
                        registro[6]=item.getSerieComprobante();
                        registro[7]=item.getNumComprobante();
                        registro[8]=sdf.format(item.getFecha());
                        registro[9]=Double.toString(item.getImpuesto());
                        registro[10]=Double.toString(item.getDescuentoProducto());
                        registro[11]=Double.toString(item.getTotal());
                        registro[12]=Double.toString(item.getTotUtilidad());
                        registro[13]=item.getEstado();
                                            
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        
        public DefaultTableModel listarDetalle(int id)
	{
		List<DetalleVenta> lista=new ArrayList();
		lista.addAll(DATOS.listarDetalle(id));
		
		//String[] titulos= {"ID","CODIGO","ARTICULO","STOCK","CANTIDAD", "PIEZAS", "PRECIO","DESCUENTO","SUBTOTAL", "UTILIDAD"};
		String[] titulos= {"ID","CODIGO","ARTICULO","FORMATO","STOCK","S. PZS","CANTIDAD","PIEZAS","PRECIO","DESCUENTO","SUBTOTAL","UTILIDAD"};
		this.modeloTabla=new DefaultTableModel(null, titulos);
				
		String[] registro=new String[12];
		
		for(DetalleVenta item:lista)
		{
			registro[0]=Integer.toString(item.getArticuloId());//ID
                        registro[1]=item.getArticuloCodigo();//CODIGO
                        registro[2]=item.getArticuloNombre();//NOMBRE ARTICULO
                        registro[3]=Integer.toString(item.getArticuloFormato());//FORMATO
                        registro[4]=Integer.toString(item.getArticuloStock());//STOCK
                        registro[5]=Integer.toString(item.getArticuloStockPzs());//S.PZS
                        registro[6]=Integer.toString(item.getCantidad());//CANTIDAD
                        registro[7]=Integer.toString(item.getPiezas());//PIEZAS
                        registro[8]=Double.toString(item.getPrecio());//PRECIO
                        registro[9]=Double.toString(item.getDescuento());//DESCUENTO
                        registro[10]=String.format("%.2f",item.getSubTotal());//SUBTOTAL
                        registro[11]=Double.toString(item.getUtilidad());//UTILIDAD
                       
                        this.modeloTabla.addRow(registro);			
		}
		
		return this.modeloTabla;
	}
        
        public String ultimoSerie(String tipoComprobante) {
        return this.DATOS.ultimoSerie(tipoComprobante);
        }
        
        public String ultimoNumero(String tipoComprobante,String serieComprobante) {
        return this.DATOS.ultimoNumero(tipoComprobante, serieComprobante);
        }
        
        public Articulo obtenerArticuloCodigoVenta(String codigo){
            Articulo art=DATOSART.obtenerArticuloCodigoVenta(codigo);
            return art;
        }
	
        public String insertar(int personaId,String tipoComprobante, String serieComprobante,String numComprobante, double impuesto, double total, DefaultTableModel modeloDetalles)

	{
		if(DATOS.existe(serieComprobante, numComprobante))
		{
			return "El registro ya existe";
			
		}else
		{
                    obj.setUsuarioId(Variables.usuarioId);
                    obj.setPersonaId(personaId);
                    obj.setTipoComprobante(tipoComprobante);
                    obj.setSerieComprobante(serieComprobante);
                    obj.setNumComprobante(numComprobante);
                    obj.setImpuesto(impuesto);
                    obj.setTotal(total);
                    
                    List<DetalleVenta> detalles=new ArrayList();
                    int articuloId;
                    int cantidad;
                    double precio;
                    double descuento;
                    int piezas;

                    for(int i=0; i<modeloDetalles.getRowCount(); i++){
                        articuloId=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 0)));
                        cantidad=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 6)));
                        piezas=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 7)));
                        precio=Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 8)));
                        descuento=Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 9)));
                       
                        detalles.add(new DetalleVenta(articuloId,cantidad, piezas, precio, descuento, 0));
                    }
                    obj.setDetalles(detalles);
                    
			if(DATOS.insertar(obj))
			{
				return "OK";
			}else
			{
				return "Error en el registro.";
			}
			
		}
	}

	
	public String anular(int id)
	{
		if(DATOS.anular(id))
		{
			return "OK";
		}else {
			return "No se puede anular el registro";
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
        
        public void reporteComprobante(String idventa)
        {
            Map p=new HashMap();
            p.put("idventa", idventa);
            JasperReport report;
            JasperPrint print;
            
            Conexion cnn=Conexion.getInstancia();
            
            try {
                report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+
                        "/src/reportes/RptComprobante.jrxml");
                print=JasperFillManager.fillReport(report, p, cnn.conectar());
                JasperViewer view=new JasperViewer(print,false);
                view.setTitle("Reporte de articulos");
                view.setVisible(true);
            } catch (JRException e) {
                e.getMessage();
            }
        }
        
        public DefaultTableModel consultaFechas(Date fechaInicio, Date fechaFin){
        List<Venta> lista=new ArrayList();
        lista.addAll(DATOS.consultaFechas(fechaInicio,fechaFin));
        
        String[] titulos={"Id","Usuario Id","Usuario","Cliente ID", "Cliente","Tipo Comprobante","Serie", "Numero","Fecha", "Impuesto","Descuento P.","Total","Utilidad", "Estado"};

        //String[] titulos={"Id","Usuario ID","Usuario","Cliente ID", "Cliente","Tipo Comprobante","Serie", "Número","Fecha", "Impuesto","Total","Estado"};
        this.modeloTabla=new DefaultTableModel(null,titulos);        
        
        String[] registro = new String[14];
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        
        this.registrosMostrados=0;
        for (Venta item:lista){
            registro[0]=Integer.toString(item.getId());
            registro[1]=Integer.toString(item.getUsuarioId());
            registro[2]=item.getUsuarioNombre();
            registro[3]=Integer.toString(item.getPersonaId());
            registro[4]=item.getPersonaNombre();
            registro[5]=item.getTipoComprobante();
            registro[6]=item.getSerieComprobante();
            registro[7]=item.getNumComprobante();
            registro[8]=sdf.format(item.getFecha());
            registro[9]=Double.toString(item.getImpuesto());
            registro[10]=Double.toString(item.getDescuentoProducto());
            registro[11]=Double.toString(item.getTotal());
            registro[12]=Double.toString(item.getTotUtilidad());
            registro[13]=item.getEstado();
            
            this.modeloTabla.addRow(registro);
            this.registrosMostrados=this.registrosMostrados+1;
        }
        return this.modeloTabla; 
    }
}

