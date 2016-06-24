
/*
 * MyGraph.java
 *
 * Created on 30 de mayo de 2006, 9:54
 *
 */
package graph;

import java.awt.AlphaComposite;
import main.Constantes;

import org.jgraph.JGraph;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author sanjose
 */
public class MyGraph extends JGraph {
    public MyGraph(GraphModel model) {
        this(model, null);
    }
    
    public MyGraph(GraphModel model, GraphLayoutCache cache) {
        super(model, cache);
        inicializar();
    }
    
    public void inicializar() {
        getGraphLayoutCache().setFactory(new MyCellViewFactory());
        setMarqueeHandler(new MyMarqueeHandler(this));
        
        // puertos visibles
        setPortsVisible(true);
        setPortsScaled(false);
        
        // usamos rejilla
        setGridEnabled(true);
        setGridColor(Color.LIGHT_GRAY);
        setGridMode(JGraph.DOT_GRID_MODE);
        setGridSize(Constantes.GRID_SIZE);
        setGridVisible(true);
        
        // mínima distancia de movimiento
        setMinimumMove(Constantes.GRID_SIZE);
        
        // máxima distancia entre el ratón y una celda para ser seleccionada
        setTolerance(2);
        
        // permitimos aceptar la edición sin tener que pulsar ENTER
        setInvokesStopCellEditing(true);
        
        // no permitimos clonar con CTRL+arrastrar
        setCloneable(false);
        
        // no se puede desconectar
        setDisconnectable(false);
        
        // saltamos al puerto por defecto al conectar con una celda
        setJumpToDefaultPort(true);
        
        // no se pueden redimensionar las celdas
        setSizeable(true);
        
        // suavizado de líneas
        setAntiAliased(true);
        
        // color de selección
        setHighlightColor(Color.LIGHT_GRAY);
    }
    
    public void updateUI() {
        setUI(new MyGraphUI());
        invalidate();
    }
    
    public BufferedImage getImage(Color bg, int inset) {
        Dimension d = getPreferredSize();
        Rectangle2D bounds;
        if (d != null) {
            bounds = new Rectangle2D.Double(0, 0, d.getWidth(), d.getHeight());
        } else {
            Object[] cells = getRoots();
            bounds = getCellBounds(cells);
        }
        if (bounds != null) {
            toScreen(bounds);
            BufferedImage img = new BufferedImage((int) bounds.getWidth(),
                    (int) bounds.getHeight(),
                    (bg != null) ? BufferedImage.TYPE_INT_RGB
                    : BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = img.createGraphics();
            if (bg != null) {
                graphics.setColor(bg);
                graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
            } else {
                graphics.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.CLEAR, 0.0f));
                graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
                graphics.setComposite(AlphaComposite.SrcOver);
            }
            print(graphics);
            graphics.dispose();
            return img;
        }
        return null;
    }
}
