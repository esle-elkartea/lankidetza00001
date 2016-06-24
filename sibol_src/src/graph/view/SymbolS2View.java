package graph.view;

import graph.*;
import graph.editor.EditorS2;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.graph.VertexView;

/**
 * S2 View
 */
public class SymbolS2View extends VertexView {
    private static SymbolRenderer2 renderer = null;
    private static GraphCellEditor cellEditor = null;
    private static String SVG = "s2_2";

    public SymbolS2View() {
        super();
        if(this.renderer == null) this.renderer = new SymbolRenderer2(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorS2();
    }

    public SymbolS2View(Object cell) {
        super(cell);
        if(this.renderer == null) this.renderer = new SymbolRenderer2(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorS2();
    }

    public CellViewRenderer getRenderer() {
        return renderer;
    }
    
    public GraphCellEditor getEditor() {
        return cellEditor;
    }
    
}
