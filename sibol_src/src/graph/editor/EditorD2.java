package graph.editor;

import graph.beans.D1Bean;
import graph.input.InputD2;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCellEditor;
import org.jgraph.graph.GraphCellEditor;

public class EditorD2 extends DefaultGraphCellEditor {
    public EditorD2() {}

    protected GraphCellEditor createGraphCellEditor() {
        return new RealCellEditor();
    }

    class RealCellEditor extends AbstractCellEditor implements GraphCellEditor {

        /**
         * componente usado para editar
         */
        InputD2 editorComponent = new InputD2(this);

        public Component getGraphCellEditorComponent(JGraph graph, Object value, boolean isSelected) {
            Border aBorder = UIManager.getBorder("Tree.editorBorder");
            value = graph.getModel().getValue(value);
            if(value instanceof D1Bean) editorComponent.installValue((D1Bean)value);
            else {
                D1Bean wrapper = new D1Bean();
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
