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
import datos.EmpleadoDAO;
import datos.PuestoDAO;
import datos.VentaRutaDAO;
import entidades.Articulo;
import entidades.DetalleVentaRuta;
import entidades.Puesto;
import entidades.Empleado;
import entidades.VentaRuta;
import java.io.File;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class VentaControlRuta
{

	private final VentaRutaDAO DATOS;
        private final ArticuloDAO DATOSART;
        private final EmpleadoDAO DATOSEMPLEADO;
	private VentaRuta obj;
	private DefaultTableModel modeloTabla;
	public int registrosMostrados;
	
	public VentaControlRuta()
	{
		this.DATOS=new VentaRutaDAO();
                this.DATOSART=new ArticuloDAO();
                this.DATOSEMPLEADO=new EmpleadoDAO();
		this.obj=new VentaRuta();
		this.registrosMostrados=0;
	}
	
	public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina)
	{
		List<VentaRuta> lista=new ArrayList();
		lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));
		
		String[] titulos= {"Id","Usuario Id","Usuario","Ruta ID", "Ruta","Numero","Fecha","Total","Efectivo", "Estado"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
				
		String[] registro=new String[10];
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		this.registrosMostrados=0;
		
		for(VentaRuta item:lista)
		{
			registro[0]=Integer.toString(item.getId());
                        registro[1]=Integer.toString(item.getUsuarioId());
                        registro[2]=item.getUsuarioNombre();
                        registro[3]=Integer.toString(item.getRutaId());
                        registro[4]=item.getRutaNombre();
                        registro[5]=item.getNumComprobante();
                        registro[6]=sdf.format(item.getFecha());
                        registro[7]=Double.toString(item.getTotalLiquidar());
                        registro[8]=Double.toString(item.getEfectivo());
                        registro[9]=item.getEstado();
                                            
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        
        /*
        En este punto ya habia editado este metodo
        
        public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina)
	{
		List<VentaRuta> lista=new ArrayList();
		lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));
		
		String[] titulos= {"Id","Usuario Id","Usuario","Cliente ID", "Cliente","Tipo Comprobante","Serie", "Numero","Fecha", "Impuesto","Total","Utilidad", "Estado"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
				
		String[] registro=new String[13];
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
                        registro[10]=Double.toString(item.getTotal());
                        registro[11]=Double.toString(item.getTotUtilidad());
                        registro[12]=item.getEstado();
                        
                        
                        
                        
                        
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        */
        
        public DefaultTableModel listarDetalle(int id)
	{
		List<DetalleVentaRuta> lista=new ArrayList();
		lista.addAll(DATOS.listarDetalle(id));
		
		String[] titulos= {"ID","CODIGO","ARTICULO","STOCK","CANTIDAD", "PRECIO","DESCUENTO","SUBTOTAL", "UTILIDAD"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
				
		String[] registro=new String[9];
		
		for(DetalleVentaRuta item:lista)
		{
			registro[0]=Integer.toString(item.getArticuloId());
                        registro[1]=item.getArticuloCodigo();
                        registro[2]=item.getArticuloNombre();
                        registro[3]=Integer.toString(item.getArticuloStock());
                        registro[4]=Integer.toString(item.getCantidad());
                        registro[5]=Double.toString(item.getPrecio());
                        registro[6]=Double.toString(item.getDescuento());
                        registro[7]=Double.toString(item.getSubTotal());
                        registro[8]=Double.toString(item.getUtilidad());
                       
                        this.modeloTabla.addRow(registro);			
		}
		
		return this.modeloTabla;
	}
        
        public DefaultComboBoxModel seleccionar()
        {
            DefaultComboBoxModel items=new DefaultComboBoxModel();
            List<Empleado> lista=new ArrayList();
            lista=DATOSEMPLEADO.seleccionar();
            for(Empleado item: lista)
            {
                items.addElement(new Empleado(item.getId(), item.getNombre()));
                
            }
            return items;
            
        }
        
        public DefaultComboBoxModel seleccionarAyudante()
        {
            DefaultComboBoxModel items=new DefaultComboBoxModel();
            List<Empleado> lista=new ArrayList();
            lista=DATOSEMPLEADO.seleccionarAyudante();
            for(Empleado item: lista)
            {
                items.addElement(new Empleado(item.getId(), item.getNombre()));
                
            }
            
            
            return items;
            
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
	
	//public String insertar(int personaId,String tipoComprobante, String serieComprobante,String numComprobante, double impuesto, double total, double totUtilidad, DefaultTableModel modeloDetalles)
        public String insertar(int rutaId, int vendedorId, int ayudanteId, String numComprobante, double otrosProductos, double creditosCobrados, double creditosOtorgados, double gastosMedicos, double refacciones, double combustible, double promocion, double efectivo, double totalLiquidar, DefaultTableModel modeloDetalles)
	{
		if(DATOS.existe("nada",numComprobante))
		{
			return "El registro ya existe";
			
		}else
		{
                    obj.setUsuarioId(Variables.usuarioId);
                    obj.setRutaId(rutaId);
                    obj.setVendedorId(vendedorId);
                    obj.setAyudanteId(ayudanteId);
                    obj.setNumComprobante(numComprobante);
                    obj.setOtrosProductos(otrosProductos);
                     System.out.println("c cobrados: " + creditosCobrados);

                    obj.setCreditosCobrados(creditosCobrados);
                    obj.setCreditosOtorgados(creditosOtorgados);
                    obj.setGastosMedicos(gastosMedicos);
                    obj.setRefacciones(refacciones);
                    obj.setCombustible(combustible);
                    obj.setPromocion(promocion);
                    obj.setEfectivo(efectivo);
                    obj.setTotalLiquidar(totalLiquidar);
                    System.out.print("seteado: " + totalLiquidar);
                   // obj.setTotUtilidad(totUtilidad);
                    
                    List<DetalleVentaRuta> detalles=new ArrayList();
                    int articuloId;
                    int cantidad;
                    double precio;
                    double descuento;
                    //double utilidad;
                    
                    for(int i=0; i<modeloDetalles.getRowCount(); i++){
                        articuloId=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 0)));
                        cantidad=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 4)));   
                        precio=Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 5)));
                        descuento=Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 6)));
                        detalles.add(new DetalleVentaRuta(articuloId,cantidad,precio, descuento, 0));
                        
                    }
                    /*for(int i=0; i<modeloDetalles.getRowCount(); i++){
                        articuloId=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 0)));
                        if(String.valueOf(modeloDetalles.getValueAt(i, 4))==null || String.valueOf(modeloDetalles.getValueAt(i, 4)).equals(""))cantidad=0;
                                else cantidad=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 4)));   
                        //cantidad=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 4)));
                        precio=Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 5)));
                        descuento=Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 6)));
                        detalles.add(new DetalleVentaRuta(articuloId,cantidad,precio, descuento, 0));
                        
                    }*/
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
        
        /*
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
                   // obj.setTotUtilidad(totUtilidad);
                    
                    List<DetalleVenta> detalles=new ArrayList();
                    int articuloId;
                    int cantidad;
                    double precio;
                    double descuento;
                    //double utilidad;
                    
                    for(int i=0; i<modeloDetalles.getRowCount(); i++){
                        articuloId=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 0)));
                        cantidad=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 4)));
                        precio=Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 5)));
                        descuento=Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 6)));
                        //utilidad=Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 8)));
                        detalles.add(new DetalleVenta(articuloId,cantidad,precio, descuento, 0));
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
	}*/

	
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
        List<VentaRuta> lista=new ArrayList();
        lista.addAll(DATOS.consultaFechas(fechaInicio,fechaFin));
        
        String[] titulos={"Id","Usuario ID","Usuario","Ruta ID","Ruta","Número","Fecha","Total","Efectivo","Estado"};
        this.modeloTabla=new DefaultTableModel(null,titulos);        
        
        String[] registro = new String[10];
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        
        this.registrosMostrados=0;
        for (VentaRuta item:lista){
            registro[0]=Integer.toString(item.getId());
            registro[1]=Integer.toString(item.getUsuarioId());
            registro[2]=item.getUsuarioNombre();
            registro[3]=Integer.toString(item.getRutaId());
            registro[4]=item.getRutaNombre();
            registro[5]=item.getNumComprobante();
            registro[6]=sdf.format(item.getFecha());
            registro[7]=Double.toString(item.getTotalLiquidar());
            registro[8]=Double.toString(item.getEfectivo());
            registro[9]=item.getEstado();
            
            this.modeloTabla.addRow(registro);
            this.registrosMostrados=this.registrosMostrados+1;
        }
        return this.modeloTabla; 
    }
}

