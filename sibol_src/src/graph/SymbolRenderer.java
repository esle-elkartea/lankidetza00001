package graph;

import com.kitfox.svg.app.beans.SVGIcon;
import funciones.Utilidades;
import graph.beans.BeanI;
import java.awt.BasicStroke;
import main.Constantes;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

import java.util.StringTokenizer;

public class SymbolRenderer extends VertexRenderer {
    private VertexView symbolView = null;
    private String svgResource = null;

    public SymbolRenderer(VertexView v, String svg) {
        this.symbolView = v;
        this.svgResource = svg;
    }
   
    public void paint(Graphics g) {
        // fijamos el tipo de letra
        Utilidades.tipoLetra(g);
        
        Graphics2D g2 = (Graphics2D)g;
        float[] dash = {5.0f};
        BasicStroke dashed = new BasicStroke(borderWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, dash, 0.0f);

        // comprobamos si ha sido editado
        boolean editado = false;
        DefaultGraphCell dgc = (DefaultGraphCell)this.symbolView.getCell();
        if(dgc.getUserObject() instanceof BeanI) {
            BeanI bi = (BeanI)dgc.getUserObject();
            editado = bi.getEditado();
        }

        String rutaSVG = "/resources/svg/" + svgResource + (editado ? Constantes.SVG_EDITADO : Constantes.SVG_NOEDITADO) + ".svg";
        //Mensaje.info("rutaSVG: " + rutaSVG, false);
        
        SVGIcon icon = new SVGIcon();
        icon.setSvgResourcePath(rutaSVG);
        //icon.setPreferredSize(new Dimension(Constantes.CELL_WIDTH, Constantes.CELL_HEIGHT));
        //icon.setScaleToFit(true);
        icon.setAntiAlias(true);
        icon.paintIcon(null, g, 0, 0);
        
        if(selected) {
            Color color0 = g.getColor();
            Stroke stroke0 = g2.getStroke();
            g.setColor(highlightColor);
            g2.setStroke(dashed);
            g.drawRect(0, 0, getWidth() - borderWidth, getHeight() - borderWidth);
            g.setColor(color0);
            g2.setStroke(stroke0);
        }
        
        // dibujamos el texto de la etiqueta
        Dimension d = getSize();
        Shape clip = g.getClip();
        g.setClip(null);
        g.setColor(Color.BLACK);
        String s = ((getText() == null) ? "" : getText());
        drawString(g, s, (int)d.getWidth() + Constantes.LABEL_POSX, Constantes.LABEL_POSY);
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
