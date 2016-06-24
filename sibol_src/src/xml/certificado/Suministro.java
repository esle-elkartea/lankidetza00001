/*
 * Suministro.java
 *
 * Created on 17 de octubre de 2006, 8:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package xml.certificado;

import funciones.BaseDatos;
import funciones.Utilidades;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Isabel
 */
public class Suministro {
    private BaseDatos bd;
    private ResultSet rs;
    private String suministro = "";
    
    /** Creates a new instance of Suministro */
    public Suministro(BaseDatos bd, ResultSet rs) throws Exception {
        this.bd = bd;    
        this.rs = rs;
                
        try{
            crearSuministro();
        }
        catch(Exception e){
            throw new Exception("Exception constructor Suministro: "+e.getMessage());
        }
    }
    
        private void crearSuministro() throws SQLException, Exception{
        suministro = "";
        
        if (!(rs.getString("INDESCPOTCOMPLEMENT")==null && rs.getString("INPOTCOMPLEMENT")==null))
            suministro+= "\t <SUMINISTRO>\n" +
                        "\t\t <DESCRIPCION"+(rs.getString("INDESCPOTCOMPLEMENT") == null ? "/>" : ">" + rs.getString("INDESCPOTCOMPLEMENT") + "</DESCRIPCION>") + "\n" +
                        "\t\t <POTENCIA"+(rs.getString("INPOTCOMPLEMENT") == null ? "/>" : ">" + Utilidades.quitarDecimales(rs.getString("INPOTCOMPLEMENT")) + "</POTENCIA>") + "\n" +                
                        "\t </SUMINISTRO>\n";
    }

    public String getSuministro() {
        return suministro;
    }

}
