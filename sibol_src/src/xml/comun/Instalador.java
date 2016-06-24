/*
 * Instalador.java
 *
 * Created on 6 de octubre de 2006, 14:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package xml.comun;

import funciones.BaseDatos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Isabel
 */
public class Instalador {
    
    private BaseDatos bd = null;
    private ResultSet rs = null;
    private comunBD consulta = null;
    
    private String instalador = null;

    
    /** Creates a new instance of Instalador */
    public Instalador(BaseDatos bd, ResultSet rs) throws Exception {
        this.bd = bd;    
        this.rs = rs;
        try{
            consulta = new comunBD(bd);
            crearInstalador();
        }
        catch(Exception e){
            throw new Exception("Exception constructor Instalador: "+e.getMessage());
        }
        
    }
    
    private void crearInstalador() throws Exception {
        Vector v = new Vector();
        instalador = "";
        
        try {
                     
            v = consulta.getInstalador(rs.getString("INITID"));
            instalador ="\t <INSTALADOR>\n" +
                        "\t\t <NIF"+(v == null ? "/>" : ">" + ((String)v.get(5)).toUpperCase() + "</NIF>") + "\n" +
                        "\t\t <CARNET"+(v == null ? "/>" : ">" + (String)v.get(0)+"/CCBT-"+(String)v.get(1) + "</CARNET>") + "\n" +
                        "\t\t <NOMBRE"+(v == null ? "/>" : ">" + (String)v.get(2) + "</NOMBRE>") + "\n" +
                        "\t\t <CATEGORIA"+(v == null ? "/>" : ">" + (String)v.get(3) + "</CATEGORIA>") + "\n" +
                        "\t\t <MODALIDAD"+(v == null ? "/>" : ">" + (String)v.get(4) + "</MODALIDAD>") + "\n" +
                        "\t </INSTALADOR>\n";
        
        } catch(Exception e){
            throw new Exception("Exception crearInstalador: "+e.getMessage());
        }

    }

    public String getInstalador() {
        return instalador;
    }    
    
    
}
