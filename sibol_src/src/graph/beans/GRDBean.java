package graph.beans;

import funciones.BaseDatos;
import funciones.Sesion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import main.Constantes;
import main.Mensaje;

public class GRDBean extends BaseBean{
    private String referencia;
    private String tipo;
    private double numeroElectrodos;
    private double lineaPrincipal;
    private double lineaEnlace;
    private double ohmios;

    public GRDBean() {}

    public GRDBean(String referencia, String tipo, double numeroElectrodos, double lineaPrincipal, double lineaEnlace, double ohmios) {
        this.referencia = referencia;
        this.tipo = tipo;
        this.numeroElectrodos = numeroElectrodos;
        this.lineaPrincipal = lineaPrincipal;
        this.lineaEnlace = lineaEnlace;
        this.ohmios = ohmios;
    }

    public String getReferencia() {
        return referencia;
    }
    
    public String getTipo() {
        return tipo;
    }

    public double getNumeroElectrodos() {
        return numeroElectrodos;
    }
    
    public double getLineaPrincipal() {
        return lineaPrincipal;
    }
    
    public double getLineaEnlace() {
        return lineaEnlace;
    }
    
    public double getOhmios() {
        return ohmios;
    }
       
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNumeroElectrodos(double numeroElectrodos) {
        this.numeroElectrodos = numeroElectrodos;
    }
    
    public void setLineaPrincipal(double lineaPrincipal) {
        this.lineaPrincipal = lineaPrincipal;
    }
    
    public void setLineaEnlace(double lineaEnlace) {
        this.lineaEnlace = lineaEnlace;
    }
    
    public void setOhmios(double ohmios) {
        this.ohmios = ohmios;
    }
        
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(recuperaDescripcionTipoTierra(tipo));
        str.append("\n");
        if(numeroElectrodos!=0){
            str.append(DecimalFormat.getInstance().format(numeroElectrodos));
            str.append("\n");
        }
        if(lineaPrincipal!=0){
            str.append(DecimalFormat.getInstance().format(lineaPrincipal));
            str.append("mm\u00B2-Cu\n");
        }
        if(lineaEnlace!=0){
            str.append(DecimalFormat.getInstance().format(lineaEnlace));
            str.append("mm\u00B2-Cu\n");
        }
        str.append(DecimalFormat.getInstance().format(ohmios));
        str.append("\u2126");
        
        return str.toString();
    }
    
    private String recuperaDescripcionTipoTierra(String codigo) {
        String resultado = codigo;
        ResultSet rs = null;
        BaseDatos bd = null;
        try {
            bd = Sesion.getInstance().getBaseDatos();
            rs = bd.ejecSelect("SELECT TTDESC FROM TIPOS_TIERRA WHERE TTID='"+codigo+"'");
            if(rs.next()) {
                resultado = rs.getString("TTDESC");
            }
        } catch(SQLException e){
            Mensaje.error("GRDBean.java. "+e.getMessage(), e, false);
        }
        return resultado;
    }
    
    public Object clone() {
        return new GRDBean(this.referencia, this.tipo, this.numeroElectrodos, this.lineaPrincipal, this.lineaEnlace, this.ohmios);
    }
}
