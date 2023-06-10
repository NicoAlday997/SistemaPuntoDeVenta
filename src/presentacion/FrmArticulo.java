/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package presentacion;

import entidades.Categoria;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;
import negocio.ArticuloControl;
import javax.swing.ImageIcon;

/**
 *
 * @author alday
 */
public class FrmArticulo extends javax.swing.JInternalFrame {

    private final ArticuloControl CONTROL;
    private String accion;
    private String nombreAnt;
    
    private String rutaOrigen;
    private String rutaDestino;
    private final String DIRECTORIO="src/files/articulos/";
    private String imagen="";
    private String imagenAnt;
    
    private int totalPorPagina=10;
    private int numPagina=1;
    private boolean primeraCarga=true;
    private int totalRegistros;
    
    /**
     * Creates new form FrmCategoria
     */
    public FrmArticulo() {
        initComponents();
        this.CONTROL=new ArticuloControl();
        this.paginar();
        this.listar("",false);
        this.primeraCarga=false;
        tabGeneral.setEnabledAt(1,false );
        this.accion="guardar";
        txtId.setVisible(false);
        this.cargarCategorias();

    }
    
    private void ocultarColumnas()
    {
        tablaListado.getColumnModel().getColumn(1).setMaxWidth(0);
        tablaListado.getColumnModel().getColumn(1).setMinWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);
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
        if(cboNumPagina.getSelectedItem()!=null){
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
    
    private void cargarCategorias()
    {
        DefaultComboBoxModel items=this.CONTROL.seleccionar();
        cboCategoria.setModel(items);
    }
    
    private void subirImagenes()
    {
        File origen=new File(this.rutaOrigen);
        File destino=new File(this.rutaDestino);
        
        try {
            InputStream in=new FileInputStream(origen);
            OutputStream out=new FileOutputStream(destino);
            byte[] buf=new byte[1024];
            int len;
            
            while((len=in.read(buf))>0)
            {
                out.write(buf, 0, len);
            }
            
            in.close();
            out.close();
        } catch (IOException e) {
            
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
            
            
    }
    
     private void obtenerNumero() { 
        String codigo = this.CONTROL.ultimoNumero();
        if (codigo.equals("")){
            txtCodigo.setText("");
        }else{
            int num;
            num=Integer.parseInt(codigo)+1;
            txtCodigo.setText(Integer.toString(num));
        }
    }
    
    private void limpiar()
    {
        txtFormato.setEditable(true);
        txtFormato.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtId.setText("");
        txtPrecioVenta.setText("");
        //txtStock.setText("");
        this.imagen="";
        this.imagenAnt="";
        lblImagen.setIcon(null);
        this.rutaDestino="";
        this.rutaOrigen="";
        this.accion="guardar";
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
        btnEditar = new javax.swing.JButton();
        btnDesactivar = new javax.swing.JButton();
        btnActivar = new javax.swing.JButton();
        cboNumPagina = new javax.swing.JComboBox<>();
        cboTotalPorPagina = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        rptArticulos = new javax.swing.JButton();
        btnGenerarBarcode = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cboCategoria = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        btnAgregarImagen = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtFormato = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Articulos");

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

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnDesactivar.setText("Desactivar");
        btnDesactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarActionPerformed(evt);
            }
        });

        btnActivar.setText("Activar");
        btnActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivarActionPerformed(evt);
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

        rptArticulos.setText("Reporte Articulos");
        rptArticulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rptArticulosActionPerformed(evt);
            }
        });

        btnGenerarBarcode.setText("Generar BARCODE");
        btnGenerarBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarBarcodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 244, Short.MAX_VALUE)
                        .addComponent(rptArticulos)
                        .addGap(27, 27, 27)
                        .addComponent(btnGenerarBarcode))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(cboNumPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(cboTotalPorPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(81, 81, 81))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnDesactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnActivar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotalRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addComponent(jScrollPane1)
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
                    .addComponent(btnEditar)
                    .addComponent(rptArticulos)
                    .addComponent(btnGenerarBarcode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNumPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTotalPorPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDesactivar)
                    .addComponent(btnActivar)
                    .addComponent(lblTotalRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        tabGeneral.addTab("Listado", jPanel1);

        jLabel2.setText("Nombre(*)");

        jLabel3.setText("Descripción");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane2.setViewportView(txtDescripcion);

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

        txtId.setEditable(false);
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        jLabel5.setText("Categoría (*)");

        jLabel6.setText("Código");

        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        jLabel7.setText("Precio Venta (*)");

        txtPrecioVenta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtPrecioVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioVentaActionPerformed(evt);
            }
        });
        txtPrecioVenta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtPrecioVentaPropertyChange(evt);
            }
        });
        txtPrecioVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioVentaKeyTyped(evt);
            }
        });

        jLabel9.setText("Imagen");

        lblImagen.setBackground(new java.awt.Color(255, 255, 102));
        lblImagen.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnAgregarImagen.setText("Agregar");
        btnAgregarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarImagenActionPerformed(evt);
            }
        });

        jLabel8.setText("Formato (*)");

        txtFormato.setEditable(false);
        txtFormato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFormatoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtPrecioVenta)
                                .addComponent(txtFormato, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancelar)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAgregarImagen, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(305, 305, 305))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtPrecioVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtFormato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(36, 36, 36)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar)
                            .addComponent(btnCancelar))
                        .addGap(26, 26, 26))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregarImagen)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        this.listar(txtBuscar.getText(), false);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
         tabGeneral.setEnabledAt(1, true);
         tabGeneral.setEnabledAt(0, false);
         tabGeneral.setSelectedIndex(1);
         this.accion="guardar";
         btnGuardar.setText("Guardar");
         this.obtenerNumero();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
         tabGeneral.setEnabledAt(0, true);
         tabGeneral.setEnabledAt(1, false);
         tabGeneral.setSelectedIndex(0);
         this.limpiar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        if(txtNombre.getText().length()==0 || txtNombre.getText().length()>100)
        {
            JOptionPane.showMessageDialog(this, "Debes ingresar un nombre y no debe ser mayor a 100 caracteres, es obligatorio.", "Sistema", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return;
        }
        if(txtPrecioVenta.getText().length()==0)
        {
            JOptionPane.showMessageDialog(this, "Debes ingresar un precio de venta, es obligatorio.", "Sistema", JOptionPane.WARNING_MESSAGE);
            txtPrecioVenta.requestFocus();
            return;
        }
        
        if(txtDescripcion.getText().length()>255)
        {
            JOptionPane.showMessageDialog(this, "Debes ingresar una descripcion no mayor a 255 caracteres.", "Sistema", JOptionPane.WARNING_MESSAGE);
            txtDescripcion.requestFocus();
            return;
        }
        
        String resp;
        
        if(this.accion.equals("editar"))
        {
          //Editar
            String imagenActual="";
            if(this.imagen.equals(""))
            {
                imagenActual=this.imagenAnt;
            }else{
                imagenActual=this.imagen;
            }
            Categoria seleccionado=(Categoria)cboCategoria.getSelectedItem();

            resp=this.CONTROL.actualizar(Integer.parseInt(txtId.getText()),seleccionado.getId(),txtCodigo.getText(),txtNombre.getText(),this.nombreAnt,Double.parseDouble(txtPrecioVenta.getText()), Integer.parseInt(txtFormato.getText()), txtDescripcion.getText(), imagenActual);
            
            if(resp.equals("OK"))
            {
                if(!this.imagen.equals("")){
                    this.subirImagenes();
                }
                this.mensajeOk("Actualizado correctamente");
                this.limpiar();
                this.listar("", false);
                
                tabGeneral.setSelectedIndex(0);  
                tabGeneral.setEnabledAt(1, false);
                tabGeneral.setEnabledAt(0, true);
         
         
                
            }else{
                this.mensajeError(resp);
            }
        }else{
            //guardar
            Categoria seleccionado=(Categoria)cboCategoria.getSelectedItem();
             resp=this.CONTROL.insertar(seleccionado.getId(),txtCodigo.getText(),txtNombre.getText(),Double.parseDouble(txtPrecioVenta.getText()), Integer.parseInt(txtFormato.getText()), txtDescripcion.getText(), this.imagen);
            
            if(resp.equals("OK"))
            {
                if(!this.imagen.equals("")){
                    this.subirImagenes();
                }

                this.mensajeOk("Registrado correctamente");
                this.limpiar();
                this.listar("", false);
                
              /*  tabGeneral.setSelectedIndex(0);  
                tabGeneral.setEnabledAt(1, false);
                tabGeneral.setEnabledAt(0, true);*/
                
            }else{
                this.mensajeError(resp);
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        if(tablaListado.getSelectedRowCount()==1)
        {
            
            String id= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 0));
            int categoriaId=Integer.parseInt(String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 1)));
            String categoriaNombre=String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 2));
            String codigo=String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 3));   
            String nombre= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 4));
            this.nombreAnt= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 4));
            String formato= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 5));
            String precioVenta= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 6));
            String descripcion= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 9));
            this.imagenAnt= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 10));

            txtId.setVisible(true);
            txtId.setText(id);
            Categoria seleccionado=new Categoria(categoriaId, categoriaNombre);
            cboCategoria.setSelectedItem(seleccionado);
            txtCodigo.setText(codigo);
            txtNombre.setText(nombre);
            txtPrecioVenta.setText(precioVenta);
            txtCodigo.setText(codigo);
            txtFormato.setText(formato);
            txtFormato.setEditable(false);
            txtDescripcion.setText(descripcion);
            
            ImageIcon im=new ImageIcon(this.DIRECTORIO + this.imagenAnt);
            Icon icono=new ImageIcon(im.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_DEFAULT));
            lblImagen.setIcon(icono);
            lblImagen.repaint();
            
            tabGeneral.setEnabledAt(0, false);
            tabGeneral.setEnabledAt(1, true);
            tabGeneral.setSelectedIndex(1);
            this.accion="editar";
            btnGuardar.setText("Editar");

        }else
        {
            this.mensajeError("Selecciones 1 registro a editar.");
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarActionPerformed

        if(tablaListado.getSelectedRowCount()==1)
        {
            String id= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 0));
            String nombre= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 4));
            
            if(JOptionPane.showConfirmDialog(this, "Deseas desactivar el registro " + nombre + " ?","Desactivar", JOptionPane.YES_NO_OPTION)==0)
            {

                String resp=this.CONTROL.desactivar(Integer.parseInt(id));
                if(resp.equals("OK"))
                {
                    this.mensajeOk("Registro desactivado");
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

    private void btnActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarActionPerformed

        if(tablaListado.getSelectedRowCount()==1)
        {
            String id= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 0));
            String nombre= String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 4));
            
            if(JOptionPane.showConfirmDialog(this, "Deseas activar el registro " + nombre + " ?","Activar", JOptionPane.YES_NO_OPTION)==0)
            {

                String resp=this.CONTROL.activar(Integer.parseInt(id));
                if(resp.equals("OK"))
                {
                    this.mensajeOk("Registro activado");
                    this.listar("", false);
                }else{
                    this.mensajeError(resp);
                }
                
            }
        }else
        {
            this.mensajeError("Seleccione 1 registro a activar.");
        }
    }//GEN-LAST:event_btnActivarActionPerformed

    private void btnAgregarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarImagenActionPerformed

        JFileChooser file=new JFileChooser();
        int estado=file.showOpenDialog(this);
        
        if(estado==JFileChooser.APPROVE_OPTION)
        {
            this.imagen=file.getSelectedFile().getName();
            this.rutaOrigen=file.getSelectedFile().getAbsolutePath();
            this.rutaDestino=this.DIRECTORIO+this.imagen;
            
            ImageIcon im=new ImageIcon(this.rutaOrigen);
            Icon icono=new ImageIcon(im.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_DEFAULT));
            lblImagen.setIcon(icono);
            lblImagen.repaint();
            
        }
             
        
    }//GEN-LAST:event_btnAgregarImagenActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void cboNumPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNumPaginaActionPerformed

        if(this.primeraCarga==false){
            this.listar("", true);
        }
        
    }//GEN-LAST:event_cboNumPaginaActionPerformed

    private void cboTotalPorPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTotalPorPaginaActionPerformed

        this.paginar();
    }//GEN-LAST:event_cboTotalPorPaginaActionPerformed

    private void rptArticulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rptArticulosActionPerformed
        this.CONTROL.reporteArticulos();
    }//GEN-LAST:event_rptArticulosActionPerformed

    private void btnGenerarBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarBarcodeActionPerformed
        this.CONTROL.reporteArticulosBarras();
    }//GEN-LAST:event_btnGenerarBarcodeActionPerformed

    private void txtPrecioVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioVentaActionPerformed

    private void txtFormatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFormatoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFormatoActionPerformed

    private void txtPrecioVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioVentaKeyTyped

    private void txtPrecioVentaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtPrecioVentaPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioVentaPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActivar;
    private javax.swing.JButton btnAgregarImagen;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDesactivar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGenerarBarcode;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cboCategoria;
    private javax.swing.JComboBox<String> cboNumPagina;
    private javax.swing.JComboBox<String> cboTotalPorPagina;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblTotalRegistros;
    private javax.swing.JButton rptArticulos;
    private javax.swing.JTabbedPane tabGeneral;
    private javax.swing.JTable tablaListado;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtFormato;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JFormattedTextField txtPrecioVenta;
    // End of variables declaration//GEN-END:variables
}
