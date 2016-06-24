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
public class PrevisionViviendas implements IntfzValidarDatos{
    
    //Viviendas
    private String gradoElect1, gradoElect2;
    private double superficieUnit1, superficieUnit2, demandaMax1, demandaMax2, cargasPrevistasViviendas;
    private int numViv1, numViv2;
            
    //Servicios generales
    private double ascensoresViviendas, alumbradoViviendas, otrosServiciosViviendas, cargasServiciosViviendas;
    
    //Locales comerciales y oficinas
    private double superficieUtil, potEspecPrevista, cargasLocalesViviendas;
    
    //Cargas totales
    private double cargasTotalesViviendas;
       
    private FrmDatosTecnicosPnl frm;
    private BaseDatos bd;
    private ParCombo pc;
    
    private String txtErrorPrevViviendas;
    
    public PrevisionViviendas() {
        frm = (FrmDatosTecnicosPnl)Sesion.getInstance().getValorHt("objFrmDatosTecnicosPnl");
        bd = Sesion.getInstance().getBaseDatos();
    }
    
    public boolean esValido()
    {
       txtErrorPrevViviendas = "\n * Pestaña \"Previsión cargas edificios viviendas\":\n";
       boolean viviendas = true;
       boolean ssGen     = true;
       boolean locales   = true;
       
       //VIVIENDAS
       pc = (ParCombo)frm.jCmbBxGradoElect1.getSelectedItem();
       gradoElect1 = pc.getKeyString();
       pc = (ParCombo)frm.jCmbBxGradoElect2.getSelectedItem();
       gradoElect2 = pc.getKeyString();
       

       if(!frm.jTxtFldNumViv1.getText().equals(""))
       {    
           try{
                       Integer i = new Integer(frm.jTxtFldNumViv1.getText());
                       numViv1 = i.intValue();
           }
           catch(NumberFormatException e){
                       viviendas = false;
                       txtErrorPrevViviendas += "      - Nº DE VIVIENDAS su valor sea un número entero válido. Ej: 10.\n";
           }
       }
       if(!frm.jTxtFldNumViv2.getText().equals(""))
       {    
           try{
                       Integer i = new Integer(frm.jTxtFldNumViv2.getText());
                       numViv2 = i.intValue();
           }
           catch(NumberFormatException e){
                       viviendas = false;
                       txtErrorPrevViviendas += "      - Nº DE VIVIENDAS contenga un número de tipo entero. Ej: 10.\n";
           }
       }
       
       try{
                   superficieUnit1 = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldSuperficieUnit1.getText()));
                   superficieUnit2 = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldSuperficieUnit2.getText()));
       }
       catch(NumberFormatException e){
                   viviendas = false;
                   txtErrorPrevViviendas += "      - SUPERFICIE UNITARIA su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   if(frm.jTxtFldDemandaMax1.isEnabled()){
                       demandaMax1 = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldDemandaMax1.getText()));
                   } else if(frm.jComboBoxDemandaMax1.isEnabled()){
                       demandaMax1 = Double.parseDouble(Utilidades.cambiarComa((String)frm.jComboBoxDemandaMax1.getSelectedItem()));
                   }
                   if(frm.jTxtFldDemandaMax2.isEnabled()){
                       demandaMax2 = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldDemandaMax2.getText()));
                   } else if(frm.jComboBoxDemandaMax2.isEnabled()){
                       demandaMax2 = Double.parseDouble(Utilidades.cambiarComa((String)frm.jComboBoxDemandaMax2.getSelectedItem()));
                   }
                   if(frm.jTxtFldDemandaMax1.isEnabled()){
                       if(demandaMax1<9200 || demandaMax1>14490){
                           viviendas = false;
                           txtErrorPrevViviendas += "      - DEMANDA MÁX/VIVIENDA su valor debe estar dentro del rango 9200-14490.\n";
                       }
                   } else if(frm.jTxtFldDemandaMax2.isEnabled()){
                       if(demandaMax2<9200 || demandaMax2>14490){
                           viviendas = false;
                           txtErrorPrevViviendas += "      - DEMANDA MÁX/VIVIENDA su valor debe estar dentro del rango 9200-14490.\n";
                       }
                   }                   
       }
       catch(NumberFormatException e){
                   viviendas = false;
                   txtErrorPrevViviendas += "      - DEMANDA MÁX/VIVIENDA su valor sea un número entero válido.\n";
       }
       try{
                   cargasPrevistasViviendas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldCargasPrevistasViviendas.getText()));
       }
       catch(NumberFormatException e){
                   viviendas = false;
                   txtErrorPrevViviendas += "      - CARGAS PREVISTAS EN VIVIENDAS (A) su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       
       //Servicios generales
       try{
                   ascensoresViviendas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldAscensoresViviendas.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevViviendas += "      - ASCENSORES su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   alumbradoViviendas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldAlumbradoEscalera.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevViviendas += "      - ALUMBRADO ESCALERA su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   otrosServiciosViviendas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldOtrosServiciosViviendas.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevViviendas += "      - OTROS SERVICIOS su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   cargasServiciosViviendas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldCargasServiciosViviendas.getText()));
       }
       catch(NumberFormatException e){
                   ssGen = false;
                   txtErrorPrevViviendas += "      - CARGAS PREVISTAS EN SERVICIOS GENERALES (B) su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       
       //Locales comerciales y oficinas
       try{
                   superficieUtil = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldSuperficieUtilTotal.getText()));
       }
       catch(NumberFormatException e){
                   locales = false;
                   txtErrorPrevViviendas += "      - SUPERFICIE ÚTIL TOTAL su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   potEspecPrevista = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldPotEspecPrevista.getText()));
       }
       catch(NumberFormatException e){
                   locales = false;
                   txtErrorPrevViviendas += "      - POTENCIA ESPECÍFICA PREVISTA su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   cargasLocalesViviendas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldCargasLocalesViviendas.getText()));
       }
       catch(NumberFormatException e){
                   locales = false;
                   txtErrorPrevViviendas += "      - CARGAS PREVISTAS EN LOCALES COMERCIALES Y OFICINAS (C) su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
       try{
                   cargasTotalesViviendas = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldCargasTotalesViviendas.getText()));
                   if(cargasTotalesViviendas>99999){
                       viviendas = false;
                       txtErrorPrevViviendas += "      - CARGAS TOTALES PREVISTAS EN EL EDIFICIO (A+B+C) su valor debe ser menor de 100.000 (valores mayores requieren proyecto y dirección de obra).\n";
                   }
       }
       catch(NumberFormatException e){
                   locales = false;
                   txtErrorPrevViviendas += "      - CARGAS TOTALES PREVISTAS EN EL EDIFICIO (A+B+C) su valor sea un número válido. Ej: 10 ó 10,44.\n";
       }
           
       if(viviendas && ssGen && locales)
           return true;
       
       Sesion.getInstance().setValorHt("txtErrorPrevViviendas",txtErrorPrevViviendas);
       return false;
    }
    
    public void insertarBD() throws SQLException
    {
        //Recojo la ID de la sesión de la tabla INSTALACIONES
        String idInstalacion = Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();
        
        String consultaViviendas = "UPDATE INSTALACIONES SET INGEID1='"+gradoElect1+"', INGEID2='"+gradoElect2+"', " +
                " INNVIV1="+numViv1+ ", INNVIV2="+numViv2+", INSUPUNIT1="+superficieUnit1+", INSUPUNIT2="+superficieUnit2+", " +
                "INDEMANDA1="+demandaMax1+", INDEMANDA2="+demandaMax2+", INVIVCARGPREV="+cargasPrevistasViviendas+" " +
                "WHERE INID="+idInstalacion;
        
        String consultaServGenerales = "UPDATE INSTALACIONES SET INPOTASC="+ascensoresViviendas+", INPOTALUMB="+alumbradoViviendas+", " +
                " INPOTOTROS="+otrosServiciosViviendas+", INSGCARGPREV="+cargasServiciosViviendas+" "+
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
    
}
