/*
 * InputCT.java
 *
 * Created on 1 de junio de 2006, 17:05
 */

package graph.input;

import funciones.LimitadorCaracteres;
import graph.beans.CTBean;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javax.swing.CellEditor;
import main.Mensaje;

/**
 *
 * @author  sanjose
 */
public class InputCT extends javax.swing.JPanel {
    private static CellEditor cellEditor;
    private static ResourceBundle bundle = ResourceBundle.getBundle("resources/config/config");
    private CTBean datosCT;
    private String mensaje = null;
    
    /** Creates new form InputCT */
    public InputCT(CellEditor cellEditor) {
        this.cellEditor = cellEditor;
        initComponents();
        establecerRestricciones();
    }
    
    public void installValue(CTBean valor) {
        datosCT = valor;
        
        if(datosCT != null) {
            jTextFieldReferencia.setText(String.valueOf(datosCT.getReferencia()));
            jComboBoxTension.setSelectedItem(DecimalFormat.getInstance().format(datosCT.getTension()));
            jComboBoxNumPolos.setSelectedItem(DecimalFormat.getInstance().format(datosCT.getNumeroPolos()));
            jTextFieldCalibre.setText(DecimalFormat.getInstance().format(datosCT.getCalibre()));
        }
    }
    
    public CTBean getValue() {
        return datosCT;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jBtnAceptar = new javax.swing.JButton();
        jLabelTension = new javax.swing.JLabel();
        jComboBoxTension = new javax.swing.JComboBox();
        jLabelNumeroPolos = new javax.swing.JLabel();
        jLabelCalibre = new javax.swing.JLabel();
        jLabelSensibilidadUnidad1 = new javax.swing.JLabel();
        jLabelNumeroPolosUnidad = new javax.swing.JLabel();
        jLabelCalibreUnidad = new javax.swing.JLabel();
        jLabelReferencia = new javax.swing.JLabel();
        jTextFieldReferencia = new javax.swing.JTextField();
        jTextFieldCalibre = new javax.swing.JTextField();
        jComboBoxNumPolos = new javax.swing.JComboBox();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jBtnAceptar.setText("Aceptar");
        jBtnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAceptarActionPerformed(evt);
            }
        });

        jLabelTension.setText("Tensi\u00f3n");

        jComboBoxTension.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "230", "400" }));
        jComboBoxTension.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTensionItemStateChanged(evt);
            }
        });

        jLabelNumeroPolos.setText("N\u00ba Polos");

        jLabelCalibre.setText("Calibre");

        jLabelSensibilidadUnidad1.setText("V");

        jLabelNumeroPolosUnidad.setText("P");

        jLabelCalibreUnidad.setText("A");

        jLabelReferencia.setText("Referencia");

        jComboBoxNumPolos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));
        jComboBoxNumPolos.setSelectedIndex(1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabelReferencia)
                            .add(jLabelTension)
                            .add(jLabelNumeroPolos)
                            .add(jLabelCalibre))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(jComboBoxTension, 0, 74, Short.MAX_VALUE)
                                    .add(jTextFieldCalibre, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                    .add(jComboBoxNumPolos, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabelCalibreUnidad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabelNumeroPolosUnidad)
                                    .add(jLabelSensibilidadUnidad1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)))
                            .add(jTextFieldReferencia, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jBtnAceptar))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabelReferencia)
                            .add(jTextFieldReferencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(layout.createSequentialGroup()
                        .add(36, 36, 36)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabelTension)
                            .add(jLabelSensibilidadUnidad1)
                            .add(jComboBoxTension, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelNumeroPolos)
                    .add(jLabelNumeroPolosUnidad)
                    .add(jComboBoxNumPolos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelCalibre)
                    .add(jLabelCalibreUnidad)
                    .add(jTextFieldCalibre, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jBtnAceptar)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
// TODO add your handling code here:
    }//GEN-LAST:event_formMousePressed

    private void jComboBoxTensionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTensionItemStateChanged
        String tension = (String)jComboBoxTension.getSelectedItem();
        if("230".equals(tension)){
            jComboBoxNumPolos.setSelectedItem("2");
        } else if("400".equals(tension)){
            jComboBoxNumPolos.setSelectedItem("4");
        } else {
            jComboBoxNumPolos.setSelectedItem("");
        }
    }//GEN-LAST:event_jComboBoxTensionItemStateChanged
    
    private void jBtnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAceptarActionPerformed
        if(valida()){
            String referencia = (String)jTextFieldReferencia.getText();
            double tension = 0;
            double numeroPolos = 0;
            double calibre = 0;
            try {
                tension = Double.parseDouble(((String)jComboBoxTension.getSelectedItem()).replace(",","."));
                numeroPolos = Double.parseDouble(((String)jComboBoxNumPolos.getSelectedItem()).replace(",","."));
                calibre = Double.parseDouble((jTextFieldCalibre.getText()).replace(",","."));
            } catch(NumberFormatException e) {}
            datosCT.setReferencia(referencia);
            datosCT.setTension(tension);
            datosCT.setNumeroPolos(numeroPolos);
            datosCT.setCalibre(calibre);
            datosCT.setEditado(true);
            cellEditor.stopCellEditing();
        }
    }//GEN-LAST:event_jBtnAceptarActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAceptar;
    private javax.swing.JComboBox jComboBoxNumPolos;
    private javax.swing.JComboBox jComboBoxTension;
    private javax.swing.JLabel jLabelCalibre;
    private javax.swing.JLabel jLabelCalibreUnidad;
    private javax.swing.JLabel jLabelNumeroPolos;
    private javax.swing.JLabel jLabelNumeroPolosUnidad;
    private javax.swing.JLabel jLabelReferencia;
    private javax.swing.JLabel jLabelSensibilidadUnidad1;
    private javax.swing.JLabel jLabelTension;
    private javax.swing.JTextField jTextFieldCalibre;
    private javax.swing.JTextField jTextFieldReferencia;
    // End of variables declaration//GEN-END:variables
    
    private void establecerRestricciones() {
        jTextFieldReferencia.setDocument(new LimitadorCaracteres(jTextFieldReferencia, 25, false, false));
    }

    private boolean valida(){
        boolean resultado = true;
        this.mensaje=null;
        resultado = resultado && validaReferencia();
        resultado = resultado && validaTension();
        resultado = resultado && validaCalibre();
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
       
    private boolean validaTension(){
        boolean resultado = true;
        String tension = (String)jComboBoxTension.getSelectedItem();
        if(tension==null || "".equals(tension)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO");
            mensaje+= " "+jLabelTension.getText()+" ";
            mensaje+= bundle.getString("ES_OBLIGATORIO");
            mensaje+= "\n";
            resultado = false;
        }
        return resultado;
    }
    
    private boolean validaCalibre(){
        boolean resultado = true;
        String calibre = jTextFieldCalibre.getText();
        if(calibre==null || "".equals(calibre)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO");
            mensaje+= " "+jLabelCalibre.getText()+" ";
            mensaje+= bundle.getString("ES_OBLIGATORIO");
            mensaje+= "\n";
            resultado = false;
        }
        return resultado;
    }
   
}
