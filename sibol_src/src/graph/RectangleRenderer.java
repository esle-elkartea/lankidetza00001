package graph;

import funciones.Utilidades;
import java.awt.BasicStroke;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import java.util.StringTokenizer;
import org.jgraph.graph.GraphConstants;

public class RectangleRenderer extends VertexRenderer {
    private VertexView rectangleView = null;
    
    public RectangleRenderer(VertexView v) {
        this.rectangleView = v;
    }
    
    public void paint(Graphics g) {
        // fijamos el tipo de letra
        Utilidades.tipoLetra(g);
        
        int b = borderWidth;
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = getSize();
        // línea discontinua
        float dash[] = {10.0f};
        BasicStroke strokeDash = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
            
        if(bordercolor != null) {
            g.setColor(bordercolor);
            g2.setStroke(strokeDash);
            g.drawRect(0, 0, d.width - b, d.height - b);
        }
        if(selected) {
            g2.setStroke(GraphConstants.SELECTION_STROKE);
            g.setColor(highlightColor);
            g.drawRect(0, 0, d.width - b, d.height - b);
        }
        
        // dibujamos el texto de la etiqueta
        Shape clip = g.getClip();
        g.setClip(null);
        g.setColor(Color.BLACK);
        String s = ((getText() == null) ? "" : getText());
        drawString(g, s, 0, -4);
        g.setClip(clip);
    }
    
    private void drawString(Graphics g, String s, int textX, int textY) {
        if(s.indexOf('\n') == -1) g.drawString(s, textX, textY);
        else {
            String[] strs = splitStringByLines(s);
            int height = g.getFontMetrics().getHeight();
            g.drawString(strs[0], textX, textY);
            for(int i = 1; i < strs.length; i++)
                if(strs[i]!=null) g.drawString(strs[i], textX, textY + (height * i));
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
