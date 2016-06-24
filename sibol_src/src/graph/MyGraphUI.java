
/*
 * MyGraphUI.java
 *
 * Created on 30 de mayo de 2006, 16:39
 *
 */
package graph;

import main.Constantes;

import org.jgraph.plaf.basic.BasicGraphUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * @author sanjose
 */
public class MyGraphUI extends BasicGraphUI {
    private int pageWidth;
    private int pageHeight;

    public MyGraphUI() {
        super();
        pageWidth = Constantes.TAMPAG_WIDTH;
        pageHeight = Constantes.TAMPAG_HEIGHT;
    }
    
    protected void paintBackground(Graphics g) {
        if(graph.isGridVisible()) {
            Point2D p = graph.toScreen(new Point(pageWidth, pageHeight));
            int w = (int)p.getX();
            int h = (int)p.getY();

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, graph.getWidth(), graph.getHeight());
            g.setColor(Color.DARK_GRAY);
            g.fillRect(3, 3, w, h);
            g.setColor(graph.getBackground());
            g.fillRect(1, 1, w - 1, h - 1);
            g.setColor(Color.DARK_GRAY);

            p = graph.toScreen(new Point(Constantes.RULER_WIDTH, 0));
            int anchuraRegleta = (int)p.getX();

            for(int x = 0; x < 8; x++) {
                int xR = x * ((w - anchuraRegleta) / 8) + anchuraRegleta;
                g.drawRect(xR, 0, (w - anchuraRegleta) / 8, anchuraRegleta);
                g.drawString(String.valueOf(x + 1), xR + ((w - anchuraRegleta) / 16), (anchuraRegleta / 2) + 4);
            }

            for(int y = 0; y < 8; y++) {
                int yR = y * ((h - anchuraRegleta) / 8) + anchuraRegleta;
                g.drawRect(0, yR, anchuraRegleta, (h - anchuraRegleta) / 8);
                g.drawString(String.valueOf((char)('A' + y)), (anchuraRegleta / 2) - 2, yR + ((h - anchuraRegleta) / 16));
            }
        }
        
        super.paintBackground(g);
    }
}
