package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

public class CTBean extends BaseBean{
    private String referencia;
    private double tension;
    private double numeroPolos;
    private double calibre;

    public CTBean() {}

    public CTBean(String referencia, double tension, double numeroPolos, double calibre) {
        this.referencia = referencia;
        this.tension = tension;
        this.numeroPolos = numeroPolos;
        this.calibre = calibre;
    }
    
    public String getReferencia() {
        return referencia;
    }

    public double getTension() {
        return tension;
    }

    public double getNumeroPolos() {
        return numeroPolos;
    }
    
    public double getCalibre() {
        return calibre;
    }
    
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
       
    public void setTension(double tension) {
        this.tension = tension;
    }

    public void setNumeroPolos(double numeroPolos) {
        this.numeroPolos = numeroPolos;
    }
    
    public void setCalibre(double calibre) {
        this.calibre = calibre;
    }
        
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(DecimalFormat.getInstance().format(tension));
        str.append("V\n");
        str.append(DecimalFormat.getInstance().format(numeroPolos));
        str.append("P\n");
        str.append(DecimalFormat.getInstance().format(calibre));
        str.append("A");

        return str.toString();
    }
    
    public Object clone() {
        return new CTBean(this.referencia, this.tension, this.numeroPolos, this.calibre);
    }
}
