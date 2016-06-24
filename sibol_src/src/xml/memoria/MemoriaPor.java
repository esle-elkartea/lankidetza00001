/*
 * MemoriaPor.java
 *
 * Created on 23 de octubre de 2006, 15:07
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
public class MemoriaPor {
    private BaseDatos bd = null;
    private ResultSet rs = null;
    private String memoriaPor = null;
    
    /** Creates a new instance of MemoriaPor */
    public MemoriaPor(BaseDatos bd, ResultSet rs) throws Exception {
        this.bd = bd;    
        this.rs = rs;
        try{
            crearMemoriaPor();
        }
        catch(Exception e){
            throw new Exception("Exception constructor MemoriaPor: "+e.getMessage());
        }   
    }
    
    private void crearMemoriaPor() throws Exception {
        memoriaPor = "";
        try {
            
            memoriaPor ="\t <MEMORIA_POR>\n" +
                        "\t\t <MOTIVO"+(rs.getString("INMMID") == null ? "/>" : ">" + rs.getString("INMMID") + "</MOTIVO>") + "\n" +
//                        "\t\t <OTRA_DESC>"+memoriaPorOtraDesc+"</OTRA_DESC>\n" + // minOccurs=0
                         "\t </MEMORIA_POR>\n";   
        } catch(Exception e){
            throw new Exception("Exception crearMemoriaPor: "+e.getMessage());
        }

    }

    public String getMemoriaPor() {
        return memoriaPor;
    }        
    
}
