package graph.beans;

import funciones.BaseDatos;
import funciones.Sesion;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.Constantes;
import main.Mensaje;

public class AcomBean extends BaseBean{
    private String referencia;
    private String puntoConexion;
    private String tipoLinea;
    private double seccion;
    private String material;

    public AcomBean() {}

    public AcomBean(String referencia, double seccion, String material, String puntoConexion, String tipoLinea){
        this.setReferencia(referencia);
        this.setSeccion(seccion);
        this.setMaterial(material);
        this.setPuntoConexion(puntoConexion);
        this.setTipoLinea(tipoLinea);
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public double getSeccion() {
        return seccion;
    }

    public void setSeccion(double seccion) {
        this.seccion = seccion;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
    
    public String getPuntoConexion() {
        return puntoConexion;
    }

    public void setPuntoConexion(String puntoConexion) {
        this.puntoConexion = puntoConexion;
    }
    
     public String getTipoLinea() {
        return tipoLinea;
    }

    public void setTipoLinea(String tipoLinea) {
        this.tipoLinea = tipoLinea;
    }
    
    public String toString(){
        StringBuffer str = new StringBuffer();
        str.append(getReferencia());
        str.append("\n");
        if (null!=getPuntoConexion() && !getPuntoConexion().equals("")) {
            str.append(recuperaDescripcionPuntoConexion(getPuntoConexion()));
            str.append("\n");
        }
        if (null!=getTipoLinea() && !getTipoLinea().equals("")) {
            str.append(recuperaDescripcionTipoLinea(getTipoLinea()));
            str.append("\n");
        }
        if (0!=getSeccion()) {
            str.append(getSeccion()+" mm\u00B2");
            str.append("\n");
        }
        if (null!=getMaterial() && !getMaterial().equals("")) {
            str.append(recuperaDescripcionMaterial(getMaterial()));
        }
        return str.toString();
    }
    
    private String recuperaDescripcionPuntoConexion(String codigo) {
        String resultado = codigo;
        ResultSet rs = null;
        BaseDatos bd = null;
        try {
            bd = Sesion.getInstance().getBaseDatos();
            rs = bd.ejecSelect("SELECT PCDESC FROM PTOS_CONEXION WHERE PCID='"+codigo+"'");
            if(rs.next()) {
                resultado = rs.getString("PCDESC");
            }
        } catch(SQLException e){
            Mensaje.error("AcomBean.java. "+e.getMessage(), e, false);
        }
        return resultado;
    }
    
    private String recuperaDescripcionTipoLinea(String codigo) {
        String resultado = codigo;
        ResultSet rs = null;
        BaseDatos bd = null;
        try {
            bd = Sesion.getInstance().getBaseDatos();
            rs = bd.ejecSelect("SELECT TLDESC FROM TIPOS_LINEA WHERE TLID='"+codigo+"'");
            if(rs.next()) {
                resultado = rs.getString("TLDESC");
            }
        } catch(SQLException e){
            Mensaje.error("AcomBean.java. "+e.getMessage(), e, false);
        }
        return resultado;
    }
    
    private String recuperaDescripcionMaterial(String codigo) {
        String resultado = codigo;
        ResultSet rs = null;
        BaseDatos bd = null;
        try {
            bd = Sesion.getInstance().getBaseDatos();
            rs = bd.ejecSelect("SELECT MADESC FROM MATERIALES WHERE MAID='"+codigo+"'");
            if(rs.next()) {
                resultado = rs.getString("MADESC");
            }
        } catch(SQLException e){
            Mensaje.error("AcomBean.java. "+e.getMessage(), e, false);
        }
        return resultado;
    }
    
    public Object clone() {
        return new AcomBean(this.referencia, this.seccion, this.material, this.puntoConexion, this.tipoLinea);
    }
}
