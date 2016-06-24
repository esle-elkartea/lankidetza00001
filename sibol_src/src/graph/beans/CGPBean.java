/*
 * CGPBean.java
 *
 * Created on 1 de junio de 2006, 12:36
 *
 */
package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

/**
 * @author sanjose
 */
public class CGPBean extends BaseBean{
    private String referencia;
    private String tension;
    private double intensidadBase;
    private double intensidadCartucho;

    public CGPBean() {}

    public CGPBean(String referencia, String tension, double intensidadBase, double intensidadCartucho) {
        this.referencia = referencia;
        this.tension = tension;
        this.intensidadBase = intensidadBase;
        this.intensidadCartucho = intensidadCartucho;
    }

    public String getReferencia() {
        return referencia;
    }
    
    public String getTension() {
        return tension;
    }

    public double getIntensidadBase() {
        return intensidadBase;
    }

    public double getIntensidadCartucho() {
        return intensidadCartucho;
    }

    public void setReferencia(String referencia) {
        this.referencia=referencia;
    }

    public void setTension(String tension) {
        this.tension = tension;
    }
    
    public void setIntensidadBase(double intensidadBase) {
        this.intensidadBase = intensidadBase;
    }

    public void setIntensidadCartucho(double intensidadCartucho) {
        this.intensidadCartucho = intensidadCartucho;
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        if (null!=tension && !tension.equals("") && !tension.equals("0")) {
            str.append("\n");
            str.append(tension);
            str.append("V");
        }
        if (0!=intensidadBase) {
            str.append("\n");
            str.append(DecimalFormat.getInstance().format(intensidadBase));
            str.append("A");
        }
        if (0!=intensidadCartucho) {
            str.append("\n");
            str.append(DecimalFormat.getInstance().format(intensidadCartucho));
            str.append("A");
        }

        return str.toString();
    }
    
    public Object clone() {
        return new CGPBean(this.referencia, this.tension, this.intensidadBase, this.intensidadCartucho);
    }
}
