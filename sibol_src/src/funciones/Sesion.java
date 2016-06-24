/*
 * Sesion.java
 *
 * Created on 2 de agosto de 2006, 14:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package funciones;

import java.util.Hashtable;
import javax.swing.JFrame;

/**
 *
 * @author enrique
 */
public class Sesion {
    public static Sesion instance = new Sesion();
    private BaseDatos baseDatos;
    private JFrame frmEntrada;
    private Hashtable ht = new Hashtable();
    
    private Sesion() {
    }
    
    public void setValorHt(String key, Object valor) {
        ht.put(key, valor);
    }
    
    public Object getValorHt(String key) {
        return ht.get(key);
    }
    
    public void setBaseDatos(BaseDatos bd) {
        baseDatos = bd;
    }
    
    public BaseDatos getBaseDatos() {
        return baseDatos;
    }
    
    public void setFrmEntrada(JFrame frm) {
        frmEntrada = frm;
    }
    
    public JFrame getFrmEntrada() {
        return frmEntrada;
    }
    
    public static Sesion getInstance() {
        //si no hay ninguna instancia de esta clase tenemos que crear una
        if (instance == null) {
            // Necesitamos sincronizacion para serializar (no multithread)
            // Las invocaciones de este metodo
            synchronized(Config.class) {
                if (instance == null) {
                    instance = new Sesion();
                }
            }
        }
        return instance;
    }
    
    public void reset() {
        ht.clear();
    }
    
    public void cerrarAplicacion(){
        baseDatos.close();
        System.exit(0);
    }
    
}
