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
import datos.VentaRutaDAO;
import entidades.Articulo;
import entidades.DetalleVentaRuta;
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
		
		String[] titulos= {"Id","Usuario Id","Usuario","Ruta ID", "Ruta","Vendedor ID", "Vendedor","Ayudante ID","Ayudante","Serie","Numero","Fecha","Otros Productos",
                    "Creditos cobrados","Creditos otorgados","Gastos medicos","Refacciones","Combustible","Otros Gastos","D. Cigarro", "D. Refresco", "U. B.", "U. N.", "F/S","T.Liquidar","Efectivo","Estado"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
				
		String[] registro=new String[27];
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		this.registrosMostrados=0;
		
		for(VentaRuta item:lista)
		{
			registro[0]=Integer.toString(item.getId());
                        registro[1]=Integer.toString(item.getUsuarioId());
                        registro[2]=item.getUsuarioNombre();
                        registro[3]=Integer.toString(item.getRutaId());
                        registro[4]=item.getRutaNombre();
                        registro[5]=Integer.toString(item.getVendedorId());
                        registro[6]=item.getVendedorNombre();
                        registro[7]=Integer.toString(item.getAyudanteId());
                        registro[8]=item.getAyudanteNombre();
                        registro[9]=item.getSerieComprobante();
                        registro[10]=item.getNumComprobante();
                        registro[11]=sdf.format(item.getFecha());
                        registro[12]=Double.toString(item.getOtrosProductos());
                        registro[13]=Double.toString(item.getCreditosCobrados());
                        registro[14]=Double.toString(item.getCreditosOtorgados());
                        registro[15]=Double.toString(item.getGastosMedicos());
                        registro[16]=Double.toString(item.getRefacciones());
                        registro[17]=Double.toString(item.getCombustible());
                        registro[18]=Double.toString(item.getOtrosGastos());  
                        registro[19]=Double.toString(item.getDescuentoCigarro());
                        registro[20]=Double.toString(item.getDescuentoRefresco());
                        registro[21]=Double.toString(item.getUtilidadBruta());
                        registro[22]=Double.toString(item.getUtilidadNeta());
                        registro[23]=Double.toString(item.getFs());
                        registro[24]=Double.toString(item.getTotalLiquidar());
                        registro[25]=Double.toString(item.getEfectivo());
                        registro[26]=item.getEstado();
                                            
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        
        
        public DefaultTableModel listarDetalle(int id)
	{
		List<DetalleVentaRuta> lista=new ArrayList();
		lista.addAll(DATOS.listarDetalle(id));

                String [] titulos = {"ID","CODIGO","ARTICULO","FORMATO","S. PQS","S. PZS","PAQUETES","PIEZAS","PRECIO","DESCUENTO","SUBTOTAL","COM X U","COMISION","UTILIDAD"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
				
		String[] registro=new String[14];
		
		for(DetalleVentaRuta item:lista)
		{
			registro[0]=Integer.toString(item.getArticuloId());
                        registro[1]=item.getArticuloCodigo();
                        registro[2]=item.getArticuloNombre();
                        registro[3]=Integer.toString(item.getArticuloFormato());
                        registro[4]=Integer.toString(item.getArticuloStock());
                        registro[5]=Integer.toString(item.getArticuloStockPzs());
                        registro[6]=Integer.toString(item.getCantidad());
                        registro[7]=Integer.toString(item.getPiezas());
                        registro[8]=Double.toString(item.getPrecio());
                        registro[9]=Double.toString(item.getDescuento());
                        //registro[10]=Double.toString(item.getSubTotal());
                        registro[10]=String.format("%.2f",item.getSubTotal());
                        registro[11]=Double.toString(item.getComisionA());
                        registro[12]=Double.toString(item.getComisionD());
                        registro[13]=Double.toString(item.getUtilidad());
                       
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
        
        public String ultimoSerie() {
        return this.DATOS.ultimoSerie();
        }
        
        public String ultimoNumero(String serieComprobante) {
        return this.DATOS.ultimoNumero(serieComprobante);
        }
        
        public Articulo obtenerArticuloCodigoVenta(String codigo){
            Articulo art=DATOSART.obtenerArticuloCodigoVenta(codigo);
            return art;
        }
	
        public String insertar(int rutaId, int vendedorId, int ayudanteId, String serieComprobante,String numComprobante, double otrosProductos, double creditosCobrados, double creditosOtorgados, double gastosMedicos, double refacciones, double combustible, double otrosGastos, double efectivo, double totalLiquidar, DefaultTableModel modeloDetalles)
	{
            System.out.println("Empezando");
            
		if(DATOS.existe(serieComprobante,numComprobante))
		{
			return "El registro ya existe";
			
		}else
		{
                    System.out.println("antes set");
                    obj.setUsuarioId(Variables.usuarioId);
                    obj.setRutaId(rutaId);
                    obj.setVendedorId(vendedorId);
                    obj.setAyudanteId(ayudanteId);
                    obj.setSerieComprobante(serieComprobante);
                    obj.setNumComprobante(numComprobante);
                    obj.setOtrosProductos(otrosProductos);
                    obj.setCreditosCobrados(creditosCobrados);
                    obj.setCreditosOtorgados(creditosOtorgados);
                    obj.setGastosMedicos(gastosMedicos);
                    obj.setRefacciones(refacciones);
                    obj.setCombustible(combustible);
                    obj.setOtrosGastos(otrosGastos);
                    obj.setEfectivo(efectivo);
                    obj.setTotalLiquidar(totalLiquidar);

                    
                    List<DetalleVentaRuta> detalles=new ArrayList();
                    int articuloId;
                    int cantidad;
                    double precio;
                    double descuento;
                    int piezas;
                    double comision;

                    for(int i=0; i<modeloDetalles.getRowCount(); i++){
        
                        articuloId=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 0)));
                        cantidad=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 6))); 
                        piezas=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 7))); 
                        precio=Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 8)));
                        descuento=Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 9)));
                        comision=Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 12)));
                        
                        detalles.add(new DetalleVentaRuta(articuloId,cantidad,piezas,precio, descuento, 0, comision));
                        
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
        
        public void reporteVentaRuta(String idVentaRuta)
        {
            Map p=new HashMap();
            p.put("idVentaRuta", idVentaRuta);
            JasperReport report;
            JasperPrint print;
            
            Conexion cnn=Conexion.getInstancia();
            
            try {
                report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+
                        "/src/reportes/RptVentaRuta.jrxml");
                print=JasperFillManager.fillReport(report, p, cnn.conectar());
                JasperViewer view=new JasperViewer(print,false);
                view.setTitle("Reporte de Liquidación");
                view.setVisible(true);
            } catch (JRException e) {
                e.getMessage();
            }
        }
        
        public DefaultTableModel consultaFechas(Date fechaInicio, Date fechaFin){
        List<VentaRuta> lista=new ArrayList();
        lista.addAll(DATOS.consultaFechas(fechaInicio,fechaFin));
        
        //String[] titulos={"Id","Usuario ID","Usuario","Ruta ID","Ruta","Número","Fecha","Total","Utilidad","Efectivo","Estado"};
        String[] titulos= {"Id","Usuario Id","Usuario","Ruta ID", "Ruta","Vendedor ID", "Vendedor","Ayudante ID","Ayudante","Serie","Numero","Fecha","Otros Productos",
        "Creditos cobrados","Creditos otorgados","Gastos medicos","Refacciones","Combustible","Otros Gastos",
        "Venta bruta", "D. Cigarro","D. Refresco","Costo Venta", "U. B.", "U. N.", "F/S", "T. Liquidar","Efectivo","Estado"};
        this.modeloTabla=new DefaultTableModel(null,titulos);        
        
        String[] registro = new String[29];
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        
        this.registrosMostrados=0;
        for (VentaRuta item:lista){
            registro[0] = Integer.toString(item.getId());
            registro[1] = Integer.toString(item.getUsuarioId());
            registro[2] = item.getUsuarioNombre();
            registro[3] = Integer.toString(item.getRutaId());
            registro[4] = item.getRutaNombre();
            registro[5] = Integer.toString(item.getVendedorId());
            registro[6] = item.getVendedorNombre();
            registro[7] = Integer.toString(item.getAyudanteId());
            registro[8] = item.getAyudanteNombre();
            registro[9] = item.getSerieComprobante();
            registro[10] = item.getNumComprobante();
            registro[11] = sdf.format(item.getFecha());
            registro[12] = Double.toString(item.getOtrosProductos());
            registro[13] = Double.toString(item.getCreditosCobrados());
            registro[14] = Double.toString(item.getCreditosOtorgados());
            registro[15] = Double.toString(item.getGastosMedicos());
            registro[16] = Double.toString(item.getRefacciones());
            registro[17] = Double.toString(item.getCombustible());
            registro[18] = Double.toString(item.getOtrosGastos());
            registro[19] = Double.toString(item.getVentaBruta());
            registro[20] = Double.toString(item.getDescuentoCigarro());
            registro[21] = Double.toString(item.getDescuentoRefresco());
            registro[22] = Double.toString(item.getCostoVenta());
            registro[23] = Double.toString(item.getUtilidadBruta());
            registro[24] = Double.toString(item.getUtilidadNeta());
            registro[25] = Double.toString(item.getFs());
            registro[26] = Double.toString(item.getTotalLiquidar());
            registro[27] = Double.toString(item.getEfectivo());
            registro[28] = item.getEstado();
            
            this.modeloTabla.addRow(registro);
            this.registrosMostrados=this.registrosMostrados+1;
        }
        return this.modeloTabla; 
    }
}

