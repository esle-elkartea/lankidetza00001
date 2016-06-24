/*
 * Tension.java
 *
 * Created on 16 de octubre de 2006, 15:20
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
public class Tension {
    private BaseDatos bd = null;
    private ResultSet rs = null;
    private comunBD consulta = null;
    private String tension = "";
    private int tipoTension = 0;
    
    /** Creates a new instance of Tension */
    public Tension(BaseDatos bd, ResultSet rs, int tipoTension) throws Exception {
        this.bd = bd;    
        this.rs = rs;
              
        try{
            consulta = new comunBD(bd);
            crearTension();
        }
        catch(Exception e){
            throw new Exception("Exception constructor Tension: "+e.getMessage());
        }        
    }

   private void crearTension() throws Exception {
        tension = "";
        String tension1 = "";
        String tension2 = "";
        
        try {
            switch(tipoTension){
                case 0:
                    tension1 = rs.getString("INTENSION1");
                    tension2 = rs.getString("INTENSION2");
                    break;
                case 1:
                    tension1 = rs.getString("INTENSION1_1");
                    tension2 = rs.getString("INTENSION2_1");
                    break;
                case 2:
                    tension1 = rs.getString("INTENSION1_2");
                    tension2 = rs.getString("INTENSION2_2");
                    break;
                case 3:
                    tension1 = rs.getString("INTENSION1_3");
                    tension2 = rs.getString("INTENSION2_3");
                    break;
                case 4:
                    tension1 = rs.getString("INTENSION1_4");
                    tension2 = rs.getString("INTENSION2_4");
                    break;
                case 5:
                    tension1 = rs.getString("INTENSION1_5");
                    tension2 = rs.getString("INTENSION2_5");
                    break;
                case 6:
                    tension1 = rs.getString("INTENSION1_6");
                    tension2 = rs.getString("INTENSION2_6");
                    break;
                default:
                    tension1 = rs.getString("INTENSION1");
                    tension2 = rs.getString("INTENSION2");                    
            }
        
            tension =   "\t <TENSION>\n"+
                        "\t\t <TENSION1"+(tension1 == null ? "/>" : ">" + tension1 + "</TENSION1>") + "\n";
            if (tension2!=null && !"".equals(tension2) && !"0".equals(tension2)){
                tension += "\t\t <TENSION2>"+ tension2 + "</TENSION2>" + "\n";
            }
            tension += "\t </TENSION>\n";
            
        } catch(Exception e){
            throw new Exception("Exception CrearTension: "+e.getMessage());
        }

    }

    public String getTension() {
        return tension;
    }   

}
