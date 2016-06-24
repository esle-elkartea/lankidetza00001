/*
 * ModeloTablaPrevision.java
 *
 * Created on 24 de agosto de 2006, 9:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package funciones.beans;

import funciones.ParCombo;
import funciones.Utilidades;
import java.util.ListIterator;
import javax.swing.JTextField;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.LinkedList;

/** 
 * Modelo de Previsiones. Cada fila es una Prevision y las columnas son los datos
 * de la Prevision.
 * Implementa TableModel y dos métodos para añadir y eliminar Previsiones del
 * modelo 
 */
public class ModeloTablaPrevision implements TableModel
{
    private double sumaPotencia;
    private int numColumnas;
    private JTextField jTxtFldSumaPotencia;
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
        return 3;
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
        // Devuelve el número de Previsiones en el modelo, es decir, el número
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
        BeanCargasInd aux;
        
        // Se obtiene la Prevision de la fila indicada
        aux = (BeanCargasInd)(datos.get(rowIndex));
        
        // Se obtiene el campo apropiado según el valor de columnIndex
        switch (columnIndex)
        {
            case 0:
                return aux.getTipoCarga();
            case 1:
                return aux.getDenominacion();
            case 2:
                return aux.getPotencia();
            case 3:
                return aux.getIdInst();
            case 4:
                return aux.getIdCargInd();
            default:
                return null;
        }
    }
    
    /**
     * Borra del modelo la Prevision en la fila indicada 
     */
    public void borraFila (int fila)
    {
        // Se borra la fila 
        datos.remove(fila);
        
        // Y se avisa a los suscriptores, creando un TableModelEvent...
        TableModelEvent evento = new TableModelEvent (this, fila, fila, 
            TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        
        // ... y pasándoselo a los suscriptores
        avisaSuscriptores (evento);
    }
    
    /**
     * Añade una Prevision al final de la tabla
     */
    public void anhadeFila (BeanCargasInd nuevaPrevision)
    {
        // Añade la Prevision al modelo 
        datos.add (nuevaPrevision);
        
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
                // La columna 0 contiene el ParCOmbo del tipo de la carga.
                return ParCombo.class;
            case 1:
                // La columna 1 contiene la denominación.
                return String.class;
            case 2:
                // La columna 2 contine la potencia.
                return String.class;
            case 3:
                // La columna 3 contine el código autonumérico de CARGAS_INDUSTRIALES
                return String.class;
            case 4:
                // La columna 4 contine el código de INSTALACIONES
                return String.class;
            default:
                // Devuelve una clase Object por defecto.
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
                return "Tipo";
            case 1:
                return "Denominación";
            case 2:
                return "Potencia";
            case 3:
                return "CIINID";
            case 4:
                return "CIID";
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
        // Obtiene la Prevision de la fila indicada
        BeanCargasInd aux;
        aux = (BeanCargasInd)(datos.get(rowIndex));
        
        // Cambia el campo de BeanCargasInd que indica columnIndex, poniendole el 
        // aValue que se nos pasa.
        switch (columnIndex)
        {
            case 0:
                aux.setTipoCarga((ParCombo)aValue);
                break;
            case 1:
                aux.setDenominacion ((String)aValue);
                break;
            case 2:
                aux.setPotencia ((String)aValue);
                break;
            case 3:
                aux.setIdInst((String)aValue);
                break;
            case 4:
                aux.setIdCargInd((String)aValue);
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
        
        sumaPotencia();
    }
    
    public void sumaPotencia(){
        String strPotencia = "";
        sumaPotencia = 0;
        
        try{
            ListIterator lstItr = datos.listIterator(0);
            while(lstItr!=null && lstItr.hasNext()){
                BeanCargasInd bean = (BeanCargasInd)lstItr.next();
                if(bean.getPotencia() == null || bean.getPotencia().equals(""))
                    sumaPotencia +=0;
                else
                {   
                    strPotencia = Utilidades.cambiarComa(bean.getPotencia());
                    sumaPotencia += Double.parseDouble(strPotencia);
                    strPotencia = Utilidades.cambiarPunto(""+sumaPotencia);
                }
            }
            jTxtFldSumaPotencia.setText(strPotencia);
        }
        catch(java.lang.NumberFormatException e){
            sumaPotencia = 0;
            jTxtFldSumaPotencia.setText(""+sumaPotencia);
        }
    }
    
    public void setJTxtFldSumaPotencia(JTextField jTxtFldSumaPotencia){
        this.jTxtFldSumaPotencia = jTxtFldSumaPotencia;
    }
    
    public double getSumaPotencia(){
        return sumaPotencia;
    }
}