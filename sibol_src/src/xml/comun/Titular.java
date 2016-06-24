/*
 * Titular.java
 *
 * Created on 6 de octubre de 2006, 9:50
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
public class Titular {
    
    private BaseDatos bd = null;
    private ResultSet rs = null;
    private comunBD consulta = null;
    private Callejero callejero = null;
    
    private String titular = null;
   
    
    
    /** Creates a new instance of Titular */
    public Titular(BaseDatos bd, ResultSet rs) throws Exception {
        this.bd = bd;    
        this.rs = rs;
              
        try{
            consulta = new comunBD(bd);
            callejero = new Callejero(bd, rs, true);
            crearTitular();
        }
        catch(Exception e){
            throw new Exception("Exception constructor Titular: "+e.getMessage());
        }
        
    }
 
   
    
    private void crearTitular() throws Exception {
        titular = "";
        try {
            
            titular =   "\t <TITULAR>\n" +
                        "\t\t <NOMBRE"+(rs.getString("INTITNOMBRE") == null ? "/>" : ">" + rs.getString("INTITNOMBRE") + "</NOMBRE>") + "\n" +
                        "\t\t <NIF"+(rs.getString("INTITNIFCIF") == null ? "/>" : ">" + rs.getString("INTITNIFCIF").toUpperCase() + "</NIF>") + "\n" +
                        callejero.getProvincia() +
                        callejero.getMunicipio() +
                        callejero.getLocalidad() +
                        callejero.getCalle() +
                        "\t\t <PORTAL"+(rs.getString("INPORTALTIT") == null ? "/>" : ">" + rs.getString("INPORTALTIT") + "</PORTAL>") + "\n" +
                        "\t\t <CP"+(rs.getString("INTITCP") == null ? "/>" : ">" + rs.getString("INTITCP") + "</CP>") + "\n" +
                        "\t\t <TELEFONO> \n" + 
                        "\t\t\t <PREFIJO"+(rs.getString("INTITTFNO") == null ? "/>" : ">" + (rs.getString("INTITTFNO").length() > 3 ? rs.getString("INTITTFNO").substring(0,3) : "") + "</PREFIJO>") + "\n" +
                        "\t\t\t <TFNO"+(rs.getString("INTITTFNO") == null ? "/>" : ">" + (rs.getString("INTITTFNO").length() > 3 ? rs.getString("INTITTFNO").substring(3) : "") + "</TFNO>") + "\n" +
                        "\t\t</TELEFONO>\n" +
                        "\t </TITULAR>\n";     
        } catch(Exception e){
            throw new Exception("Exception CrearTitular: "+e.getMessage());
        }

    }

    public String getTitular() {
        return titular;
    }    
}
