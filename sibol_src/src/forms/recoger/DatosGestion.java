/*
 * DatosGestion.java
 *
 * Created on 10 de agosto de 2006, 9:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package forms.recoger;

import forms.FrmGestionPnl;
import funciones.BaseDatos;
import funciones.ParCombo;
import funciones.Sesion;
import funciones.Utilidades;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.Constantes;

/**
 *
 * @author enrique
 */
public class DatosGestion implements IntfzValidarDatos{
    
    private FrmGestionPnl frmGestion;
    private String txtErrorGlobal, txtTitular, txtEmpInst, txtCaractInst, txtEmpSum, txtTecnCualif;
    private BaseDatos bd;
    
    private String nombreTitular, direccionTitular, nifTitular, tfnoTitular, cpTitular, representanteTitular, dniRepresentanteTitular;
    private String portalTit, bisTit, escaleraTit, pisoTit, puertaTit;
            
    private String  cups1, cups2, cups3;
    
    private ParCombo locTitular, munTitular, provTitular;
    private int idEmpresaInst;
    
    private String emplazamientoCar, portalCar, bisCar, escaleraCar, pisoCar, puertaCar, cpCar, tfnoInstalacion, emailInstalacion;

    private ParCombo locInst, munInst, provInst ,empSum;
    private int idTecnicoCualif;
    
    /**
     * Creates a new instance of DatosGestion
     */
    public DatosGestion() {
        frmGestion = (FrmGestionPnl)Sesion.getInstance().getValorHt("objFrmGestionPnl");
        bd = (BaseDatos)Sesion.getInstance().getBaseDatos();
    }
    
    public boolean esValido()
    {
        txtErrorGlobal = "";
        txtTitular = "";
        txtEmpInst = "";
        txtCaractInst = "";
        txtEmpSum = "";
        txtTecnCualif = "";
        
        boolean esValido = true;
        
        recogerDatos();
        
        if(!esCorrectoTitular())
        {
            esValido = false;
            txtErrorGlobal += txtTitular;
        }
        if(!esCorrectoEmpSum())
        {
            esValido = false;
            txtErrorGlobal += txtEmpSum;
        }
        if(!esCorrectoEmpInst())
        {
            esValido = false;
            txtErrorGlobal += txtEmpInst;
        }
        if(!esCorrectoCaractInst())
        {    
            esValido = false;
            txtErrorGlobal += txtCaractInst;
        }
        /*
        if(!esCorrectoTecnicoCualif())
        {    
            esValido = false;
            txtErrorGlobal += txtTecnCualif;
        }
        */
        
        if(!esValido)
            Sesion.getInstance().setValorHt("txtErrDatosGestion",txtErrorGlobal);

        return esValido;
          
    }
    
    public void insertarBD() throws SQLException
    {
        String consulta = "UPDATE INSTALACIONES SET INTITNOMBRE='"+nombreTitular+"', INTITNIFCIF='"+nifTitular+"', " +
                " INTITDIRECCION='"+direccionTitular+"', INTITTFNO='"+tfnoTitular+"', INTITLCID="+locTitular.getKeyInt()+", " +
                " INTITMUID="+munTitular.getKeyInt()+", INTITPRID="+provTitular.getKeyInt()+", INTITCP='"+cpTitular+"', " +
                " INPORTALTIT='"+portalTit+"', INBISTIT='"+bisTit+"', INESCALERATIT='"+escaleraTit+"', INPISOTIT='"+pisoTit+"', " +
                " INPUERTATIT='"+puertaTit+"', INTITREPRESENT='"+representanteTitular+"', INTITREPRNIF='"+dniRepresentanteTitular+"', " +
                " INESPROV='"+empSum.getKeyInt()+"', INESNUM='"+empSum.getKeyInt2()+"', INCUPS1='"+cups1+"', INCUPS2='"+cups2+"', INCUPS3='"+cups3+"', " +
                " INITID="+idEmpresaInst+", INLCID="+locInst.getKeyInt()+", " +
                " INMUID="+munInst.getKeyInt()+", INPRID="+provInst.getKeyInt()+", INCP='"+cpCar+"', " +
                " INEMPLAZAMIENTO='"+emplazamientoCar+"', INPORTALCAR='"+portalCar+"', INBISCAR='"+bisCar+"', " +
                " INESCALERACAR='"+escaleraCar+"', INPISOCAR='"+pisoCar+"', INPUERTACAR='"+puertaCar+"', INTCID="+idTecnicoCualif+", INTELEFONO="+tfnoInstalacion+", INEMAILINST='"+emailInstalacion+"' " +
                " WHERE INID="+Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();

            bd.ejecModificacion(consulta);
            ResultSet rs = bd.getUltimoResgistro("Select INID FROM INSTALACIONES");     
    }
    
    //Comprueba los datos de la pesta�a EMPRESA/INSTALADOR
    private boolean esCorrectoEmpInst()
    {
        boolean nombreEmp, numEmpresa, eibt, nombreInst, numCarne, ccbt, tfno, email;
        txtEmpInst = "\n * Pesta�a \"Empresa/Instalador\":\n";
        nombreEmp = true;
        numEmpresa = true;
        eibt = true;
        nombreInst = true;
        numCarne = true;
        ccbt = true;
        tfno = true;
        email = true;
       
        if(frmGestion.jTxtFldNombreEmpresa.getText().equals(""))
        {
            nombreEmp=false;
            txtEmpInst += "      - APELLIDOS Y NOMBRE O RAZ�N SOCIAL no debe de estar vac�o.\n";
        }
        if(frmGestion.jTxtFldNumEmpresa.getText().equals(""))
        {
            numEmpresa=false;
            txtEmpInst += "      - N� de EMPRESA no debe de estar vac�o.\n";
        }
        if(frmGestion.jTxtFldEibt.getText().equals(""))
        {
            eibt=false;
            txtEmpInst += "      - EIBT no debe de estar vac�o.\n";
        }if(frmGestion.jTxtFldNombreInst.getText().equals(""))
        {
            nombreInst=false;
            txtEmpInst += "      - NOMBRE DEL INSTALADOR no debe de estar vac�o.\n";
            
        }       
        if(frmGestion.jTxtFldNumCarne.getText().equals(""))
        {
            numCarne=false;
            txtEmpInst += "      - N� del CARN� no debe de estar vac�o.\n";
        }
        if(frmGestion.jTxtFldCcbt.getText().equals(""))
        {
            ccbt=false;
            txtEmpInst += "      - CCBT no debe de estar vac�o.\n";
        }
        if(frmGestion.jTxtFldTelefonoEmp.getText().equals(""))
        {
            tfno=true;
        }
        else if(!Utilidades.esLongitudTexto(frmGestion.jTxtFldTelefonoEmp.getText(), 9))
        {
            tfno=false;
            txtEmpInst += "      - TEL�FONO si est� rellenado, que contenga 9 d�gitos.";
        }
        if(frmGestion.jTxtFldEmailEmp.getText().equals(""))
            email=true;
        else if(!Utilidades.esCorrectoEmail(frmGestion.jTxtFldEmailEmp.getText()))
        {
             email=false;
             txtEmpInst += "      - E-MAIL si s� est� rellenado, que sea correcto: xxx@yy.com\n";
        }
    
        if(nombreEmp && numEmpresa && eibt && nombreInst && numCarne && ccbt && tfno && email)
                return true;
        
        return false;
    }    
    //Comprueba los datos de la pesta�a CARACTER�STICAS DE LA INSTALACI�N
    private boolean esCorrectoCaractInst()
    {
        boolean provincias, nombreCar, cp, superficie, tension1, tension2, tfno, email;
        provincias  = true;
        nombreCar   = true;
        cp          = true;
        superficie  = true;
        tension1    = true;
        tension2    = true;
        tfno        = true;
        email       = true;
        txtCaractInst = "\n * Pesta�a \"Caracter�sticas de la Instalaci�n\":\n";
        
        if(provInst.getDescription().equals("") || munInst.getDescription().equals("") || locInst.getDescription().equals(""))
        {
            provincias=false;
            txtCaractInst += "      - PROVINCIA, MUNICIPIO y LOCALIDAD no deben estar vac�os.\n";
        }
        
        if(frmGestion.jTxtFldEmplazamientoInst.getText().equals(""))
        {
            nombreCar=false;
            txtCaractInst += "      - EMPLAZAMIENTO no debe de estar vac�o.\n";
        }
        if(frmGestion.jTxtFldCpInst.getText().equals("")){
            cp=false;
            txtCaractInst += "      - C.P. no debe de estar vac�o.\n";
        }
        else if(!Utilidades.esLongitudTexto(frmGestion.jTxtFldCpInst.getText(), 5))
        {
            cp=false;
            txtCaractInst += "      - C.P. debe contener 5 d�gitos.\n";
        } 
        if(!Utilidades.esLongitudTexto(frmGestion.jTxtFldTelefonoInstalacion.getText(), 9))
        {
            tfno=false;
            txtCaractInst += "      - TEL�FONO debe contener 9 d�gitos.\n";
        }
        if(frmGestion.jTxtFldEmailInstalacion.getText().equals("")){
//            email=false;
//            txtCaractInst += "      - E-MAIL no debe de estar vac�o.\n";
        }
        else if(!Utilidades.esCorrectoEmail(frmGestion.jTxtFldEmailInstalacion.getText()))
        {
            email=false;
            txtCaractInst += "      - E-MAIL debe tener formato correcto: xxx@yy.com\n";
        }
        
        if(provincias && nombreCar && cp && superficie && tfno && email)
                return true;
        
        return false;
    }
     //Comprueba los datos de la pesta�a TITULAR.
    private boolean esCorrectoTitular()
    {
        boolean provincias, nombre, nifCorrecto, direccion, tfno, cp, representante, dniRepresentante;
        txtTitular = "\n * Pesta�a \"Titular\":\n";
        nombre = true;
        nifCorrecto = true;
        provincias = true;
        direccion = true;
        tfno = true;
        cp = true;
        representante = true;
        dniRepresentante = true;
        
        if(nombreTitular.equals(""))
        {
            nombre=false;
            txtTitular += "      - APELLIDOS Y NOMBRE O RAZON SOCIAL no debe estar vac�o.\n";
        }
        if(!Utilidades.esCorrectoNIF_CIF(nifTitular))
        {
            nifCorrecto=false;
            txtTitular += "      - El NIF/CIF introducido no es correcto. Ej: 71333444Z � A1122334Z.\n";
        }
        if(provTitular.getDescription().equals("") || munTitular.getDescription().equals("") || locTitular.getDescription().equals(""))
        {
            provincias=false;
            txtTitular += "      - PROVINCIA, MUNICIPIO y LOCALIDAD no deben estar vac�os.\n";
        }
        if(direccionTitular.equals(""))
        {
            direccion=false;
            txtTitular += "      - DOMICILIO (calle plaza o n�mero) no debe estar vac�o.\n";
        }
        if(tfnoTitular.equals(""))
        {
            tfno=false;
            txtTitular += "      - TELEFONO del titular no debe de estar vac�o.\n";
        }
        else if(!Utilidades.esLongitudTexto(tfnoTitular, 9))
        {
            tfno=false;
            txtTitular +="       - TELEFONO del titular debe contener 9 d�gitos.\n";
        }        
        if(cpTitular.equals("")){
            cp=false;
            txtTitular += "      - C.P. no debe de estar vac�o.\n";
        }
        else if(!Utilidades.esLongitudTexto(cpTitular, 5))
        {
            cp=false;
            txtTitular += "      - C.P. debe contener 5 d�gitos.\n";
        }       
        
        //Mira si los campos OPCIONALES "REPRESENTANTE y DNI" est�n bien rellenados.
        if(representanteTitular.equals("") && dniRepresentanteTitular.equals(""))
        {
            representante=true;
        }
        else if(representanteTitular.equals("") && !dniRepresentanteTitular.equals(""))
        {
            dniRepresentante=false;
            txtTitular += "      - REPRESENTANTE (si procede) del representante no debe de estar vac�o si NIF est� rellenado.\n";
        }
        else if(!representanteTitular.equals("") && dniRepresentanteTitular.equals(""))
        {
            dniRepresentante=false;
            txtTitular += "      - NIF no debe de estar vac�o si REPRESENTANTE (si procede) est� rellenado.\n";
        }
        else
        {
            if(!Utilidades.esDniNifValido(dniRepresentanteTitular))
            {
                dniRepresentante= false;
                txtTitular +="      - NIF del representante ha de ser una valor v�lido: \"71333444Z .\n";
            }
        }
        
        if(nombre && provincias && direccion && nifCorrecto && tfno && cp && dniRepresentante)
                return true;
        
        return false;
        
    }    
    //Comprueba los datos de la pesta�a EMPRESA SUMINISTRADORA
    private boolean esCorrectoEmpSum(){
        boolean okEmpSum = true, okCUPS= true;
        txtEmpSum = "\n * Pesta�a \"Empresa suministradora\":\n";
        
        if(empSum.getDescription()=="")
        {
            txtEmpSum += "      - NOMBRE EMPRESA SUMINISTRADORA no debe estar vac�o.\n";
            okEmpSum = false;
        }
        
        if(!"".equals(cups1) || !"".equals(cups2) || !"".equals(cups3)){ // Si se ha introducido algo en CUPS
            if(!Utilidades.esLongitudTexto(cups1, 2) || !Utilidades.esLongitudTexto(cups2, 16) || !Utilidades.esLongitudTexto(cups3, 2)){
                txtEmpSum += "      - CUPS: Compruebe que su 1� casilla tenga 2 d�gitos, su 2� casilla 16 d�gitos y su 3� casilla 2 d�gitos.\n";
                okCUPS = false;
            }
        }

        /* Comentado porque CUPS no es obligatorio
        if(cups1.equals("") || cups2.equals("") || cups3.equals(""))
        {
            txtEmpSum += "      - CUPS no debe contener ninguna casilla vac�a.\n";
            okCUPS = false;
        }
        */
        
        if(okEmpSum && okCUPS) return true;
        
        return false;
    }
    
    //Comprueba los datos de la pesta�a TECNICO CUALIFICADO
    private boolean esCorrectoTecnicoCualif(){
        txtTecnCualif = "\n * Pesta�a \"T�cnico cualificado\":\n";
        
        boolean esCorrecto=true;
        if(frmGestion.jTxtFldNombreTec.getText().equals("")){
            esCorrecto=false;
            txtTecnCualif += "      - NOMBRE no debe de estar vac�o.\n";
        }
        if(frmGestion.jTxtFldTituloTec.getText().equals("")){
            esCorrecto=false;
            txtTecnCualif += "      - T�TULO T�CNICO no debe de estar vac�o.\n";
        }
        if(frmGestion.jTxtFldNumColegiado.getText().equals("")){
            esCorrecto=false;
            txtTecnCualif += "      - N� COLEGIADO no debe de estar vac�o.\n";
        }
        if(frmGestion.jTxtFldColegioOficial.getText().equals("")){
            esCorrecto=false;
            txtTecnCualif += "      - COLEGIO OFICIAL no debe de estar vac�o.\n";
        }
        
        return esCorrecto;
    }
    
    private void recogerDatos() {
        //Pesta�a Titular
        nombreTitular = frmGestion.jTxtFldNombreTitular.getText();
        nifTitular = frmGestion.jTxtFldNifTitular.getText();
        direccionTitular = frmGestion.jTxtFldDomicilioTitular.getText();
        tfnoTitular = frmGestion.jTxtFldTelefonoTitular.getText();
        cpTitular = frmGestion.jTxtFldCpTitular.getText();
        portalTit = frmGestion.jTxtFldPortalTit.getText();
        bisTit = frmGestion.jTxtFldBisTit.getText();
        escaleraTit = frmGestion.jTxtFldEscaleraTit.getText();
        pisoTit = frmGestion.jTxtFldPisoTit.getText();
        puertaTit = frmGestion.jTxtFldPuertaTit.getText();
        representanteTitular = frmGestion.jTxtFldRepresentanteTitular.getText();
        dniRepresentanteTitular = frmGestion.jTxtFldNifRepr.getText();
        locTitular = (ParCombo)frmGestion.jCmbBxLocalidadTitular.getSelectedItem();
        munTitular = (ParCombo)frmGestion.jCmbBxMunicipioTitular.getSelectedItem();
        provTitular = (ParCombo)frmGestion.jCmbBxProvinciaTitular.getSelectedItem();
        
        //Empresa suministradora        
        empSum = (ParCombo)frmGestion.jCmbBxEmpSuministradora.getSelectedItem();
        cups1  = frmGestion.jTxtFldCups1.getText();
        cups2  = frmGestion.jTxtFldCups2.getText();
        cups3  = frmGestion.jTxtFldCups3.getText();
                
        //Pesta�a caracter�sticas de la instalaci�n
        emplazamientoCar = frmGestion.jTxtFldEmplazamientoInst.getText();
        portalCar = frmGestion.jTxtFldPortalInst.getText();
        bisCar = frmGestion.jTxtFldBisInst.getText();
        escaleraCar = frmGestion.jTxtFldEscaleraInst.getText();
        pisoCar = frmGestion.jTxtFldPisoInst.getText();
        puertaCar = frmGestion.jTxtFldPuertaInst.getText();
        cpCar = frmGestion.jTxtFldCpInst.getText();
        locInst = (ParCombo)frmGestion.jCmbBxLocalidadInst.getSelectedItem();
        munInst = (ParCombo)frmGestion.jCmbBxMunicipioInst.getSelectedItem();
        provInst = (ParCombo)frmGestion.jCmbBxProvinciaInst.getSelectedItem();  
        tfnoInstalacion = frmGestion.jTxtFldTelefonoInstalacion.getText();
        emailInstalacion = frmGestion.jTxtFldEmailInstalacion.getText();
        
        try{
            //Pesta�a Empresa/Instalador
            idEmpresaInst = Integer.parseInt(Sesion.getInstance().getValorHt(Constantes.SES_INSTALADORES_ID).toString());
            //Pesta�a Tecnico cualificado
            idTecnicoCualif = Integer.parseInt(Sesion.getInstance().getValorHt(Constantes.SES_TECNICOS_CUALIFICADOS_ID).toString());
        }
        catch(NullPointerException e){
            idTecnicoCualif = -1;
        }
    }
}
