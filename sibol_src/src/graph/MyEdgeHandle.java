/*
 * MyEdgeHandle.java
 *
 * Created on 13 de junio de 2006, 11:50
 *
 */
package graph;

import org.jgraph.graph.EdgeView;
import org.jgraph.graph.EdgeView.EdgeHandle;
import org.jgraph.graph.GraphContext;

import java.awt.event.MouseEvent;

/**
 * @author sanjose
 */
// definimos un EdgeHandle que use SHIFT+Botón (en vez del Botón
// Derecho del Ratón) para añadir/eliminar puntos de un "edge"
public class MyEdgeHandle extends EdgeHandle {
    public MyEdgeHandle(EdgeView edge, GraphContext ctx) {
        super(edge, ctx);
    }

    public boolean isAddPointEvent(MouseEvent event) {
        // añadimos puntos si está pulsado SHIFT
        return event.isShiftDown();
    }

    public boolean isRemovePointEvent(MouseEvent event) {
        // quitamos puntos si está pulsado SHIFT
        return event.isShiftDown();
    }
    
}
