/*
 * MyGraphModel.java
 *
 * Created on 27 de septiembre de 2007, 13:25
 *
 */

package graph;

import graph.beans.BeanI;
import java.util.List;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultGraphModel;

/**
 * @author sanjose
 */
public class MyGraphModel extends DefaultGraphModel {
    
    public MyGraphModel() {
        super();
    }
    
    // requerido para serialización
    public MyGraphModel(List roots, AttributeMap attributes) {
        super(roots, attributes);
    }
    
    // requerido para serialización
    public MyGraphModel(List roots, AttributeMap attributes, ConnectionSet cs) {
        super(roots, attributes, cs);
    }
    
    protected Object cloneUserObject(Object userObject) {
        if(userObject instanceof BeanI) return ((BeanI) userObject).clone();
        return super.cloneUserObject(userObject);
    }
}
