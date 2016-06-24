/*
 * NewClass.java
 *
 * Created on 11 de septiembre de 2006, 15:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package forms.establecer;

import forms.FrmDatosTecnicosPnl;
import forms.FrmPrincipal;
import funciones.BaseDatos;
import funciones.ParCombo;
import funciones.Sesion;
import funciones.Utilidades;
import funciones.UtilidadesSQL;
import funciones.beans.BeanResumen;
import funciones.beans.ModeloTablaResumen;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import main.Constantes;
import main.Mensaje;

/**
 *
 * @author enrique
 */
public class SetInfoResumen implements IntfzEstablecerDatos{
    private FrmDatosTecnicosPnl frmDatosTecnicos;
    private BaseDatos bd;
    private ModeloTablaResumen  modeloTablaResumen;
    private int idInstalacion, numFilas;
        
    public SetInfoResumen(FrmPrincipal frmPrincipal, int idInst) {
        frmDatosTecnicos = frmPrincipal.getFrmDatosTecnicos();
        bd = Sesion.getInstance().getBaseDatos();
        modeloTablaResumen = frmDatosTecnicos.modeloTablaResumen;
        for(int i=modeloTablaResumen.getRowCount()-1;i>-1;i--) modeloTablaResumen.borraFila(i);
        idInstalacion = idInst;
    }
     
     public void getInfoBD() {
         ResultSet rs = null;
        
        String consulta = "SELECT * FROM DATOS_CIRCUITOS WHERE DCINID = "+idInstalacion;
        try{
            numFilas = bd.getNumFilas(consulta);
        }
        catch(SQLException e){
            Mensaje.error("SetInfoResumen.java: "+e.getMessage(),e);
        }
    }

    public void rellenarFormulario() {
        ResultSet rs = null;
        String descripcion, potencia, tension, intensidad, caida;
        String numConductores, aislamiento, intMax, ccpia, longitud;
        String idDatosCircuito, idTipoCircuito, idTiposInstCircuito;
        DecimalFormat formateador = new DecimalFormat("##0.00");
        
        BeanResumen beanResumen;
                
        String consulta = "SELECT * FROM DATOS_CIRCUITOS WHERE DCINID = "+idInstalacion;
        try{
            rs = bd.ejecSelect(consulta);
            while(rs.next()){
                idTipoCircuito  = rs.getString("DCTRID");
                descripcion     = rs.getString("DCDESC");
                potencia        = formateador.format(Double.valueOf(rs.getString("DCPOTENCIA")));
                tension         = formateador.format(Double.valueOf(rs.getString("DCTENSION")));
                numConductores  = rs.getString("DCNUMCOND");
                aislamiento     = formateador.format(Double.valueOf(rs.getString("DCAISLAMIENTO")));
                idTiposInstCircuito = rs.getString("DCTNID");
                formateador.setGroupingUsed(false);
                intensidad      = formateador.format(Double.valueOf(rs.getString("DCINTENSIDAD")));
                intMax          = formateador.format(Double.valueOf(rs.getString("DCINTMAX")));
                ccpia           = formateador.format(Double.valueOf(rs.getString("DCCCPIA")));
                longitud        = formateador.format(Double.valueOf(rs.getString("DCLONGITUD")));
                caida           = formateador.format(Double.valueOf(rs.getString("DCCAIDA")));
                idDatosCircuito = rs.getString("DCID");
                
                beanResumen = new BeanResumen();
                beanResumen.setDescCircuito(descripcion);
                beanResumen.setPotCalculo(potencia);
                beanResumen.setTensionCalculo(tension);
                beanResumen.setNumCondSecc(numConductores);
                beanResumen.setAislamTension(aislamiento);
                beanResumen.setIntCalculo(intensidad);
                beanResumen.setIntMax(intMax);
                beanResumen.setCcpia(ccpia);
                beanResumen.setLongitud(longitud);
                beanResumen.setCaidaTension(caida);
                beanResumen.setIdDatosCircuito(idDatosCircuito);
                
                ResultSet rsTemp = bd.ejecSelect("SELECT * FROM TIPOS_CIRCUITO WHERE TRID = "+idTipoCircuito);
                if(rsTemp.next()){
                    beanResumen.setTipoCircuito(new ParCombo(""+idTipoCircuito,rsTemp.getString("TRDESC")));
                }
                int idInstalacion = ((Integer)Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID)).intValue();
                rsTemp = bd.ejecSelect( "SELECT " +
                                            "* " +
                                        "FROM " +
                                        "(" +
                                            "SELECT * FROM TIPOS_INST_CIRCUITOS WHERE TNDESC<>'' " +
                                            "UNION " +
                                            "SELECT 'D' AS TNID,INTIPOCIRCD AS TNDESC FROM INSTALACIONES WHERE INID="+UtilidadesSQL.tratarParametroNumerico(idInstalacion)+" " +
                                            "UNION " +
                                            "SELECT 'E' AS TNID,INTIPOCIRCE AS TNDESC FROM INSTALACIONES WHERE INID="+UtilidadesSQL.tratarParametroNumerico(idInstalacion)+" " +
                                            "UNION " +
                                            "SELECT 'F' AS TNID,INTIPOCIRCF AS TNDESC FROM INSTALACIONES WHERE INID="+UtilidadesSQL.tratarParametroNumerico(idInstalacion)+" " +
                                        ") AS TABLA " +
                                        "WHERE " +
                                            "TABLA.TNDESC<>'' AND " +
                                            "TABLA.TNID="+UtilidadesSQL.tratarParametroString(idTiposInstCircuito)
                    );
                if(rsTemp.next()){
                    beanResumen.setTipoInst(new ParCombo(idTiposInstCircuito,idTiposInstCircuito+" - "+rsTemp.getString("TNDESC")));
                }
                
                modeloTablaResumen.anhadeFila(beanResumen);
            }    
        }
        
        catch(SQLException e){
            Mensaje.error("SetInfoPrevisionIndustriales.java: "+e.getMessage());
        }
    }
}