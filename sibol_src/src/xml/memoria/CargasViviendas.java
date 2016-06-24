/*
 * CargasViviendas.java
 *
 * Created on 25 de octubre de 2006, 12:30
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
public class CargasViviendas {
    private BaseDatos bd = null;
    private ResultSet rs = null;
    private String cargasViviendas = null;
    
    /** Creates a new instance of CargasViviendas */
    public CargasViviendas(BaseDatos bd, ResultSet rs) throws Exception {
        this.bd = bd;
        this.rs = rs;
        try{
            crearCargasViviendas();
        } catch(Exception e){
            throw new Exception("Exception constructor CargasViviendas: "+e.getMessage());
        }
    }
    
    private void crearCargasViviendas() throws Exception {
        cargasViviendas= "";
        try {
            cargasViviendas ="\t <CARGAS_VIVIENDAS>\n" +
                    this.getElectrificacion() +
                    "\t\t <COEF_SIMULTANEIDAD"+(null==null ? "/>" : ">" + "" + "</COEF_SIMULTANEIDAD>") + "\n" + // Aclarar el dato a sacar aquí.
                    "\t\t <ASCENSORES"+(rs.getString("INPOTASC")==null ? "/>" : ">" + rs.getInt("INPOTASC") + "</ASCENSORES>") + "\n" +
                    "\t\t <ALUMBRADO_ESCALERA"+(rs.getString("INPOTALUMB")==null ? "/>" : ">" + rs.getInt("INPOTALUMB") + "</ALUMBRADO_ESCALERA>") + "\n" +
                    "\t\t <OTROS_SERVICIOS"+(rs.getString("INPOTOTROS")==null ? "/>" : ">" + rs.getInt("INPOTOTROS") + "</OTROS_SERVICIOS>") + "\n" +
                    "\t\t <SUPERFICIE"+(rs.getString("INOFICSUO")==null ? "/>" : ">" + rs.getInt("INOFICSUO") + "</SUPERFICIE>") + "\n" +
                    "\t\t <POTENCIA"+(rs.getString("INOFICPOT")==null ? "/>" : ">" + rs.getInt("INOFICPOT") + "</POTENCIA>") + "\n" +
                    "\t </CARGAS_VIVIENDAS>\n";
        } catch(Exception e){
            throw new Exception("Exception crearCargasViviendas: "+e.getMessage());
        }
        
    }
    
    private String getElectrificacion() throws Exception {
        String electrificacion = null , electrificacion2 = null;
        boolean electrificacionBasica = false;
        boolean electrificacionElevada = false;
        
        try {
            electrificacion =   "\t\t<ELECTRIFICACION>\n";
            if(rs.getString("INGEID1")!=null) {
                if(rs.getString("INGEID1").equals("B")) {
                    electrificacionBasica = true;
                    electrificacion +=  "\t\t\t<ELECTRIFICACION_BASICO>\n" +
                            "\t\t\t\t<TIPO"+(rs.getString("INGEID1")==null ? "/>" : ">" + rs.getString("INGEID1") + "</TIPO>") + "\n" +
                            "\t\t\t\t<NVIV"+(rs.getString("INNVIV1")==null ? "/>" : ">" + rs.getString("INNVIV1") + "</NVIV>") + "\n" +
                            "\t\t\t\t<SUPERFICIE"+(rs.getString("INSUPUNIT1")==null ? "/>" : ">" + rs.getInt("INSUPUNIT1") + "</SUPERFICIE>") + "\n" +
                            "\t\t\t\t<MAX_POTENCIA"+(rs.getString("INDEMANDA1")==null ? "/>" : ">" + rs.getInt("INDEMANDA1") + "</MAX_POTENCIA>") + "\n" +
                            "\t\t\t</ELECTRIFICACION_BASICO>\n";
                }
                else if(rs.getString("INGEID1").equals("E")) {
                    electrificacionElevada = true;
                    electrificacion +=  "\t\t\t<ELECTRIFICACION_ELEVADO>\n" +
                            "\t\t\t\t<TIPO"+(rs.getString("INGEID1")==null ? "/>" : ">" + rs.getString("INGEID1") + "</TIPO>") + "\n" +
                            "\t\t\t\t<NVIV"+(rs.getString("INNVIV1")==null ? "/>" : ">" + rs.getString("INNVIV1") + "</NVIV>") + "\n" +
                            "\t\t\t\t<SUPERFICIE"+(rs.getString("INSUPUNIT1")==null ? "/>" : ">" + rs.getInt("INSUPUNIT1") + "</SUPERFICIE>") + "\n" +
                            "\t\t\t\t<MAX_POTENCIA"+(rs.getString("INDEMANDA1")==null ? "/>" : ">" + rs.getInt("INDEMANDA1") + "</MAX_POTENCIA>") + "\n" +
                            "\t\t\t</ELECTRIFICACION_ELEVADO>\n";
                }
            }
            electrificacion =  electrificacion + "\t\t</ELECTRIFICACION>\n";
            
            if(rs.getString("INGEID2")!=null) {
                if(rs.getString("INGEID2").equals("B") && !electrificacionBasica) {
                    electrificacion2 =  "\t\t\t<ELECTRIFICACION_BASICO>\n" +
                            "\t\t\t\t<TIPO"+(rs.getString("INGEID2")==null ? "/>" : ">" + rs.getString("INGEID2") + "</TIPO>") + "\n" +
                            "\t\t\t\t<NVIV"+(rs.getString("INNVIV2")==null ? "/>" : ">" + rs.getString("INNVIV2") + "</NVIV>") + "\n" +
                            "\t\t\t\t<SUPERFICIE"+(rs.getString("INSUPUNIT2")==null ? "/>" : ">" + rs.getInt("INSUPUNIT2") + "</SUPERFICIE>") + "\n" +
                            "\t\t\t\t<MAX_POTENCIA"+(rs.getString("INDEMANDA2")==null ? "/>" : ">" + rs.getInt("INDEMANDA2") + "</MAX_POTENCIA>") + "\n" +
                            "\t\t\t</ELECTRIFICACION_BASICO>\n";
                }
                else if(rs.getString("INGEID2").equals("E") && !electrificacionElevada) {
                    electrificacion2 =  "\t\t\t<ELECTRIFICACION_ELEVADO>\n" +
                            "\t\t\t\t<TIPO"+(rs.getString("INGEID2")==null ? "/>" : ">" + rs.getString("INGEID2") + "</TIPO>") + "\n" +
                            "\t\t\t\t<NVIV"+(rs.getString("INNVIV2")==null ? "/>" : ">" + rs.getString("INNVIV2") + "</NVIV>") + "\n" +
                            "\t\t\t\t<SUPERFICIE"+(rs.getString("INSUPUNIT2")==null ? "/>" : ">" + rs.getInt("INSUPUNIT2") + "</SUPERFICIE>") + "\n" +
                            "\t\t\t\t<MAX_POTENCIA"+(rs.getString("INDEMANDA2")==null ? "/>" : ">" + rs.getInt("INDEMANDA2") + "</MAX_POTENCIA>") + "\n" +
                            "\t\t\t</ELECTRIFICACION_ELEVADO>\n";
                }
            }
            
            if(electrificacion2 != null) {
                electrificacion = electrificacion + "\t\t<ELECTRIFICACION>\n";
                electrificacion += electrificacion2;
                electrificacion += "\t\t</ELECTRIFICACION>\n";
            }
        } catch(Exception e){
            throw new Exception("Exception getElectrificacion: "+e.getMessage());
        }
        
        return electrificacion;
    }
    
    public String getCargasViviendas() {
        return cargasViviendas;
    }
    
}
