/*
 * NewClass.java
 *
 * Created on 11 de septiembre de 2006, 12:16
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
import main.Mensaje;

/**
 *
 * @author enrique
 */
public class SetInfoPrevisionOficinas implements IntfzEstablecerDatos{
    private FrmDatosTecnicosPnl frmDatosTecnicos;
    private BaseDatos bd;
    private int idInstalacion;
    
    //Oficinas
    private String oficinas, estab, supOficinas, supEstab, demandaOficinas, demandaEstab;
    
    //SS Generales
    private String ascensores, alumbrado, otros;
    
    //Otras cargas
    private String descripcion, potPrevista;
    
    //Cargas totales edificio
    private String cargasTotales;
    
     public SetInfoPrevisionOficinas(FrmPrincipal frmPrincipal, int idInst) {
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
            oficinas        = rs.getString("ININDOFIC");
            supOficinas     = Utilidades.cambiarPunto(rs.getString("ININDOFICSUP"));
            demandaOficinas = Utilidades.cambiarPunto(rs.getString("ININDOFICPOT"));
            estab           = rs.getString("ININD");
            supEstab        = Utilidades.cambiarPunto(rs.getString("ININDSUP"));
            demandaEstab    = Utilidades.cambiarPunto(rs.getString("ININDPOT"));
            
            //SS Generales
            ascensores  = Utilidades.cambiarPunto(rs.getString("ININDASCPOT"));
            alumbrado   = Utilidades.cambiarPunto(rs.getString("ININDALUMBPOT"));
            otros       = Utilidades.cambiarPunto(rs.getString("ININDOTROSPOT"));
            
            //Locales comerciales y oficinas
            descripcion = rs.getString("ININDOTRASDESC");
            potPrevista = Utilidades.cambiarPunto(rs.getString("ININDOTRASPOT"));
            
            //Cargas totales edificio
            cargasTotales = Utilidades.cambiarPunto(rs.getString("ININDPOTTOT"));

        }
        catch(SQLException e){
            Mensaje.error("SetInfoPrevisionOficinas.java: "+e.getMessage(),e);
        }
    }

    public void rellenarFormulario() {
            //Viviendas
            frmDatosTecnicos.jTxtFldNumTotalOficinas.setText(oficinas);
            frmDatosTecnicos.jTxtFldSuperficieOficinas.setText(supOficinas);
            frmDatosTecnicos.jTxtFldDemandaOficinas.setText(demandaOficinas);
            frmDatosTecnicos.jTxtFldNumTotalEstabOficinas.setText(estab);
            frmDatosTecnicos.jTxtFldSuperficieTotalEstabOficinas.setText(supEstab);
            frmDatosTecnicos.jTxtFldDemandaEstabOficinas.setText(demandaEstab);
            
            //SS Generales
            frmDatosTecnicos.jTxtFldAscensoresOficinas.setText(ascensores);
            frmDatosTecnicos.jTxtFldAlumbradoOficinas.setText(alumbrado);
            frmDatosTecnicos.jTxtFldOtrosServOficinas.setText(otros);
            
            //Locales comerciales y oficinas
            frmDatosTecnicos.jTxtFldDescripcionOficinas.setText(descripcion);
            frmDatosTecnicos.jTxtFldPotPrevistaOficinas.setText(potPrevista);

            //Cargas totales edificio
            frmDatosTecnicos.jTxtFldCargasTotalesOficinas.setText(cargasTotales);
            
    }
}