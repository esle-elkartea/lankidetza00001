/*
 * FrmDatosTecnicosPnl.java
 *
 * Created on 26 de julio de 2006, 14:57
 */

package forms;

import forms.establecer.SetInfoResumen;
import forms.establecer.SetInfoSuministro;
import forms.recoger.ValidarDatos;
import funciones.AutoComplCmbBxRestrictivo;
import funciones.BaseDatos;
import funciones.LimitadorCaracteres;
import funciones.ParCombo;
import funciones.Sesion;
import funciones.Utilidades;
import funciones.UtilidadesSQL;
import funciones.beans.ControlTablaPrevision;
import funciones.beans.ControlTablaResumen;
import funciones.beans.ModeloTablaPrevision;
import funciones.beans.ModeloTablaResumen;
import funciones.beans.MyComboBoxEditor;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;
import main.Constantes;
import main.Mensaje;

/**
 *
 * @author  enrique
 */
public class FrmDatosTecnicosPnl extends javax.swing.JPanel {

    public ControlTablaResumen      controlResumen;
    public ControlTablaPrevision    controlPrevision;
    public ModeloTablaResumen       modeloTablaResumen;
    public ModeloTablaPrevision     modeloTablaPrevision;
    private BaseDatos               bd;
    private ValidarDatos            validar;
    private FrmPrincipal            frmPrincipal;
    private int                     ultimaTab = -1;
    private int                     contadorMensajes = 0;

    private JComboBox c1,c2,c3;
    private DefaultComboBoxModel m1, m11, m111, m2, m22, m222, m3, m33, m333, mTemp, mTemp2, mTemp3;

    public FrmDatosTecnicosPnl() {
        //Guardo esta clase en la sesion
        Sesion.getInstance().setValorHt("objFrmDatosTecnicosPnl",this);
        bd = Sesion.getInstance().getBaseDatos();
        frmPrincipal = (FrmPrincipal)Sesion.getInstance().getValorHt("objFrmPrincipal");
        initComponents();
        establecerRestricciones();
        agregarDatosCombox();
        setModeloTablas();

        validar = new ValidarDatos();
        agregarDatosIniciales();

        //Ocultar botones de Cargas industriales y Previsión.
        /*
        jBttnPrevisionFilaNueva.setVisible(false);
        jBttnPrevisionFilaBorrar.setVisible(false);
        */
        jBttnResumenFilaNueva.setVisible(false);
        jBttnResumenFilaBorrar.setVisible(false);
        
        //Colores por defecto para cuando ciertos controles estén inactivos.
        UIManager.put("ComboBox.disabledBackground", new Color(Constantes.GRIS_COMBOS,Constantes.GRIS_COMBOS,Constantes.GRIS_COMBOS));
        UIManager.put("ComboBox.disabledForeground", Color.BLACK);
    }

    private void agregarDatosCombox() {
        //Ingresar los datos en los combox.
        ResultSet rs = null;
        ParCombo pcAux, pcSesion;
        pcSesion = (ParCombo)Sesion.getInstance().getValorHt(Constantes.SES_TIPO_INSTALACION_ELEGIDA);

        try {
            rs = bd.ejecSelect("SELECT * FROM MOTIVO_MEMORIA ORDER BY MMDESC");
            while(rs.next())
                jCmbBxMemoriaPor.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            
            rs = bd.ejecSelect("SELECT * FROM TIPOS_INSTALACION ORDER BY TIDESC");
            while(rs.next()) {
                pcAux = new ParCombo(rs.getString(1), rs.getInt(2), rs.getString(3));
                if(pcAux.getKeyString().equals(pcSesion.getKeyString()) && pcAux.getKeyInt() == pcSesion.getKeyInt())
                    pcAux = pcSesion;
                jCmbBxTipoInst.addItem(pcAux);
            }
            jCmbBxTipoInst.setSelectedItem(pcSesion);
            
            rs = bd.ejecSelect("SELECT * FROM REGLAMENTOS ORDER BY RGDESC");
            while(rs.next())
                jCmbBxReglamentos.addItem(new ParCombo(rs.getInt(1), rs.getString(2)));
            
            rs = bd.ejecSelect("SELECT * FROM PTOS_CONEXION ORDER BY PCDESC");
            jCmbBxPuntoConexionAcometida.addItem(new ParCombo("", ""));
            while(rs.next())
                jCmbBxPuntoConexionAcometida.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            
            rs = bd.ejecSelect("SELECT * FROM TIPOS_LINEA ORDER BY TLDESC ");
            jCmbBxTipoAcometida.addItem(new ParCombo("", ""));
            jCmbBxTipoLinea.addItem(new ParCombo("", ""));
            while(rs.next())
            {
                jCmbBxTipoAcometida.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
                jCmbBxTipoLinea.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            }
            
            rs = bd.ejecSelect("SELECT * FROM MATERIALES ORDER BY MADESC");
            jCmbBxMaterialAcometida.addItem(new ParCombo("", ""));
            jCmbBxMaterialLinea.addItem(new ParCombo("", ""));
            while(rs.next())
            {
                jCmbBxMaterialAcometida.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
                jCmbBxMaterialLinea.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            }
            
            rs = bd.ejecSelect("SELECT * FROM SITUAC_MODULO ORDER BY SMDESC");
            while(rs.next())
                jCmbBxSituacionModuloMedida.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            
            rs = bd.ejecSelect("SELECT * FROM TIPOS_TIERRA ORDER BY TTDESC");
            while(rs.next())
                jCmbBxTipoPuestaTierra.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            
            rs = bd.ejecSelect("SELECT * FROM GRADOS_ELECTR ORDER BY GEDESC");
            while(rs.next()) {
                jCmbBxGradoElect1.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
                jCmbBxGradoElect2.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            }
            
            rs = bd.ejecSelect("SELECT * FROM TIPO_USO ORDER BY TUDESC");
            jCmbBxTipoUso1.addItem(new ParCombo("",""));
            jCmbBxTipoUso2.addItem(new ParCombo("",""));
            jCmbBxTipoUso3.addItem(new ParCombo("",""));
            jCmbBxTipoUso4.addItem(new ParCombo("",""));
            jCmbBxTipoUso5.addItem(new ParCombo("",""));
            jCmbBxTipoUso6.addItem(new ParCombo("",""));
            while(rs.next()) {
                jCmbBxTipoUso1.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
                jCmbBxTipoUso2.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
                jCmbBxTipoUso3.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
                jCmbBxTipoUso4.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
                jCmbBxTipoUso5.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
                jCmbBxTipoUso6.addItem(new ParCombo(rs.getString(1), rs.getString(2)));
            }
        }
        catch(SQLException e){
            Mensaje.error("FrmDatosTecnicosPnl.java. "+e.getMessage(), e);
        }

        //Los combobox sean autocompletables
        new AutoComplCmbBxRestrictivo(jCmbBxTipoUso1);
        new AutoComplCmbBxRestrictivo(jCmbBxTipoUso2);
        new AutoComplCmbBxRestrictivo(jCmbBxTipoUso3);
        new AutoComplCmbBxRestrictivo(jCmbBxTipoUso4);
        new AutoComplCmbBxRestrictivo(jCmbBxTipoUso5);
        new AutoComplCmbBxRestrictivo(jCmbBxTipoUso6);
        
        new AutoComplCmbBxRestrictivo(jCmbBxTipoInst);
        new AutoComplCmbBxRestrictivo(jCmbBxUsoInstalacion);        
        new AutoComplCmbBxRestrictivo(jCmbBxMemoriaPor);
        new AutoComplCmbBxRestrictivo(jCmbBxReglamentos);
        
        new AutoComplCmbBxRestrictivo(jCmbBxMaterialAcometida);
        new AutoComplCmbBxRestrictivo(jCmbBxTipoLinea);
        new AutoComplCmbBxRestrictivo(jCmbBxPuntoConexionAcometida);
        new AutoComplCmbBxRestrictivo(jCmbBxSituacionModuloMedida);
        new AutoComplCmbBxRestrictivo(jCmbBxTipoAcometida);
        new AutoComplCmbBxRestrictivo(jCmbBxTipoPuestaTierra);
        
        new AutoComplCmbBxRestrictivo(jCmbBxGradoElect1);
        new AutoComplCmbBxRestrictivo(jCmbBxGradoElect2);
    }

    private void calcularPrevCargasOficinas(){
        double oficinas, estab, ascensores, alumbrado, otros, potPrevista, result;
        try{
            oficinas    = Double.parseDouble(Utilidades.cambiarComa(jTxtFldDemandaOficinas.getText()));
            estab       = Double.parseDouble(Utilidades.cambiarComa(jTxtFldDemandaEstabOficinas.getText()));
            ascensores  = Double.parseDouble(Utilidades.cambiarComa(jTxtFldAscensoresOficinas.getText()));
            alumbrado   = Double.parseDouble(Utilidades.cambiarComa(jTxtFldAlumbradoOficinas.getText()));
            otros       = Double.parseDouble(Utilidades.cambiarComa(jTxtFldOtrosServOficinas.getText()));
            potPrevista = Double.parseDouble(Utilidades.cambiarComa(jTxtFldPotPrevistaOficinas.getText()));

            result = oficinas + estab + ascensores + alumbrado + otros + potPrevista;
            jTxtFldCargasTotalesOficinas.setText(Utilidades.cambiarPunto(""+result));
        }
        catch(NumberFormatException e){
            Mensaje.error("Error con el formato de los números, compruebe que sea un número válido.");
        }
    }

    private double calcularPrevCargasViviendasA(){
        double d1 = 0;
        double d2 = 0;
        double n1 = 0;
        double n2 = 0;
        double result = 0;

        try{
            n1 = Double.parseDouble(Utilidades.cambiarComa(jTxtFldNumViv1.getText()));
            n2 = Double.parseDouble(Utilidades.cambiarComa(jTxtFldNumViv2.getText()));
            if(jTxtFldDemandaMax1.isEnabled()){
                d1 = n1 * Double.parseDouble(Utilidades.cambiarComa(jTxtFldDemandaMax1.getText()));
            } else if(jComboBoxDemandaMax1.isEnabled()){
                d1 = n1 * Double.parseDouble(Utilidades.cambiarComa((String)jComboBoxDemandaMax1.getSelectedItem()));
            }
            if(jTxtFldDemandaMax2.isEnabled()){
                d2 = n2 * Double.parseDouble(Utilidades.cambiarComa(jTxtFldDemandaMax2.getText()));
            } else if(jComboBoxDemandaMax2.isEnabled()){
                d2 = n2 * Double.parseDouble(Utilidades.cambiarComa((String)jComboBoxDemandaMax2.getSelectedItem()));
            }

            result = d1 + d2;
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTbbdPnDatosTecnicos = new javax.swing.JTabbedPane();
        jPnlSuministros = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTxtFldDescripcionSumCompl = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jCmbBxPotenciaCompl = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jCmbBxPotenciaNormal = new javax.swing.JComboBox();
        jPnlInstComprendidas = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jTxtFldPotInst1 = new javax.swing.JTextField();
        jPanel31 = new javax.swing.JPanel();
        jTxtFldPotInst2 = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        jTxtFldPotInst3 = new javax.swing.JTextField();
        jPanel33 = new javax.swing.JPanel();
        jTxtFldPotInst4 = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        jTxtFldPotInst5 = new javax.swing.JTextField();
        jPanel35 = new javax.swing.JPanel();
        jTxtFldPotInst6 = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jTxtFldDescripcion1 = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jTxtFldDescripcion2B = new javax.swing.JTextField();
        jTxtFldDescripcion2A = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        jTxtFldDescripcion3B = new javax.swing.JTextField();
        jTxtFldDescripcion3A = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        jTxtFldDescripcion4A = new javax.swing.JTextField();
        jTxtFldDescripcion4B = new javax.swing.JTextField();
        jTxtFldDescripcion4C = new javax.swing.JTextField();
        jTxtFldDescripcion4E = new javax.swing.JTextField();
        jTxtFldDescripcion4D = new javax.swing.JTextField();
        jTxtFldDescripcion4F = new javax.swing.JTextField();
        jTxtFldDescripcion4G = new javax.swing.JTextField();
        jTxtFldDescripcion4H = new javax.swing.JTextField();
        jTxtFldDescripcion4I = new javax.swing.JTextField();
        jTxtFldDescripcion4J = new javax.swing.JTextField();
        jTxtFldDescripcion4K = new javax.swing.JTextField();
        jTxtFldDescripcion4L = new javax.swing.JTextField();
        jTxtFldDescripcion4M = new javax.swing.JTextField();
        jPanel28 = new javax.swing.JPanel();
        jTxtFldDescripcion5A = new javax.swing.JTextField();
        jTxtFldDescripcion5B = new javax.swing.JTextField();
        jTxtFldDescripcion5C = new javax.swing.JTextField();
        jTxtFldDescripcion5E = new javax.swing.JTextField();
        jTxtFldDescripcion5D = new javax.swing.JTextField();
        jTxtFldDescripcion5F = new javax.swing.JTextField();
        jTxtFldDescripcion5G = new javax.swing.JTextField();
        jTxtFldDescripcion5H = new javax.swing.JTextField();
        jTxtFldDescripcion5I = new javax.swing.JTextField();
        jTxtFldDescripcion5J = new javax.swing.JTextField();
        jTxtFldDescripcion5K = new javax.swing.JTextField();
        jTxtFldDescripcion5L = new javax.swing.JTextField();
        jTxtFldDescripcion5M = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        jTxtFldDescripcion6A = new javax.swing.JTextField();
        jTxtFldDescripcion6B = new javax.swing.JTextField();
        jTxtFldDescripcion6C = new javax.swing.JTextField();
        jTxtFldDescripcion6E = new javax.swing.JTextField();
        jTxtFldDescripcion6D = new javax.swing.JTextField();
        jTxtFldDescripcion6F = new javax.swing.JTextField();
        jTxtFldDescripcion6G = new javax.swing.JTextField();
        jTxtFldDescripcion6H = new javax.swing.JTextField();
        jTxtFldDescripcion6I = new javax.swing.JTextField();
        jTxtFldDescripcion6J = new javax.swing.JTextField();
        jTxtFldDescripcion6K = new javax.swing.JTextField();
        jTxtFldDescripcion6L = new javax.swing.JTextField();
        jTxtFldDescripcion6M = new javax.swing.JTextField();
        jPanel65 = new javax.swing.JPanel();
        jPanel67 = new javax.swing.JPanel();
        jPanel66 = new javax.swing.JPanel();
        jTxtFldTension1_1 = new javax.swing.JTextField();
        jTxtFldTension2_1 = new javax.swing.JTextField();
        jPanel68 = new javax.swing.JPanel();
        jTxtFldTension1_2 = new javax.swing.JTextField();
        jTxtFldTension2_2 = new javax.swing.JTextField();
        jPanel69 = new javax.swing.JPanel();
        jTxtFldTension1_3 = new javax.swing.JTextField();
        jTxtFldTension2_3 = new javax.swing.JTextField();
        jPanel70 = new javax.swing.JPanel();
        jTxtFldTension1_4 = new javax.swing.JTextField();
        jTxtFldTension2_4 = new javax.swing.JTextField();
        jPanel71 = new javax.swing.JPanel();
        jTxtFldTension1_5 = new javax.swing.JTextField();
        jTxtFldTension2_5 = new javax.swing.JTextField();
        jPanel72 = new javax.swing.JPanel();
        jTxtFldTension1_6 = new javax.swing.JTextField();
        jTxtFldTension2_6 = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jPanel87 = new javax.swing.JPanel();
        jCmbBxTipoUso1 = new javax.swing.JComboBox();
        jCmbBxTipoUso2 = new javax.swing.JComboBox();
        jCmbBxTipoUso3 = new javax.swing.JComboBox();
        jCmbBxTipoUso4 = new javax.swing.JComboBox();
        jCmbBxTipoUso5 = new javax.swing.JComboBox();
        jCmbBxTipoUso6 = new javax.swing.JComboBox();
        jLabel106 = new javax.swing.JLabel();
        jPnlCaractGenerales = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTxtFldTension1 = new javax.swing.JTextField();
        jLabel101 = new javax.swing.JLabel();
        jTxtFldTension2 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jCmbBxMemoriaPor = new javax.swing.JComboBox();
        jCmbBxReglamentos = new javax.swing.JComboBox();
        jLabel100 = new javax.swing.JLabel();
        jCmbBxPotenciaPrevista = new javax.swing.JComboBox();
        jPanel23 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jCmbBxTipoInst = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jCmbBxUsoInstalacion = new javax.swing.JComboBox();
        jPanel44 = new javax.swing.JPanel();
        jTxtFldSuperficieLocal = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTxtFldVariosSinClasificar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTxtFldSeccionAcometida = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jCmbBxPuntoConexionAcometida = new javax.swing.JComboBox();
        jCmbBxTipoAcometida = new javax.swing.JComboBox();
        jCmbBxMaterialAcometida = new javax.swing.JComboBox();
        jPanel10 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jTxtFldTipoCGP = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTxtFldIntensidadBase = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTxtFldIntensidadCartucho = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTxtFldSeccionLinea = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jCmbBxMaterialLinea = new javax.swing.JComboBox();
        jCmbBxTipoLinea = new javax.swing.JComboBox();
        jPanel13 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jTxtFldTipoModuloMedida = new javax.swing.JTextField();
        jCmbBxSituacionModuloMedida = new javax.swing.JComboBox();
        jPanel14 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTxtFldInterruptorGralAutomatico = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTxtFldInterruptorDiferencial = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jTxtFldSensibilidad = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jTxtFldIccProt = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jTxtFldElectrodos = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jTxtFldLineaEnlace = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jTxtFldLineaPrincipal = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jCmbBxTipoPuestaTierra = new javax.swing.JComboBox();
        jLabel113 = new javax.swing.JLabel();
        jTxtFldResistTierraProt = new javax.swing.JTextField();
        jPanel43 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jTxtFldNumInstIndivFinales = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jTxtFldPresupuestoTotal = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jPnlPrevisionCargasIndustriales = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrTblPrevision = new javax.swing.JScrollPane();
        jTblPrevision = new javax.swing.JTable();
        jPanel41 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTxtFldSumaPotencia = new javax.swing.JTextField();
        jPanel42 = new javax.swing.JPanel();
        jBttnPrevisionFilaBorrar = new javax.swing.JButton();
        jBttnPrevisionFilaNueva = new javax.swing.JButton();
        jPnlPrevisionCargasEdificios = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
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
        jLabel105 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jTxtFldGarajes = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jCmbBxGradoElect1 = new javax.swing.JComboBox();
        jLabel61 = new javax.swing.JLabel();
        jCmbBxGradoElect2 = new javax.swing.JComboBox();
        jLabel64 = new javax.swing.JLabel();
        jTxtFldNumViv1 = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jTxtFldNumViv2 = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jTxtFldSuperficieUnit1 = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jTxtFldDemandaMax1 = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jTxtFldDemandaMax2 = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jTxtFldSuperficieUnit2 = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jTxtFldCargasPrevistasViviendas = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        jComboBoxDemandaMax1 = new javax.swing.JComboBox();
        jComboBoxDemandaMax2 = new javax.swing.JComboBox();
        jPanel9 = new javax.swing.JPanel();
        jLabel91 = new javax.swing.JLabel();
        jTxtFldCargasTotalesViviendas = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        jPnlPrevisionCargasOficinas = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTxtFldNumTotalOficinas = new javax.swing.JTextField();
        jTxtFldNumTotalEstabOficinas = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTxtFldSuperficieTotalEstabOficinas = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jTxtFldSuperficieOficinas = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jTxtFldDemandaOficinas = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jTxtFldDemandaEstabOficinas = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jTxtFldAlumbradoOficinas = new javax.swing.JTextField();
        jTxtFldAscensoresOficinas = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        jTxtFldOtrosServOficinas = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        jTxtFldDescripcionOficinas = new javax.swing.JTextField();
        jLabel110 = new javax.swing.JLabel();
        jTxtFldPotPrevistaOficinas = new javax.swing.JTextField();
        jLabel111 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel118 = new javax.swing.JLabel();
        jTxtFldCargasTotalesOficinas = new javax.swing.JTextField();
        jLabel119 = new javax.swing.JLabel();
        jPanelMemo = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaMemo = new javax.swing.JTextArea();
        jPnlResumen = new javax.swing.JPanel();
        jBttnResumenFilaNueva = new javax.swing.JButton();
        jBttnResumenFilaBorrar = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jScrTblResumen = new javax.swing.JScrollPane();
        jTblResumen = new javax.swing.JTable();

        jPanel37.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMINISTRO COMPLEMENTARIO"));

        jTxtFldDescripcionSumCompl.setAutoscrolls(false);

        jLabel5.setText("Potencia instalada ");

        jLabel6.setText("Descripción");

        jLabel57.setText("W");

        jCmbBxPotenciaCompl.setEditable(true);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel5)
                    .add(jCmbBxPotenciaCompl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 108, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(59, 59, 59)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel6)
                    .add(jTxtFldDescripcionSumCompl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 530, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(165, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel5)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(20, 20, 20)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jCmbBxPotenciaCompl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel57)
                            .add(jTxtFldDescripcionSumCompl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jLabel6))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMINISTRO NORMAL"));

        jLabel3.setText("Potencia instalada *");

        jLabel58.setText("W");

        jCmbBxPotenciaNormal.setEditable(true);
        jCmbBxPotenciaNormal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbBxPotenciaNormalItemStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jCmbBxPotenciaNormal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 110, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel58)))
                .addContainerGap(760, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jCmbBxPotenciaNormal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel58))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel37Layout = new org.jdesktop.layout.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel37Layout.createSequentialGroup()
                .add(jPanel37Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPnlSuministrosLayout = new org.jdesktop.layout.GroupLayout(jPnlSuministros);
        jPnlSuministros.setLayout(jPnlSuministrosLayout);
        jPnlSuministrosLayout.setHorizontalGroup(
            jPnlSuministrosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlSuministrosLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel37, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPnlSuministrosLayout.setVerticalGroup(
            jPnlSuministrosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlSuministrosLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(430, Short.MAX_VALUE))
        );

        jTbbdPnDatosTecnicos.addTab("Suministro", jPnlSuministros);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTxtFldPotInst1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel30Layout = new org.jdesktop.layout.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTxtFldPotInst1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .add(jTxtFldPotInst1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldPotInst2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel31Layout = new org.jdesktop.layout.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTxtFldPotInst2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .add(jTxtFldPotInst2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldPotInst3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel32Layout = new org.jdesktop.layout.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTxtFldPotInst3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jTxtFldPotInst3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTxtFldPotInst4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel33Layout = new org.jdesktop.layout.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTxtFldPotInst4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .add(jTxtFldPotInst4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldPotInst5.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel34Layout = new org.jdesktop.layout.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTxtFldPotInst5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .add(jTxtFldPotInst5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldPotInst6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel35Layout = new org.jdesktop.layout.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTxtFldPotInst6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .add(jTxtFldPotInst6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel103.setText("POT. INST.");

        jLabel104.setText("(kW)");

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel33, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel32, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(jPanel34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel35, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel103, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                    .add(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel104)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .add(jLabel103)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel104)
                .add(18, 18, 18)
                .add(jPanel30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel47.setText("DESCRIPCION DE LA INSTALACION");

        jTxtFldDescripcion1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel26Layout = new org.jdesktop.layout.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTxtFldDescripcion1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 525, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .add(jTxtFldDescripcion1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldDescripcion2B.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion2A.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel25Layout = new org.jdesktop.layout.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel25Layout.createSequentialGroup()
                .add(jTxtFldDescripcion2A, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 258, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion2B, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 260, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldDescripcion2A, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion2B, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldDescripcion3B.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion3A.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel27Layout = new org.jdesktop.layout.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel27Layout.createSequentialGroup()
                .add(jTxtFldDescripcion3A, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 260, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion3B, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 258, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldDescripcion3B, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion3A, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldDescripcion4A.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion4B.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion4C.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion4E.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion4D.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion4F.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion4G.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion4H.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion4I.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion4J.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion4K.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion4L.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion4M.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel24Layout = new org.jdesktop.layout.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel24Layout.createSequentialGroup()
                .add(jTxtFldDescripcion4A, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion4B, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion4C, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion4D, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion4E, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion4F, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion4G, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion4H, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion4I, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion4J, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion4K, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion4L, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion4M, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jPanel24Layout.linkSize(new java.awt.Component[] {jTxtFldDescripcion4A, jTxtFldDescripcion4B, jTxtFldDescripcion4C, jTxtFldDescripcion4D, jTxtFldDescripcion4E, jTxtFldDescripcion4F, jTxtFldDescripcion4G, jTxtFldDescripcion4H, jTxtFldDescripcion4I, jTxtFldDescripcion4J, jTxtFldDescripcion4K, jTxtFldDescripcion4L, jTxtFldDescripcion4M}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldDescripcion4A, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion4B, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion4C, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion4D, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion4E, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion4F, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion4G, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion4H, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion4I, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion4J, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion4K, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion4L, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion4M, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldDescripcion5A.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion5B.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion5C.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion5E.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion5D.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion5F.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion5G.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion5H.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion5I.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion5J.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion5K.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion5L.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion5M.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel28Layout = new org.jdesktop.layout.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel28Layout.createSequentialGroup()
                .add(jTxtFldDescripcion5A, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion5B, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion5C, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion5D, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion5E, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion5F, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion5G, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion5H, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion5I, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion5J, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion5K, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion5L, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion5M, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jPanel28Layout.linkSize(new java.awt.Component[] {jTxtFldDescripcion5A, jTxtFldDescripcion5B, jTxtFldDescripcion5C, jTxtFldDescripcion5D, jTxtFldDescripcion5E, jTxtFldDescripcion5F, jTxtFldDescripcion5G, jTxtFldDescripcion5H, jTxtFldDescripcion5I, jTxtFldDescripcion5J, jTxtFldDescripcion5K, jTxtFldDescripcion5L, jTxtFldDescripcion5M}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldDescripcion5A, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion5B, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion5C, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion5D, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion5E, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion5F, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion5G, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion5H, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion5I, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion5J, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion5K, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion5L, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion5M, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldDescripcion6A.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion6B.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion6C.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion6E.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion6D.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion6F.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion6G.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion6H.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion6I.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion6J.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion6K.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion6L.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldDescripcion6M.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel29Layout = new org.jdesktop.layout.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel29Layout.createSequentialGroup()
                .add(jTxtFldDescripcion6A, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion6B, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion6C, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion6D, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion6E, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion6F, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion6G, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion6H, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion6I, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion6J, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion6K, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion6L, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldDescripcion6M, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel29Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldDescripcion6A, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion6B, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion6C, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion6D, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion6E, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion6F, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion6G, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion6H, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion6I, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion6J, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion6K, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion6L, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDescripcion6M, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel24, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                .addContainerGap())
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel28, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(65, 65, 65))
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
            .add(jPanel1Layout.createSequentialGroup()
                .add(192, 192, 192)
                .add(jLabel47, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(239, 239, 239))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .add(jLabel47)
                .add(28, 28, 28)
                .add(jPanel26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jPanel65.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        org.jdesktop.layout.GroupLayout jPanel67Layout = new org.jdesktop.layout.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 42, Short.MAX_VALUE)
        );

        jTxtFldTension1_1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldTension2_1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel66Layout = new org.jdesktop.layout.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel66Layout.createSequentialGroup()
                .add(jTxtFldTension1_1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldTension2_1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel66Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel66Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldTension2_1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldTension1_1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldTension1_2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldTension2_2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel68Layout = new org.jdesktop.layout.GroupLayout(jPanel68);
        jPanel68.setLayout(jPanel68Layout);
        jPanel68Layout.setHorizontalGroup(
            jPanel68Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel68Layout.createSequentialGroup()
                .add(jTxtFldTension1_2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldTension2_2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel68Layout.setVerticalGroup(
            jPanel68Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel68Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel68Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldTension2_2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldTension1_2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldTension1_3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldTension2_3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel69Layout = new org.jdesktop.layout.GroupLayout(jPanel69);
        jPanel69.setLayout(jPanel69Layout);
        jPanel69Layout.setHorizontalGroup(
            jPanel69Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel69Layout.createSequentialGroup()
                .add(jTxtFldTension1_3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldTension2_3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel69Layout.setVerticalGroup(
            jPanel69Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel69Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel69Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldTension2_3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldTension1_3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldTension1_4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldTension2_4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel70Layout = new org.jdesktop.layout.GroupLayout(jPanel70);
        jPanel70.setLayout(jPanel70Layout);
        jPanel70Layout.setHorizontalGroup(
            jPanel70Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel70Layout.createSequentialGroup()
                .add(jTxtFldTension1_4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldTension2_4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel70Layout.setVerticalGroup(
            jPanel70Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel70Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel70Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldTension2_4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldTension1_4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldTension1_5.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldTension2_5.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel71Layout = new org.jdesktop.layout.GroupLayout(jPanel71);
        jPanel71.setLayout(jPanel71Layout);
        jPanel71Layout.setHorizontalGroup(
            jPanel71Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel71Layout.createSequentialGroup()
                .add(jTxtFldTension1_5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldTension2_5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel71Layout.setVerticalGroup(
            jPanel71Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel71Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel71Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldTension2_5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldTension1_5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTxtFldTension1_6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTxtFldTension2_6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.layout.GroupLayout jPanel72Layout = new org.jdesktop.layout.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel72Layout.createSequentialGroup()
                .add(jTxtFldTension1_6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldTension2_6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel72Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel72Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldTension2_6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldTension1_6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel107.setText("TENSIÓN (V)");

        jLabel108.setText("tensión 1");

        jLabel112.setText("tensión 2");

        org.jdesktop.layout.GroupLayout jPanel65Layout = new org.jdesktop.layout.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel65Layout.createSequentialGroup()
                .add(jPanel65Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel65Layout.createSequentialGroup()
                        .add(jPanel65Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel68, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jPanel69, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .add(26, 26, 26)
                        .add(jPanel67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel65Layout.createSequentialGroup()
                        .add(jPanel72, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(26, 26, 26))
                    .add(jPanel65Layout.createSequentialGroup()
                        .add(jPanel70, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(26, 26, 26))
                    .add(jPanel65Layout.createSequentialGroup()
                        .add(jPanel71, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(26, 26, 26))
                    .add(jPanel65Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel65Layout.createSequentialGroup()
                            .add(jLabel108)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel112))
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel66, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel65Layout.createSequentialGroup()
                        .add(21, 21, 21)
                        .add(jLabel107)))
                .addContainerGap())
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel65Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .add(jLabel107)
                .add(17, 17, 17)
                .add(jPanel65Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel108)
                    .add(jLabel112))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel66, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel65Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel65Layout.createSequentialGroup()
                        .add(jPanel67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(162, 162, 162))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel65Layout.createSequentialGroup()
                        .add(jPanel68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel69, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel71, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
        );

        jPanel87.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jCmbBxTipoUso1.setEditable(true);

        jCmbBxTipoUso2.setEditable(true);

        jCmbBxTipoUso3.setEditable(true);

        jCmbBxTipoUso4.setEditable(true);

        jCmbBxTipoUso5.setEditable(true);

        jCmbBxTipoUso6.setEditable(true);

        jLabel106.setText("TIPO DE USO");

        org.jdesktop.layout.GroupLayout jPanel87Layout = new org.jdesktop.layout.GroupLayout(jPanel87);
        jPanel87.setLayout(jPanel87Layout);
        jPanel87Layout.setHorizontalGroup(
            jPanel87Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel87Layout.createSequentialGroup()
                .add(jPanel87Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jCmbBxTipoUso5, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jCmbBxTipoUso6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jCmbBxTipoUso1, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jCmbBxTipoUso2, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jCmbBxTipoUso3, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jCmbBxTipoUso4, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel87Layout.createSequentialGroup()
                        .add(21, 21, 21)
                        .add(jLabel106)))
                .addContainerGap())
        );

        jPanel87Layout.linkSize(new java.awt.Component[] {jCmbBxTipoUso1, jCmbBxTipoUso2, jCmbBxTipoUso3, jCmbBxTipoUso4, jCmbBxTipoUso5, jCmbBxTipoUso6}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel87Layout.setVerticalGroup(
            jPanel87Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel87Layout.createSequentialGroup()
                .add(32, 32, 32)
                .add(jLabel106)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 44, Short.MAX_VALUE)
                .add(jCmbBxTipoUso1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(28, 28, 28)
                .add(jCmbBxTipoUso2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(24, 24, 24)
                .add(jCmbBxTipoUso3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(28, 28, 28)
                .add(jCmbBxTipoUso4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(32, 32, 32)
                .add(jCmbBxTipoUso5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(26, 26, 26)
                .add(jCmbBxTipoUso6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPnlInstComprendidasLayout = new org.jdesktop.layout.GroupLayout(jPnlInstComprendidas);
        jPnlInstComprendidas.setLayout(jPnlInstComprendidasLayout);
        jPnlInstComprendidasLayout.setHorizontalGroup(
            jPnlInstComprendidasLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlInstComprendidasLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel87, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 554, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPnlInstComprendidasLayout.setVerticalGroup(
            jPnlInstComprendidasLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlInstComprendidasLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPnlInstComprendidasLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel65, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel4, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel87, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(261, Short.MAX_VALUE))
        );

        jTbbdPnDatosTecnicos.addTab("<html>Instalaciones comprendidas<br>(para edificios completos)</html>", jPnlInstComprendidas);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("GENERALES"));

        jLabel2.setText("Tensión*");

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

        jLabel63.setText("W");

        jLabel9.setText("Memoria por*");

        jCmbBxMemoriaPor.setEditable(true);

        jCmbBxReglamentos.setEditable(true);

        jLabel100.setText("Reglamentos");

        jCmbBxPotenciaPrevista.setEditable(true);
        jCmbBxPotenciaPrevista.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbBxPotenciaPrevistaItemStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel20Layout = new org.jdesktop.layout.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel20Layout.createSequentialGroup()
                .add(jLabel2)
                .add(14, 14, 14)
                .add(jTxtFldTension1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel101)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldTension2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel60)
                .add(34, 34, 34)
                .add(jLabel7)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCmbBxPotenciaPrevista, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 91, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel63)
                .add(27, 27, 27)
                .add(jLabel9)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCmbBxMemoriaPor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jLabel100)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCmbBxReglamentos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel2)
                .add(jLabel101)
                .add(jTxtFldTension1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jTxtFldTension2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel60)
                .add(jLabel7)
                .add(jCmbBxPotenciaPrevista, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel63)
                .add(jCmbBxMemoriaPor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel9)
                .add(jLabel100)
                .add(jCmbBxReglamentos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jLabel13.setText("Tipo instalación*");

        jCmbBxTipoInst.setEditable(true);
        jCmbBxTipoInst.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbBxTipoInstItemStateChanged(evt);
            }
        });

        jLabel14.setText("Uso instalación*");

        jCmbBxUsoInstalacion.setEditable(true);
        jCmbBxUsoInstalacion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbBxUsoInstalacionItemStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel23Layout = new org.jdesktop.layout.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel13)
                .add(13, 13, 13)
                .add(jCmbBxTipoInst, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 334, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(14, 14, 14)
                .add(jLabel14)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCmbBxUsoInstalacion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 327, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel23Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel13)
                    .add(jCmbBxTipoInst, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel14)
                    .add(jCmbBxUsoInstalacion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel16.setText("m2");

        jLabel15.setText("Superficie local*");

        jLabel46.setText("Varios sin clasificar");

        org.jdesktop.layout.GroupLayout jPanel44Layout = new org.jdesktop.layout.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel46)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldVariosSinClasificar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(17, 17, 17)
                .add(jLabel15)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldSuperficieLocal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 61, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel16)
                .addContainerGap(330, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel44Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel15)
                .add(jTxtFldSuperficieLocal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel16)
                .add(jTxtFldVariosSinClasificar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel46))
        );

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel23, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel20, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(jPanel44, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(jPanel23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(11, 11, 11))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("ACOMETIDA (Según información de la empresa suministradora)"));

        jLabel12.setText("Punto de conexión");

        jLabel17.setText("Tipo");

        jTxtFldSeccionAcometida.setEditable(false);

        jLabel18.setText("Sección");

        jLabel19.setText("Material");

        jLabel20.setText("mm2");

        jCmbBxPuntoConexionAcometida.setEditable(true);
        jCmbBxPuntoConexionAcometida.setEnabled(false);

        jCmbBxTipoAcometida.setEditable(true);
        jCmbBxTipoAcometida.setEnabled(false);

        jCmbBxMaterialAcometida.setEditable(true);
        jCmbBxMaterialAcometida.setEnabled(false);

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel12)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCmbBxPuntoConexionAcometida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 177, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jLabel17)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCmbBxTipoAcometida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 163, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(24, 24, 24)
                .add(jLabel18)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldSeccionAcometida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel20)
                .add(21, 21, 21)
                .add(jLabel19)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCmbBxMaterialAcometida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 110, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel12)
                .add(jCmbBxPuntoConexionAcometida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel17)
                .add(jCmbBxTipoAcometida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel18)
                .add(jTxtFldSeccionAcometida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel20)
                .add(jLabel19)
                .add(jCmbBxMaterialAcometida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("C.G.P. o C/C DE SEGURIDAD"));

        jLabel21.setText("Tipo");

        jTxtFldTipoCGP.setEditable(false);

        jLabel22.setText("In. Base");

        jTxtFldIntensidadBase.setEditable(false);

        jLabel24.setText("A");

        jLabel23.setText("In Cartucho");

        jTxtFldIntensidadCartucho.setEditable(false);

        jLabel25.setText("A");

        org.jdesktop.layout.GroupLayout jPanel10Layout = new org.jdesktop.layout.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel21)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldTipoCGP, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 172, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(31, 31, 31)
                .add(jLabel22)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldIntensidadBase, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel24)
                .add(20, 20, 20)
                .add(jLabel23)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldIntensidadCartucho, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(454, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel21)
                .add(jTxtFldTipoCGP, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel25)
                .add(jTxtFldIntensidadCartucho, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel23)
                .add(jLabel24)
                .add(jTxtFldIntensidadBase, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel22))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("LINEA GENERAL DE ALIMENTACION O DERIVACION INDIVIDUAL"));

        jLabel26.setText("Tipo");

        jLabel27.setText("Sección");

        jLabel28.setText("Material");

        jTxtFldSeccionLinea.setEditable(false);

        jLabel29.setText("mm2");

        jCmbBxMaterialLinea.setEditable(true);
        jCmbBxMaterialLinea.setEnabled(false);

        jCmbBxTipoLinea.setEditable(true);
        jCmbBxTipoLinea.setEnabled(false);

        org.jdesktop.layout.GroupLayout jPanel12Layout = new org.jdesktop.layout.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel26)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCmbBxTipoLinea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 175, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(35, 35, 35)
                .add(jLabel27)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldSeccionLinea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 172, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel29)
                .add(51, 51, 51)
                .add(jLabel28)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCmbBxMaterialLinea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 71, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(274, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel26)
                .add(jCmbBxTipoLinea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel27)
                .add(jLabel29)
                .add(jTxtFldSeccionLinea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jCmbBxMaterialLinea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel28))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("MODULO DE MEDIDA"));

        jLabel30.setText("Tipo");

        jLabel31.setText("Situación");

        jTxtFldTipoModuloMedida.setEditable(false);

        jCmbBxSituacionModuloMedida.setEditable(true);
        jCmbBxSituacionModuloMedida.setEnabled(false);

        org.jdesktop.layout.GroupLayout jPanel13Layout = new org.jdesktop.layout.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel30)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldTipoModuloMedida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 181, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(23, 23, 23)
                .add(jLabel31)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCmbBxSituacionModuloMedida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 365, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(270, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel30)
                .add(jTxtFldTipoModuloMedida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jCmbBxSituacionModuloMedida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel31))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("PROTECCION MAGNETOTERMICA / DIFERENCIAL"));

        jLabel32.setText("Inst. General Automático");

        jLabel33.setText("A");

        jTxtFldInterruptorGralAutomatico.setEditable(false);

        jLabel34.setText("Icc");

        jLabel35.setText("kA");

        jLabel36.setText("Int. Diferencial");

        jTxtFldInterruptorDiferencial.setEditable(false);

        jLabel37.setText("A");

        jLabel38.setText("Sensibilidad");

        jTxtFldSensibilidad.setEditable(false);

        jLabel39.setText("mA");

        jTxtFldIccProt.setEditable(false);

        org.jdesktop.layout.GroupLayout jPanel14Layout = new org.jdesktop.layout.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel32)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldInterruptorGralAutomatico, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel33)
                .add(43, 43, 43)
                .add(jLabel34)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldIccProt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 58, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel35)
                .add(52, 52, 52)
                .add(jLabel36)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldInterruptorDiferencial, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 104, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel37)
                .add(36, 36, 36)
                .add(jLabel38)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldSensibilidad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 104, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel39)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel32)
                .add(jTxtFldInterruptorGralAutomatico, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel33)
                .add(jLabel34)
                .add(jLabel36)
                .add(jTxtFldInterruptorDiferencial, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel37)
                .add(jLabel38)
                .add(jTxtFldSensibilidad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel39)
                .add(jLabel35)
                .add(jTxtFldIccProt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("PUESTA A TIERRA"));

        jLabel40.setText("Tipo");

        jTxtFldElectrodos.setEditable(false);

        jLabel42.setText("Electrodos");

        jLabel44.setText("Línea enlace");

        jTxtFldLineaEnlace.setEditable(false);

        jLabel45.setText("mm2 Cu");

        jLabel48.setText("Línea principal");

        jTxtFldLineaPrincipal.setEditable(false);

        jLabel49.setText("mm2 Cu");

        jCmbBxTipoPuestaTierra.setEditable(true);
        jCmbBxTipoPuestaTierra.setEnabled(false);

        jLabel113.setText("Ohmios");

        jTxtFldResistTierraProt.setEditable(false);

        org.jdesktop.layout.GroupLayout jPanel15Layout = new org.jdesktop.layout.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel40)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCmbBxTipoPuestaTierra, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(40, 40, 40)
                .add(jLabel42)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldElectrodos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(35, 35, 35)
                .add(jLabel44)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldLineaEnlace, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel45)
                .add(28, 28, 28)
                .add(jLabel48)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldLineaPrincipal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel49)
                .add(26, 26, 26)
                .add(jLabel113)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldResistTierraProt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel40)
                .add(jCmbBxTipoPuestaTierra, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel42)
                .add(jTxtFldElectrodos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel44)
                .add(jLabel45)
                .add(jLabel48)
                .add(jLabel49)
                .add(jLabel113)
                .add(jTxtFldLineaPrincipal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jTxtFldLineaEnlace, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jTxtFldResistTierraProt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jPanel43.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PRESUPUESTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12)));

        jLabel62.setText("Nº de Instalaciones individuales finales*");

        jLabel102.setText("Unidades");

        jLabel43.setText("PRESUPUESTO TOTAL*");

        jLabel41.setText("€");

        org.jdesktop.layout.GroupLayout jPanel43Layout = new org.jdesktop.layout.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel62)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldNumInstIndivFinales, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel102)
                .add(37, 37, 37)
                .add(jLabel43)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldPresupuestoTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel41)
                .addContainerGap(330, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel43Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel62)
                .add(jTxtFldNumInstIndivFinales, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel102)
                .add(jLabel43)
                .add(jTxtFldPresupuestoTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel41))
        );

        org.jdesktop.layout.GroupLayout jPnlCaractGeneralesLayout = new org.jdesktop.layout.GroupLayout(jPnlCaractGenerales);
        jPnlCaractGenerales.setLayout(jPnlCaractGeneralesLayout);
        jPnlCaractGeneralesLayout.setHorizontalGroup(
            jPnlCaractGeneralesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlCaractGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPnlCaractGeneralesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel43, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel15, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel14, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel13, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel12, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel10, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPnlCaractGeneralesLayout.setVerticalGroup(
            jPnlCaractGeneralesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlCaractGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );

        jTbbdPnDatosTecnicos.addTab("Características generales", jPnlCaractGenerales);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("PREVISIÓN DE CARGAS INDUSTRIALES, AGRARIAS, DE SERVICIOS, ETC"));

        jTblPrevision.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo", "Denominación", "Potencia (W)"
            }
        ));
        jTblPrevision.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrTblPrevision.setViewportView(jTblPrevision);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel11.setText("Potencia total");

        jTxtFldSumaPotencia.setEditable(false);

        org.jdesktop.layout.GroupLayout jPanel41Layout = new org.jdesktop.layout.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel41Layout.createSequentialGroup()
                .add(jLabel11)
                .add(14, 14, 14)
                .add(jTxtFldSumaPotencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 133, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel41Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel11)
                    .add(jTxtFldSumaPotencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jBttnPrevisionFilaBorrar.setText("Borrar");
        jBttnPrevisionFilaBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBttnPrevisionFilaBorrarActionPerformed(evt);
            }
        });

        jBttnPrevisionFilaNueva.setText("Nuevo");
        jBttnPrevisionFilaNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBttnPrevisionFilaNuevaActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel42Layout = new org.jdesktop.layout.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel42Layout.createSequentialGroup()
                .add(jBttnPrevisionFilaNueva, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jBttnPrevisionFilaBorrar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel42Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jBttnPrevisionFilaBorrar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jBttnPrevisionFilaNueva, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrTblPrevision, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                    .add(jPanel6Layout.createSequentialGroup()
                        .add(jPanel42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 427, Short.MAX_VALUE)
                        .add(jPanel41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(jScrTblPrevision, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 295, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        org.jdesktop.layout.GroupLayout jPnlPrevisionCargasIndustrialesLayout = new org.jdesktop.layout.GroupLayout(jPnlPrevisionCargasIndustriales);
        jPnlPrevisionCargasIndustriales.setLayout(jPnlPrevisionCargasIndustrialesLayout);
        jPnlPrevisionCargasIndustrialesLayout.setHorizontalGroup(
            jPnlPrevisionCargasIndustrialesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlPrevisionCargasIndustrialesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPnlPrevisionCargasIndustrialesLayout.setVerticalGroup(
            jPnlPrevisionCargasIndustrialesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlPrevisionCargasIndustrialesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(260, Short.MAX_VALUE))
        );

        jTbbdPnDatosTecnicos.addTab("<html>Prevision cargas industriales,<br>agrarias, servicios...</html>", jPnlPrevisionCargasIndustriales);

        jPanel39.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("LOCALES COMERCIALES Y OFICINAS"));

        jLabel85.setText("Superficie útil total");

        jLabel86.setText("m2");

        jLabel87.setText("Potencia específica prevista");

        jTxtFldPotEspecPrevista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldPotEspecPrevistaKeyReleased(evt);
            }
        });

        jLabel88.setText("W/m2");

        jLabel89.setText("CARGAS PREVISTAS EN LOCALES COMERCIALES Y OFICINAS (C)");

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
                .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel89)
                    .add(jPanel19Layout.createSequentialGroup()
                        .add(jLabel85)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldSuperficieUtilTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel86)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel19Layout.createSequentialGroup()
                        .add(jTxtFldCargasLocalesViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel90))
                    .add(jPanel19Layout.createSequentialGroup()
                        .add(jLabel87)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldPotEspecPrevista, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(4, 4, 4)
                        .add(jLabel88)))
                .addContainerGap(320, Short.MAX_VALUE))
        );

        jPanel19Layout.linkSize(new java.awt.Component[] {jTxtFldPotEspecPrevista, jTxtFldSuperficieUtilTotal}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel19Layout.createSequentialGroup()
                .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel85)
                    .add(jTxtFldSuperficieUtilTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel86)
                    .add(jLabel87)
                    .add(jTxtFldPotEspecPrevista, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel88))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel89)
                    .add(jTxtFldCargasLocalesViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel90))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("SERVIVIOS GENERALES"));

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

        jLabel105.setText("Garajes");

        jLabel94.setText("W");

        jTxtFldGarajes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldGarajesKeyReleased(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel18Layout = new org.jdesktop.layout.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel18Layout.createSequentialGroup()
                        .add(jLabel77)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldAscensoresViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel78)
                        .add(25, 25, 25)
                        .add(jLabel80))
                    .add(jLabel83))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jTxtFldCargasServiciosViviendas)
                    .add(jTxtFldAlumbradoEscalera, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel18Layout.createSequentialGroup()
                        .add(jLabel79)
                        .add(26, 26, 26)
                        .add(jLabel81)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldOtrosServiciosViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel82)
                        .add(43, 43, 43)
                        .add(jLabel105)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldGarajes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(4, 4, 4)
                        .add(jLabel94))
                    .add(jLabel84))
                .addContainerGap(161, Short.MAX_VALUE))
        );

        jPanel18Layout.linkSize(new java.awt.Component[] {jTxtFldAlumbradoEscalera, jTxtFldAscensoresViviendas, jTxtFldOtrosServiciosViviendas}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel77)
                .add(jTxtFldAscensoresViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel78)
                .add(jLabel82)
                .add(jTxtFldOtrosServiciosViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel81)
                .add(jLabel105)
                .add(jTxtFldGarajes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel94))
            .add(jPanel18Layout.createSequentialGroup()
                .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel80)
                    .add(jTxtFldAlumbradoEscalera, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel79))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTxtFldCargasServiciosViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel83)
                    .add(jLabel84)))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("VIVIENDAS"));

        jLabel59.setText("Grado electrificación");

        jCmbBxGradoElect1.setEditable(true);
        jCmbBxGradoElect1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbBxGradoElect1ItemStateChanged(evt);
            }
        });

        jLabel61.setText("Grado electrificación");

        jCmbBxGradoElect2.setEditable(true);
        jCmbBxGradoElect2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbBxGradoElect2ItemStateChanged(evt);
            }
        });

        jLabel64.setText("Nº de viviendas");

        jTxtFldNumViv1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldNumViv1KeyReleased(evt);
            }
        });

        jLabel65.setText("m2");

        jLabel66.setText("Nº de viviendas");

        jTxtFldNumViv2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldNumViv2KeyReleased(evt);
            }
        });

        jLabel67.setText("Superficie unitaria");

        jLabel68.setText("Superficie unitaria");

        jLabel69.setText("m2");

        jLabel70.setText("Demanda máx/vivienda");

        jTxtFldDemandaMax1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldDemandaMax1KeyReleased(evt);
            }
        });

        jLabel71.setText("W");

        jLabel72.setText("Demanda máx/vivienda");

        jTxtFldDemandaMax2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldDemandaMax2KeyReleased(evt);
            }
        });

        jLabel73.setText("W");

        jLabel74.setText("Coeficiente de simultaneidad según ITC-BT-10");

        jLabel75.setText("CARGAS PREVISTAS EN VIVIENDAS (A)");

        jTxtFldCargasPrevistasViviendas.setEditable(false);

        jLabel76.setText("W");

        jComboBoxDemandaMax1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5750", "7360" }));
        jComboBoxDemandaMax1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxDemandaMax1ItemStateChanged(evt);
            }
        });

        jComboBoxDemandaMax2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5750", "7360" }));
        jComboBoxDemandaMax2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxDemandaMax2ItemStateChanged(evt);
            }
        });

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
                                .add(jLabel61)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jCmbBxGradoElect2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jLabel59)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jCmbBxGradoElect1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(19, 19, 19)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jLabel64)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTxtFldNumViv1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jLabel66)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTxtFldNumViv2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)))
                        .add(15, 15, 15)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel67)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel68))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jTxtFldSuperficieUnit2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                            .add(jTxtFldSuperficieUnit1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel65)
                            .add(jLabel69))
                        .add(16, 16, 16)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jLabel72)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jComboBoxDemandaMax2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTxtFldDemandaMax2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jLabel70)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jComboBoxDemandaMax1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTxtFldDemandaMax1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel73)
                            .add(jLabel71)))
                    .add(jLabel74)
                    .add(jPanel17Layout.createSequentialGroup()
                        .add(jLabel75)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldCargasPrevistasViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel76)))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanel17Layout.linkSize(new java.awt.Component[] {jTxtFldDemandaMax1, jTxtFldDemandaMax2, jTxtFldNumViv1, jTxtFldNumViv2, jTxtFldSuperficieUnit1, jTxtFldSuperficieUnit2}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel17Layout.createSequentialGroup()
                .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel17Layout.createSequentialGroup()
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel59)
                                    .add(jCmbBxGradoElect1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel61)
                                    .add(jCmbBxGradoElect2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(jLabel64)
                                .add(jTxtFldNumViv1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(28, 28, 28)
                                .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel66)
                                    .add(jTxtFldNumViv2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel74)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel75)
                            .add(jLabel76)
                            .add(jTxtFldCargasPrevistasViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel67)
                        .add(jTxtFldSuperficieUnit1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel65))
                    .add(jPanel17Layout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel68)
                            .add(jTxtFldSuperficieUnit2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel69)))
                    .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel70)
                        .add(jComboBoxDemandaMax1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jTxtFldDemandaMax1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel71))
                    .add(jPanel17Layout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel72)
                            .add(jComboBoxDemandaMax2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jTxtFldDemandaMax2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel73))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel91.setText("CARGAS TOTALES PREVISTAS EN EL EDIFICIO   (A+B+C)");

        jTxtFldCargasTotalesViviendas.setEditable(false);

        jLabel92.setText("W");

        org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel91)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldCargasTotalesViviendas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel92)
                .addContainerGap(514, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel91, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jTxtFldCargasTotalesViviendas)
                    .add(jLabel92, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel39Layout = new org.jdesktop.layout.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel39Layout.createSequentialGroup()
                .add(jPanel39Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel17, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel18, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel19, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel39Layout.createSequentialGroup()
                .add(jPanel17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPnlPrevisionCargasEdificiosLayout = new org.jdesktop.layout.GroupLayout(jPnlPrevisionCargasEdificios);
        jPnlPrevisionCargasEdificios.setLayout(jPnlPrevisionCargasEdificiosLayout);
        jPnlPrevisionCargasEdificiosLayout.setHorizontalGroup(
            jPnlPrevisionCargasEdificiosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlPrevisionCargasEdificiosLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel39, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPnlPrevisionCargasEdificiosLayout.setVerticalGroup(
            jPnlPrevisionCargasEdificiosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlPrevisionCargasEdificiosLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(235, Short.MAX_VALUE))
        );

        jTbbdPnDatosTecnicos.addTab("<html>Prevision cargas<br>edificios viviendas</html>", jPnlPrevisionCargasEdificios);

        jPanel40.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("OFICINAS / ESTABLECIMIENTOS"));

        jLabel4.setText("Nº Total Oficinas");

        jLabel8.setText("Nº Total Estab. Indus");

        jLabel10.setText("Superficie total oficinas");

        jLabel50.setText("Superficie total estab. Indus.");

        jLabel51.setText("m2");

        jLabel52.setText("m2");

        jLabel53.setText("Demanda máx/oficinas");

        jTxtFldDemandaOficinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldDemandaOficinasKeyReleased(evt);
            }
        });

        jLabel54.setText("W");

        jLabel55.setText("Demanda máx/Estab. Indus.");

        jTxtFldDemandaEstabOficinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldDemandaEstabOficinasKeyReleased(evt);
            }
        });

        jLabel56.setText("W");

        org.jdesktop.layout.GroupLayout jPanel11Layout = new org.jdesktop.layout.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel11Layout.createSequentialGroup()
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldNumTotalOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel11Layout.createSequentialGroup()
                        .add(jLabel8)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldNumTotalEstabOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(19, 19, 19)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel11Layout.createSequentialGroup()
                        .add(jLabel10)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldSuperficieOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel51))
                    .add(jPanel11Layout.createSequentialGroup()
                        .add(jLabel50)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTxtFldSuperficieTotalEstabOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel52)))
                .add(25, 25, 25)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel55)
                    .add(jLabel53))
                .add(10, 10, 10)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jTxtFldDemandaOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTxtFldDemandaEstabOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel56)
                    .add(jLabel54))
                .addContainerGap(185, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel11Layout.createSequentialGroup()
                        .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jTxtFldNumTotalOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel4))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel8)
                            .add(jTxtFldNumTotalEstabOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel50)
                            .add(jTxtFldSuperficieTotalEstabOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel52)
                            .add(jLabel55)
                            .add(jTxtFldDemandaEstabOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel56)))
                    .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jTxtFldSuperficieOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel51)
                        .add(jLabel10)
                        .add(jTxtFldDemandaOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel54)
                        .add(jLabel53)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("SERVICIOS GENERALES"));

        jLabel93.setText("Ascensores");

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

        jLabel95.setText("Alumbrado escalera");

        jTxtFldOtrosServOficinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldOtrosServOficinasKeyReleased(evt);
            }
        });

        jLabel96.setText("W");

        jLabel97.setText("W");

        jLabel98.setText("Otros servicios");

        jLabel99.setText("W");

        org.jdesktop.layout.GroupLayout jPanel16Layout = new org.jdesktop.layout.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel16Layout.createSequentialGroup()
                .add(57, 57, 57)
                .add(jLabel93)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldAscensoresOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel99)
                .add(53, 53, 53)
                .add(jLabel95)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldAlumbradoOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel96)
                .add(101, 101, 101)
                .add(jLabel98)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldOtrosServOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel97)
                .addContainerGap(184, Short.MAX_VALUE))
        );

        jPanel16Layout.linkSize(new java.awt.Component[] {jTxtFldAlumbradoOficinas, jTxtFldOtrosServOficinas}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel16Layout.createSequentialGroup()
                .add(jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel99)
                    .add(jTxtFldAscensoresOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel93)
                    .add(jLabel96)
                    .add(jTxtFldAlumbradoOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel95)
                    .add(jLabel97)
                    .add(jTxtFldOtrosServOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel98))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("OTRAS CARGAS"));

        jLabel109.setText("Potencia prevista");

        jLabel110.setText("Descripción");

        jTxtFldPotPrevistaOficinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtFldPotPrevistaOficinasKeyReleased(evt);
            }
        });

        jLabel111.setText("W");

        org.jdesktop.layout.GroupLayout jPanel21Layout = new org.jdesktop.layout.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel110)
                .add(52, 52, 52)
                .add(jTxtFldDescripcionOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 389, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(29, 29, 29)
                .add(jLabel109)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldPotPrevistaOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel111)
                .addContainerGap(184, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel21Layout.createSequentialGroup()
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel110)
                    .add(jLabel109)
                    .add(jTxtFldPotPrevistaOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel111)
                    .add(jTxtFldDescripcionOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel38.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel118.setText("CARGAS TOTALES PREVISTAS EN EL EDIFICIO");

        jTxtFldCargasTotalesOficinas.setEditable(false);

        jLabel119.setText("W");

        org.jdesktop.layout.GroupLayout jPanel38Layout = new org.jdesktop.layout.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel118)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTxtFldCargasTotalesOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(567, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel38Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel118)
                    .add(jTxtFldCargasTotalesOficinas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel119))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel40Layout = new org.jdesktop.layout.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel40Layout.createSequentialGroup()
                .add(jPanel40Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel21, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel16, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel38, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel40Layout.createSequentialGroup()
                .add(jPanel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPnlPrevisionCargasOficinasLayout = new org.jdesktop.layout.GroupLayout(jPnlPrevisionCargasOficinas);
        jPnlPrevisionCargasOficinas.setLayout(jPnlPrevisionCargasOficinasLayout);
        jPnlPrevisionCargasOficinasLayout.setHorizontalGroup(
            jPnlPrevisionCargasOficinasLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlPrevisionCargasOficinasLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel40, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPnlPrevisionCargasOficinasLayout.setVerticalGroup(
            jPnlPrevisionCargasOficinasLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlPrevisionCargasOficinasLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(324, Short.MAX_VALUE))
        );

        jTbbdPnDatosTecnicos.addTab("<html>Prevision cargas<br>edificios oficinas</html>", jPnlPrevisionCargasOficinas);

        jPanel45.setBorder(javax.swing.BorderFactory.createTitledBorder("MEMO"));

        jTextAreaMemo.setColumns(20);
        jTextAreaMemo.setRows(5);
        jScrollPane2.setViewportView(jTextAreaMemo);

        org.jdesktop.layout.GroupLayout jPanel45Layout = new org.jdesktop.layout.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel45Layout.createSequentialGroup()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 411, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanelMemoLayout = new org.jdesktop.layout.GroupLayout(jPanelMemo);
        jPanelMemo.setLayout(jPanelMemoLayout);
        jPanelMemoLayout.setHorizontalGroup(
            jPanelMemoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelMemoLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel45, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelMemoLayout.setVerticalGroup(
            jPanelMemoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelMemoLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(184, Short.MAX_VALUE))
        );

        jTbbdPnDatosTecnicos.addTab("Memo", jPanelMemo);

        jBttnResumenFilaNueva.setText("Nuevo");
        jBttnResumenFilaNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBttnResumenFilaNuevaActionPerformed(evt);
            }
        });

        jBttnResumenFilaBorrar.setText("Borrar");
        jBttnResumenFilaBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBttnResumenFilaBorrarActionPerformed(evt);
            }
        });

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("RESUMEN"));

        jTblResumen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html>Tipo de<br>circuito</html>", "Desc. Circuito", "<html>Potencia<br>cálculo (W)</html>", "<html>Tensión<br>cálculo (V)</html>", "<html>Intensidad<br>cálculo (A)</html>", "<html>Conductores<br>(Nº -mm2-CU/Al)</html>", "<html>Aislamiento<br>(V)</html>", "Tipo de instalación", "<html>Int. máx.<br>(A)</html>", "C/C PIA (A)", "Longitud (m)", "<html>Caída de<br>Tensión (V)</html>"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTblResumen.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTblResumen.setEnabled(false);
        jScrTblResumen.setViewportView(jTblResumen);

        org.jdesktop.layout.GroupLayout jPanel22Layout = new org.jdesktop.layout.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrTblResumen, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel22Layout.createSequentialGroup()
                .add(jScrTblResumen, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPnlResumenLayout = new org.jdesktop.layout.GroupLayout(jPnlResumen);
        jPnlResumen.setLayout(jPnlResumenLayout);
        jPnlResumenLayout.setHorizontalGroup(
            jPnlResumenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPnlResumenLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPnlResumenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel22, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPnlResumenLayout.createSequentialGroup()
                        .add(jBttnResumenFilaNueva, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 99, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jBttnResumenFilaBorrar)))
                .addContainerGap())
        );

        jPnlResumenLayout.linkSize(new java.awt.Component[] {jBttnResumenFilaBorrar, jBttnResumenFilaNueva}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPnlResumenLayout.setVerticalGroup(
            jPnlResumenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPnlResumenLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel22, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPnlResumenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jBttnResumenFilaBorrar)
                    .add(jBttnResumenFilaNueva))
                .addContainerGap())
        );

        jTbbdPnDatosTecnicos.addTab("Resumen", jPnlResumen);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTbbdPnDatosTecnicos, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 961, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTbbdPnDatosTecnicos, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCmbBxPotenciaNormalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbBxPotenciaNormalItemStateChanged
        // actualizamos la potencia prevista para igualarla a la instalada
        jCmbBxPotenciaPrevista.setSelectedIndex(jCmbBxPotenciaNormal.getSelectedIndex());
        if(jCmbBxPotenciaPrevista.getSelectedIndex() == -1) jCmbBxPotenciaPrevista.setSelectedItem(jCmbBxPotenciaNormal.getSelectedItem());
        String potencia = (String)jCmbBxPotenciaNormal.getSelectedItem();
        Sesion.getInstance().setValorHt(Constantes.SES_POTENCIA_PREVISTA, potencia);
        Sesion.getInstance().setValorHt(Constantes.SES_TENSION1, jTxtFldTension1.getText());
        Sesion.getInstance().setValorHt(Constantes.SES_TENSION2, jTxtFldTension2.getText());
        
        // actualizamos el cuadro resumen ya que la potencia instalada se usa en el cálculo de la Derivación Individual
        /*
        FrmPrincipal frmPrincipal = (FrmPrincipal)Sesion.getInstance().getValorHt("objFrmPrincipal");
        if(frmPrincipal != null) {
            FrmGraficoPnl frmGrafico;
            frmGrafico = frmPrincipal.getFrmGrafico();
            if(frmGrafico != null) {
                try {
                    frmGrafico.guardarDatosBD(UtilidadesSQL.esVivienda());
                    double potenciaNormal = Double.parseDouble(Utilidades.cambiarComa(jCmbBxPotenciaNormal.getSelectedItem().toString()));
                    int idInstalacion =((Integer)Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID)).intValue();
                    BaseDatos bd = Sesion.getInstance().getBaseDatos();
                    String consulta = "UPDATE INSTALACIONES SET INPOTNORMAL=" + potenciaNormal + " WHERE INID=" + idInstalacion;
                    bd.ejecModificacion(consulta);
                    SetInfoResumen setInfoResumen = new SetInfoResumen(frmPrincipal, idInstalacion);
                    setInfoResumen.getInfoBD();
                    setInfoResumen.rellenarFormulario();
                }
                catch(Exception e) {}
            }
        }
        */
    }//GEN-LAST:event_jCmbBxPotenciaNormalItemStateChanged

    private void jComboBoxDemandaMax2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxDemandaMax2ItemStateChanged
        calcularPrevCargasViviendasA();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jComboBoxDemandaMax2ItemStateChanged

    private void jComboBoxDemandaMax1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxDemandaMax1ItemStateChanged
        calcularPrevCargasViviendasA();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jComboBoxDemandaMax1ItemStateChanged

    private void jCmbBxGradoElect2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbBxGradoElect2ItemStateChanged
        if("E".equalsIgnoreCase(((ParCombo)jCmbBxGradoElect2.getSelectedItem()).getKeyString())){
            jComboBoxDemandaMax2.setEnabled(false);
            jTxtFldDemandaMax2.setEnabled(true);
        } else if("B".equalsIgnoreCase(((ParCombo)jCmbBxGradoElect2.getSelectedItem()).getKeyString())){
            jComboBoxDemandaMax2.setEnabled(true);
            jTxtFldDemandaMax2.setEnabled(false);
        } else {
            jComboBoxDemandaMax2.setEnabled(false);
            jTxtFldDemandaMax2.setEnabled(false);
        }
        calcularPrevCargasViviendasA();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jCmbBxGradoElect2ItemStateChanged

    private void jCmbBxGradoElect1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbBxGradoElect1ItemStateChanged
        if("E".equalsIgnoreCase(((ParCombo)jCmbBxGradoElect1.getSelectedItem()).getKeyString())){
            jComboBoxDemandaMax1.setEnabled(false);
            jTxtFldDemandaMax1.setEnabled(true);
        } else if("B".equalsIgnoreCase(((ParCombo)jCmbBxGradoElect1.getSelectedItem()).getKeyString())){
            jComboBoxDemandaMax1.setEnabled(true);
            jTxtFldDemandaMax1.setEnabled(false);
        } else {
            jComboBoxDemandaMax1.setEnabled(false);
            jTxtFldDemandaMax1.setEnabled(false);
        }
        calcularPrevCargasViviendasA();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jCmbBxGradoElect1ItemStateChanged

    private void jTxtFldTension2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldTension2KeyReleased
        String tension1 = jTxtFldTension1.getText();
        String tension2 = jTxtFldTension2.getText();

        if(tension1.equals("230") && tension2.equals("")) {
            jCmbBxPotenciaPrevista.setModel(m111);
            jCmbBxPotenciaNormal.setModel(m1);
            jCmbBxPotenciaCompl.setModel(m11);
        } else if(tension1.equals("230") && tension2.equals("130")) {
            jCmbBxPotenciaPrevista.setModel(m222);
            jCmbBxPotenciaNormal.setModel(m2);
            jCmbBxPotenciaCompl.setModel(m22);
        } else if(tension1.equals("400") && tension2.equals("230")) {
            jCmbBxPotenciaPrevista.setModel(m333);
            jCmbBxPotenciaNormal.setModel(m3);
            jCmbBxPotenciaCompl.setModel(m33);
        } else {
            jCmbBxPotenciaPrevista.setModel(mTemp3);
            jCmbBxPotenciaNormal.setModel(mTemp);
            jCmbBxPotenciaCompl.setModel(mTemp2);
        }
    }//GEN-LAST:event_jTxtFldTension2KeyReleased

    private void jTxtFldTension1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldTension1KeyReleased
        String tension1 = jTxtFldTension1.getText();
        String tension2 = jTxtFldTension2.getText();

        if(tension1.equals("230") && tension2.equals("")) {
            jCmbBxPotenciaPrevista.setModel(m111);
            jCmbBxPotenciaNormal.setModel(m1);
            jCmbBxPotenciaCompl.setModel(m11);
        } else if(tension1.equals("230") && tension2.equals("130")) {
            jCmbBxPotenciaPrevista.setModel(m222);
            jCmbBxPotenciaNormal.setModel(m2);
            jCmbBxPotenciaCompl.setModel(m22);
        } else if(tension1.equals("400") && tension2.equals("230")) {
            jCmbBxPotenciaPrevista.setModel(m333);
            jCmbBxPotenciaNormal.setModel(m3);
            jCmbBxPotenciaCompl.setModel(m33);
        } else {
            jCmbBxPotenciaPrevista.setModel(mTemp3);
            jCmbBxPotenciaNormal.setModel(mTemp);
            jCmbBxPotenciaCompl.setModel(mTemp2);
        }
    }//GEN-LAST:event_jTxtFldTension1KeyReleased

    private void jCmbBxUsoInstalacionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbBxUsoInstalacionItemStateChanged
        try{
            ParCombo pcUsoInstalacion = (ParCombo)jCmbBxUsoInstalacion.getSelectedItem();

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

    private void jCmbBxTipoInstItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbBxTipoInstItemStateChanged
        String consulta = "SELECT * FROM INSTALACIONES WHERE INID="+Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();
        ResultSet rs = null;
        ResultSet rsTemp = null;
        int inuiid;
        ParCombo pcTipoInst, pcTemp;
        
        try{
            rs = bd.ejecSelect(consulta);
            if(rs.next())
            {
                inuiid = rs.getInt("INUIID");
                pcTipoInst = (ParCombo)jCmbBxTipoInst.getSelectedItem();
                consulta = "SELECT * FROM USO_INSTALACION WHERE UITINUM="+pcTipoInst.getKeyInt()+" " +
                           "AND UITICOD='"+pcTipoInst.getKeyString()+"' ORDER BY UIID";
                
                jCmbBxUsoInstalacion.removeAllItems();
                rsTemp = bd.ejecSelect(consulta);
                while(rsTemp.next())
                    jCmbBxUsoInstalacion.addItem(new ParCombo(rsTemp.getInt("UIID"),rsTemp.getString("UIDESC")));
                
                for(int i=0; i<jCmbBxUsoInstalacion.getItemCount(); i++)
                {
                    pcTemp = (ParCombo)jCmbBxUsoInstalacion.getItemAt(i);
                    if(pcTemp.getKeyInt() == inuiid)
                        jCmbBxUsoInstalacion.setSelectedItem(pcTemp);
                }
            }
        }
        catch(SQLException e){
            Mensaje.error("FrmDatosTecnicosPnl.java: "+e.getMessage(), e);
        }
        catch(NullPointerException e){
            Mensaje.error("FrmDatosTecnicosPnl.java: "+e.getMessage(), e);
        }
    }//GEN-LAST:event_jCmbBxTipoInstItemStateChanged

    private void jTxtFldNumViv2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldNumViv2KeyReleased
        calcularPrevCargasViviendasA();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldNumViv2KeyReleased

    private void jTxtFldNumViv1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldNumViv1KeyReleased
        calcularPrevCargasViviendasA();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldNumViv1KeyReleased

    private void jTxtFldSuperficieUtilTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldSuperficieUtilTotalKeyReleased
        calcularPrevCargasViviendasC();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldSuperficieUtilTotalKeyReleased

    private void jTxtFldGarajesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldGarajesKeyReleased
        calcularPrevCargasViviendasB();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldGarajesKeyReleased

    private void jTxtFldPotEspecPrevistaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldPotEspecPrevistaKeyReleased
        calcularPrevCargasViviendasC();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldPotEspecPrevistaKeyReleased

    private void jTxtFldOtrosServiciosViviendasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldOtrosServiciosViviendasKeyReleased
        calcularPrevCargasViviendasB();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldOtrosServiciosViviendasKeyReleased

    private void jTxtFldAlumbradoEscaleraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldAlumbradoEscaleraKeyReleased
        calcularPrevCargasViviendasB();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldAlumbradoEscaleraKeyReleased

    private void jTxtFldAscensoresViviendasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldAscensoresViviendasKeyReleased
        calcularPrevCargasViviendasB();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldAscensoresViviendasKeyReleased

    private void jTxtFldDemandaMax2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldDemandaMax2KeyReleased
        calcularPrevCargasViviendasA();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldDemandaMax2KeyReleased

    private void jTxtFldDemandaMax1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldDemandaMax1KeyReleased
        calcularPrevCargasViviendasA();
        calcularPrevCargasViviendasABC();
    }//GEN-LAST:event_jTxtFldDemandaMax1KeyReleased

    private void jTxtFldPotPrevistaOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldPotPrevistaOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldPotPrevistaOficinasKeyReleased

    private void jTxtFldOtrosServOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldOtrosServOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldOtrosServOficinasKeyReleased

    private void jTxtFldAlumbradoOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldAlumbradoOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldAlumbradoOficinasKeyReleased

    private void jTxtFldAscensoresOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldAscensoresOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldAscensoresOficinasKeyReleased

    private void jTxtFldDemandaEstabOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldDemandaEstabOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldDemandaEstabOficinasKeyReleased

    private void jTxtFldDemandaOficinasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFldDemandaOficinasKeyReleased
        calcularPrevCargasOficinas();
    }//GEN-LAST:event_jTxtFldDemandaOficinasKeyReleased

    private void jBttnResumenFilaBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBttnResumenFilaBorrarActionPerformed
        try{
            int numCols[] = jTblResumen.getSelectedRows();
            if(numCols.length > 0)
            {
                JOptionPane optionPane=new JOptionPane();
                Object[] opciones={"Aceptar","Cancelar"};
                int ret=optionPane.showOptionDialog(null,"¿CONFIRMA QUE DESEA ELIMINAR LA FILA O FILAS SELECCIONADAS?","Confirmar elimnación de filas.",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opciones,opciones[0]);
	
                if(ret==JOptionPane.YES_OPTION)
                {
                    for(int i=numCols.length-1; i>=0; i--){
                        int fila = numCols[i];
                        String id = modeloTablaResumen.getValueAt(fila,12).toString();
                        controlResumen.borraFila(fila);
                        bd.ejecModificacion("DELETE FROM DATOS_CIRCUITOS WHERE DCID = "+id);
                    }
                }
            }
            else
                Mensaje.aviso("Seleccione algún registro tras haber introducido un criterio de búsqueda");
            
        } catch(SQLException e){
            Mensaje.error("Error en la eliminación de los registros: "+e.getMessage(),e);
        } catch(IndexOutOfBoundsException e){
            Mensaje.aviso("No existen registros seleccionados para borrar.");
        }      
    }//GEN-LAST:event_jBttnResumenFilaBorrarActionPerformed

    private void jBttnResumenFilaNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBttnResumenFilaNuevaActionPerformed
        if(esValidoTablaResumen())
            controlResumen.anhadeFila();
    }//GEN-LAST:event_jBttnResumenFilaNuevaActionPerformed

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
                    //Hayq ue recorrer al revés pues este modelo de la tabla trabaja con Listas Enlazadas.
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

    private void jCmbBxPotenciaPrevistaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbBxPotenciaPrevistaItemStateChanged
        String potencia = (jCmbBxPotenciaPrevista.getSelectedItem() == null ? "" : (String)jCmbBxPotenciaPrevista.getSelectedItem());
        Sesion.getInstance().setValorHt(Constantes.SES_POTENCIA_PREVISTA, potencia);
    }//GEN-LAST:event_jCmbBxPotenciaPrevistaItemStateChanged

    private boolean esValidoTablaPrevision(){
        String strPotencia = null;
        String denominacion;
        
        for(int i=0; i<jTblPrevision.getRowCount(); i++) {
            try {
                strPotencia = Utilidades.cambiarComa(modeloTablaPrevision.getValueAt(i, 2).toString());
                denominacion = modeloTablaPrevision.getValueAt(i, 1).toString();
                if(denominacion.length() > 30) {
                    Mensaje.error("La denominación no puede superar los 30 caracteres: '" + denominacion + "'");
                    return false;
                }
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
       return true;
    }

    private boolean esValidoTablaResumen() {
        String strPotCalc     = null;
        String strTensionCalc = null;
        String strIntCalc     = null;
        String strAislamiento = null;
        String strIntMax      = null;
        String strCcpia       = null;
        String strLongitud    = null;
        String strCaida       = null;

        try{
           for(int i=0; i<jTblResumen.getRowCount(); i++)
           {
               strPotCalc     = Utilidades.cambiarComa(modeloTablaResumen.getValueAt(i,2).toString());
               strTensionCalc = Utilidades.cambiarComa(modeloTablaResumen.getValueAt(i,3).toString());
               strIntCalc     = Utilidades.cambiarComa(modeloTablaResumen.getValueAt(i,4).toString());
               strAislamiento = Utilidades.cambiarComa(modeloTablaResumen.getValueAt(i,6).toString());
               strIntMax      = Utilidades.cambiarComa(modeloTablaResumen.getValueAt(i,8).toString());
               strCcpia       = Utilidades.cambiarComa(modeloTablaResumen.getValueAt(i,9).toString());
               strLongitud    = Utilidades.cambiarComa(modeloTablaResumen.getValueAt(i,10).toString());
               strCaida       = Utilidades.cambiarComa(modeloTablaResumen.getValueAt(i,11).toString());
           }

           return true;
       }
       catch(NumberFormatException e){
           Mensaje.error("Problemas con formato numeros en la tabla Resumen: "+e.getMessage());
           return false;
       }
       catch(Exception e){
           Mensaje.error("Problemas genéricos en la tabla Resumen: "+e.getMessage());
           return false;
       }
    }

    private void establecerRestricciones() {
        // Pestaña Suministro.
        jTxtFldDescripcionSumCompl.setDocument(new LimitadorCaracteres(jTxtFldDescripcionSumCompl, 50, false, false));

        //Pestaña Instalaciones Comprendidas
        jTxtFldDescripcion1.setDocument(new LimitadorCaracteres(jTxtFldDescripcion1, 50, false, false));
        jTxtFldDescripcion2A.setDocument(new LimitadorCaracteres(jTxtFldDescripcion2A, 25, false, false));
        jTxtFldDescripcion2B.setDocument(new LimitadorCaracteres(jTxtFldDescripcion2B, 25, false, false));
        jTxtFldDescripcion3A.setDocument(new LimitadorCaracteres(jTxtFldDescripcion3A, 25, false, false));
        jTxtFldDescripcion3B.setDocument(new LimitadorCaracteres(jTxtFldDescripcion3B, 25, false, false));
        jTxtFldTension1_1.setDocument(new LimitadorCaracteres(jTxtFldTension1_1, 7, true, false));
        jTxtFldTension1_2.setDocument(new LimitadorCaracteres(jTxtFldTension1_2, 7, true, false));
        jTxtFldTension1_3.setDocument(new LimitadorCaracteres(jTxtFldTension1_3, 7, true, false));
        jTxtFldTension1_4.setDocument(new LimitadorCaracteres(jTxtFldTension1_4, 7, true, false));
        jTxtFldTension1_5.setDocument(new LimitadorCaracteres(jTxtFldTension1_5, 7, true, false));
        jTxtFldTension1_6.setDocument(new LimitadorCaracteres(jTxtFldTension1_6, 7, true, false));
        jTxtFldTension2_1.setDocument(new LimitadorCaracteres(jTxtFldTension2_1, 7, true, false));
        jTxtFldTension2_2.setDocument(new LimitadorCaracteres(jTxtFldTension2_2, 7, true, false));
        jTxtFldTension2_3.setDocument(new LimitadorCaracteres(jTxtFldTension2_3, 7, true, false));
        jTxtFldTension2_4.setDocument(new LimitadorCaracteres(jTxtFldTension2_4, 7, true, false));
        jTxtFldTension2_5.setDocument(new LimitadorCaracteres(jTxtFldTension2_5, 7, true, false));
        jTxtFldTension2_6.setDocument(new LimitadorCaracteres(jTxtFldTension2_6, 7, true, false));
        jTxtFldPotInst1.setDocument(new LimitadorCaracteres(jTxtFldPotInst1, 10, true, true));
        jTxtFldPotInst2.setDocument(new LimitadorCaracteres(jTxtFldPotInst2, 10, true, true));
        jTxtFldPotInst3.setDocument(new LimitadorCaracteres(jTxtFldPotInst3, 10, true, true));
        jTxtFldPotInst4.setDocument(new LimitadorCaracteres(jTxtFldPotInst4, 10, true, true));
        jTxtFldPotInst5.setDocument(new LimitadorCaracteres(jTxtFldPotInst5, 10, true, true));
        jTxtFldPotInst6.setDocument(new LimitadorCaracteres(jTxtFldPotInst6, 10, true, true));
        jTxtFldResistTierraProt.setDocument(new LimitadorCaracteres(jTxtFldResistTierraProt, 6, true, true));

        //Pestaña Caract Generales
        jTxtFldTension1.setDocument(new LimitadorCaracteres(jTxtFldTension1, 3, true, false));
        jTxtFldTension2.setDocument(new LimitadorCaracteres(jTxtFldTension2, 3, true, false));
        jTxtFldSuperficieLocal.setDocument(new LimitadorCaracteres(jTxtFldSuperficieLocal, 5, true, false));
        jTxtFldSeccionAcometida.setDocument(new LimitadorCaracteres(jTxtFldSeccionAcometida, 20, false, false));
        jTxtFldTipoCGP.setDocument(new LimitadorCaracteres(jTxtFldTipoCGP, 20, false, false));
        jTxtFldIntensidadBase.setDocument(new LimitadorCaracteres(jTxtFldIntensidadBase, 5, true, true));
        jTxtFldIntensidadCartucho.setDocument(new LimitadorCaracteres(jTxtFldIntensidadCartucho, 5, true, true));
        jTxtFldSeccionLinea.setDocument(new LimitadorCaracteres(jTxtFldSeccionLinea, 20, false, false));
        jTxtFldTipoModuloMedida.setDocument(new LimitadorCaracteres(jTxtFldTipoModuloMedida, 25, false, false));
        jTxtFldInterruptorGralAutomatico.setDocument(new LimitadorCaracteres(jTxtFldInterruptorGralAutomatico, 10, true, true));
        jTxtFldIccProt.setDocument(new LimitadorCaracteres(jTxtFldIccProt, 10, true, true));
        jTxtFldInterruptorDiferencial.setDocument(new LimitadorCaracteres(jTxtFldInterruptorDiferencial, 10, true, true));
        jTxtFldSensibilidad.setDocument(new LimitadorCaracteres(jTxtFldSensibilidad, 10, true, true));
        jTxtFldElectrodos.setDocument(new LimitadorCaracteres(jTxtFldElectrodos, 8, false, false));
        jTxtFldLineaEnlace.setDocument(new LimitadorCaracteres(jTxtFldLineaEnlace, 10, true, true));
        jTxtFldLineaPrincipal.setDocument(new LimitadorCaracteres(jTxtFldLineaPrincipal, 10, true, true));
        jTxtFldNumInstIndivFinales.setDocument(new LimitadorCaracteres(jTxtFldNumInstIndivFinales, 4, true, false));
        jTxtFldPresupuestoTotal.setDocument(new LimitadorCaracteres(jTxtFldPresupuestoTotal, 15, true, true));

        //Pestaña Prevision cargas edificios viviendas
        jTxtFldNumViv1.setDocument(new LimitadorCaracteres(jTxtFldNumViv1, 4, true, false));
        jTxtFldNumViv2.setDocument(new LimitadorCaracteres(jTxtFldNumViv2, 4, true, false));
        jTxtFldSuperficieUnit1.setDocument(new LimitadorCaracteres(jTxtFldSuperficieUnit1, 10, true, true));
        jTxtFldSuperficieUnit2.setDocument(new LimitadorCaracteres(jTxtFldSuperficieUnit2, 10, true, true));
        jTxtFldDemandaMax1.setDocument(new LimitadorCaracteres(jTxtFldDemandaMax1, 10, true, false));
        jTxtFldDemandaMax2.setDocument(new LimitadorCaracteres(jTxtFldDemandaMax2, 10, true, false));
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

    private void setModeloTablas() {
        Vector v1 = new Vector();
        Vector v2 = new Vector();
        Vector v3 = new Vector();

        ResultSet rs = null;
         try{
             rs = bd.ejecSelect("SELECT * FROM TIPOS_CARGIND");
             while(rs.next())
                 v1.add(new ParCombo(rs.getString(1),rs.getString(2)));
             rs = bd.ejecSelect("SELECT * FROM TIPOS_CIRCUITO");
             while(rs.next())
                 v2.add(new ParCombo(rs.getString(1),rs.getString(2)));
             rs = bd.ejecSelect("SELECT * FROM TIPOS_INST_CIRCUITOS");
             while(rs.next())
                 v3.add(new ParCombo(rs.getString(1),rs.getString(1)+" - "+rs.getString(2)));
         }
         catch(SQLException e){
             Mensaje.error("FrmDatosTecnicosPnl.java: "+e.getMessage(), e);
         }

        // Crea el modelo
        modeloTablaPrevision = new ModeloTablaPrevision();
        modeloTablaResumen   = new ModeloTablaResumen();

        // Crea el control, pasándole el modelo
        controlPrevision = new ControlTablaPrevision (jTblPrevision, modeloTablaPrevision);
        controlResumen = new ControlTablaResumen (jTblResumen, modeloTablaResumen);
        jTblPrevision.setModel(modeloTablaPrevision);
        jTblResumen.setModel(modeloTablaResumen);

        //Coloco los JComboBox en las columnas correspondientes de sus tablas.
        TableColumn colPrevision0 = jTblPrevision.getColumnModel().getColumn(0);
        
        TableColumn colResumen0 = jTblResumen.getColumnModel().getColumn(0);
        TableColumn colResumen1 = jTblResumen.getColumnModel().getColumn(1);
        TableColumn colResumen2 = jTblResumen.getColumnModel().getColumn(2);
        TableColumn colResumen3 = jTblResumen.getColumnModel().getColumn(3);
        TableColumn colResumen4 = jTblResumen.getColumnModel().getColumn(4);
        TableColumn colResumen5 = jTblResumen.getColumnModel().getColumn(5);
        TableColumn colResumen6 = jTblResumen.getColumnModel().getColumn(6);
        TableColumn colResumen7 = jTblResumen.getColumnModel().getColumn(7);
        TableColumn colResumen8 = jTblResumen.getColumnModel().getColumn(8);
        TableColumn colResumen9 = jTblResumen.getColumnModel().getColumn(9);
        TableColumn colResumen10 = jTblResumen.getColumnModel().getColumn(10);
        TableColumn colResumen11 = jTblResumen.getColumnModel().getColumn(11);
        
        colPrevision0.setCellEditor(new MyComboBoxEditor(v1));
        //colPrevision0.setCellRenderer(new ComboBoxTablas(v1));
        colResumen0.setCellEditor(new MyComboBoxEditor(v2));
        //colResumen0.setCellRenderer(new ComboBoxTablas(v2));
        colResumen7.setCellEditor(new MyComboBoxEditor(v3));
        //colResumen7.setCellRenderer(new ComboBoxTablas(v3));
        
        //Tamaños de las columnas
        colResumen0.setPreferredWidth(125);
        colResumen1.setPreferredWidth(200);
        colResumen2.setPreferredWidth(60);
        colResumen3.setPreferredWidth(60);
        colResumen4.setPreferredWidth(60);
        colResumen5.setPreferredWidth(60);
        colResumen6.setPreferredWidth(60);
        colResumen7.setPreferredWidth(100);
        colResumen8.setPreferredWidth(60);
        colResumen9.setPreferredWidth(60);
        colResumen10.setPreferredWidth(60);
        colResumen11.setPreferredWidth(60);

        //Paso el textfield encargado de mostrar la suma de potencias de la tabla.
        this.modeloTablaPrevision.setJTxtFldSumaPotencia(jTxtFldSumaPotencia);
    }

    private void agregarDatosIniciales() {
        String consulta = "SELECT * FROM INSTALACIONES WHERE INID = "+Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();
        try{
            ResultSet rs = bd.ejecSelect(consulta);
            rs.next();

            String tension1        = rs.getString("INTENSION1");
            String tension2        = rs.getString("INTENSION2");

            if(tension1 == null || tension1.equals("") || tension1.equals("-1"))
                tension1 = "";
            if(tension2 == null || tension2.equals("") || tension2.equals("-1") || tension2.equals("0"))
                tension2 = "";

            //Actualiza los modelos de los combos de la pestaña Suministro.
            agregarTensionesACombos();

            if(tension1.equals("230") && tension2.equals(""))
            {
                jCmbBxPotenciaNormal.setModel(m1);
                jCmbBxPotenciaCompl.setModel(m11);
                jCmbBxPotenciaPrevista.setModel(m111);
            }
            else if(tension1.equals("230") && tension2.equals("130"))
            {
                jCmbBxPotenciaNormal.setModel(m2);
                jCmbBxPotenciaCompl.setModel(m22);
                jCmbBxPotenciaPrevista.setModel(m222);
            }
            else if(tension1.equals("400") && tension2.equals("230"))
            {
                jCmbBxPotenciaNormal.setModel(m3);
                jCmbBxPotenciaCompl.setModel(m33);
                jCmbBxPotenciaPrevista.setModel(m333);
            }
            else
            {
                jCmbBxPotenciaNormal.setModel(mTemp);
                jCmbBxPotenciaCompl.setModel(mTemp2);
                jCmbBxPotenciaPrevista.setModel(mTemp3);
            }

            //Pestaña Caract Instalación, apartado: Generales            
            String potPrevista     = Utilidades.cambiarPunto(rs.getString("INPOTPREVISTA"));
            String superfLocal     = Utilidades.cambiarPunto(rs.getString("INSUPERFICIE"));
            String inmmid          = rs.getString("INMMID");
            String inticod         = rs.getString("INTICOD");
            String inUsoVarios     = rs.getString("INUSOVARIOS");
            int intinum            = rs.getInt("INTINUM");
            int inrgid             = rs.getInt("INRGID");

            jTxtFldTension1.setText(tension1);
            jTxtFldTension2.setText(tension2);
            if (potPrevista.indexOf(",0")>0) {
                jCmbBxPotenciaPrevista.setSelectedItem(potPrevista.substring(0,potPrevista.indexOf(",0")));
            } else {
                jCmbBxPotenciaPrevista.setSelectedItem(potPrevista);
            }
            jTxtFldVariosSinClasificar.setText(inUsoVarios);
            jTxtFldSuperficieLocal.setText(superfLocal);
            
            SetInfoSuministro aux =new SetInfoSuministro(frmPrincipal, ((Integer)Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID)).intValue());
            aux.getInfoBD();
            aux.rellenarFormulario();

            ParCombo pcTipoInst;
            ParCombo pcMemoriaPor;
            ParCombo pcTemp;
           
            for(int i=0; i<jCmbBxTipoInst.getItemCount(); i++)
            {
                pcTemp = (ParCombo)jCmbBxTipoInst.getItemAt(i);
                if(pcTemp.getKeyString().equals(inticod) && pcTemp.getKeyInt() == intinum)
                    jCmbBxTipoInst.setSelectedItem(pcTemp);
            }

            for(int i=0; i<jCmbBxMemoriaPor.getItemCount(); i++)
            {
                pcTemp = (ParCombo)jCmbBxMemoriaPor.getItemAt(i);
                if(pcTemp.getKeyString().equals(inmmid))
                    jCmbBxMemoriaPor.setSelectedItem(pcTemp);
            }

            for(int i=0; i<jCmbBxReglamentos.getItemCount(); i++)
            {
                pcTemp = (ParCombo)jCmbBxReglamentos.getItemAt(i);
                if(pcTemp.getKeyInt() == inrgid)
                    jCmbBxReglamentos.setSelectedItem(pcTemp);
            }
            
            jTextAreaMemo.setText(rs.getString("INMEMO"));
        }
        catch(SQLException e){
            Mensaje.error("FrmDatosTecnicosPnl.java: "+e.getMessage(), e);
        }
    }

    private void agregarTensionesACombos() {
        m1 = new DefaultComboBoxModel();
        m11 = new DefaultComboBoxModel();
        m111 = new DefaultComboBoxModel();
        m2 = new DefaultComboBoxModel();
        m22 = new DefaultComboBoxModel();
        m222 = new DefaultComboBoxModel();
        m3 = new DefaultComboBoxModel();
        m33 = new DefaultComboBoxModel();
        m333 = new DefaultComboBoxModel();
        mTemp = new DefaultComboBoxModel();
        mTemp2 = new DefaultComboBoxModel();
        mTemp3 = new DefaultComboBoxModel();
        ParCombo pc = null;

        try{
            m11.addElement("0");
            m22.addElement("0");
            m33.addElement("0");
            ResultSet rs = bd.ejecSelect("SELECT * FROM POTNORMALIZADAS");
            while(rs.next()){
                String t1 = rs.getString("PNT1");
                String t2 = rs.getString("PNT2");
                String pot = rs.getString("PNPOT");

                if(t1.equals("230") && (t2 == null || t2.equals("")))
                {
                    m1.addElement(pot);
                    m11.addElement(pot);
                    m111.addElement(pot);
                }
                else if(t1.equals("230") && t2.equals("130"))
                {
                    m2.addElement(pot);
                    m22.addElement(pot);
                    m222.addElement(pot);
                }
                else if(t1.equals("400") && t2.equals("230"))
                {
                    m3.addElement(pot);
                    m33.addElement(pot);
                    m333.addElement(pot);
                }
            }
            mTemp.addElement("");
            mTemp2.addElement("");
            mTemp3.addElement("");
        }
        catch(SQLException e){
            Mensaje.error("FrmDatosTecnicosPnl.java: "+e.getMessage(), e);
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBttnPrevisionFilaBorrar;
    public javax.swing.JButton jBttnPrevisionFilaNueva;
    private javax.swing.JButton jBttnResumenFilaBorrar;
    private javax.swing.JButton jBttnResumenFilaNueva;
    public javax.swing.JComboBox jCmbBxGradoElect1;
    public javax.swing.JComboBox jCmbBxGradoElect2;
    public javax.swing.JComboBox jCmbBxMaterialAcometida;
    public javax.swing.JComboBox jCmbBxMaterialLinea;
    public javax.swing.JComboBox jCmbBxMemoriaPor;
    public javax.swing.JComboBox jCmbBxPotenciaCompl;
    public javax.swing.JComboBox jCmbBxPotenciaNormal;
    public javax.swing.JComboBox jCmbBxPotenciaPrevista;
    public javax.swing.JComboBox jCmbBxPuntoConexionAcometida;
    public javax.swing.JComboBox jCmbBxReglamentos;
    public javax.swing.JComboBox jCmbBxSituacionModuloMedida;
    public javax.swing.JComboBox jCmbBxTipoAcometida;
    public javax.swing.JComboBox jCmbBxTipoInst;
    public javax.swing.JComboBox jCmbBxTipoLinea;
    public javax.swing.JComboBox jCmbBxTipoPuestaTierra;
    public javax.swing.JComboBox jCmbBxTipoUso1;
    public javax.swing.JComboBox jCmbBxTipoUso2;
    public javax.swing.JComboBox jCmbBxTipoUso3;
    public javax.swing.JComboBox jCmbBxTipoUso4;
    public javax.swing.JComboBox jCmbBxTipoUso5;
    public javax.swing.JComboBox jCmbBxTipoUso6;
    public javax.swing.JComboBox jCmbBxUsoInstalacion;
    public javax.swing.JComboBox jComboBoxDemandaMax1;
    public javax.swing.JComboBox jComboBoxDemandaMax2;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
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
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
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
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelMemo;
    private javax.swing.JPanel jPnlCaractGenerales;
    private javax.swing.JPanel jPnlInstComprendidas;
    private javax.swing.JPanel jPnlPrevisionCargasEdificios;
    private javax.swing.JPanel jPnlPrevisionCargasIndustriales;
    private javax.swing.JPanel jPnlPrevisionCargasOficinas;
    private javax.swing.JPanel jPnlResumen;
    private javax.swing.JPanel jPnlSuministros;
    private javax.swing.JScrollPane jScrTblPrevision;
    private javax.swing.JScrollPane jScrTblResumen;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTbbdPnDatosTecnicos;
    public javax.swing.JTable jTblPrevision;
    public javax.swing.JTable jTblResumen;
    public javax.swing.JTextArea jTextAreaMemo;
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
    public javax.swing.JTextField jTxtFldDescripcion1;
    public javax.swing.JTextField jTxtFldDescripcion2A;
    public javax.swing.JTextField jTxtFldDescripcion2B;
    public javax.swing.JTextField jTxtFldDescripcion3A;
    public javax.swing.JTextField jTxtFldDescripcion3B;
    public javax.swing.JTextField jTxtFldDescripcion4A;
    public javax.swing.JTextField jTxtFldDescripcion4B;
    public javax.swing.JTextField jTxtFldDescripcion4C;
    public javax.swing.JTextField jTxtFldDescripcion4D;
    public javax.swing.JTextField jTxtFldDescripcion4E;
    public javax.swing.JTextField jTxtFldDescripcion4F;
    public javax.swing.JTextField jTxtFldDescripcion4G;
    public javax.swing.JTextField jTxtFldDescripcion4H;
    public javax.swing.JTextField jTxtFldDescripcion4I;
    public javax.swing.JTextField jTxtFldDescripcion4J;
    public javax.swing.JTextField jTxtFldDescripcion4K;
    public javax.swing.JTextField jTxtFldDescripcion4L;
    public javax.swing.JTextField jTxtFldDescripcion4M;
    public javax.swing.JTextField jTxtFldDescripcion5A;
    public javax.swing.JTextField jTxtFldDescripcion5B;
    public javax.swing.JTextField jTxtFldDescripcion5C;
    public javax.swing.JTextField jTxtFldDescripcion5D;
    public javax.swing.JTextField jTxtFldDescripcion5E;
    public javax.swing.JTextField jTxtFldDescripcion5F;
    public javax.swing.JTextField jTxtFldDescripcion5G;
    public javax.swing.JTextField jTxtFldDescripcion5H;
    public javax.swing.JTextField jTxtFldDescripcion5I;
    public javax.swing.JTextField jTxtFldDescripcion5J;
    public javax.swing.JTextField jTxtFldDescripcion5K;
    public javax.swing.JTextField jTxtFldDescripcion5L;
    public javax.swing.JTextField jTxtFldDescripcion5M;
    public javax.swing.JTextField jTxtFldDescripcion6A;
    public javax.swing.JTextField jTxtFldDescripcion6B;
    public javax.swing.JTextField jTxtFldDescripcion6C;
    public javax.swing.JTextField jTxtFldDescripcion6D;
    public javax.swing.JTextField jTxtFldDescripcion6E;
    public javax.swing.JTextField jTxtFldDescripcion6F;
    public javax.swing.JTextField jTxtFldDescripcion6G;
    public javax.swing.JTextField jTxtFldDescripcion6H;
    public javax.swing.JTextField jTxtFldDescripcion6I;
    public javax.swing.JTextField jTxtFldDescripcion6J;
    public javax.swing.JTextField jTxtFldDescripcion6K;
    public javax.swing.JTextField jTxtFldDescripcion6L;
    public javax.swing.JTextField jTxtFldDescripcion6M;
    public javax.swing.JTextField jTxtFldDescripcionOficinas;
    public javax.swing.JTextField jTxtFldDescripcionSumCompl;
    public javax.swing.JTextField jTxtFldElectrodos;
    public javax.swing.JTextField jTxtFldGarajes;
    public javax.swing.JTextField jTxtFldIccProt;
    public javax.swing.JTextField jTxtFldIntensidadBase;
    public javax.swing.JTextField jTxtFldIntensidadCartucho;
    public javax.swing.JTextField jTxtFldInterruptorDiferencial;
    public javax.swing.JTextField jTxtFldInterruptorGralAutomatico;
    public javax.swing.JTextField jTxtFldLineaEnlace;
    public javax.swing.JTextField jTxtFldLineaPrincipal;
    public javax.swing.JTextField jTxtFldNumInstIndivFinales;
    public javax.swing.JTextField jTxtFldNumTotalEstabOficinas;
    public javax.swing.JTextField jTxtFldNumTotalOficinas;
    public javax.swing.JTextField jTxtFldNumViv1;
    public javax.swing.JTextField jTxtFldNumViv2;
    public javax.swing.JTextField jTxtFldOtrosServOficinas;
    public javax.swing.JTextField jTxtFldOtrosServiciosViviendas;
    public javax.swing.JTextField jTxtFldPotEspecPrevista;
    public javax.swing.JTextField jTxtFldPotInst1;
    public javax.swing.JTextField jTxtFldPotInst2;
    public javax.swing.JTextField jTxtFldPotInst3;
    public javax.swing.JTextField jTxtFldPotInst4;
    public javax.swing.JTextField jTxtFldPotInst5;
    public javax.swing.JTextField jTxtFldPotInst6;
    public javax.swing.JTextField jTxtFldPotPrevistaOficinas;
    public javax.swing.JTextField jTxtFldPresupuestoTotal;
    public javax.swing.JTextField jTxtFldResistTierraProt;
    public javax.swing.JTextField jTxtFldSeccionAcometida;
    public javax.swing.JTextField jTxtFldSeccionLinea;
    public javax.swing.JTextField jTxtFldSensibilidad;
    public javax.swing.JTextField jTxtFldSumaPotencia;
    public javax.swing.JTextField jTxtFldSuperficieLocal;
    public javax.swing.JTextField jTxtFldSuperficieOficinas;
    public javax.swing.JTextField jTxtFldSuperficieTotalEstabOficinas;
    public javax.swing.JTextField jTxtFldSuperficieUnit1;
    public javax.swing.JTextField jTxtFldSuperficieUnit2;
    public javax.swing.JTextField jTxtFldSuperficieUtilTotal;
    public javax.swing.JTextField jTxtFldTension1;
    public javax.swing.JTextField jTxtFldTension1_1;
    public javax.swing.JTextField jTxtFldTension1_2;
    public javax.swing.JTextField jTxtFldTension1_3;
    public javax.swing.JTextField jTxtFldTension1_4;
    public javax.swing.JTextField jTxtFldTension1_5;
    public javax.swing.JTextField jTxtFldTension1_6;
    public javax.swing.JTextField jTxtFldTension2;
    public javax.swing.JTextField jTxtFldTension2_1;
    public javax.swing.JTextField jTxtFldTension2_2;
    public javax.swing.JTextField jTxtFldTension2_3;
    public javax.swing.JTextField jTxtFldTension2_4;
    public javax.swing.JTextField jTxtFldTension2_5;
    public javax.swing.JTextField jTxtFldTension2_6;
    public javax.swing.JTextField jTxtFldTipoCGP;
    public javax.swing.JTextField jTxtFldTipoModuloMedida;
    public javax.swing.JTextField jTxtFldVariosSinClasificar;
    // End of variables declaration//GEN-END:variables

}
