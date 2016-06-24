/*
 * BaseBean.java
 *
 * Created on 2 de agosto de 2006, 13:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package graph.beans;

/**
 *
 * @author oscar
 */
public class BaseBean implements BeanI{
    
    private boolean editado = false;
    
    /** Creates a new instance of BaseBean */
    public BaseBean() {
    }

    public boolean getEditado() {
        return editado;
    }

    public void setEditado(boolean editado) {
        this.editado = editado;
    }
    
    public Object clone() {
        return new BaseBean();
    }
}
