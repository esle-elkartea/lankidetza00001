/*
 * ModeloTablaResumen.java
 *
 * Created on 24 de agosto de 2006, 9:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package funciones.beans;

import funciones.ParCombo;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.LinkedList;

/** 
 * Modelo de la tablaResumen. Cada fila es un BeanResumen y las columnas son los datos
 * del BeanResumen.
 * Implementa TableModel y dos métodos para añadir y eliminar las instancias de BeanResumen del
 * modelo 
 */
public class ModeloTablaResumen implements TableModel
{
    private int numColumnas;
    /**
     * Lista con los datos. Cada elemento de la lista es una instancia de
     * BeanCargasInd
     */
    private LinkedList datos = new LinkedList();
    /** Lista de suscriptores. El JTable será un suscriptor de este modelo de
     * datos */
    private LinkedList listeners = new LinkedList();
    
    /** Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     *
     */      
    public int getColumnCount() {
        // Devuelve el número de columnas del modelo que deseamos sea visible.
        return 12;
    }
    
    /** Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    public int getRowCount() {
        // Devuelve el número de BeanResumen en el modelo, es decir, el número
        // de filas en la tabla.
        return datos.size();
    }
    
    /** Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param	rowIndex	the row whose value is to be queried
     * @param	columnIndex 	the column whose value is to be queried
     * @return	the value Object at the specified cell
     *
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        BeanResumen aux;
        
        // Se obtiene el BeanResumen de la fila indicada
        aux = (BeanResumen)(datos.get(rowIndex));
        
        // Se obtiene el campo apropiado según el valor de columnIndex
        switch (columnIndex)
        {
            case 0:
                return aux.getTipoCircuito();
            case 1:
                return aux.getDescCircuito();
            case 2:
                return aux.getPotCalculo();
            case 3:
                return aux.getTensionCalculo();
            case 4:
                return aux.getIntCalculo();
            case 5:
                return aux.getNumCondSecc();
            case 6:
                return aux.getAislamTension();
            case 7:
                return aux.getTipoInst();
            case 8:
                return aux.getIntMax();
            case 9:
                return aux.getCcpia();
            case 10:
                return aux.getLongitud();
            case 11:
                return aux.getCaidaTension();
            case 12:
                return aux.getIdDatosCircuito();
            case 13:
                return aux.getIdInst();
            default:
                return null;
        }
    }
    
    /**
     * Borra del modelo el BeanResumen en la fila indicada 
     */
    public void borraFila (int fila)
    {
        datos.remove(fila);
        
        // Y se avisa a los suscriptores, creando un TableModelEvent...
        TableModelEvent evento = new TableModelEvent (this, fila, fila, 
            TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        
        // ... y pasándoselo a los suscriptores
        avisaSuscriptores (evento);
    }
    
    /**
     * Añade unn BeanResumen al final de la tabla
     */
    public void anhadeFila (BeanResumen nuevoBeanResumen)
    {
        // Añade el BeanResumen al modelo 
        datos.add (nuevoBeanResumen);
        
        // Avisa a los suscriptores creando un TableModelEvent...
        TableModelEvent evento;
        evento = new TableModelEvent (this, this.getRowCount()-1,
            this.getRowCount()-1, TableModelEvent.ALL_COLUMNS,
            TableModelEvent.INSERT);

        // ... y avisando a los suscriptores
        avisaSuscriptores (evento);
    }
    
    /** Adds a listener to the list that is notified each time a change
     * to the data model occurs.
     *
     * @param	l		the TableModelListener
     *
     */
    public void addTableModelListener(TableModelListener l) {
        // Añade el suscriptor a la lista de suscriptores
        listeners.add (l);
    }
    
    /** Returns the most specific superclass for all the cell values
     * in the column.  This is used by the <code>JTable</code> to set up a
     * default renderer and editor for the column.
     *
     * @param columnIndex  the index of the column
     * @return the common ancestor class of the object values in the model.
     *
     */
    public Class getColumnClass(int columnIndex) {
        // Devuelve la clase que hay en cada columna.
        switch (columnIndex)
        {
            case 0:
                return ParCombo.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            case 6:
                return String.class;
            case 7:
                return ParCombo.class;
            case 8:
                return String.class;
            case 9:
                return String.class;
            case 10:
                return String.class;
            case 11:
                return String.class;
           case 12:
                return String.class;
           case 13:
                return String.class;
            default:
                return Object.class;
        }
    }
    
    /** Returns the name of the column at <code>columnIndex</code>.  This is used
     * to initialize the table's column header name.  Note: this name does
     * not need to be unique; two columns in a table can have the same name.
     *
     * @param	columnIndex	the index of the column
     * @return  the name of the column
     *
     */
    public String getColumnName(int columnIndex) 
    {
        // Devuelve el nombre de cada columna. Este texto aparecerá en la
        // cabecera de la tabla.
        switch (columnIndex)
        {
            case 0:
                return "<html><font size=2>Tipo<br>circuito</font></html>";
            case 1:
                return "<html><font size=2>Desc.<br>circuito</font></html>";
            case 2:
                return "<html><font size=2>Pot.<br>cálculo(W)</font></html>";
            case 3:
                return "<html><font size=2>Tensión<br>cálculo(V)</font></html>";
            case 4:
                return "<html><font size=2>Int. de<br>cálculo(A)</font></html>";
            case 5:
                return "<html><font size=2>Nº Cond. Secc.<br>Material(Nº-mm2 CU/Al)</font></html>";
            case 6:
                return "<html><font size=2>Aislam. tensión<br>nominal(V)</font></html>";
            case 7:
                return "<html><font size=2>Tipo instalación</font></html>";
            case 8:
                return "<html><font size=2>Int. máx.<br>admisible(A)</font></html>";
            case 9:
                return "<html><font size=2>C/C<br>PIA(A)</font></html>";
            case 10:
                return "<html><font size=2>Long.<br>(m)</font></html>";
            case 11:
                return "<html><font size=2>Caída<br>tensión(V)</font></html>";
            case 12:
                return "idAuto";
            case 13:
                return "idInstalacion";
            default:
                return null;
        } 
    }
    
    /** Returns true if the cell at <code>rowIndex</code> and
     * <code>columnIndex</code>
     * is editable.  Otherwise, <code>setValueAt</code> on the cell will not
     * change the value of that cell.
     *
     * @param	rowIndex	the row whose value to be queried
     * @param	columnIndex	the column whose value to be queried
     * @return	true if the cell is editable
     * @see #setValueAt
     *
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Permite que la celda sea editable.
        return true;
    }
    
    /** Removes a listener from the list that is notified each time a
     * change to the data model occurs.
     *
     * @param	l		the TableModelListener
     *
     */
    public void removeTableModelListener(TableModelListener l) {
        // Elimina los suscriptores.
        listeners.remove(l);
    }
    
    /** Sets the value in the cell at <code>columnIndex</code> and
     * <code>rowIndex</code> to <code>aValue</code>.
     *
     * @param	aValue		 the new value
     * @param	rowIndex	 the row whose value is to be changed
     * @param	columnIndex 	 the column whose value is to be changed
     * @see #getValueAt
     * @see #isCellEditable
     *
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
    {
        // Obtiene el BeanResumen de la fila indicada
        BeanResumen aux;
        aux = (BeanResumen)(datos.get(rowIndex));
        
        // Cambia el campo de BeanResumen que indica columnIndex, poniendole el 
        // aValue que se nos pasa.
        switch (columnIndex)
        {         
            case 0:
                aux.setTipoCircuito((ParCombo)aValue);
                break;
            case 1:
                aux.setDescCircuito ((String)aValue);
                break;
            case 2:
                aux.setPotCalculo ((String)aValue);
                break;
            case 3:
                aux.setTensionCalculo((String)aValue);
                break;
            case 4:
                aux.setIntCalculo((String)aValue);
                break;
            case 5:
                aux.setNumCondSecc ((String)aValue);
                break;
            case 6:
                aux.setAislamTension ((String)aValue);
                break;
            case 7:
                aux.setTipoInst((ParCombo)aValue);
                break;
            case 8:
                aux.setIntMax((String)aValue);
                break;
            case 9:
                aux.setCcpia ((String)aValue);
                break;
            case 10:
                aux.setLongitud ((String)aValue);
                break;
            case 11:
                aux.setCaidaTension((String)aValue);
                break;
            case 12:
                aux.setIdDatosCircuito((String)aValue);
                break;
            case 13:
                aux.setIdInst((String)aValue);
                break;
            default:
                break;
        }
        
        // Avisa a los suscriptores del cambio, creando un TableModelEvent ...
        TableModelEvent evento = new TableModelEvent (this, rowIndex, rowIndex, 
            columnIndex);

        // ... y pasándoselo a los suscriptores.
        avisaSuscriptores (evento);
    }
    
    /**
     * Pasa a los suscriptores el evento.
     */
    private void avisaSuscriptores (TableModelEvent evento)
    {
        int i;
        
        // Bucle para todos los suscriptores en la lista, se llama al metodo
        // tableChanged() de los mismos, pasándole el evento.
        for (i=0; i<listeners.size(); i++)
            ((TableModelListener)listeners.get(i)).tableChanged(evento);
    }
}