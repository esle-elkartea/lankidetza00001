/*
 * NewClass.java
 *
 * Created on 11 de septiembre de 2006, 11:39
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
import main.Mensaje;

/**
 *
 * @author enrique
 */
public class SetInfoPrevisionViviendas implements IntfzEstablecerDatos{
    private FrmDatosTecnicosPnl frmDatosTecnicos;
    private BaseDatos bd;
    private int idInstalacion;
    
    //Viviendas
    private String numViv1, numViv2, sup1, sup2, demanda1, demanda2, cargasViviendas;
    private String idGradoElec1, idGradoElec2;
    
    //SS Generales
    private String ascensores, alumbrado, otros, garajes, cargasServicios;
    
    //Locales comerciales y oficinas
    private String supTotal, potPrevista, cargasLocales;
    
    //Cargas totales edificio
    private String cargasTotales;
    
     public SetInfoPrevisionViviendas(FrmPrincipal frmPrincipal, int idInst) {
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
            
            //Viviendas
            idGradoElec1 = rs.getString("INGEID1");
            idGradoElec2 = rs.getString("INGEID2");
            numViv1     = rs.getString("INNVIV1");
            numViv2     = rs.getString("INNVIV2");
            sup1        = Utilidades.cambiarPunto(rs.getString("INSUPUNIT1"));
            sup2        = Utilidades.cambiarPunto(rs.getString("INSUPUNIT2"));
            demanda1    = String.valueOf(rs.getInt("INDEMANDA1"));
            demanda2    = String.valueOf(rs.getInt("INDEMANDA2"));
            cargasViviendas = Utilidades.cambiarPunto(rs.getString("INVIVCARGPREV"));
            
            //SS Generales
            ascensores  = Utilidades.cambiarPunto(rs.getString("INPOTASC"));
            alumbrado   = Utilidades.cambiarPunto(rs.getString("INPOTALUMB"));
            otros       = Utilidades.cambiarPunto(rs.getString("INPOTOTROS"));
            garajes     = Utilidades.cambiarPunto(rs.getString("INPOTGARAJES"));
            cargasServicios = Utilidades.cambiarPunto(rs.getString("INSGCARGPREV"));
            
            
            //Locales comerciales y oficinas
            supTotal    = Utilidades.cambiarPunto(rs.getString("INOFICSUO"));
            potPrevista = Utilidades.cambiarPunto(rs.getString("INOFICPOT"));
            cargasLocales = Utilidades.cambiarPunto(rs.getString("INOFICCARGPREV"));
            
            //Cargas totales edificio
            cargasTotales = Utilidades.cambiarPunto(rs.getString("INOFICCARGTOT"));
        
        rellenarCombos();
        }
        catch(SQLException e){
            Mensaje.error("SetInfoPrevisionViviendas.java: "+e.getMessage(),e);
        }
    }

    public void rellenarFormulario() {
            //Viviendas
            frmDatosTecnicos.jTxtFldNumViv1.setText(numViv1);
            frmDatosTecnicos.jTxtFldNumViv2.setText(numViv2);
            frmDatosTecnicos.jTxtFldSuperficieUnit1.setText(sup1);
            frmDatosTecnicos.jTxtFldSuperficieUnit2.setText(sup2);
            if(!"B".equals(idGradoElec1)){
                frmDatosTecnicos.jTxtFldDemandaMax1.setText(demanda1);
            }
            if(!"B".equals(idGradoElec2)){
                frmDatosTecnicos.jTxtFldDemandaMax2.setText(demanda2);
            }
            frmDatosTecnicos.jTxtFldCargasPrevistasViviendas.setText(cargasViviendas);
            
            //SS Generales
            frmDatosTecnicos.jTxtFldAscensoresViviendas.setText(ascensores);
            frmDatosTecnicos.jTxtFldAlumbradoEscalera.setText(alumbrado);
            frmDatosTecnicos.jTxtFldOtrosServiciosViviendas.setText(otros);
            frmDatosTecnicos.jTxtFldGarajes.setText(garajes);
            frmDatosTecnicos.jTxtFldCargasServiciosViviendas.setText(cargasServicios);
            
            //Locales comerciales y oficinas
            frmDatosTecnicos.jTxtFldSuperficieUtilTotal.setText(supTotal);
            frmDatosTecnicos.jTxtFldPotEspecPrevista.setText(potPrevista);
            frmDatosTecnicos.jTxtFldCargasLocalesViviendas.setText(cargasLocales);

            //Cargas totales edificio
            frmDatosTecnicos.jTxtFldCargasTotalesViviendas.setText(cargasTotales);
            
            rellenarCombos();

    } 

    private void rellenarCombos() {
        ParCombo pcGrado1;
        ParCombo pcGrado2;
        ParCombo pcTemp;

        for(int i=0; i<frmDatosTecnicos.jCmbBxGradoElect1.getItemCount(); i++)
        {
            pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxGradoElect1.getItemAt(i);
            if(pcTemp.getKeyString().equals(idGradoElec1))
                frmDatosTecnicos.jCmbBxGradoElect1.setSelectedItem(pcTemp);
        }
        
        for(int i=0; i<frmDatosTecnicos.jCmbBxGradoElect2.getItemCount(); i++)
        {
            pcTemp = (ParCombo)frmDatosTecnicos.jCmbBxGradoElect2.getItemAt(i);
            if(pcTemp.getKeyString().equals(idGradoElec2))
                frmDatosTecnicos.jCmbBxGradoElect2.setSelectedItem(pcTemp);
        }
        if("B".equals(idGradoElec1)){
            frmDatosTecnicos.jComboBoxDemandaMax1.setSelectedItem(demanda1);
        }
        if("B".equals(idGradoElec2)){
            frmDatosTecnicos.jComboBoxDemandaMax2.setSelectedItem(demanda2);
        }
    }
}