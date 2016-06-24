/*
 * NewClass.java
 *
 * Created on 30 de agosto de 2006, 10:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package funciones.beans;

import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 *
 * @author enrique
 */
public class MyComboBoxEditor extends DefaultCellEditor {
        public MyComboBoxEditor(Vector items) {
            super(new JComboBox(items));
        }
    
}
