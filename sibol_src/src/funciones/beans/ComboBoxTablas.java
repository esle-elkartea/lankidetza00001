/*
 * NewClass.java
 *
 * Created on 25 de agosto de 2006, 10:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package funciones.beans;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellRenderer;

public class ComboBoxTablas extends JComboBox implements TableCellRenderer
 {

    //constr klasjdksakld
    public ComboBoxTablas(Vector items) {
         super(items);

         // Nos apuntamos a cuando se seleccione algo, para avisar a la tabla
         // de que hemos cambiado el dato.
         this.addActionListener(new ActionListener() {
             public void actionPerformed (ActionEvent evento)
             {
                 editado(true);
             }
         });
         
         // Nos apuntamos a la p�rdida de foco, que quiere decir que se ha
         // dejado de editar la celda, sin aceptar ninguna opci�n. Avisamos
         // a la tabla de la cancelaci�n de la edici�n.
         this.addFocusListener(new FocusListener() {
             public void focusGained (FocusEvent e) {;}
             public void focusLost (FocusEvent e)
             {
                 editado (false);
             }
         });
     }
     
     /** Adds a listener to the list that's notified when the editor
      * stops, or cancels editing.
      *
      * @param	l		the CellEditorListener
      *
      */
     public void addCellEditorListener(CellEditorListener l) {
         // Se a�ade el suscriptor a la lista.
         suscriptores.add (l);
     }
     
     /** Tells the editor to cancel editing and not accept any partially
      * edited value.
      *
      */
     public void cancelCellEditing() {
         // No hay que hacer nada especial.
     }
     
     /** Returns the value contained in the editor.
      * @return the value contained in the editor
      *
      */
     public Object getCellEditorValue() {
         
          return this.getSelectedItem();
     }
     
     /**  Sets an initial <code>value</code> for the editor.  This will cause
      *  the editor to <code>stopEditing</code> and lose any partially
      *  edited value if the editor is editing when this method is called. <p>
      *
      *  Returns the component that should be added to the client's
      *  <code>Component</code> hierarchy.  Once installed in the client's
      *  hierarchy this component will then be able to draw and receive
      *  user input.
      *
      * @param	table		the <code>JTable</code> that is asking the
      * 				editor to edit; can be <code>null</code>
      * @param	value		the value of the cell to be edited; it is
      * 				up to the specific editor to interpret
      * 				and draw the value.  For example, if value is
      * 				the string "true", it could be rendered as a
      * 				string or it could be rendered as a check
      * 				box that is checked.  <code>null</code>
      * 				is a valid value
      * @param	isSelected	true if the cell is to be rendered with
      * 				highlighting
      * @param	row     	the row of the cell being edited
      * @param	column  	the column of the cell being edited
      * @return	the component for editing
      *
      */
     public Component getTableCellEditorComponent(JTable table, Object value, 
        boolean isSelected, int row, int column) {
            // Devolvemos el JComboBox del que heredamos.
            return this;
     }
     
     /** Asks the editor if it can start editing using <code>anEvent</code>.
      * <code>anEvent</code> is in the invoking component coordinate system.
      * The editor can not assume the Component returned by
      * <code>getCellEditorComponent</code> is installed.  This method
      * is intended for the use of client to avoid the cost of setting up
      * and installing the editor component if editing is not possible.
      * If editing can be started this method returns true.
      *
      * @param	anEvent		the event the editor should use to consider
      * 				whether to begin editing or not
      * @return	true if editing can be started
      * @see #shouldSelectCell
      *
      */
     public boolean isCellEditable(EventObject anEvent) {
         // La celda es editable ante cualquier evento.
         return true;
     }
     
     /** Removes a listener from the list that's notified
      *
      * @param	l		the CellEditorListener
      *
      */
     public void removeCellEditorListener(CellEditorListener l) {
         // Se elimina el suscriptor.
         suscriptores.remove(l);
     }
     
     /** Returns true if the editing cell should be selected, false otherwise.
      * Typically, the return value is true, because is most cases the editing
      * cell should be selected.  However, it is useful to return false to
      * keep the selection from changing for some types of edits.
      * eg. A table that contains a column of check boxes, the user might
      * want to be able to change those checkboxes without altering the
      * selection.  (See Netscape Communicator for just such an example)
      * Of course, it is up to the client of the editor to use the return
      * value, but it doesn't need to if it doesn't want to.
      *
      * @param	anEvent		the event the editor should use to start
      * 				editing
      * @return	true if the editor would like the editing cell to be selected;
      *    otherwise returns false
      * @see #isCellEditable
      *
      */
     public boolean shouldSelectCell(EventObject anEvent) {
         // Indica si al editar la celda, debemos seleccionar la fila que la
         // contiene.
         return true;
     }
     
     /** Tells the editor to stop editing and accept any partially edited
      * value as the value of the editor.  The editor returns false if
      * editing was not stopped; this is useful for editors that validate
      * and can not accept invalid entries.
      *
      * @return	true if editing was stopped; false otherwise
      *
      */
     public boolean stopCellEditing() {
         // Indica si se puede detener la edici�n.
         return true;
     }
 
     /**
      * Si cambiado es true, se avisa a los suscriptores de que se ha terminado
      * la edici�n. Si es false, se avisa de que se ha cancelado la edici�n.
      */
     protected void editado(boolean cambiado)
     {
         ChangeEvent evento = new ChangeEvent (this);
         int i;
         for (i=0; i<suscriptores.size(); i++)
         {
             CellEditorListener aux = (CellEditorListener)suscriptores.get(i);
             if (cambiado)
                aux.editingStopped(evento);
             else
                aux.editingCanceled(evento);
         }
     }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
    
            // Select the current value
            setSelectedItem(value);
            return this;
    }
     
     /** Lista de suscriptores */
     private LinkedList suscriptores = new LinkedList();
}