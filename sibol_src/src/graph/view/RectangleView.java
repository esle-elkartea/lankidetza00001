package graph.view;

import graph.*;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;

/**
 * Rectangle View
 */
public class RectangleView extends VertexView {
    private static RectangleRenderer renderer = null;
    
    public RectangleView() {
        super();
        if(this.renderer == null) this.renderer = new RectangleRenderer(this);
    }
    
    public RectangleView(Object cell) {
        super(cell);
        if(this.renderer == null) this.renderer = new RectangleRenderer(this);
    }

    public CellViewRenderer getRenderer() {
        return renderer;
    }    
}
