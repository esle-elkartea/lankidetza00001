package graph.input;

import funciones.BaseDatos;
import funciones.LimitadorCaracteres;
import funciones.ParCombo;
import funciones.Sesion;
import funciones.Utilidades;
import funciones.UtilidadesSQL;
import graph.beans.EdgeBean;
import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.swing.CellEditor;
import javax.swing.DefaultComboBoxModel;
import main.Constantes;
import main.Mensaje;

/**
 *
 * @author  sanjose
 */
public class InputEdge extends javax.swing.JPanel {
    private static CellEditor cellEditor;
    private static ResourceBundle bundle = ResourceBundle.getBundle("resources/config/config");
    private EdgeBean datosEdge;
    private String mensaje = null;
    private int tipoInstalacionDISeleccionada = -1;
    private int tipoInstalacionLGASeleccionada = -1;
    private DefaultComboBoxModel m1, m2, m3, mTemp;
    
    /** Creates new form InputEdge */
    public InputEdge(CellEditor cellEditor) {
        this.cellEditor = cellEditor;
        initComponents();
        establecerRestricciones();
        cargarOpcionesDesdeBD();
    }
    
    public void installValue(EdgeBean valor) {
        datosEdge = valor;
        
        if(datosEdge != null) {
            DecimalFormat formateador = (DecimalFormat) DecimalFormat.getInstance();
            formateador.setGroupingUsed(false);
            if("Otro".equals(datosEdge.getTipo())){
                jRadioButtonTipoOtro.setSelected(false);
                jRadioButtonTipoOtro.setSelected(true);
                
                jTextFieldOtroDescripcion.setText(String.valueOf(datosEdge.getOtroDescripcion()));
                
                jComboBoxLGATipo.setSelectedItem(null);
                jTextFieldLGASeccion.setText("");
                jComboBoxLGAMaterial.setSelectedIndex(-1);
                //jTextFieldLGAAislamiento.setText("");
                //jTextFieldLGATensionAislamiento.setText("");
                jTextFieldLGALongitud.setText("");
                jComboBoxLGATension.setSelectedItem(null);
                jComboBoxLGATipoInstalacion.setSelectedItem(null);
                jComboBoxLGATipoCable.setSelectedItem(null);
                jComboBoxLGAProteccion.setSelectedItem(null);
                
                jComboBoxDITipo.setSelectedItem(null);
                jTextFieldDISeccion.setText("");
                jComboBoxDIMaterial.setSelectedIndex(-1);
                jComboBoxDIAislamiento.setSelectedItem(null);
                jComboBoxDITensionAislamiento.setSelectedIndex(-1);
                jTextFieldDILongitud.setText("");
                jComboBoxDITipoInstalacion.setSelectedItem(null);
                jComboBoxDITipoCable.setSelectedItem(null);
                jComboBoxDIProteccion.setSelectedItem(null);
                jTextFieldDITension1.setText("");
                jTextFieldDITension2.setText("");
                jComboBoxDIPotencia.setSelectedItem(null);
                jTextFieldDIDescripcion.setText("");
                jComboBoxDITipo2.setSelectedItem(null);
            } else if("LA".equals(datosEdge.getTipo())){
                jRadioButtonTipoLGA.setSelected(false);
                jRadioButtonTipoLGA.setSelected(true);
                
                jTextFieldOtroDescripcion.setText("");
                
                jComboBoxDITipo.setSelectedItem(null);
                jTextFieldDISeccion.setText("");
                jComboBoxDIMaterial.setSelectedIndex(-1);
                jComboBoxDIAislamiento.setSelectedItem(null);
                jComboBoxDITensionAislamiento.setSelectedItem(null);
                jTextFieldDILongitud.setText("");
                jComboBoxDITipoInstalacion.setSelectedItem(null);
                jComboBoxDITipoCable.setSelectedItem(null);
                jComboBoxDIProteccion.setSelectedItem(null);
                jTextFieldDITension1.setText("");
                jTextFieldDITension2.setText("");
                jComboBoxDIPotencia.setSelectedItem(null);
                
                /*for(int i=0;i<jComboBoxjComboBoxDITipoCable.setSelectedIndex(null);LGAPuntoConexion.getItemCount();i++){
                    ParCombo elemento = (ParCombo)jComboBoxLGAPuntoConexion.getItemAt(i);
                    if(elemento.getDescription().equals(datosEdge.getLgaPuntoConexion())){
                        jComboBoxLGAPuntoConexion.setSelectedIndex(i);
                        break;
                    }
                }*/
                for(int i=0;i<jComboBoxLGATipo.getItemCount();i++){
                    ParCombo elemento = (ParCombo)jComboBoxLGATipo.getItemAt(i);
                    if(elemento.getKeyString().equals(datosEdge.getLgaTipo())){
                        jComboBoxLGATipo.setSelectedIndex(i);
                        break;
                    }
                }
                jTextFieldLGASeccion.setText(formateador.format(datosEdge.getLgaSeccion()));
                for(int i=0;i<jComboBoxLGAMaterial.getItemCount();i++){
                    ParCombo elemento = (ParCombo)jComboBoxLGAMaterial.getItemAt(i);
                    if(elemento.getKeyString().equals(datosEdge.getLgaMaterial())){
                        jComboBoxLGAMaterial.setSelectedIndex(i);
                        break;
                    }
                }
                jTextFieldLGAAislamiento.setText(datosEdge.getLgaAislamiento());
                /*jComboBoxLGAAislamiento.setSelectedItem(datosEdge.getLgaAislamiento());
                for(int i=0;i<jComboBoxLGAAislamiento.getItemCount();i++){
                    String elemento = (String)jComboBoxLGAAislamiento.getItemAt(i);
                    if(elemento.equals(datosEdge.getLgaAislamiento())){
                        jComboBoxLGAAislamiento.setSelectedIndex(i);
                        break;
                    }
                }*/
                jTextFieldLGATensionAislamiento.setText(formateador.format(datosEdge.getLgaTensionAislamiento()));
                //jComboBoxLGATensionAislamiento.setSelectedItem(DecimalFormat.getInstance().format(datosEdge.getLgaTensionAislamiento()));
                jTextFieldLGALongitud.setText(formateador.format(datosEdge.getLgaLongitud()));
                for(int i=0;i<jComboBoxLGATipoInstalacion.getItemCount();i++){
                    ParCombo elemento = (ParCombo)jComboBoxLGATipoInstalacion.getItemAt(i);
                    if(elemento.getKeyString().equals(datosEdge.getLgaTipoInstalacion())){
                        jComboBoxLGATipoInstalacion.setSelectedIndex(i);
                        break;
                    }
                }
                if(datosEdge.getLgaTension()==400){
                    jComboBoxLGATension.setSelectedItem("400");
                } else if(datosEdge.getLgaTension()==230){
                    jComboBoxLGATension.setSelectedItem("230");
                } else {
                    jComboBoxLGATension.setSelectedItem(null);
                }
                
                for(int i=0;i<jComboBoxLGATipoCable.getItemCount();i++){
                    ParCombo elemento = (ParCombo)jComboBoxLGATipoCable.getItemAt(i);
                    if(elemento.getKeyString().equals(datosEdge.getTipoCable())){
                        jComboBoxLGATipoCable.setSelectedIndex(i);
                        break;
                    }
                }
                
                jComboBoxLGAProteccion.setSelectedItem(DecimalFormat.getInstance().format(datosEdge.getProteccion()));
            } else if("DI".equals(datosEdge.getTipo())){
                jRadioButtonTipoDI.setSelected(false);
                jRadioButtonTipoDI.setSelected(true);
                
                jTextFieldOtroDescripcion.setText("");
                
                //jComboBoxLGAPuntoConexion.setSelectedItem(null);
                jComboBoxLGATipo.setSelectedItem(null);
                jTextFieldLGASeccion.setText("");
                jComboBoxLGAMaterial.setSelectedIndex(-1);
                //jTextFieldLGAAislamiento.setText("");
                //jTextFieldLGATensionAislamiento.setText("");
                jTextFieldLGALongitud.setText("");
                jComboBoxLGATension.setSelectedItem(null);
                jComboBoxLGATipoInstalacion.setSelectedItem(null);
                jComboBoxLGATipoCable.setSelectedItem(null);
                jComboBoxLGAProteccion.setSelectedItem(null);
                
                for(int i=0;i<jComboBoxDITipo.getItemCount();i++){
                    ParCombo elemento = (ParCombo)jComboBoxDITipo.getItemAt(i);
                    if(elemento.getKeyString().equals(datosEdge.getDiTipo())){
                        jComboBoxDITipo.setSelectedIndex(i);
                        break;
                    }
                }
                jTextFieldDISeccion.setText(formateador.format(datosEdge.getDiSeccion()));
                for(int i=0;i<jComboBoxDIMaterial.getItemCount();i++){
                    ParCombo elemento = (ParCombo)jComboBoxDIMaterial.getItemAt(i);
                    if(elemento.getKeyString().equals(datosEdge.getDiMaterial())){
                        jComboBoxDIMaterial.setSelectedIndex(i);
                        break;
                    }
                }
                jComboBoxDIAislamiento.setSelectedItem(datosEdge.getDiAislamiento());
                if(datosEdge.getDiTensionAislamiento()==1000){
                    jComboBoxDITensionAislamiento.setSelectedItem("1000");
                } else if(datosEdge.getDiTensionAislamiento()==750){
                    jComboBoxDITensionAislamiento.setSelectedItem("750");
                } else {
                    jComboBoxDITensionAislamiento.setSelectedItem(null);
                }
                jTextFieldDILongitud.setText(formateador.format(datosEdge.getDiLongitud()));
                for(int i=0;i<jComboBoxDITipoInstalacion.getItemCount();i++){
                    ParCombo elemento = (ParCombo)jComboBoxDITipoInstalacion.getItemAt(i);
                    if(elemento.getKeyString().equals(datosEdge.getDiTipoInstalacion())){
                        jComboBoxDITipoInstalacion.setSelectedIndex(i);
                        break;
                    }
                }
                
                for(int i=0;i<jComboBoxDITipoCable.getItemCount();i++){
                    ParCombo elemento = (ParCombo)jComboBoxDITipoCable.getItemAt(i);
                    if(elemento.getKeyString().equals(datosEdge.getTipoCable())){
                        jComboBoxDITipoCable.setSelectedIndex(i);
                        break;
                    }
                }
                
                jComboBoxDIProteccion.setSelectedItem(DecimalFormat.getInstance().format(datosEdge.getProteccion()));
                jTextFieldDITension1.setText(datosEdge.getTension1());
                jTextFieldDITension2.setText(datosEdge.getTension2());
                cambiarPotenciasCombo();
                String potencia = Utilidades.quitarDecimales(String.valueOf(datosEdge.getPotencia()));
                jComboBoxDIPotencia.setSelectedItem(potencia);
                jTextFieldDIDescripcion.setText(datosEdge.getDiDescripcion());
                jComboBoxDITipo2.setSelectedItem(datosEdge.getDiTipoDerivacion());
            } else {
                jRadioButtonTipoOtro.setSelected(false);
                jRadioButtonTipoOtro.setSelected(true);

                jTextFieldOtroDescripcion.setText(null);

                //jComboBoxLGAPuntoConexion.setSelectedItem(null);
                jComboBoxLGATipo.setSelectedItem(null);
                jTextFieldLGASeccion.setText("");
                jComboBoxLGAMaterial.setSelectedIndex(-1);
                //jTextFieldLGAAislamiento.setText("");
                //jTextFieldLGATensionAislamiento.setText("");
                jTextFieldLGALongitud.setText("");
                jComboBoxLGATension.setSelectedItem(null);
                jComboBoxLGATipoInstalacion.setSelectedItem(null);
                jComboBoxLGATipoCable.setSelectedItem(null);
                jComboBoxLGAProteccion.setSelectedItem(null);

                jComboBoxDITipo.setSelectedItem(null);
                jTextFieldDISeccion.setText("");
                jComboBoxDIMaterial.setSelectedIndex(-1);
                jComboBoxDIAislamiento.setSelectedItem(null);
                jComboBoxDITensionAislamiento.setSelectedItem(null);
                jTextFieldDILongitud.setText("");
                jComboBoxDITipoInstalacion.setSelectedItem(null);
                jComboBoxDITipoCable.setSelectedItem(null);
                jComboBoxDIProteccion.setSelectedItem(null);
                jTextFieldDITension1.setText("");
                jTextFieldDITension2.setText("");
                jComboBoxDIPotencia.setSelectedItem(null);
                jTextFieldDIDescripcion.setText("");
                jComboBoxDITipo2.setSelectedItem(null);
            }
        } else {
            jRadioButtonTipoOtro.setSelected(false);
            jRadioButtonTipoOtro.setSelected(true);
            
            jTextFieldOtroDescripcion.setText(null);
            
            //jComboBoxLGAPuntoConexion.setSelectedItem(null);
            jComboBoxLGATipo.setSelectedItem(null);
            jTextFieldLGASeccion.setText("");
            jComboBoxLGAMaterial.setSelectedIndex(-1);
            //jTextFieldLGAAislamiento.setText("");
            //jTextFieldLGATensionAislamiento.setText("");
            jTextFieldLGALongitud.setText("");
            jComboBoxLGATension.setSelectedItem(null);
            jComboBoxLGATipoInstalacion.setSelectedItem(null);
            jComboBoxLGATipoCable.setSelectedItem(null);
            jComboBoxLGAProteccion.setSelectedItem(null);
                
            jComboBoxDITipo.setSelectedItem(null);
            jTextFieldDISeccion.setText("");
            jComboBoxDIMaterial.setSelectedIndex(-1);
            jComboBoxDIAislamiento.setSelectedItem(null);
            jComboBoxDITensionAislamiento.setSelectedItem(null);
            jTextFieldDILongitud.setText("");
            jComboBoxDITipoInstalacion.setSelectedItem(null);
            jComboBoxDITipoCable.setSelectedItem(null);
            jComboBoxDIProteccion.setSelectedItem(null);
            jTextFieldDITension1.setText("");
            jTextFieldDITension2.setText("");
            jComboBoxDIPotencia.setSelectedItem(null);
            jTextFieldDIDescripcion.setText("");
            jComboBoxDITipo2.setSelectedItem(null);
        }
        
        if(!"Otra".equalsIgnoreCase((String)(jComboBoxDITipo2.getSelectedItem()))){
            jTextFieldDIDescripcion.setEditable(false);
        }
    }
    
    public EdgeBean getValue() {
        return datosEdge;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupTipo = new javax.swing.ButtonGroup();
        jPanelOtro = new javax.swing.JPanel();
        jTextFieldOtroDescripcion = new javax.swing.JTextField();
        jLabelOtroDescripcion = new javax.swing.JLabel();
        jPanelDI = new javax.swing.JPanel();
        jLabelDITipo = new javax.swing.JLabel();
        jComboBoxDITipo = new javax.swing.JComboBox();
        jLabelDISeccion = new javax.swing.JLabel();
        jTextFieldDISeccion = new javax.swing.JTextField();
        jComboBoxDIMaterial = new javax.swing.JComboBox();
        jLabelDIMaterial = new javax.swing.JLabel();
        jLabelDITensionAislamiento = new javax.swing.JLabel();
        jTextFieldDILongitud = new javax.swing.JTextField();
        jLabelDILongitud = new javax.swing.JLabel();
        jComboBoxDIAislamiento = new javax.swing.JComboBox();
        jLabelDIAislamiento = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxDITensionAislamiento = new javax.swing.JComboBox();
        jLabelDITipoInstalacion = new javax.swing.JLabel();
        jComboBoxDITipoInstalacion = new javax.swing.JComboBox();
        jComboBoxDITipoCable = new javax.swing.JComboBox();
        jLabelDITipoCable = new javax.swing.JLabel();
        jComboBoxDIProteccion = new javax.swing.JComboBox();
        jLabelDIProteccion = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelDIPotencia = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldDITension1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldDITension2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboBoxDIPotencia = new javax.swing.JComboBox();
        jLabelTipoDerivacion = new javax.swing.JLabel();
        jLabelDIDescripcion = new javax.swing.JLabel();
        jComboBoxDITipo2 = new javax.swing.JComboBox();
        jTextFieldDIDescripcion = new javax.swing.JTextField();
        jPanelLGA = new javax.swing.JPanel();
        jLabelLGATipo = new javax.swing.JLabel();
        jComboBoxLGATipo = new javax.swing.JComboBox();
        jLabelLGASeccion = new javax.swing.JLabel();
        jTextFieldLGASeccion = new javax.swing.JTextField();
        jLabelLGAMaterial = new javax.swing.JLabel();
        jComboBoxLGAMaterial = new javax.swing.JComboBox();
        jTextFieldLGALongitud = new javax.swing.JTextField();
        jLabelLGALongitud = new javax.swing.JLabel();
        jLabelLGAAislamiento = new javax.swing.JLabel();
        jLabelLGATensionAislamiento = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldLGATensionAislamiento = new javax.swing.JTextField();
        jTextFieldLGAAislamiento = new javax.swing.JTextField();
        jLabelLGATipoInstalacion = new javax.swing.JLabel();
        jComboBoxLGATipoInstalacion = new javax.swing.JComboBox();
        jLabelLGATension = new javax.swing.JLabel();
        jComboBoxLGATension = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxLGATipoCable = new javax.swing.JComboBox();
        jLabelLGATipoCable = new javax.swing.JLabel();
        jComboBoxLGAProteccion = new javax.swing.JComboBox();
        jLabelLGAProteccion = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jRadioButtonTipoLGA = new javax.swing.JRadioButton();
        jRadioButtonTipoDI = new javax.swing.JRadioButton();
        jRadioButtonTipoOtro = new javax.swing.JRadioButton();
        jLabelTipo = new javax.swing.JLabel();
        jBtnAceptar = new javax.swing.JButton();
        jPanel = new javax.swing.JPanel();

        jPanelOtro.setMaximumSize(new java.awt.Dimension(250, 41));
        jPanelOtro.setMinimumSize(new java.awt.Dimension(250, 41));
        jPanelOtro.setPreferredSize(new java.awt.Dimension(250, 41));

        jLabelOtroDescripcion.setText("Descripción");

        org.jdesktop.layout.GroupLayout jPanelOtroLayout = new org.jdesktop.layout.GroupLayout(jPanelOtro);
        jPanelOtro.setLayout(jPanelOtroLayout);
        jPanelOtroLayout.setHorizontalGroup(
            jPanelOtroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelOtroLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabelOtroDescripcion)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTextFieldOtroDescripcion, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelOtroLayout.setVerticalGroup(
            jPanelOtroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelOtroLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelOtroLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelOtroDescripcion)
                    .add(jTextFieldOtroDescripcion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanelDI.setMaximumSize(new java.awt.Dimension(382, 328));
        jPanelDI.setMinimumSize(new java.awt.Dimension(382, 328));
        jPanelDI.setPreferredSize(new java.awt.Dimension(382, 328));

        jLabelDITipo.setText("Tipo");

        jLabelDISeccion.setText("Sección");

        jLabelDIMaterial.setText("Material");

        jLabelDITensionAislamiento.setText("Tensión aislamiento");

        jLabelDILongitud.setText("Longitud");

        jComboBoxDIAislamiento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ES07Z1-K", "RZ1-K" }));

        jLabelDIAislamiento.setText("Aislamiento");

        jLabel1.setText("mm2");

        jLabel2.setText("V");

        jLabel3.setText("m");

        jComboBoxDITensionAislamiento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "750", "1000" }));

        jLabelDITipoInstalacion.setText("Tipo instalación");

        jComboBoxDITipoInstalacion.setEditable(true);
        jComboBoxDITipoInstalacion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxDITipoInstalacionItemStateChanged(evt);
            }
        });

        jLabelDITipoCable.setText("Tipo cable");

        jComboBoxDIProteccion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1,5", "3", "5", "6", "7,5", "10", "16", "20", "25", "32", "40", "50", "63", "80", "100", "125" }));

        jLabelDIProteccion.setText("Protección de línea");

        jLabel8.setText("A");

        jLabelDIPotencia.setText("Potencia");

        jLabel11.setText("W");

        jTextFieldDITension1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldDITension1KeyReleased(evt);
            }
        });

        jLabel10.setText("/");

        jTextFieldDITension2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldDITension2KeyReleased(evt);
            }
        });

        jLabel12.setText("V");

        jLabel13.setText("Tensión");

        jComboBoxDIPotencia.setEditable(true);

        jLabelTipoDerivacion.setText("Tipo Derivación");

        jLabelDIDescripcion.setText("Descripción");

        jComboBoxDITipo2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "A Servicios Generales", "A Planta", "Otra" }));
        jComboBoxDITipo2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxDITipo2ItemStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelDILayout = new org.jdesktop.layout.GroupLayout(jPanelDI);
        jPanelDI.setLayout(jPanelDILayout);
        jPanelDILayout.setHorizontalGroup(
            jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelDILayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelDILayout.createSequentialGroup()
                        .add(55, 55, 55)
                        .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabelDISeccion)
                            .add(jLabelDIMaterial)
                            .add(jLabelDITipo))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jComboBoxDITipo, 0, 269, Short.MAX_VALUE)
                            .add(jPanelDILayout.createSequentialGroup()
                                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jComboBoxDIMaterial, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jTextFieldDISeccion, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel1))))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelDILayout.createSequentialGroup()
                        .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabelDIDescripcion)
                            .add(jLabelTipoDerivacion)
                            .add(jLabel13)
                            .add(jLabelDIProteccion)
                            .add(jLabelDITipoCable)
                            .add(jLabelDITipoInstalacion)
                            .add(jLabelDILongitud)
                            .add(jLabelDITensionAislamiento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 93, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelDIAislamiento))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jTextFieldDIDescripcion, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                            .add(jComboBoxDITipoInstalacion, 0, 269, Short.MAX_VALUE)
                            .add(jComboBoxDITipoCable, 0, 269, Short.MAX_VALUE)
                            .add(jPanelDILayout.createSequentialGroup()
                                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jComboBoxDIAislamiento, 0, 92, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jComboBoxDITensionAislamiento, 0, 92, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jTextFieldDILongitud, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel3)
                                    .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelDILayout.createSequentialGroup()
                                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jComboBoxDITipo2, 0, 228, Short.MAX_VALUE)
                                    .add(jPanelDILayout.createSequentialGroup()
                                        .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jPanelDILayout.createSequentialGroup()
                                                .add(jComboBoxDIProteccion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 89, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                                            .add(jPanelDILayout.createSequentialGroup()
                                                .add(jTextFieldDITension1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(jLabel10)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(jTextFieldDITension2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(jLabel12)
                                                .add(13, 13, 13)))
                                        .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jPanelDILayout.createSequentialGroup()
                                                .add(10, 10, 10)
                                                .add(jLabelDIPotencia))
                                            .add(jLabel8))
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jComboBoxDIPotencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel11)
                                .add(27, 27, 27)))))
                .addContainerGap())
        );

        jPanelDILayout.linkSize(new java.awt.Component[] {jComboBoxDIAislamiento, jComboBoxDIMaterial, jComboBoxDITensionAislamiento, jTextFieldDILongitud, jTextFieldDISeccion}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanelDILayout.linkSize(new java.awt.Component[] {jTextFieldDITension1, jTextFieldDITension2}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanelDILayout.setVerticalGroup(
            jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelDILayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jComboBoxDITipo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelDITipo))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelDISeccion)
                    .add(jTextFieldDISeccion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelDIMaterial)
                    .add(jComboBoxDIMaterial, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelDIAislamiento)
                    .add(jComboBoxDIAislamiento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelDITensionAislamiento)
                    .add(jLabel2)
                    .add(jComboBoxDITensionAislamiento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelDILongitud)
                    .add(jLabel3)
                    .add(jTextFieldDILongitud, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jComboBoxDITipoInstalacion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelDITipoInstalacion))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jComboBoxDITipoCable, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelDITipoCable))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelDIProteccion)
                    .add(jComboBoxDIProteccion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel13)
                    .add(jTextFieldDITension1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel10)
                    .add(jTextFieldDITension2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel12)
                    .add(jLabelDIPotencia)
                    .add(jComboBoxDIPotencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel11))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelTipoDerivacion)
                    .add(jComboBoxDITipo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelDILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextFieldDIDescripcion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelDIDescripcion))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelLGA.setMaximumSize(new java.awt.Dimension(250, 230));
        jPanelLGA.setMinimumSize(new java.awt.Dimension(250, 230));
        jPanelLGA.setPreferredSize(new java.awt.Dimension(250, 230));

        jLabelLGATipo.setText("Tipo");

        jLabelLGASeccion.setText("Sección");

        jLabelLGAMaterial.setText("Material");

        jLabelLGALongitud.setText("Longitud");

        jLabelLGAAislamiento.setText("Aislamiento");

        jLabelLGATensionAislamiento.setText("Tensión aislamiento");

        jLabel4.setText("mm2");

        jLabel5.setText("V");

        jLabel6.setText("m");

        jTextFieldLGATensionAislamiento.setEditable(false);
        jTextFieldLGATensionAislamiento.setText("1000");

        jTextFieldLGAAislamiento.setEditable(false);
        jTextFieldLGAAislamiento.setText("RZ1-K");

        jLabelLGATipoInstalacion.setText("Tipo instalación");

        jComboBoxLGATipoInstalacion.setEditable(true);
        jComboBoxLGATipoInstalacion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxLGATipoInstalacionItemStateChanged(evt);
            }
        });

        jLabelLGATension.setText("Tensión");

        jComboBoxLGATension.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "230", "400" }));

        jLabel7.setText("V");

        jLabelLGATipoCable.setText("Tipo cable");

        jComboBoxLGAProteccion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1,5", "3", "5", "6", "7,5", "10", "16", "20", "25", "32", "40", "50", "63", "80", "100", "125", "250", "400" }));

        jLabelLGAProteccion.setText("Protección de línea");

        jLabel9.setText("A");

        org.jdesktop.layout.GroupLayout jPanelLGALayout = new org.jdesktop.layout.GroupLayout(jPanelLGA);
        jPanelLGA.setLayout(jPanelLGALayout);
        jPanelLGALayout.setHorizontalGroup(
            jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelLGALayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabelLGAAislamiento)
                    .add(jPanelLGALayout.createSequentialGroup()
                        .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabelLGASeccion)
                            .add(jLabelLGAMaterial)
                            .add(jLabelLGATension)
                            .add(jLabelLGATipo))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                    .add(jPanelLGALayout.createSequentialGroup()
                        .add(7, 7, 7)
                        .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabelLGALongitud)
                            .add(jLabelLGATensionAislamiento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 93, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelLGATipoInstalacion)
                            .add(jLabelLGAProteccion)
                            .add(jLabelLGATipoCable))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelLGALayout.createSequentialGroup()
                        .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jTextFieldLGALongitud)
                            .add(jTextFieldLGATensionAislamiento)
                            .add(jComboBoxLGAMaterial, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jTextFieldLGAAislamiento)
                            .add(jTextFieldLGASeccion, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                            .add(jComboBoxLGATension, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel6)
                            .add(jLabel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel4)
                            .add(jLabel5)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelLGALayout.createSequentialGroup()
                        .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanelLGALayout.createSequentialGroup()
                                .add(jComboBoxLGAProteccion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 83, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel9)
                                .add(3, 3, 3))
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jComboBoxLGATipoCable, 0, 222, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jComboBoxLGATipoInstalacion, 0, 222, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                    .add(jComboBoxLGATipo, 0, 222, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelLGALayout.setVerticalGroup(
            jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelLGALayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelLGATipo)
                    .add(jComboBoxLGATipo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelLGATension)
                    .add(jComboBoxLGATension, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7))
                .add(6, 6, 6)
                .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelLGASeccion)
                    .add(jTextFieldLGASeccion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelLGAMaterial)
                    .add(jComboBoxLGAMaterial, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelLGAAislamiento)
                    .add(jTextFieldLGAAislamiento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelLGATensionAislamiento)
                    .add(jTextFieldLGATensionAislamiento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelLGALongitud)
                    .add(jTextFieldLGALongitud, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel6))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelLGATipoInstalacion)
                    .add(jComboBoxLGATipoInstalacion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelLGATipoCable)
                    .add(jComboBoxLGATipoCable, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelLGALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelLGAProteccion)
                    .add(jComboBoxLGAProteccion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel9))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        buttonGroupTipo.add(jRadioButtonTipoLGA);
        jRadioButtonTipoLGA.setText("LGA");
        jRadioButtonTipoLGA.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonTipoLGA.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButtonTipoLGA.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonTipoLGAItemStateChanged(evt);
            }
        });

        buttonGroupTipo.add(jRadioButtonTipoDI);
        jRadioButtonTipoDI.setText("DI");
        jRadioButtonTipoDI.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonTipoDI.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButtonTipoDI.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonTipoDIItemStateChanged(evt);
            }
        });

        buttonGroupTipo.add(jRadioButtonTipoOtro);
        jRadioButtonTipoOtro.setSelected(true);
        jRadioButtonTipoOtro.setText("Otro");
        jRadioButtonTipoOtro.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonTipoOtro.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButtonTipoOtro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonTipoOtroItemStateChanged(evt);
            }
        });

        jLabelTipo.setText("Tipo");

        jBtnAceptar.setText("Aceptar");
        jBtnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAceptarActionPerformed(evt);
            }
        });

        jPanel.setMaximumSize(new java.awt.Dimension(382, 328));
        jPanel.setMinimumSize(new java.awt.Dimension(382, 328));
        jPanel.setPreferredSize(new java.awt.Dimension(382, 328));

        org.jdesktop.layout.GroupLayout jPanelLayout = new org.jdesktop.layout.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 382, Short.MAX_VALUE)
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 355, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jLabelTipo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jRadioButtonTipoLGA)
                        .add(6, 6, 6)
                        .add(jRadioButtonTipoDI)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jRadioButtonTipoOtro))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jBtnAceptar))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelTipo)
                    .add(jRadioButtonTipoLGA)
                    .add(jRadioButtonTipoDI)
                    .add(jRadioButtonTipoOtro))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jBtnAceptar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldDITension2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDITension2KeyReleased
        cambiarPotenciasCombo();
    }//GEN-LAST:event_jTextFieldDITension2KeyReleased

    private void jTextFieldDITension1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDITension1KeyReleased
        cambiarPotenciasCombo();
    }//GEN-LAST:event_jTextFieldDITension1KeyReleased

    private void jComboBoxLGATipoInstalacionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxLGATipoInstalacionItemStateChanged
        if (jComboBoxLGATipoInstalacion.getItemCount()==6) {
            if (jComboBoxLGATipoInstalacion.getSelectedIndex()!=-1) {
                tipoInstalacionLGASeleccionada = jComboBoxLGATipoInstalacion.getSelectedIndex();
                switch (tipoInstalacionLGASeleccionada) {
                    case 0: jComboBoxLGATipoInstalacion.setEditable(false); break;
                    case 1: jComboBoxLGATipoInstalacion.setEditable(false); break;
                    case 2: jComboBoxLGATipoInstalacion.setEditable(false); break;
                    case 3: jComboBoxLGATipoInstalacion.setEditable(true); break;
                    case 4: jComboBoxLGATipoInstalacion.setEditable(true); break;
                    case 5: jComboBoxLGATipoInstalacion.setEditable(true); break;
                }
            }
        }
    }//GEN-LAST:event_jComboBoxLGATipoInstalacionItemStateChanged

    private void jComboBoxDITipoInstalacionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxDITipoInstalacionItemStateChanged
        if (jComboBoxDITipoInstalacion.getItemCount()==6) {
            if (jComboBoxDITipoInstalacion.getSelectedIndex()!=-1) {
                tipoInstalacionDISeleccionada = jComboBoxDITipoInstalacion.getSelectedIndex();
                switch (tipoInstalacionDISeleccionada) {
                    case 0: jComboBoxDITipoInstalacion.setEditable(false); break;
                    case 1: jComboBoxDITipoInstalacion.setEditable(false); break;
                    case 2: jComboBoxDITipoInstalacion.setEditable(false); break;
                    case 3: jComboBoxDITipoInstalacion.setEditable(true); break;
                    case 4: jComboBoxDITipoInstalacion.setEditable(true); break;
                    case 5: jComboBoxDITipoInstalacion.setEditable(true); break;
                }
            }
        }
    }//GEN-LAST:event_jComboBoxDITipoInstalacionItemStateChanged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
// TODO add your handling code here:
    }//GEN-LAST:event_formMousePressed

    private void jRadioButtonTipoOtroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonTipoOtroItemStateChanged
        if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED) mostrarPanelCorrespondiente();
    }//GEN-LAST:event_jRadioButtonTipoOtroItemStateChanged

    private void jRadioButtonTipoDIItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonTipoDIItemStateChanged
        if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED) mostrarPanelCorrespondiente();
    }//GEN-LAST:event_jRadioButtonTipoDIItemStateChanged

    private void jRadioButtonTipoLGAItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonTipoLGAItemStateChanged
        if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED) mostrarPanelCorrespondiente();
    }//GEN-LAST:event_jRadioButtonTipoLGAItemStateChanged
    
    private void jBtnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAceptarActionPerformed
        if(valida()){
            datosEdge.setTipo("");
            if(jRadioButtonTipoOtro.isSelected()){
                datosEdge.setTipo("Otro");
                datosEdge.setOtroDescripcion(jTextFieldOtroDescripcion.getText());
            } else {
                datosEdge.setOtroDescripcion("");
            }
            if(jRadioButtonTipoLGA.isSelected()){
                datosEdge.setTipo("LA");
                //datosEdge.setLgaPuntoConexion(((ParCombo)jComboBoxLGAPuntoConexion.getSelectedItem()).getDescription());
                datosEdge.setLgaTipo(((ParCombo)jComboBoxLGATipo.getSelectedItem()).getKeyString());
                datosEdge.setLgaSeccion(Double.parseDouble((String)(jTextFieldLGASeccion.getText()).replace(",", ".")));
                datosEdge.setLgaMaterial(((ParCombo)jComboBoxLGAMaterial.getSelectedItem()).getKeyString());
                datosEdge.setLgaAislamiento(jTextFieldLGAAislamiento.getText());
                datosEdge.setLgaTensionAislamiento(Double.parseDouble(((String)jTextFieldLGATensionAislamiento.getText()).replace(",", ".")));
                datosEdge.setLgaLongitud(Double.parseDouble(jTextFieldLGALongitud.getText().replace(",", ".")));
                datosEdge.setTipoCable(((ParCombo)jComboBoxLGATipoCable.getSelectedItem()).getKeyString());
                datosEdge.setProteccion(Double.parseDouble(((String)jComboBoxLGAProteccion.getSelectedItem()).replace(",",".")));
                
                String valorTension = (String)jComboBoxLGATension.getSelectedItem();
                if(valorTension==null || "".equals(valorTension)){
                    valorTension = "0";
                }
                datosEdge.setLgaTension(Double.parseDouble(valorTension));

                String tipoInstalacion = null;
                if(jComboBoxLGATipoInstalacion.getSelectedItem() instanceof ParCombo){
                    tipoInstalacion = ((ParCombo)jComboBoxLGATipoInstalacion.getSelectedItem()).getKeyString();
                } else {
                    String nuevaInstalacion = String.valueOf(jComboBoxLGATipoInstalacion.getSelectedItem());
                    if (jComboBoxLGATipoInstalacion.getItemCount()==6) {
                        String tipoInstalacionModificada = "";
                        switch (tipoInstalacionLGASeleccionada) {
                            case 3: tipoInstalacionModificada="D";break;
                            case 4: tipoInstalacionModificada="E";break;
                            case 5: tipoInstalacionModificada="F";break;
                        }
                        if (null!=tipoInstalacionModificada) {
                            guardarNuevoTipoInstalacion(tipoInstalacionModificada,nuevaInstalacion);

                            ParCombo opcion = (ParCombo)jComboBoxLGATipoInstalacion.getItemAt(tipoInstalacionLGASeleccionada);
                            opcion.setKeyString(tipoInstalacionModificada);
                            opcion.setDescription(nuevaInstalacion);
                        }
                    } else {
                        String letra = ((ParCombo)jComboBoxLGATipoInstalacion.getItemAt(jComboBoxLGATipoInstalacion.getItemCount()-1)).getKeyString();
                        tipoInstalacion = "A";
                        if("A".equals(letra)) tipoInstalacion="B";
                        else if("B".equals(letra)) tipoInstalacion="C";
                        else if("C".equals(letra)) tipoInstalacion="D";
                        else if("D".equals(letra)) tipoInstalacion="E";
                        else if("E".equals(letra)) tipoInstalacion="F";
                        guardarNuevoTipoInstalacion(tipoInstalacion,nuevaInstalacion);
                        jComboBoxLGATipoInstalacion.addItem(new ParCombo(tipoInstalacion,nuevaInstalacion));
                    }
                    //if(jComboBoxLGATipoInstalacion.getItemCount()>=6)jComboBoxLGATipoInstalacion.setEditable(false);
                }
                datosEdge.setLgaTipoInstalacion(tipoInstalacion);
            } else {
                //datosEdge.setLgaPuntoConexion("");
                datosEdge.setLgaTipo("");
                datosEdge.setLgaSeccion(0);
                datosEdge.setLgaMaterial("");
                datosEdge.setLgaAislamiento("");
                datosEdge.setLgaTensionAislamiento(0);
                datosEdge.setLgaLongitud(0);
                datosEdge.setLgaTension(0);
                datosEdge.setLgaTipoInstalacion("");
            }
            if(jRadioButtonTipoDI.isSelected()){
                datosEdge.setTipo("DI");
                datosEdge.setDiTipo(((ParCombo)jComboBoxDITipo.getSelectedItem()).getKeyString());
                datosEdge.setDiSeccion(Double.parseDouble((String)(jTextFieldDISeccion.getText()).replace(",", ".")));
                datosEdge.setDiMaterial(((ParCombo)jComboBoxDIMaterial.getSelectedItem()).getKeyString());
                datosEdge.setDiAislamiento((String)jComboBoxDIAislamiento.getSelectedItem());
                datosEdge.setDiTensionAislamiento(Double.parseDouble((String)jComboBoxDITensionAislamiento.getSelectedItem()));
                datosEdge.setDiLongitud(Double.parseDouble(jTextFieldDILongitud.getText().replace(",", ".")));
                datosEdge.setTipoCable(((ParCombo)jComboBoxDITipoCable.getSelectedItem()).getKeyString());
                datosEdge.setProteccion(Double.parseDouble(((String)jComboBoxDIProteccion.getSelectedItem()).replace(",",".")));
                datosEdge.setTension1(jTextFieldDITension1.getText());
                datosEdge.setTension2(jTextFieldDITension2.getText());
                datosEdge.setPotencia(Double.parseDouble(((String)jComboBoxDIPotencia.getSelectedItem()).replace(",",".")));
                
                String tipoInstalacion = null;
                if(jComboBoxDITipoInstalacion.getSelectedItem() instanceof ParCombo){
                    tipoInstalacion = ((ParCombo)jComboBoxDITipoInstalacion.getSelectedItem()).getKeyString();
                } else {
                    String nuevaInstalacion = String.valueOf(jComboBoxDITipoInstalacion.getSelectedItem());
                    if (jComboBoxDITipoInstalacion.getItemCount()==6) {
                        String tipoInstalacionModificada = "";
                        switch (tipoInstalacionDISeleccionada) {
                            case 3: tipoInstalacionModificada="D";break;
                            case 4: tipoInstalacionModificada="E";break;
                            case 5: tipoInstalacionModificada="F";break;
                        }
                        if (null!=tipoInstalacionModificada) {
                            guardarNuevoTipoInstalacion(tipoInstalacionModificada,nuevaInstalacion);

                            ParCombo opcion = (ParCombo)jComboBoxDITipoInstalacion.getItemAt(tipoInstalacionDISeleccionada);
                            opcion.setKeyString(tipoInstalacionModificada);
                            opcion.setDescription(nuevaInstalacion);
                        }
                    } else {
                        String letra = ((ParCombo)jComboBoxDITipoInstalacion.getItemAt(jComboBoxDITipoInstalacion.getItemCount()-1)).getKeyString();
                        tipoInstalacion = "A";
                        if("A".equals(letra)) tipoInstalacion="B";
                        else if("B".equals(letra)) tipoInstalacion="C";
                        else if("C".equals(letra)) tipoInstalacion="D";
                        else if("D".equals(letra)) tipoInstalacion="E";
                        else if("E".equals(letra)) tipoInstalacion="F";
                        guardarNuevoTipoInstalacion(tipoInstalacion,nuevaInstalacion);
                        jComboBoxDITipoInstalacion.addItem(new ParCombo(tipoInstalacion,nuevaInstalacion));
                    }
                    //if(jComboBoxLGATipoInstalacion.getItemCount()>=6)jComboBoxLGATipoInstalacion.setEditable(false);
                }
                datosEdge.setDiTipoInstalacion(tipoInstalacion);
                datosEdge.setDiTipoDerivacion((String)jComboBoxDITipo2.getSelectedItem());
                datosEdge.setDiDescripcion(jTextFieldDIDescripcion.getText());
            } else {
                datosEdge.setDiTipo("");
                datosEdge.setDiSeccion(0);
                datosEdge.setDiMaterial("");
                datosEdge.setDiComposicion("");
                datosEdge.setDiAislamiento("");
                datosEdge.setDiTensionAislamiento(0);
                datosEdge.setDiLongitud(0);
                datosEdge.setDiCoseno(1);
                datosEdge.setDiRendimiento(1);
                datosEdge.setDiTipoDerivacion("");
                datosEdge.setDiDescripcion("");
            }
            
            datosEdge.setEditado(true);
            cellEditor.stopCellEditing();
        }
    }//GEN-LAST:event_jBtnAceptarActionPerformed

    private void jComboBoxDITipo2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxDITipo2ItemStateChanged
        if (jComboBoxDITipo2.getSelectedIndex()!=-1) {
            if("Otra".equalsIgnoreCase((String)jComboBoxDITipo2.getSelectedItem())){
                jTextFieldDIDescripcion.setEditable(true);
            } else {
                jTextFieldDIDescripcion.setText("");
                jTextFieldDIDescripcion.setEditable(false);
            }
        }
    }//GEN-LAST:event_jComboBoxDITipo2ItemStateChanged
    
    private void guardarNuevoTipoInstalacion(String letra, String tipoInstalacion){
        BaseDatos bd = Sesion.getInstance().getBaseDatos();
        try{
            int idInstalacion = ((Integer)Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID)).intValue();
            bd.ejecModificacion("UPDATE INSTALACIONES SET INTIPOCIRC"+letra+"="+UtilidadesSQL.tratarParametroString(tipoInstalacion)+" WHERE INID="+UtilidadesSQL.tratarParametroNumerico(idInstalacion));
        } catch(SQLException e){
            Mensaje.error("Error en InputEdge.guardarNuevoTipoInstalacion al cargar opciones en ComboBox: "+e.getMessage());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupTipo;
    private javax.swing.JButton jBtnAceptar;
    private javax.swing.JComboBox jComboBoxDIAislamiento;
    private javax.swing.JComboBox jComboBoxDIMaterial;
    private javax.swing.JComboBox jComboBoxDIPotencia;
    private javax.swing.JComboBox jComboBoxDIProteccion;
    private javax.swing.JComboBox jComboBoxDITensionAislamiento;
    private javax.swing.JComboBox jComboBoxDITipo;
    private javax.swing.JComboBox jComboBoxDITipo2;
    private javax.swing.JComboBox jComboBoxDITipoCable;
    private javax.swing.JComboBox jComboBoxDITipoInstalacion;
    private javax.swing.JComboBox jComboBoxLGAMaterial;
    private javax.swing.JComboBox jComboBoxLGAProteccion;
    private javax.swing.JComboBox jComboBoxLGATension;
    private javax.swing.JComboBox jComboBoxLGATipo;
    private javax.swing.JComboBox jComboBoxLGATipoCable;
    private javax.swing.JComboBox jComboBoxLGATipoInstalacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelDIAislamiento;
    private javax.swing.JLabel jLabelDIDescripcion;
    private javax.swing.JLabel jLabelDILongitud;
    private javax.swing.JLabel jLabelDIMaterial;
    private javax.swing.JLabel jLabelDIPotencia;
    private javax.swing.JLabel jLabelDIProteccion;
    private javax.swing.JLabel jLabelDISeccion;
    private javax.swing.JLabel jLabelDITensionAislamiento;
    private javax.swing.JLabel jLabelDITipo;
    private javax.swing.JLabel jLabelDITipoCable;
    private javax.swing.JLabel jLabelDITipoInstalacion;
    private javax.swing.JLabel jLabelLGAAislamiento;
    private javax.swing.JLabel jLabelLGALongitud;
    private javax.swing.JLabel jLabelLGAMaterial;
    private javax.swing.JLabel jLabelLGAProteccion;
    private javax.swing.JLabel jLabelLGASeccion;
    private javax.swing.JLabel jLabelLGATension;
    private javax.swing.JLabel jLabelLGATensionAislamiento;
    private javax.swing.JLabel jLabelLGATipo;
    private javax.swing.JLabel jLabelLGATipoCable;
    private javax.swing.JLabel jLabelLGATipoInstalacion;
    private javax.swing.JLabel jLabelOtroDescripcion;
    private javax.swing.JLabel jLabelTipo;
    private javax.swing.JLabel jLabelTipoDerivacion;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPanel jPanelDI;
    private javax.swing.JPanel jPanelLGA;
    private javax.swing.JPanel jPanelOtro;
    private javax.swing.JRadioButton jRadioButtonTipoDI;
    private javax.swing.JRadioButton jRadioButtonTipoLGA;
    private javax.swing.JRadioButton jRadioButtonTipoOtro;
    private javax.swing.JTextField jTextFieldDIDescripcion;
    private javax.swing.JTextField jTextFieldDILongitud;
    private javax.swing.JTextField jTextFieldDISeccion;
    private javax.swing.JTextField jTextFieldDITension1;
    private javax.swing.JTextField jTextFieldDITension2;
    private javax.swing.JTextField jTextFieldLGAAislamiento;
    private javax.swing.JTextField jTextFieldLGALongitud;
    private javax.swing.JTextField jTextFieldLGASeccion;
    private javax.swing.JTextField jTextFieldLGATensionAislamiento;
    private javax.swing.JTextField jTextFieldOtroDescripcion;
    // End of variables declaration//GEN-END:variables
    
    private void establecerRestricciones() {
        jTextFieldOtroDescripcion.setDocument(new LimitadorCaracteres(jTextFieldOtroDescripcion, 25, false, false));
        jTextFieldLGASeccion.setDocument(new LimitadorCaracteres(jTextFieldLGASeccion, 5, true, true));
        jTextFieldLGALongitud.setDocument(new LimitadorCaracteres(jTextFieldLGALongitud, 9, true, true));
        jTextFieldDILongitud.setDocument(new LimitadorCaracteres(jTextFieldDILongitud, 9, true, true));
        jTextFieldDISeccion.setDocument(new LimitadorCaracteres(jTextFieldDISeccion, 5, true, true));
        jTextFieldDIDescripcion.setDocument(new LimitadorCaracteres(jTextFieldDIDescripcion, 25, false, false));
    }
        
    private boolean valida(){
        boolean resultado = true;
        this.mensaje=null;
        if(jRadioButtonTipoOtro.isSelected()){
            boolean r1 = validaOtroDescripcion();
            resultado = r1;
        } else if(jRadioButtonTipoLGA.isSelected()){
            boolean r1 = true; //validaLGAPuntoConexion();
            boolean r2 = validaLGATipo();
            boolean r3 = validaLGASeccion();
            boolean r4 = validaLGAMaterial();
            boolean r5 = true;//validaLGAAislamiento();
            boolean r6 = true;//validaLGATensionAislamiento();
            boolean r7 = validaLGALongitud();
            boolean r8 = validaLGATension();
            boolean r9 = validaLGATipoInstalacion();
            boolean r10 = validaLGATipoCable();
            resultado = r1 && r2 && r3 && r4 && r5 && r6 && r7 && r8 && r9 && r10;
        } else if(jRadioButtonTipoDI.isSelected()){
            boolean r1 = validaDITipo();
            boolean r2 = validaDISeccion();
            boolean r3 = validaDIMaterial();
            boolean r4 = validaDIAislamiento();
            boolean r5 = validaDITensionAislamiento();
            boolean r6 = validaDILongitud();
            boolean r7 = validaDITipoInstalacion();
            boolean r8 = validaDITipoCable();
            boolean r9 = validaDITipo2();
            boolean r10 = validaDIPotencia();
            resultado = r1 && r2 && r3 && r4 && r5 && r6 && r7 && r8 && r9 && r10;
        } else {
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+jLabelTipo+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        if(!resultado){
            // Mostrar mensaje
            Mensaje.error(bundle.getString("ERRORES_EN_DATOS_INTRODUCIDOS")+this.mensaje+"\n");
        }
        return resultado;
    }       
    
    private void mostrarPanelCorrespondiente(){
        if(jRadioButtonTipoDI.isSelected()){            
            jPanel.removeAll();
            jPanel.setLayout(new BorderLayout());
            jPanel.add(jPanelDI,BorderLayout.CENTER);
            jPanel.revalidate();
            this.repaint();
            // si no hay potencia definida, cogemos como potencia por defecto la de suministro
            String potencia = (String)Sesion.getInstance().getValorHt(Constantes.SES_POTENCIA_PREVISTA);
            String tension1 = (String)Sesion.getInstance().getValorHt(Constantes.SES_TENSION1);
            String tension2 = (String)Sesion.getInstance().getValorHt(Constantes.SES_TENSION2);
            if(jComboBoxDIPotencia.getSelectedIndex() == -1) {
                jTextFieldDITension1.setText(tension1);
                jTextFieldDITension2.setText(tension2);
                cambiarPotenciasCombo();
                jComboBoxDIPotencia.setSelectedItem(potencia);
            }
        } else if(jRadioButtonTipoLGA.isSelected()){
            jPanel.removeAll();
            jPanel.setLayout(new BorderLayout());
            jPanel.add(jPanelLGA,BorderLayout.CENTER);
            jPanel.revalidate();
            this.repaint();
        } else if(jRadioButtonTipoOtro.isSelected()){
            jPanel.removeAll();
            jPanel.setLayout(new BorderLayout());
            jPanel.add(jPanelOtro,BorderLayout.CENTER);
            jPanel.revalidate();
            this.repaint();
        }
    }
    
    private void cargarOpcionesDesdeBD(){
        ResultSet rs = null;
        BaseDatos bd = Sesion.getInstance().getBaseDatos();
        try{
            jComboBoxDITipoInstalacion.removeAllItems();
            jComboBoxLGATipoInstalacion.removeAllItems();
            jComboBoxDITipoCable.removeAllItems();
            jComboBoxLGATipoCable.removeAllItems();
            int idInstalacion = ((Integer)Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID)).intValue();
            rs = bd.ejecSelect( "SELECT " +
                    "* " +
                    "FROM " +
                    "(" +
                    "SELECT * FROM TIPOS_INST_CIRCUITOS WHERE TNDESC<>'' " +
                    "UNION " +
                    "SELECT 'D' AS TNID,INTIPOCIRCD AS TNDESC FROM INSTALACIONES WHERE INID="+UtilidadesSQL.tratarParametroNumerico(idInstalacion)+" " +
                    "UNION " +
                    "SELECT 'E' AS TNID,INTIPOCIRCE AS TNDESC FROM INSTALACIONES WHERE INID="+UtilidadesSQL.tratarParametroNumerico(idInstalacion)+" " +
                    "UNION " +
                    "SELECT 'F' AS TNID,INTIPOCIRCF AS TNDESC FROM INSTALACIONES WHERE INID="+UtilidadesSQL.tratarParametroNumerico(idInstalacion)+" " +
                    ") AS TABLA " +
                    "WHERE " +
                    "TABLA.TNDESC<>'' " +
                    "ORDER " +
                    "BY TNID");
            while(rs.next()){
                jComboBoxLGATipoInstalacion.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
                jComboBoxDITipoInstalacion.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            }
            if(jComboBoxLGATipoInstalacion.getItemCount()>=6) jComboBoxLGATipoInstalacion.setEditable(false);
            if(jComboBoxDITipoInstalacion.getItemCount()>=6) jComboBoxDITipoInstalacion.setEditable(false);
            /*rs = bd.ejecSelect("SELECT * FROM PTOS_CONEXION ORDER BY PCDESC");
            while(rs.next()){
                jComboBoxLGAPuntoConexion.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            }*/
            rs = bd.ejecSelect("SELECT * FROM TIPOS_LINEA ORDER BY TLDESC ");
            while(rs.next()){
                jComboBoxLGATipo.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
                jComboBoxDITipo.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            }
            rs = bd.ejecSelect("SELECT * FROM MATERIALES ORDER BY MADESC");
            while(rs.next()){
                jComboBoxLGAMaterial.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
                jComboBoxDIMaterial.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            }
            rs = bd.ejecSelect("SELECT TSID, TSDESCCORTA FROM TIPOS_SALIDA ORDER BY TSID ASC");
            while(rs.next()){
                jComboBoxDITipoCable.addItem(new ParCombo(rs.getString("TSID"),rs.getString("TSDESCCORTA")));
                jComboBoxLGATipoCable.addItem(new ParCombo(rs.getString("TSID"),rs.getString("TSDESCCORTA")));
            }
            
            agregarPotenciasACombos();
        } catch(SQLException e){
            Mensaje.error("Error en InputEdge.cargarOpcionesDesdeBD al cargar opciones en ComboBox: "+e.getMessage());
        } finally {
            try{
                if(rs!=null)rs.close();
            } catch(SQLException e){
                Mensaje.error("Error en InputEdge.cargarOpcionesDesdeBD al cerrar el RecordSet: "+e.getMessage());
            }
        }
    }
    
    private boolean validaOtroDescripcion(){
        boolean resultado = true;
        return resultado;
    }

    /*private boolean validaLGAPuntoConexion() {
        boolean resultado = true;
        ParCombo valor = (ParCombo)jComboBoxLGAPuntoConexion.getSelectedItem();
        if(valor==null || "".equals(valor.getDescription())){
            String label = " "+jLabelLGAPuntoConexion.getText()+" ";
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }*/
    
    private boolean validaLGATipo() {
        boolean resultado = true;
        ParCombo valor = (ParCombo)jComboBoxLGATipo.getSelectedItem();
        if(valor==null || "".equals(valor.getDescription())){
            String label = " "+jLabelLGATipo.getText()+" ";
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }

    private boolean validaLGASeccion() {
        boolean resultado = true;
        String valor = jTextFieldLGASeccion.getText();
        String label = jLabelLGASeccion.getText();
        if(valor!=null && !"".equals(valor)){
            if(!Pattern.matches("((0?[1-9][0-9])|([1-9][0-9]{2}))(,[0-9][0-9]?)??", valor)){
                // Mensaje campo con formato incorrecto
                if(mensaje==null)mensaje="";
                mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("VALOR_INCORRECTO")+"(valores correctos: 10-999)\n";
                resultado=false;
            }
        } else {
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }

    private boolean validaLGAMaterial() {
        boolean resultado = true;
        ParCombo valor = (ParCombo)jComboBoxLGAMaterial.getSelectedItem();
        if(valor==null || "".equals(valor.getDescription())){
            String label = " "+jLabelLGAMaterial.getText()+" ";
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }
    
    private boolean validaLGAAislamiento() {
        boolean resultado = true;
        String valor = jTextFieldLGAAislamiento.getText();
        if(valor==null || "".equals(valor)){
            String label = " "+jLabelLGAAislamiento.getText()+" ";
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }
    
    private boolean validaLGATensionAislamiento() {
        boolean resultado = true;
        String valor = jTextFieldLGATensionAislamiento.getText();
        String label = " "+jLabelLGATensionAislamiento.getText()+" ";
        if(valor==null || "".equals(valor)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        } else {
            if(!Pattern.matches("(1000)|([6-9][0-9]{2})", valor)){
                // Mensaje campo con formato incorrecto
                if(mensaje==null)mensaje="";
                mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("VALOR_INCORRECTO")+" (valores correctos: 600-1000)\n";
                resultado=false;
            }
        }
        return resultado;
    }
    
    private boolean validaLGALongitud() {
        boolean resultado = true;
        String valor = jTextFieldLGALongitud.getText();
        String label = " "+jLabelLGALongitud.getText()+" ";
        if(valor==null || "".equals(valor)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        } else {
            if(!Pattern.matches("[0-9]{1,6}(,[0-9]{1,2})?", valor)){
                // Mensaje campo con formato incorrecto
                if(mensaje==null)mensaje="";
                mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("VALOR_INCORRECTO")+" (valores correctos: 0-999999)\n";
                resultado=false;
            }
        }
        return resultado;
    }
    
    private boolean validaLGATension() {
        boolean resultado = true;
        String valor = (String)jComboBoxLGATension.getSelectedItem();
        if(valor==null || "".equals(valor)){
            String label = " "+jLabelLGATension.getText()+" ";
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }
    
    private boolean validaLGATipoInstalacion(){
        boolean resultado = true;
        String valor = null;
        if(jComboBoxLGATipoInstalacion.getSelectedItem()!=null){
            if (jComboBoxLGATipoInstalacion.getSelectedItem() instanceof ParCombo) {
                valor=((ParCombo)jComboBoxLGATipoInstalacion.getSelectedItem()).getKeyString();
            } else {
                valor=(String)jComboBoxLGATipoInstalacion.getSelectedItem();
            }
        }
        String label = jLabelLGATipoInstalacion.getText();
        if(valor==null || "".equals(valor)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }
    
    private boolean validaLGATipoCable(){
        boolean resultado = true;
        String valor = null;
        if(jComboBoxLGATipoCable.getSelectedItem()!=null){
            if (jComboBoxLGATipoCable.getSelectedItem() instanceof ParCombo) {
                valor=((ParCombo)jComboBoxLGATipoCable.getSelectedItem()).getKeyString();
            } else {
                valor=(String)jComboBoxLGATipoCable.getSelectedItem();
            }
        }
        String label = jLabelLGATipoCable.getText();
        if(valor==null || "".equals(valor)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }
      
    private boolean validaDITipoInstalacion() {
        boolean resultado = true;
        String valor = null;
        if(jComboBoxDITipoInstalacion.getSelectedItem()!=null){
            if (jComboBoxDITipoInstalacion.getSelectedItem() instanceof ParCombo) {
                valor=((ParCombo)jComboBoxDITipoInstalacion.getSelectedItem()).getKeyString();
            } else {
                valor=(String)jComboBoxDITipoInstalacion.getSelectedItem();
            }
        }
        String label = jLabelDITipoInstalacion.getText();
        if(valor==null || "".equals(valor)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }

    private boolean validaDITipoCable() {
        boolean resultado = true;
        String valor = null;
        if(jComboBoxDITipoCable.getSelectedItem()!=null){
            if (jComboBoxDITipoCable.getSelectedItem() instanceof ParCombo) {
                valor=((ParCombo)jComboBoxDITipoCable.getSelectedItem()).getKeyString();
            } else {
                valor=(String)jComboBoxDITipoCable.getSelectedItem();
            }
        }
        String label = jLabelDITipoCable.getText();
        if(valor==null || "".equals(valor)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }
    
    private boolean validaDITipo() {
        boolean resultado = true;
        ParCombo valor = (ParCombo)jComboBoxDITipo.getSelectedItem();
        if(valor==null || "".equals(valor.getDescription())){
            String label = " "+jLabelDITipo.getText()+" ";
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }
    
    private boolean validaDITipo2() {
        boolean resultado = true;
        String valor = (String)jComboBoxDITipo2.getSelectedItem();
        if(valor==null || "".equals(valor)){
            String label = " "+jLabelTipoDerivacion.getText()+" ";
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        } else if("Otra".equalsIgnoreCase(valor)){
            valor=jTextFieldDIDescripcion.getText();
            if(valor==null || "".equals(valor)){
                String label = " "+jLabelDIDescripcion.getText()+" ";
                // Mensaje campo obligatorio
                if(mensaje==null)mensaje="";
                mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
                resultado = false;                
            }
        }
        return resultado;
    }

    private boolean validaDISeccion() {
        boolean resultado = true;
        String valor = jTextFieldDISeccion.getText();
        String label = jLabelDISeccion.getText();
        if(valor!=null && !"".equals(valor)){
            if(!Pattern.matches("((0?0?[6-7])|(0?[1-9][0-9])|([1-9][0-9]{2}))(,[0-9][0-9]?)??", valor)){
                // Mensaje campo con formato incorrecto
                if(mensaje==null)mensaje="";
                mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("VALOR_INCORRECTO")+" (valores correctos: 6-999)\n";
                resultado=false;
            }
        } else {
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }

    private boolean validaDIMaterial() {
        boolean resultado = true;
        ParCombo valor = (ParCombo)jComboBoxDIMaterial.getSelectedItem();
        if(valor==null || "".equals(valor.getDescription())){
            String label = " "+jLabelDIMaterial.getText()+" ";
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }
   
    private boolean validaDIAislamiento() {
        boolean resultado = true;
        String valor = (String)jComboBoxDIAislamiento.getSelectedItem();
        if(valor==null || "".equals(valor)){
            String label = " "+jLabelDIAislamiento.getText()+" ";
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }

    private boolean validaDITensionAislamiento() {
        boolean resultado = true;
        String valor = (String)jComboBoxDITensionAislamiento.getSelectedItem();
        if(valor==null || "".equals(valor)){
            String label = " "+jLabelDITensionAislamiento.getText()+" ";
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        }
        return resultado;
    }

    private boolean validaDILongitud() {
        boolean resultado = true;
        String valor = jTextFieldDILongitud.getText();
        String label = " "+jLabelDILongitud.getText()+" ";
        if(valor==null || "".equals(valor)){
            // Mensaje campo obligatorio
            if(mensaje==null)mensaje="";
            mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("ES_OBLIGATORIO")+"\n";
            resultado = false;
        } else {
            if(!Pattern.matches("[0-9]{1,6}(,[0-9]{1,2})?", valor)){
                // Mensaje campo con formato incorrecto
                if(mensaje==null)mensaje="";
                mensaje+= bundle.getString("EL_CAMPO")+" "+label+" "+bundle.getString("VALOR_INCORRECTO")+" (valores correctos: 0-999999)\n";
                resultado=false;
            }
        }
        return resultado;
    }
    
    private boolean validaDIPotencia() {
        boolean resultado = true;
        String labelPotencia = jLabelDIPotencia.getText();
        double potenciaPrevista = Double.parseDouble(Utilidades.cambiarComa((String)Sesion.getInstance().getValorHt(Constantes.SES_POTENCIA_PREVISTA)));
        double potencia = Double.parseDouble(Utilidades.cambiarComa((String)jComboBoxDIPotencia.getSelectedItem()));
        
        if(potencia > potenciaPrevista) {
            if(mensaje == null) mensaje="";
            mensaje += bundle.getString("EL_CAMPO") + " " + labelPotencia + " " + bundle.getString("POTENCIA_MAYOR_LIMITE") + " (Potencia prevista=" + potenciaPrevista + ")\n";
            resultado = false;
        }
        
        return resultado;
    }
    
    private void agregarPotenciasACombos() {
        BaseDatos bd = Sesion.getInstance().getBaseDatos();
        m1 = new DefaultComboBoxModel();
        m2 = new DefaultComboBoxModel();
        m3 = new DefaultComboBoxModel();
        mTemp = new DefaultComboBoxModel();
        ParCombo pc = null;
        ResultSet rs = null;

        try{
            rs = bd.ejecSelect("SELECT * FROM POTNORMALIZADAS");
            while(rs.next()){
                String t1 = rs.getString("PNT1");
                String t2 = rs.getString("PNT2");
                String pot = rs.getString("PNPOT");

                if(t1.equals("230") && (t2 == null || t2.equals(""))) m1.addElement(pot);
                else if(t1.equals("230") && t2.equals("130")) m2.addElement(pot);
                else if(t1.equals("400") && t2.equals("230")) m3.addElement(pot);
            }
            mTemp.addElement("");
        }
        catch(SQLException e){
            Mensaje.error("InputEdge.java: "+e.getMessage(), e);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
        }

    }
    
    private void cambiarPotenciasCombo() {
        String tension1 = jTextFieldDITension1.getText();
        String tension2 = jTextFieldDITension2.getText();

        if(tension1.equals("230") && tension2.equals("")) {
            jComboBoxDIPotencia.setModel(m1);
        } else if(tension1.equals("230") && tension2.equals("130")) {
            jComboBoxDIPotencia.setModel(m2);
        } else if(tension1.equals("400") && tension2.equals("230")) {
            jComboBoxDIPotencia.setModel(m3);
        } else {
            jComboBoxDIPotencia.setModel(mTemp);
        }

    }
    
}
