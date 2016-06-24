/*
 * Grabador.java
 *
 * Created on 11 de septiembre de 2006, 16:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package graph;

import funciones.BaseDatos;
import funciones.Sesion;
import funciones.Utilidades;
import funciones.UtilidadesSQL;
import graph.beans.AcomBean;
import graph.beans.CCBean;
import graph.beans.CGPBean;
import graph.beans.D1Bean;
import graph.beans.EdgeBean;
import graph.beans.GRDBean;
import graph.beans.IG2Bean;
import graph.beans.IGBean;
import graph.beans.M1Bean;
import graph.beans.SBean;
import graph.cell.SymbolCGPCell;
import graph.cell.SymbolM1Cell;
import graph.cell.SymbolS1Cell;
import graph.cell.SymbolS2Cell;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
import main.Constantes;
import main.Mensaje;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphModel;

/**
 *
 * @author oscar
 */
public class Grabador {
       
    /** Creates a new instance of Grabador */
    public Grabador(){}
       
    public static void grabar(GraphModel model, String xml, boolean hayErrores) {
        String cgpTipo = null;
        double cgpIntensidadBase = 0;
        double cgpIntensidadCartucho = 0;
        String tipoLgaDi = null;
        String lgaTipo = null;
        double lgaSeccion = 0;
        String lgaMaterial = null;
        String diTipo = null;
        double diSeccion = 0;
        String diMaterial = null;
        String ccTipo = null;
        String ccSituacion = null;
        String grdTipo = null;
        double grdElectrodos = 0;
        double grdLineaEnlace = 0;
        double grdLineaPrincipal = 0;
        double grdOhmios = 0;
        double igInstGeneralAutomatico = 0;
        double igIcc = 0;
        double dIntDiferencial = 0;
        double dSensibilidad = 0;         
        String acomPuntoConexion = null;
        String acomTipoLinea = null;
        double acomSeccion = 0;
        String acomMaterial = null;
        Vector<String[]> datosSalidas = null;
         
        calcularCCPIA(model, hayErrores);
        
        boolean esVivienda = UtilidadesSQL.esVivienda();
        
        boolean encontradoIG = false;
        boolean encontradaLGA = false;
        String[] valoresLGA = null;
        boolean encontradaDI = false;
        
        for(int i=0; i < model.getRootCount(); i++) {
            Object obj = model.getRootAt(i);            
            if(obj instanceof DefaultGraphCell){
                Object objBean = ((DefaultGraphCell)obj).getUserObject();
                // CGP
                if(objBean!=null && objBean instanceof CGPBean){
                    CGPBean bean = (CGPBean)objBean;
                    cgpTipo=bean.getReferencia();
                    cgpIntensidadBase=bean.getIntensidadBase();
                    cgpIntensidadCartucho=bean.getIntensidadCartucho();
                // CC
                } else if(objBean!=null && objBean instanceof CCBean){                
                    CCBean bean = (CCBean)objBean;
                    ccTipo=bean.getReferencia();
                    ccSituacion=bean.getSituacion();
                // Tierra
                } else if(objBean!=null && objBean instanceof GRDBean){
                    GRDBean bean = (GRDBean)objBean;
                    grdTipo=bean.getTipo();
                    grdElectrodos=bean.getNumeroElectrodos();
                    grdLineaEnlace=bean.getLineaEnlace();
                    grdLineaPrincipal=bean.getLineaPrincipal();
                    grdOhmios=bean.getOhmios();
                // IG
                } else if(objBean!=null && objBean instanceof IGBean){            
                    IGBean bean = (IGBean)objBean;
                    igInstGeneralAutomatico=bean.getCalibre();
                    igIcc=bean.getPoderDeCorte();
                    encontradoIG = true;
                } else if(objBean!=null && objBean instanceof IG2Bean){            
                    IG2Bean bean = (IG2Bean)objBean;
                    igInstGeneralAutomatico=bean.getCalibre();
                    igIcc=bean.getPoderDeCorte();
                    dIntDiferencial = bean.getCalibre();
                    dSensibilidad = bean.getSensibilidad();
                    encontradoIG = true;
                // Magnetotérmico
                } else if(objBean!=null && objBean instanceof M1Bean && !encontradoIG){
                    M1Bean bean = (M1Bean)objBean;
                    if("IG".equalsIgnoreCase(bean.getReferencia())){
                        igInstGeneralAutomatico=bean.getCalibre();
                        igIcc=bean.getPoderDeCorte();
                    }
                // Diferencial
                } else if(objBean!=null && objBean instanceof D1Bean){                 
                    D1Bean bean = (D1Bean)objBean;
                    dIntDiferencial = bean.getCalibre();
                    dSensibilidad = bean.getSensibilidad();
                // Línea General de Alimentación o Derivación Individual
                } else if(objBean!=null && objBean instanceof EdgeBean){           
                    EdgeBean bean = (EdgeBean)objBean;
                    // Línea General de Alimentación
                    if("LA".equals(bean.getTipo())){
                        tipoLgaDi = "LA";
                        lgaTipo = bean.getLgaTipo();
                        lgaSeccion = bean.getLgaSeccion();
                        lgaMaterial = bean.getLgaMaterial();
                        String[] valores = new String[14];
                        valores[0]="LGA";
                        valores[2]=String.valueOf(bean.getLgaTension()); //Tension
                        valores[3]=getIntensidad(model); //Intensidad
                        valores[1]=getPotencia(Double.parseDouble(valores[2]),Double.parseDouble(valores[3])); //Potencia
                        valores[4]=("400".equals(valores[1])?"3":"2")+"x"+bean.getLgaSeccion()+" "+bean.getLgaMaterial(); //Fases x seccion x material
                        valores[5]=String.valueOf(bean.getLgaTensionAislamiento()); //Tension aislamiento
                        valores[6]=bean.getLgaTipoInstalacion(); //Tipo instalacion
                        valores[7]=calcularIntensidadMaximaAdmisible(bean.getTipoCable(),bean.getLgaAislamiento(),bean.getLgaMaterial(),("400".equals(valores[1])?"3":"2"),bean.getLgaSeccion()); //Intensidad maxima admisible
                        valores[8]=String.valueOf(bean.getProteccion()); //CCPIA
                        valores[9]=String.valueOf(bean.getLgaLongitud()); //Longitud
                        valores[10]=getCaidaTension(valores[1],valores[2],valores[9],bean.getLgaMaterial(),String.valueOf(bean.getLgaSeccion())); //Caida tension
                        valores[11]=String.valueOf(Constantes.LINEA_GENERAL);
                        valores[12]="";
                        valores[13]="";
                        encontradaLGA = true;
                        ///if(datosSalidas==null) datosSalidas = new Vector<String[]>();
                        ///datosSalidas.add(valores);
                        valoresLGA = valores;
                    // Derivación Individual
                    } else if("DI".equals(bean.getTipo())){
                        tipoLgaDi = "DI";
                        diTipo = bean.getDiTipo();
                        diSeccion = bean.getDiSeccion();
                        diMaterial = bean.getDiMaterial();
                        String[] valores = new String[14];
                        valores[0]="DI";
                        valores[1]=String.valueOf(bean.getPotencia()); //getPotencia(); //Potencia
                        valores[2]=bean.getTension(); //getTension(model); //Tension
                        valores[3]=getIntensidad(valores[1],valores[2]); //Intensidad
                        valores[4]=("400".equals(bean.getTension1())?"3":"2")+"x"+bean.getDiSeccion()+" "+bean.getDiMaterial(); //Fases x seccion x material
                        valores[5]=String.valueOf(bean.getDiTensionAislamiento()); //Tension aislamiento
                        valores[6]=bean.getDiTipoInstalacion(); //Tipo instalacion
                        valores[7]=calcularIntensidadMaximaAdmisible(bean.getTipoCable(),bean.getDiAislamiento(),bean.getDiMaterial(),("400".equals(bean.getTension1())?"3":"2"),bean.getDiSeccion()); //Intensidad maxima admisible
                        valores[8]=String.valueOf(bean.getProteccion()); //CCPIA
                        valores[9]=String.valueOf(bean.getDiLongitud()); //Longitud
                        valores[10]=getCaidaTension(valores[1],valores[2],valores[9],bean.getDiMaterial(),String.valueOf(bean.getDiSeccion())); //Caida tension
                                                
                        // si es vivienda y hay LGA cogemos de tipo DERIVACION_INDIVIDUAL (la LGA tiene que estar "dibujada" antes que la DI)
                        if(esVivienda && encontradaLGA) {
                            valores[11]=String.valueOf(Constantes.DERIVACION_INDIVIDUAL);
                        }
                        // si no es vivienda o es vivienda y no hay LGA cogemos de tipo LINEA_GENERAL
                        else {
                            valores[11]=String.valueOf(Constantes.LINEA_GENERAL);
                        }
                        valores[12]=bean.getDiTipoDerivacion();
                        valores[13]=bean.getDiDescripcion();
                        encontradaDI = true;
                        if(datosSalidas==null) datosSalidas = new Vector<String[]>();
                        datosSalidas.add(valores);
                    }
                // Salida
                } else if(objBean!=null && objBean instanceof SBean){
                    DecimalFormat df = new DecimalFormat("###.00");
                    DecimalFormat df2  = new DecimalFormat("###");
                    SBean bean = (SBean)objBean;
                    String[] valores = new String[14];
                    valores[0]=bean.getReferencia();
                    valores[1]=Utilidades.cambiarComa(df.format(bean.getPotencia()));
                    valores[2]=Utilidades.cambiarComa(df.format(bean.getTension()));
                    valores[3]=Utilidades.cambiarComa(df.format(bean.getIntensidad()));
                    valores[4]=df2.format(bean.getFases())+"x"+bean.getSeccion()+" "+bean.getMaterial();
                    valores[5]=String.valueOf(bean.getTensionAislamiento());
                    valores[6]=bean.getTipoInstalacion();
                    valores[7]=Utilidades.cambiarComa(df.format(bean.getIntensidadMaximaAdmisible()));
                    valores[8]=Utilidades.cambiarComa(df.format(bean.getCCPIA()));
                    valores[9]=Utilidades.cambiarComa(df.format(bean.getLongitud()));
                    if(Double.isNaN(bean.getCaidaTension()) || Double.isInfinite(bean.getCaidaTension())){
                        valores[10]="0";
                    } else {
                        valores[10] = Utilidades.cambiarComa(df.format(bean.getCaidaTension()));
                    }
                    if(esVivienda){
                        // si es C1, C2, C* consideramos que es Vivienda Tipo
                        if (Pattern.matches("C.*", valores[0])) {
                            valores[11]=String.valueOf(Constantes.VIVIENDAS_TIPO);
                        }    
                        // si es A1, A2, A* o E1, E2, E* consideramos que es Servicios Comunes/Alumbrado
                        else if(Pattern.matches("A.*", valores[0]) || Pattern.matches("E.*", valores[0])) {
                            valores[11]=String.valueOf(Constantes.ALUMBRADO);
                        }
                        // en otro caso consideramos que es Servicios Comunes/Fuerza
                        else {
                            valores[11]=String.valueOf(Constantes.FUERZA);
                        }
                    } else {
                        valores[11]=String.valueOf(Constantes.INSTALACIONES_INDUSTRIALES_AGRARIAS_SERVICIOS);
                    }
                    valores[12]="";
                    valores[13]="";
                    if(datosSalidas==null) datosSalidas = new Vector<String[]>();
                    datosSalidas.add(valores);
                } else if(objBean!=null && objBean instanceof AcomBean){
                    AcomBean bean = (AcomBean)objBean;
                    acomPuntoConexion = bean.getPuntoConexion();
                    acomTipoLinea = bean.getTipoLinea();
                    acomSeccion = bean.getSeccion();
                    acomMaterial = bean.getMaterial();
                }
            }
        }
        
        // sólo tenemos en cuenta la LGA si no hay DI o si es vivienda
        if(encontradaLGA && (esVivienda || !encontradaDI)) {
            datosSalidas.add(valoresLGA);
            
            // en este caso ponemos todas las DI como tipo DERIVACION_INDIVIDUAL
            // ya que puede ser que la LGA no esté dibujada (o internamente representada) antes de las DI
            for(int i = 0; i < datosSalidas.size(); i++) {
                String[] valores = datosSalidas.get(i);
                if(valores[0].equals("DI")) valores[11]=String.valueOf(Constantes.DERIVACION_INDIVIDUAL);
                datosSalidas.set(i, valores);
            }
        }
        
        // Obtener cadena xml
        guardarDatosGenerales(
            tipoLgaDi,
            lgaTipo,
            lgaSeccion,
            lgaMaterial,
            cgpTipo,
            cgpIntensidadBase,
            cgpIntensidadCartucho,
            diTipo,
            diSeccion,
            diMaterial,
            ccTipo,
            ccSituacion,
            igInstGeneralAutomatico,
            igIcc,
            dIntDiferencial,
            dSensibilidad,
            grdTipo,            
            grdElectrodos,
            grdLineaEnlace,
            grdLineaPrincipal,
            grdOhmios,
            xml,
            acomPuntoConexion,
            acomTipoLinea,
            acomSeccion,
            acomMaterial
        );
        borrarDatosCircuitos();
        if(datosSalidas!=null){
            for(int i=0;i<datosSalidas.size();i++){
                String[] valores = datosSalidas.get(i);
                guardarDatosCircuito(valores);
            }
        }
    }
       
    private static void guardarDatosGenerales(
        String tipoLgaDi,
        String lgaTipo,
        double lgaSeccion,
        String lgaMaterial,
        String cgpTipo,
        double cgpIntensidadBase,
        double cgpIntensidadCartucho,
        String diTipo,
        double diSeccion,
        String diMaterial,
        String ccTipo,
        String ccSituacion,
        double igInstGeneralAutomatico,
        double igIcc,
        double dIntDiferencial,
        double dSensibilidad,
        String grdTipo,            
        double grdElectrodos,
        double grdLineaEnlace,
        double grdLineaPrincipal,
        double grdOhmios,
        String xml,
        String acomPuntoConexion,
        String acomTipoLinea,
        double acomSeccion,
        String acomMaterial
    ){
        BaseDatos bd = Sesion.getInstance().getBaseDatos();
        try{
            StringBuffer sql = new StringBuffer("UPDATE INSTALACIONES SET ");
            sql.append("INPCIDACOMETIDA=").append(UtilidadesSQL.tratarParametroString(acomPuntoConexion)).append(", ");
            sql.append("INTLIDACOMETIDA=").append(UtilidadesSQL.tratarParametroString(acomTipoLinea)).append(", ");
            sql.append("INSECCACOMETIDA=").append(UtilidadesSQL.tratarParametroString(acomSeccion)).append(", ");
            sql.append("INMAIDACOMETIDA=").append(UtilidadesSQL.tratarParametroString(acomMaterial)).append(", ");
            sql.append("INCGPTIPO=").append(UtilidadesSQL.tratarParametroString(cgpTipo)).append(", ");
            sql.append("INCGPIBASE=").append(UtilidadesSQL.tratarParametroNumerico(cgpIntensidadBase)).append(", ");
            sql.append("INCGPICARTUCHO=").append(UtilidadesSQL.tratarParametroNumerico(cgpIntensidadCartucho)).append(", ");
            if ("DI".equals(tipoLgaDi)) {
                sql.append("INTLGENERAL=").append(UtilidadesSQL.tratarParametroString(diTipo)).append(", ");
                sql.append("INSECCGENERAL=").append(UtilidadesSQL.tratarParametroString(diSeccion)).append(", ");
                sql.append("INMAIDGENERAL=").append(UtilidadesSQL.tratarParametroString(diMaterial)).append(", ");
            } else if ("LA".equals(tipoLgaDi)) {
                sql.append("INTLGENERAL=").append(UtilidadesSQL.tratarParametroString(lgaTipo)).append(", ");
                sql.append("INSECCGENERAL=").append(UtilidadesSQL.tratarParametroString(lgaSeccion)).append(", ");
                sql.append("INMAIDGENERAL=").append(UtilidadesSQL.tratarParametroString(lgaMaterial)).append(", ");
            } else {
                sql.append("INTLGENERAL=NULL, ");
                sql.append("INSECCGENERAL=0, ");
                sql.append("INMAIDGENERAL=NULL, ");
            }
            sql.append("INMODULOMED=").append(UtilidadesSQL.tratarParametroString(ccTipo)).append(", ");
            sql.append("INSMID=").append(UtilidadesSQL.tratarParametroString(ccSituacion)).append(", ");
            sql.append("ININTGRAL=").append(UtilidadesSQL.tratarParametroNumerico(igInstGeneralAutomatico)).append(", ");
            sql.append("INICC=").append(UtilidadesSQL.tratarParametroNumerico(igIcc)).append(", ");
            sql.append("ININTDIF=").append(UtilidadesSQL.tratarParametroNumerico(dIntDiferencial)).append(", ");
            sql.append("INSENSIBILIDAD=").append(UtilidadesSQL.tratarParametroNumerico(dSensibilidad)).append(", ");
            sql.append("INTTID=").append(UtilidadesSQL.tratarParametroString(grdTipo)).append(", ");
            sql.append("INELECTRODOS=").append(UtilidadesSQL.tratarParametroString(grdElectrodos)).append(", ");
            sql.append("INSECCLINENLACE=").append(UtilidadesSQL.tratarParametroNumerico(grdLineaEnlace)).append(", ");
            sql.append("INSECCLINPPAL=").append(UtilidadesSQL.tratarParametroNumerico(grdLineaPrincipal)).append(", ");
            sql.append("INRESTIERRA=").append(UtilidadesSQL.tratarParametroNumerico(grdOhmios)).append(", ");
            sql.append("INXML=").append(UtilidadesSQL.tratarParametroString(xml));
            sql.append(" WHERE INID=").append(Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString());
            
            bd.ejecModificacion(sql.toString());
        } catch(SQLException e){
            Mensaje.error("Error SQL en Grabador.guardarDatosGenerales: "+e.getMessage());
        }
    }
    
    private static void borrarDatosCircuitos(){
        BaseDatos bd = Sesion.getInstance().getBaseDatos();
        try{
            String sql =    "DELETE FROM " +
                                "DATOS_CIRCUITOS " +
                            "WHERE " +
                                "DCINID="+Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();
            bd.ejecModificacion(sql);
        } catch(SQLException e){
            Mensaje.error("Error SQL en Grabador.borrarDatosCircuitos: "+e.getMessage());
        }
    }
    
    private static void guardarDatosCircuito(String[] valores){
        BaseDatos bd = Sesion.getInstance().getBaseDatos();
        try{
            String sql =    "INSERT INTO " +
                                "DATOS_CIRCUITOS " +
                            "(" +
                                "DCINID," +
                                "DCTRID," +
                                "DCDESC," +
                                "DCPOTENCIA," +
                                "DCTENSION," +
                                "DCINTENSIDAD," +
                                "DCNUMCOND," +
                                "DCAISLAMIENTO," +
                                "DCTNID," +
                                "DCINTMAX," +
                                "DCCCPIA," +
                                "DCLONGITUD," +
                                "DCCAIDA," +
                                "DCTIPODERIVACION," +
                                "DCDESCDERIVACION " +
                            ") VALUES (" +
                                Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString()+","+
                                UtilidadesSQL.tratarParametroNumerico(Integer.parseInt(valores[11]))+","+
                                UtilidadesSQL.tratarParametroString(valores[0])+","+
                                valores[1]+","+
                                valores[2]+","+
                                valores[3]+","+
                                UtilidadesSQL.tratarParametroString(valores[4])+","+
                                valores[5]+","+
                                UtilidadesSQL.tratarParametroString(valores[6])+","+
                                valores[7]+","+
                                valores[8]+","+
                                valores[9]+","+
                                valores[10]+","+
                                UtilidadesSQL.tratarParametroString(valores[12])+","+
                                UtilidadesSQL.tratarParametroString(valores[13])+
                            ")";
            bd.ejecModificacion(sql);
        } catch(SQLException e){
            Mensaje.error("Error SQL en Grabador.guardarDatosCircuito: "+e.getMessage());
        }
    }
    
    private static void calcularCCPIA(GraphModel model, boolean hayErrores) {
        CalculadorCaminos obtenedor = new CalculadorCaminos(model);
        try {
            List caminos = obtenedor.getCaminos();
            if(caminos!=null){
                for(int i=0;i<caminos.size();i++){
                    double ccpia = 0;
                    List camino = (List) caminos.get(i);
                    for(int j=0;j<camino.size()-1;j++){
                        Object obj = camino.get(j);
                        if(obj instanceof SymbolM1Cell){
                            DefaultGraphCell cell = (DefaultGraphCell)obj;
                            if(cell.getUserObject()!=null){
                                M1Bean bean = (M1Bean)cell.getUserObject();
                                if(ccpia==0 || (bean.getCalibre()<ccpia)){
                                    ccpia = bean.getCalibre();
                                }
                            }
                        }
                    }
                    Object obj = camino.get(camino.size()-1);
                    if(obj instanceof SymbolS1Cell || obj instanceof SymbolS2Cell){
                        DefaultGraphCell cell = (DefaultGraphCell)obj;
                        if(cell.getUserObject()!=null){
                            SBean bean = (SBean)cell.getUserObject();
                            bean.setCCPIA(ccpia);
                        }
                    }
                }
            }
        } catch (Exception e){
            if (!hayErrores) {
                Mensaje.error("Error en Grabador.calcularCCPIA: "+e.getMessage());
            }
        }
    }
    
    private static String getTension(GraphModel model){
        String resultado = "230";
        for(int i=0;i<model.getRootCount();i++){
            Object obj = model.getRootAt(i);            
            if(obj instanceof SymbolS1Cell || obj instanceof SymbolS2Cell){
                Object objBean = ((DefaultGraphCell)obj).getUserObject();
                if(objBean!=null && objBean instanceof SBean){
                    SBean bean = (SBean)objBean;
                    if(bean.getTension()==400){
                        resultado="400";
                        break;
                    }
                }
            }
        }
        return resultado;
    }
    
    private static String getPotencia(){
        String resultado = "";
        BaseDatos bd = Sesion.getInstance().getBaseDatos();
        ResultSet rs = null;
        try{
            String sql = "SELECT INPOTNORMAL FROM INSTALACIONES WHERE INID="+Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();
            rs = bd.ejecSelect(sql);
            if(rs.next()){
                resultado = rs.getString("INPOTNORMAL");
            }
        } catch(SQLException e){
            Mensaje.error("Grabador.java: "+e.getMessage(),e);
        } finally {
            try {
                if(rs!=null){rs.close();}
            } catch (SQLException e){
                Mensaje.error("Grabador.java: "+e.getMessage(),e);    
            }
        }
        return resultado;
    }
    
    private static String getPotencia(double tension, double intensidad){
        return String.valueOf(Math.ceil((tension==400?Math.sqrt(3):1)*tension*intensidad*0.85*1));
    }    
    
    private static String getIntensidad(GraphModel model){
        String resultado = "0";
        for(int i=0;i<model.getRootCount();i++){
            Object obj = model.getRootAt(i);            
            if(obj instanceof SymbolCGPCell){
                Object objBean = ((DefaultGraphCell)obj).getUserObject();
                if(objBean!=null && objBean instanceof CGPBean){
                    CGPBean bean = (CGPBean)objBean;
                    resultado=String.valueOf(bean.getIntensidadCartucho());
                    break;
                }
            }
        }
        return resultado;
    }
    
    private static String getIntensidad(String potencia, String tension) {
        double resultado;
        double fases = ("400".equals(tension)?3:2);
        if(fases==2){
            resultado=Double.parseDouble(potencia)/(Double.parseDouble(tension));
        } else {
            resultado=Double.parseDouble(potencia)/(Math.sqrt(3)*Double.parseDouble(tension));
        }
        return String.valueOf(Math.ceil(resultado));
    }
    
    private static String getCaidaTension(String potencia, String tension, String longitud, String material, String seccion){
        double fases = ("400".equals(tension)?3:2);
        double dblTension = Double.parseDouble(tension);
        double dblPotencia = Double.parseDouble(potencia);
        double dblLongitud = Double.parseDouble(longitud);
        double conductividad = ("Al".equalsIgnoreCase(material)?35.0:56.0);
        double dblSeccion = Double.parseDouble(seccion);
        DecimalFormat df = new DecimalFormat("###.00");
        return Utilidades.cambiarComa(df.format((((fases==2?2.0:1.0)*dblPotencia*dblLongitud)/(conductividad*dblSeccion*dblTension))));
    }
    
    private static String getCaidaTensionPorcentual(String potencia, String tension, String longitud, String material, String seccion){
        double fases = ("400".equals(tension)?3:2);
        double dblTension = Double.parseDouble(tension);
        double dblPotencia = Double.parseDouble(potencia);
        double dblLongitud = Double.parseDouble(longitud);
        double conductividad = ("Al".equalsIgnoreCase(material)?35.0:56.0);
        double dblSeccion = Double.parseDouble(seccion);
        DecimalFormat df = new DecimalFormat("###.00");
        return Utilidades.cambiarComa(df.format((((fases==2?2.0:1.0)*dblPotencia*dblLongitud)/(conductividad*dblSeccion*dblTension))*100/dblTension));
    }
    
    private static String calcularIntensidadMaximaAdmisible(String tipoSalida, String aislamiento, String material, String polos, double seccion){
        String resultado = "0";
        ResultSet rs = null;
        BaseDatos bd = Sesion.getInstance().getBaseDatos();
        
        try{
            String sql = 
                    "SELECT " +
                        "IAINTENSIDAD " +
                    "FROM " +
                        "INTENSIDADES_ADMISIBLES " +
                    "WHERE " +
                        "IATSID="+UtilidadesSQL.tratarParametroString(tipoSalida)+" AND " +
                        "IANUMPOLOS="+UtilidadesSQL.tratarParametroNumerico(Integer.parseInt(polos))+" AND " +
                        "IAAIGRUPO IN (" +
                            "SELECT " +
                                "AIGRUPO " +
                            "FROM " +
                                "MATERIALES_AISLAMIENTO " +
                            "WHERE " +
                                "AIID="+UtilidadesSQL.tratarParametroString(aislamiento)+
                        ") AND " +
                        "IAMAID="+UtilidadesSQL.tratarParametroString(material)+" AND " +
                        "IASECCION<="+UtilidadesSQL.tratarParametroNumerico(seccion)+" " +
                    "ORDER BY IASECCION DESC " +
                    "LIMIT 1";

            rs = bd.ejecSelect(sql);
            if(rs.next()){
                resultado = rs.getString("IAINTENSIDAD");
            }
        } catch(SQLException e){
            Mensaje.error("Error en Grabador.calcularIntensidad: "+e.getMessage());
        } finally {
            try{
                if(rs!=null)rs.close();
            } catch(SQLException e){
                Mensaje.error("Error en Grabador.calcularIntensidad al cerrar el RecordSet: "+e.getMessage());
            }
        }
        return resultado;
    }
           
}
