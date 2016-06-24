/*
 * SetInfoPrevisionIndustriales.java
 *
 * Created on 11 de septiembre de 2006, 12:52
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
import funciones.beans.BeanCargasInd;
import funciones.beans.ModeloTablaPrevision;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.Mensaje;

/**
 *
 * @author enrique
 */
public class SetInfoPrevisionIndustriales implements IntfzEstablecerDatos{
    private FrmDatosTecnicosPnl     frmDatosTecnicos;
    private BaseDatos               bd;
    private ModeloTablaPrevision    modeloTablaPrevision;
    private int                     idInstalacion;
    private int                     numFilas;
    
    //Oficinas
    private String oficinas;
    
     public SetInfoPrevisionIndustriales(FrmPrincipal frmPrincipal, int idInst) {
        frmDatosTecnicos = frmPrincipal.getFrmDatosTecnicos();
        bd = Sesion.getInstance().getBaseDatos();
        modeloTablaPrevision = frmDatosTecnicos.modeloTablaPrevision;
        idInstalacion = idInst;
    }
     
     public void getInfoBD() {
         ResultSet rs = null;
        
        String consulta = "SELECT * FROM CARGAS_INDUSTRIALES WHERE CIINID = "+idInstalacion;
        try{
            numFilas = bd.getNumFilas(consulta);
        }
        catch(SQLException e){
            Mensaje.error("SetInfoPrevisionIndustriales.java: "+e.getMessage(),e);
        }
    }

    public void rellenarFormulario() {
        ParCombo tipoCarga;
        String idCargaIndustrial, idTipoCargaInd;
        String potencia, descripcion;
        ResultSet rs = null;
        BeanCargasInd beanCargaInd;
        
        String consulta = "SELECT * FROM CARGAS_INDUSTRIALES WHERE CIINID = "+idInstalacion;
        try{
            //relleno la tabla copmleta con los valores de la BD menos el JComboBox.
            rs = bd.ejecSelect(consulta);
            while(rs.next()){
                idCargaIndustrial = rs.getString("CIID");
                idTipoCargaInd    = rs.getString("CITGID");
                potencia          = Utilidades.cambiarPunto(rs.getString("CIPOTENCIA"));
                descripcion       = rs.getString("CIDESC");
                
                beanCargaInd = new BeanCargasInd();
                beanCargaInd.setPotencia(potencia);
                beanCargaInd.setIdCargInd(idCargaIndustrial);
                beanCargaInd.setDenominacion(descripcion);
                beanCargaInd.setIdInst(""+idInstalacion);
                        
                ResultSet rsTemp = bd.ejecSelect("SELECT * FROM TIPOS_CARGIND WHERE TGID = '"+idTipoCargaInd+"'");
                if(rsTemp.next()){
                    beanCargaInd.setTipoCarga(new ParCombo(""+idTipoCargaInd,rsTemp.getString("TGDESC")));
                }
                
                modeloTablaPrevision.anhadeFila(beanCargaInd);
            }
            
        }
        
        catch(SQLException e){
            Mensaje.error("SetInfoPrevisionIndustriales.java: "+e.getMessage(),e);
        }
    }
}