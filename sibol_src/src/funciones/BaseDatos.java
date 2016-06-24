/*
 * BaseDatos.java
 *
 * Created on 19 de julio de 2006, 11:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package funciones;

import java.sql.Statement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BaseDatos
{
    private String driver, cadena, usuario, clave;
    private Connection conex;
    
    public BaseDatos(String driver, String cadena, String login, String clave )
    {
        this.driver = driver;
        this.cadena = cadena;
        this.usuario = login;
        this.clave = clave;
        this.conex = null;
    }

    //Constructor por defecto para el modo embebido de la BD
    public BaseDatos()
    {
        this(Config.getInstance().getDriverBD(),Config.getInstance().getCadenaConexion(),Config.getInstance().getUsrBD(),Config.getInstance().getPswBD());
    }
    
    public void conectar() throws Exception
    {
        try
        {
            Class.forName(this.driver);
            conex = DriverManager.getConnection(this.cadena,this.usuario,this.clave); //(cadena de conexión, usuario, password)
        }
        catch(Exception e)
        {
            throw new Exception("Error al conectar con la Base de Datos\nERROR: " + e.getMessage());
        }
    }

    //Ejecuta la sentencia SELECT
    public ResultSet ejecSelect(String consulta) throws SQLException
    {
        ResultSet rs = null;
        Statement st = null;
    
        try {
            st = this.conex.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(consulta);
        }
        finally {
            st.close();
        }
        
        return rs;
    }
    
    //Saber el nº de filas de la sentencia Select
    public int getNumFilas(String consulta) throws SQLException
    {
        ResultSet rs = null;
        Statement st = null;
        int numFilas = -1;
    
        try {
            st = this.conex.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(consulta);
            rs.last();
            numFilas=rs.getRow();
        }
        finally {
            st.close();
        }
        
        return numFilas;
    }
    
    public ResultSet getUltimoResgistro(String consulta) throws SQLException
    {
        ResultSet rs = null;
        Statement st = null;
    
        try {
            st = this.conex.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(consulta);
            rs.last();
        }
        finally {
            st.close();
        }
        return rs;
    }
    
    //Ejecuta las sentencias CREATE TABLE, INSERT, UPDATE, DELETE
    public void ejecModificacion(String consulta) throws SQLException
    {
        Statement st = null;
    
        try {
            st = this.conex.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.executeUpdate(consulta);
        }
        finally {
            st.close();
        }
    }
    
    //Cierra la coneión a la BD de forma segura
    public void close()
    {
        Statement st = null;
        
        try
        {
            st = conex.createStatement();
            st.execute("CHECKPOINT");
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally {
            try { 
                st.close(); 
            }
            catch(Exception e) {}
            try { 
                conex.close(); 
            }
            catch(Exception e) {}
            finally { 
                conex = null; 
            }
        }
    }
    
    public void ejecutarScriptBD(String path) throws Exception
    {
        BufferedReader bf = null;
                
        try{
            bf = new BufferedReader(new FileReader(path));
            while(bf.ready()){
                String txt = bf.readLine();
                ejecModificacion(txt);
            }
        }
        catch(Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally {
            bf.close();
        }
    }
}

