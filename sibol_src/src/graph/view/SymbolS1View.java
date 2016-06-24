package graph.view;

import graph.*;
import graph.editor.EditorS1;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.graph.VertexView;

/**
 * S1 View
 */
public class SymbolS1View extends VertexView {
    private static SymbolRenderer2 renderer = null;
    private static GraphCellEditor cellEditor = null;
    private static String SVG = "s1_2";

    public SymbolS1View() {
        super();
        if(this.renderer == null) this.renderer = new SymbolRenderer2(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorS1();
    }

    public SymbolS1View(Object cell) {
        super(cell);
        if(this.renderer == null) this.renderer = new SymbolRenderer2(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorS1();
    }

    public CellViewRenderer getRenderer() {
        return renderer;
    }
    
    public GraphCellEditor getEditor() {
        return cellEditor;
    }
    
}
