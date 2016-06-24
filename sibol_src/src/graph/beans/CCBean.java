package graph.beans;

import funciones.BaseDatos;
import funciones.Sesion;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.Constantes;
import main.Mensaje;

public class CCBean extends BaseBean{
    private String referencia;
    private String situacion;

    public CCBean() {}

    public CCBean(String referencia, String situacion) {
        this.referencia = referencia;
        this.situacion = situacion;
    }

    public String getReferencia() {
        return referencia;
    }
    
    public String getSituacion() {
        return situacion;
    }

    public void setReferencia(String referencia) {
        this.referencia=referencia;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }
    
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(recuperaDescripcionSituacion(situacion));

        return str.toString();
    }

    private String recuperaDescripcionSituacion(String codigo) {
        String resultado = codigo;
        ResultSet rs = null;
        BaseDatos bd = null;
        try {
            bd = Sesion.getInstance().getBaseDatos();
            rs = bd.ejecSelect("SELECT SMDESC FROM SITUAC_MODULO WHERE SMID='"+codigo+"'");
            if(rs.next()) {
                resultado = rs.getString("SMDESC");
            }
        } catch(SQLException e){
            Mensaje.error("CCBean.java. "+e.getMessage(), e, false);
        }
        return resultado;
    }
    
    public Object clone() {
        return new CCBean(this.referencia, this.situacion);
    }
}
