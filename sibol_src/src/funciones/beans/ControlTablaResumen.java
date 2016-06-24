package funciones.beans;

import funciones.BaseDatos;
import funciones.ParCombo;
import funciones.Sesion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import main.Constantes;
import main.Mensaje;

/**
 * Clase para modificar el modelo de la tabla.
 */
public class ControlTablaResumen
 {
     /** El modelo de la tabla */
     private ModeloTablaResumen modelo = null;
     private JTable tabla = null;
     private BaseDatos bd = Sesion.getInstance().getBaseDatos();
     private int numId=1;
        
     /**
      * Constructor. Se le pasa el modelo, al que añade varios elementos y
      * se lo guarda.
      */
     public ControlTablaResumen(JTable tabla, ModeloTablaResumen modelo)
     {
         this.modelo = modelo;
         this.tabla = tabla;   
     }
     
     /**
      * Añade una fila nueva en el modelo, al final del mismo
      */
     public void anhadeFila ()
     {
         ResultSet rs = null;
         BeanResumen dato = null;
         ParCombo pcTipoCircuito = new ParCombo();
         ParCombo pcTipoInstCircuito = new ParCombo();
         int idDato=0;
         
         try{
             rs = bd.ejecSelect("SELECT * FROM TIPOS_CIRCUITO");
             while(rs.next())
             {
                 pcTipoCircuito.setKeyString(rs.getString(1));
                 pcTipoCircuito.setDescription(rs.getString(2));
                 
             }
             rs = bd.ejecSelect("SELECT * FROM TIPOS_INST_CIRCUITOS");
             while(rs.next())
             {
                 pcTipoInstCircuito.setKeyString(rs.getString(1));
                 pcTipoInstCircuito.setDescription(rs.getString(2));    
             }

             //Obtengo de la sesion la ID de la Instalación que se está creando.
             String idInst = Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();
             rs = bd.ejecSelect("SELECT * FROM DATOS_CIRCUITOS");
             
             //SI existe algún registro en la BD hay que calcular su ID para agregarlo a la fila.
             if(rs.next())
             {
                 if(tabla.getRowCount()>0) //Si hay algun registro introducido en la tabla Resumen
                 {
                     idDato =Integer.parseInt(modelo.getValueAt(tabla.getRowCount()-1,12).toString());
                     idDato++;
                 }
                 else
                 {
                     rs = bd.getUltimoResgistro("SELECT * FROM DATOS_CIRCUITOS");
                     idDato = rs.getInt("DCID");
                     idDato++;
                 }
                               
                dato = new BeanResumen ();
                dato.setIdDatosCircuito(String.valueOf(idDato));
                dato.setIdInst(idInst);
                dato.setTipoCircuito(pcTipoCircuito);
                dato.setDescCircuito("");
                dato.setPotCalculo("");
                dato.setTensionCalculo("");
                dato.setIntCalculo("");
                dato.setNumCondSecc("");
                dato.setAislamTension("");
                dato.setTipoInst(pcTipoInstCircuito);
                dato.setIntMax("");
                dato.setCcpia("");
                dato.setLongitud("");
                dato.setCaidaTension("");
                 
             }    
             else
             {
                dato = new BeanResumen();
                dato.setIdDatosCircuito(String.valueOf(numId));
                dato.setIdInst(idInst);
                dato.setTipoCircuito(pcTipoCircuito);
                dato.setDescCircuito("");
                dato.setPotCalculo("");
                dato.setTensionCalculo("");
                dato.setIntCalculo("");
                dato.setNumCondSecc("");
                dato.setAislamTension("");
                dato.setTipoInst(pcTipoInstCircuito);
                dato.setIntMax("");
                dato.setCcpia("");
                dato.setLongitud("");
                dato.setCaidaTension("");
                numId++;
             }

             modelo.anhadeFila (dato);
         }
         catch(SQLException e){
             Mensaje.error("ControlTablaResumen.java: "+e.getMessage(),e);
         }
     }
     
     /** Elimina la primera fila del modelo */
     public void borraFila (int fila)
     {
         if (modelo.getRowCount() > 0)
            modelo.borraFila (fila);
     }
}
