package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

public class KABean extends BaseBean{
    private String referencia;
    private double tiempo1;
    private double tiempo2;
    private String unidades;

    public KABean() {}

    public KABean(String referencia, double tiempo1, double tiempo2, String unidades) {
        this.referencia = referencia;
        this.tiempo1 = tiempo1;
        this.tiempo2 = tiempo2;
        this.unidades = unidades;
    }

    public String getReferencia() {
        return referencia;
    }

    public double getTiempo1() {
        return tiempo1;
    }
    
    public double getTiempo2() {
        return tiempo2;
    }
    
    public String getUnidades() {
        return unidades;
    }
       
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setTiempo1(double tiempo1) {
        this.tiempo1 = tiempo1;
    }
    
    public void setTiempo2(double tiempo2) {
        this.tiempo2 = tiempo2;
    }
    
    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }
        
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(tiempo1);
        str.append("\n");
        str.append(tiempo2);
        str.append("\n");
        str.append(unidades);
        return str.toString();
    }
    
    public Object clone() {
        return new KABean(this.referencia, this.tiempo1, this.tiempo2, this.unidades);
    }
}
