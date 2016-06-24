package graph;

import funciones.Utilidades;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import java.util.StringTokenizer;

public class EdgeRenderer extends org.jgraph.graph.EdgeRenderer {
    public EdgeRenderer() {}

    protected void paintLabel(Graphics g, String label, Point2D p, boolean mainLabel) {
        // fijamos el tipo de letra
        Utilidades.tipoLetra(g);
        
        if((p != null) && (label != null) && (label.length() > 0) && (metrics != null)) {
            int sw = metrics.stringWidth(label);
            int sh = metrics.getHeight();
            Graphics2D g2 = (Graphics2D)g;
            boolean applyTransform = false;
            double angle = 0;
            int dx = -sw / 2;
            int offset = (isMoveBelowZero || applyTransform) ? 0 : Math.min(0, (int)(dx + p.getX()));
            g2.translate(p.getX() - offset, p.getY());
            if(applyTransform) {
                angle = 0;
                g2.rotate(angle);
            }

            if(isOpaque() && mainLabel) {
                g.setColor(getBackground());
                g.fillRect(-sw / 2 - 1, -sh / 2 - 1, sw + 2, sh + 2);
            }

            if((borderColor != null) && mainLabel) {
                g.setColor(borderColor);
                g.drawRect(-sw / 2 - 1, -sh / 2 - 1, sw + 2, sh + 2);
            }

            int dy = +sh / 4;
            g.setColor(fontColor);
            if(applyTransform && (borderColor == null) && !isOpaque()) {
                // Shift label perpendicularly by the descent so it
                // doesn't cross the line.
                dy = -metrics.getDescent();
            }

            //g.drawString(label, dx, dy);
            drawString(g, label, dx, dy);
            
            if(applyTransform) {
                // Undo the transform
                g2.rotate(-angle);
            }

            g2.translate(-p.getX() + offset, -p.getY());
        }
    }

    private void drawString(Graphics g, String s, int textX, int textY) {
        if(s.indexOf('\n') == -1) g.drawString(s, textX, textY);
        else {
            String[] strs = splitStringByLines(s);
            int height = g.getFontMetrics().getHeight();

            int totalHeight = height * strs.length;
            Rectangle bounds = g.getClipBounds();
            g.setClip((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth(), (int)bounds.getHeight() + totalHeight);
            
            g.drawString(strs[0], textX, textY);
            for(int i = 1; i < strs.length; i++)
                if(strs[i] != null) g.drawString(strs[i], textX, textY + (height * i));
        }
    }

    private String[] splitStringByLines(String str) {
        String[] strs;
        int lines = 1;
        int i, c;
        for(i = 0, c = str.length(); i < c; i++) {
            if(str.charAt(i) == '\n') lines++;
        }

        strs = new String[lines];
        StringTokenizer st = new StringTokenizer(str, "\n");
        int line = 0;
        while(st.hasMoreTokens())
            strs[line++] = st.nextToken();

        return strs;
    }
}
