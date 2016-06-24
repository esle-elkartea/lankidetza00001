/*
 * FrmNuevaInst.java
 *
 * Created on 31 de julio de 2006, 13:18
 */

package forms;

import forms.establecer.EstablecerDatos;
import funciones.AutoComplCmbBxRestrictivo;
import funciones.BaseDatos;
import funciones.LimitadorCaracteres;
import funciones.ParCombo;
import funciones.Sesion;
import funciones.Utilidades;
import funciones.UtilidadesSQL;
import funciones.beans.ControlTablaPrevision;
import funciones.beans.ModeloTablaPrevision;
import funciones.beans.MyComboBoxEditor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import main.Constantes;
import main.Mensaje;

/**
 *
 * @author  enrique
 */
public class FrmNuevaInst extends javax.swing.JFrame {
    private BaseDatos bd;
    public ModeloTablaPrevision modeloTablaPrevision;
    public ControlTablaPrevision    controlPrevision;
    private ParCombo pcTipoInstElegida, pcReglamentos, pcUsoInstalacion;
    boolean esSinProyecto = false;
    private String descTipoInst;
    private String variosSinClasificar;
    private String tiCod = "";
    private int tiNum;
    private int tiLim;
    private double potencia, superficieLocal;
    private double tension1, tension2;
    private String memoriaPor;
    private int ultimaTab = -1;
    private Calendar c = new GregorianCalendar();
    private int dia;
    private int mes;
    private int anyo;
    private String fecha;

    //Pestaña Prev industrales (como propiedades locales)

    //Pestaña Prev viviendas
    private ParCombo pc = null;
    private String gradoElect1, gradoElect2;
    private int numViv1, numViv2;
    private double superficieUnit1, superficieUnit2, demandaMax1, demandaMax2, cargasPrevistasViviendas;
    private double ascensoresViviendas, alumbradoViviendas, otrosServiciosViviendas, garajes, cargasServiciosViviendas;
    private double superficieUtil, potEspecPrevista, cargasLocalesViviendas, cargasTotalesViviendas;

    //Pestaña Prev oficinas
    private double supTotalOficinas, supTotalEstabOficinas, demandaOficinas, demandaEstabOficinas;
    private String numTotalOficinas, numTotalEstabOficinas;
    private double ascensoresOficinas, alumbradoOficinas, otrosServOficinas;
    private String descripcionOficinas;
    private double potPrevistaOficinas;
    private double cargasTotalesOficinas;

    //Texto errores pestañas
    private String txtErrorPrevIndustriales;
    private String txtErrorPrevOficinas;
    private String txtErrorPrevViviendas;
    private String txtErrorNuevaInstGeneral;
    private String txtErrorPrevisiones;
    private String txtError;

    private String idInstalacion;
    private String consulta;

    private DefaultComboBoxModel m1, m2, m3, mTemp;

    /**
     * Creates new form FrmNuevaInst
     */
    public FrmNuevaInst() {
        bd = Sesion.getInstance().getBaseDatos();
        initComponents();
        rellenarInfo();
        establecerRestricciones();
        insertarRegistroInicial();
        setModeloTablaPrevision();
        agregarTensionesACombos();
    }

    public void establecerRestricciones() {
        jTxtFldNombreInst.setDocument(new LimitadorCaracteres(jTxtFldNombreInst, 100, false, false));
        jTxtFldTension1.setDocument(new LimitadorCaracteres(jTxtFldTension1,7, true, false));
        jTxtFldTension2.setDocument(new LimitadorCaracteres(jTxtFldTension2,7, true, false));
        jTxtFldVariosSinClasificar.setDocument(new LimitadorCaracteres(jTxtFldVariosSinClasificar,50, false, false));
        jTxtFldSuperficieLocal.setDocument(new LimitadorCaracteres(jTxtFldSuperficieLocal, 10, true, true));

        //Pestaña Prevision cargas edificios viviendas
        jTxtFldNumViv1.setDocument(new LimitadorCaracteres(jTxtFldNumViv1, 4, true, false));
        jTxtFldNumViv2.setDocument(new LimitadorCaracteres(jTxtFldNumViv2, 4, true, false));
        jTxtFldSuperficieUnit1.setDocument(new LimitadorCaracteres(jTxtFldSuperficieUnit1, 10, true, true));
        jTxtFldSuperficieUnit2.setDocument(new LimitadorCaracteres(jTxtFldSuperficieUnit2, 10, true, true));
        jTxtFldDemandaMax1.setDocument(new LimitadorCaracteres(jTxtFldDemandaMax1, 10, true, true));
        jTxtFldDemandaMax2.setDocument(new LimitadorCaracteres(jTxtFldDemandaMax2, 10, true, true));
        jTxtFldCargasPrevistasViviendas.setDocument(new LimitadorCaracteres(jTxtFldCargasPrevistasViviendas, 10, true, true));
        jTxtFldAscensoresViviendas.setDocument(new LimitadorCaracteres(jTxtFldAscensoresViviendas, 10, true, true));
        jTxtFldAlumbradoEscalera.setDocument(new LimitadorCaracteres(jTxtFldAlumbradoEscalera, 10, true, true));
        jTxtFldOtrosServiciosViviendas.setDocument(new LimitadorCaracteres(jTxtFldOtrosServiciosViviendas, 10, true, true));
        jTxtFldCargasServiciosViviendas.setDocument(new LimitadorCaracteres(jTxtFldCargasServiciosViviendas, 10, true, true));
        jTxtFldSuperficieUtilTotal.setDocument(new LimitadorCaracteres(jTxtFldSuperficieUtilTotal, 10, true, true));
        jTxtFldPotEspecPrevista.setDocument(new LimitadorCaracteres(jTxtFldPotEspecPrevista, 10, true, true));
        jTxtFldCargasLocalesViviendas.setDocument(new LimitadorCaracteres(jTxtFldCargasLocalesViviendas, 10, true, true));
        jTxtFldCargasTotalesViviendas.setDocument(new LimitadorCaracteres(jTxtFldCargasTotalesViviendas, 10, true, true));

        //Pestaña Prevision cargas edificios oficinas
        jTxtFldNumTotalOficinas.setDocument(new LimitadorCaracteres(jTxtFldNumTotalOficinas, 4, true, false));
        jTxtFldNumTotalEstabOficinas.setDocument(new LimitadorCaracteres(jTxtFldNumTotalEstabOficinas, 4, true, false));
        jTxtFldSuperficieOficinas.setDocument(new LimitadorCaracteres(jTxtFldSuperficieOficinas, 10, true, true));
        jTxtFldSuperficieTotalEstabOficinas.setDocument(new LimitadorCaracteres(jTxtFldSuperficieTotalEstabOficinas, 10, true, true));
        jTxtFldDemandaOficinas.setDocument(new LimitadorCaracteres(jTxtFldDemandaOficinas, 10, true, true));
        jTxtFldDemandaEstabOficinas.setDocument(new LimitadorCaracteres(jTxtFldDemandaEstabOficinas, 10, true, true));
        jTxtFldAscensoresOficinas.setDocument(new LimitadorCaracteres(jTxtFldAscensoresOficinas, 10, true, true));
        jTxtFldAlumbradoOficinas.setDocument(new LimitadorCaracteres(jTxtFldAlumbradoOficinas, 10, true, true));
        jTxtFldOtrosServOficinas.setDocument(new LimitadorCaracteres(jTxtFldOtrosServOficinas, 10, true, true));
        jTxtFldDescripcionOficinas.setDocument(new LimitadorCaracteres(jTxtFldDescripcionOficinas, 50, false, false));
        jTxtFldPotPrevistaOficinas.setDocument(new LimitadorCaracteres(jTxtFldPotPrevistaOficinas, 10, true, true));
        jTxtFldCargasTotalesOficinas.setDocument(new LimitadorCaracteres(jTxtFldCargasTotalesOficinas, 10, true, true));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBttnAceptar = new javax.swing.JButton();
        jBttnMenuPrincipal = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jTxtFldVariosSinClasificar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTxtFldSuperficieLocal = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jCmbBxReglamentos = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jCmbBxMemoriaPor = new javax.swing.JComboBox();
        jTxtFldNombreInst = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jCmbBxTipoInstalacion = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jCmbBxUsoInstalacion = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jTxtFldTension1 = new javax.swing.JTextField();
        jLabel101 = new javax.swing.JLabel();
        jTxtFldTension2 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jCmbBxPotenciaPrevista = new javax.swing.JComboBox();
        jLabel63 = new javax.swing.JLabel();
        jTbbdPnDatosTecnicos = new javax.swing.JTabbedPane();
        jPnlPrevisionCargasIndustriales = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrTblPrevision = new javax.swing.JScrollPane();
        jTblPrevision = new javax.swing.JTable();
        jBttnPrevisionFilaNueva = new javax.swing.JButton();
        jBttnPrevisionFilaBorrar = new javax.swing.JButton();
        jTxtFldSumaPotencia = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPnlPrevisionCargasEdificios = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jTxtFldPotEspecPrevista = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jTxtFldCargasLocalesViviendas = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        jTxtFldSuperficieUtilTotal = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jTxtFldAscensoresViviendas = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jTxtFldOtrosServiciosViviendas = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jTxtFldCargasServiciosViviendas = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        jTxtFldAlumbradoEscalera = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        jTxtFldGarajes = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jCmbBxGradoElect1 = new javax.swing.JComboBox();
        jLabel65 = new javax.swing.JLabel();
        jCmbBxGradoElect2 = new javax.swing.JComboBox();
        jLabel66 = new javax.swing.JLabel();
        jTxtFldNumViv1 = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jTxtFldNumViv2 = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jTxtFldSuperficieUnit1 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jTxtFldDemandaMax1 = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jTxtFldDemandaMax2 = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jTxtFldSuperficieUnit2 = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        jTxtFldCargasPrevistasViviendas = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jTxtFldCargasTotalesViviendas = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        jPnlPrevisionCargasOficinas = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jTxtFldNumTotalOficinas = new javax.swing.JTextField();
        jTxtFldNumTotalEstabOficinas = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jTxtFldSuperficieTotalEstabOficinas = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        jTxtFldSuperficieOficinas = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jTxtFldDemandaOficinas = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jTxtFldDemandaEstabOficinas = new javax.swing.JTextField();
        jLabel104 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        jTxtFldAlumbradoOficinas = new javax.swing.JTextField();
        jTxtFldAscensoresOficinas = new javax.swing.JTextField();
        jLabel106 = new javax.swing.JLabel();
        jTxtFldOtrosServOficinas = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel111 = new javax.swing.JLabel();
        jTxtFldDescripcionOficinas = new javax.swing.JTextField();
        jLabel112 = new javax.swing.JLabel();
        jTxtFldPotPrevistaOficinas = new javax.swing.JTextField();
        jLabel113 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel118 = new javax.swing.JLabel();
        jTxtFldCargasTotalesOficinas = new javax.swing.JTextField();
        jLabel119 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Nueva instalación");
        setIconImage((new ImageIcon(getClass().getResource("/resources/icons/sibol.gif"))).getImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jBttnAceptar.setText("ACEPTAR");
        jBttnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBttnAceptarActionPerformed(evt);
            }
        });

        jBttnMenuPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/tofront.gif"))); // NOI18N
        jBttnMenuPrincipal.setText("MENÚ PRINCIPAL");
        jBttnMenuPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBttnMenuPrincipalActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de la nueva instalación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel17.setText("Varios sin clasificar");

        jLabel15.setText("Superficie local*");

        jTxtFldSuperficieLocal.setText("4");

        jLabel16.setText("m2");

        jLabel11.setText("Reglamento");

        jCmbBxReglamentos.setEditable(true);

        jLabel9.setText("Memoria por*");

        jCmbBxMemoriaPor.setEditable(true);

        jLabel5.setText("Nombre de la instalación*");

        jCmbBxTipoInstalacion.setEditable(true);
        jCmbBxTipoInstalacion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbBxTipoInstalacionItemStateChanged(evt);
            }
        });

        jLabel1.setText("Tipo de instalación");

        jLabel14.setText("Uso instalación*");

        jCmbBxUsoInstalacion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbBxUsoInstalacionItemStateChanged(evt);
            }
        });

        jLabel4.setText("Tensión*");

        jTxtFldTension1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldTension1KeyReleased(evt);
            }
        });

        jLabel101.setText("/");

        jTxtFldTension2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldTension2KeyReleased(evt);
            }
        });

        jLabel60.setText("V");

        jLabel7.setText("Potencia prevista*");

        jCmbBxPotenciaPrevista.setEditable(true);

        jLabel63.setText("W");

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jTxtFldNombreInst, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 380, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(jLabel5)
                        .add(jPanel3Layout.createSequentialGroup()
                            .add(jLabel9)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jCmbBxMemoriaPor, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldTension1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel101)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldTension2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel7)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jCmbBxPotenciaPrevista, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 91, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jLabel15)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldSuperficieLocal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel16)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jLabel11)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jCmbBxReglamentos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 128, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel3Layout.createSequentialGroup()
                            .add(jLabel17)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jTxtFldVariosSinClasificar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .add(317, 317, 317))
                        .add(jPanel3Layout.createSequentialGroup()
                            .add(jLabel1)
                            .addContainerGap(444, Short.MAX_VALUE))
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jCmbBxTipoInstalacion, 0, 456, Short.MAX_VALUE))
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3Layout.createSequentialGroup()
                                    .add(jLabel14)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jCmbBxUsoInstalacion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 286, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(76, 76, 76)))))
        );

        jPanel3Layout.linkSize(new java.awt.Component[] {jTxtFldTension1, jTxtFldTension2}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jLabel5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jTxtFldNombreInst, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jCmbBxTipoInstalacion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jLabel1)
                        .add(28, 28, 28)))
                .add(13, 13, 13)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel9)
                    .add(jCmbBxMemoriaPor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel14)
                    .add(jCmbBxUsoInstalacion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(jTxtFldTension1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldTension2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel101)
                    .add(jLabel60)
                    .add(jLabel7)
                    .add(jCmbBxPotenciaPrevista, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel63)
                    .add(jLabel17)
                    .add(jTxtFldVariosSinClasificar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel15)
                    .add(jTxtFldSuperficieLocal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel16)
                    .add(jCmbBxReglamentos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel11))
                .add(104, 104, 104))
        );

        jTbbdPnDatosTecnicos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Previsiones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("PREVISIÓN DE CARGAS INDUSTRIALES, AGRARIAS, DE SERVICIOS, ETC"));

        jTblPrevision.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo", "Denominación", "Potencia (W)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTblPrevision.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrTblPrevision.setViewportView(jTblPrevision);

        jBttnPrevisionFilaNueva.setText("Nuevo");
        jBttnPrevisionFilaNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBttnPrevisionFilaNuevaActionPerformed(evt);
            }
        });

        jBttnPrevisionFilaBorrar.setText("Borrar");
        jBttnPrevisionFilaBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBttnPrevisionFilaBorrarActionPerformed(evt);
            }
        });

        jTxtFldSumaPotencia.setEditable(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel2.setText("Potencia total");

        org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrTblPrevision, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(jBttnPrevisionFilaNueva, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 71, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jBttnPrevisionFilaBorrar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 102, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 433, Short.MAX_VALUE)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldSumaPotencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 133, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel9Layout.linkSize(new java.awt.Component[] {jBttnPrevisionFilaBorrar, jBttnPrevisionFilaNueva}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel9Layout.createSequentialGroup()
                .add(jScrTblPrevision, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(jBttnPrevisionFilaNueva, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jBttnPrevisionFilaBorrar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jTxtFldSumaPotencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel2)))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPnlPrevisionCargasIndustrialesLayout = new org.jdesktop.layout.GroupLayout(jPnlPrevisionCargasIndustriales);
        jPnlPrevisionCargasIndustriales.setLayout(jPnlPrevisionCargasIndustrialesLayout);
        jPnlPrevisionCargasIndustrialesLayout.setHorizontalGroup(
            jPnlPrevisionCargasIndustrialesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlPrevisionCargasIndustrialesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPnlPrevisionCargasIndustrialesLayout.setVerticalGroup(
            jPnlPrevisionCargasIndustrialesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlPrevisionCargasIndustrialesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTbbdPnDatosTecnicos.addTab("<html>Prevision cargas industriales,<br>agrarias, servicios...</html>", jPnlPrevisionCargasIndustriales);

        jPanel41.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("LOCALES COMERCIALES Y OFICINAS"));

        jLabel85.setText("Superficie útil total");

        jLabel86.setText("m2");

        jLabel87.setText("Pot. específica prevista");

        jTxtFldPotEspecPrevista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldPotEspecPrevistaKeyReleased(evt);
            }
        });

        jLabel88.setText("W/m2");

        jLabel89.setText("CARGAS LOCALES COMERCIALES Y OFICINAS (C)");

        jTxtFldCargasLocalesViviendas.setEditable(false);

        jLabel90.setText("W");

        jTxtFldSuperficieUtilTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldSuperficieUtilTotalKeyReleased(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel19Layout = new org.jdesktop.layout.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel85)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldSuperficieUtilTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel86)
                .add(17, 17, 17)
                .add(jLabel87)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldPotEspecPrevista, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 74, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel88)
                .add(16, 16, 16)
                .add(jLabel89)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldCargasLocalesViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel90)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel85)
                .add(jTxtFldSuperficieUtilTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel86)
                .add(jLabel87)
                .add(jLabel89)
                .add(jTxtFldCargasLocalesViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel90)
                .add(jTxtFldPotEspecPrevista, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel88))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("SERVICIOS GENERALES"));

        jLabel77.setText("Ascensores");

        jTxtFldAscensoresViviendas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldAscensoresViviendasKeyReleased(evt);
            }
        });

        jLabel78.setText("W");

        jLabel79.setText("W");

        jLabel80.setText("Alumbrado escalera");

        jLabel81.setText("Otros servicios");

        jTxtFldOtrosServiciosViviendas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldOtrosServiciosViviendasKeyReleased(evt);
            }
        });

        jLabel82.setText("W");

        jLabel84.setText("W");

        jTxtFldCargasServiciosViviendas.setEditable(false);

        jLabel83.setText("CARGAS PREVISTAS EN SERVICIOS GENERALES (B)");

        jTxtFldAlumbradoEscalera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldAlumbradoEscaleraKeyReleased(evt);
            }
        });

        jLabel102.setText("Garajes");

        jTxtFldGarajes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldGarajesKeyReleased(evt);
            }
        });

        jLabel103.setText("W");

        org.jdesktop.layout.GroupLayout jPanel18Layout = new org.jdesktop.layout.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel18Layout.createSequentialGroup()
                        .add(jLabel77)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldAscensoresViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel78)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 30, Short.MAX_VALUE)
                        .add(jLabel80))
                    .add(jLabel83))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jTxtFldCargasServiciosViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 73, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldAlumbradoEscalera, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel18Layout.createSequentialGroup()
                        .add(jLabel79)
                        .add(25, 25, 25)
                        .add(jLabel81)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldOtrosServiciosViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel82)
                        .add(73, 73, 73)
                        .add(jLabel102)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldGarajes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(4, 4, 4)
                        .add(jLabel103))
                    .add(jLabel84))
                .addContainerGap(107, Short.MAX_VALUE))
        );

        jPanel18Layout.linkSize(new java.awt.Component[] {jTxtFldAscensoresViviendas, jTxtFldCargasServiciosViviendas, jTxtFldGarajes, jTxtFldOtrosServiciosViviendas}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel18Layout.createSequentialGroup()
                .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel77)
                        .add(jTxtFldAscensoresViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel78)
                        .add(jLabel80)
                        .add(jLabel79)
                        .add(jTxtFldAlumbradoEscalera, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jTxtFldOtrosServiciosViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel82)
                        .add(jLabel81)
                        .add(jLabel102)
                        .add(jTxtFldGarajes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel103)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel83)
                    .add(jTxtFldCargasServiciosViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel84)))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("VIVIENDAS"));

        jLabel59.setText("Grado electrificación");

        jLabel65.setText("Grado electrificación");

        jLabel66.setText("Nº de viviendas");

        jTxtFldNumViv1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldNumViv1KeyReleased(evt);
            }
        });

        jLabel67.setText("m2");

        jLabel68.setText("Nº de viviendas");

        jTxtFldNumViv2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldNumViv2KeyReleased(evt);
            }
        });

        jLabel69.setText("Superficie unitaria");

        jLabel70.setText("Superficie unitaria");

        jLabel71.setText("m2");

        jLabel72.setText("Demanda máx/vivienda");

        jTxtFldDemandaMax1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldDemandaMax1KeyReleased(evt);
            }
        });

        jLabel73.setText("W");

        jLabel74.setText("Demanda máx/vivienda");

        jTxtFldDemandaMax2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldDemandaMax2KeyReleased(evt);
            }
        });

        jLabel75.setText("W");

        jLabel91.setText("CARGAS PREVISTAS EN VIVIENDAS (A)");

        jTxtFldCargasPrevistasViviendas.setEditable(false);

        jLabel92.setText("W");

        org.jdesktop.layout.GroupLayout jPanel17Layout = new org.jdesktop.layout.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel17Layout.createSequentialGroup()
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jLabel65)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jCmbBxGradoElect2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jLabel59)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jCmbBxGradoElect1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(19, 19, 19)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jLabel66)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTxtFldNumViv1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jLabel68)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTxtFldNumViv2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)))
                        .add(15, 15, 15)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel69)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel70))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jTxtFldSuperficieUnit2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                            .add(jTxtFldSuperficieUnit1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel67)
                            .add(jLabel71))
                        .add(16, 16, 16)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jLabel74)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTxtFldDemandaMax2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jLabel72)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTxtFldDemandaMax1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel75)
                            .add(jLabel73)))
                    .add(jPanel17Layout.createSequentialGroup()
                        .add(jLabel91)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldCargasPrevistasViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel92)))
                .addContainerGap(109, Short.MAX_VALUE))
        );

        jPanel17Layout.linkSize(new java.awt.Component[] {jTxtFldCargasPrevistasViviendas, jTxtFldNumViv2}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel17Layout.createSequentialGroup()
                .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel17Layout.createSequentialGroup()
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel59)
                            .add(jCmbBxGradoElect1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel65)
                            .add(jCmbBxGradoElect2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel66)
                        .add(jTxtFldNumViv1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel17Layout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel68)
                            .add(jTxtFldNumViv2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel69)
                        .add(jTxtFldSuperficieUnit1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel67))
                    .add(jPanel17Layout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel70)
                            .add(jTxtFldSuperficieUnit2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel71)))
                    .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel72)
                        .add(jTxtFldDemandaMax1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel17Layout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel74)
                            .add(jTxtFldDemandaMax2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel75)))
                    .add(jLabel73))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldCargasPrevistasViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel92)
                    .add(jLabel91)))
        );

        jPanel42.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel93.setText("CARGAS TOTALES PREVISTAS EN EL EDIFICIO   (A+B+C)");

        jTxtFldCargasTotalesViviendas.setEditable(false);

        jLabel94.setText("W");

        org.jdesktop.layout.GroupLayout jPanel42Layout = new org.jdesktop.layout.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel93, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(40, 40, 40)
                .add(jTxtFldCargasTotalesViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 85, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel94, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(472, 472, 472))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
            .add(jLabel93, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
            .add(jTxtFldCargasTotalesViviendas, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
            .add(jLabel94, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout jPanel41Layout = new org.jdesktop.layout.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel41Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel18, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel17, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel19, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel42, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel41Layout.createSequentialGroup()
                .add(jPanel17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        org.jdesktop.layout.GroupLayout jPnlPrevisionCargasEdificiosLayout = new org.jdesktop.layout.GroupLayout(jPnlPrevisionCargasEdificios);
        jPnlPrevisionCargasEdificios.setLayout(jPnlPrevisionCargasEdificiosLayout);
        jPnlPrevisionCargasEdificiosLayout.setHorizontalGroup(
            jPnlPrevisionCargasEdificiosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlPrevisionCargasEdificiosLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel41, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPnlPrevisionCargasEdificiosLayout.setVerticalGroup(
            jPnlPrevisionCargasEdificiosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlPrevisionCargasEdificiosLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTbbdPnDatosTecnicos.addTab("<html>Prevision cargas<br>edificios viviendas</html>", jPnlPrevisionCargasEdificios);

        jPanel44.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("OFICINAS / ESTABLECIMIENTOS"));

        jLabel54.setText("Nº Total Oficinas");

        jLabel55.setText("Nº Total Estab. Indus");

        jTxtFldNumTotalOficinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldNumTotalOficinasKeyReleased(evt);
            }
        });

        jTxtFldNumTotalEstabOficinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldNumTotalEstabOficinasKeyReleased(evt);
            }
        });

        jLabel56.setText("Superficie total oficinas");

        jLabel95.setText("Superficie total estab. Indus.");

        jLabel96.setText("m2");

        jLabel97.setText("m2");

        jLabel98.setText("Demanda máx/oficinas");

        jTxtFldDemandaOficinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldDemandaOficinasKeyReleased(evt);
            }
        });

        jLabel99.setText("W");

        jLabel100.setText("Demanda máx/Estab. Indus.");

        jTxtFldDemandaEstabOficinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldDemandaEstabOficinasKeyReleased(evt);
            }
        });

        jLabel104.setText("W");

        org.jdesktop.layout.GroupLayout jPanel11Layout = new org.jdesktop.layout.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel11Layout.createSequentialGroup()
                        .add(jLabel54)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldNumTotalOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel11Layout.createSequentialGroup()
                        .add(jLabel55)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldNumTotalEstabOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(19, 19, 19)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel11Layout.createSequentialGroup()
                        .add(jLabel56)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldSuperficieOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel96))
                    .add(jPanel11Layout.createSequentialGroup()
                        .add(jLabel95)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldSuperficieTotalEstabOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel97)))
                .add(25, 25, 25)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel100)
                    .add(jLabel98))
                .add(10, 10, 10)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jTxtFldDemandaOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDemandaEstabOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel104)
                    .add(jLabel99))
                .addContainerGap(142, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel11Layout.createSequentialGroup()
                        .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jTxtFldNumTotalOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel54))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel55)
                            .add(jTxtFldNumTotalEstabOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel95)
                            .add(jTxtFldSuperficieTotalEstabOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel97)
                            .add(jLabel100)
                            .add(jTxtFldDemandaEstabOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel104)))
                    .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jTxtFldSuperficieOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel96)
                        .add(jLabel56)
                        .add(jTxtFldDemandaOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel99)
                        .add(jLabel98)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("SERVICIOS GENERALES"));

        jLabel105.setText("Ascensores");

        jTxtFldAlumbradoOficinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldAlumbradoOficinasKeyReleased(evt);
            }
        });

        jTxtFldAscensoresOficinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldAscensoresOficinasKeyReleased(evt);
            }
        });

        jLabel106.setText("Alumbrado escalera");

        jTxtFldOtrosServOficinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldOtrosServOficinasKeyReleased(evt);
            }
        });

        jLabel107.setText("W");

        jLabel108.setText("W");

        jLabel109.setText("Otros servicios");

        jLabel110.setText("W");

        org.jdesktop.layout.GroupLayout jPanel16Layout = new org.jdesktop.layout.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel16Layout.createSequentialGroup()
                .add(57, 57, 57)
                .add(jLabel105)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldAscensoresOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel110)
                .add(53, 53, 53)
                .add(jLabel106)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldAlumbradoOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel107)
                .add(101, 101, 101)
                .add(jLabel109)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldOtrosServOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel108)
                .addContainerGap(141, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel16Layout.createSequentialGroup()
                .add(jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel110)
                    .add(jTxtFldAscensoresOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel105)
                    .add(jLabel107)
                    .add(jTxtFldAlumbradoOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel106)
                    .add(jLabel108)
                    .add(jTxtFldOtrosServOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel109))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("OTRAS CARGAS"));

        jLabel111.setText("Potencia prevista");

        jLabel112.setText("Descripción");

        jTxtFldPotPrevistaOficinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldPotPrevistaOficinasKeyReleased(evt);
            }
        });

        jLabel113.setText("W");

        org.jdesktop.layout.GroupLayout jPanel22Layout = new org.jdesktop.layout.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel112)
                .add(52, 52, 52)
                .add(jTxtFldDescripcionOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 365, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(53, 53, 53)
                .add(jLabel111)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldPotPrevistaOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel113)
                .addContainerGap(141, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel22Layout.createSequentialGroup()
                .add(jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldDescripcionOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel112)
                    .add(jLabel111)
                    .add(jTxtFldPotPrevistaOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel113))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel118.setText("CARGAS TOTALES PREVISTAS EN EL EDIFICIO");

        jTxtFldCargasTotalesOficinas.setEditable(false);

        jLabel119.setText("W");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel118)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldCargasTotalesOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(524, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel118)
                    .add(jTxtFldCargasTotalesOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel119))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel44Layout = new org.jdesktop.layout.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel44Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel16, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel22, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel44Layout.createSequentialGroup()
                .add(jPanel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPnlPrevisionCargasOficinasLayout = new org.jdesktop.layout.GroupLayout(jPnlPrevisionCargasOficinas);
        jPnlPrevisionCargasOficinas.setLayout(jPnlPrevisionCargasOficinasLayout);
        jPnlPrevisionCargasOficinasLayout.setHorizontalGroup(
            jPnlPrevisionCargasOficinasLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlPrevisionCargasOficinasLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel44, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPnlPrevisionCargasOficinasLayout.setVerticalGroup(
            jPnlPrevisionCargasOficinasLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlPrevisionCargasOficinasLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel44, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTbbdPnDatosTecnicos.addTab("<html>Prevision cargas<br>edificios oficinas</html>", jPnlPrevisionCargasOficinas);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jTbbdPnDatosTecnicos, 0, 0, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jBttnAceptar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 126, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jBttnMenuPrincipal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 158, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 185, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTbbdPnDatosTecnicos, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jBttnMenuPrincipal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jBttnAceptar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCmbBxUsoInstalacionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbBxUsoInstalacionItemStateChanged
        try{
            pcUsoInstalacion = (ParCombo)jCmbBxUsoInstalacion.getSelectedItem();

            if(pcUsoInstalacion.getDescription().equalsIgnoreCase("Varios sin clasificar"))
            {
                jTxtFldVariosSinClasificar.setText("");
                jTxtFldVariosSinClasificar.setEditable(true);
            }
            else{
                jTxtFldVariosSinClasificar.setText("");
                jTxtFldVariosSinClasificar.setEditable(false);
            }
        }
        catch(NullPointerException e){

        }
    }//GEN-LAST:event_jCmbBxUsoInstalacionItemStateChanged

    private void jTxtFldSuperficieUtilTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldSuperficieUtilTotalKeyReleased
        calcularPrevCargasViviendasC();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldSuperficieUtilTotalKeyReleased

    private void jTxtFldNumTotalEstabOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldNumTotalEstabOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldNumTotalEstabOficinasKeyReleased

    private void jTxtFldNumTotalOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldNumTotalOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldNumTotalOficinasKeyReleased

    private void jTxtFldNumViv2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldNumViv2KeyReleased
        calcularPrevCargasViviendasA();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldNumViv2KeyReleased

    private void jTxtFldNumViv1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldNumViv1KeyReleased
        calcularPrevCargasViviendasA();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldNumViv1KeyReleased

    private void jTxtFldTension2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldTension2KeyReleased
        String tension1 = jTxtFldTension1.getText();
        String tension2 = jTxtFldTension2.getText();

        if(tension1.equals("230") && tension2.equals(""))
            jCmbBxPotenciaPrevista.setModel(m1);
        else if(tension1.equals("230") && tension2.equals("130"))
            jCmbBxPotenciaPrevista.setModel(m2);
        else if(tension1.equals("400") && tension2.equals("230"))
            jCmbBxPotenciaPrevista.setModel(m3);
        else
            jCmbBxPotenciaPrevista.setModel(mTemp);
    }//GEN-LAST:event_jTxtFldTension2KeyReleased

    private void jTxtFldTension1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldTension1KeyReleased
        String tension1 = jTxtFldTension1.getText();
        String tension2 = jTxtFldTension2.getText();

        if(tension1.equals("230") && tension2.equals(""))
            jCmbBxPotenciaPrevista.setModel(m1);
        else if(tension1.equals("230") && tension2.equals("130"))
            jCmbBxPotenciaPrevista.setModel(m2);
        else if(tension1.equals("400") && tension2.equals("230"))
            jCmbBxPotenciaPrevista.setModel(m3);
        else
            jCmbBxPotenciaPrevista.setModel(mTemp);
    }//GEN-LAST:event_jTxtFldTension1KeyReleased

    private void jTxtFldDemandaMax1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldDemandaMax1KeyReleased
        calcularPrevCargasViviendasA();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldDemandaMax1KeyReleased

    private void jTxtFldDemandaMax2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldDemandaMax2KeyReleased
        calcularPrevCargasViviendasA();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldDemandaMax2KeyReleased

    private void jTxtFldAscensoresViviendasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldAscensoresViviendasKeyReleased
        calcularPrevCargasViviendasB();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldAscensoresViviendasKeyReleased

    private void jTxtFldOtrosServiciosViviendasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldOtrosServiciosViviendasKeyReleased
        calcularPrevCargasViviendasB();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldOtrosServiciosViviendasKeyReleased

    private void jTxtFldAlumbradoEscaleraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldAlumbradoEscaleraKeyReleased
        calcularPrevCargasViviendasB();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldAlumbradoEscaleraKeyReleased

    private void jTxtFldGarajesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldGarajesKeyReleased
        calcularPrevCargasViviendasB();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldGarajesKeyReleased

    private void jTxtFldPotEspecPrevistaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldPotEspecPrevistaKeyReleased
        calcularPrevCargasViviendasC();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldPotEspecPrevistaKeyReleased

    private void jTxtFldPotPrevistaOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldPotPrevistaOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldPotPrevistaOficinasKeyReleased

    private void jTxtFldOtrosServOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldOtrosServOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldOtrosServOficinasKeyReleased

    private void jTxtFldAscensoresOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldAscensoresOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldAscensoresOficinasKeyReleased

    private void jTxtFldAlumbradoOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldAlumbradoOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldAlumbradoOficinasKeyReleased

    private void jTxtFldDemandaEstabOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldDemandaEstabOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldDemandaEstabOficinasKeyReleased

    private void jTxtFldDemandaOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldDemandaOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldDemandaOficinasKeyReleased

    private void jBttnPrevisionFilaBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBttnPrevisionFilaBorrarActionPerformed
        try{
            int numCols[] = jTblPrevision.getSelectedRows();
            if(numCols.length > 0)
            {
                JOptionPane optionPane=new JOptionPane();
                Object[] opciones={"Aceptar","Cancelar"};
                int ret=optionPane.showOptionDialog(null,"¿CONFIRMA QUE DESEA ELIMINAR LA FILA O FILAS SELECCIONADAS?","Confirmar elimnación de filas.",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opciones,opciones[0]);

                if(ret==JOptionPane.YES_OPTION)
                {
                    for(int i=numCols.length-1; i>=0; i--){
                        int fila = numCols[i];
                        String id = modeloTablaPrevision.getValueAt(fila,4).toString();
                        controlPrevision.borraFila(fila);
                        bd.ejecModificacion("DELETE FROM CARGAS_INDUSTRIALES WHERE CIID = "+id);
                    }
                }
            }
            else
                Mensaje.aviso("Seleccione alguna registro tras haber introducido algún criterio de búsqueda");

        } catch(SQLException e){
            Mensaje.error("Error en la eliminación de los registros: "+e.getMessage(),e);
        } catch(IndexOutOfBoundsException e){
            Mensaje.aviso("No existen registros seleccionados para borrar.");
        }
    }//GEN-LAST:event_jBttnPrevisionFilaBorrarActionPerformed

    private void jBttnPrevisionFilaNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBttnPrevisionFilaNuevaActionPerformed
        if(esValidoTablaPrevision())
            controlPrevision.anhadeFila();
    }//GEN-LAST:event_jBttnPrevisionFilaNuevaActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{
            consulta = "DELETE FROM INSTALACIONES WHERE INID="+idInstalacion;
            bd.ejecModificacion(consulta);
        }
        catch(SQLException e){
            Sesion.getInstance().reset();
        }
        finally{
            Sesion.getInstance().cerrarAplicacion();
        }
    }//GEN-LAST:event_formWindowClosing

    private void jBttnMenuPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBttnMenuPrincipalActionPerformed
        try{
            consulta = "DELETE FROM INSTALACIONES WHERE INID="+idInstalacion;
            bd.ejecModificacion(consulta);
        }
        catch(SQLException e){
            Sesion.getInstance().reset();
        }
        finally{
            this.setVisible(false);
            JFrame frm = Sesion.getInstance().getFrmEntrada();
            Utilidades.igualarFormularios(frm, this);
            frm.setVisible(true);
        }
    }//GEN-LAST:event_jBttnMenuPrincipalActionPerformed

    private void jCmbBxTipoInstalacionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbBxTipoInstalacionItemStateChanged
        try{
            comprobarTipoInstalacion();
        } catch(Exception e){
            Mensaje.aviso("Error al cambiar de pestaña: "+e.getMessage());
        }
    }//GEN-LAST:event_jCmbBxTipoInstalacionItemStateChanged

    private void jBttnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBttnAceptarActionPerformed
        esSinProyecto = false;
        txtError = "";

        try{
            boolean esValidoNuevaInst = esValidoDatosIniciales();
            boolean esValidoPrevision = esValidoPrevisiones();
            boolean esBloqueViviendas = esBloqueViviendas();

            if(esValidoNuevaInst && esValidoPrevision)
            {
                consulta = "UPDATE INSTALACIONES SET INFECHA='"+fecha+"', INDESC="+UtilidadesSQL.tratarParametroString(jTxtFldNombreInst.getText())+",INTICOD='"+tiCod+"', INTINUM="+tiNum+" WHERE INID="+idInstalacion;
                bd.ejecModificacion(consulta);

                if(!esSinProyecto)
                        Mensaje.info("Sepa que este tipo de instalación requerirá PROYECTO, DIRECCIÓN DE OBRA y en algunos casos, OCA");
                if(esBloqueViviendas)
                        Mensaje.info("Ud. va a desarrollar el esquema de una vivienda tipo del bloque de viviendas. \nEn caso de tener alguna vivienda cuya electrificación no corresponda con el de la vivienda deberá tratarlas aparte.");

                Sesion.getInstance().setValorHt(Constantes.SES_ES_CON_BLOQUE_VIVIENDAS,esBloqueViviendas);
                Sesion.getInstance().setValorHt(Constantes.SES_ES_SIN_PROYECTO,esSinProyecto);
                Sesion.getInstance().setValorHt(Constantes.SES_POTENCIA_PREVISTA,new Double(potencia));
                Sesion.getInstance().setValorHt(Constantes.SES_TENSION1, jTxtFldTension1.getText());
                Sesion.getInstance().setValorHt(Constantes.SES_TENSION2, jTxtFldTension2.getText());

                //Actualizar la BD con el registro creado.
                insertarBDIniciales();
                insertarBDPrevIndustriales();
                insertarBDPrevViviendas();
                insertarBDPrevOficinas();

                EstablecerDatos establecerDatos = new EstablecerDatos(Integer.parseInt(idInstalacion));
                establecerDatos.setInfoPrevisionIndustrialesDatosTecn();
                establecerDatos.setInfoPrevCargasViviendasDatosTecn();
                establecerDatos.setInfoPrevCargasOficinasDatosTecn();
                establecerDatos.setVisibleFrmPrincipal(true, this);

                this.setVisible(false);
            }
            else
            {
                txtError += txtErrorNuevaInstGeneral + txtErrorPrevisiones;
                Mensaje.error(txtError);
            }
        }
        catch(SQLException e){
                Mensaje.error("FrmNuevaInst.java: "+e.getMessage(), e);
        }
    }//GEN-LAST:event_jBttnAceptarActionPerformed

    private boolean esValidoTablaPrevision(){
        String strPotencia = null;

        try{
           for(int i=0; i<jTblPrevision.getRowCount(); i++)
               strPotencia = Utilidades.cambiarComa(modeloTablaPrevision.getValueAt(i,2).toString());

           return true;
       }
       catch(NumberFormatException e){
           Mensaje.error("Problemas con formato numeros en la tabla Prevision: "+e.getMessage());
           return false;
       }
       catch(Exception e){
           Mensaje.error("Problemas genéricos en la tabla Previsión: "+e.getMessage());
           return false;
       }
    }

    //Mira si el JTxtFld de POTENCIA debe ser editable según la instalación elegida.
    private void comprobarTipoInstalacion() throws SQLException {
        //Restablecemos las variables globales que hacen referencia a la tabla TIPO_INSTALACION
        String consulta;
        ResultSet rs = null;
        pcTipoInstElegida = (ParCombo)jCmbBxTipoInstalacion.getSelectedItem();
        Sesion.getInstance().setValorHt(Constantes.SES_TIPO_INSTALACION_ELEGIDA,pcTipoInstElegida);
        tiCod = pcTipoInstElegida.getKeyString();
        descTipoInst = pcTipoInstElegida.getDescription();
        tiNum = pcTipoInstElegida.getKeyInt();
        tiLim = -1;

        consulta = "SELECT * from TIPOS_INSTALACION where TICOD='"+tiCod+"' AND TINUM="+tiNum;
        rs = bd.ejecSelect(consulta);
        rs.next();
        tiLim = rs.getInt("TILIM");
        if(tiCod.equalsIgnoreCase("G") || tiCod.equalsIgnoreCase("H"))
        {
            this.jCmbBxPotenciaPrevista.setModel(mTemp);
            this.jCmbBxPotenciaPrevista.setEnabled(false);
            potencia = -1;
        }
        else
        {
            this.jCmbBxPotenciaPrevista.setEnabled(true);
        }

        jCmbBxUsoInstalacion.removeAllItems();

        pcTipoInstElegida = (ParCombo)jCmbBxTipoInstalacion.getSelectedItem();
        consulta = "SELECT * FROM USO_INSTALACION WHERE UITINUM="+tiNum+" AND UITICOD='"+tiCod+"' " +
                   "ORDER BY UIID";

        rs = bd.ejecSelect(consulta);
        while(rs.next())
             jCmbBxUsoInstalacion.addItem(new ParCombo(rs.getInt("UIID"),rs.getString("UIDESC")));
    }

    private void rellenarInfo() {
        ResultSet rs = null;
        try {
            rs = bd.ejecSelect("SELECT * FROM TIPOS_INSTALACION ORDER BY TICOD ASC,TINUM ASC");
            while(rs.next())
                this.jCmbBxTipoInstalacion.addItem(new ParCombo(rs.getString(1), rs.getInt(2), "Grupo "+rs.getString(1)+" - " +rs.getString(3)));
            rs = bd.ejecSelect("SELECT * FROM MOTIVO_MEMORIA ORDER BY MMDESC");
            while(rs.next())
                jCmbBxMemoriaPor.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            jCmbBxMemoriaPor.setSelectedIndex(jCmbBxMemoriaPor.getItemCount() - 1);
            rs = bd.ejecSelect("SELECT * FROM GRADOS_ELECTR ORDER BY GEDESC");
            while(rs.next()) {
                jCmbBxGradoElect1.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
                jCmbBxGradoElect2.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            }
            rs = bd.ejecSelect("SELECT * FROM REGLAMENTOS ORDER BY RGDESC");
            while(rs.next())
                jCmbBxReglamentos.addItem(new ParCombo(rs.getInt(1), rs.getString(2)));
            jCmbBxReglamentos.setSelectedIndex(jCmbBxReglamentos.getItemCount() - 1);

            new AutoComplCmbBxRestrictivo(jCmbBxTipoInstalacion);
            new AutoComplCmbBxRestrictivo(jCmbBxUsoInstalacion);
            new AutoComplCmbBxRestrictivo(jCmbBxMemoriaPor);
            new AutoComplCmbBxRestrictivo(jCmbBxReglamentos);
            comprobarTipoInstalacion();

        }
        catch (SQLException e) {
            Mensaje.error("FrmNuevaInst.java: "+e.getMessage(), e);
        }
    }

    //Comprueba si el elemento seleccionado del ComboBox puede medirse en términos de Potencia
    private boolean admitePotencia(String strTipoInst) {
        return false;
    }

    private void calcularPrevCargasOficinas(){
        double numOfi, numEstab, oficinas, estab, ascensores, alumbrado, otros, potPrevista, result;
        try{
            numOfi      = Double.parseDouble(Utilidades.cambiarComa(jTxtFldNumTotalOficinas.getText()));
            numEstab    = Double.parseDouble(Utilidades.cambiarComa(jTxtFldNumTotalEstabOficinas.getText()));
            oficinas    = Double.parseDouble(Utilidades.cambiarComa(jTxtFldDemandaOficinas.getText()));
            estab       = Double.parseDouble(Utilidades.cambiarComa(jTxtFldDemandaEstabOficinas.getText()));
            ascensores  = Double.parseDouble(Utilidades.cambiarComa(jTxtFldAscensoresOficinas.getText()));
            alumbrado   = Double.parseDouble(Utilidades.cambiarComa(jTxtFldAlumbradoOficinas.getText()));
            otros       = Double.parseDouble(Utilidades.cambiarComa(jTxtFldOtrosServOficinas.getText()));
            potPrevista = Double.parseDouble(Utilidades.cambiarComa(jTxtFldPotPrevistaOficinas.getText()));

            result = (numOfi*oficinas) + (numEstab*estab) + ascensores + alumbrado + otros + potPrevista;
            jTxtFldCargasTotalesOficinas.setText(Utilidades.cambiarPunto(""+result));
        } catch(NumberFormatException e){
            jTxtFldCargasTotalesOficinas.setText("0,0");
        }
    }

    private double calcularPrevCargasViviendasA(){
        double d1, d2, numViv1, numViv2, result;

        try{
            numViv1 = Double.parseDouble(Utilidades.cambiarComa(jTxtFldNumViv1.getText()));

            numViv2 = Double.parseDouble(Utilidades.cambiarComa(jTxtFldNumViv2.getText()));
            d1 = Double.parseDouble(Utilidades.cambiarComa(jTxtFldDemandaMax1.getText()));
            d2 = Double.parseDouble(Utilidades.cambiarComa(jTxtFldDemandaMax2.getText()));

            result = (numViv1*d1) + (numViv2*d2);
            jTxtFldCargasPrevistasViviendas.setText(Utilidades.cambiarPunto(""+result));
            return result;
        }
        catch(NumberFormatException e){
            result = 0;
            jTxtFldCargasPrevistasViviendas.setText(Utilidades.cambiarPunto(""+result));
            jTxtFldCargasTotalesViviendas.setText(Utilidades.cambiarPunto(""+result));
            return result;
        }
    }

    private double calcularPrevCargasViviendasB(){
        double asc, alum, otros, garajes, result;

        try{
            asc = Double.parseDouble(Utilidades.cambiarComa(jTxtFldAscensoresViviendas.getText()));
            alum = Double.parseDouble(Utilidades.cambiarComa(jTxtFldAlumbradoEscalera.getText()));
            otros = Double.parseDouble(Utilidades.cambiarComa(jTxtFldOtrosServiciosViviendas.getText()));
            garajes  = Double.parseDouble(Utilidades.cambiarComa(jTxtFldGarajes.getText()));

            result = asc + alum + otros + garajes;
            jTxtFldCargasServiciosViviendas.setText(Utilidades.cambiarPunto(""+result));

            return result;
        }
        catch(NumberFormatException e){
            result = 0;
            jTxtFldCargasServiciosViviendas.setText(Utilidades.cambiarPunto(""+result));
            jTxtFldCargasTotalesViviendas.setText(Utilidades.cambiarPunto(""+result));
            return result;
        }
    }

    private double calcularPrevCargasViviendasC(){
        double superf, potencia, result;

        try{
            superf = Double.parseDouble(Utilidades.cambiarComa(jTxtFldSuperficieUtilTotal.getText()));
            potencia = Double.parseDouble(Utilidades.cambiarComa(jTxtFldPotEspecPrevista.getText()));
            result = superf * potencia;

            jTxtFldCargasLocalesViviendas.setText(Utilidades.cambiarPunto(""+result));
            return result;
        }
        catch(NumberFormatException e){
            result = 0;
            jTxtFldCargasLocalesViviendas.setText(Utilidades.cambiarPunto(""+result));
            jTxtFldCargasTotalesViviendas.setText(Utilidades.cambiarPunto(""+result));
            return result;
        }
    }

    private void calcularPrevCargasViviendasABC(){
        double a, b, c, result;

        a = calcularPrevCargasViviendasA();
        b = calcularPrevCargasViviendasB();
        c = calcularPrevCargasViviendasC();

        result = a + b + c;
        jTxtFldCargasTotalesViviendas.setText(Utilidades.cambiarPunto(""+result));
    }

    private void setModeloTablaPrevision() {
        Vector v1 = new Vector();

        ResultSet rs = null;
         try{
             rs = bd.ejecSelect("SELECT * FROM TIPOS_CARGIND");
             while(rs.next())
                 v1.add(new ParCombo(rs.getString(1),rs.getString(2)));
         }
         catch(SQLException e){
             Mensaje.error("FrmNuevaInst.java: "+e.getMessage(), e);
         }

        // Crea el modelo
        modeloTablaPrevision = new ModeloTablaPrevision();

        // Crea el control, pasándole el modelo
        controlPrevision = new ControlTablaPrevision (jTblPrevision, modeloTablaPrevision);
        jTblPrevision.setModel(modeloTablaPrevision);

        TableColumn colPrevision0 = jTblPrevision.getColumnModel().getColumn(0);
        colPrevision0.setCellEditor(new MyComboBoxEditor(v1));
        //colPrevision0.setCellRenderer(new ComboBoxTablas(v1));

        //Paso el textfield encargado de mostrar la suma de potencias de la tabla.
        this.modeloTablaPrevision.setJTxtFldSumaPotencia(jTxtFldSumaPotencia);
    }

    private boolean esValidoDatosIniciales() {
        String txtTemp = "\n * Error en \"Datos de la nueva instalación\":\n";
        txtErrorNuevaInstGeneral = "";
        boolean correcto = true;
        pcReglamentos = (ParCombo)jCmbBxReglamentos.getSelectedItem();

        if(jTxtFldNombreInst.getText().equals(""))
        {
            correcto = false;
            txtErrorNuevaInstGeneral += "      - NOMBRE DE LA INSTALACIÓN no debe estar vacío.\n";
        }

        if(jTxtFldTension1.getText().equals(""))
        {
            txtErrorNuevaInstGeneral += "      - TENSION su primera casilla no debe estar vacía.\n";
            correcto = false;
        }
        else{
            try{
                   tension1 = Double.parseDouble(Utilidades.cambiarComa(jTxtFldTension1.getText()));
                   tension2 = Double.parseDouble(Utilidades.cambiarComa(jTxtFldTension2.getText()));
            }
            catch(NumberFormatException e){
                       txtErrorNuevaInstGeneral += "      - TENSIÓN su valor sea un número válido. Ej: 10 ó 10,44.\n";
                       correcto = false;
            }
        }

        if(jCmbBxPotenciaPrevista.isEditable() &&
           (jCmbBxPotenciaPrevista.getSelectedItem() == null || jCmbBxPotenciaPrevista.getSelectedItem().toString().equals("")))
        {
            correcto = false;
            txtErrorNuevaInstGeneral += "      - POTENCIA PREVISTA no debe estar vacío.\n";
        }
        else{
            try{
                   potencia = Double.parseDouble(Utilidades.cambiarComa(jCmbBxPotenciaPrevista.getSelectedItem().toString()));
            }
            catch(NumberFormatException e){
                       txtErrorNuevaInstGeneral += "      - POTENCIA PREVISTA su valor sea un número válido. Ej: 10 ó 10,44.\n";
                       correcto = false;
            }
        }

        if(jTxtFldSuperficieLocal.getText().equals(""))
        {
            txtErrorNuevaInstGeneral += "      - SUPERFICIE LOCAL no debe de estar vacío.\n";
            correcto = false;
        }
        else{
            try{
                   superficieLocal = Double.parseDouble(Utilidades.cambiarComa(jTxtFldSuperficieLocal.getText()));
            }
            catch(NumberFormatException e){
                   correcto = false;
                   txtErrorNuevaInstGeneral += "      - SUPERFICIE LOCAL su valor sea un número válido. Ej: 10 ó 10,44.\n";
            }
        }

        if(tiLim == -1)
             esSinProyecto = false;
        else if (tiLim == 0)
             esSinProyecto = true;
        else
        {
             if(potencia/1000 > tiLim)
                  esSinProyecto = false;
             else
                  esSinProyecto = true;
        }

        pc = (ParCombo)jCmbBxMemoriaPor.getSelectedItem();
        memoriaPor = pc.getKeyString();
        if(correcto)
            return true;
        else{
            txtErrorNuevaInstGeneral = txtTemp + txtErrorNuevaInstGeneral;
            return correcto;
        }
    }

    private boolean esValidoPrevIndustriales() {
        boolean correcto = true;
        double temp;
        String strPotencia = "";
        String strDenominacion = "";
        ModeloTablaPrevision modelo;
        String txtTemp = "\n      * Pestaña \"Previsión cargas industriales, agrarias, servicios...\":\n";
        txtErrorPrevIndustriales = "";

           modelo = (ModeloTablaPrevision) jTblPrevision.getModel();
           for(int i=0; i<jTblPrevision.getRowCount(); i++)
           {
               strDenominacion = modelo.getValueAt(i,1).toString();
               strPotencia = modelo.getValueAt(i,2).toString();

               if(strDenominacion.equals(""))
               {
                   correcto = false;
                   txtErrorPrevIndustriales += "            - Compruebe que todas las filas para la columna DENOMINACIÓN no estén vacías.\n";
               }
               if(strPotencia.equals(""))
               {
                   correcto = false;
                   txtErrorPrevIndustriales += "            - Compruebe que todas las filas para la columna POTENCIA no estén vacías.\n";
               }
               else{
                   try{
                       temp = Double.parseDouble(Utilidades.cambiarComa(strPotencia));
                   }
                   catch(NumberFormatException e){
                       correcto = false;
                       txtErrorPrevIndustriales += "            - Compruebe que todas las filas para la columna POTENCIA contengan un número válido. Ej: 10 ó 10,44.\n";
                   }
               }

               if(!correcto)
                   break;
           }
        if(correcto)
            return true;
        else{
            txtErrorPrevIndustriales = txtTemp + txtErrorPrevIndustriales;
            return false;
        }
    }

    private boolean esValidoPrevViviendas() {

        boolean viviendas = true;
        boolean ssGen     = true;
        boolean locales   = true;
        String txtTemp = "\n      * Pestaña \"Previsión cargas edificios viviendas\":\n";
        txtErrorPrevViviendas = "";

       //VIVIENDAS
       pc = (ParCombo)jCmbBxGradoElect1.getSelectedItem();
       gradoElect1 = pc.getKeyString();
       pc = (ParCombo)jCmbBxGradoElect2.getSelectedItem();
       gradoElect2 = pc.getKeyString();


       try{
            if(!jTxtFldNumViv1.getText().equals(""))
            {
                Integer i = new Integer(jTxtFldNumViv1.getText());
                numViv1 = i.intValue();
            }
            else
                numViv1 = 0;
            if(!jTxtFldNumViv2.getText().equals(""))
            {
                Integer i = new Integer(jTxtFldNumViv2.getText());
                numViv2 = i.intValue();
            }
            else
                numViv2 = 0;
       }
       catch(NumberFormatException e){
                   viviendas = false;
                   txtErrorPrevViviendas += "            - Nº DE VIVIENDAS su valor sea un número entero válido. Ej: 10.\n";
       }
       try{
                   superficieUnit1 = Double.parseDouble(Utilidades.cambiarComa(jTxtFldSuperficieUnit1.getText()));
                   superficieUnit2 = Double.parseDouble(Utilidades.cambiarComa(jTxtFldSuperficieUnit2.getText()));
       }
       catch(NumberFormatException e){
                   viviendas = false;
                   txtErrorPrevViviendas += "            - SUPERFICIE UNITARIA su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   demandaMax1 = Double.parseDouble(Utilidades.cambiarComa(jTxtFldDemandaMax1.getText()));
                   demandaMax2 = Double.parseDouble(Utilidades.cambiarComa(jTxtFldDemandaMax2.getText()));
       }
       catch(NumberFormatException e){
                   viviendas = false;
                   txtErrorPrevViviendas += "            - DEMANDA MÁX/VIVIENDA su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   cargasPrevistasViviendas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldCargasPrevistasViviendas.getText()));
       }
       catch(NumberFormatException e){
                   viviendas = false;
                   txtErrorPrevViviendas += "            - CARGAS PREVISTAS EN VIVIENDAS (A) su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }

       //Servicios generales
       try{
                   ascensoresViviendas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldAscensoresViviendas.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevViviendas += "            - ASCENSORES su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   alumbradoViviendas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldAlumbradoEscalera.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevViviendas += "            - ALUMBRADO ESCALERA su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   otrosServiciosViviendas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldOtrosServiciosViviendas.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevViviendas += "            - OTROS SERVICIOS su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   garajes = Double.parseDouble(Utilidades.cambiarComa(jTxtFldGarajes.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevViviendas += "            - GARAJES su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   cargasServiciosViviendas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldCargasServiciosViviendas.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevViviendas += "            - CARGAS PREVISTAS EN SERVICIOS GENERALES (B) su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }

       //Locales comerciales y oficinas
       try{
                   superficieUtil = Double.parseDouble(Utilidades.cambiarComa(jTxtFldSuperficieUtilTotal.getText()));
       }
       catch(NumberFormatException e){
                   locales = false;
                   txtErrorPrevViviendas += "            - SUPERFICIE ÚTIL TOTAL su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   potEspecPrevista = Double.parseDouble(Utilidades.cambiarComa(jTxtFldPotEspecPrevista.getText()));
       }
       catch(NumberFormatException e){
                   locales = false;
                   txtErrorPrevViviendas += "            - POTENCIA ESPECÍFICA PREVISTA su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   cargasLocalesViviendas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldCargasLocalesViviendas.getText()));
       }
       catch(NumberFormatException e){
                   locales = false;
                   txtErrorPrevViviendas += "            - CARGAS PREVISTAS EN LOCALES COMERCIALES Y OFICINAS (C) su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   cargasTotalesViviendas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldCargasTotalesViviendas.getText()));
       }
       catch(NumberFormatException e){
                   locales = false;
                   txtErrorPrevViviendas += "            - CARGAS TOTALES PREVISTAS EN EL EDIFICIO (A+B+C) su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }

       if(viviendas && ssGen && locales)
           return true;
       else{
           txtErrorPrevViviendas = txtTemp + txtErrorPrevViviendas;
            return false;
       }


    }

    private boolean esValidoPrevOficinas() {
        boolean oficinasEstab = true;
        boolean ssGen         = true;
        boolean otrasCargas   = true;
        String txtTemp = "\n      * Pestaña \"Previsión cargas edificios oficinas\":\n";
        txtErrorPrevOficinas  = "";

       //Viviendas
       numTotalOficinas = jTxtFldNumTotalOficinas.getText();
       numTotalEstabOficinas = jTxtFldNumTotalEstabOficinas.getText();

       if(jTxtFldNumTotalOficinas.getText().equals(""))
           numTotalOficinas = "0";
       if(jTxtFldNumTotalEstabOficinas.getText().equals(""))
           numTotalEstabOficinas = "0";

       try{
                   supTotalOficinas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldSuperficieOficinas.getText()));
       }
       catch(NumberFormatException e){
                   oficinasEstab = false;
                   txtErrorPrevOficinas += "            - SUPERFICIE TOTAL OFICINAS su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   supTotalEstabOficinas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldSuperficieTotalEstabOficinas.getText()));
       }
       catch(NumberFormatException e){
                   oficinasEstab = false;
                   txtErrorPrevOficinas += "            - SUPERFICIE TOTAL ESTAB. INDUS. su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   demandaOficinas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldDemandaOficinas.getText()));
       }
       catch(NumberFormatException e){
                   oficinasEstab = false;
                   txtErrorPrevOficinas += "            - DEMANDA MÁX / OFICINAS su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   demandaEstabOficinas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldDemandaEstabOficinas.getText()));
       }
       catch(NumberFormatException e){
                   oficinasEstab = false;
                   txtErrorPrevOficinas += "            - DEMANDA MÁX / ESTAB. INDUS. su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }

       //Servicios generales
       try{
                   ascensoresOficinas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldAscensoresOficinas.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevOficinas += "            - ASCENSORES su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   alumbradoOficinas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldAlumbradoOficinas.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevOficinas += "            - ALUMBRADO ESCALERA su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   otrosServOficinas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldOtrosServOficinas.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevOficinas += "            - OTROS SERVICIOS su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }

       //Otras cargas
       descripcionOficinas = jTxtFldDescripcionOficinas.getText();
       try{
                    potPrevistaOficinas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldPotPrevistaOficinas.getText()));
       }
       catch(NumberFormatException e){
                   otrasCargas = false;
                   txtErrorPrevOficinas += "            - POTENCIA PREVISTA su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }

        //Cargas totales
        cargasTotalesOficinas = Double.parseDouble(Utilidades.cambiarComa(jTxtFldCargasTotalesOficinas.getText()));

        if(oficinasEstab && ssGen && otrasCargas)
           return true;
        else{
            txtErrorPrevOficinas = txtTemp + txtErrorPrevOficinas;
            return false;
        }

    }

    private boolean esValidoPrevisiones(){
        txtErrorPrevisiones = "";

        boolean esValidoPrevInd = esValidoPrevIndustriales();
        boolean esValidoPrevViv = esValidoPrevViviendas();
        boolean esValidoPrevOfi = esValidoPrevOficinas();

        if(esValidoPrevInd && esValidoPrevViv && esValidoPrevOfi)
            return true;
        else
        {
            txtErrorPrevisiones = "\n * Error en \"Previsiones\":";
            txtErrorPrevisiones += txtErrorPrevIndustriales + txtErrorPrevViviendas + txtErrorPrevOficinas;
        }

        return false;
    }

    private void insertarBDPrevIndustriales() throws SQLException{
        //Recojo la ID de la sesión de la tabla INSTALACIONES

        ResultSet rs = null;
        ParCombo pcTipoCarga;
        String strPotencia, denominacion;
        String idInst, idCargInd;
        String consulta = "";
        ModeloTablaPrevision modelo;

        modelo = (ModeloTablaPrevision) jTblPrevision.getModel();

        for(int i=0; i<jTblPrevision.getRowCount(); i++)
        {
               pcTipoCarga  = (ParCombo)modelo.getValueAt(i,0);
               denominacion = (String)modelo.getValueAt(i,1);
               strPotencia  = Utilidades.cambiarComa(modelo.getValueAt(i,2).toString());
               idCargInd    = (String)modelo.getValueAt(i,4);

               potencia = Double.parseDouble(strPotencia);

               rs = bd.ejecSelect("Select * from CARGAS_INDUSTRIALES where CIID="+idCargInd);
               if(rs.next())
               {    consulta = " UPDATE CARGAS_INDUSTRIALES SET CITGID='"+pcTipoCarga.getKeyString()+"', " +
                       " CIPOTENCIA="+potencia+ ", CIDESC='"+denominacion+"' " +
                       " WHERE CIID="+idCargInd +" AND CIINID="+idInstalacion;
               }
               else
               {
                   consulta = "INSERT INTO CARGAS_INDUSTRIALES(CIID,CIINID,CITGID,CIPOTENCIA,CIDESC) " +
                       " VALUES("+idCargInd+","+idInstalacion+",'"+pcTipoCarga.getKeyString()+"',"+potencia+",'"+denominacion+"')";
               }

               bd.ejecModificacion(consulta);
        }
    }

    private void insertarBDPrevViviendas() throws SQLException{
        String consultaViviendas = "UPDATE INSTALACIONES SET INGEID1='"+gradoElect1+"', INGEID2='"+gradoElect2+"', " +
                " INNVIV1="+numViv1+ ", INNVIV2="+numViv2+", INSUPUNIT1="+superficieUnit1+", INSUPUNIT2="+superficieUnit2+", " +
                "INDEMANDA1="+demandaMax1+", INDEMANDA2="+demandaMax2+", INVIVCARGPREV="+cargasPrevistasViviendas+" " +
                "WHERE INID="+idInstalacion;

        String consultaServGenerales = "UPDATE INSTALACIONES SET INPOTASC="+ascensoresViviendas+", INPOTALUMB="+alumbradoViviendas+", " +
                " INPOTGARAJES = "+garajes+", INPOTOTROS="+otrosServiciosViviendas+", INSGCARGPREV="+cargasServiciosViviendas+" "+
                " WHERE INID="+idInstalacion;

        String consultaLocales = "UPDATE INSTALACIONES SET INOFICSUO="+superficieUtil+", INOFICPOT="+potEspecPrevista+", " +
                " INOFICCARGPREV="+cargasLocalesViviendas+" WHERE INID="+idInstalacion;

        String consultaCargasTotales = "UPDATE INSTALACIONES SET INOFICCARGTOT="+cargasTotalesViviendas+"" +
                " WHERE INID="+idInstalacion;

            bd.ejecModificacion(consultaViviendas);
            bd.ejecModificacion(consultaServGenerales);
            bd.ejecModificacion(consultaLocales);
            bd.ejecModificacion(consultaCargasTotales);
    }

    private void insertarBDPrevOficinas() throws SQLException {
        //Recojo la ID de la sesión de la tabla INSTALACIONES

        String consultaOficinas = "UPDATE INSTALACIONES SET ININDOFIC="+numTotalOficinas+", ININDOFICSUP="+supTotalOficinas+", " +
                " ININDOFICPOT="+demandaOficinas+ ", ININD="+numTotalEstabOficinas+", ININDSUP="+supTotalEstabOficinas+", " +
                "ININDPOT="+demandaEstabOficinas+" WHERE INID="+idInstalacion;

        String  consultaServicios= "UPDATE INSTALACIONES SET ININDASCPOT="+ascensoresOficinas+", ININDALUMBPOT="+alumbradoOficinas+", " +
                " ININDOTROSPOT="+otrosServOficinas+" WHERE INID="+idInstalacion;

        String consultaOtrasCargas = "UPDATE INSTALACIONES SET ININDOTRASDESC='"+descripcionOficinas+"', " +
                "ININDOTRASPOT="+potPrevistaOficinas+" WHERE INID="+idInstalacion;

        String consultaCargasTotales = "UPDATE INSTALACIONES SET ININDPOTTOT="+cargasTotalesOficinas+"" +
                " WHERE INID="+idInstalacion;

            bd.ejecModificacion(consultaOficinas);
            bd.ejecModificacion(consultaServicios);
            bd.ejecModificacion(consultaOtrasCargas);
            bd.ejecModificacion(consultaCargasTotales);
    }

    private void insertarRegistroInicial() {
        //Al ser una nueva instalación, crear de antemano un registro nuevo en INSTALACION:
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH)+1;
        anyo = c.get(Calendar.YEAR);
        fecha = UtilidadesSQL.fechaBD(dia,mes,anyo);

        try{
            consulta = "INSERT INTO INSTALACIONES (INDESC, INFECHA) VALUES("+UtilidadesSQL.tratarParametroString( jTxtFldNombreInst.getText())+",'"+fecha+"')";
            bd = Sesion.getInstance().getBaseDatos();
            bd.ejecModificacion(consulta);
            ResultSet rs = bd.getUltimoResgistro("SELECT * FROM INSTALACIONES");
            idInstalacion = rs.getString(Constantes.SES_INSTALACIONES_ID);
            Sesion.getInstance().setValorHt(Constantes.SES_INSTALACIONES_ID,idInstalacion);
        }
        catch(SQLException e){

        }
    }

    private void insertarBDIniciales() throws SQLException{
        String tension1 = jTxtFldTension1.getText();
        String tension2 = jTxtFldTension2.getText();
        variosSinClasificar = jTxtFldVariosSinClasificar.getText();
        
        if(tension1.equals(""))
            tension1 = "-1";
        if(tension2.equals(""))
            tension2 = "-1";

        String consultaGenerales = "UPDATE INSTALACIONES SET INTENSION1="+tension1+", INTENSION2="+tension2+", " +
                " INUIID="+pcUsoInstalacion.getKeyInt()+", INUSOVARIOS='"+variosSinClasificar+"', "+
                " INPOTPREVISTA="+potencia+", INPOTNORMAL="+potencia+", INPOTCOMPLEMENT=0, INMMID='"+memoriaPor+"', INTICOD='"+tiCod+"', " +
                " INTINUM="+tiNum+", INSUPERFICIE="+superficieLocal+", " +
                " INRGID="+pcReglamentos.getKeyInt()+" WHERE INID="+idInstalacion;
        
        bd.ejecModificacion(consultaGenerales);
    }

    private void agregarTensionesACombos() {
        m1 = new DefaultComboBoxModel();
        m2 = new DefaultComboBoxModel();
        m3 = new DefaultComboBoxModel();
        mTemp = new DefaultComboBoxModel();

        try{
            ResultSet rs = bd.ejecSelect("SELECT * FROM POTNORMALIZADAS");
            while(rs.next()){
                String t1 = rs.getString("PNT1");
                String t2 = rs.getString("PNT2");
                String pot = rs.getString("PNPOT");
                if(t1.equals("230") && (t2 == null || t2.equals("")))
                    m1.addElement(pot);
                else if(t1.equals("230") && t2.equals("130"))
                    m2.addElement(pot);
                else if(t1.equals("400") && t2.equals("230"))
                    m3.addElement(pot);
            }
            mTemp.addElement("");
        }
        catch(SQLException e){
            Mensaje.error("FrmNuevaInst.java: "+e.getMessage(), e);
        }
    }
    
    private boolean esBloqueViviendas(){
        ParCombo pcTipoInst = (ParCombo)Sesion.getInstance().getValorHt(Constantes.SES_TIPO_INSTALACION_ELEGIDA);
        String tiCod = pcTipoInst.getKeyString();
        ParCombo pcUsoInst = (ParCombo)jCmbBxUsoInstalacion.getSelectedItem();
        int usoInst = pcUsoInst.getKeyInt();
        
        if(tiCod.equals("E") && (usoInst == 1)) return true;
        else return false;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBttnAceptar;
    private javax.swing.JButton jBttnMenuPrincipal;
    private javax.swing.JButton jBttnPrevisionFilaBorrar;
    public javax.swing.JButton jBttnPrevisionFilaNueva;
    public javax.swing.JComboBox jCmbBxGradoElect1;
    public javax.swing.JComboBox jCmbBxGradoElect2;
    public javax.swing.JComboBox jCmbBxMemoriaPor;
    private javax.swing.JComboBox jCmbBxPotenciaPrevista;
    public javax.swing.JComboBox jCmbBxReglamentos;
    private javax.swing.JComboBox jCmbBxTipoInstalacion;
    private javax.swing.JComboBox jCmbBxUsoInstalacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPnlPrevisionCargasEdificios;
    private javax.swing.JPanel jPnlPrevisionCargasIndustriales;
    private javax.swing.JPanel jPnlPrevisionCargasOficinas;
    private javax.swing.JScrollPane jScrTblPrevision;
    private javax.swing.JTabbedPane jTbbdPnDatosTecnicos;
    public javax.swing.JTable jTblPrevision;
    public javax.swing.JTextField jTxtFldAlumbradoEscalera;
    public javax.swing.JTextField jTxtFldAlumbradoOficinas;
    public javax.swing.JTextField jTxtFldAscensoresOficinas;
    public javax.swing.JTextField jTxtFldAscensoresViviendas;
    public javax.swing.JTextField jTxtFldCargasLocalesViviendas;
    public javax.swing.JTextField jTxtFldCargasPrevistasViviendas;
    public javax.swing.JTextField jTxtFldCargasServiciosViviendas;
    public javax.swing.JTextField jTxtFldCargasTotalesOficinas;
    public javax.swing.JTextField jTxtFldCargasTotalesViviendas;
    public javax.swing.JTextField jTxtFldDemandaEstabOficinas;
    public javax.swing.JTextField jTxtFldDemandaMax1;
    public javax.swing.JTextField jTxtFldDemandaMax2;
    public javax.swing.JTextField jTxtFldDemandaOficinas;
    public javax.swing.JTextField jTxtFldDescripcionOficinas;
    public javax.swing.JTextField jTxtFldGarajes;
    private javax.swing.JTextField jTxtFldNombreInst;
    public javax.swing.JTextField jTxtFldNumTotalEstabOficinas;
    public javax.swing.JTextField jTxtFldNumTotalOficinas;
    public javax.swing.JTextField jTxtFldNumViv1;
    public javax.swing.JTextField jTxtFldNumViv2;
    public javax.swing.JTextField jTxtFldOtrosServOficinas;
    public javax.swing.JTextField jTxtFldOtrosServiciosViviendas;
    public javax.swing.JTextField jTxtFldPotEspecPrevista;
    public javax.swing.JTextField jTxtFldPotPrevistaOficinas;
    private javax.swing.JTextField jTxtFldSumaPotencia;
    public javax.swing.JTextField jTxtFldSuperficieLocal;
    public javax.swing.JTextField jTxtFldSuperficieOficinas;
    public javax.swing.JTextField jTxtFldSuperficieTotalEstabOficinas;
    public javax.swing.JTextField jTxtFldSuperficieUnit1;
    public javax.swing.JTextField jTxtFldSuperficieUnit2;
    public javax.swing.JTextField jTxtFldSuperficieUtilTotal;
    public javax.swing.JTextField jTxtFldTension1;
    public javax.swing.JTextField jTxtFldTension2;
    public javax.swing.JTextField jTxtFldVariosSinClasificar;
    // End of variables declaration//GEN-END:variables

}
