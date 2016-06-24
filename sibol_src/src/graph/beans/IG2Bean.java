package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

public class IG2Bean extends BaseBean{
    private String referencia;
    private double numeroPolos;
    private double calibre;
    private double sensibilidad;
    private String regulacion;
    private double poderDeCorte;

    public IG2Bean() {}

    public IG2Bean(String referencia, double numeroPolos, double calibre, double sensibilidad, String regulacion, double poderDeCorte) {
        this.referencia = referencia;
        this.numeroPolos = numeroPolos;
        this.calibre = calibre;
        this.sensibilidad = sensibilidad;
        this.regulacion = regulacion;
        this.poderDeCorte = poderDeCorte;
    }

    public String getReferencia() {
        return referencia;
    }

    public double getNumeroPolos() {
        return numeroPolos;
    }
    
    public double getCalibre() {
        return calibre;
    }
    
    public double getSensibilidad() {
        return sensibilidad;
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

    public void setNumeroPolos(double numeroPolos) {
        this.numeroPolos = numeroPolos;
    }
    
    public void setCalibre(double calibre) {
        this.calibre = calibre;
    }
    
    public void setSensibilidad(double sensibilidad) {
        this.sensibilidad = sensibilidad;
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
        str.append(DecimalFormat.getInstance().format(numeroPolos));
        str.append("P\n");
        str.append(DecimalFormat.getInstance().format(calibre));
        str.append("A\n");
        str.append(DecimalFormat.getInstance().format(sensibilidad));
        str.append("mA\n");
        str.append(regulacion);
        str.append("%\n");
        str.append(DecimalFormat.getInstance().format(poderDeCorte));
        str.append("KA");

        return str.toString();
    }
    
    public Object clone() {
        return new IG2Bean(this.referencia, this.numeroPolos, this.calibre, this.sensibilidad, this.regulacion, this.poderDeCorte);
    }
}
