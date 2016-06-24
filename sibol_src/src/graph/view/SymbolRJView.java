package graph.view;

import graph.*;
import graph.editor.EditorRJ;
import main.Constantes;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.graph.VertexView;

/**
 * RJ View
 */
public class SymbolRJView extends VertexView {
    private static SymbolRenderer renderer = null;
    private static GraphCellEditor cellEditor = null;
    private static String SVG = "rj";

    public SymbolRJView() {
        super();
        if(this.renderer == null) this.renderer = new SymbolRenderer(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorRJ();
    }

    public SymbolRJView(Object cell) {
        super(cell);
        if(this.renderer == null) this.renderer = new SymbolRenderer(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorRJ();
    }

    public CellViewRenderer getRenderer() {
        return renderer;
    }
    
    public GraphCellEditor getEditor() {
        return cellEditor;
    }
}
