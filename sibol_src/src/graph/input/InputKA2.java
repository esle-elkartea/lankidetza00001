package graph.input;

import funciones.LimitadorCaracteres;
import graph.beans.KABean;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.swing.CellEditor;
import main.Mensaje;

/**
 *
 * @author  sanjose
 */
public class InputKA2 extends javax.swing.JPanel {
    private static CellEditor cellEditor;
    private static ResourceBundle bundle = ResourceBundle.getBundle("resources/config/config");
    private KABean datosKA;
    private String mensaje = null;
    
    /** Creates new form InputKA */
    public InputKA2(CellEditor cellEditor) {
        this.cellEditor = cellEditor;
        initComponents();
        establecerRestricciones();
    }
    
    public void installValue(KABean valor) {
        datosKA = valor;
        
        if(datosKA != null) {
            jTextFieldReferencia.setText(String.valueOf(datosKA.getReferencia()));
            jTextFieldTiempo1.setText(DecimalFormat.getInstance().format(datosKA.getTiempo1()));
            jTextFieldTiempo2.setText(DecimalFormat.getInstance().format(datosKA.getTiempo2()));
            jComboBoxUnidades.setSelectedItem(datosKA.getUnidades());
        }
    }
    
    public KABean getValue() {
        return datosKA;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jBtnAceptar = new javax.swing.JButton();
        jTextFieldReferencia = new javax.swing.JTextField();
        jLabelReferencia = new javax.swing.JLabel();
        jTextFieldTiempo1 = new javax.swing.JTextField();
        jTextFieldTiempo2 = new javax.swing.JTextField();
        jLabelTiempo1 = new javax.swing.JLabel();
        jLabelTiempo2 = new javax.swing.JLabel();
        jComboBoxUnidades = new javax.swing.JComboBox();
        jLabelUnidades = new javax.swing.JLabel();

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

        jLabelReferencia.setText("Referencia");

        jLabelTiempo1.setText("Tiempo 1");

        jLabelTiempo2.setText("Tiempo 2");

        jComboBoxUnidades.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Segundos", "Minutos", "Horas" }));

        jLabelUnidades.setText("Unidades");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabelUnidades)
                            .add(jLabelReferencia)
                            .add(jLabelTiempo2)
                            .add(jLabelTiempo1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jTextFieldReferencia, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jTextFieldTiempo2)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jTextFieldTiempo1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jComboBoxUnidades, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .add(jLabelTiempo1)
                    .add(jTextFieldTiempo1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelTiempo2)
                    .add(jTextFieldTiempo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jComboBoxUnidades, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelUnidades))
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
            double tiempo1 = 0;
            double tiempo2 = 0;
            try {
                tiempo1 = Double.parseDouble(jTextFieldTiempo1.getText().replace(",","."));
                tiempo2 = Double.parseDouble(jTextFieldTiempo2.getText().replace(",","."));
            } catch(NumberFormatException e) {}
            datosKA.setReferencia(jTextFieldReferencia.getText());
            datosKA.setTiempo1(tiempo1);
            datosKA.setTiempo2(tiempo2);
            datosKA.setUnidades((String)jComboBoxUnidades.getSelectedItem());
            datosKA.setEditado(true);
            cellEditor.stopCellEditing();
        }
    }//GEN-LAST:event_jBtnAceptarActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAceptar;
    private javax.swing.JComboBox jComboBoxUnidades;
    private javax.swing.JLabel jLabelReferencia;
    private javax.swing.JLabel jLabelTiempo1;
    private javax.swing.JLabel jLabelTiempo2;
    private javax.swing.JLabel jLabelUnidades;
    private javax.swing.JTextField jTextFieldReferencia;
    private javax.swing.JTextField jTextFieldTiempo1;
    private javax.swing.JTextField jTextFieldTiempo2;
    // End of variables declaration//GEN-END:variables
    
    private void establecerRestricciones() {
        jTextFieldReferencia.setDocument(new LimitadorCaracteres(jTextFieldReferencia, 25, false, false));
        jTextFieldTiempo1.setDocument(new LimitadorCaracteres(jTextFieldTiempo1, 6, true, false));
        jTextFieldTiempo2.setDocument(new LimitadorCaracteres(jTextFieldTiempo2, 6, true, false));
    }
        
    private boolean valida(){
        boolean resultado = true;
        this.mensaje=null;
        resultado = resultado && validaReferencia();
        resultado = resultado && validaTiempo1();
        resultado = resultado && validaTiempo2();
        resultado = resultado && validaUnidades();
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
    
    private boolean validaTiempo1(){
        boolean resultado = true;
        String valor = jTextFieldTiempo1.getText();
        String label = jLabelTiempo1.getText();
        if(valor==null || "".equals(valor)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        } else {
            if(!Pattern.matches("[0-9]{1,6}", valor)){
                // Mensaje campo con formato incorrecto
                if(mensaje==null)mensaje="";
                mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("FORMATO_INCORRECTO")+"\n";
                resultado=false;
            }
        }
        return resultado;
    }
    
    private boolean validaTiempo2(){
        boolean resultado = true;
        String valor = jTextFieldTiempo2.getText();
        String label = jLabelTiempo2.getText();
        if(valor==null || "".equals(valor)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        } else {
            if(!Pattern.matches("[0-9]{1,6}", valor)){
                // Mensaje campo con formato incorrecto
                if(mensaje==null)mensaje="";
                mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("FORMATO_INCORRECTO")+"\n";
                resultado=false;
            }
        }
        return resultado;
    }
    
    private boolean validaUnidades(){
        boolean resultado = true;
        String valor = (String)jComboBoxUnidades.getSelectedItem();
        String label = jLabelUnidades.getText();
        if(valor==null || "".equals(valor)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }
    
}