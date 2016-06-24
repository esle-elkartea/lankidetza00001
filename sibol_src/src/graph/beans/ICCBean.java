package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

public class ICCBean extends BaseBean{
    private String referencia;
    private String numeroPolos;
    private double calibre;

    public ICCBean() {}

    public ICCBean(String referencia, String numeroPolos, double calibre) {
        this.referencia = referencia;
        this.numeroPolos = numeroPolos;
        this.calibre = calibre;
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
    
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setNumeroPolos(String numeroPolos) {
        this.numeroPolos = numeroPolos;
    }
    
    public void setCalibre(double calibre) {
        this.calibre = calibre;
    }
        
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(numeroPolos);
        str.append("P\n");
        str.append(DecimalFormat.getInstance().format(calibre));
        str.append("A");

        return str.toString();
    }
    
    public Object clone() {
        return new ICCBean(this.referencia, this.numeroPolos, this.calibre);
    }
}
