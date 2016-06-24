package graph.cell;

import java.awt.Rectangle;
import main.Constantes;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

/**
 * FantasmaCell
 */
public class FantasmaImageCell extends DefaultGraphCell {
    
    private double escala;
    
    public FantasmaImageCell() {
        this.escala = 1.0;
        initFantasma();
    }
    
    public FantasmaImageCell(double escala) {
        this.escala = escala;
        initFantasma();
    }
    
    private void initFantasma() {
        Rectangle r = escalarPuntos(Constantes.TAMPAG_WIDTH, Constantes.TAMPAG_HEIGHT, 0, 0);
        GraphConstants.setBounds(this.getAttributes(), this.getAttributes().createRect(0,0,(int)r.getX(), (int)r.getY()));
        
        // no se puede redimensionar, ni mover, ni seleccionar
        GraphConstants.setSizeable(this.getAttributes(), false);
        GraphConstants.setMoveable(this.getAttributes(), false);
        GraphConstants.setSelectable(this.getAttributes(), false);        
    }
    
    private Rectangle escalarPuntos(int x, int y, int ancho, int alto) {
        // aplicamos la escala
        double x1 = escalarPunto(x);
        double y1 = escalarPunto(y);
        double ancho1 = escalarPunto(ancho);
        double alto1 = escalarPunto(alto);
        
        // ajustamos al grid
        x1 = x1 / (double)Constantes.GRID_SIZE;
        x1 = Math.rint(x1) * (double)Constantes.GRID_SIZE;;
        y1 = y1 / (double)Constantes.GRID_SIZE;
        y1 = Math.rint(y1) * (double)Constantes.GRID_SIZE;;
        
        return(new Rectangle((int)x1, (int)y1, (int)ancho1, (int)alto1));
    }
    
    // escalar los puntos y dimensiones del elemento a insertar en el gráfico
    // en función de la escala actual
    // además ajustamos al "grid"
    private double escalarPunto(int p) {
        double p1 = (double)p / (double)escala;
        return p1;
    }
    
}
