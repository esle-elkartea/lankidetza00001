/*
 * TipoInstalacion.java
 *
 * Created on 16 de octubre de 2006, 15:07
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
public class TipoInstalacion {

    private BaseDatos bd = null;
    private ResultSet rs = null;
    private comunBD consulta = null;
    private String tipoInstalacion = null;

    /** Creates a new instance of TipoInstalacion */
    public TipoInstalacion(BaseDatos bd, ResultSet rs) throws Exception {
      this.bd = bd;    
      this.rs = rs;
        try{
            consulta = new comunBD(bd);
            crearTipoInstalacion();
        }
        catch(Exception e){
            throw new Exception("Exception constructor Titular: "+e.getMessage());
        }
    }
    
    private void crearTipoInstalacion() throws Exception {
        tipoInstalacion = "";
        
        try {           
            String desc = "-";
            if(rs.getString("INUSOVARIOS")!=null && !"".equals(rs.getString("INUSOVARIOS"))){
                desc = rs.getString("INUSOVARIOS");
            } else {
                if(rs.getString("INUIID")!=null){
                    desc = consulta.getTipoUsoInstalacion(rs.getString("INUIID"),rs.getString("INTICOD"),rs.getString("INTINUM"));
                    if(desc==null || "".equals(desc)) desc = "-";
                }
            }
            if(desc.length()>150){
                desc = desc.substring(0,150);
            }

            String tiCod = rs.getString("INTICOD");
            String uso = rs.getString("INUIID");
            // para las instalaciones tipo A, D, H, N, T, Z no hay que enviar el uso
            if("A".equals(tiCod) || "D".equals(tiCod) || "H".equals(tiCod) || "N".equals(tiCod) || "T".equals(tiCod) || "Z".equals(tiCod)) {
                uso = null;
            }
                        
            tipoInstalacion = "\t <TIPO_INST>\n" +
                "\t\t <LETRA"+(tiCod == null ? "/>" : ">" + tiCod + "</LETRA>") + "\n" +                 
                "\t\t <NUMERO"+(rs.getString("INTINUM") == null ? "/>" : ">" + rs.getString("INTINUM") + "</NUMERO>") + "\n" +
                "\t\t <USO"+(uso == null ? "/>" : ">" + uso + "</USO>") + "\n" +                  
                "\t\t <DESC_USO>"+desc + "</DESC_USO>\n" +                
                "\t </TIPO_INST>\n";
            
            
        } catch(Exception e){
            throw new Exception("Exception CrearTipoInstalacion: "+e.getMessage());
        }

    }

    public String getTipoInstalacion() {
        return tipoInstalacion;
    }     
    
}
