/*
 * EstablecerDatos.java
 *
 * Created on 8 de septiembre de 2006, 12:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package forms.establecer;

import forms.FrmPrincipal;
import funciones.Sesion;
import funciones.Utilidades;
import javax.swing.JFrame;
import main.Constantes;

/**
 *
 * @author enrique
 */
public class EstablecerDatos {
    
    private FrmPrincipal frmPrincipal;
    private SetInfoFrmDatosGestion setInfoFrmDatosGestion;
    private SetInfoSuministro setInfoSuministro;
    private SetInfoInstComprendidas setInfoInstComprendidas;
    private SetInfoCaractGenerales setInfoCaractGenerales;
    private SetInfoPrevisionViviendas setInfoPrevisionCargasViviendas;
    private SetInfoPrevisionOficinas setInfoPrevisionCargasOficinas;
    private SetInfoPrevisionIndustriales setInfoPrevisionIndustriales;
    private SetInfoResumen setInfoResumen;
    
    private int idInstalacion;
  
    
    /** Creates a new instance of EstablecerDatos */
    public EstablecerDatos(int idInst) {
        idInstalacion = idInst;
        Sesion.getInstance().setValorHt(Constantes.SES_INSTALACIONES_ID, idInstalacion);
        frmPrincipal = new FrmPrincipal();        
        setInfoFrmDatosGestion = new SetInfoFrmDatosGestion(frmPrincipal, idInstalacion);
        setInfoSuministro = new SetInfoSuministro(frmPrincipal, idInstalacion);
        setInfoInstComprendidas = new SetInfoInstComprendidas(frmPrincipal, idInstalacion);
        setInfoCaractGenerales = new SetInfoCaractGenerales(frmPrincipal, idInstalacion);
        setInfoPrevisionIndustriales = new SetInfoPrevisionIndustriales(frmPrincipal, idInstalacion);
        setInfoPrevisionCargasViviendas = new SetInfoPrevisionViviendas(frmPrincipal, idInstalacion);
        setInfoPrevisionCargasOficinas = new SetInfoPrevisionOficinas(frmPrincipal, idInstalacion);
        setInfoResumen = new SetInfoResumen(frmPrincipal, idInstalacion);
    }
    
    public void setVisibleFrmPrincipal(boolean esVisible, JFrame frm) {
        Utilidades.igualarFormularios(frmPrincipal, frm);
        frmPrincipal.setVisible(esVisible);
    }
    
    public void setInfoDatosGestion(){
        setInfoFrmDatosGestion.getInfoBD();
        setInfoFrmDatosGestion.rellenarFormulario();
    }
    
    public void setInfoSuministroDatosTecn(){
        setInfoSuministro.getInfoBD();
        setInfoSuministro.rellenarFormulario();
    }
    
    public void setInfoInstComprendidasDatosTecn(){
        setInfoInstComprendidas.getInfoBD();
        setInfoInstComprendidas.rellenarFormulario();
    }
    
    public void setInfoInstCaractGeneralesDatosTecn(){
        setInfoCaractGenerales.getInfoBD();
        setInfoCaractGenerales.rellenarFormulario();
    }
    
    public void setInfoPrevCargasViviendasDatosTecn(){
        setInfoPrevisionCargasViviendas.getInfoBD();
        setInfoPrevisionCargasViviendas.rellenarFormulario();
    }
    
    public void setInfoPrevCargasOficinasDatosTecn(){
        setInfoPrevisionCargasOficinas.getInfoBD();
        setInfoPrevisionCargasOficinas.rellenarFormulario();
    }
    
    public void setInfoPrevisionIndustrialesDatosTecn(){
        setInfoPrevisionIndustriales.getInfoBD();
        setInfoPrevisionIndustriales.rellenarFormulario();
    }
    
    public void setInfoResumenDatosTecn(){
        setInfoResumen.getInfoBD();
        setInfoResumen.rellenarFormulario();
    }
    
}
