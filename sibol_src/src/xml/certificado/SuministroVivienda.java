/*
 * SuministroVivienda.java
 *
 * Created on 19 de octubre de 2006, 14:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package xml.certificado;

import funciones.BaseDatos;
import java.sql.ResultSet;
import java.sql.SQLException;

import xml.comun.Tension;
import xml.comun.comunBD;

/**
 *
 * @author isabel
 */
public class SuministroVivienda {
    private BaseDatos bd;
    private ResultSet rs;
    private String suministroVivienda = "";
    private comunBD consulta = null;
    private Tension tension = null;
    
    /** Creates a new instance of SuministroVivienda */
    public SuministroVivienda(BaseDatos bd, ResultSet rs) throws Exception {
        this.bd = bd;    
        this.rs = rs;
                
        try{
            consulta = new comunBD(bd);
            crearSuministroVivienda();
        }
        catch(Exception e){
            throw new Exception("Exception constructor SuministroVivienda: "+e.getMessage());
        }
    }
    
    private void crearSuministroVivienda() throws SQLException, Exception{
        suministroVivienda = "";
        tension = null;
        String tipoUso = "";
        boolean hayUso = false;
        
        if (!(rs.getString("INICDESC1")==null && rs.getString("INICDESC2A")==null)) {
            suministroVivienda += "\t <SUMINISTRO_VIV>\n";
        
            // LINEA 1
            tipoUso = rs.getString("INTUID1");
            if(tipoUso!=null && !"".equals(tipoUso)){
                hayUso = true;
                suministroVivienda += "\t\t <DESCRIPCION"+(rs.getString("INICDESC1") == null ? "/>" : ">" + rs.getString("INICDESC1") + "</DESCRIPCION>") + "\n" +
                            "\t\t <POTENCIA"+(rs.getString("INICPOT1") == null ? "/>" : ">" + rs.getDouble("INICPOT1") + "</POTENCIA>") + "\n" +
                            "\t\t <TIPO>L</TIPO>\n";
                tension = new Tension(bd,rs,1);
                suministroVivienda += "\t" + tension.getTension() + "\t" +
                            "\t\t <TIP_USO"+(tipoUso.equals("") ? "/>" : ">" + tipoUso + "</TIP_USO>") + "\n";
            }

            // LINEA 2A - 2B
            tipoUso = rs.getString("INTUID2");
            if(tipoUso!=null && !"".equals(tipoUso)){
                hayUso = true;
                for(char c = 'A'; c <= 'B'; c++) {
                    suministroVivienda += "\t\t <DESCRIPCION"+((rs.getString("INICDESC2" + c) == null) ? "/>" : ">" + rs.getString("INICDESC2" + c) + "</DESCRIPCION>") + "\n" +
                                "\t\t <POTENCIA"+(rs.getString("INICPOT2") == null ? "/>" : ">" + rs.getDouble("INICPOT2") + "</POTENCIA>") + "\n" +
                                "\t\t <TIPO>M</TIPO>\n";
                    tension = new Tension(bd,rs,2);
                    suministroVivienda += "\t" + tension.getTension() + "\t" +
                                "\t\t <TIP_USO"+(tipoUso.equals("") ? "/>" : ">" + tipoUso + "</TIP_USO>") + "\n";
                }
            }
            
            // LINEA 3A - 3B
            tipoUso = rs.getString("INTUID3");
            if(tipoUso!=null && !"".equals(tipoUso)){
                hayUso = true;
                for(char c = 'A'; c <= 'B'; c++) {
                    suministroVivienda += "\t\t <DESCRIPCION"+((rs.getString("INICDESC3" + c) == null) ? "/>" : ">" + rs.getString("INICDESC3" + c) + "</DESCRIPCION>") + "\n" +
                                "\t\t <POTENCIA"+(rs.getString("INICPOT3") == null ? "/>" : ">" + rs.getDouble("INICPOT3") + "</POTENCIA>") + "\n" +
                                "\t\t <TIPO>M</TIPO>\n";
                    tension = new Tension(bd,rs,3);
                    suministroVivienda += "\t" + tension.getTension() + "\t" +
                                "\t\t <TIP_USO"+(tipoUso.equals("") ? "/>" : ">" + tipoUso + "</TIP_USO>") + "\n";
                }
            }
            
            // LINEA 4A - 4M
            tipoUso = rs.getString("INTUID4");
            if(tipoUso!=null && !"".equals(tipoUso)){
                hayUso = true;
                for(char c = 'A'; c <= 'M'; c++) {
                    suministroVivienda += "\t\t <DESCRIPCION"+(rs.getString("INICDESC4" + c) == null ? "/>" : ">" + rs.getString("INICDESC4" + c) + "</DESCRIPCION>") + "\n" +
                                "\t\t <POTENCIA"+(rs.getString("INICPOT4") == null ? "/>" : ">" + rs.getDouble("INICPOT4") + "</POTENCIA>") + "\n" +
                                "\t\t <TIPO>C</TIPO>\n";
                    tension = new Tension(bd,rs,4);
                    suministroVivienda += "\t" + tension.getTension() + "\t" +
                                "\t\t <TIP_USO"+(tipoUso.equals("") ? "/>" : ">" + tipoUso + "</TIP_USO>") + "\n";
                }
            }
            
            // LINEA 5A - 5M
            tipoUso = rs.getString("INTUID5");
            if(tipoUso!=null && !"".equals(tipoUso)){
                hayUso = true;
                for(char c = 'A'; c <= 'M'; c++) {
                    suministroVivienda += "\t\t <DESCRIPCION"+(rs.getString("INICDESC5" + c) == null ? "/>" : ">" + rs.getString("INICDESC5" + c) + "</DESCRIPCION>") + "\n" +
                                "\t\t <POTENCIA"+(rs.getString("INICPOT5") == null ? "/>" : ">" + rs.getDouble("INICPOT5") + "</POTENCIA>") + "\n" +
                                "\t\t <TIPO>C</TIPO>\n";
                    tension = new Tension(bd,rs,5);
                    suministroVivienda += "\t" + tension.getTension() + "\t" +
                                "\t\t <TIP_USO"+(tipoUso.equals("") ? "/>" : ">" + tipoUso + "</TIP_USO>") + "\n";
                }
            }
            
            // LINEA 6A - 6M
            tipoUso = rs.getString("INTUID6");
            if(tipoUso!=null && !"".equals(tipoUso)){
                hayUso = true;
                for(char c = 'A'; c <= 'M'; c++) {
                    suministroVivienda += "\t\t <DESCRIPCION"+(rs.getString("INICDESC6" + c) == null ? "/>" : ">" + rs.getString("INICDESC6" + c) +  "</DESCRIPCION>") + "\n" +
                                "\t\t <POTENCIA"+(rs.getString("INICPOT6") == null ? "/>" : ">" + rs.getDouble("INICPOT6") + "</POTENCIA>") + "\n" +
                                "\t\t <TIPO>C</TIPO>\n";
                    tension = new Tension(bd,rs,5);
                    suministroVivienda += "\t" + tension.getTension() + "\t" +
                                "\t\t <TIP_USO"+(tipoUso.equals("") ? "/>" : ">" + tipoUso + "</TIP_USO>") + "\n";
                }
            }
            
            suministroVivienda += "\t </SUMINISTRO_VIV>\n";
            
            if(!hayUso) suministroVivienda ="";
        }
    }

    public String getSuministroVivienda() {
        return suministroVivienda;
    }    
}
