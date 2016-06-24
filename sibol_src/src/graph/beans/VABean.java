package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

public class VABean extends BaseBean{
    private String referencia;
    private String numeroPolos;
    private String poderCorte;

    public VABean() {}

    public VABean(String referencia, String numeroPolos, String poderCorte) {
        this.referencia = referencia;
        this.numeroPolos = numeroPolos;
        this.poderCorte = poderCorte;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getNumeroPolos() {
        return numeroPolos;
    }
    
    public String getPoderCorte() {
        return poderCorte;
    }
       
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setNumeroPolos(String numeroPolos) {
        this.numeroPolos = numeroPolos;
    }
    
    public void setPoderCorte(String poderCorte) {
        this.poderCorte = poderCorte;
    }
        
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(numeroPolos);
        str.append("P\n");
        str.append(poderCorte);
        if(poderCorte != null && poderCorte.length() > 0) str.append("KA");

        return str.toString();
    }
    
    public Object clone() {
        return new VABean(this.referencia, this.numeroPolos, this.poderCorte);
    }
}
