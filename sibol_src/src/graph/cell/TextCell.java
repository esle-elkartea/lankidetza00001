package graph.cell;

import org.jgraph.graph.DefaultGraphCell;

public class TextCell extends DefaultGraphCell {
    private boolean multiLined = true;
    
    public TextCell() {
        this(null);
    }
    
    public TextCell(Object userObject) {
        this(userObject, true);
    }
    
    public TextCell(Object userObject, boolean multiLined) {
        super(userObject);
        this.multiLined = multiLined;
    }
        
    public boolean isMultiLined() {
        return multiLined;
    }
}
