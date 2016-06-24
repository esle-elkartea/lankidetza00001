package graph.view;

import graph.*;
import graph.editor.EditorContactoA;
import main.Constantes;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.graph.VertexView;

/**
 * Contacto A View
 */
public class SymbolContactoAView extends VertexView {
    private static SymbolRenderer renderer = null;
    private static GraphCellEditor cellEditor = null;
    private static String SVG = "contacto_a";

    public SymbolContactoAView() {
        super();
        if(this.renderer == null) this.renderer = new SymbolRenderer(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorContactoA();
    }

    public SymbolContactoAView(Object cell) {
        super(cell);
        if(this.renderer == null) this.renderer = new SymbolRenderer(this, this.SVG);
        if(this.cellEditor == null) this.cellEditor = new EditorContactoA();
    }

    public CellViewRenderer getRenderer() {
        return renderer;
    }
    
    public GraphCellEditor getEditor() {
        return cellEditor;
    }
}
