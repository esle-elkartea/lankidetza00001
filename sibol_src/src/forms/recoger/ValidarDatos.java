/*
 * ValidarDatos.java
 *
 * Created on 23 de agosto de 2006, 12:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package forms.recoger;

import funciones.Sesion;
import java.sql.SQLException;
import main.Mensaje;

/**
 *
 * @author enrique
 */
public class ValidarDatos {
    
    private DatosGestion datosGestion;
    private CaractGenerales caractGenDatosTecn;
    private InstComprendidas instComprendidas;
    private PrevisionOficinas prevOficinas;
    private PrevisionViviendas prevViviendas;
    private SuministroDatosTecn sumDatosTecn;
    private PrevisionIndustriales prevIndustriales;
    private PrevisionIndustriales resumen;
    
    private String txtErrorDatosGestion;
    private String txtErrorDatosTecnicos;
    private boolean validoDatosTecnicos;
    private boolean validoDatosGestion;
    
    /**
     * Creates a new instance of ValidarDatos
     */
    public ValidarDatos() {
        datosGestion        = new DatosGestion();
        caractGenDatosTecn  = new CaractGenerales();
        instComprendidas    = new InstComprendidas();
        prevOficinas        = new PrevisionOficinas();
        prevViviendas       = new PrevisionViviendas();
        sumDatosTecn        = new SuministroDatosTecn();
        prevIndustriales    = new PrevisionIndustriales();
        // OSCAR - Ya no es necesario validar el resumen
        //resumen             = new Resumen();
        
    }
        
    //Devuelve un booleano indicando si todo el FrmDatosTecnicosPnl es correcto actualizando el mensaje de errores: "txtErrorDatosGestion". y la BD
    public void procesarDatosTecnicosPnl(){
        String txtErrorSuministros      = "";
        String txtErrorInstComprendidas = "";
        String txtErrorCaractGenerales  = "";
        String txtErrorPrevIndustriales = "";
        String txtErrorPrevViviendas    = "";
        String txtErrorPrevOficinas     = "";
        // OSCAR - Ya no es necesario validar el resumen
        // String txtErrorResumen          = "";
        this.txtErrorDatosTecnicos      = "";
        this.validoDatosTecnicos        = true;

        try{
            if(!esValidoSumDatosTecn())
            {
                txtErrorSuministros      = Sesion.getInstance().getValorHt("txtErrSuministros").toString();
                txtErrorDatosTecnicos += txtErrorSuministros;
                validoDatosTecnicos = false;
            }
            else
                insertarBDSumDatosTecn();
            if(!esValidoInstComprendidas())
            {
                txtErrorInstComprendidas      = Sesion.getInstance().getValorHt("txtErrorInstComprendidas").toString();
                txtErrorDatosTecnicos += txtErrorInstComprendidas;
                validoDatosTecnicos = false;
            }
            else
                insertarBDInstComprendidas();
            if(!esValidoCaractGenDatosTecn())
            {
                txtErrorCaractGenerales      = Sesion.getInstance().getValorHt("txtErrorCaractGenerales").toString();
                txtErrorDatosTecnicos += txtErrorCaractGenerales;
                validoDatosTecnicos = false;
            }
            else
                insertarBDCaractGenDatosTecn();
            if(!esValidoPrevIndustriales())
            {
                txtErrorPrevIndustriales      = Sesion.getInstance().getValorHt("txtErrorPrevIndustriales").toString();
                txtErrorDatosTecnicos += txtErrorPrevIndustriales;
                validoDatosTecnicos = false;
            }
            else
                insertarBDPrevIndustriales();
            if(!esValidoPrevViviendas())
            {
                txtErrorPrevViviendas      = Sesion.getInstance().getValorHt("txtErrorPrevViviendas").toString();
                txtErrorDatosTecnicos += txtErrorPrevViviendas;
                validoDatosTecnicos = false;
            }
            else
                insertarBDPrevViviendas();
            if(!esValidoPrevOficinas())
            {
                txtErrorPrevOficinas      = Sesion.getInstance().getValorHt("txtErrorPrevOficinas").toString();
                txtErrorDatosTecnicos += txtErrorPrevOficinas;
                validoDatosTecnicos = false;
            }
            else
                insertarBDPrevOficinas();
// OSCAR - Como estos datos son ahora calculados desde el grafo, no realizamos la validacion ni el guardado en este punto            
//            if(!esValidoResumen())
//            {
//                txtErrorResumen      = Sesion.getInstance().getValorHt("txtErrorResumen").toString();
//                txtErrorDatosTecnicos += txtErrorResumen;
//                validoDatosTecnicos = false;
//            }
//            else
//                insertarBDResumen(); 
        }
        catch(SQLException e){
            Mensaje.error(" ValidarDatos.java:\n Error en el procesamiento de los datos técnicos: "+e.getMessage(),e);
        }
    }
    
    //Métodos que validan cada parte de los formularios por separado.
    public void procesarDatosGestionPnl(){
        validoDatosGestion = true;
        txtErrorDatosGestion = "";
        try
        {
            if(datosGestion.esValido())
            {
                insertarBDDatosGestionPnl();
                validoDatosGestion = true;
            }
            else
            {
                txtErrorDatosGestion += Sesion.getInstance().getValorHt("txtErrDatosGestion").toString();
                validoDatosGestion = false;
            }
        }
        catch(SQLException e){
            Mensaje.error(" ValidarDatos.java:\n Error en el procesamiento de los datos de gestión: "+e.getMessage(),e);
        }
    }
    public boolean esValidoCaractGenDatosTecn(){
        return caractGenDatosTecn.esValido();
    }
    public boolean esValidoInstComprendidas(){
        return instComprendidas.esValido();
    }
    public boolean esValidoPrevOficinas(){
        return prevOficinas.esValido();
    }
    public boolean esValidoPrevViviendas(){
        return prevViviendas.esValido();
    }
    public boolean esValidoSumDatosTecn(){
        return sumDatosTecn.esValido();
    }
    public boolean esValidoPrevIndustriales(){
        return prevIndustriales.esValido();
    }
// OSCAR - Esta funcion no es ya necesaria porque estos datos ahora no son editables    
//    public boolean esValidoResumen(){
//        return resumen.esValido();
//    }
    
    //Actualiza todo el formulario FrmDatosTecnicosPnl.java
    public void insertarBDDatosTecnicosPnl() throws SQLException{
        caractGenDatosTecn.insertarBD();
        instComprendidas.insertarBD();
        prevOficinas.insertarBD();
        prevOficinas.insertarBD();
        prevViviendas.insertarBD();
        sumDatosTecn.insertarBD();
// OSCAR - Ya no es necesario validar el resumen
//        resumen.insertarBD();
    }    
    
    //Metodos que actualizan la BD por separado
    public void insertarBDDatosGestionPnl() throws SQLException{
        datosGestion.insertarBD();
    }
    public void insertarBDCaractGenDatosTecn() throws SQLException{
        caractGenDatosTecn.insertarBD();
    }
    public void insertarBDInstComprendidas() throws SQLException{
        instComprendidas.insertarBD();
    }
    public void insertarBDPrevOficinas() throws SQLException{
        prevOficinas.insertarBD();
    }
    public void insertarBDPrevViviendas() throws SQLException{
        prevViviendas.insertarBD();
    }
    public void insertarBDSumDatosTecn() throws SQLException{
        sumDatosTecn.insertarBD();
    }
    public void insertarBDPrevIndustriales() throws SQLException{
        prevIndustriales.insertarBD();
    }
// OSCAR - El resumen se introduce desde el grafo y no desde el formulario   
//    public void insertarBDResumen() throws SQLException{
//        resumen.insertarBD();
//    }
    
    //Actualiza la BD de una atacada.
    public void insertarBDFormCompleto()  throws SQLException
    {
        //Formulario DatosGestionPnl
        datosGestion.insertarBD();
        //Formulario DatosTecnicosPnl
        caractGenDatosTecn.insertarBD();
        instComprendidas.insertarBD();
        prevOficinas.insertarBD();
        prevViviendas.insertarBD();
        sumDatosTecn.insertarBD();
        prevIndustriales.insertarBD();
// OSCAR - El resumen se introduce desde el grafo y no desde el formulario        
//        resumen.insertarBD();
        
    }

    public String getTxtErrorDatosGestion() {
        return txtErrorDatosGestion;
    }

    public String getTxtErrorDatosTecnicos() {
        return txtErrorDatosTecnicos;
    }

    public boolean esValidoDatosTecnicos() {
        return validoDatosTecnicos;
    }

    public boolean esValidoDatosGestion() {
        return validoDatosGestion;
    }
}
