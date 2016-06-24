/*
 * PrevisionIndustriales.java
 *
 * Created on 28 de agosto de 2006, 11:42
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
import funciones.beans.ModeloTablaPrevision;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import main.Constantes;

/**
 *
 * @author enrique
 */
public class PrevisionIndustriales implements IntfzValidarDatos{
    
    private JTable tabla;
    private double potencia; 
    
    private ModeloTablaPrevision modelo;
    
    private FrmDatosTecnicosPnl frm;
    private BaseDatos bd;
    private ParCombo pc;
    private String txtErrorPrevIndustriales;
    
    public PrevisionIndustriales() {
        frm = (FrmDatosTecnicosPnl)Sesion.getInstance().getValorHt("objFrmDatosTecnicosPnl");
        bd = Sesion.getInstance().getBaseDatos();
    }

    public boolean esValido() {
        boolean correcto = true;
        double temp;
        String strPotencia = "";
        String strDenominacion = "";
        tabla = frm.jTblPrevision;
        txtErrorPrevIndustriales = "\n * Pestaña \"Previsión cargas industriales, agrarias, servicios...\":\n";
        ModeloTablaPrevision modelo = (ModeloTablaPrevision) tabla.getModel();
           for(int i=0; i<tabla.getRowCount(); i++)
           {
               strDenominacion = modelo.getValueAt(i,1).toString();
               strPotencia = modelo.getValueAt(i,2).toString();
               
               if(strDenominacion.equals(""))
               {
                   correcto = false;
                   txtErrorPrevIndustriales += "            - Compruebe que todas las filas para la columna DENOMINACIÓN no estén vacías.\n";
               }
               if(strDenominacion.length() > 30)
               {
                   correcto = false;
                   txtErrorPrevIndustriales += "            - La columna DENOMINACIÓN no puede superar los 30 caracteres: '" + strDenominacion + "'.\n";
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
        
        Sesion.getInstance().setValorHt("txtErrorPrevIndustriales",txtErrorPrevIndustriales);
        return false;
    }

    public void insertarBD() throws SQLException
    {
        //Recojo la ID de la sesión de la tabla INSTALACIONES
        String idInstalacion = Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();
        
        ResultSet rs = null;
        ParCombo pcTipoCarga;
        String strPotencia, denominacion;
        String idInst, idCargInd;
        String consulta = "";
        
        modelo = (ModeloTablaPrevision) tabla.getModel();

        for(int i=0; i<tabla.getRowCount(); i++)
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
  
}
