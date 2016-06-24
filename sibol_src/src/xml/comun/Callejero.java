/*
 * Callejero.java
 *
 * Created on 6 de octubre de 2006, 10:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package xml.comun;

import funciones.BaseDatos;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Isabel
 */
public class Callejero {
    private BaseDatos bd = null;
    private ResultSet rs = null;
    private comunBD consulta = null;
    private String provincia = null;
    private String municipio = null;
    private String localidad = null;
    private String calle = null;
    private boolean titular = false;
    
    /** Creates a new instance of Callejero */
    // Campo titular --> indica si los datos a recoger son del titular (true) o de la instalación (false).
    public Callejero(BaseDatos bd, ResultSet rs, boolean titular) throws Exception {       
        this.bd = bd;    
        this.rs = rs;

        try{
            consulta = new comunBD(bd);
            this.titular = titular;
        }
        catch(Exception e){
            throw new Exception("Exception constructor Callejero: "+e.getMessage());
        }
        
    }

    // PROVINCIA
    private void crearProvincia() throws Exception {
        provincia = "";
        String codigo = "";
        
        
        try {
            codigo = rs.getString("INPRID");
            if (titular) 
                codigo = rs.getString("INTITPRID");
            
            provincia = "\t\t <PROVINCIA> \n" + 
                "\t\t\t <CODIGO"+(codigo == null ? "/>" : ">" + codigo + "</CODIGO>") + "\n" +
                "\t\t\t<NOMBRE"+(codigo == null ? "/>" : ">" + consulta.getProvinciaNombreBD(codigo) + "</NOMBRE>") + "\n" +
                "\t\t </PROVINCIA>\n";
        } catch(Exception e){
            throw new Exception("Exception Provincia: "+e.getMessage());
        }
    }
    public String getProvincia() throws Exception {
        crearProvincia();
        return provincia;
    }


    // MUNICIPIO
    private void crearMunicipio() throws Exception {
        municipio = "";
        String codigo = "";
        String provincia = ""; 
        
        
        try {
            codigo = rs.getString("INMUID");
            provincia = rs.getString("INPRID");
            if (titular) {
                codigo = rs.getString("INTITMUID");
                provincia = rs.getString("INTITPRID");
            }

            municipio = "\t\t <MUNICIPIO> \n" + 
                "\t\t\t <CODIGO"+(codigo == null ? "/>" : ">" + codigo + "</CODIGO>") + "\n" +
                "\t\t\t<NOMBRE"+(codigo == null ? "/>" : ">" + consulta.getMunicipioNombreBD(provincia, codigo) + "</NOMBRE>") + "\n" +
                "\t\t </MUNICIPIO>\n";
        } catch(Exception e){
            throw new Exception("Exception Municipio: "+e.getMessage());
        }
    }
    public String getMunicipio() throws Exception {
        crearMunicipio();
        return municipio;
    }
    
    // LOCALIDAD
    private void crearLocalidad() throws Exception {
        localidad = "";
        String codigo = "";
        String provincia = ""; 
        String municipio = "";
        
        try {
            codigo = rs.getString("INLCID");
            provincia = rs.getString("INPRID");
            municipio = rs.getString("INMUID");
            if (titular) {
                codigo = rs.getString("INTITLCID");
                provincia = rs.getString("INTITPRID");
                municipio = rs.getString("INTITMUID");
            }
            
            localidad = "\t\t <LOCALIDAD> \n" + 
                "\t\t\t <CODIGO"+(codigo == null ? "/>" : ">" + codigo + "</CODIGO>") + "\n" +
                "\t\t\t<NOMBRE"+(codigo == null ? "/>" : ">" + consulta.getLocalidadNombreBD(provincia, municipio, codigo) + "</NOMBRE>") + "\n" +
                "\t\t </LOCALIDAD>\n";
        } catch(Exception e){
            throw new Exception("Exception Localidad: "+e.getMessage());
        }
    }
    public String getLocalidad() throws Exception {
        crearLocalidad();
        return localidad;
    }

    // CALLE
    private void crearCalle() throws Exception {
        calle = "";
        String codigo = "";
        String provincia = ""; 
        String municipio = "";
        String localidad = "";
        
        try {
            codigo = rs.getString("INEMPLAZAMIENTO");
            provincia = rs.getString("INPRID");
            municipio = rs.getString("INMUID");
            localidad = rs.getString("INLCID");
            if (titular) {
                codigo = rs.getString("INTITDIRECCION");
                provincia = rs.getString("INTITPRID");
                municipio = rs.getString("INTITMUID");
                localidad= rs.getString("INTITLCID");
            }
            calle = "\t\t <CALLE> \n" + 
                "\t\t\t<CODIGO>" + (codigo == null ? "0" : consulta.getCalleCodBD(provincia, municipio, localidad, codigo)) + "</CODIGO>" + "\n" +
                "\t\t\t<NOMBRE"+(codigo == null ? "/>" : ">" + consulta.getCalleNombreBD(provincia, municipio, localidad,codigo) + "</NOMBRE>") + "\n" +
                "\t\t </CALLE>\n";
        } catch(Exception e){
            throw new Exception("Exception Calle: "+e.getMessage());
        }
    }
    public String getCalle() throws Exception {
        crearCalle();
        return calle;
    }
    
}
