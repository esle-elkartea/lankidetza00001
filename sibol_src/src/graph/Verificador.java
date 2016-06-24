/*
 * Verificador.java
 *
 * Created on 11 de septiembre de 2006, 16:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package graph;

import errores.ElementoFinalNoSalidaException;
import errores.PartesDesconectadasException;
import errores.TieneBuclesException;
import graph.beans.EdgeBean;
import graph.beans.SBean;
import graph.cell.CajetinSCell;
import graph.cell.RectangleCell;
import graph.cell.SymbolAcomCell;
import graph.cell.SymbolCC2Cell;
import graph.cell.SymbolCCCell;
import graph.cell.SymbolCGPCell;
import graph.cell.SymbolCTCell;
import graph.cell.SymbolD1Cell;
import graph.cell.SymbolD2Cell;
import graph.cell.SymbolFUS2Cell;
import graph.cell.SymbolFUSCell;
import graph.cell.SymbolGRDCell;
import graph.cell.SymbolICCCell;
import graph.cell.SymbolICPCell;
import graph.cell.SymbolIG2Cell;
import graph.cell.SymbolIGCell;
import graph.cell.SymbolM1Cell;
import graph.cell.SymbolRDCell;
import graph.cell.SymbolS1Cell;
import graph.cell.SymbolS2Cell;
import graph.cell.SymbolSchucoCell;
import graph.cell.TextCell;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.swing.tree.TreeNode;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.Port;

/**
 *
 * @author oscar
 */
public class Verificador {
    private String mensajes = null;
    private String warning = null;
    private boolean esVivienda = true;
    private boolean esReforma = false;
    
    private static ResourceBundle bundle = ResourceBundle.getBundle("resources/config/config");
    
    /** Creates a new instance of Verificador */
    public Verificador(boolean esVivienda, boolean esReforma) {
        this.esVivienda = esVivienda;
        this.esReforma = esReforma;
    }
    
    public boolean esVacio(GraphModel model){
        return (model.getRootCount()==0);
    }
       
    public boolean cumpleRestricciones(GraphModel model) {
        boolean resultado = true;
        mensajes = null;
        boolean r0 = verificarElementosObligatorios(model);
        // desactivamos esta restricción para pemitir que se puedan crear más de una Derivación Individual con su propio ICP, etc...
        //boolean r1 = verificarElementosUnicos(model);
        boolean r1 = true;
        boolean r2 = verificarSinElementosSueltos(model);
        resultado = r0 && r1 && r2;
        if(resultado){
            // Calcular caminos        
            CalculadorCaminos obtenedor = new CalculadorCaminos(model);
            List caminos = null;
            resultado = false;
            try{
                caminos = obtenedor.getCaminos();
                if(caminos!=null && caminos.size()>0)resultado = true;
                if(resultado==false){
                    if(mensajes==null) mensajes = "";
                    mensajes = mensajes + bundle.getString("AVISO_SIN_CAMINOS")+"\n";
                }
            } catch (PartesDesconectadasException e){
                if(mensajes==null) mensajes = "";
                mensajes = mensajes + bundle.getString("AVISO_RESTRICCION_PARTES_DESCONECTADAS")+"\n";
            } catch (ElementoFinalNoSalidaException e){
                if(mensajes==null) mensajes = "";
                mensajes = mensajes + bundle.getString("AVISO_RESTRICCION_ELEMENTOS_FINALES_SALIDAS")+"\n";
            } catch (TieneBuclesException e){
                if(mensajes==null) mensajes = "";
                mensajes = mensajes + bundle.getString("AVISO_RESTRICCION_TIENE_BUCLES_EXCEPTION")+"\n";
            }
            if(resultado){
                boolean r3 = true;  //verificarHastaIGSinDerivaciones(caminos);
                boolean r4 = verificarCadaLineaSalidaUnM1(caminos);
                boolean r5 = true; //verificarIGTrasICP(caminos);
                boolean r6 = verificarCadaCincoCircuitosUnDiferencial(caminos);
                boolean r7 = verificarSiViviendaPresenciaObligatoriaCircuitos(model);
                boolean r8 = true; //verificarHastaIGLosDosTiposDeLineas(caminos);
                resultado = r3 && r4 && r5 && r6 && r7 && r8;
            }
        }
        return resultado;
    }
    
    public String getMensajes(){
        return mensajes;
    }
    
    public String getWarning(){
        return warning;
    }
    
    private boolean verificarElementosObligatorios(GraphModel model){
        boolean resultado = true;
        int contadorGRD = 0;
        for(int i=0;i<model.getRootCount();i++){
            Object obj = model.getRootAt(i);
            if(obj instanceof SymbolGRDCell) contadorGRD++;
        }
        if(contadorGRD==0) {
            resultado = false;
            if(mensajes==null) mensajes = "";
            mensajes = mensajes + bundle.getString("AVISO_RESTRICCION_ELEMENTOS_OBLIGATORIOS")+"\n";
        }
        return resultado;
    }
    
    private boolean verificarElementosUnicos(GraphModel model){
        boolean resultado = true;
        int contadorAcom = 0;
        int contadorCGP = 0;
        int contadorICC = 0;
        int contadorCC = 0;
        int contadorICP = 0;
        int contadorIG = 0;
        int contadorGRD = 0;
        for(int i=0;i<model.getRootCount();i++){
            Object obj = model.getRootAt(i);
            if(obj instanceof SymbolCGPCell) contadorCGP++;
            else if(obj instanceof SymbolICCCell) contadorICC++;
            else if(obj instanceof SymbolCCCell) contadorCC++;
            else if(obj instanceof SymbolICPCell) contadorICP++;
            else if(obj instanceof SymbolIGCell) contadorIG++;
            else if(obj instanceof SymbolIG2Cell) contadorIG++;
            else if(obj instanceof SymbolAcomCell) contadorAcom++;
            else if(obj instanceof SymbolGRDCell) contadorGRD++;
        }
        if(contadorCGP>1 || contadorICC>1 || contadorCC>1 || contadorICP>1 || contadorIG>1 || contadorAcom>1 || contadorGRD>1) {
            resultado = false;
            if(mensajes==null) mensajes = "";
            mensajes = mensajes + bundle.getString("AVISO_RESTRICCION_ELEMENTOS_UNICOS")+"\n";
        }
        return resultado;
    }
    
    private boolean verificarIGTrasICP(List caminos){
        boolean resultado = true;
        // Dado que hasta el IG todos los caminos deben tener esa parte en comun
        // basta con mirarlo en el primer camino
        List primerCamino = (List)caminos.get(0);
        int indice=-1;
        Object obj = null;
        for(int i=0;i<primerCamino.size();i++){
            obj = primerCamino.get(i);
            if(obj instanceof SymbolICPCell){
                indice = i;
                break;
            }
        }        
        try{
            if(indice>-1){
                Object obj1 = primerCamino.get(indice+1);
                Object obj2 = primerCamino.get(indice+2);
                if(!(obj1 instanceof DefaultEdge && (obj2 instanceof SymbolIGCell || obj2 instanceof SymbolIG2Cell))) resultado = false;
            }
        } catch (IndexOutOfBoundsException e) {
            resultado = false;
        }
        if(resultado==false){
            if(mensajes==null) mensajes = "";
            mensajes = mensajes + bundle.getString("AVISO_RESTRICCION_IG_TRAS_ICP")+"\n";
        }
        return resultado;
    }
    
    private boolean verificarHastaIGSinDerivaciones(List caminos){
        boolean resultado = true;
        List primerCamino = (List)caminos.get(0);
        for(int j=1;j<caminos.size();j++){
            List otroCamino = (List)caminos.get(j);
            for(int i=0;i<primerCamino.size();i++){
                Object obj = primerCamino.get(i);
                Object otroObj = null;
                try{
                    otroObj = otroCamino.get(i);
                } catch(IndexOutOfBoundsException e){
                    resultado = false;
                    break;
                }
                if(obj!=otroObj){
                    resultado = false;
                    break;
                }
                if(obj instanceof SymbolIGCell | obj instanceof SymbolIG2Cell) break;
            }
            if(resultado==false) break;
        }
        if(resultado==false){
            if(mensajes==null) mensajes = "";
            mensajes = mensajes + bundle.getString("AVISO_RESTRICCION_HASTA_IG_SIN_DERIVACIONES")+"\n";
        }
        return resultado;
    }
      
    private boolean verificarCadaLineaSalidaUnM1(List caminos){
        boolean resultado = true;
        for(int i=0;i<caminos.size();i++){
            List camino = (List) caminos.get(i);
            boolean encontrado = false;
            for(int j=0;j<camino.size();j++){
                Object obj = camino.get(j);
                if(obj instanceof SymbolM1Cell){
                    encontrado = true;
                    break;
                }
            }
            if(!encontrado){
                resultado = false;
                break;
            }
        }
        if(resultado==false){
            if(mensajes==null) mensajes = "";
            mensajes = mensajes + bundle.getString("AVISO_RESTRICCION_AL_MENOS_UN_M1")+"\n";
        }
        return resultado;
    }
    
    private boolean verificarSinElementosSueltos(GraphModel model){
        boolean resultado = true;
        for(int i=0;i<model.getRootCount();i++){
            Object obj = model.getRootAt(i);
            if(esElemento(obj)){                
                DefaultGraphCell cell = (DefaultGraphCell) obj;
                for(int j=0;j<cell.getChildCount();j++){
                    if(model.isPort(cell.getChildAt(j))){
                        Port port = (Port)cell.getChildAt(j);
                        if(!port.edges().hasNext()){
                            if (!esTierra(obj)) {
                                resultado = false;
                                break;
//                            } else {
//                                if(warning==null) warning = "";
//                                warning = warning + bundle.getString("AVISO_WARNING_TIERRA")+"\n";
                            }
                        }
                    }
                }
                if(!resultado) break;
                
            }
        }
        if(resultado==false) {
            if(mensajes==null) mensajes = "";
            mensajes = mensajes + bundle.getString("AVISO_RESTRICCION_ELEMENTOS_SUELTOS")+"\n";
        }
        return resultado;
    }
       
    private boolean verificarCadaCincoCircuitosUnDiferencial(List caminos){
        boolean resultado = true;
        if(!this.esReforma) {
            ArrayList<SymbolM1Cell> elementosEncontrados = new ArrayList<SymbolM1Cell>();
            ArrayList<int[]> contadores = new ArrayList<int[]>();
            for(int i=0;i<caminos.size();i++){
                List camino = (List)caminos.get(i);
                DefaultGraphCell salida = (DefaultGraphCell) camino.get(camino.size()-1);
                SBean sBean = (SBean)salida.getUserObject();
                String referenciaSalida = sBean.getReferencia();
                for(int j=camino.size()-2;j>=0;j--){
                    Object obj = camino.get(j);
                    if(obj instanceof SymbolM1Cell){
                        SymbolM1Cell cellM1 = (SymbolM1Cell) obj;
                        if(elementosEncontrados.contains(cellM1)){
                            int indice = elementosEncontrados.indexOf(cellM1);
                            int[] contadoresElemento = (int[])contadores.get(indice);
                            if("C1".equalsIgnoreCase(referenciaSalida)) contadoresElemento[0]++;
                            else if("C2".equalsIgnoreCase(referenciaSalida)) contadoresElemento[1]++;
                            else if("C3".equalsIgnoreCase(referenciaSalida)) contadoresElemento[2]++;
                            else if("C4".equalsIgnoreCase(referenciaSalida)) contadoresElemento[3]++;
                            else if(Pattern.matches("[C,c]4(.[1-5])?",referenciaSalida)) if(contadoresElemento[3]==0)contadoresElemento[3]++;
                            else if("C5".equalsIgnoreCase(referenciaSalida)) contadoresElemento[4]++;
                            else contadoresElemento[5]++;
                        } else {
                            elementosEncontrados.add(cellM1);
                            int[] contadoresElemento = new int[6];
                            if("C1".equalsIgnoreCase(referenciaSalida)) contadoresElemento[0]++;
                            else if("C2".equalsIgnoreCase(referenciaSalida)) contadoresElemento[1]++;
                            else if("C3".equalsIgnoreCase(referenciaSalida)) contadoresElemento[2]++;
                            else if("C4".equalsIgnoreCase(referenciaSalida)) contadoresElemento[3]++;
                            else if(Pattern.matches("[C,c]4(.[1-5])?",referenciaSalida)) if(contadoresElemento[3]==0)contadoresElemento[3]++;
                            else if("C5".equalsIgnoreCase(referenciaSalida)) contadoresElemento[4]++;
                            else contadoresElemento[5]++;
                            contadores.add(contadoresElemento);
                        }
                        break;
                    }
                }
            }
            for(int i=0;i<contadores.size();i++){
                int[] contador = contadores.get(i);
                int suma = 0;
                for(int j=0;j<contador.length;j++){
                    suma = suma + contador[j];
                }
                if(suma>5){
                    resultado = false;
                    break;
                }
            }
            if(resultado==false) {
                if(mensajes==null) mensajes = "";
                mensajes = mensajes + bundle.getString("AVISO_RESTRICCION_CINCO_CIRCUITOS_UN_DIFERENCIAL")+"\n";
            }
        }
        return resultado;
    }
    
    private boolean verificarSiViviendaPresenciaObligatoriaCircuitos(GraphModel model){
        boolean resultado = true;
        if(this.esVivienda && !this.esReforma){
            boolean c1 = false;
            boolean c2 = false;
            boolean c3 = false;
            boolean c4 = false;
            boolean c5 = false;
            for(int i=0;i<model.getRootCount();i++){
                Object obj = model.getRootAt(i);
                if(esSalida(obj)){
                    DefaultGraphCell cell = (DefaultGraphCell) obj;
                    SBean sBean = (SBean)cell.getUserObject();
                    if(sBean.getReferencia()!=null){
                        if("C1".equalsIgnoreCase(sBean.getReferencia())) c1 = true;
                        else if("C2".equalsIgnoreCase(sBean.getReferencia())) c2 = true;
                        else if("C3".equalsIgnoreCase(sBean.getReferencia())) c3 = true;
                        else if(Pattern.matches("[C,c]4(.[1-5])?", sBean.getReferencia())) c4 = true;
                        else if("C5".equalsIgnoreCase(sBean.getReferencia())) c5 = true;
                    }
                }
            }
            resultado = (c1 && c2 && c3 && c4 && c5);
        }
        if(resultado==false) {
            if(mensajes==null) mensajes = "";
            mensajes = mensajes + bundle.getString("AVISO_RESTRICCION_SALIDAS_REQUERIDAS_SI_VIVIENDA")+"\n";
        }
        return resultado;
    }
    
    private boolean verificarHastaIGLosDosTiposDeLineas(List caminos){
        boolean resultado = true;
        boolean la = false;
        boolean di = false;
        // Dado que hasta el IG todos los caminos deben tener esa parte en comun
        // basta con mirarlo en el primer camino
        List primerCamino = (List)caminos.get(0);
        int indice=-1;
        Object obj = null;
        for(int i=0;i<primerCamino.size();i++){
            obj = primerCamino.get(i);
            if(obj instanceof DefaultEdge){
                DefaultEdge edge = (DefaultEdge) obj;
                if(edge.getUserObject()!=null){
                    EdgeBean edgeBean = (EdgeBean)edge.getUserObject();
                    if(!"LA".equals(edgeBean.getTipo())) la = true;
                    if(!"DI".equals(edgeBean.getTipo())) di = true;
                }
            } else if(esIG(obj)){
                break;
            }
        }
        resultado = (la && di);
        if(resultado == false){
            if(mensajes==null) mensajes = "";
            mensajes = mensajes + bundle.getString("AVISO_RESTRICCION_HASTA_IG_LOS_DOS_TIPOS_DE_LINEAS")+"\n";
        }
        return resultado;
    }
    
    private boolean esSalida(Object obj){
        return (obj instanceof SymbolS1Cell ||
                obj instanceof SymbolS2Cell);
    }
    
    private boolean esTierra(Object obj){
        return (obj instanceof SymbolGRDCell);
    }

    private boolean esElemento(Object obj){
        // TODO Repasar para ver si hemos de añadir nuevos simbolos a esta condicion
        /*return (obj instanceof SymbolCCCell ||
                obj instanceof SymbolCC2Cell ||
                obj instanceof SymbolCGPCell ||
                obj instanceof SymbolCTCell ||
                obj instanceof SymbolD1Cell ||
                obj instanceof SymbolD2Cell ||
                obj instanceof SymbolFUSCell ||
                obj instanceof SymbolFUS2Cell ||
                obj instanceof SymbolGRDCell ||
                obj instanceof SymbolICCCell ||
                obj instanceof SymbolICPCell ||
                obj instanceof SymbolIGCell ||
                obj instanceof SymbolIG2Cell ||
                obj instanceof SymbolM1Cell ||
                obj instanceof SymbolRDCell ||
                obj instanceof SymbolS1Cell ||
                obj instanceof SymbolS2Cell ||
                obj instanceof SymbolSchucoCell);*/
        boolean esElemento = obj instanceof DefaultGraphCell;
        if (obj instanceof CajetinSCell ||
            obj instanceof TextCell ||
            obj instanceof RectangleCell) {
            esElemento = false;
        }
        return esElemento;
    }
    
    private boolean esIG(Object obj){
        // TODO Repasar para ver si hemos de añadir nuevos simbolos a esta condicion
        return (obj instanceof SymbolIGCell || obj instanceof SymbolIG2Cell);
    }
    
}
