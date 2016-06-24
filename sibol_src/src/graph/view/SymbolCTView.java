package graph.view;

import graph.*;
import graph.editor.EditorCT;
import main.Constantes;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.graph.VertexView;

/**
 * CT View
 */
public class SymbolCTView extends VertexView {
    private static SymbolRenderer renderer = null;
    private static GraphCellEditor cellEditor = null;
    private static String SVG = "ct";

    public SymbolCTView() {
        super();
        if(this.renderer == null) this.renderer = new SymbolRenderer(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorCT();
    }

    public SymbolCTView(Object cell) {
        super(cell);
        if(this.renderer == null) this.renderer = new SymbolRenderer(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorCT();
    }
    
    public CellViewRenderer getRenderer() {
        return renderer;
    }
    
    public GraphCellEditor getEditor() {
        return cellEditor;
    }
    
}
