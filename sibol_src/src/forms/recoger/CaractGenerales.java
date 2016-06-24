/*
 * CaractGenerales.java
 *
 * Created on 21 de agosto de 2006, 9:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package forms.recoger;

import forms.FrmDatosTecnicosPnl;
import funciones.BaseDatos;
import funciones.ParCombo;
import funciones.Sesion;
import funciones.Utilidades;
import java.sql.SQLException;
import java.util.regex.Pattern;
import main.Constantes;

/**
 *
 * @author enrique
 */
public class CaractGenerales implements IntfzValidarDatos{
    
    private double potenciaPrevista, superficieLocal;
    private int tension1, tension2;
    private String memoriaPor, tipoInstalacion1;
    private int tipoInstalacion2, idUsoInstalacion, idReglamentos;
    private String variosSinClasificar;
    private String puntoConexionAcometida, materialAcometida, tipoAcometida;
    private String tipoCGP;
    private double intensidadBase, intensidadCartucho;
    private String seccionAcometida, tipoLinea, materialLinea;
    private String tipoModuloMedida, situacionModuloMedida, seccionLinea;
    private double inGralAutomatico, icc, inDiferencial, sensibilidad;
    private String tipoPuestaTierra, electrodos;
    private double lineaEnlace, lineaPrincipal;
    private double ohmios;
    private int numInstIndivFinales;
    private double presupuestoTotal;
    
    private FrmDatosTecnicosPnl frm;
    private BaseDatos bd;
    private ParCombo pc;
    
    private String txtErrorCaractGenerales;
    
    public CaractGenerales() {
        frm = (FrmDatosTecnicosPnl)Sesion.getInstance().getValorHt("objFrmDatosTecnicosPnl");
        bd = Sesion.getInstance().getBaseDatos();
    }
    
    public boolean esValido()
    {
        boolean generales = true;
        boolean acometida = true;
        boolean seguridad = true;
        boolean lineaGeneral = true;
        boolean moduloMedida = true;
        boolean proteccion = true;
        boolean tierra = true;
        boolean presupuesto = true;
        
        String txtGeneralesIni      = "\n     En \"GENERALES\":\n";
        String txtAcometidaIni      = "\n     En \"ACOMETIDA\":\n";
        String txtSeguridadIni      = "\n     En \" C.G.P. o C/C DE SEGURIDAD\":\n";
        String txtLineaGeneralIni   = "\n     En \"LÍNEA GENERAL DE ALIMENTACIÓN O DERIVACIÓN INDIVIDUAL\":\n";
        String txtModuloMedidaIni   = "\n     En \"MODULO DE MEDIDA\":\n";
        String txtProteccionIni     = "\n     En \"PROTECCIÓN MAGNETOTÉRMICA / DIFERENCIAL\":\n";
        String txtPuestaTierraIni   = "\n     En \"PUESTA A TIERRA\":\n";
        String txtPresupuestoIni    = "\n     En \"PRESUPUESTO\":\n";
        
        String txtGenerales         = "";
        String txtAcometida         = "";
        String txtSeguridad         = "";
        String txtLineaGeneral      = "";
        String txtModuloMedida      = "";
        String txtProteccion        = "";
        String txtPuestaTierra      = "";
        String txtPresupuesto       = "";
        
        txtErrorCaractGenerales = "\n * Pestaña \"Características generales\":";
        
        
        pc = (ParCombo)frm.jCmbBxUsoInstalacion.getSelectedItem();
        idUsoInstalacion = pc.getKeyInt();
        variosSinClasificar = frm.jTxtFldVariosSinClasificar.getText();
        pc = (ParCombo)frm.jCmbBxMemoriaPor.getSelectedItem();
        memoriaPor = pc.getKeyString();
        pc = (ParCombo)frm.jCmbBxTipoInst.getSelectedItem();
        tipoInstalacion1 = pc.getKeyString();
        tipoInstalacion2 = pc.getKeyInt();
        pc = (ParCombo)frm.jCmbBxReglamentos.getSelectedItem();
        idReglamentos = pc.getKeyInt();
        
        
        seccionAcometida = frm.jTxtFldSeccionAcometida.getText();
        pc = (ParCombo)frm.jCmbBxPuntoConexionAcometida.getSelectedItem();
        puntoConexionAcometida = pc.getKeyString();
        pc = (ParCombo)frm.jCmbBxMaterialAcometida.getSelectedItem();
        materialAcometida = pc.getKeyString();
        pc = (ParCombo)frm.jCmbBxTipoAcometida.getSelectedItem();
        tipoAcometida = pc.getKeyString();
        
        tipoCGP = frm.jTxtFldTipoCGP.getText();
        intensidadBase = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldIntensidadBase.getText()));
        intensidadCartucho = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldIntensidadCartucho.getText()));
        seccionLinea = frm.jTxtFldSeccionLinea.getText();
        
        pc = (ParCombo)frm.jCmbBxTipoLinea.getSelectedItem();
        tipoLinea = pc.getKeyString();
        pc = (ParCombo)frm.jCmbBxMaterialLinea.getSelectedItem();
        materialLinea = pc.getKeyString();
        
        tipoModuloMedida = frm.jTxtFldTipoModuloMedida.getText();
        pc = (ParCombo)frm.jCmbBxSituacionModuloMedida.getSelectedItem();
        situacionModuloMedida = pc.getKeyString();
        
        inGralAutomatico = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldInterruptorGralAutomatico.getText()));
        icc = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldIccProt.getText()));
        inDiferencial = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldInterruptorDiferencial.getText()));
        sensibilidad = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldSensibilidad.getText()));
        lineaEnlace = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldLineaEnlace.getText()));
        lineaPrincipal = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldLineaPrincipal.getText()));
        electrodos = frm.jTxtFldElectrodos.getText();        
        pc = (ParCombo)frm.jCmbBxTipoPuestaTierra.getSelectedItem();
        tipoPuestaTierra = pc.getKeyString();
        
        
        //Generales
        if(frm.jTxtFldTension1.getText() == null || frm.jTxtFldTension1.getText().equals(""))
        {
            tension1 = 0;
            generales = false;
        }
        else
              tension1 = Integer.parseInt(frm.jTxtFldTension1.getText());
        
        if(frm.jTxtFldTension2.getText() == null || frm.jTxtFldTension2.getText().equals(""))
            tension2 = 0;
        else
              tension2 = Integer.parseInt(frm.jTxtFldTension2.getText());
        
        if("".equals((String)frm.jCmbBxPotenciaPrevista.getSelectedItem()))
        {
            generales=false;
            txtGenerales += "      - POTENCIA PREVISTA no debe de estar vacío.\n";
        }
        else{
            try{
               if(Pattern.matches("[0-9]{1,4},[0-9]{0,2}|[0-9]{1,5},[0-9]{0,1}|[0-9]{1,6}", (String)frm.jCmbBxPotenciaPrevista.getSelectedItem()))
               {
                   potenciaPrevista = Double.parseDouble(Utilidades.cambiarComa((String)frm.jCmbBxPotenciaPrevista.getSelectedItem()));
               }
               else
               {
                   generales=false;
                   txtGenerales += "      - POTENCIA PREVISTA su valor sea un número válido. Ej: 111,12.\n";                   
               }
            }
            catch(NumberFormatException e){
                       generales = false;
                       txtGenerales += "      - POTENCIA PREVISTA su valor sea un número válido. Ej: 10 ó 10,44.\n";
            }
        }
        
        if(frm.jTxtFldSuperficieLocal.getText().equals(""))
        {
            generales=false;
            txtGenerales += "      - SUPERFICIE LOCAL no debe de estar vacío.\n";
        }
        else{
            try{
                   superficieLocal = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldSuperficieLocal.getText()));
            }
            catch(NumberFormatException e){
                       generales = false;
                       txtGenerales += "      - SUPERFICIE LOCAL su valor sea un número válido. Ej: 10 ó 10,44.\n";
            }
        }

/*        //Acometida
        if(frm.jTxtFldSeccionAcometida.getText().equals(""))
        {
            acometida=false;
            txtAcometida += "      - SECCIÓN ACOMETIDA no debe de estar vacío.\n";
        }
        else{
            seccionAcometida = frm.jTxtFldSeccionAcometida.getText();
        }

        pc = (ParCombo)frm.jCmbBxPuntoConexionAcometida.getSelectedItem();
        puntoConexionAcometida = pc.getKeyString();
        pc = (ParCombo)frm.jCmbBxMaterialAcometida.getSelectedItem();
        materialAcometida = pc.getKeyString();
        pc = (ParCombo)frm.jCmbBxTipoAcometida.getSelectedItem();
        tipoAcometida = pc.getKeyString();
        
        //Seguridad
        tipoCGP = frm.jTxtFldTipoCGP.getText();
        if(tipoCGP.equals(""))
        {
            seguridad=false;
            txtSeguridad += "      - TIPO DE C.G.P. o C/C DE SEGURIDAD no debe de estar vacío.\n";
        }
        
        if(frm.jTxtFldIntensidadBase.getText().equals(""))
        {
            seguridad=false;
            txtSeguridad += "      - IN. BASE no debe de estar vacío.\n";
        }
        else{
            try{
                   intensidadBase = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldIntensidadBase.getText()));
            }
            catch(NumberFormatException e){
                       seguridad = false;
                       txtSeguridad += "      - IN. BASE su valor sea un número válido. Ej: 10 ó 10,44.\n";
            }
        }
        
        if(frm.jTxtFldIntensidadCartucho.getText().equals(""))
        {
            seguridad=false;
            txtSeguridad += "      - IN. CARTUCHO no debe de estar vacío.\n";
        }
        else{
            try{
                   intensidadCartucho = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldIntensidadCartucho.getText()));
            }
            catch(NumberFormatException e){
                       seguridad = false;
                       txtSeguridad += "      - IN. CARTUCHO su valor sea un número válido. Ej: 10 ó 10,44.\n";
            }
        }

        //Linea general de alimentación
        if(frm.jTxtFldSeccionLinea.getText().equals(""))
        {
            lineaGeneral=false;
            txtLineaGeneral += "      - SECCIÓN de LINEA GENERAL DE ALIMENTACIÓN O DERIVACIÓN INDIVIDUAL no debe de estar vacío.\n";
        }
        else{
            seccionLinea = frm.jTxtFldSeccionLinea.getText();
        }
        
        pc = (ParCombo)frm.jCmbBxTipoLinea.getSelectedItem();
        tipoLinea = pc.getKeyString();
        pc = (ParCombo)frm.jCmbBxMaterialLinea.getSelectedItem();
        materialLinea = pc.getKeyString();
        
        //Módulo medida
        if(frm.jTxtFldTipoModuloMedida.getText().equals(""))
        {
            moduloMedida=false;
            txtModuloMedida += "      - TIPO DE MÓDULO DE MEDIDA no debe de estar vacío.\n";
        }
        else{
            try{
                   tipoModuloMedida = frm.jTxtFldTipoModuloMedida.getText();
            }
            catch(NumberFormatException e){
                       moduloMedida = false;
                       txtModuloMedida += "      - TIPO DE MÓDULO DE MEDIDA su valor sea un número válido. Ej: 10 ó 10,44.\n";
            }
        }
        
        pc = (ParCombo)frm.jCmbBxSituacionModuloMedida.getSelectedItem();
        situacionModuloMedida = pc.getKeyString();
        
        //Proteccion mag...
        if(frm.jTxtFldInterruptorGralAutomatico.getText().equals(""))
        {
            proteccion=false;
            txtProteccion += "      - INST. GENERAL AUTOMÁTICO no debe de estar vacío.\n";
        }
        else{
            try{
                   inGralAutomatico = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldInterruptorGralAutomatico.getText()));
            }
            catch(NumberFormatException e){
                       proteccion = false;
                       txtProteccion += "      - INST. GENERAL AUTOMÁTICO su valor sea un número válido. Ej: 10 ó 10,44.\n";
            }
        }
        if(frm.jTxtFldIccProt.getText().equals(""))
        {
            proteccion=false;
            txtProteccion += "      - ICC no debe de estar vacío.\n";
        }
        else{
            try{
                   icc = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldIccProt.getText()));
            }
            catch(NumberFormatException e){
                       proteccion = false;
                       txtProteccion += "      - ICC su valor sea un número válido. Ej: 10 ó 10,44.\n";
            }
        }
        if(frm.jTxtFldInterruptorDiferencial.getText().equals(""))
        {
            proteccion=false;
            txtProteccion += "      - INT. DIFERENCIAL no debe de estar vacío.\n";
        }
        else{
            try{
                   inDiferencial = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldInterruptorDiferencial.getText()));
            }
            catch(NumberFormatException e){
                       proteccion = false;
                       txtProteccion += "      - INT. DIFERENCIAL su valor sea un número válido. Ej: 10 ó 10,44.\n";
            }
        }
        if(frm.jTxtFldSensibilidad.getText().equals(""))
        {
            proteccion=false;
            txtProteccion += "      - SENSIBILIDAD no debe de estar vacío.\n";
        }
        else{
            try{
                   sensibilidad = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldSensibilidad.getText()));
            }
            catch(NumberFormatException e){
                       proteccion = false;
                       txtProteccion += "      - SENSIBILIDAD su valor sea un número válido. Ej: 10 ó 10,44.\n";
            }
        }
        
        //Puesta a tierra
        if(frm.jTxtFldLineaEnlace.getText().equals(""))
        {
            tierra=false;
            txtPuestaTierra += "      - LÍNEA ENLACE no debe de estar vacío.\n";
        }
        else{
            try{
                   lineaEnlace = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldLineaEnlace.getText()));
            }
            catch(NumberFormatException e){
                       tierra = false;
                       txtPuestaTierra += "      - LÍNEA ENLACE enlace su valor sea un número válido. Ej: 10 ó 10,44.\n";
            }
        }
        
        if(frm.jTxtFldLineaPrincipal.getText().equals(""))
        {
            tierra=false;
            txtPuestaTierra += "      - LÍNEA PRINCIPAL no debe de estar vacío.\n";
        }
        else{
            try{
                   lineaPrincipal = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldLineaPrincipal.getText()));
            }
            catch(NumberFormatException e){
                       tierra = false;
                       txtPuestaTierra += "      - LÍNEA PRINCIPAL su valor sea un número válido. Ej: 10 ó 10,44.\n";
            }
        }
        
        electrodos = frm.jTxtFldElectrodos.getText();
        if(electrodos.equals("")){
            tierra=false;
            txtPuestaTierra += "      - ELECTRODOS no debe de estar vacío.\n";
        }
        
        pc = (ParCombo)frm.jCmbBxTipoPuestaTierra.getSelectedItem();
        tipoPuestaTierra = pc.getKeyString();
           
 */          
        //Presupuesto
        if(frm.jTxtFldNumInstIndivFinales.getText().equals(""))
        {
            presupuesto=false;
            txtPresupuesto += "      - Nº DE INSTALACIONES INDIVIDUALES FINALES no debe de estar vacío.\n";
        }
        else{
            numInstIndivFinales = Integer.parseInt(frm.jTxtFldNumInstIndivFinales.getText());
        }
        
        if(frm.jTxtFldPresupuestoTotal.getText().equals(""))
        {
            presupuesto=false;
            txtPresupuesto += "      - PRESUPUESTO TOTAL no debe de estar vacío.\n";
        }
        else{
            try{
                   presupuestoTotal = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldPresupuestoTotal.getText()));
            }
            catch(NumberFormatException e){
                       presupuesto = false;
                       txtPresupuesto += "      - PRESUPUESTO TOTAL su valor sea un número válido. Ej: 10 ó 10,44.\n";
            }
        }
        
        //Casilla Ohmios
       if(frm.jTxtFldResistTierraProt.getText().equals(""))
       {
               txtPuestaTierra += "      - OHMIOS no debe de estar vacío.\n";
               tierra = false;
       } else{
                try
                {
                    if(Pattern.matches("[0-9]{0,3},[0-9]{0,2}|[0-9]{0,4},[0-9]{0,1}|[0-9]{0,5}", frm.jTxtFldResistTierraProt.getText()))
                    {
                        ohmios = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldResistTierraProt.getText()));
                    }
                    else
                    {
                        tierra = false;
                        txtPuestaTierra += "      - OHMIOS sea un número válido. Ej: 110,44.\n";
                    }
                                   
                }
                catch(NumberFormatException e){
                    txtPuestaTierra += "      - OHMIOS sea un número válido. Ej: 10 ó 10,44.\n";
                    tierra = false;
                }
       }
        
        if(generales && acometida && seguridad && lineaGeneral && moduloMedida  && proteccion  && tierra  && presupuesto)
           return true;
        
        
        if(!generales)
            txtErrorCaractGenerales += txtGeneralesIni + txtGenerales;
        if(!acometida)
            txtErrorCaractGenerales += txtAcometidaIni + txtAcometida;
        if(!seguridad)
            txtErrorCaractGenerales += txtSeguridadIni + txtSeguridad;
        if(!lineaGeneral)
            txtErrorCaractGenerales += txtLineaGeneralIni + txtLineaGeneral;
        if(!moduloMedida)
            txtErrorCaractGenerales += txtModuloMedidaIni + txtModuloMedida;
        if(!proteccion)
            txtErrorCaractGenerales += txtProteccionIni + txtProteccion;
        if(!tierra)
            txtErrorCaractGenerales += txtPuestaTierraIni + txtPuestaTierra;
        if(!presupuesto)
            txtErrorCaractGenerales += txtPresupuestoIni + txtPresupuesto;
        
        Sesion.getInstance().setValorHt("txtErrorCaractGenerales",txtErrorCaractGenerales);
        return false;
    }
    
    public void insertarBD() throws SQLException
    {
        //Recojo la ID de la sesión de la tabla INSTALACIONES
        ParCombo pc = (ParCombo)Sesion.getInstance().getValorHt(Constantes.SES_TIPO_INSTALACION_ELEGIDA);
        String idInstalacion = Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();
                
        String consultaGenerales = "UPDATE INSTALACIONES SET INTENSION1="+tension1+", INTENSION2="+tension2+", " +
                " INUSOVARIOS='"+variosSinClasificar+"', INUIID="+idUsoInstalacion+", INRGID="+idReglamentos+", "+
                " INPOTPREVISTA="+potenciaPrevista+", INMMID='"+memoriaPor+"', INTICOD='"+tipoInstalacion1+"', " +
                " INTINUM="+tipoInstalacion2+", INSUPERFICIE="+superficieLocal+"" +
                " WHERE INID="+idInstalacion;
        
        String consultaAcometida = "UPDATE INSTALACIONES SET INPCIDACOMETIDA='"+puntoConexionAcometida+"', " +
                " INTLIDACOMETIDA='"+tipoAcometida+"', INSECCACOMETIDA='"+seccionAcometida+"', " +
                " INMAIDACOMETIDA='"+materialAcometida+"' " +
                " WHERE INID="+idInstalacion;
                
        String consultaSeguridad = "UPDATE INSTALACIONES SET INCGPTIPO='"+tipoCGP+"', " +
                " INCGPIBASE='"+intensidadBase+"', INCGPICARTUCHO='"+intensidadCartucho+"' " +
                " WHERE INID="+idInstalacion;
        
        String consultaLineaGeneral = "UPDATE INSTALACIONES SET INTLGENERAL='"+tipoLinea+"', " +
                " INSECCGENERAL='"+seccionLinea+"', INMAIDGENERAL='"+materialLinea+"' " +
                " WHERE INID="+idInstalacion;
        
        String consultaModuloMedida = "UPDATE INSTALACIONES SET INSMID='"+situacionModuloMedida+"', " +
                " INMODULOMED='"+tipoModuloMedida+"' WHERE INID="+idInstalacion;
        
        String consultaProteccion = "UPDATE INSTALACIONES SET ININTGRAL="+inGralAutomatico+", " +
                " INICC="+icc+", ININTDIF="+inDiferencial+", " +
                " INSENSIBILIDAD="+sensibilidad+" " +
                " WHERE INID="+idInstalacion;
        
        String consultaPuestaTierra = "UPDATE INSTALACIONES SET INTTID='"+tipoPuestaTierra+"', " +
                " INELECTRODOS='"+electrodos+"', INSECCLINENLACE="+lineaEnlace+", INRESTIERRA='"+ohmios+"', " +
                " INSECCLINPPAL="+lineaPrincipal+" " +
                " WHERE INID="+idInstalacion;
        
        String consultaPresupuesto = "UPDATE INSTALACIONES SET INNUMINSTALACIONES="+numInstIndivFinales+", " +
                " INPRESUPUESTO="+presupuestoTotal+" WHERE INID="+idInstalacion;
        
            bd.ejecModificacion(consultaGenerales);
            bd.ejecModificacion(consultaAcometida);
            bd.ejecModificacion(consultaSeguridad);
            bd.ejecModificacion(consultaLineaGeneral);
            bd.ejecModificacion(consultaModuloMedida);
            bd.ejecModificacion(consultaProteccion);
            bd.ejecModificacion(consultaPuestaTierra);
            bd.ejecModificacion(consultaPresupuesto);
    }
}
