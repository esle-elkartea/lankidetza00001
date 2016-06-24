/*
 * SetInfoSuministro.java
 *
 * Created on 8 de septiembre de 2006, 12:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package forms.establecer;

import forms.FrmDatosTecnicosPnl;
import forms.FrmPrincipal;
import funciones.BaseDatos;
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
public class SetInfoSuministro implements IntfzEstablecerDatos{
    private FrmDatosTecnicosPnl frmDatosTecnicos;
    private BaseDatos bd;
    private int idInstalacion;
    private String potComl, descripcion, potNormal;
    
     public SetInfoSuministro(FrmPrincipal frmPrincipal, int idInst) {
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
            
            //Titular
            potComl     = Utilidades.cambiarPunto(rs.getString("INPOTCOMPLEMENT"));
            descripcion = rs.getString("INDESCPOTCOMPLEMENT");
            potNormal   = Utilidades.cambiarPunto(rs.getString("INPOTNORMAL"));
        }
        catch(SQLException e){
            Mensaje.error("SetInfoSuministro.java: "+e.getMessage(),e);
        }
    }

    public void rellenarFormulario() {
        //Caract. de la instalación
        if (null==frmDatosTecnicos) {
            frmDatosTecnicos = (FrmDatosTecnicosPnl)Sesion.getInstance().getValorHt("objFrmDatosTecnicosPnl");
        }
        if (null!=frmDatosTecnicos) {
            if (potNormal.indexOf(",0")>0) {
                frmDatosTecnicos.jCmbBxPotenciaNormal.setSelectedItem(potNormal.substring(0,potNormal.indexOf(",0")));
            } else {
                frmDatosTecnicos.jCmbBxPotenciaNormal.setSelectedItem(potNormal);
            }
            String potencia = (String)frmDatosTecnicos.jCmbBxPotenciaNormal.getSelectedItem();
            Sesion.getInstance().setValorHt(Constantes.SES_POTENCIA_PREVISTA, potencia);
            Sesion.getInstance().setValorHt(Constantes.SES_TENSION1, frmDatosTecnicos.jTxtFldTension1.getText());
            Sesion.getInstance().setValorHt(Constantes.SES_TENSION2, frmDatosTecnicos.jTxtFldTension2.getText());
            if (potComl.indexOf(",0")>0) {
                frmDatosTecnicos.jCmbBxPotenciaCompl.setSelectedItem(potComl.substring(0,potComl.indexOf(",0")));
            } else {
                frmDatosTecnicos.jCmbBxPotenciaCompl.setSelectedItem(potComl);
            }
            frmDatosTecnicos.jTxtFldDescripcionSumCompl.setText(descripcion);
        }
    }
}
