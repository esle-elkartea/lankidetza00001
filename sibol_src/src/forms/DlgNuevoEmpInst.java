/*
 * DlgNuevoEmpInst.java
 *
 * Created on 8 de agosto de 2006, 9:01
 */

package forms;

import funciones.AutoComplCmbBxRestrictivo;
import funciones.BaseDatos;
import funciones.LimitadorCaracteres;
import funciones.ParCombo;
import funciones.Sesion;
import funciones.Utilidades;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import main.Constantes;
import main.Mensaje;

/**
 *
 * @author  enrique
 */
public class DlgNuevoEmpInst extends javax.swing.JDialog {
    
    private String  nombreEmp, numEmpresa, eibt, nombreInst, numCarne, ccbt, tfno, email, nif;
    private ParCombo pcCat, pcMod;
    private FrmGestionPnl frmGestion = null;
    private BaseDatos bd = null;
    private String txtEmpInst;
    private boolean esActualizar = false;
    
    /**
     * Creates new form DlgNuevoEmpInst
     */
    public DlgNuevoEmpInst(javax.swing.JFrame frame, boolean modal) {
        super(frame, modal);
        bd = Sesion.getInstance().getBaseDatos();
        initComponents();
        frmGestion = (FrmGestionPnl)Sesion.getInstance().getValorHt("objFrmGestionPnl");
        UIManager.put("ComboBox.disabledBackground", new Color(Constantes.GRIS_COMBOS,Constantes.GRIS_COMBOS,Constantes.GRIS_COMBOS));
        UIManager.put("ComboBox.disabledForeground", Color.BLACK);
        establecerRestricciones();
        agregarDatosCombox();
        
    }
    
    //Validación de todo el formulario.
    //Comprueba los datos de la pestaña EMPRESA/INSTALADOR
    private boolean esCorrectoEmpInst()
    {
        boolean nombreEmp, numEmpresa, nombreInst, numCarne, tfno, email, nif;
        txtEmpInst = "\n * Error en la introducción de los datos:\n";
        nombreEmp = true;
        numEmpresa = true;
        nombreInst = true;
        numCarne = true;
        tfno = true;
        email = true;
        nif = true;
       
        if(jTxtFldNombreEmpresa.getText().equals(""))
        {
            nombreEmp=false;
            txtEmpInst += "      - APELLIDOS Y NOMBRE O RAZÓN SOCIAL no debe de estar vacío.\n";
        }
        if(jTxtFldNumEmpresa.getText().equals("") || jTxtFldEibt.getText().equals(""))
        {
            numEmpresa=false;
            txtEmpInst += "      - Nº de EMPRESA no debe contener ninguna casilla vacía.\n";
        }
        else if(!Utilidades.esLongitudTexto(jTxtFldNumEmpresa.getText(), 2) || !Utilidades.esLongitudTexto(jTxtFldEibt.getText(), 5))
        {
            numEmpresa=false;
            txtEmpInst += "      - Nº de EMPRESA: Compruebe que su 1ª casilla tenga 2 dígitos y su 2ª casilla 5 dígitos.\n";
        }
        
        if(jTxtFldNombreInst.getText().equals(""))
        {
            nombreInst=false;
            txtEmpInst += "      - NOMBRE DEL INSTALADOR no debe de estar vacío.\n";    
        }       
        if(jTxtFldNumCarne.getText().equals("") || jTxtFldCcbt.getText().equals(""))
        {
            numCarne=false;
            txtEmpInst += "      - Nº del CARNÉ no debe contener ninguna casilla vacía.\n";
        }
        else if(!Utilidades.esLongitudTexto(jTxtFldNumCarne.getText(),2) || !Utilidades.esLongitudTexto(jTxtFldCcbt.getText(),5))
        {
            numCarne=false;
            txtEmpInst += "      - Nº del CARNÉ: Compruebe que su 1ª casilla tenga 2 dígitos y su 2ª casilla 5 dígitos.\n";
        }
        if(jTxtFldNIFInstalador.getText().equals(""))
        {
            nif=false;
            txtEmpInst += "      - DNI: DNI no debe de estar vacío.\n";
        }
        if(!Utilidades.esDniNifValido(jTxtFldNIFInstalador.getText()))
        {
            nif=false;
            txtEmpInst += "      - DNI: DNI no contiene un valor válido.\n";
        }
        
        if(jTxtFldTelefonoEmp.getText().equals(""))
        {
            tfno=false;
            txtEmpInst += "      - TELÉFONO: TELÉFONO no debe de estar vacío.\n";
        }
        else if(!Utilidades.esLongitudTexto(jTxtFldTelefonoEmp.getText(), 9))
        {
            tfno=false;
            txtEmpInst += "      - TELÉFONO si está rellenado, que contenga 9 dígitos.\n";
        }
        if(jTxtFldEmailEmp.getText().equals(""))
        {
            email=false;
            txtEmpInst += "      - E-MAIL: E-MAIL no debe de estar vacío.\n";
        }
        else if(!Utilidades.esCorrectoEmail(jTxtFldEmailEmp.getText()))
        {
             email=false;
             txtEmpInst += "      - E-MAIL: E-MAIL no tiene el formato correcto: xxx@yy.com\n";
        }
    
        if(nombreEmp && numEmpresa && nombreInst && numCarne && tfno && email && nif)
                return true;
        
        return false;
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxtFldNombreEmpresa = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTxtFldNombreInst = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTxtFldNumEmpresa = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jTxtFldNumCarne = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTxtFldTelefonoEmp = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jTxtFldEibt = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jTxtFldCcbt = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTxtFldEmailEmp = new javax.swing.JTextField();
        jCmbBxModalidadEmp = new javax.swing.JComboBox();
        jCmbBxCatInst = new javax.swing.JComboBox();
        jTxtFldNIFInstalador = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jBttnAceptar = new javax.swing.JButton();
        jBttnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Formulario de alta de nueva Empresa/Instalador");
        setResizable(false);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel1.setText("APELLIDOS Y NOMBRE O RAZON SOCIAL *");

        jTxtFldNombreEmpresa.setAutoscrolls(false);

        jLabel24.setText("NOMBRE DEL INSTALADOR *");

        jTxtFldNombreInst.setAutoscrolls(false);

        jLabel26.setText("CATEGOR\u00cdA INSTALADOR *");

        jLabel27.setText("MODALIDAD *");

        jLabel23.setText("N\u00ba de EMPRESA *");

        jTxtFldNumEmpresa.setAutoscrolls(false);
        jTxtFldNumEmpresa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtFldNumEmpresaFocusLost(evt);
            }
        });

        jLabel25.setText("N\u00ba del CARN\u00c9 *");

        jTxtFldNumCarne.setAutoscrolls(false);
        jTxtFldNumCarne.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtFldNumCarneFocusLost(evt);
            }
        });

        jLabel28.setText("TELEFONO *");

        jTxtFldTelefonoEmp.setAutoscrolls(false);

        jLabel39.setText("/ EIBT -");

        jTxtFldEibt.setAutoscrolls(false);
        jTxtFldEibt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtFldEibtFocusLost(evt);
            }
        });

        jLabel40.setText("/ CCBT -");

        jTxtFldCcbt.setAutoscrolls(false);
        jTxtFldCcbt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtFldCcbtFocusLost(evt);
            }
        });

        jLabel29.setText("E-MAIL *");

        jTxtFldEmailEmp.setAutoscrolls(false);

        jCmbBxModalidadEmp.setEditable(true);

        jCmbBxCatInst.setEditable(true);
        jCmbBxCatInst.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbBxCatInstItemStateChanged1(evt);
            }
        });

        jTxtFldNIFInstalador.setAutoscrolls(false);

        jLabel30.setText("DNI *");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jLabel24)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jTxtFldNombreInst, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jTxtFldNombreEmpresa, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel26)
                                    .add(jCmbBxCatInst, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(20, 20, 20)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jCmbBxModalidadEmp, 0, 148, Short.MAX_VALUE)
                                    .add(jLabel27))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jTxtFldTelefonoEmp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel28))))
                        .add(8, 8, 8)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(3, 3, 3)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel25)
                                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                                                .add(jTxtFldNumEmpresa, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(jLabel39)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(jTxtFldEibt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 53, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                            .add(jPanel1Layout.createSequentialGroup()
                                                .add(jTxtFldNumCarne)
                                                .add(8, 8, 8)
                                                .add(jLabel40)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(jTxtFldCcbt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                                    .add(jLabel23))
                                .add(33, 33, 33)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jTxtFldNIFInstalador, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 108, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel30))
                                .add(44, 44, 44))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(6, 6, 6)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jTxtFldEmailEmp, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                                    .add(jPanel1Layout.createSequentialGroup()
                                        .add(jLabel29)
                                        .addContainerGap(277, Short.MAX_VALUE))))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(19, 19, 19)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldNombreEmpresa, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(45, 45, 45)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jLabel24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTxtFldNombreInst, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jLabel30)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTxtFldNIFInstalador, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel23)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jTxtFldNumEmpresa, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel39)
                            .add(jTxtFldEibt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(45, 45, 45)
                        .add(jLabel25)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jTxtFldCcbt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel40)
                            .add(jTxtFldNumCarne, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .add(49, 49, 49)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel28)
                            .add(jLabel26)
                            .add(jLabel27))
                        .add(6, 6, 6)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jTxtFldTelefonoEmp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jCmbBxCatInst, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jCmbBxModalidadEmp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel29)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldEmailEmp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBttnAceptar.setText("ACEPTAR");
        jBttnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBttnAceptarActionPerformed(evt);
            }
        });

        jBttnCancelar.setText("CANCELAR");
        jBttnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBttnCancelarActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(jBttnAceptar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(8, 8, 8)
                        .add(jBttnCancelar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {jBttnAceptar, jBttnCancelar}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jBttnAceptar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jBttnCancelar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtFldCcbtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtFldCcbtFocusLost
        jTxtFldCcbt.setText(Utilidades.rellenarCeros(jTxtFldCcbt.getText(), 5));
    }//GEN-LAST:event_jTxtFldCcbtFocusLost

    private void jTxtFldNumCarneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtFldNumCarneFocusLost
        jTxtFldNumCarne.setText(Utilidades.rellenarCeros(jTxtFldNumCarne.getText(), 2));
    }//GEN-LAST:event_jTxtFldNumCarneFocusLost

    private void jTxtFldEibtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtFldEibtFocusLost
        jTxtFldEibt.setText(Utilidades.rellenarCeros(jTxtFldEibt.getText(), 5));
    }//GEN-LAST:event_jTxtFldEibtFocusLost

    private void jTxtFldNumEmpresaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtFldNumEmpresaFocusLost
        jTxtFldNumEmpresa.setText(Utilidades.rellenarCeros(jTxtFldNumEmpresa.getText(), 2));
    }//GEN-LAST:event_jTxtFldNumEmpresaFocusLost

    private void jCmbBxCatInstItemStateChanged1(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbBxCatInstItemStateChanged1
        ParCombo pc = (ParCombo)jCmbBxCatInst.getSelectedItem();
        String idCat = pc.getKeyString();
        try{
            if(idCat.equalsIgnoreCase("B")){
                this.jCmbBxModalidadEmp.setSelectedIndex(0);
                this.jCmbBxModalidadEmp.setEnabled(false);
            }
            else{
                this.jCmbBxModalidadEmp.setEnabled(true);
                this.jCmbBxModalidadEmp.setSelectedIndex(1);
            }
        }
        catch(Exception e){
            this.jCmbBxModalidadEmp.setEnabled(false);
        }
    }//GEN-LAST:event_jCmbBxCatInstItemStateChanged1

    private void jBttnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBttnCancelarActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jBttnCancelarActionPerformed

    private void jBttnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBttnAceptarActionPerformed
        String txtError = "";
        
        if(esCorrectoEmpInst())
        {
            if(esActualizar)
                this.actualizarBD();
            else
                this.introducirBD();
            
            insertarValoresFrmGestion();
            frmGestion.setVisible(true);
            this.setVisible(false);
            this.dispose();
        }
        else
            Mensaje.error(txtEmpInst);
    }//GEN-LAST:event_jBttnAceptarActionPerformed

    //Introducir los datos en la base de datos
    private void introducirBD() {
        ResultSet rs = null;
        nombreEmp = jTxtFldNombreEmpresa.getText();
        numEmpresa = jTxtFldNumEmpresa.getText();
        eibt = jTxtFldEibt.getText();
        nombreInst = jTxtFldNombreInst.getText();
        numCarne = jTxtFldNumCarne.getText();
        ccbt = jTxtFldCcbt.getText();
        tfno = jTxtFldTelefonoEmp.getText();
        email = jTxtFldEmailEmp.getText();
        pcCat = (ParCombo)jCmbBxCatInst.getSelectedItem();
        pcMod = (ParCombo)jCmbBxModalidadEmp.getSelectedItem();
        nif = jTxtFldNIFInstalador.getText();

        BaseDatos bd = Sesion.getInstance().getBaseDatos();
        String consulta ="INSERT INTO INSTALADORES (ITNOMBREEMP, ITNUMEMP1, ITNUMEMP2, ITINSTALADOR, ITCARNET1, ITCARNET2, ITCATEGORIA, ITMODALIDAD, ITTELEFONO, ITEMAIL, ITNIF)" +
                " VALUES('"+nombreEmp+"','"+numEmpresa+"','"+eibt+"','"+nombreInst+"','"+numCarne+"','"+ccbt+"','"+pcCat.getKeyString()+"',"+pcMod.getKeyString()+",'"+tfno+"','"+email+"','"+nif+"')";
        
        try {
            bd.ejecModificacion(consulta);
            consulta = "SELECT * FROM INSTALADORES";
            rs = bd.getUltimoResgistro(consulta);
            
            //Guardar en la sesión la ID de INSTALADORES
            Sesion.getInstance().setValorHt(Constantes.SES_INSTALADORES_ID, rs.getString(Constantes.SES_INSTALADORES_ID));   
        } 
        catch (SQLException e) {
            Mensaje.error("DlgNuevoEmpInst.java. "+e.getMessage(),e);
        } 
    }

    //Inserta los valores en Empresa/instalador del formulario FrmGestionPnl.
    private void insertarValoresFrmGestion() {
        frmGestion.jTxtFldNombreEmpresa.setText(nombreEmp);
        frmGestion.jTxtFldNumEmpresa.setText(numEmpresa);
        frmGestion.jTxtFldEibt.setText(eibt);
        frmGestion.jTxtFldNombreInst.setText(nombreInst);
        frmGestion.jTxtFldCcbt.setText(ccbt);
        frmGestion.jTxtFldNumCarne.setText(numCarne);
        frmGestion.jTxtFldTelefonoEmp.setText(tfno);
        frmGestion.jTxtFldEmailEmp.setText(email);
        frmGestion.jTxtFldNIFInstalador.setText(nif);
        
        ParCombo pcTemp;

        for(int i=0; i<frmGestion.jCmbBxModalidadEmp.getItemCount(); i++)
        {
                pcTemp = (ParCombo)frmGestion.jCmbBxModalidadEmp.getItemAt(i);
                if(pcTemp.getKeyString().equals(pcMod.getKeyString()))
                    frmGestion.jCmbBxModalidadEmp.setSelectedItem(pcTemp);
        }
        
        for(int i=0; i<frmGestion.jCmbBxCatInst.getItemCount(); i++)
        {
                pcTemp = (ParCombo)frmGestion.jCmbBxCatInst.getItemAt(i);
                if(pcTemp.getKeyString().equals(pcCat.getKeyString()))
                    frmGestion.jCmbBxCatInst.setSelectedItem(pcTemp);
        }        
    }

    private void agregarDatosCombox() {
         ResultSet rs = null;
        
        try{  
            rs = bd.ejecSelect("SELECT * FROM CAT_INSTALADOR ORDER BY CIDESC");
            while(rs.next())
                jCmbBxCatInst.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            rs = bd.ejecSelect("SELECT * FROM MODALIDAD_INST ORDER BY MIDESC");
            while(rs.next())
                jCmbBxModalidadEmp.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
        }
        catch(SQLException e){
            Mensaje.error("DlgNuevoEmpInst.java: "+e.getMessage(),e);
        }
         
        new AutoComplCmbBxRestrictivo(jCmbBxCatInst);
        new AutoComplCmbBxRestrictivo(jCmbBxModalidadEmp);
    }

    private void establecerRestricciones() {
        jTxtFldNombreEmpresa.setDocument(new LimitadorCaracteres(jTxtFldNombreEmpresa, 44, false, false));
        jTxtFldNumEmpresa.setDocument(new LimitadorCaracteres(jTxtFldNumEmpresa, 2, true, false));
        jTxtFldEibt.setDocument(new LimitadorCaracteres(jTxtFldEibt, 5, false, false));
        jTxtFldNombreInst.setDocument(new LimitadorCaracteres(jTxtFldNombreInst, 50, false, false));
        jTxtFldNumCarne.setDocument(new LimitadorCaracteres(jTxtFldNumCarne, 2, true, false));
        jTxtFldCcbt.setDocument(new LimitadorCaracteres(jTxtFldCcbt, 5, false, false));
        jTxtFldTelefonoEmp.setDocument(new LimitadorCaracteres(jTxtFldTelefonoEmp, 9, true, false));
        jTxtFldEmailEmp.setDocument(new LimitadorCaracteres(jTxtFldEmailEmp, 100, false, false));
        jTxtFldNIFInstalador.setDocument(new LimitadorCaracteres(jTxtFldNIFInstalador, 9, false, false));
    }

    public void setEsActualizar(boolean esActualizar) {
        this.esActualizar = esActualizar;
    }

    private void actualizarBD() {
        ResultSet rs = null;
        nombreEmp = jTxtFldNombreEmpresa.getText();
        numEmpresa = jTxtFldNumEmpresa.getText();
        eibt = jTxtFldEibt.getText();
        nombreInst = jTxtFldNombreInst.getText();
        numCarne = jTxtFldNumCarne.getText();
        ccbt = jTxtFldCcbt.getText();
        tfno = jTxtFldTelefonoEmp.getText();
        email = jTxtFldEmailEmp.getText();
        nif = jTxtFldNIFInstalador.getText();
        pcCat = (ParCombo)jCmbBxCatInst.getSelectedItem();
        pcMod = (ParCombo)jCmbBxModalidadEmp.getSelectedItem();

        BaseDatos bd = Sesion.getInstance().getBaseDatos();
        String consulta ="UPDATE INSTALADORES SET ITNOMBREEMP='"+nombreEmp+"', " +
                " ITNUMEMP1='"+numEmpresa+"',ITNUMEMP2='"+eibt+"', ITINSTALADOR='"+nombreInst+"', " +
                " ITCARNET1='"+numCarne+"',ITCARNET2='"+ccbt+"',ITCATEGORIA='"+pcCat.getKeyString()+"'," +
                " ITMODALIDAD="+pcMod.getKeyString()+",ITTELEFONO='"+tfno+"', ITEMAIL='"+email+"', ITNIF='"+ nif +"' "+
                " WHERE ITID="+Sesion.getInstance().getValorHt(Constantes.SES_INSTALADORES_ID).toString();
        
        try {
            bd.ejecModificacion(consulta);
        } 
        catch (SQLException e) {
            Mensaje.error("DlgNuevoEmpInst.java. "+e.getMessage());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBttnAceptar;
    private javax.swing.JButton jBttnCancelar;
    public javax.swing.JComboBox jCmbBxCatInst;
    public javax.swing.JComboBox jCmbBxModalidadEmp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JTextField jTxtFldCcbt;
    public javax.swing.JTextField jTxtFldEibt;
    public javax.swing.JTextField jTxtFldEmailEmp;
    public javax.swing.JTextField jTxtFldNIFInstalador;
    public javax.swing.JTextField jTxtFldNombreEmpresa;
    public javax.swing.JTextField jTxtFldNombreInst;
    public javax.swing.JTextField jTxtFldNumCarne;
    public javax.swing.JTextField jTxtFldNumEmpresa;
    public javax.swing.JTextField jTxtFldTelefonoEmp;
    // End of variables declaration//GEN-END:variables
    
}
