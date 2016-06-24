/*
 * GenerarFlujoXML.java
 *
 * Created on 20 de septiembre de 2006, 17:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package xml;

import funciones.BaseDatos;
import funciones.Config;
import funciones.HTMLEncode;
import funciones.Sesion;
import funciones.Utilidades;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.Mensaje;

import xml.comun.Titular;
import xml.comun.Instalador;
import xml.comun.TipoInstalacion;
import xml.comun.Tension;
import xml.comun.comunBD;

import xml.certificado.Representante;
import xml.certificado.EmpresaInstaladora;
import xml.certificado.Instalacion;
import xml.certificado.Suministro;
import xml.certificado.SuministroVivienda;

import xml.memoria.MemoriaPor;
import xml.memoria.CargasIndustriales;
import xml.memoria.CargasViviendas;
import xml.memoria.CargasOficinas;
import xml.memoria.CuadroCircuitos;
import xml.memoria.DatosTecnicos;

/**
 *
 * @author enrique
 */
public class GenerarFlujoXML {
    
    private int idInstalacion;
    private String strXML = null;
    private BaseDatos bd;
    private comunBD consulta = null;
    private String esquemaUnifilar = null;
    

    public GenerarFlujoXML(int idIns, String esquema) {
        try{
            idInstalacion = idIns;
            esquemaUnifilar = esquema;
            bd = Sesion.getInstance().getBaseDatos();
            //bd = new BaseDatos();
            consulta = new comunBD(bd);
            //bd.conectar();
            generarFlujoXML();
            //bd.close();
        }
        catch(SQLException e){
            Mensaje.error("Error en la interaccción con la BD para XML: "+e.getMessage(),e);
        }
        catch(Exception e){
            Mensaje.error("Error en la generación del fichero XML: "+e.getMessage(),e);
        }
    }

    public String getStrXML() {
        return strXML;
    }

    private void generarFlujoXML() throws SQLException, Exception {
        String consultaInst = null;
        String certificado = "";
        String memoria = "";
        ResultSet rs   = null;
        String reglamento = null;
        strXML         = "";
        
        // CONSULTAR LA INSTALACIÓN POR ID
        consultaInst   = "SELECT * FROM INSTALACIONES WHERE INID="+idInstalacion;
        rs = bd.ejecSelect(consultaInst);
        if (rs.next()) {
        
            // Objetos Comunes
            Titular titular = new Titular(bd, rs);

            // Objetos Certificado
            Representante representante = new Representante(bd, rs);
            EmpresaInstaladora empresaInstaladora = new EmpresaInstaladora(bd, rs);
            Instalador instalador = new Instalador(bd, rs);
            Instalacion instalacion = new Instalacion(bd,rs);
            TipoInstalacion tipoInstalacion = new TipoInstalacion(bd,rs);
            Tension tension = new Tension(bd, rs,0); // 0 = Tipo de Tension Básica de Certificado y Memoria
            Suministro suministro = new Suministro(bd,rs);
            SuministroVivienda sumVivienda = new SuministroVivienda(bd,rs);

            //Construcción del Certificado
            certificado += titular.getTitular();
            certificado += representante.getRepresentante();
            certificado += "\t <SUMINISTRADORA"+(rs.getString("INESPROV") == null ? "/>" : ">" + rs.getString("INESPROV")+"-"+ rs.getString("INESNUM")+ "</SUMINISTRADORA>") + "\n";
            String cups1 = rs.getString("INCUPS1");
            String cups2 = rs.getString("INCUPS2");
            String cups3 = rs.getString("INCUPS3");
            String cups = (cups1 == null || "".equals(cups1)) || (cups2 == null || "".equals(cups2)) || (cups3 == null || "".equals(cups3))  ? "" :  cups1 + cups2 + cups3;
            certificado += "\t <CUPS>" + cups + "</CUPS>" + "\n";                       
            certificado += empresaInstaladora.getEmpresaInstaladora();
            certificado += instalador.getInstalador();
            certificado += instalacion.getInstalacion();
            certificado += tipoInstalacion.getTipoInstalacion();
            certificado += "\t <SUPERFICIE"+(rs.getString("INSUPERFICIE") == null ? "/>" : ">" + rs.getInt("INSUPERFICIE") + "</SUPERFICIE>") + "\n";
            certificado += tension.getTension();
            certificado += "\t <POTENCIA"+(rs.getString("INPOTNORMAL") == null ? "/>" : ">" + Utilidades.quitarDecimales(rs.getString("INPOTNORMAL")) + "</POTENCIA>") + "\n";
            certificado += suministro.getSuministro();
            certificado += sumVivienda.getSuministroVivienda();
            certificado += "\t <RESISTENCIA"+(rs.getString("INRESTIERRA") == null ? "/>" : ">" + rs.getString("INRESTIERRA") + "</RESISTENCIA>") + "\n";
            if (rs.getString("INRGID")!=null)
                reglamento = consulta.getReglamento(rs.getString("INRGID"));
            certificado += "\t <REGLAMENTO"+(reglamento == null ? "/>" : ">" + reglamento + "</REGLAMENTO>") + "\n";
            
            // Objetos Memoria
            MemoriaPor memoriaPor = new MemoriaPor(bd,rs);
            CargasIndustriales cargasIndustriales = new CargasIndustriales(bd,rs);
            CargasViviendas cargasViviendas = new CargasViviendas(bd,rs);
            CargasOficinas cargasOficinas = new CargasOficinas(bd,rs);
            CuadroCircuitos cuadroCircuitos = new CuadroCircuitos(bd,rs);
            DatosTecnicos datosTecnicos = new DatosTecnicos(bd,rs);
            
            //Construcción de la Memoria
            memoria += titular.getTitular();
            memoria += tension.getTension();
            memoria += "\t <POTENCIA"+(rs.getString("INPOTNORMAL") == null ? "/>" : ">" + Utilidades.quitarDecimales(rs.getString("INPOTNORMAL")) + "</POTENCIA>") + "\n";
            memoria += memoriaPor.getMemoriaPor();
            memoria += tipoInstalacion.getTipoInstalacion();
            memoria += "\t <SUPERFICIE"+(rs.getString("INSUPERFICIE") == null ? "/>" : ">" + rs.getInt("INSUPERFICIE") + "</SUPERFICIE>") + "\n";
            memoria += datosTecnicos.getDatosTecnicos();
            memoria += cargasIndustriales.getCargasIndustriales();
            memoria += cargasViviendas.getCargasViviendas();
            memoria += cargasOficinas.getCargasOficinas();
            memoria += instalador.getInstalador();
            memoria += cuadroCircuitos.getCuadroCircuitos();
            ///memoria += "\t<MEMORIA_DESCRIPTIVA"+(rs.getString("INMEMO") == null ? "/>" : "><![CDATA[" + HTMLEncode.encode(rs.getString("INMEMO")) + "]]></MEMORIA_DESCRIPTIVA>") + "\n"; // Memoria Descriptiva
            memoria += "\t<MEMORIA_DESCRIPTIVA"+(rs.getString("INMEMO") == null ? "/>" : "><![CDATA[" + rs.getString("INMEMO") + "]]></MEMORIA_DESCRIPTIVA>") + "\n"; // Memoria Descriptiva
            memoria += "\t <ESQUEMA_UNIFILAR"+(esquemaUnifilar == null ? "/>" : ">" + esquemaUnifilar + "</ESQUEMA_UNIFILAR>") + "\n";            
            
            
            strXML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
            strXML = strXML + "<EXPEDIENTES xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"expedientes.xsd\">\n<EXPEDIENTE>\n";
            
            if (certificado.equals(""))
                strXML += "<CERTIFICADO/>\n";
            else
                strXML += "<CERTIFICADO>\n" + certificado + "</CERTIFICADO>\n";
            
            if (memoria.equals(""))
                strXML += "<MEMORIA/>\n";
            else
                strXML += "<MEMORIA>\n" + memoria + "</MEMORIA>\n";
            
            strXML += "<PROVEEDOR>" + Config.getInstance().getParametro("PROVEEDOR_XML") + "</PROVEEDOR>\n";
            
            strXML += "</EXPEDIENTE>\n</EXPEDIENTES>\n";
            
            //System.out.println(strXML);
        } else {
            throw new Exception("No existe la instalación.");
        }
    }
    
    public static void main(String a[]){
        //Se le pasa en el constructor el id de la instalación deseada.
        GenerarFlujoXML g = new GenerarFlujoXML(1007, null);  
    }
}
