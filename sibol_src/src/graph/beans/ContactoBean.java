package graph.beans;

import java.text.DecimalFormat;
import main.Constantes;

public class ContactoBean extends BaseBean{
    private String referencia;
    private String desc;

    public ContactoBean() {}

    public ContactoBean(String referencia, String desc) {
        this.referencia = referencia;
        this.desc = desc;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getDesc() {
        return desc;
    }
    
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(referencia);
        str.append("\n");
        str.append(desc);

        return str.toString();
    }
    
    public Object clone() {
        return new ContactoBean(this.referencia, this.desc);
    }
}
