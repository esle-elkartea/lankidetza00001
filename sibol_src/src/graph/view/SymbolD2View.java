package graph.view;

import graph.*;
import graph.editor.EditorD2;
import main.Constantes;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.graph.VertexView;

/**
 * D View
 */
public class SymbolD2View extends VertexView {
    private static SymbolRenderer renderer = null;
    private static GraphCellEditor cellEditor = null;
    private static String SVG = "d2";

    public SymbolD2View() {
        super();
        if(this.renderer == null) this.renderer = new SymbolRenderer(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorD2();
    }

    public SymbolD2View(Object cell) {
        super(cell);
        if(this.renderer == null) this.renderer = new SymbolRenderer(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorD2();
    }

    public CellViewRenderer getRenderer() {
        return renderer;
    }
    
    public GraphCellEditor getEditor() {
        return cellEditor;
    }
    
}
