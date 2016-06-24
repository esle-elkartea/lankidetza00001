package graph.editor;

import graph.beans.IG2Bean;
import graph.input.InputIG2;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCellEditor;
import org.jgraph.graph.GraphCellEditor;

public class EditorIG2 extends DefaultGraphCellEditor {
    public EditorIG2() {}

    protected GraphCellEditor createGraphCellEditor() {
        return new RealCellEditor();
    }

    class RealCellEditor extends AbstractCellEditor implements GraphCellEditor {

        /**
         * componente usado para editar
         */
        InputIG2 editorComponent = new InputIG2(this);

        public Component getGraphCellEditorComponent(JGraph graph, Object value, boolean isSelected) {
            Border aBorder = UIManager.getBorder("Tree.editorBorder");
            value = graph.getModel().getValue(value);
            if(value instanceof IG2Bean) editorComponent.installValue((IG2Bean)value);
            else {
                IG2Bean wrapper = new IG2Bean();
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
