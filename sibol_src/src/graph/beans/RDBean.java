package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

public class RDBean extends BaseBean{
    private String referencia;
    private double sensibilidad;
    private String reguladorTiempo1;
    private String reguladorTiempo2;

    public RDBean() {}

    public RDBean(String referencia, double sensibilidad, String reguladorTiempo1, String reguladorTiempo2) {
        this.referencia = referencia;
        this.sensibilidad = sensibilidad;
        this.reguladorTiempo1 = reguladorTiempo1;
        this.reguladorTiempo2 = reguladorTiempo2;
    }
    
    public String getReferencia() {
        return referencia;
    }

    public double getSensibilidad() {
        return sensibilidad;
    }
    
    public String getReguladorTiempo1() {
        return reguladorTiempo1;
    }
    
    public String getReguladorTiempo2() {
        return reguladorTiempo2;
    }
    
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setSensibilidad(double sensibilidad) {
        this.sensibilidad = sensibilidad;
    }
    
    public void setReguladorTiempo1(String reguladorTiempo1) {
        this.reguladorTiempo1 = reguladorTiempo1;
    }
    
    public void setReguladorTiempo2(String reguladorTiempo2) {
        this.reguladorTiempo2 = reguladorTiempo2;
    }   

    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(DecimalFormat.getInstance().format(sensibilidad));
        str.append("mA\n");
        str.append(reguladorTiempo1);
        str.append("\n");
        str.append(reguladorTiempo2);

        return str.toString();
    }
    
    public Object clone() {
        return new RDBean(this.referencia, this.sensibilidad, this.reguladorTiempo1, this.reguladorTiempo2);
    }
}
