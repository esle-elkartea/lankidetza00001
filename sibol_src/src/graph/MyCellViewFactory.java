package graph;

import graph.cell.CajetinSCell;
import graph.cell.RectangleCell;
import graph.cell.SymbolAcomCell;
import graph.cell.SymbolCC2Cell;
import graph.cell.SymbolCCCell;
import graph.cell.SymbolCGPCell;
import graph.cell.SymbolCTCell;
import graph.cell.SymbolContactoACell;
import graph.cell.SymbolContactoCCell;
import graph.cell.SymbolD1Cell;
import graph.cell.SymbolD2Cell;
import graph.cell.SymbolEQ1Cell;
import graph.cell.SymbolEQ2Cell;
import graph.cell.SymbolFTCell;
import graph.cell.SymbolFUS2Cell;
import graph.cell.SymbolFUSCell;
import graph.cell.SymbolGRDCell;
import graph.cell.SymbolICCCell;
import graph.cell.SymbolICPCell;
import graph.cell.SymbolIG2Cell;
import graph.cell.SymbolIGCell;
import graph.cell.SymbolKA2Cell;
import graph.cell.SymbolKACell;
import graph.cell.SymbolM1Cell;
import graph.cell.SymbolRDCell;
import graph.cell.SymbolRJCell;
import graph.cell.SymbolS1Cell;
import graph.cell.SymbolS2Cell;
import graph.cell.SymbolSchucoCell;
import graph.cell.SymbolVACell;
import graph.cell.SymbolVoltCell;
import graph.cell.SymbolZetacCell;
import graph.cell.TextCell;
import graph.view.CajetinSView;
import graph.view.MyEdgeView;
import graph.view.RectangleView;
import graph.view.SymbolAcomView;
import graph.view.SymbolCC2View;
import graph.view.SymbolCCView;
import graph.view.SymbolCGPView;
import graph.view.SymbolCTView;
import graph.view.SymbolContactoAView;
import graph.view.SymbolContactoCView;
import graph.view.SymbolD1View;
import graph.view.SymbolD2View;
import graph.view.SymbolEQ1View;
import graph.view.SymbolEQ2View;
import graph.view.SymbolFTView;
import graph.view.SymbolFUS2View;
import graph.view.SymbolFUSView;
import graph.view.SymbolGRDView;
import graph.view.SymbolICCView;
import graph.view.SymbolICPView;
import graph.view.SymbolIG2View;
import graph.view.SymbolIGView;
import graph.view.SymbolKA2View;
import graph.view.SymbolKAView;
import graph.view.SymbolM1View;
import graph.view.SymbolRDView;
import graph.view.SymbolRJView;
import graph.view.SymbolS1View;
import graph.view.SymbolS2View;
import graph.view.SymbolSchucoView;
import graph.view.SymbolVAView;
import graph.view.SymbolVoltView;
import graph.view.SymbolZetacView;
import graph.view.TextView;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.VertexView;
import java.util.ArrayList;
import org.jgraph.graph.EdgeView;

/**
 * Factoría de 'views' para JGraph. Esta factoría asocia una clase de celda
 * a una vista.
 */
public class MyCellViewFactory extends DefaultCellViewFactory {
    private ArrayList viewIndirections;
    
    public MyCellViewFactory() {
        ArrayList v = new ArrayList();
        v.add(new ViewIndirection(DefaultGraphCell.class, VertexView.class));
        v.add(new ViewIndirection(RectangleCell.class, RectangleView.class));
        v.add(new ViewIndirection(TextCell.class, TextView.class));
        v.add(new ViewIndirection(CajetinSCell.class, CajetinSView.class));
        v.add(new ViewIndirection(SymbolCGPCell.class, SymbolCGPView.class));
        v.add(new ViewIndirection(SymbolFUSCell.class, SymbolFUSView.class));
        v.add(new ViewIndirection(SymbolFUS2Cell.class, SymbolFUS2View.class));
        v.add(new ViewIndirection(SymbolCCCell.class, SymbolCCView.class));
        v.add(new ViewIndirection(SymbolCC2Cell.class, SymbolCC2View.class));
        v.add(new ViewIndirection(SymbolICPCell.class, SymbolICPView.class));
        v.add(new ViewIndirection(SymbolIGCell.class, SymbolIGView.class));
        v.add(new ViewIndirection(SymbolIG2Cell.class, SymbolIG2View.class));
        v.add(new ViewIndirection(SymbolRDCell.class, SymbolRDView.class));
        v.add(new ViewIndirection(SymbolM1Cell.class, SymbolM1View.class));
        v.add(new ViewIndirection(SymbolD1Cell.class, SymbolD1View.class));
        v.add(new ViewIndirection(SymbolD2Cell.class, SymbolD2View.class));
        v.add(new ViewIndirection(SymbolCTCell.class, SymbolCTView.class));
        v.add(new ViewIndirection(SymbolICCCell.class, SymbolICCView.class));
        v.add(new ViewIndirection(SymbolGRDCell.class, SymbolGRDView.class));
        v.add(new ViewIndirection(SymbolS1Cell.class, SymbolS1View.class));
        v.add(new ViewIndirection(SymbolS2Cell.class, SymbolS2View.class));
        v.add(new ViewIndirection(SymbolAcomCell.class, SymbolAcomView.class));
        v.add(new ViewIndirection(SymbolContactoACell.class, SymbolContactoAView.class));
        v.add(new ViewIndirection(SymbolContactoCCell.class, SymbolContactoCView.class));
        v.add(new ViewIndirection(SymbolEQ1Cell.class, SymbolEQ1View.class));
        v.add(new ViewIndirection(SymbolEQ2Cell.class, SymbolEQ2View.class));
        v.add(new ViewIndirection(SymbolFTCell.class, SymbolFTView.class));
        v.add(new ViewIndirection(SymbolKACell.class, SymbolKAView.class));
        v.add(new ViewIndirection(SymbolKA2Cell.class, SymbolKA2View.class));
        v.add(new ViewIndirection(SymbolRJCell.class, SymbolRJView.class));
        v.add(new ViewIndirection(SymbolSchucoCell.class, SymbolSchucoView.class));
        v.add(new ViewIndirection(SymbolVACell.class, SymbolVAView.class));
        v.add(new ViewIndirection(SymbolVoltCell.class, SymbolVoltView.class));
        v.add(new ViewIndirection(SymbolZetacCell.class, SymbolZetacView.class));
        setViewIndirections(v);
    }
      
    protected VertexView createVertexView(Object v) {
        VertexView view = null;
        try {
            for(int i = 0; i < viewIndirections.size(); i++) {
                ViewIndirection indirection = (ViewIndirection)viewIndirections.get(i);
                if(v.getClass() == indirection.getCellClass()) {
                    view = (VertexView)indirection.getViewClass().newInstance();
                    view.setCell(v);
                    return view;
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return super.createVertexView(v);
    }
    
    protected EdgeView createEdgeView(Object cell) {
	return new MyEdgeView(cell);
    }
    
    /* JSJ: eliminado porque no serializa bien con XMLEncoder
    protected EdgeView createEdgeView(Object cell) {
        return new EdgeView(cell) {
            public CellHandle getHandle(GraphContext context) {
                return new MyEdgeHandle(this, context);
            }
        };
    }
    */
    
    public ArrayList getViewIndirections() {
        return viewIndirections;
    }
    
    public void setViewIndirections(ArrayList viewIndirections) {
        this.viewIndirections = viewIndirections;
    }
    
    public static class ViewIndirection {
        private Class cellClass;
        private Class viewClass;

        public ViewIndirection(Class cellClass, Class viewClass) {
            this.cellClass = cellClass;
            this.viewClass = viewClass;
        }

        public Class getCellClass() {
            return cellClass;
        }

        public Class getViewClass() {
            return viewClass;
        }

        public void setCellClass(Class cellClass) {
            this.cellClass = cellClass;
        }

        public void setViewClass(Class viewClass) {
            this.viewClass = viewClass;
        }
    }

}
