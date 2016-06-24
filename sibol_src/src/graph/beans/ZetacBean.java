package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

public class ZetacBean extends BaseBean{
    private String referencia;
    private String numeroPolos;
    private double intensidad;

    public ZetacBean() {}

    public ZetacBean(String referencia, String numeroPolos, double intensidad) {
        this.referencia = referencia;
        this.numeroPolos = numeroPolos;
        this.intensidad = intensidad;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getNumeroPolos() {
        return numeroPolos;
    }
    
    public double getIntensidad() {
        return intensidad;
    }
       
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setNumeroPolos(String numeroPolos) {
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
        return new ZetacBean(this.referencia, this.numeroPolos, this.intensidad);
    }
}
