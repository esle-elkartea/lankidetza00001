/*
 * SetInfoFrmPrincipal.java
 *
 * Created on 7 de septiembre de 2006, 13:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package forms.establecer;

import forms.FrmGestionPnl;
import forms.FrmPrincipal;
import funciones.BaseDatos;
import funciones.ParCombo;
import funciones.Sesion;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.Constantes;
import main.Mensaje;

/**
 *
 * @author enrique
 */
public class SetInfoFrmDatosGestion implements IntfzEstablecerDatos{
    
    private FrmGestionPnl frmGestion;
    private BaseDatos bd;
    private int idInstalacion;
    
    //Titular
    private String nombreTitular, nifCifTitular, domicilioTitular, tfnoTitular, represTitular, nifRepresTitular, cpTitular;
    private String portalTit , bisTit , escaleraTit , pisoTit , puertaTit , cpTit;
    private int idProvTitular, idMunTitular, idLocTitular;
    
    //Empresa suministradora
    private String empSum;
    private String cups1, cups2, cups3;
    
    //Empresa/Instalador
    private int idInstalador;
    private String nombreEmp, numEmp, eibtEmp, nombreInsEmp, numCarneEmp, ccbtEmp, tfnoEmp, emailEmp, nifInstalador;
    private String idCatInstalador, idModalidad, estemp;
    
    //Caract instalación
    private String emplazamientoCar, portalCar , bisCar , escaleraCar , pisoCar , puertaCar , cpCar, tfnoInstalacion, emailInstalacion;
    private int idProvInst, idMunInst;
    
    private int idLocInst;
    
    //Tecnico cualificado
    private int idTecCualif;
    private String nombreTec, titulo, numColegiado, colegio, nif;
    
    //Constructor
    public SetInfoFrmDatosGestion(FrmPrincipal frmPrincipal, int idInst) {
        frmGestion = frmPrincipal.getFrmGestion();
        bd = Sesion.getInstance().getBaseDatos();
        idInstalacion = idInst;
    }
    
    private void rellenarCombosGestion() {
        ParCombo pcProvInst;
        ParCombo pcProvTitular;
        ParCombo pcLocalidadInst;
        ParCombo pcLocalidadTitular;
        ParCombo pcMunicipioInst;
        ParCombo pcMunicipioTitular;
        ParCombo pcTemp;
        ResultSet rs;
        
            for(int i=0; i<frmGestion.jCmbBxProvinciaTitular.getItemCount(); i++)
            {
                pcTemp = (ParCombo)frmGestion.jCmbBxProvinciaTitular.getItemAt(i);
                if(pcTemp.getKeyInt() == idProvTitular)
                    frmGestion.jCmbBxProvinciaTitular.setSelectedItem(pcTemp);
            }
            for(int i=0; i<frmGestion.jCmbBxMunicipioTitular.getItemCount(); i++)
            {
                pcTemp = (ParCombo)frmGestion.jCmbBxMunicipioTitular.getItemAt(i);
                if(pcTemp.getKeyInt() == idMunTitular)
                    frmGestion.jCmbBxMunicipioTitular.setSelectedItem(pcTemp);
            }
            for(int i=0; i<frmGestion.jCmbBxLocalidadTitular.getItemCount(); i++)
            {
                pcTemp = (ParCombo)frmGestion.jCmbBxLocalidadTitular.getItemAt(i);
                if(pcTemp.getKeyInt() == idLocTitular)
                    frmGestion.jCmbBxLocalidadTitular.setSelectedItem(pcTemp);
            }
            
            for(int i=0; i<frmGestion.jCmbBxProvinciaInst.getItemCount(); i++)
            {
                pcTemp = (ParCombo)frmGestion.jCmbBxProvinciaInst.getItemAt(i);
                if(pcTemp.getKeyInt() == idProvInst)
                    frmGestion.jCmbBxProvinciaInst.setSelectedItem(pcTemp);
            }
            
            for(int i=0; i<frmGestion.jCmbBxMunicipioInst.getItemCount(); i++)
            {                
                pcTemp = (ParCombo)frmGestion.jCmbBxMunicipioInst.getItemAt(i);
                if(pcTemp.getKeyInt() == idMunInst)
                    frmGestion.jCmbBxMunicipioInst.setSelectedItem(pcTemp);
            }
            
            for(int i=0; i<frmGestion.jCmbBxLocalidadInst.getItemCount(); i++)
            {                
                pcTemp = (ParCombo)frmGestion.jCmbBxLocalidadInst.getItemAt(i);
                if(pcTemp.getKeyInt() == idLocInst)
                    frmGestion.jCmbBxLocalidadInst.setSelectedItem(pcTemp);
            }
        
        //Categoria instalador en Empresa/Instalador
            for(int i=0; i<frmGestion.jCmbBxCatInst.getItemCount(); i++)
            {                
                pcTemp = (ParCombo)frmGestion.jCmbBxCatInst.getItemAt(i);
                if(pcTemp.getKeyString().equals(idCatInstalador))
                    frmGestion.jCmbBxCatInst.setSelectedItem(pcTemp);
            }
        
        //Modalidad en Empresa/Instalador
            for(int i=0; i<frmGestion.jCmbBxModalidadEmp.getItemCount(); i++)
            {                
                pcTemp = (ParCombo)frmGestion.jCmbBxModalidadEmp.getItemAt(i);
                if(pcTemp.getKeyString().equals(idModalidad))
                    frmGestion.jCmbBxModalidadEmp.setSelectedItem(pcTemp);
            }
        //Nombre Empresa Suministradora en Empresa/Suministradora
            for(int i=0; i<frmGestion.jCmbBxEmpSuministradora.getItemCount(); i++)
            {                
                pcTemp = (ParCombo)frmGestion.jCmbBxEmpSuministradora.getItemAt(i);                
                estemp = Integer.toString(pcTemp.getKeyInt2());
                if(Integer.toString(pcTemp.getKeyInt2()).equals(empSum))
                    frmGestion.jCmbBxEmpSuministradora.setSelectedItem(pcTemp);
            }
    }

    public void getInfoBD() {
        ResultSet rs = null;
        
        String consulta = "SELECT * FROM INSTALACIONES WHERE INID = "+idInstalacion;
        try{
            rs = bd.ejecSelect(consulta);
            rs.next();
            
            //Titular
            nombreTitular = rs.getString("INTITNOMBRE");
            nifCifTitular = rs.getString("INTITNIFCIF");
            idProvTitular   = rs.getInt("INTITPRID");
            idMunTitular    = rs.getInt("INTITMUID");
            idLocTitular    = rs.getInt("INTITLCID");
            cpTitular = rs.getString("INTITCP");
            domicilioTitular = rs.getString("INTITDIRECCION");
            
            portalTit = rs.getString("INPORTALTIT");
            bisTit = rs.getString("INBISTIT");
            escaleraTit = rs.getString("INESCALERATIT");
            pisoTit = rs.getString("INPISOTIT");
            puertaTit = rs.getString("INPUERTATIT");
            cpTit = rs.getString("INTITCP");
            
            tfnoTitular = rs.getString("INTITTFNO");
            represTitular = rs.getString("INTITREPRESENT");
            nifRepresTitular = rs.getString("INTITREPRNIF");
            
            //Empresa suministradora
            empSum = rs.getString("INESNUM");            
            cups1 = rs.getString("INCUPS1");
            cups2 = rs.getString("INCUPS2");
            cups3 = rs.getString("INCUPS3");
            
            //Empresa/Instalador
            idInstalador = rs.getInt("INITID");
            consulta = "SELECT * FROM INSTALADORES WHERE ITID="+idInstalador;
            Sesion.getInstance().setValorHt(Constantes.SES_INSTALADORES_ID, idInstalador);
            rs = bd.ejecSelect(consulta);
            if(rs.next())
            {   
                nombreEmp = rs.getString("ITNOMBREEMP");
                numEmp = rs.getString("ITNUMEMP1");
                eibtEmp = rs.getString("ITNUMEMP2");
                nombreInsEmp = rs.getString("ITINSTALADOR");
                numCarneEmp = rs.getString("ITCARNET1");
                ccbtEmp = rs.getString("ITCARNET2");
                tfnoEmp = rs.getString("ITTELEFONO");
                emailEmp = rs.getString("ITEMAIL");
                nifInstalador = rs.getString("ITNIF");
                rs = bd.ejecSelect("SELECT * FROM INSTALADORES WHERE ITID = "+idInstalador);
                rs.next();
                idCatInstalador = rs.getString("ITCATEGORIA");
                idModalidad = rs.getString("ITMODALIDAD");
            }

            //Caract. de la instalación
            consulta = "SELECT * FROM INSTALACIONES WHERE INID = "+idInstalacion;
            rs = bd.ejecSelect(consulta);
            rs.next();
            emplazamientoCar = rs.getString("INEMPLAZAMIENTO");
            portalCar  = rs.getString("INPORTALCAR");
            bisCar  = rs.getString("INBISCAR");
            escaleraCar  = rs.getString("INESCALERACAR");
            pisoCar  = rs.getString("INPISOCAR");
            puertaCar = rs.getString("INPUERTACAR");
            cpCar = rs.getString("INCP");
            idProvInst = rs.getInt("INPRID");
            idMunInst   = rs.getInt("INMUID");
            idLocInst   = rs.getInt("INLCID");
            tfnoInstalacion = rs.getString("INTELEFONO");
            emailInstalacion = rs.getString("INEMAILINST");
            
            //Tecnico cualificado
            idTecCualif = rs.getInt("INTCID");
            consulta = "SELECT * FROM TECNICOS_CUALIFICADOS WHERE TCID="+idTecCualif;
            Sesion.getInstance().setValorHt(Constantes.SES_TECNICOS_CUALIFICADOS_ID, idTecCualif);
            rs = bd.ejecSelect(consulta);
            if(rs.next()){
                
                nombreTec = rs.getString("TCNOMBRE");
                titulo = rs.getString("TCTITULO");
                numColegiado = rs.getString("TCNUMCOLEGIADO");
                colegio = rs.getString("TCCOLEGIO");
                nif = rs.getString("TCNIF");
            }
            
            rellenarCombosGestion();
        }
        catch(SQLException e){
            Mensaje.error("SetInfoDatosGestion.java: "+e.getMessage(),e);
        }
    }

    public void rellenarFormulario() {
            //Titular
            frmGestion.jTxtFldNombreTitular.setText(nombreTitular);
            frmGestion.jTxtFldNifTitular.setText(nifCifTitular);
            frmGestion.jTxtFldDomicilioTitular.setText(domicilioTitular);
            frmGestion.jTxtFldCpTitular.setText(cpTitular);
            frmGestion.jTxtFldTelefonoTitular.setText(tfnoTitular);
            frmGestion.jTxtFldRepresentanteTitular.setText(represTitular);
            frmGestion.jTxtFldNifRepr.setText(nifRepresTitular);
            frmGestion.jTxtFldPortalTit.setText(portalTit);
            frmGestion.jTxtFldBisTit.setText(bisTit);
            frmGestion.jTxtFldEscaleraTit.setText(escaleraTit);
            frmGestion.jTxtFldPisoTit.setText(pisoTit);
            frmGestion.jTxtFldPuertaTit.setText(puertaTit);
            
            //Empresa Suministradora
            //frmGestion.jTxtFldEmpSuministradora.setText(empSum);
            frmGestion.jTxtFldCups1.setText(cups1);
            frmGestion.jTxtFldCups2.setText(cups2);
            frmGestion.jTxtFldCups3.setText(cups3);

            //Empresa/Instalador
            frmGestion.jTxtFldNombreEmpresa.setText(nombreEmp);
            frmGestion.jTxtFldNumEmpresa.setText(numEmp);
            frmGestion.jTxtFldEibt.setText(eibtEmp);
            frmGestion.jTxtFldNombreInst.setText(nombreInsEmp);
            frmGestion.jTxtFldNumCarne.setText(numCarneEmp);
            frmGestion.jTxtFldCcbt.setText(ccbtEmp);
            frmGestion.jTxtFldTelefonoEmp.setText(tfnoEmp);
            frmGestion.jTxtFldEmailEmp.setText(emailEmp);
            frmGestion.jTxtFldNIFInstalador.setText(nifInstalador);
            
            //Caract. de la instalación
            frmGestion.jTxtFldCpInst.setText(cpCar);
            frmGestion.jTxtFldEmplazamientoInst.setText(emplazamientoCar);
            frmGestion.jTxtFldPortalInst.setText(portalCar);
            frmGestion.jTxtFldBisInst.setText(bisCar);
            frmGestion.jTxtFldEscaleraInst.setText(escaleraCar);
            frmGestion.jTxtFldPisoInst.setText(pisoCar);
            frmGestion.jTxtFldPuertaInst.setText(puertaCar);
            frmGestion.jTxtFldTelefonoInstalacion.setText(tfnoInstalacion);
            frmGestion.jTxtFldEmailInstalacion.setText(emailInstalacion);
            
            //Tecnico cualificado
            frmGestion.jTxtFldNombreTec.setText(nombreTec);
            frmGestion.jTxtFldTituloTec.setText(titulo);
            frmGestion.jTxtFldNumColegiado.setText(numColegiado);
            frmGestion.jTxtFldColegioOficial.setText(colegio);
            frmGestion.jTxtFldNIF.setText(nif);
    }
}
