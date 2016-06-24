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
public class ControlTablaPrevision
 {
     /** El modelo de la tabla */
     private ModeloTablaPrevision modelo = null;
     private JTable tabla = null;
     private BaseDatos bd = Sesion.getInstance().getBaseDatos();
     private int numId=1;
        
     /**
      * Constructor. Se le pasa el modelo, al que añade varios elementos y
      * se lo guarda.
      */
     public ControlTablaPrevision(JTable tabla, ModeloTablaPrevision modelo)
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
         BeanCargasInd dato=null;
         ParCombo pc = new ParCombo();
         int idCarga=0;

         try{
             rs = bd.ejecSelect("SELECT * FROM TIPOS_CARGIND");
             while(rs.next())
             {
                 pc.setKeyString(rs.getString(1));
                 pc.setDescription(rs.getString(2));
             }
             //Obtengo de la sesion la ID de la Instalación que se está creando.
             String idInst = Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString();
             rs = bd.ejecSelect("SELECT * FROM CARGAS_INDUSTRIALES");
             
             //SI existe algún registro en la BD hay que calcular su ID para agregarlo a la fila.
             if(rs.next())
             {
                 if(tabla.getRowCount()>0) //Si hay algun registro introducido en la tabla Prevision
                 {
                     String strIdCarga = modelo.getValueAt(tabla.getRowCount()-1,4).toString();
                     idCarga=Integer.parseInt(strIdCarga);
                     idCarga++;
                 }
                 else
                 {
                     rs = bd.getUltimoResgistro("SELECT * FROM CARGAS_INDUSTRIALES");
                     idCarga = rs.getInt("CIID");
                     idCarga++;
                 }    
                                  
                 dato = new BeanCargasInd (pc,"","",""+idInst,""+idCarga);
             }    
             else
             {
                 dato = new BeanCargasInd (pc,"","",""+idInst,""+numId);
                 numId++;
             }

             modelo.anhadeFila (dato);
         }
         catch(SQLException e){
             Mensaje.error("ControlTablaPrevision.java: "+e.getMessage(),e);
         }
     }
     
     /** Elimina la primera fila del modelo */
     public void borraFila (int fila)
     {
         if (modelo.getRowCount() > 0)
            modelo.borraFila (fila);
     }
}
