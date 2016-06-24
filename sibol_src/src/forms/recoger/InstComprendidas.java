/*
 * InstComprendidas.java
 *
 * Created on 21 de agosto de 2006, 8:35
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
public class InstComprendidas implements IntfzValidarDatos{
    
    private String descripcion1, descripcion2A, descripcion2B, descripcion3A, descripcion3B;
    private String[] descripcion4, descripcion5, descripcion6;    
    private double[] potenciaInst;
    private ParCombo[] tipoUso;
    private String[] tension1;
    private String[] tension2;
    private FrmDatosTecnicosPnl frm;
    private BaseDatos bd;
    private String txtErrorInstComprendidas;
    private boolean nums;
    private String memo;
    
    public InstComprendidas() {
        frm = (FrmDatosTecnicosPnl)Sesion.getInstance().getValorHt("objFrmDatosTecnicosPnl");
        bd = Sesion.getInstance().getBaseDatos();
        descripcion4 = new String[14];
        descripcion5 = new String[14];
        descripcion6 = new String[14];
        potenciaInst = new double[7];
        tipoUso      = new ParCombo[7];
        tension1     = new String[7];
        tension2     = new String[7];
    }
    
    public boolean esValido()
    {
       memo = frm.jTextAreaMemo.getText(); 
       
       potenciaInst[1] = 0;
       potenciaInst[2] = 0;
       potenciaInst[3] = 0;
       potenciaInst[4] = 0;
       potenciaInst[5] = 0;
       potenciaInst[6] = 0;
       
       descripcion1 = frm.jTxtFldDescripcion1.getText();
       descripcion2A = frm.jTxtFldDescripcion2A.getText();
       descripcion2B = frm.jTxtFldDescripcion2B.getText();
       descripcion3A = frm.jTxtFldDescripcion3A.getText();
       descripcion3B = frm.jTxtFldDescripcion3B.getText();
       
       descripcion4[Constantes.A] = frm.jTxtFldDescripcion4A.getText();
       descripcion4[Constantes.B] = frm.jTxtFldDescripcion4B.getText();
       descripcion4[Constantes.C] = frm.jTxtFldDescripcion4C.getText();
       descripcion4[Constantes.D] = frm.jTxtFldDescripcion4D.getText();
       descripcion4[Constantes.E] = frm.jTxtFldDescripcion4E.getText();
       descripcion4[Constantes.F] = frm.jTxtFldDescripcion4F.getText();
       descripcion4[Constantes.G] = frm.jTxtFldDescripcion4G.getText();
       descripcion4[Constantes.H] = frm.jTxtFldDescripcion4H.getText();
       descripcion4[Constantes.I] = frm.jTxtFldDescripcion4I.getText();
       descripcion4[Constantes.J] = frm.jTxtFldDescripcion4J.getText();
       descripcion4[Constantes.K] = frm.jTxtFldDescripcion4K.getText();
       descripcion4[Constantes.L] = frm.jTxtFldDescripcion4L.getText();
       descripcion4[Constantes.M] = frm.jTxtFldDescripcion4M.getText();
       
       descripcion5[Constantes.A] = frm.jTxtFldDescripcion5A.getText();
       descripcion5[Constantes.B] = frm.jTxtFldDescripcion5B.getText();
       descripcion5[Constantes.C] = frm.jTxtFldDescripcion5C.getText();
       descripcion5[Constantes.D] = frm.jTxtFldDescripcion5D.getText();
       descripcion5[Constantes.E] = frm.jTxtFldDescripcion5E.getText();
       descripcion5[Constantes.F] = frm.jTxtFldDescripcion5F.getText();
       descripcion5[Constantes.G] = frm.jTxtFldDescripcion5G.getText();
       descripcion5[Constantes.H] = frm.jTxtFldDescripcion5H.getText();
       descripcion5[Constantes.I] = frm.jTxtFldDescripcion5I.getText();
       descripcion5[Constantes.J] = frm.jTxtFldDescripcion5J.getText();
       descripcion5[Constantes.K] = frm.jTxtFldDescripcion5K.getText();
       descripcion5[Constantes.L] = frm.jTxtFldDescripcion5L.getText();
       descripcion5[Constantes.M] = frm.jTxtFldDescripcion5M.getText();
       
       descripcion6[Constantes.A] = frm.jTxtFldDescripcion6A.getText();
       descripcion6[Constantes.B] = frm.jTxtFldDescripcion6B.getText();
       descripcion6[Constantes.C] = frm.jTxtFldDescripcion6C.getText();
       descripcion6[Constantes.D] = frm.jTxtFldDescripcion6D.getText();
       descripcion6[Constantes.E] = frm.jTxtFldDescripcion6E.getText();
       descripcion6[Constantes.F] = frm.jTxtFldDescripcion6F.getText();
       descripcion6[Constantes.G] = frm.jTxtFldDescripcion6G.getText();
       descripcion6[Constantes.H] = frm.jTxtFldDescripcion6H.getText();
       descripcion6[Constantes.I] = frm.jTxtFldDescripcion6I.getText();
       descripcion6[Constantes.J] = frm.jTxtFldDescripcion6J.getText();
       descripcion6[Constantes.K] = frm.jTxtFldDescripcion6K.getText();
       descripcion6[Constantes.L] = frm.jTxtFldDescripcion6L.getText();
       descripcion6[Constantes.M] = frm.jTxtFldDescripcion6M.getText();
       
       txtErrorInstComprendidas = "\n * Pestaña \"Instalaciones comprendidas\":\n";
       nums = true;
       
       //Tipo de uso
       tipoUso[1] = (ParCombo)frm.jCmbBxTipoUso1.getSelectedItem();
       tipoUso[2] = (ParCombo)frm.jCmbBxTipoUso2.getSelectedItem();
       tipoUso[3] = (ParCombo)frm.jCmbBxTipoUso3.getSelectedItem();
       tipoUso[4] = (ParCombo)frm.jCmbBxTipoUso4.getSelectedItem();
       tipoUso[5] = (ParCombo)frm.jCmbBxTipoUso5.getSelectedItem();
       tipoUso[6] = (ParCombo)frm.jCmbBxTipoUso6.getSelectedItem();
       
       //Casillas Potencia instalada.
       try{ 
           if(!frm.jTxtFldPotInst1.getText().equals(""))
               potenciaInst[1] = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldPotInst1.getText()));
           if(!frm.jTxtFldPotInst2.getText().equals(""))
               potenciaInst[2] = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldPotInst2.getText()));
           if(!frm.jTxtFldPotInst3.getText().equals(""))
               potenciaInst[3] = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldPotInst3.getText()));
           if(!frm.jTxtFldPotInst4.getText().equals(""))
               potenciaInst[4] = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldPotInst4.getText()));
           if(!frm.jTxtFldPotInst5.getText().equals(""))
               potenciaInst[5] = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldPotInst5.getText()));
           if(!frm.jTxtFldPotInst6.getText().equals(""))
               potenciaInst[6] = Double.parseDouble(Utilidades.cambiarComa(frm.jTxtFldPotInst6.getText()));
       }
       catch(NumberFormatException e){
           txtErrorInstComprendidas += "      - POTENCIA INSTALADA(kW): la casilla rellenada sea un número válido. Ej: 10 ó 10,44.\n";
           nums = false;
       }
       
       //Casillas Tensión. 
       tension1[1] = frm.jTxtFldTension1_1.getText();
       tension2[1] = frm.jTxtFldTension2_1.getText();
               
       tension1[2] = frm.jTxtFldTension1_2.getText();
       tension2[2] = frm.jTxtFldTension2_2.getText();

       tension1[3] = frm.jTxtFldTension1_3.getText();
       tension2[3] = frm.jTxtFldTension2_3.getText();

       tension1[4] = frm.jTxtFldTension1_4.getText();
       tension2[4] = frm.jTxtFldTension2_4.getText();

       tension1[5] = frm.jTxtFldTension1_5.getText();
       tension2[5] = frm.jTxtFldTension2_5.getText();

       tension1[6] = frm.jTxtFldTension1_6.getText();
       tension2[6] = frm.jTxtFldTension2_6.getText();
               
       if(tension1[1].equals(""))
           tension1[1] = null;
       if(tension1[2].equals(""))
           tension1[2] = null;
       if(tension1[3].equals(""))
           tension1[3] = null;
       if(tension1[4].equals(""))
           tension1[4] = null;
       if(tension1[5].equals(""))
           tension1[5] = null;
       if(tension1[6].equals(""))
           tension1[6] = null;
       
       if(tension2[1].equals(""))
           tension2[1] = null;
       if(tension2[2].equals(""))
           tension2[2] = null;
       if(tension2[3].equals(""))
           tension2[3] = null;
       if(tension2[4].equals(""))
           tension2[4] = null;
       if(tension2[5].equals(""))
           tension2[5] = null;
       if(tension2[6].equals(""))
           tension2[6] = null;
       
       if(nums)
           return true;
       else{
           Sesion.getInstance().setValorHt("txtErrorInstComprendidas",txtErrorInstComprendidas);
           return false;
       }
    }
    public void insertarBD() throws SQLException
    {
        //Recojo la ID de la sesión de la tabla INSTALACIONES
        String idInstalacion = Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();
        String consulta = "UPDATE INSTALACIONES SET INICDESC1='"+descripcion1+"', INICDESC2A='"+descripcion2A+"', INICDESC2B='"+descripcion2B+"', INICDESC3A='"+descripcion3A+"', INICDESC3B='"+descripcion3B+"', " +
                " INICDESC4A='"+descripcion4[Constantes.A]+"', INICDESC4B='"+descripcion4[Constantes.B]+"', INICDESC4C='"+descripcion4[Constantes.C]+"', INICDESC4D='"+descripcion4[Constantes.D]+"', INICDESC4E='"+descripcion4[Constantes.E]+"', INICDESC4F='"+descripcion4[Constantes.F]+"', INICDESC4G='"+descripcion4[Constantes.G]+"', INICDESC4H='"+descripcion4[Constantes.H]+"', INICDESC4I='"+descripcion4[Constantes.I]+"', INICDESC4J='"+descripcion4[Constantes.J]+"', INICDESC4K='"+descripcion4[Constantes.K]+"', INICDESC4L='"+descripcion4[Constantes.L]+"', INICDESC4M='"+descripcion4[Constantes.M]+"', " +
                " INICDESC5A='"+descripcion5[Constantes.A]+"', INICDESC5B='"+descripcion5[Constantes.B]+"', INICDESC5C='"+descripcion5[Constantes.C]+"', INICDESC5D='"+descripcion5[Constantes.D]+"', INICDESC5E='"+descripcion5[Constantes.E]+"', INICDESC5F='"+descripcion5[Constantes.F]+"', INICDESC5G='"+descripcion5[Constantes.G]+"', INICDESC5H='"+descripcion5[Constantes.H]+"', INICDESC5I='"+descripcion5[Constantes.I]+"', INICDESC5J='"+descripcion5[Constantes.J]+"', INICDESC5K='"+descripcion5[Constantes.K]+"', INICDESC5L='"+descripcion5[Constantes.L]+"', INICDESC5M='"+descripcion5[Constantes.M]+"', " +
                " INICDESC6A='"+descripcion6[Constantes.A]+"', INICDESC6B='"+descripcion6[Constantes.B]+"', INICDESC6C='"+descripcion6[Constantes.C]+"', INICDESC6D='"+descripcion6[Constantes.D]+"', INICDESC6E='"+descripcion6[Constantes.E]+"', INICDESC6F='"+descripcion6[Constantes.F]+"', INICDESC6G='"+descripcion6[Constantes.G]+"', INICDESC6H='"+descripcion6[Constantes.H]+"', INICDESC6I='"+descripcion6[Constantes.I]+"', INICDESC6J='"+descripcion6[Constantes.J]+"', INICDESC6K='"+descripcion6[Constantes.K]+"', INICDESC6L='"+descripcion6[Constantes.L]+"', INICDESC6M='"+descripcion6[Constantes.M]+"', " +
                " INICPOT1="+potenciaInst[1]+", INICPOT2="+potenciaInst[2]+", INICPOT3="+potenciaInst[3]+", INICPOT4="+potenciaInst[4]+", INICPOT5="+potenciaInst[5]+", INICPOT6="+potenciaInst[6]+", " +
                " INTUID1='"+tipoUso[1].getKeyString()+"',INTUID2='"+tipoUso[2].getKeyString()+"',INTUID3='"+tipoUso[3].getKeyString()+"',INTUID4='"+tipoUso[4].getKeyString()+"',INTUID5='"+tipoUso[5].getKeyString()+"', INTUID6='"+tipoUso[6].getKeyString()+"', "+
                " INTENSION1_1="+tension1[1]+",INTENSION2_1="+tension2[1]+",INTENSION1_2="+tension1[2]+",INTENSION2_2="+tension2[2]+",INTENSION1_3="+tension1[3]+",INTENSION2_3="+tension2[3]+",INTENSION1_4="+tension1[4]+",INTENSION2_4="+tension2[4]+",INTENSION1_5="+tension1[5]+",INTENSION2_5="+tension2[5]+",INTENSION1_6="+tension1[6]+",INTENSION2_6="+tension2[6]+","+
                " INMEMO='"+memo.replaceAll("'","''")+"' "+
                " WHERE INID="+idInstalacion;
        
        bd.ejecModificacion(consulta);       
    }
 
}
