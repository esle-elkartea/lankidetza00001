/*
 * PartesDesconectadasException.java
 *
 * Created on 15 de septiembre de 2006, 8:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package errores;

/**
 *
 * @author oscar
 */
public class PartesDesconectadasException extends Exception{
       
    /**
     * Creates a new instance of PartesDesconectadasException
     */
    public PartesDesconectadasException() {
        super();
    }
    
    public PartesDesconectadasException(String mensaje) {
        super(mensaje);
    }
    
}
