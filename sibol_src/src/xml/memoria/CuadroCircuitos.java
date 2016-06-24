/*
 * CuadroCircuitos.java
 *
 * Created on 25 de octubre de 2006, 15:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package xml.memoria;

import funciones.BaseDatos;
import funciones.Utilidades;
import java.sql.ResultSet;
import java.util.*;

import main.Constantes;
import xml.comun.comunBD;

/**
 *
 * @author isabel
 */
public class CuadroCircuitos {
        
    private BaseDatos bd = null;
    private ResultSet rs = null;
    private comunBD consulta = null;
    private String cuadroCircuitos = null;
    
    /** Creates a new instance of CuadroCircuitos */
    public CuadroCircuitos(BaseDatos bd, ResultSet rs) throws Exception {
        this.bd = bd;    
        this.rs = rs;
        try{
            consulta = new comunBD(bd);
            crearCuadroCircuitos();
        }
        catch(Exception e){
            throw new Exception("Exception constructor CuadroCircuitos: "+e.getMessage());
        }  
    }
    
    private void crearCuadroCircuitos() throws Exception {
        Vector v = new Vector();
        cuadroCircuitos= "";
        try {
            v = consulta.getCuadroCircuitos(rs.getString("INID"));
            cuadroCircuitos ="\t <CUADRO_CIRCUITOS>\n";
            for(int tipo = Constantes.ACOMETIDA; tipo<=Constantes.FUERZA; tipo++) {
                for(int i = 0; i < v.size(); i++) {    
                    String[] dato = (String[])v.get(i);
                    if(Integer.parseInt(dato[0])==tipo){
                        cuadroCircuitos +=getXMLElemento(dato);
                    }
                }
            }
            cuadroCircuitos +="\t </CUADRO_CIRCUITOS>\n";
        } catch(Exception e){
            throw new Exception("Exception crearCuadroCircuitos: "+e.getMessage());
        }

    }

    public String getCuadroCircuitos() {
        return cuadroCircuitos;
    }
    
    private String getXMLElemento(String[] dato) throws Exception {
        String resultado = "";
        int tipo = Integer.parseInt(dato[0]);
        switch(tipo) {
            case Constantes.ACOMETIDA:
                resultado +=  "\t\t<ACOMETIDA>" + "\n";
                break;
            case Constantes. LINEA_GENERAL:
                resultado +=  "\t\t<LINEA_GENERAL>" + "\n";
                break;
            case Constantes.INSTALACIONES_INDUSTRIALES_AGRARIAS_SERVICIOS:
                resultado +=  "\t\t<INS_INDUSTRIALES>" + "\n";
                break;
            case Constantes.DERIVACION_INDIVIDUAL:
                resultado +=  "\t\t<DERIVACION_INDIVIDUAL>" + "\n";
                break;
            case Constantes.VIVIENDAS_TIPO:
                resultado +=  "\t\t<VIV_TIPO>" + "\n";
                break;
            case Constantes.ALUMBRADO:
                resultado +=  "\t\t<ALUMBRADO>" + "\n";
                break;
            case Constantes.FUERZA:
                resultado +=  "\t\t<FUERZA>" + "\n";
                break;
            default:
        }

        if(dato[5].length()>5){
            dato[5]=dato[5].substring(0,5);
        }
        if(dato[5].length()==5){
            if(dato[5].lastIndexOf(".")==-1 || dato[5].lastIndexOf(".")==dato[5].length()){
                dato[5]=dato[5].substring(0,4);
            }
        }

        String potencia = Utilidades.quitarDecimales(dato[3]);
        potencia = potencia.equals("") || potencia.equals("-") ? "0" : potencia;
        
        resultado += "\t\t\t<TIPO>" + dato[0] + "</TIPO>" + "\n" +
                (tipo != Constantes.LINEA_GENERAL && tipo != Constantes.ALUMBRADO && tipo != Constantes.FUERZA ? "\t\t\t<DESCRIPCION>" + (tipo == Constantes.DERIVACION_INDIVIDUAL ? dato[13] : dato[1]) + "</DESCRIPCION>\n":"") + 
                ((tipo == Constantes.DERIVACION_INDIVIDUAL || tipo == Constantes.FUERZA) && "Otra".equalsIgnoreCase(dato[13])?"\t\t\t<OTRA_DESCRIPCION>" + dato[2] + "</OTRA_DESCRIPCION>" + "\n":"") +
                "\t\t\t<DATOS_CIRCUITOS>" + "\n" +
                "\t\t\t<DAT_CIRCUITOS>" + "\n" +
                "\t\t\t\t<POTENCIA>" + potencia + "</POTENCIA>" + "\n" +
                "\t\t\t\t<TEN_CAL"+(dato[4].equals("-") ? "/>" : ">" + dato[4] + "</TEN_CAL>") + "\n" +
                "\t\t\t\t<INTENSIDAD"+(dato[5].equals("-") ? "/>" : ">" + dato[5] + "</INTENSIDAD>") + "\n" +
                "\t\t\t\t<N_CONDUC"+(dato[6].equals("-") ? "/>" : ">" + dato[6] + "</N_CONDUC>") + "\n" +
                "\t\t\t\t<AISLAMIENTO"+(dato[7].equals("-") ? "/>" : ">" + dato[7] + "</AISLAMIENTO>") + "\n" +
                "\t\t\t\t<TIPO_INS_CIRCTO>" + "\n" +
                "\t\t\t\t\t<TIPO"+(dato[8].equals("-") ? "/>" : ">" + dato[8] + "</TIPO>") + "\n" +
                "\t\t\t\t\t<DESCRIPCION>" + consulta.getDescTipoInsCircuito( rs.getString("INID"), dato[8]) + "</DESCRIPCION>" + "\n" +
                "\t\t\t\t</TIPO_INS_CIRCTO>" + "\n" +
                "\t\t\t\t<INT_MAX"+(dato[9].equals("-") ? "/>" : ">" + dato[9] + "</INT_MAX>") + "\n" +
                "\t\t\t\t<CCPIA"+(dato[10].equals("-") ? "/>" : ">" + dato[10] + "</CCPIA>") + "\n" +
                "\t\t\t\t<LONGITUD"+(dato[11].equals("-") ? "/>" : ">" + dato[11] + "</LONGITUD>") + "\n" +
                "\t\t\t\t<CAIDA"+(dato[12].equals("-") ? "/>" : ">" + dato[12] + "</CAIDA>") + "\n" +
                "\t\t\t</DAT_CIRCUITOS>" + "\n" +
                "\t\t\t</DATOS_CIRCUITOS>" + "\n";

        switch(tipo) {
            case Constantes.ACOMETIDA:
                resultado +=  "\t\t</ACOMETIDA>" + "\n";
                break;
            case Constantes.LINEA_GENERAL:
                resultado +=  "\t\t</LINEA_GENERAL>" + "\n";
                break;
            case Constantes.INSTALACIONES_INDUSTRIALES_AGRARIAS_SERVICIOS:
                resultado +=  "\t\t</INS_INDUSTRIALES>" + "\n";
                break;
            case Constantes.DERIVACION_INDIVIDUAL:
                resultado +=  "\t\t</DERIVACION_INDIVIDUAL>" + "\n";
                break;
            case Constantes.VIVIENDAS_TIPO:
                resultado +=  "\t\t</VIV_TIPO>" + "\n";
                break;
            case Constantes.ALUMBRADO:
                resultado +=  "\t\t</ALUMBRADO>" + "\n";
                break;
            case Constantes.FUERZA:
                resultado +=  "\t\t</FUERZA>" + "\n";
                break;
            default:
        }
        return resultado;
    }
    
}
