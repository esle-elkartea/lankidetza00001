/*
 * DatosTecnicos.java
 *
 * Created on 26 de octubre de 2006, 11:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package xml.memoria;

import funciones.BaseDatos;
import funciones.Utilidades;
import java.sql.ResultSet;
import java.sql.SQLException;

import xml.comun.comunBD;

/**
 *
 * @author isabel
 */
public class DatosTecnicos {
    private BaseDatos bd = null;
    private ResultSet rs = null;
    private comunBD consulta = null;
    private String datosTecnicos = null;   

    
    /** Creates a new instance of DatosTecnicos */
    public DatosTecnicos(BaseDatos bd, ResultSet rs){
        this.bd = bd;    
        this.rs = rs;
        try{
            consulta = new comunBD(bd);
            crearDatosTecnicos();
        }
        catch(Exception e){
            System.out.println("Exception constructor DatosTecnicos: "+e.getMessage());
        } 
    }
    
    private void crearDatosTecnicos(){
        String situacionModulo = null;
        datosTecnicos= "";
        try {
            String tipo = rs.getString("INPCIDACOMETIDA") == null || "".equals(rs.getString("INPCIDACOMETIDA")) ? "O" : rs.getString("INPCIDACOMETIDA").toUpperCase();
                    
            datosTecnicos +=        "\t <DATOS_TECNICOS>\n" +
            // PUNTO CONEXION
                                    "\t\t <PUNTO_CONEXION>\n" +
                                    "\t\t\t<TIPO>" + tipo + "</TIPO>" + "\n";
            if (rs.getString("INPCIDACOMETIDA")!=null)
                if (!(tipo.equals("CTR") || tipo.equals("RTB")))
                    datosTecnicos +="\t\t\t<DESCRIPCION>OTROS</DESCRIPCION>" + "\n";
            datosTecnicos +=        "\t\t </PUNTO_CONEXION>\n";

            
            // TIPO ACOMETIDA
            datosTecnicos +=        "\t\t <TIPO_ACOMETIDA>\n" +
                                    "\t\t\t<TIPO>" + (rs.getString("INTLIDACOMETIDA")==null ? "O" : rs.getString("INTLIDACOMETIDA")) + "</TIPO>\n";
            if (rs.getString("INTLIDACOMETIDA")!=null){
                if (!(rs.getString("INTLIDACOMETIDA").toUpperCase().equals("A") || rs.getString("INTLIDACOMETIDA").toUpperCase().equals("S") || rs.getString("INTLIDACOMETIDA").toUpperCase().equals("I"))){
                    datosTecnicos +="\t\t\t<DESCRIPCION>OTROS</DESCRIPCION>" + "\n";
                }
            } else {
                datosTecnicos +="\t\t\t<DESCRIPCION>OTROS</DESCRIPCION>" + "\n";
            }
                    
            datosTecnicos +=        "\t\t </TIPO_ACOMETIDA>\n";

            
            // SECCION ACOMETIDA
            datosTecnicos +=        "\t\t <SECCION_ACOMETIDA" + (rs.getString("INSECCACOMETIDA")==null ? "/>" : ">" + rs.getString("INSECCACOMETIDA") + "</SECCION_ACOMETIDA>") + "\n";

            
            // MATERIAL ACOMETIDA
            if(rs.getString("INMAIDACOMETIDA")!=null && !"".equals(rs.getString("INMAIDACOMETIDA"))) {
                datosTecnicos +=        "\t\t <MATERIAL_ACOMETIDA>\n" +
                                        "\t\t\t <MATERIAL>" + rs.getString("INMAIDACOMETIDA").toUpperCase() + "</MATERIAL>" + "\n" +
                                        "\t\t </MATERIAL_ACOMETIDA>\n";
            }
            
            // TIPO CGP
            datosTecnicos +=        "\t\t <TIPO_CGP" + (rs.getString("INCGPTIPO")==null ? "/>" : ">" + rs.getString("INCGPTIPO") + "</TIPO_CGP>") + "\n";

            // IN_BASE
            datosTecnicos +=        "\t\t <IN_BASE" + (rs.getString("INCGPIBASE")==null ? "/>" : ">" + rs.getInt("INCGPIBASE") + "</IN_BASE>") + "\n";
            
            // IN_CARTUCHO
            datosTecnicos +=        "\t\t <IN_CARTUCHO" + (rs.getString("INCGPICARTUCHO")==null ? "/>" : ">" + rs.getInt("INCGPICARTUCHO") + "</IN_CARTUCHO>") + "\n";
            
            // TIPO LINEA
            datosTecnicos +=        "\t\t <TIPO_LINEA>\n" +
                                    "\t\t\t<TIPO" + (rs.getString("INTLGENERAL")==null ? "/>" : ">" + rs.getString("INTLGENERAL") + "</TIPO>") + "\n";
            if (rs.getString("INTLGENERAL")!=null)
                if (!(rs.getString("INTLGENERAL").toUpperCase().equals("A") || rs.getString("INTLGENERAL").toUpperCase().equals("S") || rs.getString("INTLGENERAL").toUpperCase().equals("I")))
                    datosTecnicos +="\t\t\t<DESCRIPCION>OTROS</DESCRIPCION>" + "\n";
            datosTecnicos +=        "\t\t </TIPO_LINEA>\n";            
            
            // SECCION LINEA
            datosTecnicos +=        "\t\t <SECCION_LINEA" + (rs.getString("INSECCGENERAL")==null ? "/>" : ">" + rs.getString("INSECCGENERAL") + "</SECCION_LINEA>") + "\n";
            
            // MATERIAL LINEA
            datosTecnicos +=        "\t\t <MATERIAL_LINEA>\n" +
                                    "\t\t\t <MATERIAL" + (rs.getString("INMAIDGENERAL")==null ? "/>" : ">" + rs.getString("INMAIDGENERAL").toUpperCase() + "</MATERIAL>") + "\n" +
                                    "\t\t </MATERIAL_LINEA>\n";

            // TIPO MODULO
            datosTecnicos +=        "\t\t <TIPO_MODULO" + (rs.getString("INMODULOMED")==null ? "/>" : ">" + rs.getString("INMODULOMED") + "</TIPO_MODULO>") + "\n";
            
            // SITUACION MODULO
            situacionModulo = consulta.getSituacionModulo(rs.getString("INSMID"));
            if(situacionModulo!=null && situacionModulo.length()>25) situacionModulo = situacionModulo.substring(0,25);
            datosTecnicos +=        "\t\t <SITUACION_MODULO" + (situacionModulo==null ? "/>" : ">" + situacionModulo + "</SITUACION_MODULO>") + "\n";

            // IN PROTECCION
            datosTecnicos +=        "\t\t <IN_PROTECCION" + (rs.getString("ININTGRAL")==null ? "/>" : ">" + rs.getInt("ININTGRAL") + "</IN_PROTECCION>") + "\n";
            
            // ICC PROTECCION
            datosTecnicos +=        "\t\t <ICC_PROTECCION" + (rs.getString("INICC")==null ? "/>" : ">" + rs.getInt("INICC") + "</ICC_PROTECCION>") + "\n";

            // DIF PROTECCION
            datosTecnicos +=        "\t\t <DIF_PROTECCION" + (rs.getString("ININTDIF")==null ? "/>" : ">" + rs.getInt("ININTDIF") + "</DIF_PROTECCION>") + "\n";

            // SENSIBILIDAD PROTECCION
            datosTecnicos +=        "\t\t <SENSIBILIDAD_PROTECCION" + (rs.getString("INSENSIBILIDAD")==null ? "/>" : ">" + rs.getString("INSENSIBILIDAD") + "</SENSIBILIDAD_PROTECCION>") + "\n";
            
            // PTA TIERRA
            datosTecnicos +=        "\t\t <PTA_TIERRA>\n" +
                                    "\t\t\t<TIPO" + (rs.getString("INTTID")==null ? "/>" : ">" + rs.getString("INTTID") + "</TIPO>") + "\n";
            if (rs.getString("INTTID")!=null)
                if (!(rs.getString("INTTID").toUpperCase().equals("MA") || rs.getString("INTTID").toUpperCase().equals("PI") || rs.getString("INTTID").toUpperCase().equals("PL")))
                    datosTecnicos +="\t\t\t<DESCRIPCION>OTROS</DESCRIPCION>" + "\n";
            datosTecnicos +=        "\t\t </PTA_TIERRA>\n";

            // ELECTRODOS
            datosTecnicos +=        "\t\t <ELECTRODOS" + (rs.getString("INELECTRODOS")==null ? "/>" : ">" + Utilidades.quitarDecimales(rs.getString("INELECTRODOS")) + "</ELECTRODOS>") + "\n";

            // LIN ENLACE
            datosTecnicos +=        "\t\t <LIN_ENLACE" + (rs.getString("INSECCLINENLACE")==null ? "/>" : ">" + rs.getInt("INSECCLINENLACE") + "</LIN_ENLACE>") + "\n";
            
            // LINEA PPAL
            datosTecnicos +=        "\t\t <LINEA_PPAL" + (rs.getString("INSECCLINPPAL")==null ? "/>" : ">" + rs.getInt("INSECCLINPPAL") + "</LINEA_PPAL>") + "\n";

            // PRESUPUESTO
            datosTecnicos +=        "\t\t <PRESUPUESTO" + (rs.getString("INPRESUPUESTO")==null ? "/>" : ">" + rs.getInt("INPRESUPUESTO") + "</PRESUPUESTO>") + "\n";

            // N INSTALACIONES
            datosTecnicos +=        "\t\t <N_INSTALACIONES" + (rs.getString("INNUMINSTALACIONES")==null ? "/>" : ">" + rs.getString("INNUMINSTALACIONES") + "</N_INSTALACIONES>") + "\n";
            
            datosTecnicos +=        "\t </DATOS_TECNICOS>\n";   
                
            
        } catch(Exception e){
            System.out.println("Exception crearDatosTecnicos: "+e.getMessage());
        }

    }

    public String getDatosTecnicos() {
        return datosTecnicos;
    }     
    
}
