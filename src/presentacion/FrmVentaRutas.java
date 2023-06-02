/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package presentacion;


import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import negocio.VentaControlRuta;
import negocio.ArticuloControl;
import entidades.Empleado;
import java.awt.Color;
import java.lang.Object;
import negocio.ComprobacionControl;



/**
 *
 * @author alday
 */
public class FrmVentaRutas extends javax.swing.JInternalFrame {

    private final VentaControlRuta CONTROL;
    private final ComprobacionControl COMPCONTROL;
    private final ArticuloControl DATOSARTICULO;
    private String accion;
    private String nombreAnt; 
    private int totalPorPagina=10;
    private int numPagina=1;
    private boolean primeraCarga=true;
    private double subTotal;
    private int totalRegistros;
    private Color col,rojo,verde;
    private int cont;
    private String usuario;
    
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
        this.col=txtEstado.getBackground();
        this.cont=0;
        usuario=negocio.Variables.rolNombre;
        rojo=new java.awt.Color(250,22,20,50);
        verde=new java.awt.Color(0,199,88,50);
        this.contenedor=frmP;
        this.CONTROL=new VentaControlRuta();
        this.COMPCONTROL=new ComprobacionControl();
        this.DATOSARTICULO=new ArticuloControl();
        this.paginar();
        this.listar("",false);
        this.primeraCarga=false;
        tabGeneral.setEnabledAt(1,false );
        this.accion="guardar";
        this.subTotal=0.0;
        this.crearDetalles();
        this.calculaEfectivo();
        this.calculaCrementos();
        this.calculaDescuentos();
        this.cargarVendedores();
        this.cargarAyudantes();
        this.cargaArticulos();
    }
    
    private void cargaArticulos() {                                                       
    
        DefaultTableModel modeloTabla=new DefaultTableModel();
        modeloTabla=this.DATOSARTICULO.listarArticuloVentaRuta();
        
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String id= String.valueOf(modeloTabla.getValueAt(i, 0));
            String codigo= String.valueOf(modeloTabla.getValueAt(i, 1));
            String nombre= String.valueOf(modeloTabla.getValueAt(i, 2));
            String formato= String.valueOf(modeloTabla.getValueAt(i, 3));
            String stock= String.valueOf(modeloTabla.getValueAt(i, 4));
            String piezas= String.valueOf(modeloTabla.getValueAt(i, 5));
            String precio= String.valueOf(modeloTabla.getValueAt(i, 6));
            String comision= String.valueOf(modeloTabla.getValueAt(i, 7));
                        
            this.agregarDetalles(id, codigo, nombre, formato, stock, piezas, precio,"",comision);
            

        }
        
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
        
        int array[] = {1,3,5,7,12,13,14,15,16,17,18};
        this.ocultarColumnas(array);
        lblTotalRegistros.setText("Mostrando: " + this.CONTROL.totalMostrados()+ " de un total de "+ this.CONTROL.total() + " registros");
    }
    
    private void ocultarColumnas(int[] array)
    {
        
        for(int i=0;i<array.length;i++){
        tablaListado.getColumnModel().getColumn(array[i]).setMaxWidth(0);
        tablaListado.getColumnModel().getColumn(array[i]).setMinWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(array[i]).setMaxWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(array[i]).setMinWidth(0);
        }
        
        if(usuario.equals("Almacenero")|| usuario.equals("Vendedor")){  
        tablaListado.getColumnModel().getColumn(20).setMaxWidth(0);
        tablaListado.getColumnModel().getColumn(20).setMinWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(20).setMaxWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(20).setMinWidth(0);
        }
        
    }
    
    private void ocultarColumnasDetalles()
    {
        tablaDetalles.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaDetalles.getColumnModel().getColumn(0).setMinWidth(0);
        tablaDetalles.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        tablaDetalles.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        
        tablaDetalles.getColumnModel().getColumn(11).setMaxWidth(0);
        tablaDetalles.getColumnModel().getColumn(11).setMinWidth(0);
        tablaDetalles.getTableHeader().getColumnModel().getColumn(11).setMaxWidth(0);
        tablaDetalles.getTableHeader().getColumnModel().getColumn(11).setMinWidth(0); 
        
        if(usuario.equals("Almacenero")|| usuario.equals("Vendedor")){ 
                tablaDetalles.getColumnModel().getColumn(13).setMaxWidth(0);
                tablaDetalles.getColumnModel().getColumn(13).setMinWidth(0);
                tablaDetalles.getTableHeader().getColumnModel().getColumn(13).setMaxWidth(0);
                tablaDetalles.getTableHeader().getColumnModel().getColumn(13).setMinWidth(0); 
            }  

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
                   Double totSinDesc;
 
                   try {
                       otrosP=Double.parseDouble((String)getValueAt(0,1));
                       //utilidad=Double.parseDouble((String)getValueAt(row,5));
                   } catch (Exception e) {
                       otrosP=0.0;
                   }                  
                  
                  try {
                       creditosC=Double.parseDouble((String)getValueAt(1,1));
                   } catch (Exception e) {
                       creditosC=0.0;
                   }
                   
                   
                   if(otrosP!=null && creditosC!=null){
                       totSinDesc=subTotal+otrosP+creditosC;

                       return String.format("%.2f",totSinDesc);


                   }else{
                       return 0;
                   }
               }
       
               return super.getValueAt(row, col);
               
           }
                 
           
           @Override
           public void setValueAt(Object aValue, int row, int col){
               super.setValueAt(aValue, row, col);
               
              if(!isDouble((String)getValueAt(row, 1)) && !((String)getValueAt(row,1)).equals("")){
                   super.setValueAt(String.valueOf(0), row, 1);
                    mensajeError("Debe ingresar solo valores númericos");
               }
               
               
               try {
                   int cantD=Integer.parseInt((String)getValueAt(row, 1));
                   if(cantD<0){
                      super.setValueAt(0, row, 1);

                       mensajeError("La cantidad a ingresar debe ser mayor a 0: " + 0);

                   }
               } catch (Exception e) {
                   System.out.println(e.getMessage());
                   
               }

                calcularLiquidacion();
               
               fireTableDataChanged();
           }
       };
       
       modeloCrementos.setColumnIdentifiers(new Object[] {"Cremento","Monto"});
       
       modeloCrementos.addRow(new Object[]{"Otros productos",""});
       modeloCrementos.addRow(new Object[]{"Creditos Cobrados",""});
       modeloCrementos.addRow(new Object[]{"Total sin dscto",""});

 
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
               
               if(row==5 && col==1){
           
                   Double creditosOtorgados,gastosMedicos, refacciones, combustible;
                   Double promo, subTot;
           
                  try {
                       creditosOtorgados=Double.parseDouble((String)getValueAt(0,1));
                   } catch (Exception e) {
                       creditosOtorgados=0.0;
                   }
                  
                   try {
                       gastosMedicos=Double.parseDouble((String)getValueAt(1,1));
                   } catch (Exception e) {
                       gastosMedicos=0.0;
                   }
                  
                  
                  try {
                       refacciones=Double.parseDouble((String)getValueAt(2,1));
                   } catch (Exception e) {
                       refacciones=0.0;
                   }
                  
                  try {
                       combustible=Double.parseDouble((String)getValueAt(3,1));
                   } catch (Exception e) {
                       combustible=0.0;
                   }
                   
                  
                  try {
                       promo=Double.parseDouble((String)getValueAt(4,1));
                   } catch (Exception e) {
                       promo=0.0;
                   }
                  

                   
                   if(gastosMedicos!=null && refacciones!=null && combustible!=null && promo!=null){
                       subTot=creditosOtorgados+gastosMedicos+refacciones+combustible+promo;
  
                       return String.format("%.2f",subTot);
 
                   }else{
                       return 0;
                   }
               }
       
               return super.getValueAt(row, col);
               
           }
                 
           
           @Override
           public void setValueAt(Object aValue, int row, int col){
               super.setValueAt(aValue, row, col);
               
               if(!isDouble((String)getValueAt(row, 1)) && !((String)getValueAt(row,1)).equals("")){
                   super.setValueAt(String.valueOf(0), row, 1);
                    mensajeError("Debe ingresar solo valores númericos");
               }
               
               try {
                   int cantD=Integer.parseInt((String)getValueAt(row, 1));
                   if(cantD<0){
                      super.setValueAt(0, row, 1);

  
                       mensajeError("La cantidad a ingresar debe ser mayor a 0: " + 0);

    
                   }
               } catch (Exception e) {
                   System.out.println(e.getMessage());
                   
               }

               calcularLiquidacion();
               
               fireTableDataChanged();
           }
       };
       
       modeloDescuentos.setColumnIdentifiers(new Object[] {"Descuento","Monto"});
       
       modeloDescuentos.addRow(new Object[]{"Creditos otorgados",""});
       modeloDescuentos.addRow(new Object[]{"Gastos Medicos",""});
       modeloDescuentos.addRow(new Object[]{"Refacciones",""});
       modeloDescuentos.addRow(new Object[]{"Combustible",""});
       modeloDescuentos.addRow(new Object[]{"Otros Gastos",""});
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
                 

                   try {
                       cantD=Double.parseDouble((String)getValueAt(row,1));
                   } catch (Exception e) {
                       cantD=1.0;
                   }
                  
                  
                  try {
                       cant=Double.parseDouble((String)getValueAt(row,0));
                   } catch (Exception e) {
                       cant=0.0;
                   }
                   
                   
                   if(cantD!=null){
                       subTot=cantD*cant;

                       return String.format("%.2f",subTot);

                   }else{
                       return 0;
                   }
               }
       
               return super.getValueAt(row, col);
               
           }
                 
           
           @Override
           public void setValueAt(Object aValue, int row, int col){
               super.setValueAt(aValue, row, col);
               
               if(row!=6){
                   if(!isNumeric((String)getValueAt(row, 0)) && !((String)getValueAt(row,0)).equals("")){
                   super.setValueAt(String.valueOf(0), row, 0);
                    mensajeError("Debe ingresar solo valores númericos");
                }
               }
               
               if(row==6){
                   if(!isDouble((String)getValueAt(row, 0)) && !((String)getValueAt(row,0)).equals("")){
                       super.setValueAt(String.valueOf(0), row, 0);
                       mensajeError("Debe ingresar solo valores númericos");
                   }
               }
               

               
               try {
                   int cantD=Integer.parseInt((String)getValueAt(row, 0));
                   if(cantD<0){
                       super.setValueAt(0, row, 0);

  
                       mensajeError("La cantidad a ingresar debe ser mayor a 0: " + 0);
                       
                   }
               } catch (Exception e) {
                   System.out.println(e.getMessage());
                   
               }

               calcularTotEfectivo();
               calcularLiquidacion();
               
               fireTableDataChanged();
           }
       };
       
       
       modeloEfectivo.setColumnIdentifiers(new Object[] {"Cant.","Denom.","Total"});
     
        modeloEfectivo.addRow(new Object[]{"","20","0"});
        modeloEfectivo.addRow(new Object[]{"","50","0"});
        modeloEfectivo.addRow(new Object[]{"","100","0"});
        modeloEfectivo.addRow(new Object[]{"","200","0"});
        modeloEfectivo.addRow(new Object[]{"","500","0"});
        modeloEfectivo.addRow(new Object[]{"","1000","0"});
        modeloEfectivo.addRow(new Object[]{"","1","0"});
        

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
                
            }
        }
        
        txtEfectivo.setText(String.format("%.2f", total));
  
    }
    
    private void calcularLiquidacion()
    {
        double totLiquidar=0, detalleVenta=0, estado=0;
        double efectivo=0, crementos=0, descuentos=0;
        double otrosProductos, creditosOtorgados;
        
        try {
            otrosProductos=Double.parseDouble(String.valueOf(modeloCrementos.getValueAt(0, 1)));
        } catch (Exception e) {
            otrosProductos=0.0;
        }
        
        try {
            creditosOtorgados=Double.parseDouble(String.valueOf(modeloCrementos.getValueAt(1, 1)));
        } catch (Exception e) {
            creditosOtorgados=0.0;
        }
            
        
        efectivo=Double.parseDouble(txtEfectivo.getText());
        
        detalleVenta=(Double.parseDouble(txtSubTotal.getText())-(Double.parseDouble(txtDescuentos.getText())));
        
        crementos=otrosProductos+creditosOtorgados;
        
        descuentos=Double.parseDouble(String.valueOf(modeloDescuentos.getValueAt(5, 1)));
        
        
        totLiquidar=detalleVenta+crementos-descuentos;
        
        estado=efectivo-totLiquidar;
        
        if(estado<0){
            txtEstado.setBackground(rojo);
            lblEstado.setText("Faltante");
        }else if(estado>0){
            txtEstado.setBackground(verde);
            lblEstado.setText("Sobrante");
        }else if(estado==0){
            txtEstado.setBackground(col);
            lblEstado.setText("OK");
        }
        
        txtEstado.setText(String.format("%.2f", estado));
        
        
        txtTotalLiquidar.setText(String.format("%.2f", totLiquidar));
  
    }
    
    private void crearDetalles(){
                
        modeloDetalles=new DefaultTableModel(){
            
           @Override
           public boolean isCellEditable(int fila, int columna){
   
               if(columna==6){
                   return columna==6;
               }
               if(columna==7){
                   return columna==7;
               }
               if(columna==8){
                   
                   return columna==8;
               }
               if(columna==9){
                   
                   return columna==9;
               }
               return columna==6;
           }
           
           @Override
           public Object getValueAt(int row, int col){
           
               //calcular descuentos y subtotal
               
               
               if(col==10){
                   Double cantidad;
                   Double piezas;
                   Double precioPieza;
                   Double totComision;
                   Double comXpieza;
                   
                   try {
                       cantidad=Double.parseDouble((String)getValueAt(row,6));
                       //utilidad=Double.parseDouble((String)getValueAt(row,5));
                   } catch (Exception e) {
                       cantidad=0.0;
                   }
                   
                   try {
                       piezas=Double.parseDouble((String)getValueAt(row,7));
                       //utilidad=Double.parseDouble((String)getValueAt(row,5));
                   } catch (Exception e) {
                       piezas=0.0;
                   }
                   
                   
                   Double formato=Double.parseDouble((String)getValueAt(row,3));
                   
                   Double precio, descuentoD;
                   
                   try{
                      precio=Double.parseDouble((String)getValueAt(row,8));
                   }catch(Exception e){
                       precio=0.0;
                   }             
                   
                   try{
                     descuentoD=Double.parseDouble((String)getValueAt(row,9));

                   }catch(Exception e){
                      descuentoD=0.0;
                   }
                   
                   Double comXunidad = Double.parseDouble((String) getValueAt(row, 11));

                   
                   precioPieza=(precio/formato);
         
                   
                   if(cantidad!=null && precio!=null && descuentoD!=null && precioPieza!=null){
                       tablaCrementos.repaint();
                       super.setValueAt(String.format("%.2f", (((cantidad*precio)+(piezas*precioPieza))-descuentoD)), row, 10);
                       return String.format("%.2f",((cantidad*precio)+(piezas*precioPieza))-descuentoD);

                   }else{
                       return 0;
                   }          
                   
               }
               
               
               if(col==12){
                   Double cantidad;
                   Double piezas;
                   Double totComision;
                   Double comXpieza;
                   
                
                   try {
                       cantidad=Double.parseDouble((String)getValueAt(row,6));
                   } catch (Exception e) {
                       cantidad=0.0;
                   }
                   
                   try {
                       piezas=Double.parseDouble((String)getValueAt(row,7));
                   } catch (Exception e) {
                       piezas=0.0;
                   }
                   
                    Double formato=Double.parseDouble((String)getValueAt(row,3));                   
                                      
                   Double comXunidad = Double.parseDouble((String) getValueAt(row, 11));
                   comXpieza=(comXunidad/formato);
                   
                   
                   
                   if(cantidad!=null && comXunidad!=null && comXpieza!=null){

                       totComision=((cantidad*comXunidad)+(piezas*comXpieza));
                       super.setValueAt(String.format("%.2f", totComision), row, 12);
                       return String.format("%.2f",totComision);

                   }else{
                       return 0;
                   }
                   
                   
                   
               }
       
               return super.getValueAt(row, col);
               
           }
                 
           
           @Override
           public void setValueAt(Object aValue, int row, int col){
               super.setValueAt(aValue, row, col);
               
               if(col==6 || col==7){
                   if(!isNumeric((String)getValueAt(row, col))){
                       super.setValueAt(String.valueOf(0), row, col);
                       mensajeError("Debe ingresar solo valores númericos");
                    } 
               }
               
               if(col==8){
                   if(!isDouble((String)getValueAt(row, col))){
                       super.setValueAt(String.valueOf(0), row, col);
                       mensajeError("Debe ingresar solo valores númericos");
                    } 
               }
               
               
               
               try {
                  
                   int cantU=Integer.parseInt((String)getValueAt(row, 6));
                   int stockD=Integer.parseInt((String)getValueAt(row, 4));

                   if(cantU>stockD){
                       super.setValueAt(String.valueOf(stockD), row, 6);
 
                       mensajeError("La cantidad a vender no puede superar el stock. Usted puede vender como máximo: " + stockD);   
   
                   }    
    
                   
               } catch (Exception e) {
                   System.out.println(e.getMessage());
                   
               }
               
               try {
      
                   int piezas=Integer.parseInt((String)getValueAt(row, 7));
                   
                   int formato=Integer.parseInt((String)getValueAt(row, 3));
                   
                   if(piezas>(formato-1)){
                       super.setValueAt(String.valueOf(formato-1), row, 7);
  
                       mensajeError("La cantidad de piezas no puede superar el formato. Usted puede ingresar como máximo: " + (formato-1));          

                   }    
                   
                   
                   
                   
               } catch (Exception e) {
                   System.out.println(e.getMessage());
                   
               }
               
               try{
                   
                   int cantU, piezas, stockD, stockPiezas;
                      
                   try {
                       cantU=Integer.parseInt((String)getValueAt(row, 6));
                   } catch (Exception e) {
                       cantU=0;
                   }
                   
                   try {
                       piezas=Integer.parseInt((String)getValueAt(row, 7));
                   } catch (Exception e) {
                       piezas=0;
                   }

                   stockD=Integer.parseInt((String)getValueAt(row, 4));
                   stockPiezas=Integer.parseInt((String)getValueAt(row, 5));
                   
                   
                   if(cantU==stockD){
                       
                       if(piezas>stockPiezas){
                             super.setValueAt(String.valueOf(stockPiezas), row, 7);

                       mensajeError("La cantidad de piezas no puede superar el stock. Usted puede ingresar como máximo: " + stockPiezas);  
                       }
                             
                     
    
                   } 
               }catch(Exception e){
                                      System.out.println(e.getMessage());

               }

               calcularTotales();
               calcularLiquidacion();
               
               fireTableDataChanged();
           }
           
           
        };
        modeloDetalles.setColumnIdentifiers(new Object[] {"ID","CODIGO","ARTICULO","FORMATO","S. UDES","S. PZS","CANTIDAD","PIEZAS","PRECIO","DESCUENTO","SUBTOTAL","COM X U","COMISION","UTILIDAD"});

      
        tablaDetalles.setModel(modeloDetalles);

        this.ocultarColumnasDetalles();
    }
    
     private boolean isNumeric(String str){
         
         boolean isNumeric = str.chars().allMatch( Character::isDigit );    
         return isNumeric;
    }
     
    private boolean isDouble(String str){
        boolean resp=false;
        try{
            Double num=Double.valueOf(str);
            resp=true;

        }catch(Exception e){
          resp=false;
    }
        return resp;
    }
    
    public void agregarDetalles(String id, String codigo, String nombre,String formato,String stock, String piezas, String precio, String descuento, String comision){
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

            this.modeloDetalles.addRow(new Object[]{id,codigo,nombre,formato,stock, piezas,"","",precio,"","0",comision,"0","0"});
            this.calcularTotales();
        }
    }
    
     
    private void calcularTotales(){

        double total=0;
        double comision=0;
        double desuentos=0, aux;
        
        int items=modeloDetalles.getRowCount();
        if(items==0){
            total=0;
        }else{
            for (int i = 0; i < items; i++) {
                try{
                    aux=(Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 9))));
                }catch(Exception e){
                   aux=0;
                }
                desuentos=desuentos+aux; 
                total=total+(Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 10)))); 
                comision=comision+(Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 12)))); 
            
                
            }
        }
        
        subTotal=total/(1);
        subTotal=subTotal+desuentos; 

        txtDescuentos.setText(String.format("%.2f", desuentos));
        txtSubTotal.setText(String.format("%.2f", subTotal));
        txtComision.setText(String.format("%.2f", comision));
        //this.calculaCrementos();

    }
  
     private void obtenerNumero() {
        String serieComprobante = this.CONTROL.ultimoSerie();
        String numComprobante = this.CONTROL.ultimoNumero(serieComprobante);
        txtSerieComprobante.setText(serieComprobante);
        if (numComprobante.equals("")){
            txtNumComprobante.setText("");
        }else{
            int num;
            num=Integer.parseInt(numComprobante)+1;
            txtNumComprobante.setText(Integer.toString(num));
        }        
    }
     
    private void comprobacion(){
        entidades.Comprobacion comp;
        comp=this.COMPCONTROL.obtenerComprobacion();
            
        //System.out.println(comp);
            
        if(comp.getPzsArticulo() != comp.getPzsDetalle()){
            this.mensajeError("Error de COMPROBACIÓN");
        }
    }
    
    

    
    private void limpiar()
    {
        txtNombreRuta.setText("");
        txtIdRuta.setText("");
        txtNumComprobante.setText("");
        
  
        this.limpiarTablas();
        
        this.accion="guardar";
        
        txtTotalLiquidar.setText("0.00");
        txtEstado.setText("0.00");
        txtSubTotal.setText("0.00");
        txtDescuentos.setText("0.00");
        this.obtenerNumero();
        
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
    //validar con piezas
             if ((String.valueOf(modeloDetalles.getValueAt(i - remov, 6)) == null || String.valueOf(modeloDetalles.getValueAt(i-remov, 6)).equals("") || Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i-remov, 6)))==0) && (String.valueOf(modeloDetalles.getValueAt(i - remov, 7)) == null || String.valueOf(modeloDetalles.getValueAt(i-remov, 7)).equals("") || Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i-remov, 7)))==0) ) {

                 modeloDetalles.removeRow(i - remov);
                 remov++;
             }

         }
         
         tamaño=modeloDetalles.getRowCount();;
         
         for (int i = 0; i < tamaño; i++) {
             
             modeloDetalles.setValueAt(validaCampo((String)modeloDetalles.getValueAt(i,6)), i, 6); 
             modeloDetalles.setValueAt(validaCampo((String)modeloDetalles.getValueAt(i,7)), i, 7); 
             modeloDetalles.setValueAt(validaCampo((String)modeloDetalles.getValueAt(i,9)), i, 9); 
         }

 
        
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
        btnReporteVentaRuta = new javax.swing.JButton();
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
        lblEstado = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        txtTotalLiquidar = new javax.swing.JTextField();
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
        txtComision = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSerieComprobante = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDescuentos = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

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

        btnReporteVentaRuta.setText("Reporte");
        btnReporteVentaRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteVentaRutaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 331, Short.MAX_VALUE)
                        .addComponent(btnReporteVentaRuta)
                        .addGap(85, 85, 85)
                        .addComponent(btnVerVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnDesactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTotalRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(cboNumPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(cboTotalPorPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
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
                    .addComponent(btnReporteVentaRuta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNumPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTotalPorPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDesactivar)
                    .addComponent(lblTotalRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
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
        tablaDetalles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDetallesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDetalles);

        jLabel9.setText("Subtotal");

        lblEstado.setText("OK");

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

        txtIdVendedor.setEditable(false);

        txtIdAyudante.setEditable(false);

        txtComision.setEditable(false);
        txtComision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComisionActionPerformed(evt);
            }
        });

        jLabel6.setText("Comision");

        txtSerieComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSerieComprobanteActionPerformed(evt);
            }
        });

        jLabel8.setText("Serie Comprobante (*)");

        txtDescuentos.setEditable(false);

        jLabel12.setText("Desc. Articulos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtSerieComprobante)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSeleccionarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIdAyudante, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(cboAyudante, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(113, 113, 113))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addGap(42, 42, 42)
                        .addComponent(btnCancelar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                        .addGap(614, 614, 614)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(txtTotalLiquidar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtComision)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txtDescuentos, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(15, 15, 15)
                        .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel7)
                        .addComponent(txtNumComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtSerieComprobante, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtComision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel12)
                                    .addComponent(txtDescuentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblEstado)
                                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalLiquidar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tabGeneral.addTab("Mantenimiento", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabGeneral, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabGeneral)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            JOptionPane.showMessageDialog(this, "Debes seleccionar una ruta.","Sistema", JOptionPane.WARNING_MESSAGE);
            btnSeleccionarCliente.requestFocus();
            return;
        }


        if(txtNumComprobante.getText().length()==0 || txtNumComprobante.getText().length()>10){
            JOptionPane.showMessageDialog(this, "Debes ingresar un número de comprobante no mayor a 10 caracteres.","Sistema", JOptionPane.WARNING_MESSAGE);
            txtNumComprobante.requestFocus();
            return;
        }

        

        String resp="";
      
        eliminaDetallesVacios();
        
        if(modeloDetalles.getRowCount()==0){
            JOptionPane.showMessageDialog(this, "Debes agregar articulos al detalle.","Sistema", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Double otrosProductos=Double.parseDouble(this.validaCampo((String)modeloCrementos.getValueAt(0, 1)));
        Double creditosCobrados=Double.parseDouble(this.validaCampo((String)modeloCrementos.getValueAt(1, 1)));
        Double creditosOtorgados=Double.parseDouble(this.validaCampo((String)modeloDescuentos.getValueAt(0, 1)));
        Double gastosMedicos=Double.parseDouble(this.validaCampo((String)modeloDescuentos.getValueAt(1, 1)));
        Double refacciones=Double.parseDouble(this.validaCampo((String)modeloDescuentos.getValueAt(2, 1)));
        Double combustible=Double.parseDouble(this.validaCampo((String)modeloDescuentos.getValueAt(3, 1)));
        Double otrosGastos=Double.parseDouble(this.validaCampo((String)modeloDescuentos.getValueAt(4, 1)));

        resp=this.CONTROL.insertar(Integer.parseInt(txtIdRuta.getText()), Integer.parseInt(txtIdVendedor.getText()),Integer.parseInt(txtIdAyudante.getText()),txtSerieComprobante.getText(),txtNumComprobante.getText(), otrosProductos, creditosCobrados, creditosOtorgados, gastosMedicos, refacciones, combustible, otrosGastos,Double.parseDouble(txtEfectivo.getText()), Double.parseDouble(txtTotalLiquidar.getText()), modeloDetalles);

        
        if (resp.equals("OK")) {

            this.mensajeOk("Registrado correctamente");
            this.comprobacion();
            this.limpiar();
            this.listar("", false);

            /*  tabGeneral.setSelectedIndex(0);
            tabGeneral.setEnabledAt(1, false);
            tabGeneral.setEnabledAt(0, true);*/
        } else {
            this.mensajeError(resp);
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnVerVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerVentaActionPerformed
        if(tablaListado.getSelectedRowCount()==1){
  
            String id= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),0));
            String idRuta= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),3));
            String nombreRuta= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),4));
            Integer idVendedor= Integer.parseInt(String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),5)));
            String nombreVendedor= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),6));
            Integer idAyudante= Integer.parseInt(String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),7)));
            String nombreAyudante= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),8));
            String serie= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),9));
            String numero= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),10));
            String otrosProductos= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),12));
            String creditosCobrados= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),13));
            String creditosOtorgados= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),14));
            String gastosMedicos= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),15));
            String refacciones= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),16));
            String combustible= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),17));
            String otrosGastos= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),18));
            //String totalLiquidar= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),19));
            String efectivo= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),22));
                                            
                        
           Empleado vendedor=new Empleado(idVendedor, nombreVendedor);
           DefaultComboBoxModel items=this.CONTROL.seleccionar();
           items.setSelectedItem(vendedor.getNombre());
           cboVendedor.setModel(items);  
           txtIdVendedor.setText(String.valueOf(vendedor.getId()));
           
           Empleado ayudante=new Empleado(idAyudante, nombreAyudante);
           DefaultComboBoxModel items2=this.CONTROL.seleccionarAyudante();
           items2.setSelectedItem(ayudante.getNombre());
           cboAyudante.setModel(items2);  
           txtIdAyudante.setText(String.valueOf(ayudante.getId()));
             
            txtIdRuta.setText(idRuta);
            txtNombreRuta.setText(nombreRuta);

            txtSerieComprobante.setText(serie);
            txtNumComprobante.setText(numero);
            txtEfectivo.setText(efectivo);
            
            modeloCrementos.setValueAt(otrosProductos, 0, 1);
            modeloCrementos.setValueAt(creditosCobrados, 1, 1);
            
            modeloDescuentos.setValueAt(creditosOtorgados, 0, 1);
            modeloDescuentos.setValueAt(gastosMedicos, 1, 1);
            modeloDescuentos.setValueAt(refacciones, 2, 1);
            modeloDescuentos.setValueAt(combustible, 3, 1);
            modeloDescuentos.setValueAt(otrosGastos, 4, 1);

            this.modeloDetalles=CONTROL.listarDetalle(Integer.parseInt(id));
            tablaDetalles.setModel(modeloDetalles);
            this.ocultarColumnasDetalles();
             
            
            this.calcularTotales(); 
            this.calcularLiquidacion();
            
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
            String comprobante= "LIQUIDACIÓN";
            String serie= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 9));
            String numero= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 10));
            String estado= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 21));

            if(estado.equals("Anulado"))
            {
                this.mensajeError("No puede Anular un registro Anulado");
            }else
            {
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
            }
            
           
        }else
        {
            this.mensajeError("Seleccione 1 registro a desactivar.");
        }
    }//GEN-LAST:event_btnDesactivarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        tablaDetalles.getColumnModel().getColumn(13).setMaxWidth(0);
        tablaDetalles.getColumnModel().getColumn(13).setMinWidth(0);
        tablaDetalles.getTableHeader().getColumnModel().getColumn(13).setMaxWidth(0);
        tablaDetalles.getTableHeader().getColumnModel().getColumn(13).setMinWidth(0);
        
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

    private void tablaDetallesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDetallesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaDetallesMouseClicked

    private void txtSerieComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSerieComprobanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSerieComprobanteActionPerformed

    private void btnReporteVentaRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteVentaRutaActionPerformed

        if(tablaListado.getSelectedRowCount()==1){ 
            String id= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),0));  
            this.CONTROL.reporteVentaRuta(id);
        }else{
            this.mensajeError("Seleccione la venta para ver su reporte.");
        }
    }//GEN-LAST:event_btnReporteVentaRutaActionPerformed

    private void txtComisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComisionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComisionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDesactivar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnReporteVentaRuta;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblTotalRegistros;
    private javax.swing.JTabbedPane tabGeneral;
    private javax.swing.JTable tablaCrementos;
    private javax.swing.JTable tablaDescuentos;
    private javax.swing.JTable tablaDetalles;
    private javax.swing.JTable tablaEfectivo;
    private javax.swing.JTable tablaListado;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtComision;
    private javax.swing.JTextField txtDescuentos;
    private javax.swing.JTextField txtEfectivo;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtIdAyudante;
    public javax.swing.JTextField txtIdRuta;
    private javax.swing.JTextField txtIdVendedor;
    public javax.swing.JTextField txtNombreRuta;
    private javax.swing.JTextField txtNumComprobante;
    private javax.swing.JTextField txtSerieComprobante;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTotalLiquidar;
    // End of variables declaration//GEN-END:variables
}
