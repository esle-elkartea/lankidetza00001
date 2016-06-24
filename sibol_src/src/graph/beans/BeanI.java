/*
 * BeanI.java
 *
 * Created on 2 de agosto de 2006, 10:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package graph.beans;

/**
 *
 * @author oscar
 */
public interface BeanI extends Cloneable {
       
    public boolean getEditado();
    public void setEditado(boolean editado);
    public abstract Object clone();
    
}
