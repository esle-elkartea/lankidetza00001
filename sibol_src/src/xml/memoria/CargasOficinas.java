/*
 * CargasOficinas.java
 *
 * Created on 24 de octubre de 2006, 13:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package xml.memoria;

import funciones.BaseDatos;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author isabel
 */
public class CargasOficinas {
    private BaseDatos bd = null;
    private ResultSet rs = null;
    private String cargasOficinas = null;   
    
    /** Creates a new instance of CargasOficinas */
    public CargasOficinas(BaseDatos bd, ResultSet rs) throws Exception {
        this.bd = bd;    
        this.rs = rs;
        try{
            crearCargasOficinas();
        }
        catch(Exception e){
            throw new Exception("Exception constructor CargasOficinas: "+e.getMessage());
        }           
    }
    
    private void crearCargasOficinas() throws Exception {
        cargasOficinas= "";
        try {
                cargasOficinas ="\t <CARGAS_OFICINAS>\n" +
                                "\t\t <TOTAL_OFICINAS"+(rs.getString("ININDOFIC")==null ? "/>" : ">" + rs.getString("ININDOFIC") + "</TOTAL_OFICINAS>") + "\n" +
                                "\t\t <SUPERFICIE_OFICINAS"+(rs.getString("ININDOFICSUP")==null ? "/>" : ">" + rs.getInt("ININDOFICSUP") + "</SUPERFICIE_OFICINAS>") + "\n" +
                                "\t\t <POTENCIA_OF"+(rs.getString("ININDOFICPOT")==null ? "/>" : ">" + rs.getInt("ININDOFICPOT") + "</POTENCIA_OF>") + "\n" +
                                "\t\t <ESTABLECIMIENTOS"+(rs.getString("ININD")==null ? "/>" : ">" + rs.getInt("ININD") + "</ESTABLECIMIENTOS>") + "\n" +
                                "\t\t <SUPERFICIE_ESTABLECIMIENTOS"+(rs.getString("ININDSUP")==null ? "/>" : ">" + rs.getInt("ININDSUP") + "</SUPERFICIE_ESTABLECIMIENTOS>") + "\n" +
                                "\t\t <POTENCIA_EST"+(rs.getString("ININDPOT")==null ? "/>" : ">" + rs.getInt("ININDPOT") + "</POTENCIA_EST>") + "\n" +
                                "\t\t <ASCENSORES"+(rs.getString("ININDASCPOT")==null ? "/>" : ">" + rs.getInt("ININDASCPOT") + "</ASCENSORES>") + "\n" +
                                "\t\t <ALUMBRADO_ESCALERA"+(rs.getString("ININDALUMBPOT")==null ? "/>" : ">" + rs.getInt("ININDALUMBPOT") + "</ALUMBRADO_ESCALERA>") + "\n" +
                                "\t\t <OTROS_SERVICIOS"+(rs.getString("ININDOTROSPOT")==null ? "/>" : ">" + rs.getInt("ININDOTROSPOT") + "</OTROS_SERVICIOS>") + "\n" +
                                "\t\t <OTRAS_CARGAS"+(rs.getString("ININDOTRASDESC")==null ? "/>" : ">" + rs.getString("ININDOTRASDESC") + "</OTRAS_CARGAS>") + "\n" +
                                "\t\t <POTENCIA"+(rs.getString("ININDOTRASPOT")==null ? "/>" : ">" + rs.getInt("ININDOTRASPOT") + "</POTENCIA>") + "\n" +
                                "\t </CARGAS_OFICINAS>\n";   
        } catch(Exception e){
            throw new Exception("Exception crearCargasOficinas: "+e.getMessage());
        }

    }

    public String getCargasOficinas() {
        return cargasOficinas;
    }        
    
}
