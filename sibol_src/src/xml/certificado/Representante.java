/*
 * Representante.java
 *
 * Created on 6 de octubre de 2006, 13:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package xml.certificado;

import funciones.BaseDatos;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Isabel
 */
public class Representante {
    
    private BaseDatos bd = null;
    private ResultSet rs = null;
    private String representante = null;
    
    /** Creates a new instance of Representante */
    public Representante(BaseDatos bd, ResultSet rs) throws Exception {
        this.bd = bd;    
        this.rs = rs;
        
        try{
            crearRepresentante();
        }
        catch(Exception e){
            throw new Exception("Exception constructor Representante: "+e.getMessage());
        }
        
    }
    
    private void crearRepresentante() throws Exception {
        representante = "";
        try {
            
            
        if (!((rs.getString("INTITREPRESENT")==null || rs.getString("INTITREPRESENT").equals("")) && (rs.getString("INTITREPRNIF")==null || rs.getString("INTITREPRNIF").equals(""))))
            representante = "\t <REPRESENTANTE>\n" +
                            "\t\t <NOMBRE"+(rs.getString("INTITREPRESENT") == null ? "/>" : ">" + rs.getString("INTITREPRESENT") + "</NOMBRE>") + "\n" +
                            "\t\t <NIF"+(rs.getString("INTITREPRNIF") == null ? "/>" : ">" + rs.getString("INTITREPRNIF").toUpperCase() + "</NIF>") + "\n" +
                            "\t </REPRESENTANTE>\n";

        
        } catch(Exception e){
            throw new Exception("Exception crearRepresentante: "+e.getMessage());
        }

    }

    public String getRepresentante() {
        return representante;
    }     
    
}
