/*
 * IntfzValidarDatos.java
 *
 * Created on 23 de agosto de 2006, 12:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package forms.recoger;

import java.sql.SQLException;

/**
 *
 * @author enrique
 */
public interface IntfzValidarDatos {
    public boolean esValido();
    public void insertarBD() throws SQLException;
}
