/*
 * EmpresaInstaladora.java
 *
 * Created on 6 de octubre de 2006, 13:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package xml.certificado;

import funciones.BaseDatos;
import xml.comun.comunBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Isabel
 */
public class EmpresaInstaladora {
    
    private BaseDatos bd = null;
    private ResultSet rs = null;
    private comunBD consulta = null;
    
    private String empresaInstaladora = null;
  
    
    /** Creates a new instance of EmpresaInstaladora */
    public EmpresaInstaladora(BaseDatos bd, ResultSet rs) throws Exception {
        this.bd = bd;    
        this.rs = rs;
                
        try{
            consulta = new comunBD(bd);
            crearEmpresaInstaladora();
        }
        catch(Exception e){
            throw new Exception("Exception constructor EmpresaInstaladora: "+e.getMessage());
        }
        
    }
    
    private void crearEmpresaInstaladora() throws Exception {
        Vector v = new Vector();
        empresaInstaladora = "";
        
        try {
            v = consulta.getEmpresaInstaladora(rs.getString("INITID")); // En la tabla de INSTALADORES están los datos de la empresa instaladora.
            empresaInstaladora =    "\t <EMP_INSTALADORA>\n" +
                                    "\t\t <CODIGO"+(v == null ? "/>" : ">" + (String)v.get(0)+"/EIBT-"+(String)v.get(1) + "</CODIGO>") + "\n" +
                                    "\t\t <NOMBRE"+(v == null ? "/>" : ">" + (String)v.get(2) + "</NOMBRE>") + "\n" +
                                    "\t </EMP_INSTALADORA>\n";            
        } catch(Exception e){
            throw new Exception("Exception crearEmpresaInstaladora: "+e.getMessage());
        }

    }

    public String getEmpresaInstaladora() {
        return empresaInstaladora;
    }      
    
}
