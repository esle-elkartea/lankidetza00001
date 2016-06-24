/*
 * InputRD.java
 *
 * Created on 1 de junio de 2006, 17:05
 */

package graph.input;

import funciones.LimitadorCaracteres;
import graph.beans.RDBean;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.swing.CellEditor;
import main.Mensaje;

/**
 *
 * @author  sanjose
 */
public class InputRD extends javax.swing.JPanel {
    private static CellEditor cellEditor;
    private static ResourceBundle bundle = ResourceBundle.getBundle("resources/config/config");
    private RDBean datosRD;
    private String mensaje = null;
    
    /** Creates new form InputRD */
    public InputRD(CellEditor cellEditor) {
        this.cellEditor = cellEditor;
        initComponents();
        establecerRestricciones();
    }
    
    public void installValue(RDBean valor) {
        datosRD = valor;
        
        if(datosRD != null) {
            jTextFieldReferencia.setText(datosRD.getReferencia());
            jTxtSensibilidad.setText(DecimalFormat.getInstance().format(datosRD.getSensibilidad()));
            jTxtReguladorTiempo1.setText(datosRD.getReguladorTiempo1());
            jTxtReguladorTiempo2.setText(datosRD.getReguladorTiempo2());
        }
    }
    
    public RDBean getValue() {
        return datosRD;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabelSensibilidad = new javax.swing.JLabel();
        jTxtSensibilidad = new javax.swing.JTextField();
        jBtnAceptar = new javax.swing.JButton();
        jLabelReferencia = new javax.swing.JLabel();
        jTextFieldReferencia = new javax.swing.JTextField();
        jLabelTipoUnidad = new javax.swing.JLabel();
        jLabelReguladorTiempo1 = new javax.swing.JLabel();
        jTxtReguladorTiempo1 = new javax.swing.JTextField();
        jLabelReguladorTiempo2 = new javax.swing.JLabel();
        jTxtReguladorTiempo2 = new javax.swing.JTextField();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jLabelSensibilidad.setText("Sensibilidad");

        jBtnAceptar.setText("Aceptar");
        jBtnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAceptarActionPerformed(evt);
            }
        });

        jLabelReferencia.setText("Referencia");

        jLabelTipoUnidad.setText("mA");

        jLabelReguladorTiempo1.setText("Regulador de Tiempo 1");

        jLabelReguladorTiempo2.setText("Regulador de Tiempo 2");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabelReguladorTiempo2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtReguladorTiempo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabelReferencia)
                            .add(jLabelSensibilidad)
                            .add(jLabelReguladorTiempo1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jTxtSensibilidad)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jTxtReguladorTiempo1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabelTipoUnidad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jTextFieldReferencia, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jBtnAceptar))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextFieldReferencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelReferencia))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtSensibilidad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelTipoUnidad)
                    .add(jLabelSensibilidad))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelReguladorTiempo1)
                    .add(jTxtReguladorTiempo1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelReguladorTiempo2)
                    .add(jTxtReguladorTiempo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jBtnAceptar)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
// TODO add your handling code here:
    }//GEN-LAST:event_formMousePressed
    
    private void jBtnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAceptarActionPerformed
        if(valida()){
            String referencia = jTextFieldReferencia.getText();
            double sensibilidad = 0;
            String reguladorTiempo1 = "";
            String reguladorTiempo2 = "";
            try {
                sensibilidad = Double.parseDouble(jTxtSensibilidad.getText().replace(",","."));
                reguladorTiempo1 = jTxtReguladorTiempo1.getText();
                reguladorTiempo2 = jTxtReguladorTiempo2.getText();
            } catch(NumberFormatException e) {}
            datosRD.setReferencia(referencia);
            datosRD.setSensibilidad(sensibilidad);
            datosRD.setReguladorTiempo1(reguladorTiempo1);
            datosRD.setReguladorTiempo2(reguladorTiempo2);
            datosRD.setEditado(true);
            cellEditor.stopCellEditing();
        }
    }//GEN-LAST:event_jBtnAceptarActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAceptar;
    private javax.swing.JLabel jLabelReferencia;
    private javax.swing.JLabel jLabelReguladorTiempo1;
    private javax.swing.JLabel jLabelReguladorTiempo2;
    private javax.swing.JLabel jLabelSensibilidad;
    private javax.swing.JLabel jLabelTipoUnidad;
    private javax.swing.JTextField jTextFieldReferencia;
    private javax.swing.JTextField jTxtReguladorTiempo1;
    private javax.swing.JTextField jTxtReguladorTiempo2;
    private javax.swing.JTextField jTxtSensibilidad;
    // End of variables declaration//GEN-END:variables

    private void establecerRestricciones() {
        jTextFieldReferencia.setDocument(new LimitadorCaracteres(jTextFieldReferencia, 25, false, false));
        jTxtSensibilidad.setDocument(new LimitadorCaracteres(jTxtSensibilidad, 8, true, true));
    }
        
    private boolean valida(){
        boolean resultado = true;
        this.mensaje=null;
        resultado = resultado && validaReferencia();
        resultado = resultado && validaSensibilidad();
        if(!resultado){
            // Mostrar mensaje
            Mensaje.error(bundle.getString("ERRORES_EN_DATOS_INTRODUCIDOS")+this.mensaje+"\n");
        }
        return resultado;
    }
       
    private boolean validaReferencia(){
        boolean resultado = true;
        String valor = jTextFieldReferencia.getText();
        String label = jLabelReferencia.getText();
        if(valor==null || "".equals(valor)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }
       
    private boolean validaSensibilidad(){
        boolean resultado = true;
        String sensibilidad = (String)jTxtSensibilidad.getText();
        if(sensibilidad==null || "".equals(sensibilidad)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO");
            mensaje+= " "+jLabelSensibilidad.getText()+" ";
            mensaje+= bundle.getString("ES_OBLIGATORIO");
            mensaje+= "\n";
            resultado = false;
        } else {
            if(!Pattern.matches("(([0]{0,3}[1-9][0-9])|([0]{0,2}[1-9][0-9]{2})|([0]{0,1}[1-9][0-9]{3})|([1-9][0-9]{4}))(,[0-9][0-9]?)?", sensibilidad)){
                // Mensaje campo con formato incorrecto
                if(mensaje==null)mensaje="";
                mensaje+= bundle.getString("EL_CAMPO");
                mensaje+= " "+jLabelSensibilidad.getText()+" ";
                mensaje+= bundle.getString("FORMATO_INCORRECTO");
                mensaje+= "\n";
                resultado=false;
            }
        }
        return resultado;
    }
   
}
