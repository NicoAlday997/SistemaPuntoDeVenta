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
import datos.IngresoDAO;
import entidades.Articulo;
import entidades.DetalleIngreso;
import entidades.Ingreso;
import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class IngresoControl
{
	private final IngresoDAO DATOS;
        private final ArticuloDAO DATOSART;
	private Ingreso obj;
	private DefaultTableModel modeloTabla;
	public int registrosMostrados;
	
	public IngresoControl()
	{
		this.DATOS=new IngresoDAO();
                this.DATOSART=new ArticuloDAO();
		this.obj=new Ingreso();
		this.registrosMostrados=0;
	}
	
	public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina)
	{
		List<Ingreso> lista=new ArrayList();
		lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));
		
		String[] titulos= {"Id","Usuario Id","Usuario","Proveedor ID", "Proveedor","Tipo Comprobante","Serie", "Numero","Fecha", "Impuesto","Total","Estado"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
				
		String[] registro=new String[12];
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		this.registrosMostrados=0;
		
		for(Ingreso item:lista)
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
                        registro[11]=item.getEstado();
                        
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        
        public DefaultTableModel listarDetalle(int id)
	{
		List<DetalleIngreso> lista=new ArrayList();
		lista.addAll(DATOS.listarDetalle(id));
		
                String[] titulos= {"ID","CODIGO","ARTICULO","FORMATO","UNIDADES","PIEZAS","PRECIO","SUBTOTAL"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
				
		String[] registro=new String[8];
		
		for(DetalleIngreso item:lista)
		{
			registro[0]=Integer.toString(item.getArticuloId());
                        registro[1]=item.getArticuloCodigo();
                        registro[2]=item.getArticuloNombre();
                        registro[3]=Integer.toString(item.getArticuloFormato());
                        registro[4]=Integer.toString(item.getCantidad());
                        registro[5]=Integer.toString(item.getPiezas());
                        registro[6]=Double.toString(item.getPrecio());
                        registro[7]=Double.toString(item.getSubtotal());
                       
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
        
        public Articulo obtenerArticuloCodigoIngreso(String codigo){
            Articulo art=DATOSART.obtenerArticuloCodigoIngreso(codigo);
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
                    
                    List<DetalleIngreso> detalles=new ArrayList();
                    int articuloId;
                    int cantidad;
                    int piezas;
                    double precio;
                    int existencia;
                    int existenciaPiezas;
                                                       
                    for(int i=0; i<modeloDetalles.getRowCount(); i++){
                        articuloId=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 0)));
                        cantidad=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 4)));
                        piezas=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 5)));
                        precio=Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 6)));
                        existencia=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 4)));
                        existenciaPiezas=Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 5)));
                        detalles.add(new DetalleIngreso(articuloId,cantidad,piezas,precio,existencia,existenciaPiezas));
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
        
        
        public void reporteIngreso(String idIngreso)
        {
            Map p=new HashMap();
            p.put("idIngreso", idIngreso);
            JasperReport report;
            JasperPrint print;
            
            Conexion cnn=Conexion.getInstancia();
            
            try {
                report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+
                        "/src/reportes/RptIngreso.jrxml");
                print=JasperFillManager.fillReport(report, p, cnn.conectar());
                JasperViewer view=new JasperViewer(print,false);
                view.setTitle("Reporte de Compra");
                view.setVisible(true);
            } catch (JRException e) {
                e.getMessage();
                System.out.println("no se encontro reporte con id: " + idIngreso);
            }
        }
        
        public DefaultTableModel consultaFechas(Date fechaInicio, Date fechaFin){
        List<Ingreso> lista=new ArrayList();
        lista.addAll(DATOS.consultaFechas(fechaInicio,fechaFin));
        
        String[] titulos={"Id","Usuario ID","Usuario","Proveedor ID","Proveedor","Tipo Comprobante","Serie","Número","Fecha","Impuesto","Total","Estado"};
        this.modeloTabla=new DefaultTableModel(null,titulos);        
        
        String[] registro = new String[12];
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        
        this.registrosMostrados=0;
        for (Ingreso item:lista){
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
            registro[11]=item.getEstado();
            
            this.modeloTabla.addRow(registro);
            this.registrosMostrados=this.registrosMostrados+1;
        }
        return this.modeloTabla; 
    }
}

