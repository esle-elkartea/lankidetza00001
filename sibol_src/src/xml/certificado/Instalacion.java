/*
 * Instalacion.java
 *
 * Created on 21 de septiembre de 2006, 12:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package xml.certificado;

import funciones.BaseDatos;
import java.sql.ResultSet;
import java.sql.SQLException;
import xml.comun.Callejero;
import xml.comun.comunBD;

/**
 *
 * @author Isabel
 */
public class Instalacion {
    private BaseDatos bd;
    private ResultSet rs;
    private Callejero callejero = null;
    private String instalacion = "";
    
    /** Creates a new instance of Instalacion */
    public Instalacion(BaseDatos bd, ResultSet rs) throws Exception {
        this.bd = bd;    
        this.rs = rs;
                
        try{
            callejero = new Callejero (bd,rs, false);
            crearInstalacion();
        }
        catch(Exception e){
           throw new Exception("Exception constructor Instalación: "+e.getMessage());
        }
        
    }
    
    private void crearInstalacion() throws SQLException, Exception{
        instalacion = "";
        
        String telefono = obtenerTelefonoInstalador(rs.getInt("INITID")); /// rs.getString("INTELEFONO")
        
        instalacion+= "\t <INSTALACION>\n" +
                        callejero.getProvincia() +
                        callejero.getMunicipio() +
                        callejero.getLocalidad() +
                        callejero.getCalle() +
                        "\t\t <PORTAL"+(rs.getString("INPORTALCAR") == null ? "/>" : ">" + rs.getString("INPORTALCAR") + "</PORTAL>") + "\n" +
                        "\t\t <CP"+(rs.getString("INCP") == null ? "/>" : ">" + rs.getString("INCP") + "</CP>") + "\n" +
                        "\t\t <TELEFONO> \n" + 
                        "\t\t\t <PREFIJO"+(telefono == null ? "/>" : ">" + (telefono.length() > 3 ? telefono.substring(0,3) : "") + "</PREFIJO>") + "\n" +
                        "\t\t\t <TFNO"+(telefono == null ? "/>" : ">" + (telefono.length() > 3 ? telefono.substring(3) : "") + "</TFNO>") + "\n" +
                        "\t\t</TELEFONO>\n" +
                        ///"\t\t <EMAIL>"+(rs.getString("INEMAILINST") == null || "".equals(rs.getString("INEMAILINST"))?"":rs.getString("INEMAILINST"))+ "</EMAIL>" + "\n" +
                        "\t\t <EMAIL>"+obtenerEmailInstalador(rs.getInt("INITID"))+ "</EMAIL>" + "\n" +
                        "\t\t <BIS"+(rs.getString("INBISCAR") == null ? "/>" : ">" + rs.getString("INBISCAR") + "</BIS>") + "\n" +
                        "\t\t <ESCALERA"+(rs.getString("INESCALERACAR") == null ? "/>" : ">" + rs.getString("INESCALERACAR") + "</ESCALERA>") + "\n" +
                        "\t\t <PISO"+(rs.getString("INPISOCAR") == null ? "/>" : ">" + rs.getString("INPISOCAR") + "</PISO>") + "\n" +
                        "\t\t <PUERTA"+(rs.getString("INPUERTACAR") == null ? "/>" : ">" + rs.getString("INPUERTACAR") + "</PUERTA>") + "\n" +               
                        "\t </INSTALACION>\n";
    }

    public String getInstalacion() {
        return instalacion;
    }
    
    private String obtenerTelefonoInstalador(int idInstalador) {
        ResultSet rs = null;
        String sql  = "SELECT ITTELEFONO FROM INSTALADORES WHERE ITID=" + idInstalador;
        String telefono = "";
        try {
            rs = bd.ejecSelect(sql);
            if(rs.next()) {
                telefono = rs.getString("ITTELEFONO") == null ? "" : rs.getString("ITTELEFONO");
            }
        }
        catch(Exception e) {}
        finally {
            try {
                if(rs != null) rs.close();
            } catch(SQLException e) {}
        }
       
        return telefono;
    }
    
    private String obtenerEmailInstalador(int idInstalador) {
        ResultSet rs = null;
        String sql  = "SELECT ITEMAIL FROM INSTALADORES WHERE ITID=" + idInstalador;
        String email = "";
        try {
            rs = bd.ejecSelect(sql);
            if(rs.next()) {
                email = rs.getString("ITEMAIL") == null ? "" : rs.getString("ITEMAIL");
            }
        }
        catch(Exception e) {}
        finally {
            try {
                if(rs != null) rs.close();
            } catch(SQLException e) {}
        }
        
        return email;
    }
}
