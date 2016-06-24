package graph.view;

import graph.*;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.graph.VertexView;

/**
 * Cajetin Salidas View
 */
public class CajetinSView extends VertexView {
    private static SymbolCajetinSRenderer renderer = null;

    public CajetinSView() {
        super();
        if(this.renderer == null) this.renderer = new SymbolCajetinSRenderer(this);
    }

    public CajetinSView(Object cell) {
        super(cell);
        if(this.renderer == null) this.renderer = new SymbolCajetinSRenderer(this);
    }

    public CellViewRenderer getRenderer() {
        return renderer;
    }
    
    public GraphCellEditor getEditor() {
        return cellEditor;
    }
    
}
