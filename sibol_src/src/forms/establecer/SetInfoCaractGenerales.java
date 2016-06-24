/*
 * NewClass.java
 *
 * Created on 11 de septiembre de 2006, 9:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package forms.establecer;

import forms.FrmDatosTecnicosPnl;
import forms.FrmPrincipal;
import funciones.BaseDatos;
import funciones.ParCombo;
import funciones.Sesion;
import funciones.Utilidades;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.Mensaje;

/**
 *
 * @author enrique
 */
public class SetInfoCaractGenerales implements IntfzEstablecerDatos{
    private FrmDatosTecnicosPnl frmDatosTecnicos;
    private BaseDatos bd;
    private int idInstalacion;
    
    //Generales
    private String tension1, tension2, potPrevista, superfLocal;
    private String inusovarios, inticod, inmmid;
    private int inuiid,intinum, inrgid;
    
    //Acometida
    private String seccionAcometida;
    private String idAcometida;
    private String idTipoAcometida;
    private String idMaterialAcometida;
    
    //CGP Seguridad
    private String tipoSeguridad, inBase, inCartucho;
    
    //Linea general
    private String seccionLinea;
    private String idTipoLinea;
    private String idMaterialLinea;
    
    //Modulo de medida
    private String tipoModulo;
    private String idSituacionModulo;
    //Proteccion magnetotermica
    private String instGenAutom, icc, instDiferecncial, sensibilidad;
    //Puesta a tierra
    private String electrodos, lineaEnlace, lineaPrincipal, ohmios;
    private String idInstGenAutom;
    //Presupuesto
    private String numInst, presupuesto;
    
    
    
    public SetInfoCaractGenerales(FrmPrincipal frmPrincipal, int idInst) {
        frmDatosTecnicos = frmPrincipal.getFrmDatosTecnicos();
        bd = Sesion.getInstance().getBaseDatos();
        idInstalacion = idInst;
    }
    
    public void getInfoBD() {
        ResultSet rs = null;
        
        String consulta = "SELECT * FROM INSTALACIONES WHERE INID = "+idInstalacion;
        try{
            rs = bd.ejecSelect(consulta);
            rs.next();
            
            //Generales
            tension1        = rs.getString("INTENSION1");
            tension2        = rs.getString("INTENSION2");
            
            if(tension1 == null || tension1.equals("") || tension1.equals("-1"))
                tension1 = "";
            if(tension2 == null || tension2.equals("") || tension2.equals("-1") || tension2.equals("0"))
                tension2 = "";
            
            potPrevista     = Utilidades.cambiarPunto(rs.getString("INPOTPREVISTA"));
            superfLocal     = Utilidades.cambiarPunto(rs.getString("INSUPERFICIE"));
            inmmid          = rs.getString("INMMID");
            inticod         = rs.getString("INTICOD");
            inusovarios     = rs.getString("INUSOVARIOS");
            intinum         = rs.getInt("INTINUM");
            inrgid          = rs.getInt("INRGID");
            inuiid          = rs.getInt("INUIID");
            
            //Acometida
            seccionAcometida= rs.getString("INSECCACOMETIDA");
            idAcometida = rs.getString("INPCIDACOMETIDA");
            idTipoAcometida = rs.getString("INTLIDACOMETIDA");
            idMaterialAcometida = rs.getString("INMAIDACOMETIDA");
            
            //CGP Seguridad
            tipoSeguridad   = rs.getString("INCGPTIPO");
            inBase          = Utilidades.cambiarPunto(rs.getString("INCGPIBASE"));
            inCartucho      = Utilidades.cambiarPunto(rs.getString("INCGPICARTUCHO"));
            
            //Linea general
            seccionLinea    = rs.getString("INSECCGENERAL");
            idTipoLinea     = rs.getString("INTLGENERAL");
            idMaterialLinea = rs.getString("INMAIDGENERAL");
            
            //Modulo de medida
            tipoModulo      = rs.getString("INMODULOMED");
            idSituacionModulo = rs.getString("INSMID");
            
            //Proteccion magnetotermica
            instGenAutom    = Utilidades.cambiarPunto(rs.getString("ININTGRAL"));
            icc             = Utilidades.cambiarPunto(rs.getString("INICC"));
            instDiferecncial= Utilidades.cambiarPunto(rs.getString("ININTDIF"));
            sensibilidad    = Utilidades.cambiarPunto(rs.getString("INSENSIBILIDAD"));
            
            //Puesta a tierra
            electrodos      = Utilidades.quitarDecimales(rs.getString("INELECTRODOS"));
            lineaEnlace     = Utilidades.cambiarPunto(rs.getString("INSECCLINENLACE"));
            lineaPrincipal  = Utilidades.cambiarPunto(rs.getString("INSECCLINPPAL"));
            ohmios = Utilidades.cambiarPunto(rs.getString("INRESTIERRA"));
            idInstGenAutom  = rs.getString("INTTID");
            
            //Presupuesto
            numInst         = rs.getString("INNUMINSTALACIONES");
            if(numInst == null || numInst.equals("")) numInst = "0";
            presupuesto     = Utilidades.cambiarPunto(rs.getString("INPRESUPUESTO"));
            
        } catch(SQLException e){
            Mensaje.error("SetInfoCaractGenerales.java: "+e.getMessage(),e);
        }
    }
    
    public void rellenarFormulario() {
        //Caract. de la instalación
        frmDatosTecnicos.jTxtFldTension1.setText(tension1);
        frmDatosTecnicos.jTxtFldTension2.setText(tension2);
        frmDatosTecnicos.jCmbBxPotenciaPrevista.setSelectedItem(potPrevista);
        frmDatosTecnicos.jTxtFldSuperficieLocal.setText(superfLocal);
        frmDatosTecnicos.jTxtFldVariosSinClasificar.setText(inusovarios);
        //Acometida
        frmDatosTecnicos.jTxtFldSeccionAcometida.setText(seccionAcometida);
        //CGP Seguridad
        frmDatosTecnicos.jTxtFldTipoCGP.setText(tipoSeguridad);
        frmDatosTecnicos.jTxtFldIntensidadBase.setText(inBase);
        frmDatosTecnicos.jTxtFldIntensidadCartucho.setText(inCartucho);
        //Linea general
        frmDatosTecnicos.jTxtFldSeccionLinea.setText(seccionLinea);
        //Modulo de medida
        frmDatosTecnicos.jTxtFldTipoModuloMedida.setText(tipoModulo);
        //Proteccion magnetotermica
        frmDatosTecnicos.jTxtFldInterruptorGralAutomatico.setText(instGenAutom);
        frmDatosTecnicos.jTxtFldIccProt.setText(icc);
        frmDatosTecnicos.jTxtFldInterruptorDiferencial.setText(instDiferecncial);
        frmDatosTecnicos.jTxtFldSensibilidad.setText(sensibilidad);
        //Puesta a tierra
        frmDatosTecnicos.jTxtFldElectrodos.setText(electrodos);
        frmDatosTecnicos.jTxtFldLineaEnlace.setText(lineaEnlace);
        frmDatosTecnicos.jTxtFldLineaPrincipal.setText(lineaPrincipal);
        frmDatosTecnicos.jTxtFldResistTierraProt.setText(ohmios);
        //Presupuesto
        frmDatosTecnicos.jTxtFldNumInstIndivFinales.setText(numInst);
        frmDatosTecnicos.jTxtFldPresupuestoTotal.setText(presupuesto);
        
        
        rellenarCombos();
    }
    
    private void rellenarCombos() {
        ParCombo pcTemp;
        
        for(int i=0; i<frmDatosTecnicos.jCmbBxTipoInst.getItemCount(); i++) {
            pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxTipoInst.getItemAt(i);
            if(pcTemp.getKeyString().equals(inticod) && pcTemp.getKeyInt() == intinum)
                frmDatosTecnicos.jCmbBxTipoInst.setSelectedItem(pcTemp);
        }
        
        for(int i=0; i<frmDatosTecnicos.jCmbBxReglamentos.getItemCount(); i++) {
            pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxReglamentos.getItemAt(i);
            if(pcTemp.getKeyInt() == inrgid)
                frmDatosTecnicos.jCmbBxReglamentos.setSelectedItem(pcTemp);
        }
        
        for(int i=0; i<frmDatosTecnicos.jCmbBxMemoriaPor.getItemCount(); i++) {
            pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxMemoriaPor.getItemAt(i);
            if(pcTemp.getKeyString().equals(inmmid))
                frmDatosTecnicos.jCmbBxMemoriaPor.setSelectedItem(pcTemp);
        }
        
        for(int i=0; i<frmDatosTecnicos.jCmbBxPuntoConexionAcometida.getItemCount(); i++) {
            pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxPuntoConexionAcometida.getItemAt(i);
            if(pcTemp.getKeyString().equals(idAcometida))
                frmDatosTecnicos.jCmbBxPuntoConexionAcometida.setSelectedItem(pcTemp);
        }
        
        for(int i=0; i<frmDatosTecnicos.jCmbBxTipoAcometida.getItemCount(); i++) {
            pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxTipoAcometida.getItemAt(i);
            if(pcTemp.getKeyString().equals(idTipoAcometida))
                frmDatosTecnicos.jCmbBxTipoAcometida.setSelectedItem(pcTemp);
        }
        
        for(int i=0; i<frmDatosTecnicos.jCmbBxMaterialAcometida.getItemCount(); i++) {
            pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxMaterialAcometida.getItemAt(i);
            if(pcTemp.getKeyString().equals(idMaterialAcometida))
                frmDatosTecnicos.jCmbBxMaterialAcometida.setSelectedItem(pcTemp);
        }
        
        for(int i=0; i<frmDatosTecnicos.jCmbBxTipoLinea.getItemCount(); i++) {
            pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxTipoLinea.getItemAt(i);
            if(pcTemp.getKeyString().equals(idTipoLinea)) {
                frmDatosTecnicos.jCmbBxTipoLinea.setSelectedItem(pcTemp);
            }
        }
        
        for(int i=0; i<frmDatosTecnicos.jCmbBxMaterialLinea.getItemCount(); i++) {
            pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxMaterialLinea.getItemAt(i);
            if(pcTemp.getKeyString().equals(idMaterialLinea))
                frmDatosTecnicos.jCmbBxMaterialLinea.setSelectedItem(pcTemp);
        }
        
        for(int i=0; i<frmDatosTecnicos.jCmbBxSituacionModuloMedida.getItemCount(); i++) {
            pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxSituacionModuloMedida.getItemAt(i);
            if(pcTemp.getKeyString().equals(idSituacionModulo))
                frmDatosTecnicos.jCmbBxSituacionModuloMedida.setSelectedItem(pcTemp);
        }
        
        for(int i=0; i<frmDatosTecnicos.jCmbBxTipoPuestaTierra.getItemCount(); i++) {
            pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxTipoPuestaTierra.getItemAt(i);
            if(pcTemp.getKeyString().equals(idInstGenAutom))
                frmDatosTecnicos.jCmbBxTipoPuestaTierra.setSelectedItem(pcTemp);
        }
    }
}
