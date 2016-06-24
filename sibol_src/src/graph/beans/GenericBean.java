package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

public class GenericBean extends BaseBean{
    private String referencia;
    private String descripcion1;
    private String descripcion2;
    private String descripcion3;

    public GenericBean() {}

    public GenericBean(String referencia, String descripcion1, String descripcion2, String descripcion3) {
        this.referencia = referencia;
        this.descripcion1 = descripcion1;
        this.descripcion2 = descripcion2;
        this.descripcion3 = descripcion3;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getDescripcion1() {
        return descripcion1;
    }
    
    public String getDescripcion2() {
        return descripcion2;
    }
    
    public String getDescripcion3() {
        return descripcion3;
    }
       
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setDescripcion1(String descripcion1) {
        this.descripcion1 = descripcion1;
    }
    
    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }
    
    public void setDescripcion3(String descripcion3) {
        this.descripcion3 = descripcion3;
    }
        
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(descripcion1);
        str.append("\n");
        str.append(descripcion2);
        str.append("\n");
        str.append(descripcion3);
        return str.toString();
    }
    
    public Object clone() {
        return new GenericBean(this.referencia, this.descripcion1, this.descripcion2, this.descripcion3);
    }
}
