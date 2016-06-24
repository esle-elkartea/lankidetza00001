package graph.editor;

import graph.beans.ZetacBean;
import graph.input.InputZetac;
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
public class EditorZetac extends DefaultGraphCellEditor {
    public EditorZetac() {}

    protected GraphCellEditor createGraphCellEditor() {
        return new RealCellEditor();
    }

    class RealCellEditor extends AbstractCellEditor implements GraphCellEditor {

        /**
         * componente usado para editar
         */
        InputZetac editorComponent = new InputZetac(this);

        public Component getGraphCellEditorComponent(JGraph graph, Object value, boolean isSelected) {
            Border aBorder = UIManager.getBorder("Tree.editorBorder");
            value = graph.getModel().getValue(value);
            if(value instanceof ZetacBean) editorComponent.installValue((ZetacBean)value);
            else {
                ZetacBean wrapper = new ZetacBean();
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
