package graph.beans;

import forms.FrmDatosTecnicosPnl;
import funciones.Sesion;
import java.text.DecimalFormat;
import main.Constantes;

public class IGBean extends BaseBean{
    private String referencia;
    private String numeroPolos;
    private double calibre;
    private String regulacion;
    private double poderDeCorte;

    public IGBean() {}

    public IGBean(String referencia, String numeroPolos, double calibre, String regulacion, double poderDeCorte) {
        this.referencia = referencia;
        this.numeroPolos = numeroPolos;
        this.calibre = calibre;
        this.regulacion = regulacion;
        this.poderDeCorte = poderDeCorte;
    }
    
    public String getReferencia() {
        return referencia;
    }

    public String getNumeroPolos() {
        return numeroPolos;
    }
    
    public double getCalibre() {
        return calibre;
    }

    public String getRegulacion() {
        return regulacion;
    }

    public double getPoderDeCorte() {
        return poderDeCorte;
    }
    
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setNumeroPolos(String numeroPolos) {
        this.numeroPolos=numeroPolos;
    }

    public void setCalibre(double calibre) {
        this.calibre = calibre;
    }
    
    public void setRegulacion(String regulacion) {
        this.regulacion = regulacion;
    }

    public void setPoderDeCorte(double poderDeCorte) {
        this.poderDeCorte = poderDeCorte;
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(numeroPolos);
        str.append("P\n");
        str.append(DecimalFormat.getInstance().format(calibre));
        str.append("A");
        str.append("\n");
        str.append(regulacion);
        str.append("%");
        str.append("\n");
        str.append(DecimalFormat.getInstance().format(poderDeCorte));
        str.append("KA");

        return str.toString();
    }
    
    public Object clone() {
        return new IGBean(this.referencia, this.numeroPolos, this.calibre, this.regulacion, this.poderDeCorte);
    }
}
