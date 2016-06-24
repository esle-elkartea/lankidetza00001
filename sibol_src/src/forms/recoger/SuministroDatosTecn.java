/*
 * SuministroDatosTecn.java
 *
 * Created on 11 de agosto de 2006, 14:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package forms.recoger;

import forms.FrmDatosTecnicosPnl;
import funciones.BaseDatos;
import funciones.Sesion;
import funciones.Utilidades;
import java.sql.SQLException;
import main.Constantes;

/**
 *
 * @author enrique
 */
public class SuministroDatosTecn implements IntfzValidarDatos{
    
    private double potenciaComplem, potenciaNormal;
    private String descripcion;
    private FrmDatosTecnicosPnl frm;
    private BaseDatos bd;
    private String txtErrorSuministros;
    
    public SuministroDatosTecn() {
        frm = (FrmDatosTecnicosPnl)Sesion.getInstance().getValorHt("objFrmDatosTecnicosPnl");
        bd = Sesion.getInstance().getBaseDatos();
    }
    
    public boolean esValido()
    {
       boolean potNormal, porCompl;
       potNormal = true;
       porCompl = true;
       txtErrorSuministros = "\n * Pestaña \"Suministro\":\n";
          
           if(frm.jCmbBxPotenciaNormal.getSelectedItem().toString().equals(""))
           {
            potNormal=false;
            txtErrorSuministros += "      - POTENCIA INSTALADA NORMAL no debe de estar vacío.\n";
           }
           else{
               try{
                   potenciaNormal = Double.parseDouble(Utilidades.cambiarComa(frm.jCmbBxPotenciaNormal.getSelectedItem().toString()));
               }
               catch(NumberFormatException e){
                   potNormal = false;
                   txtErrorSuministros += "      - POTENCIA INSTALADA NORMAL su valor sea un número válido. Ej: 10 ó 10,44.\n";
               }
           }
           if(!frm.jCmbBxPotenciaCompl.getSelectedItem().toString().equals(""))
           {
               try{
                   potenciaComplem = Double.parseDouble(Utilidades.cambiarComa(frm.jCmbBxPotenciaCompl.getSelectedItem().toString()));
               }
               catch(NumberFormatException e){
                   porCompl = false;
                   txtErrorSuministros += "      - POTENCIA INSTALADA COMPLEMENTARIA su valor sea un número válido. Ej: 10 ó 10,44.\n";
               }
            }
               
               descripcion = frm.jTxtFldDescripcionSumCompl.getText();
               
               if(porCompl && potNormal)
                   return true;
               
               Sesion.getInstance().setValorHt("txtErrSuministros",txtErrorSuministros);
               return false;
       
    }
    public void insertarBD() throws SQLException
    {
        //Recojo la ID de la sesión de la tabla INSTALACIONES
        String idInstalacion = Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();
        String consulta = "UPDATE INSTALACIONES SET INPOTNORMAL="+potenciaNormal+", " +
                " INPOTCOMPLEMENT="+potenciaComplem+", INDESCPOTCOMPLEMENT='"+descripcion+"' " +
                " WHERE INID="+idInstalacion;

        bd.ejecModificacion(consulta);
    }
}
