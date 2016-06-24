package graph.view;

import graph.*;
import graph.editor.EditorCGP;
import main.Constantes;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.graph.VertexView;

/**
 * CGP View
 */
public class SymbolCGPView extends VertexView {
    private static SymbolRenderer renderer = null;
    private static GraphCellEditor cellEditor = null;
    private static String SVG = "cgp";
    
    public SymbolCGPView() {
        super();
        if(this.renderer == null) this.renderer = new SymbolRenderer(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorCGP();
    }
    
    public SymbolCGPView(Object cell) {
        super(cell);
        if(this.renderer == null) this.renderer = new SymbolRenderer(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorCGP();
    }

    public CellViewRenderer getRenderer() {
        return renderer;
    }
    
    public GraphCellEditor getEditor() {
        return cellEditor;
    }
    
}
