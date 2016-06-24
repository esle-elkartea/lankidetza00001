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
// definimos un EdgeHandle que use SHIFT+Bot�n (en vez del Bot�n
// Derecho del Rat�n) para a�adir/eliminar puntos de un "edge"
public class MyEdgeHandle extends EdgeHandle {
    public MyEdgeHandle(EdgeView edge, GraphContext ctx) {
        super(edge, ctx);
    }

    public boolean isAddPointEvent(MouseEvent event) {
        // a�adimos puntos si est� pulsado SHIFT
        return event.isShiftDown();
    }

    public boolean isRemovePointEvent(MouseEvent event) {
        // quitamos puntos si est� pulsado SHIFT
        return event.isShiftDown();
    }
    
}
