/*
 * CargasIndustriales.java
 *
 * Created on 23 de octubre de 2006, 15:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package xml.memoria;

import funciones.BaseDatos;
import funciones.Utilidades;
import java.sql.ResultSet;
import java.util.*;
import xml.comun.comunBD;

/**
 *
 * @author isabel
 */
public class CargasIndustriales {
    private BaseDatos bd = null;
    private ResultSet rs = null;
    private String cargasIndustriales = null;   
    private comunBD consulta = null;
    
    /** Creates a new instance of CargasIndustriales */
    public CargasIndustriales(BaseDatos bd, ResultSet rs) throws Exception {
        this.bd = bd;    
        this.rs = rs;
        try{
            consulta = new comunBD(bd);
            crearCargasIndustriales();
        }
        catch(Exception e){
            throw new Exception("Exception constructor CargasIndustriales: "+e.getMessage());
        }   
    }
    
    private void crearCargasIndustriales() throws Exception {
        Vector v = new Vector();
        cargasIndustriales = "";
        try {
            v = consulta.getCargasIndustriales(rs.getString("INID"));
            for(int i = 0; i < v.size(); i++) {    
                String[] dato = new String[3];
                dato = (String[]) v.get(i);
                cargasIndustriales += "\t <CARGAS_INDUSTRIALES>\n" +
                                    "\t\t <TIPO"+(dato[0].equals("") ? "/>" : ">" + dato[0] + "</TIPO>") + "\n" +
                                    "\t\t <DESCRIPCION"+(dato[2].equals("") ? "/>" : ">" + dato[2] + "</DESCRIPCION>") + "\n" +
                                    "\t\t <POTENCIA"+(dato[1].equals("") ? "/>" : ">" + Utilidades.quitarDecimales(dato[1]) + "</POTENCIA>") + "\n" +
                                    "\t </CARGAS_INDUSTRIALES>\n";   
            }
        } catch(Exception e){
            throw new Exception("Exception crearCargasIndustriales: "+e.getMessage());
        }
    }

    public String getCargasIndustriales() {
        return cargasIndustriales;
    }      
    
}
