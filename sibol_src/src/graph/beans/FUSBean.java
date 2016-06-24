package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

public class FUSBean extends BaseBean{
    private String referencia;
    private double talla;
    private double calibre;

    public FUSBean() {}

    public FUSBean(String referencia, double talla, double calibre) {
        this.referencia = referencia;
        this.talla = talla;
        this.calibre = calibre;
    }

    public String getReferencia() {
        return referencia;
    }
    
    public double getTalla() {
        return talla;
    }
    
    public double getCalibre() {
        return calibre;
    }

    public void setReferencia(String referencia) {
        this.referencia=referencia;
    }
        
    public void setCalibre(double calibre) {
        this.calibre=calibre;
    }

    public void setTalla(double talla) {
        this.talla = talla;
    }
    
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(DecimalFormat.getInstance().format(talla));
        str.append("\n");
        str.append(DecimalFormat.getInstance().format(calibre));
        str.append("A");

        return str.toString();
    }
    
    public Object clone() {
        return new FUSBean(this.referencia, this.talla, this.calibre);
    }
}
