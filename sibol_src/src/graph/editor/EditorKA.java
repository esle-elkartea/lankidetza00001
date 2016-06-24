package graph.editor;

import graph.beans.KABean;
import graph.input.InputKA;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCellEditor;
import org.jgraph.graph.GraphCellEditor;

/**
 * @author sanjose
 */
public class EditorKA extends DefaultGraphCellEditor {
    public EditorKA() {}

    protected GraphCellEditor createGraphCellEditor() {
        return new RealCellEditor();
    }

    class RealCellEditor extends AbstractCellEditor implements GraphCellEditor {

        /**
         * componente usado para editar
         */
        InputKA editorComponent = new InputKA(this);

        public Component getGraphCellEditorComponent(JGraph graph, Object value, boolean isSelected) {
            Border aBorder = UIManager.getBorder("Tree.editorBorder");
            value = graph.getModel().getValue(value);
            if(value instanceof KABean) editorComponent.installValue((KABean)value);
            else {
                KABean wrapper = new KABean();
                editorComponent.installValue(wrapper);
            }

            editorComponent.setBorder(aBorder);

            return editorComponent;
        }

        /**
         * devuelve el valor almacenado en el objeto de negocio (bean)
         */
        public Object getCellEditorValue() {
            return editorComponent.getValue();
        }
        
        public void cancelCellEditing(){
            editorComponent.repaint();
            super.cancelCellEditing();
        }
        
    }
}
