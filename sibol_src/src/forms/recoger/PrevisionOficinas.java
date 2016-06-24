/*
 * CaractGeneralesDatosTecn.java
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
import main.Constantes;

/**
 *
 * @author enrique
 */
public class PrevisionOficinas implements IntfzValidarDatos{
    
    //Oficinas/Establecimientos
    private double supTotalOficinas, supTotalEstabOficinas, demandaOficinas, demandaEstabOficinas;
    private String numTotalOficinas, numTotalEstabOficinas;
    
    //Servicios generales
    private double ascensoresOficinas, alumbradoOficinas, otrosServOficinas;
    
    //Otras cargas
    private String descripcionOficinas;
    private double potPrevistaOficinas;
    
    //Cargas totales
    private double cargasTotalesOficinas;
       
    private FrmDatosTecnicosPnl frm;
    private BaseDatos bd;
    private ParCombo pc;
    
    private String txtErrorPrevOficinas;
    
    public PrevisionOficinas() {
        frm = (FrmDatosTecnicosPnl)Sesion.getInstance().getValorHt("objFrmDatosTecnicosPnl");
        bd = Sesion.getInstance().getBaseDatos();
    }
    
    public boolean esValido()
    {
       boolean oficinasEstab = true;
       boolean ssGen         = true;
       boolean otrasCargas   = true;
        
       txtErrorPrevOficinas  = "\n * Pestaña \"Previsión cargas edificios oficinas\":\n";
       
       //Viviendas
       numTotalOficinas = frm.jTxtFldNumTotalOficinas.getText();
       numTotalEstabOficinas = frm.jTxtFldNumTotalEstabOficinas.getText();
       
       if(frm.jTxtFldNumTotalOficinas.getText().equals(""))
           numTotalOficinas = "0";
       if(frm.jTxtFldNumTotalEstabOficinas.getText().equals(""))
           numTotalEstabOficinas = "0";

       try{
                   supTotalOficinas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldSuperficieOficinas.getText()));
       }
       catch(NumberFormatException e){
                   oficinasEstab = false;
                   txtErrorPrevOficinas += "      - SUPERFICIE TOTAL OFICINAS su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   supTotalEstabOficinas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldSuperficieTotalEstabOficinas.getText()));
       }
       catch(NumberFormatException e){
                   oficinasEstab = false;
                   txtErrorPrevOficinas += "      - SUPERFICIE TOTAL ESTAB. INDUS. su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   demandaOficinas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldDemandaOficinas.getText()));
       }
       catch(NumberFormatException e){
                   oficinasEstab = false;
                   txtErrorPrevOficinas += "      - DEMANDA MÁX / OFICINAS su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   demandaEstabOficinas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldDemandaEstabOficinas.getText()));
       }
       catch(NumberFormatException e){
                   oficinasEstab = false;
                   txtErrorPrevOficinas += "      - DEMANDA MÁX / ESTAB. INDUS. su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       
       //Servicios generales
       try{
                   ascensoresOficinas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldAscensoresOficinas.getText()));  
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevOficinas += "      - ASCENSORES su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   alumbradoOficinas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldAlumbradoOficinas.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevOficinas += "      - ALUMBRADO ESCALERA su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   otrosServOficinas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldOtrosServOficinas.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevOficinas += "      - OTROS SERVICIOS su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       
       //Otras cargas
       descripcionOficinas = frm.jTxtFldDescripcionOficinas.getText();
       try{
                    potPrevistaOficinas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldPotPrevistaOficinas.getText()));
       }
       catch(NumberFormatException e){
                   otrasCargas = false;
                   txtErrorPrevOficinas += "      - POTENCIA PREVISTA su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
        
        //Cargas totales
        cargasTotalesOficinas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldCargasTotalesOficinas.getText()));
           
        if(oficinasEstab && ssGen && otrasCargas)
           return true;
       
       Sesion.getInstance().setValorHt("txtErrorPrevOficinas",txtErrorPrevOficinas);
       return false;

    }
    public void insertarBD() throws SQLException
    {
        //Recojo la ID de la sesión de la tabla INSTALACIONES
        String idInstalacion = Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();
        
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
    
}
