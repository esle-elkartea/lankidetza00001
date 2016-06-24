/*
 * Config.java
 *
 * Created on 31 de julio de 2006, 9:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package funciones;

import java.util.ResourceBundle;

/**
 *
 * @author enrique
 */
public class Config {
    static private Config instance = null;
    static private String cadenaConexion = null;
    static private String driverBD = null;
    static private String usrBD = null;
    static private String pswBD = null;
    
    /** Creates a new instance of Config */
    public Config() {
        try {
            cadenaConexion = ResourceBundle.getBundle("resources/config/config").getString("CADENA_CONEXION");
            driverBD = ResourceBundle.getBundle("resources/config/config").getString("DRIVER_BD");
            usrBD =ResourceBundle.getBundle("resources/config/config").getString("USR_BD");
            pswBD = ResourceBundle.getBundle("resources/config/config").getString("PSW_BD");
        }
        catch(Exception e) {}
    }
    
    /**
     * Metodo que devuelve una instancia de la clase.
     * De esta forma conseguimos que solo exista una haciendo que la
     * sobrecarga en del servidor sea menor. Patrón Singleton
     */
    public static Config getInstance() {
        //si no hay ninguna instancia de esta clase tenemos que crear una
        if (instance == null) {
            // Necesitamos sincronizacion para serializar (no multithread)
            // Las invocaciones de este metodo
            synchronized(Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    public static String getCadenaConexion() {
        return cadenaConexion;
    }
    
    public static String getDriverBD() {
        return driverBD;
    }
    
    public static String getUsrBD() {
        return usrBD;
    }
    
    public static String getPswBD() {
        return pswBD;
    }
    
    public static String getParametro(String parametro) {
        return ResourceBundle.getBundle("resources/config/config").getString(parametro);
    }
}
