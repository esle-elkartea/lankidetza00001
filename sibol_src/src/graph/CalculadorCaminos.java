/*
 * CalculadorCaminos.java
 *
 * Created on 13 de septiembre de 2006, 9:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package graph;

import errores.ElementoFinalNoSalidaException;
import errores.PartesDesconectadasException;
import errores.TieneBuclesException;
import graph.cell.CajetinSCell;
import graph.cell.FantasmaCell;
import graph.cell.FantasmaImageCell;
import graph.cell.MyEdge;
import graph.cell.RectangleCell;
import graph.cell.SymbolAcomCell;
import graph.cell.SymbolContactoACell;
import graph.cell.SymbolContactoCCell;
import graph.cell.SymbolEQ1Cell;
import graph.cell.SymbolEQ2Cell;
import graph.cell.SymbolFTCell;
import graph.cell.SymbolGRDCell;
import graph.cell.SymbolKA2Cell;
import graph.cell.SymbolKACell;
import graph.cell.SymbolRJCell;
import graph.cell.SymbolS1Cell;
import graph.cell.SymbolS2Cell;
import graph.cell.SymbolSchucoCell;
import graph.cell.SymbolVACell;
import graph.cell.SymbolVoltCell;
import graph.cell.SymbolZetacCell;
import graph.cell.TextCell;
import graph.view.CajetinSView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.Port;

/**
 *
 * @author oscar
 */
public class CalculadorCaminos {
    
    private GraphModel model = null;
    private List<List> caminosEncontrados = null;
    private List caminoActual = null;
    private Port ultimoPort = null;
    
    /**
     * Creates a new instance of CalculadorCaminos
     */
    public CalculadorCaminos(GraphModel model) {
        this.model = model;
    }
    
    public List getCaminos() throws PartesDesconectadasException,ElementoFinalNoSalidaException,TieneBuclesException{
        caminoActual = new ArrayList();
        SymbolAcomCell entrada = getElementoEntrada();
//System.out.println("Añadido el primer elemento: "+entrada.toString());
        caminoActual.add(entrada);
        caminosEncontrados = null;
        ultimoPort = null;
        resolverCaminos();
        if(!todosLosElementoEnAlMenosUnCamino()) throw new PartesDesconectadasException();
        return caminosEncontrados;
    }
    
    private void resolverCaminos() throws ElementoFinalNoSalidaException,TieneBuclesException{
        Object obj = caminoActual.get(caminoActual.size()-1);
        if(esUnElementoSalida(obj)){
            if(caminosEncontrados==null) caminosEncontrados = new ArrayList();
            agregarCaminoEncontrado();
//System.out.println("Encontrado un camino");
        } else if(obj instanceof DefaultEdge){
//System.out.println("DEFAULTEDGE");
            DefaultEdge edge = (DefaultEdge) obj;
            Port port1 = (Port)edge.getSource();
            Port port2 = (Port)edge.getTarget();
            Port portAnterior = ultimoPort;
            if(port1==ultimoPort) ultimoPort = port2;
            else ultimoPort = port1;
            Iterator it = ultimoPort.edges();
            while (it.hasNext()) {
                DefaultEdge edgeSalida = (DefaultEdge)it.next();
                if(edge!=edgeSalida){
                    if(caminoActual.contains(edgeSalida)) throw new TieneBuclesException();
                    caminoActual.add(edgeSalida);
//System.out.println("- Añadida una línea: "+(String)edgeSalida.getUserObject());
                    resolverCaminos();
                    caminoActual.remove(edgeSalida);
//System.out.println("- Borrada una línea: "+(String)edgeSalida.getUserObject());
                }
            }
            DefaultGraphCell padre = (DefaultGraphCell) this.model.getParent(ultimoPort);
             if(caminoActual.contains(padre)) throw new TieneBuclesException();
            caminoActual.add(padre);
//System.out.println("Añadido un elemento: "+padre.toString());
            resolverCaminos();
            caminoActual.remove(padre);
//System.out.println("Borrado un elemento: "+padre.toString());
            ultimoPort = portAnterior;
        } else if(obj instanceof DefaultGraphCell){
//System.out.println("DEFAULTGRAPHCELL");
            DefaultGraphCell cell = (DefaultGraphCell) obj;
            Port portAnterior = ultimoPort;
            boolean esElementoFinal = true;
            for(int i=0;i<cell.getChildCount();i++){
                Object obj2 = cell.getChildAt(i);
//System.out.println("CELL CHILD "+i+" "+obj2.hashCode());
                if(obj2 instanceof Port){
                    ultimoPort = (Port) obj2;
                    if(portAnterior!=ultimoPort){
                        Iterator it = ultimoPort.edges();
                        while (it.hasNext()) {
                            DefaultEdge edge = (DefaultEdge)it.next();
                            if(caminoActual.contains(edge)) throw new TieneBuclesException();
                            caminoActual.add(edge);
                            esElementoFinal = false;
//System.out.println("* Añadida una línea: "+(String)edge.getUserObject());
                            resolverCaminos();
                            caminoActual.remove(edge);
//System.out.println("* Borrada una línea: "+(String)edge.getUserObject());
                        }
                    }
                }
            }
            // Dado que las salidas entran por otra condicion del if sabemos que el elemento
            // no es una salida. Si en esta condicion vemos que se trata de un elemento final,
            // como ademas no es una salida, hemos encontrado un error
            if(esElementoFinal && !esElementoFinalIgnorable(obj)) throw new ElementoFinalNoSalidaException();
            Port _ultimoPort = portAnterior;
        }
    }
    
    private SymbolAcomCell getElementoEntrada(){
        SymbolAcomCell resultado = null;
        for(int i=0;i<this.model.getRootCount();i++){
            Object obj = this.model.getRootAt(i);
            if(obj instanceof SymbolAcomCell){
                resultado = (SymbolAcomCell) obj;
                break;
            }
        }
        return resultado;
    }
    
    private void agregarCaminoEncontrado(){
        ArrayList camino = new ArrayList();
        camino.addAll(caminoActual);
        caminosEncontrados.add(camino);
    }
    
    private boolean esUnElementoSalida(Object obj){
        return (obj instanceof SymbolS1Cell ||
                obj instanceof SymbolS2Cell);
    }
    
    private boolean esElementoFinalIgnorable(Object obj){
        return (obj instanceof SymbolGRDCell ||
                obj instanceof SymbolVoltCell ||
                obj instanceof SymbolEQ1Cell ||
                obj instanceof SymbolEQ2Cell ||
                obj instanceof SymbolSchucoCell ||
                obj instanceof SymbolZetacCell);
    }
    
    private boolean esElementoIgnorable(Object obj){
        return (obj instanceof MyEdge ||
                obj instanceof SymbolContactoACell ||
                obj instanceof SymbolContactoCCell ||
                obj instanceof SymbolFTCell ||
                obj instanceof SymbolKACell ||
                obj instanceof SymbolKA2Cell ||
                obj instanceof SymbolRJCell ||
                obj instanceof SymbolVACell ||
                obj instanceof SymbolGRDCell ||
                obj instanceof SymbolVoltCell ||
                obj instanceof SymbolEQ1Cell ||
                obj instanceof SymbolEQ2Cell ||
                obj instanceof SymbolSchucoCell ||
                obj instanceof SymbolZetacCell ||
                obj instanceof RectangleCell ||
                obj instanceof TextCell ||
                obj instanceof CajetinSCell ||
                obj instanceof FantasmaCell ||
                obj instanceof FantasmaImageCell);
    }
    
    private boolean todosLosElementoEnAlMenosUnCamino(){
        boolean resultado = true;
        if(caminosEncontrados!=null){
            for(int i=0;i<this.model.getRootCount();i++){
                Object obj = this.model.getRootAt(i);
                if(!esElementoIgnorable(obj)){
                    boolean encontrado = false;
                    for(int j=0;j<caminosEncontrados.size();j++){
                        List camino = (List) caminosEncontrados.get(j);
                        if(camino.contains(obj)){
                            encontrado = true;
                            break;
                        }
                    }
                    if(!encontrado) {
                        resultado = false;
                        break;
                    }
                }
            }
        }
        return resultado;
    }
    
}
