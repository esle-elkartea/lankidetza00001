/*
 * NewClass.java
 *
 * Created on 8 de septiembre de 2006, 14:01
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
import main.Constantes;
import main.Mensaje;

/**
 *
 * @author enrique
 */
public class SetInfoInstComprendidas implements IntfzEstablecerDatos{
    
    private FrmDatosTecnicosPnl frmDatosTecnicos;
    private BaseDatos bd;
    private int idInstalacion;
    
    private String descripcion1, descripcion2A, descripcion2B, descripcion3A, descripcion3B;
    private String[] descripcion4, descripcion5, descripcion6;    
    private String[] potenciaInst;
    private ParCombo[] tipoUso;
    private String[] tension1;
    private String[] tension2;
    
     public SetInfoInstComprendidas(FrmPrincipal frmPrincipal, int idInst) {
        frmDatosTecnicos = frmPrincipal.getFrmDatosTecnicos();
        bd = Sesion.getInstance().getBaseDatos();
        idInstalacion = idInst;
        descripcion4 = new String[14];
        descripcion5 = new String[14];
        descripcion6 = new String[14];
        potenciaInst = new String[7];
        tipoUso      = new ParCombo[7];
        tension1     = new String[7];
        tension2     = new String[7];
    }
     
     public void getInfoBD() {
        ResultSet rs = null;
        ParCombo pcTemp;
        String tipoUsoTemp;

        String consulta = "SELECT * FROM INSTALACIONES WHERE INID = "+idInstalacion;
        try{
            rs = bd.ejecSelect(consulta);
            rs.next();
            
            tipoUsoTemp = rs.getString("INTUID1");
            for(int i=0; i<frmDatosTecnicos.jCmbBxTipoUso1.getItemCount(); i++)
            {
                pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxTipoUso1.getItemAt(i);
                if(pcTemp.getKeyString().equals(tipoUsoTemp))
                    frmDatosTecnicos.jCmbBxTipoUso1.setSelectedItem(pcTemp);
            }
            tipoUsoTemp = rs.getString("INTUID2");
            for(int i=0; i<frmDatosTecnicos.jCmbBxTipoUso2.getItemCount(); i++)
            {
                pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxTipoUso2.getItemAt(i);
                if(pcTemp.getKeyString().equals(tipoUsoTemp))
                    frmDatosTecnicos.jCmbBxTipoUso2.setSelectedItem(pcTemp);
            }
            tipoUsoTemp = rs.getString("INTUID3");
            for(int i=0; i<frmDatosTecnicos.jCmbBxTipoUso3.getItemCount(); i++)
            {
                pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxTipoUso3.getItemAt(i);
                if(pcTemp.getKeyString().equals(tipoUsoTemp))
                    frmDatosTecnicos.jCmbBxTipoUso3.setSelectedItem(pcTemp);
            }
            tipoUsoTemp = rs.getString("INTUID4");
            for(int i=0; i<frmDatosTecnicos.jCmbBxTipoUso4.getItemCount(); i++)
            {
                pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxTipoUso4.getItemAt(i);
                if(pcTemp.getKeyString().equals(tipoUsoTemp))
                    frmDatosTecnicos.jCmbBxTipoUso4.setSelectedItem(pcTemp);
            }
            tipoUsoTemp = rs.getString("INTUID5");
            for(int i=0; i<frmDatosTecnicos.jCmbBxTipoUso5.getItemCount(); i++)
            {
                pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxTipoUso5.getItemAt(i);
                if(pcTemp.getKeyString().equals(tipoUsoTemp))
                    frmDatosTecnicos.jCmbBxTipoUso5.setSelectedItem(pcTemp);
            }
            tipoUsoTemp = rs.getString("INTUID6");
            for(int i=0; i<frmDatosTecnicos.jCmbBxTipoUso6.getItemCount(); i++)
            {
                pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxTipoUso6.getItemAt(i);
                if(pcTemp.getKeyString().equals(tipoUsoTemp))
                    frmDatosTecnicos.jCmbBxTipoUso6.setSelectedItem(pcTemp);
            }
            
            tension1[1] = rs.getString("INTENSION1_1");
            tension1[2] = rs.getString("INTENSION1_2");
            tension1[3] = rs.getString("INTENSION1_3");
            tension1[4] = rs.getString("INTENSION1_4");
            tension1[5] = rs.getString("INTENSION1_5");
            tension1[6] = rs.getString("INTENSION1_6");
            tension2[1] = rs.getString("INTENSION2_1");
            tension2[2] = rs.getString("INTENSION2_2");
            tension2[3] = rs.getString("INTENSION2_3");
            tension2[4] = rs.getString("INTENSION2_4");
            tension2[5] = rs.getString("INTENSION2_5");
            tension2[6] = rs.getString("INTENSION2_6");
            
            potenciaInst[1] = Utilidades.cambiarPunto(rs.getString("INICPOT1"));
            potenciaInst[2] = Utilidades.cambiarPunto(rs.getString("INICPOT2"));
            potenciaInst[3] = Utilidades.cambiarPunto(rs.getString("INICPOT3"));
            potenciaInst[4] = Utilidades.cambiarPunto(rs.getString("INICPOT4"));
            potenciaInst[5] = Utilidades.cambiarPunto(rs.getString("INICPOT5"));
            potenciaInst[6] = Utilidades.cambiarPunto(rs.getString("INICPOT6"));
            
            descripcion1 = rs.getString("INICDESC1");
            descripcion2A = rs.getString("INICDESC2A");
            descripcion2B = rs.getString("INICDESC2B");
            descripcion3A = rs.getString("INICDESC3A");
            descripcion3B = rs.getString("INICDESC3B");
            
            descripcion4[Constantes.A] = rs.getString("INICDESC4A");
            descripcion4[Constantes.B] = rs.getString("INICDESC4B");
            descripcion4[Constantes.C] = rs.getString("INICDESC4C");
            descripcion4[Constantes.D] = rs.getString("INICDESC4D");
            descripcion4[Constantes.E] = rs.getString("INICDESC4E");
            descripcion4[Constantes.F] = rs.getString("INICDESC4F");
            descripcion4[Constantes.G] = rs.getString("INICDESC4G");
            descripcion4[Constantes.H] = rs.getString("INICDESC4H");
            descripcion4[Constantes.I] = rs.getString("INICDESC4I");
            descripcion4[Constantes.J] = rs.getString("INICDESC4J");
            descripcion4[Constantes.K] = rs.getString("INICDESC4K");
            descripcion4[Constantes.L] = rs.getString("INICDESC4L");
            descripcion4[Constantes.M] = rs.getString("INICDESC4M");
           
            descripcion5[Constantes.A] = rs.getString("INICDESC5A");
            descripcion5[Constantes.B] = rs.getString("INICDESC5B");
            descripcion5[Constantes.C] = rs.getString("INICDESC5C");
            descripcion5[Constantes.D] = rs.getString("INICDESC5D");
            descripcion5[Constantes.E] = rs.getString("INICDESC5E");
            descripcion5[Constantes.F] = rs.getString("INICDESC5F");
            descripcion5[Constantes.G] = rs.getString("INICDESC5G");
            descripcion5[Constantes.H] = rs.getString("INICDESC5H");
            descripcion5[Constantes.I] = rs.getString("INICDESC5I");
            descripcion5[Constantes.J] = rs.getString("INICDESC5J");
            descripcion5[Constantes.K] = rs.getString("INICDESC5K");
            descripcion5[Constantes.L] = rs.getString("INICDESC5L");
            descripcion5[Constantes.M] = rs.getString("INICDESC5M");
            
            descripcion6[Constantes.A] = rs.getString("INICDESC6A");
            descripcion6[Constantes.B] = rs.getString("INICDESC6B");
            descripcion6[Constantes.C] = rs.getString("INICDESC6C");
            descripcion6[Constantes.D] = rs.getString("INICDESC6D");
            descripcion6[Constantes.E] = rs.getString("INICDESC6E");
            descripcion6[Constantes.F] = rs.getString("INICDESC6F");
            descripcion6[Constantes.G] = rs.getString("INICDESC6G");
            descripcion6[Constantes.H] = rs.getString("INICDESC6H");
            descripcion6[Constantes.I] = rs.getString("INICDESC6I");
            descripcion6[Constantes.J] = rs.getString("INICDESC6J");
            descripcion6[Constantes.K] = rs.getString("INICDESC6K");
            descripcion6[Constantes.L] = rs.getString("INICDESC6L");
            descripcion6[Constantes.M] = rs.getString("INICDESC6M");
        }
        catch(SQLException e){
            Mensaje.error("SetInfoInstComprendidas.java: "+e.getMessage(), e);
        }
    }

    public void rellenarFormulario() {
        frmDatosTecnicos.jTxtFldTension1_1.setText(tension1[1]);
        frmDatosTecnicos.jTxtFldTension1_2.setText(tension1[2]);
        frmDatosTecnicos.jTxtFldTension1_3.setText(tension1[3]);
        frmDatosTecnicos.jTxtFldTension1_4.setText(tension1[4]);
        frmDatosTecnicos.jTxtFldTension1_5.setText(tension1[5]);
        frmDatosTecnicos.jTxtFldTension1_6.setText(tension1[6]);
        frmDatosTecnicos.jTxtFldTension2_1.setText(tension2[1]);
        frmDatosTecnicos.jTxtFldTension2_2.setText(tension2[2]);
        frmDatosTecnicos.jTxtFldTension2_3.setText(tension2[3]);
        frmDatosTecnicos.jTxtFldTension2_4.setText(tension2[4]);
        frmDatosTecnicos.jTxtFldTension2_5.setText(tension2[5]);
        frmDatosTecnicos.jTxtFldTension2_6.setText(tension2[6]);
        
        frmDatosTecnicos.jTxtFldDescripcion1.setText(descripcion1);
        frmDatosTecnicos.jTxtFldDescripcion2A.setText(descripcion2A);
        frmDatosTecnicos.jTxtFldDescripcion2B.setText(descripcion2B);
        frmDatosTecnicos.jTxtFldDescripcion3A.setText(descripcion3A);
        frmDatosTecnicos.jTxtFldDescripcion3B.setText(descripcion3B);
        
        frmDatosTecnicos.jTxtFldDescripcion4A.setText(descripcion4[Constantes.A]);
        frmDatosTecnicos.jTxtFldDescripcion4B.setText(descripcion4[Constantes.B]);
        frmDatosTecnicos.jTxtFldDescripcion4C.setText(descripcion4[Constantes.C]);
        frmDatosTecnicos.jTxtFldDescripcion4D.setText(descripcion4[Constantes.D]);
        frmDatosTecnicos.jTxtFldDescripcion4E.setText(descripcion4[Constantes.E]);
        frmDatosTecnicos.jTxtFldDescripcion4F.setText(descripcion4[Constantes.F]);
        frmDatosTecnicos.jTxtFldDescripcion4G.setText(descripcion4[Constantes.G]);
        frmDatosTecnicos.jTxtFldDescripcion4H.setText(descripcion4[Constantes.H]);
        frmDatosTecnicos.jTxtFldDescripcion4I.setText(descripcion4[Constantes.I]);
        frmDatosTecnicos.jTxtFldDescripcion4J.setText(descripcion4[Constantes.J]);
        frmDatosTecnicos.jTxtFldDescripcion4K.setText(descripcion4[Constantes.K]);
        frmDatosTecnicos.jTxtFldDescripcion4L.setText(descripcion4[Constantes.L]);
        frmDatosTecnicos.jTxtFldDescripcion4M.setText(descripcion4[Constantes.M]);
        
        frmDatosTecnicos.jTxtFldDescripcion5A.setText(descripcion5[Constantes.A]);
        frmDatosTecnicos.jTxtFldDescripcion5B.setText(descripcion5[Constantes.B]);
        frmDatosTecnicos.jTxtFldDescripcion5C.setText(descripcion5[Constantes.C]);
        frmDatosTecnicos.jTxtFldDescripcion5D.setText(descripcion5[Constantes.D]);
        frmDatosTecnicos.jTxtFldDescripcion5E.setText(descripcion5[Constantes.E]);
        frmDatosTecnicos.jTxtFldDescripcion5F.setText(descripcion5[Constantes.F]);
        frmDatosTecnicos.jTxtFldDescripcion5G.setText(descripcion5[Constantes.G]);
        frmDatosTecnicos.jTxtFldDescripcion5H.setText(descripcion5[Constantes.H]);
        frmDatosTecnicos.jTxtFldDescripcion5I.setText(descripcion5[Constantes.I]);
        frmDatosTecnicos.jTxtFldDescripcion5J.setText(descripcion5[Constantes.J]);
        frmDatosTecnicos.jTxtFldDescripcion5K.setText(descripcion5[Constantes.K]);
        frmDatosTecnicos.jTxtFldDescripcion5L.setText(descripcion5[Constantes.L]);
        frmDatosTecnicos.jTxtFldDescripcion5M.setText(descripcion5[Constantes.M]);
        
        frmDatosTecnicos.jTxtFldDescripcion6A.setText(descripcion6[Constantes.A]);
        frmDatosTecnicos.jTxtFldDescripcion6B.setText(descripcion6[Constantes.B]);
        frmDatosTecnicos.jTxtFldDescripcion6C.setText(descripcion6[Constantes.C]);
        frmDatosTecnicos.jTxtFldDescripcion6D.setText(descripcion6[Constantes.D]);
        frmDatosTecnicos.jTxtFldDescripcion6E.setText(descripcion6[Constantes.E]);
        frmDatosTecnicos.jTxtFldDescripcion6F.setText(descripcion6[Constantes.F]);
        frmDatosTecnicos.jTxtFldDescripcion6G.setText(descripcion6[Constantes.G]);
        frmDatosTecnicos.jTxtFldDescripcion6H.setText(descripcion6[Constantes.H]);
        frmDatosTecnicos.jTxtFldDescripcion6I.setText(descripcion6[Constantes.I]);
        frmDatosTecnicos.jTxtFldDescripcion6J.setText(descripcion6[Constantes.J]);
        frmDatosTecnicos.jTxtFldDescripcion6K.setText(descripcion6[Constantes.K]);
        frmDatosTecnicos.jTxtFldDescripcion6L.setText(descripcion6[Constantes.L]);
        frmDatosTecnicos.jTxtFldDescripcion6M.setText(descripcion6[Constantes.M]);
        
        //Potencia Instalada
        frmDatosTecnicos.jTxtFldPotInst1.setText(potenciaInst[1]);
        frmDatosTecnicos.jTxtFldPotInst2.setText(potenciaInst[2]);
        frmDatosTecnicos.jTxtFldPotInst3.setText(potenciaInst[3]);
        frmDatosTecnicos.jTxtFldPotInst4.setText(potenciaInst[4]);
        frmDatosTecnicos.jTxtFldPotInst5.setText(potenciaInst[5]);
        frmDatosTecnicos.jTxtFldPotInst6.setText(potenciaInst[6]);
    } 
}
