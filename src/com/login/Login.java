/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.login;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import negocio.UsuarioControl;
import presentacion.FrmPrincipal;

/**
 *
 * @author alday
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/presentacion/images/punto_venta.png")).getImage());
        txtEmail.setForeground(Color.gray);
        txtClave.setForeground(Color.gray);
        txtEmail.setText("irvin.alday290999@gmail.com");
        txtClave.setText("1234");
        /*txtEmail.setText("alday997@gmail.com");
        txtClave.setText("administrador");*/

    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        bg = new javax.swing.JPanel();
        btnIniciarSesion = new javax.swing.JLabel();
        btnUsuario = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        sepUsuario = new javax.swing.JSeparator();
        sepPassword = new javax.swing.JSeparator();
        btnContraseña = new javax.swing.JLabel();
        txtClave = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        panelEntrar = new javax.swing.JPanel();
        btnEntrar = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jTextField2.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");
        setLocationByPlatform(true);
        setResizable(false);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnIniciarSesion.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnIniciarSesion.setForeground(new java.awt.Color(0, 0, 0));
        btnIniciarSesion.setText("INICIAR SESIÓN");
        bg.add(btnIniciarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 230, 30));

        btnUsuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnUsuario.setForeground(new java.awt.Color(0, 0, 0));
        btnUsuario.setText("USUARIO");
        bg.add(btnUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, -1, -1));

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));
        txtEmail.setText("Ingrese su nombre de usuario");
        txtEmail.setBorder(null);
        txtEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtEmailMousePressed(evt);
            }
        });
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        bg.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 370, 30));
        bg.add(sepUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 370, -1));
        bg.add(sepPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 290, 370, -1));

        btnContraseña.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnContraseña.setForeground(new java.awt.Color(0, 0, 0));
        btnContraseña.setText("CONTRASEÑA");
        bg.add(btnContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 230, -1, -1));

        txtClave.setBackground(new java.awt.Color(255, 255, 255));
        txtClave.setText("********");
        txtClave.setBorder(null);
        txtClave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtClaveMousePressed(evt);
            }
        });
        txtClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClaveActionPerformed(evt);
            }
        });
        bg.add(txtClave, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 370, 30));

        jPanel2.setBackground(new java.awt.Color(124, 54, 241));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentacion/images/Inventario (1).png"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentacion/images/logo2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(202, 202, 202))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );

        bg.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 410));

        panelEntrar.setBackground(new java.awt.Color(124, 54, 241));

        btnEntrar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnEntrar.setForeground(new java.awt.Color(255, 255, 255));
        btnEntrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEntrar.setText("ENTRAR");
        btnEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEntrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntrarMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelEntrarLayout = new javax.swing.GroupLayout(panelEntrar);
        panelEntrar.setLayout(panelEntrarLayout);
        panelEntrarLayout.setHorizontalGroup(
            panelEntrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEntrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEntrar, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelEntrarLayout.setVerticalGroup(
            panelEntrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEntrar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        bg.add(panelEntrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, 120, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseEntered
        btnEntrar.setBackground(new Color(60,63,65));
    }//GEN-LAST:event_btnEntrarMouseEntered

    private void txtEmailMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEmailMousePressed
        if(txtEmail.getText().equals("Ingrese su nombre de usuario")){
            txtEmail.setText("");
            txtEmail.setForeground(Color.black);
        }
        
        if(String.valueOf(txtClave.getPassword()).isEmpty()){
            txtClave.setText("********");
            txtClave.setForeground(Color.gray);
        }
       
    }//GEN-LAST:event_txtEmailMousePressed

    private void txtClaveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtClaveMousePressed
        if(String.valueOf(txtClave.getPassword()).equals("********"))
        {
        txtClave.setText("");
        txtClave.setForeground(Color.black);
        }
        
        if(txtEmail.getText().isEmpty()){
           txtEmail.setText("Ingrese su nombre de usuario");
          txtEmail.setForeground(Color.gray);
        }

        

    }//GEN-LAST:event_txtClaveMousePressed

    private void txtClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClaveActionPerformed
    }//GEN-LAST:event_txtClaveActionPerformed

    private void btnEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseClicked
        if(txtEmail.getText().length()==0 || txtEmail.getText().length()>50)
        {
            JOptionPane.showMessageDialog(this, "Debes ingresar un email, y este no debe superar los 50 caracteres.", "Sistema", JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return;
        }
        
        if(txtClave.getText().length()==0 || txtClave.getText().length()>64)
        {
            JOptionPane.showMessageDialog(this, "Debes ingresar una clave, y este no debe superar los 64 caracteres.", "Sistema", JOptionPane.WARNING_MESSAGE);
            txtClave.requestFocus();
            return;
        }
        
        UsuarioControl control=new UsuarioControl();
        String resp=control.login(txtEmail.getText(), txtClave.getText());
        if(resp.equals("1")){
            this.dispose();
            FrmPrincipal frm=new FrmPrincipal();
            frm.toFront();
            frm.setVisible(true);
        }else if(resp.equals("2")){
            JOptionPane.showMessageDialog(this, "Usuario no tiene acceso.", "Sistema", JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "Los datos de acceso son incorrectos.", "Sistema", JOptionPane.ERROR_MESSAGE);

        }
        
    }//GEN-LAST:event_btnEntrarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JLabel btnContraseña;
    private javax.swing.JLabel btnEntrar;
    private javax.swing.JLabel btnIniciarSesion;
    private javax.swing.JLabel btnUsuario;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPanel panelEntrar;
    private javax.swing.JSeparator sepPassword;
    private javax.swing.JSeparator sepUsuario;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JTextField txtEmail;
    // End of variables declaration//GEN-END:variables
}
