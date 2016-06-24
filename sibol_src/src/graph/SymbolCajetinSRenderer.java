package graph;

import funciones.Utilidades;
import main.Constantes;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;

import java.util.StringTokenizer;

public class SymbolCajetinSRenderer extends VertexRenderer {
    private VertexView symbolView = null;

    public SymbolCajetinSRenderer(VertexView v) {
        this.symbolView = v;
    }
   
    public void paint(Graphics g) {
        // fijamos el tipo de letra
        Utilidades.tipoLetra(g);
        
        // dibujamos el cajetin
        String txtCajetin = "REF.\nUTILIZACIÓN\nNº ELEM.\nPOTENCIA (W)\nTENSIÓN (V)\nINTENSIDAD (A)\nSECCIÓN (mm2)\nLONGITUD (m)";
        
        Dimension d = getSize();
        Shape clip = g.getClip();
        g.setClip(null);
        g.setColor(Color.BLACK);
        int xTexto = Constantes.CAJETIN_POSX;
        int yTexto = Constantes.CAJETIN_POSY;
        
        int anchuraCuadro = (int)d.getWidth();
        int alturaCuadro = (int)d.getHeight();
        int alturaFila = alturaCuadro / Constantes.NUMFILAS_SALIDA;
        int xCuadro = xTexto - 6;
        int yCuadro = yTexto - 12;

        g.drawRect(xCuadro, yCuadro, anchuraCuadro, alturaCuadro);
        for(int i = 0; i < 8; i++) {
            int yLineaCuadro = yCuadro + (i * alturaFila);
            g.drawLine(xCuadro, yLineaCuadro, xCuadro + anchuraCuadro, yLineaCuadro);
        }
        
        Font f = g.getFont();
        Font f2 = f.deriveFont(Font.BOLD);
        g.setFont(f2);
        
        drawString(g, txtCajetin, xTexto, yTexto);
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
