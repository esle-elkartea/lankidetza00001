package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

public class D1Bean extends BaseBean{
    private String referencia;
    private double tension;
    private double numeroPolos;
    private double calibre;
    private double sensibilidad;

    public D1Bean() {}

    public D1Bean(String referencia, double numeroPolos, double calibre, double sensibilidad) {
        this.referencia = referencia;
        this.numeroPolos = numeroPolos;
        this.calibre = calibre;
        this.sensibilidad = sensibilidad;
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
    
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(DecimalFormat.getInstance().format(numeroPolos));
        str.append("P\n");
        str.append(DecimalFormat.getInstance().format(calibre));
        str.append("A\n");
        str.append(DecimalFormat.getInstance().format(sensibilidad));
        str.append("mA");

        return str.toString();
    }
    
    public Object clone() {
        return new D1Bean(this.referencia, this.numeroPolos, this.calibre, this.sensibilidad);
    }
}
