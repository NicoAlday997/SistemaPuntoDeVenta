/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package presentacion;

import java.util.Calendar;
import java.sql.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import negocio.IngresoControl;
import negocio.VentaControl;


/**
 *
 * @author alday
 */
public class FrmConsultaComprasFecha extends javax.swing.JInternalFrame {

    private final IngresoControl CONTROL;
    private int totalRegistros;
    
    public DefaultTableModel modeloDetalles;
    public JFrame contenedor;
    
    /**
     * Creates new form FrmCategoria
     */
    public FrmConsultaComprasFecha() {
        initComponents();
        this.CONTROL=new IngresoControl();
    }
   
    private void listar()
    {
        Calendar cal;
        Date fechaIni;
        Date fechaFin;
        int d,m,a;
        
        cal=dcFechaInicio.getCalendar();
        d=cal.get(Calendar.DAY_OF_MONTH);
        m=cal.get(Calendar.MONTH);
        a=cal.get(Calendar.YEAR)-1900;
        fechaIni=new Date(a,m,d);
        
        cal=dcFechaFin.getCalendar();
        d=cal.get(Calendar.DAY_OF_MONTH);
        m=cal.get(Calendar.MONTH);
        a=cal.get(Calendar.YEAR)-1900;
        fechaFin=new Date(a,m,d);
        
        
        
        tablaListado.setModel(this.CONTROL.consultaFechas(fechaIni, fechaFin));

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
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaListado = new javax.swing.JTable();
        lblTotalRegistros = new javax.swing.JLabel();
        btnReporteComprobante = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        dcFechaFin = new com.toedter.calendar.JDateChooser();
        dcFechaInicio = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consulta de compras entre fechas");
        setToolTipText("");

        tabGeneral.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Fecha Inicio");

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

        btnReporteComprobante.setText("Reporte ");
        btnReporteComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteComprobanteActionPerformed(evt);
            }
        });

        jLabel14.setText("Fecha Fin");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1050, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblTotalRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
                        .addComponent(btnReporteComprobante)
                        .addGap(338, 338, 338)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(dcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(btnReporteComprobante)
                            .addComponent(jLabel14)
                            .addComponent(btnBuscar)))
                    .addComponent(dcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTotalRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tabGeneral.addTab("Listado", jPanel1);

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

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        this.listar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnReporteComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteComprobanteActionPerformed
        if(tablaListado.getSelectedRowCount()==1){
            String id= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(),0));
            this.CONTROL.reporteComprobante(id);
        }else{
            this.mensajeError("Seleccione la venta para ver su reporte.");
        }
    }//GEN-LAST:event_btnReporteComprobanteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnReporteComprobante;
    private com.toedter.calendar.JDateChooser dcFechaFin;
    private com.toedter.calendar.JDateChooser dcFechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotalRegistros;
    private javax.swing.JTabbedPane tabGeneral;
    private javax.swing.JTable tablaListado;
    // End of variables declaration//GEN-END:variables
}
