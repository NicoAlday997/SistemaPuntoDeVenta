/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package presentacion;

import datos.PuestoDAO;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import negocio.IngresoControl;
import negocio.VentaControl;
import negocio.VentaControlRuta;
import negocio.ArticuloControl;
import entidades.Empleado;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;



/**
 *
 * @author alday
 */
public class FrmVentaRutas extends javax.swing.JInternalFrame {

    //private final VentaControl CONTROL;
    private final VentaControlRuta CONTROL;
    private final ArticuloControl DATOSARTICULO;
    private String accion;
    private String nombreAnt; 
    private int totalPorPagina=10;
    private int numPagina=1;
    private boolean primeraCarga=true;
    private int totalRegistros;
    
    public DefaultTableModel modeloDetalles;
    public DefaultTableModel modeloEfectivo;
    
    public DefaultTableModel modeloCrementos;
    public DefaultTableModel modeloDescuentos;
    public DefaultTableModel modeloDetallesInsert;

    public JFrame contenedor;
    
    /**
     * Creates new form FrmCategoria
     */
    public FrmVentaRutas(JFrame frmP) {
        initComponents();
        //jTable2.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 0));

        this.contenedor=frmP;
        //this.CONTROL=new VentaControl();
        this.CONTROL=new VentaControlRuta();
        this.DATOSARTICULO=new ArticuloControl();
        this.paginar();
        this.listar("",false);
        this.primeraCarga=false;
        tabGeneral.setEnabledAt(1,false );
        this.accion="guardar";
        this.crearDetalles();
        this.calculaEfectivo();
        this.calculaCrementos();
        this.calculaDescuentos();
        this.cargarVendedores();
        this.cargarAyudantes();
        this.cargaArticulos();
    }
    
    private void cargaArticulos() {                                                       
        
         //tablaDetalles.setModel(this.DATOSARTICULO.listarArticuloVentaRuta());
        /* TableRowSorter orden=new TableRowSorter(tablaDetalles.getModel());
         tablaDetalles.setRowSorter(orden);
         this.ocultarColumnas();*/
         
    
        DefaultTableModel modeloTabla=new DefaultTableModel();
        modeloTabla=this.DATOSARTICULO.listarArticuloVentaRuta();

        
        //for (int i = 0; i < tablaDetalles.getRowCount(); i++) {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String id= String.valueOf(modeloTabla.getValueAt(i, 0));
            String codigo= String.valueOf(modeloTabla.getValueAt(i, 1));
            String nombre= String.valueOf(modeloTabla.getValueAt(i, 2));       
            String stock= String.valueOf(modeloTabla.getValueAt(i, 3));
            String precio= String.valueOf(modeloTabla.getValueAt(i, 4));
            
            this.agregarDetalles(id, codigo, nombre, stock, precio,"0","0");
            
        }
        
        
        /*
        if(tablaListado.getSelectedRowCount()==1){
            String id=String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 0));
            String codigo=String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 3));
            String nombre=String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 4));
            String precio=String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 5)); 
            String stock=String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 6));
                    
            this.vista.agregarDetalles(id, codigo, nombre, stock, precio,"0","0");
        }else{
            this.mensajeError("Debe seleccionar el artículo que desea agregar al detalle");
        }*/
    }  
    
    
    private void paginar()
    {
        int totalPaginas;
        this.totalRegistros=this.CONTROL.total();
        this.totalPorPagina=Integer.parseInt((String)cboTotalPorPagina.getSelectedItem());
        totalPaginas=(int)(Math.ceil((double)this.totalRegistros/this.totalPorPagina));
        if(totalPaginas==0){
            totalPaginas=1;
        }
        cboNumPagina.removeAllItems();
        
        for (int i = 1; i <= totalPaginas; i++) {
            cboNumPagina.addItem(Integer.toString(i));
        }
        
        cboNumPagina.setSelectedIndex(0);
    }
    private void listar(String texto, boolean paginar)
    {
        this.totalPorPagina=Integer.parseInt((String)cboTotalPorPagina.getSelectedItem());
        if((String)cboNumPagina.getSelectedItem()!=null){
            this.numPagina=Integer.parseInt((String)cboNumPagina.getSelectedItem());
        }
        
        if(paginar==true){
            tablaListado.setModel(this.CONTROL.listar(texto, this.totalPorPagina,this.numPagina));

        }else{
             tablaListado.setModel(this.CONTROL.listar(texto, this.totalPorPagina,1));

        }
        TableRowSorter orden=new TableRowSorter(tablaListado.getModel());
        tablaListado.setRowSorter(orden);
        this.ocultarColumnas();
        lblTotalRegistros.setText("Mostrando: " + this.CONTROL.totalMostrados()+ " de un total de "+ this.CONTROL.total() + " registros");
    }
    
    private void ocultarColumnas()
    {
        tablaListado.getColumnModel().getColumn(1).setMaxWidth(0);
        tablaListado.getColumnModel().getColumn(1).setMinWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);
        
        tablaListado.getColumnModel().getColumn(3).setMaxWidth(0);
        tablaListado.getColumnModel().getColumn(3).setMinWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);
        
        
        
    } 
    
    private void calculaCrementos(){
       modeloCrementos=new DefaultTableModel(){
           @Override
           public boolean isCellEditable(int fila, int columna){
   
               if(columna==1 && fila==0){
                   return columna==1;
               }
               
               if(columna==1 && fila==1){
                   return columna==1;
               }
             
               return columna==1 && fila==0;
           }
           
           @Override
           public Object getValueAt(int row, int col){
           
               //calcular descuentos
               
               
               if(row==2 && col==1){
           
                   Double otrosP;
                   Double creditosC;
                   Double subTot;
                 
               
                  //Cuando ingresamos una cantidad que supera el stock, el sistema por
                  //un momento detecta que no hay valor en la caja de texto de cantD;
                   try {
                       otrosP=Double.parseDouble((String)getValueAt(0,1));
                       //utilidad=Double.parseDouble((String)getValueAt(row,5));
                   } catch (Exception e) {
                       otrosP=0.0;
                   }
                  // System.out.println("Valor tomado: " + cantD);
                  
                  
                  try {
                       creditosC=Double.parseDouble((String)getValueAt(1,1));
                       //utilidad=Double.parseDouble((String)getValueAt(row,5));
                   } catch (Exception e) {
                       creditosC=0.0;
                   }
                   
                   
                   if(otrosP!=null && creditosC!=null){
                       subTot=otrosP+creditosC;
                      // super.setValueAt(String.format("%.2f",subTot), row, 1);
                       //super.setValueAt(String.format("%.2f", ((cantD*precioD)-descuentoD)), row, 7);
                       return String.format("%.2f",subTot);
                       //return String.format("%.2f",(row*precioD)-descuentoD);
                       //return String.format("%.2f",(cantD*precioD)-descuentoD);

                   }else{
                       return 0;
                   }
               }
       
               return super.getValueAt(row, col);
               
           }
                 
           
           @Override
           public void setValueAt(Object aValue, int row, int col){
               super.setValueAt(aValue, row, col);
               try {
                   int cantD=Integer.parseInt((String)getValueAt(row, 1));
                   if(cantD<0){
                      super.setValueAt(0, row, 1);
                       //setValueAt(0, row, 4);
                       //modeloDetalles.setValueAt(stockD, row, 4);

  
                       mensajeError("La cantidad a ingresar debe ser mayor a 0: " + 0);
                       
                       
                       
                       //calcularTotales();
                      // super.setValueAt(stockD, row, 4);
    
                   }
               } catch (Exception e) {
                   System.out.println(e.getMessage());
                   
               }
               //this.getValueAt(row, 7);
               //super.getValueAt(row,7);
              // calcularTotEfectivo();
                calcularLiquidacion();
               
               fireTableDataChanged();
           }
       };
       
       //modeloEfectivo=new DefaultTableModel();
       modeloCrementos.setColumnIdentifiers(new Object[] {"Cremento","Monto"});
       
       modeloCrementos.addRow(new Object[]{"Otros productos",""});
       modeloCrementos.addRow(new Object[]{"Creditos Cobrados",""});
       modeloCrementos.addRow(new Object[]{"Sub Total",""});

 
       tablaCrementos.setModel(modeloCrementos);

        
    }
    
      private void calculaDescuentos(){
       modeloDescuentos=new DefaultTableModel(){
           @Override
           public boolean isCellEditable(int fila, int columna){
   
               if(columna==1 && fila<5){
                   return columna==1 && fila<5;
               }
               
             
               return columna==1 && fila<5;
           }
           
           @Override
           public Object getValueAt(int row, int col){
           
               //calcular descuentos
               
               
               if(row==5 && col==1){
           
                   Double creditosOtorgados,gastosMedicos, refacciones, combustible;
                   Double promo, subTot;
           
                 
               
                  //Cuando ingresamos una cantidad que supera el stock, el sistema por
                  //un momento detecta que no hay valor en la caja de texto de cantD;
                  try {
                       creditosOtorgados=Double.parseDouble((String)getValueAt(0,1));
                   } catch (NumberFormatException e) {
                       creditosOtorgados=0.0;
                   }
                  
                   try {
                       gastosMedicos=Double.parseDouble((String)getValueAt(1,1));
                   } catch (NumberFormatException e) {
                       gastosMedicos=0.0;
                   }
                  
                  
                  try {
                       refacciones=Double.parseDouble((String)getValueAt(2,1));
                   } catch (NumberFormatException e) {
                       refacciones=0.0;
                   }
                  
                  try {
                       combustible=Double.parseDouble((String)getValueAt(3,1));
                   } catch (NumberFormatException e) {
                       combustible=0.0;
                   }
                   
                  
                  try {
                       promo=Double.parseDouble((String)getValueAt(4,1));
                   } catch (NumberFormatException e) {
                       promo=0.0;
                   }
                  

                   
                   if(gastosMedicos!=null && refacciones!=null && combustible!=null && promo!=null){
                       subTot=creditosOtorgados+gastosMedicos+refacciones+combustible+promo;
                      // super.setValueAt(String.format("%.2f",subTot), row, 1);
                       //super.setValueAt(String.format("%.2f", ((cantD*precioD)-descuentoD)), row, 7);
                       return String.format("%.2f",subTot);
                       //return String.format("%.2f",(row*precioD)-descuentoD);
                       //return String.format("%.2f",(cantD*precioD)-descuentoD);

                   }else{
                       return 0;
                   }
               }
       
               return super.getValueAt(row, col);
               
           }
                 
           
           @Override
           public void setValueAt(Object aValue, int row, int col){
               super.setValueAt(aValue, row, col);
               try {
                   int cantD=Integer.parseInt((String)getValueAt(row, 1));
                   if(cantD<0){
                      super.setValueAt(0, row, 1);
                       //setValueAt(0, row, 4);
                       //modeloDetalles.setValueAt(stockD, row, 4);

  
                       mensajeError("La cantidad a ingresar debe ser mayor a 0: " + 0);
                       
                       
                       
                       //calcularTotales();
                      // super.setValueAt(stockD, row, 4);
    
                   }
               } catch (Exception e) {
                   System.out.println(e.getMessage());
                   
               }
               //this.getValueAt(row, 7);
               //super.getValueAt(row,7);
              // calcularTotEfectivo();
               calcularLiquidacion();
               
               fireTableDataChanged();
           }
       };
       
       //modeloEfectivo=new DefaultTableModel();
       modeloDescuentos.setColumnIdentifiers(new Object[] {"Descuento","Monto"});
       
       modeloDescuentos.addRow(new Object[]{"Creditos otorgados",""});
       modeloDescuentos.addRow(new Object[]{"Gastos Medicos",""});
       modeloDescuentos.addRow(new Object[]{"Refacciones",""});
       modeloDescuentos.addRow(new Object[]{"Combustible",""});
       modeloDescuentos.addRow(new Object[]{"Promocion",""});
       modeloDescuentos.addRow(new Object[]{"Sub Total",""});

 
       tablaDescuentos.setModel(modeloDescuentos);

        
    }
    
    private void calculaEfectivo(){
       modeloEfectivo=new DefaultTableModel(){
           @Override
           public boolean isCellEditable(int fila, int columna){
   
               if(columna==0){
                   return columna==0;
               }
             
               return columna==0;
           }
           
           @Override
           public Object getValueAt(int row, int col){
           
               //calcular descuentos
               
               
               if(col==2){
                   Double cantD;
                   Double subTot;
                   Double cant;
                 
               
                  //Cuando ingresamos una cantidad que supera el stock, el sistema por
                  //un momento detecta que no hay valor en la caja de texto de cantD;
                   try {
                       cantD=Double.parseDouble((String)getValueAt(row,1));
                       //utilidad=Double.parseDouble((String)getValueAt(row,5));
                   } catch (Exception e) {
                       cantD=1.0;
                   }
                  // System.out.println("Valor tomado: " + cantD);
                  
                  
                  try {
                       cant=Double.parseDouble((String)getValueAt(row,0));
                       //utilidad=Double.parseDouble((String)getValueAt(row,5));
                   } catch (Exception e) {
                       cant=0.0;
                   }
                   
                   
                   if(cantD!=null){
                       subTot=cantD*cant;
                       //super.setValueAt(String.format("%.2f",utilidad), row, 8);
                       //super.setValueAt(String.format("%.2f", ((cantD*precioD)-descuentoD)), row, 7);
                       return String.format("%.2f",subTot);
                       //return String.format("%.2f",(row*precioD)-descuentoD);

                   }else{
                       return 0;
                   }
               }
       
               return super.getValueAt(row, col);
               
           }
                 
           
           @Override
           public void setValueAt(Object aValue, int row, int col){
               super.setValueAt(aValue, row, col);
               try {
                   int cantD=Integer.parseInt((String)getValueAt(row, 0));
                   if(cantD<0){
                       super.setValueAt(0, row, 0);
                       //setValueAt(stockD, row, 4);
                       //modeloDetalles.setValueAt(stockD, row, 4);

  
                       mensajeError("La cantidad a ingresar debe ser mayor a 0: " + 0);
                       
                       
                       
                       //calcularTotales();
                      // super.setValueAt(stockD, row, 4);
    
                   }
               } catch (Exception e) {
                   System.out.println(e.getMessage());
                   
               }
               //this.getValueAt(row, 7);
               //super.getValueAt(row,7);
               calcularTotEfectivo();
               calcularLiquidacion();
               
               fireTableDataChanged();
           }
       };
       
       
       //modeloEfectivo=new DefaultTableModel();
       modeloEfectivo.setColumnIdentifiers(new Object[] {"Cant.","Denom.","Total"});
     
       
       modeloEfectivo.addRow(new Object[]{"","20","0"});
        modeloEfectivo.addRow(new Object[]{"","50","0"});
        modeloEfectivo.addRow(new Object[]{"","100","0"});
        modeloEfectivo.addRow(new Object[]{"","200","0"});
        modeloEfectivo.addRow(new Object[]{"","500","0"});
        modeloEfectivo.addRow(new Object[]{"","1000","0"});
        modeloEfectivo.addRow(new Object[]{"","1","0"});
        
        //mytable.setModel(model);
       
      // modeloEfectivo.setNumRows(7);
       tablaEfectivo.setModel(modeloEfectivo);

        
    }
    
    private void calcularTotEfectivo()
    {
        double total=0;
        
        int items=modeloEfectivo.getRowCount();
        if(items==0){
            total=0;
        }else{
            for (int i = 0; i < items; i++) {
                total=total+Double.parseDouble(String.valueOf(modeloEfectivo.getValueAt(i, 2))); 
                //utilidadTotal=utilidadTotal+Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 8))); 
                
            }
        }
        
        txtEfectivo.setText(String.format("%.2f", total));
  
    }
    
    private void calcularLiquidacion()
    {
        double totLiquidar=0, detalleVenta=0, estado=0;
        double efectivo=0, crementos=0, descuentos=0;
        
        efectivo=Double.parseDouble(txtEfectivo.getText());
        
        detalleVenta=Double.parseDouble(txtSubTotal.getText());
        
        crementos=Double.parseDouble(String.valueOf(modeloCrementos.getValueAt(2, 1)));
        
        descuentos=Double.parseDouble(String.valueOf(modeloDescuentos.getValueAt(5, 1)));
        
       /* int itemsCrem=modeloCrementos.getRowCount()-1;
        if(itemsCrem<1){
            crementos=0;
        }else{
            for (int i = 0; i < itemsCrem; i++) {
                
               
                crementos=crementos+Double.parseDouble(String.valueOf(modeloCrementos.getValueAt(i, 1))); 
            }
        }
        
        int itemsDes=modeloDescuentos.getRowCount()-1;
        if(itemsDes<1){
            descuentos=0;
        }else{
            for (int i = 0; i < itemsDes; i++) {
                descuentos=descuentos+Double.parseDouble(String.valueOf(modeloDescuentos.getValueAt(i, 1))); 
            }
        }*/
        
        totLiquidar=detalleVenta+crementos-descuentos;
        
        estado=efectivo-totLiquidar;
        
        txtEstado.setText(String.format("%.2f", estado));
        
        txtTotalLiquidar.setText(String.format("%.2f", totLiquidar));
  
    }
    
    private void crearDetalles(){
        
        modeloDetalles=new DefaultTableModel(){
            
           @Override
           public boolean isCellEditable(int fila, int columna){
   
               if(columna==4){
                   return columna==4;
               }
               if(columna==5){
                   return columna==5;
               }
               if(columna==6){
                   
                   return columna==6;
               }
               return columna==4;
           }
           
           @Override
           public Object getValueAt(int row, int col){
           
               //calcular descuentos
               
               
               if(col==7){
                   Double cantD;
                   Double utilidad;
                   
               
                  //Cuando ingresamos una cantidad que supera el stock, el sistema por
                  //un momento detecta que no hay valor en la caja de texto de cantD;
                   try {
                       cantD=Double.parseDouble((String)getValueAt(row,4));
                       //utilidad=Double.parseDouble((String)getValueAt(row,5));
                   } catch (Exception e) {
                       cantD=0.0;
                   }
                  // System.out.println("Valor tomado: " + cantD);
                  
                   Double precioD=Double.parseDouble((String)getValueAt(row,5));
                   Double descuentoD=Double.parseDouble((String)getValueAt(row,6));
                   if(cantD!=null && precioD!=null && descuentoD!=null){
                       //utilidad=(cantD*precioD*.2)-descuentoD;
                       //super.setValueAt(String.format("%.2f",utilidad), row, 8);
                       super.setValueAt(String.format("%.2f", ((cantD*precioD)-descuentoD)), row, 7);
                       return String.format("%.2f",(cantD*precioD)-descuentoD);
                       //return String.format("%.2f",(row*precioD)-descuentoD);

                   }else{
                       return 0;
                   }
               }
       
               return super.getValueAt(row, col);
               
           }
                 
           
           @Override
           public void setValueAt(Object aValue, int row, int col){
               super.setValueAt(aValue, row, col);
               try {
                   
                   int  cantD=Integer.parseInt((String)getValueAt(row, 4));
                   int stockD=Integer.parseInt((String)getValueAt(row, 3));
                   if(cantD>stockD){
                       super.setValueAt(stockD, row, 4);
                       //setValueAt(stockD, row, 4);
                       //modeloDetalles.setValueAt(stockD, row, 4);

  
                       mensajeError("La cantidad a vender no puede superar el stock. Usted puede vender como máximo: " + stockD);
                       
                       
                       
                       //calcularTotales();
                      // super.setValueAt(stockD, row, 4);
    
                   }
               } catch (Exception e) {
                   System.out.println(e.getMessage());
                   
               }
               //this.getValueAt(row, 7);
               //super.getValueAt(row,7);
               calcularTotales();
               calcularLiquidacion();
               
               fireTableDataChanged();
           }
           
           
        };
        
        
        modeloDetalles.setColumnIdentifiers(new Object[] {"ID","CODIGO","ARTICULO","STOCK","CANTIDAD","PRECIO","DESCUENTO","SUBTOTAL"});
        tablaDetalles.setModel(modeloDetalles);
    }
    
    public void agregarDetalles(String id, String codigo, String nombre,String stock, String precio, String descuento, String utilidad){
        String idT;
        boolean existe=false;
        for(int i=0;i<this.modeloDetalles.getRowCount();i++){
            idT=String.valueOf(this.modeloDetalles.getValueAt(i, 0));
            if(idT.equals(id)){
                existe=true;
            }
        }
        if(existe){
            this.mensajeError("El articulo ya ha sido agregado");

        }else{
           // utilidad=this.calcularUtilidad(id,precio);
            //this.modeloDetalles.addRow(new Object[]{id,codigo,nombre,stock,"0",precio,descuento,"1"});
            this.modeloDetalles.addRow(new Object[]{id,codigo,nombre,stock,"",precio,descuento,"1"});

            //utilidad=this.calcularUtilidad(id, "1");
            this.calcularTotales();
            //this.calcularUtilidad();

        }
    }
    
      
    /*private String calcularUtilidad(String id,String precio){
        //Calcular utilidades de los articulos
        double utilidad= (Double.parseDouble(precio))*0.2;
        //return String.valueOf(utilidad);
        return String.format("%.2f",utilidad);
    }*/

    private void calcularTotales(){
        double total=0;
        double subTotal;
        double utilidadTotal=0;
        int items=modeloDetalles.getRowCount();
        if(items==0){
            total=0;
        }else{
            for (int i = 0; i < items; i++) {
                total=total+Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 7))); 
                //utilidadTotal=utilidadTotal+Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 8))); 

                
            }
        }
       // subTotal=total/(1+Double.parseDouble(txtImpuesto.getText()));
       subTotal=total/(1);

        
        //txtUtilidadTotal.setText(String.format("%.2f", utilidadTotal));
        //txtTotalLiquidar.setText(String.format("%.2f", total));
        txtSubTotal.setText(String.format("%.2f", subTotal));
        //txtEstado.setText(String.format("%.2f", total-subTotal));
    }
  
    
    private void obtenerNumero() {
      /*  String tipoComprobante = (String) cboTipoComprobante.getSelectedItem();
        String serieComprobante = this.CONTROL.ultimoSerie(tipoComprobante);
        String numComprobante = this.CONTROL.ultimoNumero(tipoComprobante, serieComprobante);
        txtSerieComprobante.setText(serieComprobante);
        if (numComprobante.equals("")){
            txtNumComprobante.setText("");
        }else{
            int num;
            num=Integer.parseInt(numComprobante)+1;
            txtNumComprobante.setText(Integer.toString(num));
        }    */    
    }
    

    
    private void limpiar()
    {
        txtNombreRuta.setText("");
        txtIdRuta.setText("");
        txtNumComprobante.setText("");
        
  
        this.limpiarTablas();
        
        this.accion="guardar";
        
        txtTotalLiquidar.setText("0.00");
        txtSubTotal.setText("0.00");
        this.crearDetalles();
        this.calculaEfectivo();
        this.calculaCrementos();
        this.calculaDescuentos();
        this.cargarVendedores();
        this.cargarAyudantes();
        this.cargaArticulos();
        
        
        btnGuardar.setVisible(true);
    }
    
    private void limpiarTablas(){
        int itemsCrem=modeloCrementos.getRowCount()-1;
        
        for (int i = 0; i < itemsCrem; i++) {              
                modeloCrementos.setValueAt("", i, 1);
             }
            
         int itemsDesc=modeloDescuentos.getRowCount()-1;
         
         for(int i=0; i<itemsDesc; i++){
             modeloDescuentos.setValueAt("", i, 1);
         }
         
         for(int i=0; i<modeloEfectivo.getRowCount();i++){
             modeloEfectivo.setValueAt("", i, 0);
         }
        
    }
    
     private void cargarVendedores()
     {       
        DefaultComboBoxModel items=this.CONTROL.seleccionar();
        cboVendedor.setModel(items);  
        
         if(cboVendedor.getItemCount()>0){
            Empleado empSeleccionado= (Empleado) cboVendedor.getSelectedItem();
            int id=empSeleccionado.getId();
            txtIdVendedor.setText(String.valueOf(id));
        }
        
    }
     
     private void cargarAyudantes()
     {
        DefaultComboBoxModel items=this.CONTROL.seleccionarAyudante();
        cboAyudante.setModel(items);
        
        if(cboAyudante.getItemCount()>0){
            Empleado empSeleccionado= (Empleado) cboAyudante.getSelectedItem();
            int id=empSeleccionado.getId();
            txtIdAyudante.setText(String.valueOf(id));
        }
    }
     
     private void eliminaDetallesVacios(){
       
         
         int tamaño=modeloDetalles.getRowCount();

         int remov=0;
        
         for (int i = 0; i < tamaño; i++) {

             if (String.valueOf(modeloDetalles.getValueAt(i - remov, 4)) == null || String.valueOf(modeloDetalles.getValueAt(i-remov, 4)).equals("")) {

                 modeloDetalles.removeRow(i - remov);
                 remov++;
             }

         }

         
         /*
        System.out.println("tamaño y datos despues de eliminar: "+modeloDetalles.getRowCount());
        for(int i=0; i<modeloDetalles.getRowCount();i++){

            System.out.println("pos " + i + ":" + String.valueOf(modeloDetalles.getValueAt(i, 4)));
        }*/
        
     }
     
     private String validaCampo(String cadena)
     {
         
         if (cadena == null || cadena.equals("")) {
             cadena="0";
             return cadena;
         }    
         
         return cadena;
     }
    
    private void mensajeError(String mensaje)
    {
        JOptionPane.showMessageDialog(this, mensaje, "Sistema", JOptionPane.ERROR_MESSAGE);
        
    }

    private void mensajeOk(String mensaje)
    {
        JOptionPane.showMessageDialog(this, mensaje, "Sistema", JOptionPane.INFORMATION_MESSAGE);

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabGeneral = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaListado = new javax.swing.JTable();
        lblTotalRegistros = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnDesactivar = new javax.swing.JButton();
        cboNumPagina = new javax.swing.JComboBox<>();
        cboTotalPorPagina = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnVerVenta = new javax.swing.JButton();
        btnReporteComprobante = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtIdRuta = new javax.swing.JTextField();
        txtNombreRuta = new javax.swing.JTextField();
        btnSeleccionarCliente = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDetalles = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        txtTotalLiquidar = new javax.swing.JTextField();
        btnQuitar = new javax.swing.JButton();
        cboVendedor = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        cboAyudante = new javax.swing.JComboBox<>();
        txtEfectivo = new javax.swing.JTextField();
        txtNumComprobante = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaEfectivo = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaCrementos = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDescuentos = new javax.swing.JTable();
        txtIdVendedor = new javax.swing.JTextField();
        txtIdAyudante = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Ventas Rutas");

        tabGeneral.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Nombre:");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tablaListado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaListado);

        lblTotalRegistros.setText("Registros");

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnDesactivar.setText("Anular");
        btnDesactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarActionPerformed(evt);
            }
        });

        cboNumPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNumPaginaActionPerformed(evt);
            }
        });

        cboTotalPorPagina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10", "20", "50", "100", "200", "500" }));
        cboTotalPorPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTotalPorPaginaActionPerformed(evt);
            }
        });

        jLabel10.setText("# Página");

        jLabel11.setText("Total de registros por página ");

        btnVerVenta.setText("Ver Venta");
        btnVerVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerVentaActionPerformed(evt);
            }
        });

        btnReporteComprobante.setText("Reporte ");
        btnReporteComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteComprobanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(cboNumPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(cboTotalPorPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 246, Short.MAX_VALUE)
                        .addComponent(lblTotalRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(btnDesactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscar)
                                .addGap(18, 18, 18)
                                .addComponent(btnNuevo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnReporteComprobante)
                                .addGap(168, 168, 168)))
                        .addComponent(btnVerVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnNuevo)
                    .addComponent(btnVerVenta)
                    .addComponent(btnReporteComprobante))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotalRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboNumPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTotalPorPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 213, Short.MAX_VALUE)
                        .addComponent(btnDesactivar)
                        .addContainerGap())))
        );

        tabGeneral.addTab("Listado", jPanel1);

        jLabel2.setText("Ruta(*)");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel4.setText("(*) Indica que es un campo obligatorio");

        txtIdRuta.setEditable(false);

        txtNombreRuta.setEditable(false);
        txtNombreRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreRutaActionPerformed(evt);
            }
        });

        btnSeleccionarCliente.setText("...");
        btnSeleccionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarClienteActionPerformed(evt);
            }
        });

        jLabel3.setText("Vendedor(*)");

        jLabel7.setText("Número Comprobante (*)");

        tablaDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tablaDetalles);

        jLabel9.setText("Subtotal");

        jLabel12.setText("Faltante/Sobrante");

        jLabel13.setText("Total Liquidar");

        txtSubTotal.setEditable(false);
        txtSubTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubTotalActionPerformed(evt);
            }
        });

        txtEstado.setEditable(false);
        txtEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoActionPerformed(evt);
            }
        });

        txtTotalLiquidar.setEditable(false);
        txtTotalLiquidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalLiquidarActionPerformed(evt);
            }
        });

        btnQuitar.setText("Quitar");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        cboVendedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboVendedorItemStateChanged(evt);
            }
        });
        cboVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboVendedorActionPerformed(evt);
            }
        });

        jLabel14.setText("Ayudante(*)");

        cboAyudante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboAyudanteActionPerformed(evt);
            }
        });

        txtEfectivo.setEditable(false);
        txtEfectivo.setText("0");
        txtEfectivo.setToolTipText("");
        txtEfectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEfectivoActionPerformed(evt);
            }
        });

        jLabel5.setText("Total Efectivo");

        tablaEfectivo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(tablaEfectivo);

        tablaCrementos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tablaCrementos);

        tablaDescuentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tablaDescuentos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtIdRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNumComprobante)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSeleccionarCliente)
                                .addGap(71, 71, 71)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtIdAyudante, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(cboAyudante, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnQuitar)
                                .addGap(97, 97, 97))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(57, 57, 57)
                                .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 899, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addGap(35, 35, 35))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(btnGuardar)
                                        .addGap(44, 44, 44)
                                        .addComponent(btnCancelar))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addGap(52, 52, 52)
                                            .addComponent(txtEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(153, 153, 153)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotalLiquidar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(146, 146, 146)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(143, 143, 143))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIdRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeleccionarCliente)
                    .addComponent(jLabel3)
                    .addComponent(cboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(cboAyudante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdAyudante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNumComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuitar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(btnGuardar))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(btnCancelar))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTotalLiquidar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addGap(66, 66, 66)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))))
        );

        tabGeneral.addTab("Mantenimiento", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabGeneral)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabGeneral)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        if(tablaDetalles.getSelectedRowCount()==1){
            this.modeloDetalles.removeRow(tablaDetalles.getSelectedRow());
            this.calcularTotales();
        }else{
            this.mensajeError("Seleccione el detalle a quitar");
        }
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void txtTotalLiquidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalLiquidarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalLiquidarActionPerformed

    private void btnSeleccionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarClienteActionPerformed
        FrmSeleccionarRutaVenta frm=new FrmSeleccionarRutaVenta(contenedor, this, true);
        frm.toFront();
    }//GEN-LAST:event_btnSeleccionarClienteActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        tabGeneral.setEnabledAt(0, true);
        tabGeneral.setEnabledAt(1, false);
        tabGeneral.setSelectedIndex(0);
        this.limpiar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        if(txtIdRuta.getText().length()==0){
            JOptionPane.showMessageDialog(this, "Debes seleccionar un proveedor.","Sistema", JOptionPane.WARNING_MESSAGE);
            btnSeleccionarCliente.requestFocus();
            return;
        }

       /* if(txtSerieComprobante.getText().length()>7){
            JOptionPane.showMessageDialog(this, "Debes ingresar una serie no mayor a 7 caracteres.","Sistema", JOptionPane.WARNING_MESSAGE);
            txtSerieComprobante.requestFocus();
            return;
        }*/

        if(txtNumComprobante.getText().length()==0 || txtNumComprobante.getText().length()>10){
            JOptionPane.showMessageDialog(this, "Debes ingresar un número de comprobante no mayor a 10 caracteres.","Sistema", JOptionPane.WARNING_MESSAGE);
            txtNumComprobante.requestFocus();
            return;
        }

        if(modeloDetalles.getRowCount()==0){
            JOptionPane.showMessageDialog(this, "Debes agregar articulos al detalle.","Sistema", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String resp="";
        //resp=this.CONTROL.insertar(Integer.parseInt(txtIdCliente.getText()),(String)cboTipoComprobante.getSelectedItem(), txtSerieComprobante.getText(),txtNumComprobante.getText(), Double.parseDouble(txtImpuesto.getText()), Double.parseDouble(txtTotal.getText()),Double.parseDouble(txtUtilidadTotal.getText()), modeloDetalles);
        //resp=this.CONTROL.insertar(Integer.parseInt(txtIdCliente.getText()),(String)cboTipoComprobante.getSelectedItem(), txtSerieComprobante.getText(),txtNumComprobante.getText(), Double.parseDouble(txtImpuesto.getText()), Double.parseDouble(txtTotal.getText()),  modeloDetalles);
         
        eliminaDetallesVacios();
        
        Double otrosProductos=Double.parseDouble(this.validaCampo((String)modeloCrementos.getValueAt(0, 1)));
        Double creditosCobrados=Double.parseDouble(this.validaCampo((String)modeloCrementos.getValueAt(1, 1)));
        Double creditosOtorgados=Double.parseDouble(this.validaCampo((String)modeloDescuentos.getValueAt(0, 1)));
        Double gastosMedicos=Double.parseDouble(this.validaCampo((String)modeloDescuentos.getValueAt(1, 1)));
        Double refacciones=Double.parseDouble(this.validaCampo((String)modeloDescuentos.getValueAt(2, 1)));
        Double combustible=Double.parseDouble(this.validaCampo((String)modeloDescuentos.getValueAt(3, 1)));
        Double promocion=Double.parseDouble(this.validaCampo((String)modeloDescuentos.getValueAt(4, 1)));


        resp=this.CONTROL.insertar(Integer.parseInt(txtIdRuta.getText()), Integer.parseInt(txtIdVendedor.getText()),Integer.parseInt(txtIdAyudante.getText()),txtNumComprobante.getText(), otrosProductos, creditosCobrados, creditosOtorgados, gastosMedicos, refacciones, combustible, promocion,Double.parseDouble(txtEfectivo.getText()), Double.parseDouble(txtTotalLiquidar.getText()), modeloDetalles);
       // public String insertar(int rutaId, int vendedorId, int ayudanteId, String numComprobante, double otrosProductos, double creditosCobrados, double creditosOtorgados, double gastosMedicos, double refacciones, double combustible, double promocion, double efectivo, double totalLiquidar, DefaultTableModel modeloDetalles)

        
        if (resp.equals("OK")) {

            this.mensajeOk("Registrado correctamente");
            this.limpiar();
            this.listar("", false);

            /*  tabGeneral.setSelectedIndex(0);
            tabGeneral.setEnabledAt(1, false);
            tabGeneral.setEnabledAt(0, true);*/
        } else {
            this.mensajeError(resp);
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnReporteComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteComprobanteActionPerformed
        if(tablaListado.getSelectedRowCount()==1){
            String id= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),0));
            this.CONTROL.reporteComprobante(id);
        }else{
            this.mensajeError("Seleccione la venta para ver su reporte.");
        }
    }//GEN-LAST:event_btnReporteComprobanteActionPerformed

    private void btnVerVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerVentaActionPerformed
        if(tablaListado.getSelectedRowCount()==1){
            String id= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),0));
            String idProveedor= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),3));
            String nombreProveedor= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),4));
            /*String tipoComprobante= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),5));
            String serie= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),6));*/
            String numero= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),5));
            String efectivo= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),8));
            
            
           /* registro[0]=Integer.toString(item.getId());
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
                        
              registro[0]=Integer.toString(item.getId());
                        registro[1]=Integer.toString(item.getUsuarioId());
                        registro[2]=item.getUsuarioNombre();
                        registro[3]=Integer.toString(item.getRutaId());
                        registro[4]=item.getRutaNombre();
                        registro[5]=item.getNumComprobante();
                        registro[6]=sdf.format(item.getFecha());
                        registro[8]=Double.toString(item.getTotal());
                        registro[7]=Double.toString(item.getEfectivo());
                        registro[9]=item.getEstado();*/
                                            
                        

            txtIdRuta.setText(idProveedor);
            txtNombreRuta.setText(nombreProveedor);
            /*cboTipoComprobante.setSelectedItem(tipoComprobante);
            txtSerieComprobante.setText(serie);*/
            txtNumComprobante.setText(numero);
            txtEfectivo.setText(efectivo);

            this.modeloDetalles=CONTROL.listarDetalle(Integer.parseInt(id));
            tablaDetalles.setModel(modeloDetalles);
            this.calcularTotales(); 

            tabGeneral.setEnabledAt(1, true);
            tabGeneral.setEnabledAt(0, false);
            tabGeneral.setSelectedIndex(1);
            btnGuardar.setVisible(false);
        }else{
            this.mensajeError("Seleccione el ingreso a mostrar.");
        }
    }//GEN-LAST:event_btnVerVentaActionPerformed

    private void cboTotalPorPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTotalPorPaginaActionPerformed

        this.paginar();
    }//GEN-LAST:event_cboTotalPorPaginaActionPerformed

    private void cboNumPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNumPaginaActionPerformed

        if(this.primeraCarga==false){
            this.listar("", true);
        }

    }//GEN-LAST:event_cboNumPaginaActionPerformed

    private void btnDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarActionPerformed

        if(tablaListado.getSelectedRowCount()==1)
        {
            String id= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 0));
            String comprobante= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 5));
            String serie= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 6));
            String numero= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 7));

            if(JOptionPane.showConfirmDialog(this, "Deseas anular el registro: " + comprobante + " " + serie + "-"+ numero + "?","Anular", JOptionPane.YES_NO_OPTION)==0)
            {

                String resp=this.CONTROL.anular(Integer.parseInt(id));
                if(resp.equals("OK"))
                {
                    this.mensajeOk("Registro anulado");
                    this.listar("", false);
                }else{
                    this.mensajeError(resp);
                }

            }
        }else
        {
            this.mensajeError("Seleccione 1 registro a desactivar.");
        }
    }//GEN-LAST:event_btnDesactivarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        tabGeneral.setEnabledAt(1, true);
        tabGeneral.setEnabledAt(0, false);
        tabGeneral.setSelectedIndex(1);
        this.accion="guardar";
        btnGuardar.setText("Guardar");
        this.obtenerNumero();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        this.listar(txtBuscar.getText(), false);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtSubTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubTotalActionPerformed

    private void txtEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstadoActionPerformed

    private void txtEfectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEfectivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEfectivoActionPerformed

    private void txtNombreRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreRutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreRutaActionPerformed

    private void cboVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboVendedorActionPerformed

    private void cboVendedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboVendedorItemStateChanged
        if(cboVendedor.getItemCount()>0){
            Empleado empSeleccionado= (Empleado) cboVendedor.getSelectedItem();
            int id=empSeleccionado.getId();
            txtIdVendedor.setText(String.valueOf(id));
        }
    }//GEN-LAST:event_cboVendedorItemStateChanged

    private void cboAyudanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboAyudanteActionPerformed
        if(cboAyudante.getItemCount()>0){
            Empleado empSeleccionado= (Empleado) cboAyudante.getSelectedItem();
            int id=empSeleccionado.getId();
            txtIdAyudante.setText(String.valueOf(id));
        }
    }//GEN-LAST:event_cboAyudanteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDesactivar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnReporteComprobante;
    private javax.swing.JButton btnSeleccionarCliente;
    private javax.swing.JButton btnVerVenta;
    private javax.swing.JComboBox<String> cboAyudante;
    private javax.swing.JComboBox<String> cboNumPagina;
    private javax.swing.JComboBox<String> cboTotalPorPagina;
    private javax.swing.JComboBox<String> cboVendedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblTotalRegistros;
    private javax.swing.JTabbedPane tabGeneral;
    private javax.swing.JTable tablaCrementos;
    private javax.swing.JTable tablaDescuentos;
    private javax.swing.JTable tablaDetalles;
    private javax.swing.JTable tablaEfectivo;
    private javax.swing.JTable tablaListado;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtEfectivo;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtIdAyudante;
    public javax.swing.JTextField txtIdRuta;
    private javax.swing.JTextField txtIdVendedor;
    public javax.swing.JTextField txtNombreRuta;
    private javax.swing.JTextField txtNumComprobante;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTotalLiquidar;
    // End of variables declaration//GEN-END:variables
}
