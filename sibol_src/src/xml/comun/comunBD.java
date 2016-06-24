/*
 * comunBD.java
 *
 * Created on 6 de octubre de 2006, 8:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package xml.comun;

import funciones.BaseDatos;
import funciones.UtilidadesSQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.lang.String;

/**
 *
 * @author Isabel
 */
public class comunBD {

    // Variables privadas
    private BaseDatos bd;
    
    /** Creates a new instance of comunBD */
    public comunBD(BaseDatos bd){
        this.bd = bd;    
    }

    
    public String getProvinciaNombreBD(String provinciaCod) throws SQLException, Exception{
        ResultSet rsTemp = null;
        String consulta = "SELECT * FROM PROVINCIAS WHERE PRID="+provinciaCod;
        String provincia = "";
        
        try {
            rsTemp = bd.ejecSelect(consulta);
            rsTemp.next();
            provincia = rsTemp.getString("PRNOM");
            //restriccion a 25 caracteres para que valide el xsd
            provincia = provincia.substring(0, (provincia.length() > 25 ? 25 : provincia.length()));
        } catch(Exception e) {
            throw new Exception("Exception en getProvinciaNombreBD: "+e.getMessage());
        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
        return provincia;
    }

    public String getMunicipioNombreBD(String provinciaCod, String municipioCod) throws SQLException, Exception{
        ResultSet rsTemp = null;
        String consulta = "SELECT * FROM MUNICIPIOS WHERE MUID="+municipioCod+" AND MUPRID="+provinciaCod;
        String municipio = "";
        try {
            
            rsTemp = bd.ejecSelect(consulta);
            if (rsTemp.next())
                municipio = rsTemp.getString("MUNOM");
             //restriccion a 40 caracteres para que valide el xsd
                municipio = municipio.substring(0, (municipio.length() > 40 ? 40 : municipio.length()));
        } catch(Exception e) {
            throw new Exception("Exception en getMunicipioNombreBD: "+e.getMessage());

        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
        return municipio;
    }

    
    public String getLocalidadNombreBD(String provinciaCod, String municipioCod, String localidadCod) throws SQLException, Exception{
        ResultSet rsTemp = null;
        String consulta = "SELECT * FROM LOCALIDADES WHERE LCID="+localidadCod+" AND LCMUID="+municipioCod+" AND LCPRID="+provinciaCod;
        String localidad = "";
        
        try {        
            rsTemp = bd.ejecSelect(consulta);
            if (rsTemp.next()) 
                localidad = rsTemp.getString("LCNOM");
             //restriccion a 40 caracteres para que valide el xsd
                localidad = localidad.substring(0, (localidad.length() > 40 ? 40 : localidad.length()));
        } catch(Exception e) {
            throw new Exception("Exception en getLocalidadNombreBD: "+e.getMessage());
        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
        return localidad;       
    }

    public String getCalleCodBD(String provinciaCod, String municipioCod, String localidadCod, String calleNombre) throws SQLException, Exception{
        ResultSet rsTemp = null;
        String descCalle, tipoCalle;
        String calle = "0";
        
        try {
            tipoCalle = getTipoCalle(calleNombre);
            descCalle = getDescCalle(calleNombre);

            String consulta = "SELECT * FROM CALLEJERO WHERE CLLCID="+localidadCod+" " +
                    " AND CLMUID="+municipioCod+" AND CLPRID="+provinciaCod+" AND " +
                    " UPPER(CLNOM)='"+descCalle.toUpperCase()+"' AND UPPER(CLTIPO)='"+tipoCalle.toUpperCase()+"'";
            rsTemp = bd.ejecSelect(consulta);
            if(rsTemp.next())
                calle = rsTemp.getString("CLID");
        } catch(Exception e) {
            throw new Exception("Exception en getCalleCodBD: "+e.getMessage());
        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
        
        return calle;
    }
    
    public String getCalleNombreBD(String provinciaCod, String municipioCod, String localidadCod, String calleNombre) throws SQLException, Exception{
        ResultSet rsTemp = null;
        String descCalle, tipoCalle;
        String calle = calleNombre;
        
        try {
            tipoCalle = getTipoCalle(calleNombre);
            descCalle = getDescCalle(calleNombre);

            String consulta = "SELECT * FROM CALLEJERO WHERE CLLCID="+localidadCod+" " +
                    " AND CLMUID="+municipioCod+" AND CLPRID="+provinciaCod+" AND " +
                    " UPPER(CLNOM)='"+descCalle.toUpperCase()+"' AND UPPER(CLTIPO)='"+tipoCalle.toUpperCase()+"'";

            rsTemp = bd.ejecSelect(consulta);
            if(rsTemp.next())
                calle = rsTemp.getString("CLNOM");
        } catch(Exception e) {
            throw new Exception("Exception en getCalleNombreBD: "+e.getMessage());
        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
                
        return calle;
    }

    public String getDescCalle(String calleNombre) {
        if(calleNombre == null || calleNombre.equals(""))
            return "";
        else{
            int Long = (calleNombre.length());

            String tipo="";
            String desc="";

            int indiceGuion = calleNombre.indexOf("-");

            if(indiceGuion==(-1))
            {
                    return calleNombre;
            }
            else
            {
                    tipo = calleNombre.substring(0,indiceGuion-1);
                    desc = calleNombre.substring(indiceGuion + 2,Long);
            }

            return desc;
        }
    }

    public String getTipoCalle(String calleNombre) {
        if(calleNombre == null || calleNombre.equals(""))
            return "";
        else{
            int Long = (calleNombre.length());

            String tipo="";
            String desc="";

            int indiceGuion = calleNombre.indexOf("-");

            if(indiceGuion==(-1))
            {
                    return "";
            }
            else
            {
                    tipo = calleNombre.substring(0,indiceGuion-1);
                    desc = calleNombre.substring(indiceGuion + 2,Long);
            }

            return tipo;
        }
    }    
    
    public Vector getEmpresaInstaladora (String codigo) throws Exception {
        Vector v = null;
        String consulta = "SELECT * FROM INSTALADORES WHERE ITID="+codigo;
        ResultSet rsTemp = null;
        try {
            rsTemp = bd.ejecSelect(consulta);
        
            v = new Vector();                        
            if (rsTemp.next()) {
                v.addElement(rsTemp.getString("ITNUMEMP1"));
                v.addElement(rsTemp.getString("ITNUMEMP2"));
                v.addElement(rsTemp.getString("ITNOMBREEMP"));
            }
        } catch(Exception e) {
            throw new Exception("Exception en getEmpresaInstaladora: "+e.getMessage());
        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
        return v;
    }
    
    public Vector getInstalador (String codigo) throws Exception {
        Vector v = null;
        String consulta = "SELECT * FROM INSTALADORES WHERE ITID="+codigo;
        ResultSet rsTemp = null;
        try {
            rsTemp = bd.ejecSelect(consulta);
      
            v = new Vector();                        
            if (rsTemp.next()) {
                v.addElement(rsTemp.getString("ITCARNET1")==null?"":rsTemp.getString("ITCARNET1"));
                v.addElement(rsTemp.getString("ITCARNET2")==null?"":rsTemp.getString("ITCARNET2"));
                v.addElement(rsTemp.getString("ITINSTALADOR")==null?"":rsTemp.getString("ITINSTALADOR"));
                v.addElement(rsTemp.getString("ITCATEGORIA")==null?"":rsTemp.getString("ITCATEGORIA"));
                v.addElement(rsTemp.getString("ITMODALIDAD")==null?"":rsTemp.getString("ITMODALIDAD"));
                v.addElement(rsTemp.getString("ITNIF")==null?"":rsTemp.getString("ITNIF"));
            }
        } catch(Exception e) {
            throw new Exception("Exception en getInstalador: "+e.getMessage());
        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
        return v;
    }
    
    public String getTipoUso (String codigo) throws Exception {
        String consulta = "SELECT * FROM TIPO_USO WHERE TUID='"+ codigo + "'";
        ResultSet rsTemp = null;
        String tipoUso = "";
        try {
            rsTemp = bd.ejecSelect(consulta);
                            
            if (rsTemp.next()) {
                tipoUso = rsTemp.getString("TUDESC");
            }
        } catch(Exception e) {
            throw new Exception("Exception en getTipoUso: "+e.getMessage());
        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
        return tipoUso;
    }
    
    public String getTipoUsoInstalacion (String codigo, String letra, String numero) throws Exception {
        String consulta = "SELECT * FROM USO_INSTALACION WHERE UIID="+ codigo + " AND UITICOD='"+letra+"' AND UITINUM="+numero;
        ResultSet rsTemp = null;
        String tipoUso = "";
        try {
            rsTemp = bd.ejecSelect(consulta);
                            
            if (rsTemp.next()) {
                tipoUso = rsTemp.getString("UIDESC");
            }
        } catch(Exception e) {
            throw new Exception("Exception en getTipoUsoInstalacion: "+e.getMessage());
        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
        return tipoUso;
    }
    
    public String getReglamento (String codigo) throws Exception {
        String consulta = "SELECT * FROM REGLAMENTOS WHERE RGID="+ codigo;
        ResultSet rsTemp = null;
        String reglamento = null;
        try {
            rsTemp = bd.ejecSelect(consulta);
            if (rsTemp.next()) {
                reglamento = rsTemp.getString("RGDESC");
            }
        } catch(Exception e) {
            throw new Exception("Exception en getReglamento: "+e.getMessage());
        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
        return reglamento;
    }   
    
    public Vector getCargasIndustriales (String codigo) throws Exception {
        Vector v = null;
        String consulta = "SELECT * FROM CARGAS_INDUSTRIALES WHERE CIINID="+codigo;
        ResultSet rsTemp = null;
        try {
            rsTemp = bd.ejecSelect(consulta);
            v = new Vector();                        
            while (rsTemp.next()) {
                String[] dato = new String[3];
                dato[0] = rsTemp.getString("CITGID")==null ? "":rsTemp.getString("CITGID").toUpperCase();
                dato[1] = rsTemp.getString("CIPOTENCIA")==null ? "": rsTemp.getString("CIPOTENCIA");
                dato[2] = rsTemp.getString("CIDESC")==null ? "": rsTemp.getString("CIDESC");
                v.addElement(dato);
            }
        } catch(Exception e) {
            throw new Exception("Exception en getCargasIndustriales: "+e.getMessage());
        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
        return v;
    }    

    public Vector getCuadroCircuitos(String codigo) throws Exception {
        Vector v = null;
        String consulta = "SELECT * FROM DATOS_CIRCUITOS WHERE DCINID="+codigo;
        ResultSet rsTemp = null;
        try {
            rsTemp = bd.ejecSelect(consulta);
      
            
            v = new Vector();                        
            while (rsTemp.next()) {
                String[] dato = new String[14];
                dato[0] = rsTemp.getString("DCTRID");
                dato[1] = rsTemp.getString("DCDESC")==null ? "": rsTemp.getString("DCDESC");
                dato[2] = rsTemp.getString("DCDESCDERIVACION")==null ? "": rsTemp.getString("DCDESCDERIVACION");
                dato[3] = rsTemp.getString("DCPOTENCIA")==null? "-":String.valueOf(rsTemp.getInt("DCPOTENCIA"));
                dato[4] = rsTemp.getString("DCTENSION")==null? "-":String.valueOf(rsTemp.getInt("DCTENSION"));
                dato[5] = rsTemp.getString("DCINTENSIDAD")==null? "-":rsTemp.getString("DCINTENSIDAD");
                dato[6] = rsTemp.getString("DCNUMCOND")==null? "-":rsTemp.getString("DCNUMCOND");
                dato[7] = rsTemp.getString("DCAISLAMIENTO")==null? "-":rsTemp.getString("DCAISLAMIENTO");
                dato[8] = rsTemp.getString("DCTNID")==null? "-":rsTemp.getString("DCTNID");
                dato[9] = rsTemp.getString("DCINTMAX")==null? "-":String.valueOf((int)Math.ceil(Double.parseDouble(rsTemp.getString("DCINTMAX"))));
                dato[10] = rsTemp.getString("DCCCPIA")==null? "-":String.valueOf(rsTemp.getInt("DCCCPIA"));
                dato[11] = rsTemp.getString("DCLONGITUD")==null? "-":String.valueOf(rsTemp.getInt("DCLONGITUD"));
                dato[12] = rsTemp.getString("DCCAIDA")==null? "-":rsTemp.getString("DCCAIDA");
                dato[13] = rsTemp.getString("DCTIPODERIVACION")==null ? "": rsTemp.getString("DCTIPODERIVACION");
                v.addElement(dato);
            }
        } catch(Exception e) {
            throw new Exception("Exception en getCuadroCircuitos: "+e.getMessage());
        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
        return v;
    }    
    
    public String getSituacionModulo (String codigo) throws Exception {
        String consulta = "SELECT * FROM SITUAC_MODULO WHERE SMID='"+ codigo+"'";
        ResultSet rsTemp = null;
        String situacionModulo = null;
        try {
            rsTemp = bd.ejecSelect(consulta);
            if (rsTemp.next()) {
                situacionModulo = rsTemp.getString("SMDESC");
            }
        } catch(Exception e) {
            throw new Exception("Exception en getSituacionModulo: "+e.getMessage());
        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
        return situacionModulo;
    }      
    
    public String getDescTipoInsCircuito(String idInstalacion, String codigo) throws Exception {
        ResultSet rsTemp = null;
        String descTipoInsCircuito = "-";
        String consulta = "SELECT * FROM (" +
                                        "SELECT * FROM TIPOS_INST_CIRCUITOS WHERE TNDESC<>'' " +
                                        "UNION " +
                                        "SELECT 'D' AS TNID,INTIPOCIRCD AS TNDESC FROM INSTALACIONES WHERE INID=" + idInstalacion + " " +
                                        "UNION " +
                                        "SELECT 'E' AS TNID,INTIPOCIRCE AS TNDESC FROM INSTALACIONES WHERE INID=" + idInstalacion + " " +
                                        "UNION " +
                                        "SELECT 'F' AS TNID,INTIPOCIRCF AS TNDESC FROM INSTALACIONES WHERE INID=" + idInstalacion + " " +
                                    ") AS TABLA " +
                                    "WHERE " +
                                        "TABLA.TNDESC<>'' AND " +
                                        "TABLA.TNID=" + UtilidadesSQL.tratarParametroString(codigo);
        try {
            rsTemp = bd.ejecSelect(consulta);
            if (rsTemp.next()) {
                descTipoInsCircuito = rsTemp.getString("TNDESC");
            }
        } catch(Exception e) {
            throw new Exception("Exception en getDescTipoInsCircuito: "+e.getMessage());
        } finally {
            if(rsTemp!=null) try { rsTemp.close(); } catch(Exception e) {}
        }
        
        return descTipoInsCircuito;
    }
}
