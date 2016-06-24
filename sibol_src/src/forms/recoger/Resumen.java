/*
 * Resumen.java
 *
 * Created on 29 de agosto de 2006, 13:25
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
import funciones.beans.ModeloTablaResumen;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import main.Constantes;

/**
 *
 * @author enrique
 */
// OSCAR - Esta clase ya no es necesaria
public class Resumen implements IntfzValidarDatos{
    
    private JTable tabla;
    
    private ModeloTablaResumen modelo;
    
    private String descCircuito    = null;
    private String potCalc         = null;
    private String tensionCalc     = null;
    private String intCalc         = null;
    private String numCond         = null;
    private String aislamiento     = null;
    private String intMax          = null;
    private String ccpia           = null;
    private String longitud        = null;
    private String caida           = null;
    private String idInstalacion   = null;
    private String idDatosCircuito = null;
    
    private FrmDatosTecnicosPnl frm;
    private BaseDatos bd;
    private ParCombo pcTipoCircuito;
    private ParCombo pcTipoInstCircuito;
    
    private String txtErrorResumen;
    
    public Resumen() {
        frm = (FrmDatosTecnicosPnl)Sesion.getInstance().getValorHt("objFrmDatosTecnicosPnl");
        bd = Sesion.getInstance().getBaseDatos();
        idInstalacion   = Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();
    }

    public boolean esValido() {

        boolean correcto = true;
        txtErrorResumen = "\n * Pestaña \"Resumen\":\n";
        double temp;
        
        tabla = frm.jTblResumen;
        modelo = (ModeloTablaResumen) tabla.getModel();
        
           for(int i=0; i<tabla.getRowCount(); i++)
           {
               pcTipoCircuito     = (ParCombo)modelo.getValueAt(i,0);
               descCircuito       = modelo.getValueAt(i,1).toString();
               potCalc            = modelo.getValueAt(i,2).toString();
               tensionCalc        = modelo.getValueAt(i,3).toString();
               intCalc            = modelo.getValueAt(i,4).toString();
               numCond            = modelo.getValueAt(i,5).toString();
               aislamiento        = modelo.getValueAt(i,6).toString();
               pcTipoInstCircuito = (ParCombo)modelo.getValueAt(i,7);
               intMax             = modelo.getValueAt(i,8).toString();
               ccpia              = modelo.getValueAt(i,9).toString();
               longitud           = modelo.getValueAt(i,10).toString();
               caida              = modelo.getValueAt(i,11).toString();
               idDatosCircuito    = modelo.getValueAt(i,12).toString();
               
               if(pcTipoCircuito == null || pcTipoCircuito.getDescription().equals("")){
                   correcto = false;
                   txtErrorResumen += "      - Compruebe que todas las filas para la columna TIPO CIRCUITO no estén vacías.\n";
               }
               
               if(potCalc.equals(""))
               {
                   correcto = false;
                   txtErrorResumen += "      - Compruebe que todas las filas para la columna POTENCIA DE CÁLCULO no estén vacías.\n";
               }
               else{
                   try{
                       temp =  Double.parseDouble(Utilidades.cambiarComa(potCalc));
                   }
                   catch(NumberFormatException e){
                       correcto = false;
                       txtErrorResumen += "      - Compruebe que todas las filas para la columna POTENCIA DE CÁLCULO contengan un número válido. Ej: 10 ó 10,44.\n";
                   }
               }
               
               if(tensionCalc.equals(""))
               {
                   correcto = false;
                   txtErrorResumen += "      - Compruebe que todas las filas para la columna TENSIÓN DE CÁLCULO no estén vacías.\n";
               }
               else{
                   try{
                       temp =  Double.parseDouble(Utilidades.cambiarComa(tensionCalc));
                   }
                   catch(NumberFormatException e){
                       correcto = false;
                       txtErrorResumen += "      - Compruebe que todas las filas para la columna TENSIÓN DE CÁLCULO contengan un número válido. Ej: 10 ó 10,44.\n";
                   }
               }
               
               if(intCalc.equals(""))
               {
                   correcto = false;
                   txtErrorResumen += "      - Compruebe que todas las filas para la columna INTENSIDAD DE CÁLCULO no estén vacías.\n";
               }
               else{
                   try{
                       temp =  Double.parseDouble(Utilidades.cambiarComa(intCalc));
                   }
                   catch(NumberFormatException e){
                       correcto = false;
                       txtErrorResumen += "      - Compruebe que todas las filas para la columna INTENSIDAD DE CÁLCULO contengan un número válido. Ej: 10 ó 10,44.\n";
                   }
               }
               
               if(numCond.equals(""))
               {
                   correcto = false;
                   txtErrorResumen += "      - Compruebe que todas las filas para la columna Nº CONDUCC. SECC. no estén vacías.\n";
               }
               else{
                   
                   try{
                       Integer in = new Integer(numCond);
                       int temp2 = in.intValue();
                   }
                   catch(NumberFormatException e){
                       correcto = false;
                       txtErrorResumen += "      - Compruebe que todas las filas para la columna Nº CONDUCC. SECC. contengan un número de tipo entero. Ej: 10.\n";
                   }
               }
               
               if(aislamiento.equals(""))
               {
                   correcto = false;
                   txtErrorResumen += "      - Compruebe que todas las filas para la columna AISLAMIENTO TENSIÓN NOMINAL no estén vacías.\n";
               }
               else{
                   try{
                       temp =  Double.parseDouble(Utilidades.cambiarComa(aislamiento));
                   }
                   catch(NumberFormatException e){
                       correcto = false;
                       txtErrorResumen += "      - Compruebe que todas las filas para la columna AISLAMIENTO TENSIÓN NOMINAL contengan un número válido. Ej: 10 ó 10,44.\n";
                   }
               }
               
               if(pcTipoInstCircuito == null || pcTipoInstCircuito.getDescription().equals("")){
                   correcto = false;
                   txtErrorResumen += "      - Compruebe que todas las filas para la columna TIPO DE INSTALACIÓN no estén vacías.\n";
               }               
               
               if(intMax.equals(""))
               {
                   correcto = false;
                   txtErrorResumen += "      - Compruebe que todas las filas para la columna INTENSIDAD MÁXIMA no estén vacías.\n";
               }
               else{
                   try{
                       temp =  Double.parseDouble(Utilidades.cambiarComa(intMax));
                   }
                   catch(NumberFormatException e){
                       correcto = false;
                       txtErrorResumen += "      - Compruebe que todas las filas para la columna INTENSIDAD MÁXIMA contengan un número válido. Ej: 10 ó 10,44.\n";
                   }
               }
               
               if(ccpia.equals(""))
               {
                   correcto = false;
                   txtErrorResumen += "      - Compruebe que todas las filas para la columna C/C PIA no estén vacías.\n";
               }
               else{
                   try{
                       temp =  Double.parseDouble(Utilidades.cambiarComa(ccpia));
                   }
                   catch(NumberFormatException e){
                       correcto = false;
                       txtErrorResumen += "      - Compruebe que todas las filas para la columna C/C PIA contengan un número válido. Ej: 10 ó 10,44.\n";
                   }
               }
               
               if(longitud.equals(""))
               {
                   correcto = false;
                   txtErrorResumen += "      - Compruebe que todas las filas para la columna LONGITUD no estén vacías.\n";
               }
               else{
                   try{
                       temp =  Double.parseDouble(Utilidades.cambiarComa(longitud));
                   }
                   catch(NumberFormatException e){
                       correcto = false;
                       txtErrorResumen += "      - Compruebe que todas las filas para la columna LONGITUD contengan un número válido. Ej: 10 ó 10,44.\n";
                   }
               }
               
               if(caida.equals(""))
               {
                   correcto = false;
                   txtErrorResumen += "      - Compruebe que todas las filas para la columna CAIDA DE TENSION no estén vacías.\n";
               }
               else{
                   try{
                       temp =  Double.parseDouble(Utilidades.cambiarComa(caida));
                   }
                   catch(NumberFormatException e){
                       correcto = false;
                       txtErrorResumen += "      - Compruebe que todas las filas para la columna CAIDA DE TENSION contengan un número válido. Ej: 10 ó 10,44.\n";
                   }
               }
               
               if(!correcto)
                   break;
           }
           if (correcto)
               return true;

           Sesion.getInstance().setValorHt("txtErrorResumen",txtErrorResumen);
           return false;
    }

    public void insertarBD()  throws SQLException{       
        ResultSet rs = null;       
        modelo = (ModeloTablaResumen) tabla.getModel();
        String consulta="";

            for(int i=0; i<tabla.getRowCount(); i++)
            {
               potCalc     = Utilidades.cambiarComa(modelo.getValueAt(i,2).toString());
               tensionCalc = Utilidades.cambiarComa(modelo.getValueAt(i,3).toString());
               intCalc     = Utilidades.cambiarComa(modelo.getValueAt(i,4).toString());
               aislamiento = Utilidades.cambiarComa(modelo.getValueAt(i,6).toString());
               intMax      = Utilidades.cambiarComa(modelo.getValueAt(i,8).toString());
               ccpia       = Utilidades.cambiarComa(modelo.getValueAt(i,9).toString());
               longitud    = Utilidades.cambiarComa(modelo.getValueAt(i,10).toString());
               caida       = Utilidades.cambiarComa(modelo.getValueAt(i,11).toString());
               
               pcTipoCircuito     = (ParCombo)modelo.getValueAt(i,0);
               descCircuito       = modelo.getValueAt(i,1).toString();
               numCond            = modelo.getValueAt(i,5).toString();
               pcTipoInstCircuito = (ParCombo)modelo.getValueAt(i,7);
               idDatosCircuito    = modelo.getValueAt(i,12).toString();
               
               rs = bd.ejecSelect("Select * from DATOS_CIRCUITOS where DCID="+idDatosCircuito);
               
               if(rs.next())
               {    consulta = " UPDATE DATOS_CIRCUITOS SET DCTRID="+pcTipoCircuito.getKeyString()+", " +
                       " DCDESC='"+descCircuito+"', DCPOTENCIA="+potCalc+", " +
                       " DCTENSION="+tensionCalc+",DCINTENSIDAD="+intCalc+", " +
                       " DCNUMCOND='"+numCond+"', DCAISLAMIENTO="+aislamiento+", " +
                       " DCTNID='"+pcTipoInstCircuito.getKeyString()+"', DCINTMAX="+intMax+", " +
                       " DCCCPIA="+ccpia+", DCLONGITUD="+longitud+", " +
                       " DCCAIDA="+caida+" WHERE DCID="+idDatosCircuito+" AND " +
                       " DCINID="+idInstalacion;
               }
               else
               {
                   consulta = "INSERT INTO DATOS_CIRCUITOS(DCID,DCINID,DCTRID,DCDESC," +
                           "DCPOTENCIA,DCTENSION,DCINTENSIDAD,DCNUMCOND,DCAISLAMIENTO," +
                           "DCTNID,DCINTMAX,DCCCPIA,DCLONGITUD,DCCAIDA) " +
                       " VALUES("+idDatosCircuito+","+idInstalacion+","+pcTipoCircuito.getKeyString()+"," +
                       "'"+descCircuito+"',"+potCalc+","+tensionCalc+","+intCalc+", " +
                       "'"+numCond+"',"+aislamiento+",'"+pcTipoInstCircuito.getKeyString()+"',"+intMax+", " +
                       ""+ccpia+","+longitud+","+caida+")";
               }
               
               bd.ejecModificacion(consulta);
            }   
    } 
}