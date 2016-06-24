package graph.view;

import graph.EdgeRenderer;
import graph.editor.EditorEdge;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphCellEditor;

/**
 * Edge View
 */
public class MyEdgeView extends EdgeView {
    private static EdgeRenderer renderer = null;
    private static GraphCellEditor cellEditor = null;

    public MyEdgeView() {
        super();
        if(this.renderer == null) this.renderer = new EdgeRenderer();
        if(this.cellEditor == null) this.cellEditor = new EditorEdge();
    }

    public MyEdgeView(Object cell) {
        super(cell);
        if(this.renderer == null) this.renderer = new EdgeRenderer();
        if(this.cellEditor == null) this.cellEditor = new EditorEdge();
    }
    
    public CellViewRenderer getRenderer() {
        return renderer;
    }
    
    public GraphCellEditor getEditor() {
        return cellEditor;
    }
}
