/*
 * MyMarqueeHandler.java
 *
 * Created on 30 de mayo de 2006, 10:17
 *
 */
package graph;

import funciones.Sesion;
import graph.beans.EdgeBean;
import graph.cell.MyEdge;
import main.Constantes;
import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.Port;
import org.jgraph.graph.PortView;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.util.Hashtable;
import java.util.Map;

import javax.swing.SwingUtilities;

/**
 * @author sanjose
 */
public class MyMarqueeHandler extends BasicMarqueeHandler {
    private MyGraph graph;
    // primer puerto y puerto actual
    private PortView port, firstPort;
    // puntos inicial y actual
    private Point2D startP, currentP;

    MyMarqueeHandler(MyGraph g) {
        this.graph = g;
    }

    protected void connect(Port source, Port target) {
        MyEdge edge = new MyEdge(new EdgeBean());
        if(graph.getModel().acceptsSource(edge, source) && graph.getModel().acceptsTarget(edge, target)) {
            // ortogonal
            //GraphConstants.setRouting(edge.getAttributes(), GraphConstants.ROUTING_SIMPLE);
            //GraphConstants.setLineStyle(edge.getAttributes(), GraphConstants.STYLE_ORTHOGONAL);
            graph.getGraphLayoutCache().insertEdge(edge, source, target);
        }
    }

    public void mouseDragged(MouseEvent e) {
        // si tenemos un punto inicial memorizado
        if(startP != null) {
            Graphics g = graph.getGraphics();
            PortView newPort = getTargetPortAt(e.getPoint());
            // sólo redibujamos si se ha cambiado de puerto
            if((newPort == null) || (newPort != port)) {
                // ocultamos el conector anterior
                paintConnector(Color.black, graph.getBackground(), g);
                // si hemos encontrado un puerto almacenamos su posición
                // sino cogemos la posición del ratón
                port = newPort;
                if(port != null) currentP = graph.toScreen(port.getLocation());
                else currentP = graph.snap(e.getPoint());
                // dibujamos el nuevo conector
                paintConnector(graph.getBackground(), Color.BLACK, g);
            }
        }
        super.mouseDragged(e);
    }

    public void mouseMoved(MouseEvent e) {
        // comprobamos si estamos sobre un puerto...
        if((e != null) && (getSourcePortAt(e.getPoint()) != null) && graph.isPortsVisible()) {
            // ...y si es así cambiamos el tipo de cursor del ratón (se resetea automáticamente)
            graph.setCursor(new Cursor(Cursor.HAND_CURSOR));
            // NOTA: esto es para indicar al MouseHandle de BasicGraphUI
            // que no siga procesando
            e.consume();
        }
        else
            super.mouseMoved(e);
    }

    public void mousePressed(final MouseEvent e) {
        // mostramos menú contextual al pulsar el botón derecho del ratón
        if(SwingUtilities.isRightMouseButton(e)) {
            // Object cell = graph.getFirstCellForLocation(e.getX(), e.getY());
            // JPopupMenu menu = createPopupMenu(e.getPoint(), cell);
            // menu.show(graph, e.getX(), e.getY());
        }
        // si tenemos un puerto memorizar y estamos en modo conexión (=PortsVisible)
        else if((port != null) && graph.isPortsVisible()) {
            // guardamos el punto inicial y el primer puerto
            startP = graph.toScreen(port.getLocation());
            firstPort = port;
        }
        else
            super.mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
        // si es un evento válido conectamos el primer puerto con el actual
        // sino redibujamos el gráfico
        if((e != null) && (port != null) && (firstPort != null) && (firstPort != port)) {
            connect((Port)firstPort.getCell(), (Port)port.getCell());
            e.consume();
        }
        else
            graph.repaint();
        firstPort = port = null;
        startP = currentP = null;
        super.mouseReleased(e);
    }

    protected void paintConnector(Color fg, Color bg, Graphics g) {
        // pintamos el conector en modo XOR
        g.setColor(fg);
        g.setXORMode(bg);
        paintPort(graph.getGraphics());
        // si tenemos un puerto inicial y los puntos inicial y actual
        // dibujamos una línea
        if((firstPort != null) && (startP != null) && (currentP != null)) {
            g.drawLine((int)startP.getX(), (int)startP.getY(), (int)currentP.getX(), (int)currentP.getY());
        }
    }

    protected void paintPort(Graphics g) {
        // si el puerto actual es válido lo resaltamos
        if(port != null) {
            // si no es un puerto flotante usamos los límites del padre, sino los propios
            Rectangle2D r = (GraphConstants.getOffset(port.getAllAttributes()) != null) ? port.getBounds() : port.getParentView().getBounds();
            // escalamos de modelo a pantalla
            r = graph.toScreen((Rectangle2D)r.clone());
            // añadimos algo de espacio adicional para el resalte
            r.setFrame(r.getX() - 3, r.getY() - 3, r.getWidth() + 6, r.getHeight() + 6);
            // dibujamos el puerto en modo 'preview' (=Highlight)
            graph.getUI().paintCell(g, port, r, true);
        }
    }

    public PortView getSourcePortAt(Point2D point) {
        graph.setJumpToDefaultPort(false);
        PortView result;
        try {
            result = graph.getPortViewAt(point.getX(), point.getY());
        }
        finally {
            graph.setJumpToDefaultPort(true);
        }

        return result;
    }

    protected PortView getTargetPortAt(Point2D point) {
        return graph.getPortViewAt(point.getX(), point.getY());
    }

    public boolean isForceMarqueeEvent(MouseEvent e) {
        if(e.isShiftDown()) {
            return false;
        }
        // si se pulsa el botón derecho del ratón queremos mostrar menú contextual
        if(SwingUtilities.isRightMouseButton(e)) {
            return true;
        }
        // buscamos y guardamos el puerto...
        port = getSourcePortAt(e.getPoint());
        // ...y si lo encontramos y estamos en modo conexión (=PortsVisible)
        if((port != null) && graph.isPortsVisible()) {
            return true;
        }

        return super.isForceMarqueeEvent(e);
    }
}
