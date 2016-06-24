package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

public class SchucoBean extends BaseBean{
    private String referencia;
    private double numeroPolos;
    private double intensidad;

    public SchucoBean() {}

    public SchucoBean(String referencia) {
        this.referencia = referencia;
        this.numeroPolos = 2.0;
        this.intensidad = 16.0;
    }
    
    public SchucoBean(String referencia, double numeroPolos, double intensidad) {
        this.referencia = referencia;
        this.numeroPolos = numeroPolos;
        this.intensidad = intensidad;
    }
    
    public String getReferencia() {
        return referencia;
    }

    public double getNumeroPolos() {
        return numeroPolos;
    }
    
    public double getIntensidad() {
        return intensidad;
    }
       
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    public void setNumeroPolos(double numeroPolos) {
        this.numeroPolos = numeroPolos;
    }
      
    public void setIntensidad(double intensidad) {
        this.intensidad = intensidad;
    }
    
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(numeroPolos);
        str.append("P\n");
        str.append(DecimalFormat.getInstance().format(intensidad));
        str.append("A");

        return str.toString();
    }
    
    public Object clone() {
        return new SchucoBean(this.referencia, this.numeroPolos, this.intensidad);
    }
}
