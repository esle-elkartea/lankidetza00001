package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

public class M1Bean extends BaseBean{
    private String referencia;
    private String numeroPolos;
    private double calibre;
    private String curva;
    private double poderDeCorte;

    public M1Bean() {}

    public M1Bean(String referencia, String numeroPolos, double calibre, String curva, double poderDeCorte) {
        this.referencia = referencia;
        this.numeroPolos = numeroPolos;
        this.calibre = calibre;
        this.curva = curva;
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
    
    public String getCurva() {
        return curva;
    }
    
    public double getPoderDeCorte() {
        return poderDeCorte;
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

    public void setCurva(String curva) {
        this.curva = curva;
    }
    
    public void setPoderDeCorte(double poderDeCorte) {
        this.poderDeCorte = poderDeCorte;
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(numeroPolos);
        str.append("P");
        str.append("\n");
        str.append(DecimalFormat.getInstance().format(calibre));
        str.append("A");
        str.append("\n");
        str.append(curva);
        str.append("\n");
        str.append(DecimalFormat.getInstance().format(poderDeCorte));
        str.append("KA");

        return str.toString();
    }
    
    public Object clone() {
        return new M1Bean(this.referencia, this.numeroPolos, this.calibre, this.curva, this.poderDeCorte);
    }
}
