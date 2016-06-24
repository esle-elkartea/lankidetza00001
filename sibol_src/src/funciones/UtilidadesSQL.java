package funciones;

import java.sql.ResultSet;
import main.Constantes;
import main.Mensaje;

public class UtilidadesSQL {
    public static String tratarParametroString(String valor) {
        return((valor == null) ? "NULL" : "'" + valor.replaceAll("'", "''") + "'");
    }
    
    public static String tratarParametroString(double valor) {
        return "'" + String.valueOf(valor) + "'";
    }
    
    public static String tratarParametroString(int valor) {
        return "'" + String.valueOf(valor) + "'";
    }
    
    public static String tratarParametroNumerico(double valor) {
        return String.valueOf(valor);
    }
    
    public static String tratarParametroNumerico(int valor) {
        return String.valueOf(valor);
    }
    
    /**
     * Convierte una fecha de formato AAAAMMDD a DD/MM/AAAA
     * @param s Cadena con la fecha en formato AAAAMMDD
     * @return Cadena con la fecha en formato DD/MM/AAAA
     */
    public static String convierteTextoFecha(String s) {
        if(s != null) {
            String f = "";
            if(s.length() >= 8) {
                f = s.substring(6, 8) + "/" + s.substring(4, 6) + "/" + s.substring(0, 4);
            }
            return f;
        } else return "";
    }
    
    /**
     * Convierte una fecha de formato DD/MM/AAAA a AAAAMMDD
     * @param s Cadena con la fecha en formato DD/MM/AAAA
     * @return Cadena con la fecha en formato AAAAMMDD, cadena vacía si la fecha no es válida
     */
    public static String convierteFechaTexto(String s) {
        if(s != null) {
            String f = normalizarFecha(s);
            String[] fecha = f.split("/");
            if(fecha.length == 3) {
                try {
                    int dia = Integer.parseInt(fecha[0]);
                    int mes = Integer.parseInt(fecha[1]);
                    int anyo = Integer.parseInt(fecha [2]);
                    f = normalizarAnyo(anyo) + (mes < 10 ? "0" : "") + String.valueOf(mes) + (dia < 10 ? "0" : "") + String.valueOf(dia);
                } catch(Exception e) {
                    return "";
                }
            }
            return f;
        } else return "";
    }
    
    /**
     * Normalizar una fecha en formato DD-MM-AAAA o DD-MM-AA a DD/MM/AAAA
     * @param s Cadena con la fecha en formato DD-MM-AAAA
     * @return Cadena con la fecha en formato DD/MM/AAAA, cadena vacía si la fecha no es válida
     */
    public static String normalizarFecha(String s) {
        if(s != null) {
            String f = s;
            // primero sustituímos los posibles '-' por '/'
            f = f.replace('-', '/');
            // miramos si la fecha es válida; tiene que tener el formato DD/MM/AAAA
            String[] fecha = f.split("/");
            if(fecha.length == 3) {
                try {
                    int dia = Integer.parseInt(fecha[0]);
                    int mes = Integer.parseInt(fecha[1]);
                    int anyo = Integer.parseInt(fecha [2]);
                    f =  (dia < 10 ? "0" : "") + String.valueOf(dia) + "/" +  (mes < 10 ? "0" : "") + String.valueOf(mes) + "/" + normalizarAnyo(anyo);
                } catch(Exception e) {
                    return "";
                }
                return f;
            } else return "";
        } else return "";
    }
    
    /**
     * Genera una fecha en formato BD (AAAAMMDD) partiendo del dia, mes y año
     * @param dia Dia
     * @param mes Mes
     * @param anyo Año
     * @return fecha en formato de BD (AAAAMMDD)
     */
    public static String fechaBD(int dia, int mes, int anyo) {
        String f =  normalizarAnyo(anyo) + (mes < 10 ? "0" : "") + String.valueOf(mes) + (dia < 10 ? "0" : "") + String.valueOf(dia);
        return f;
    }
    
    /**
     * Normaliza el añoo cuando es de dos dígitos
     * @param anyo Año
     * @return Año normalizado a 4 dígitos (consideramos que si es 50 o mayor se refiere a fechas de 1900 y si no a fechas del 2000)
     */
    private static String normalizarAnyo(int anyo) {
        String prefAnyo = "";
        if(anyo< 50) prefAnyo = "20";
        else if(anyo <= 99) prefAnyo = "19";
        return prefAnyo + (anyo < 10 ? "0" : "") + anyo;
    }
    
    /**
     * Comprueba si la instalación actual se corresponde con una vivienda
     * @return TRUE si es vivienda, FALSE si no
     */
    public static boolean esVivienda() {
        boolean vivienda = false;
        BaseDatos bd = Sesion.getInstance().getBaseDatos();
        try {
            ResultSet rs = bd.ejecSelect("SELECT INTICOD, INTINUM FROM INSTALACIONES WHERE INID=" + Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString());
            if(rs.next()) {
                String tiCod = rs.getString("INTICOD");
                int tiNum = rs.getInt("INTINUM");
                // son viviendas: E1, F1
                if(("E".equals(tiCod) || "F".equals(tiCod)) && tiNum == 1) vivienda = true;
            }
        } catch(Exception e) {
            Mensaje.error("UtilidadesSQL.java: "+e.getMessage(),e);
        }
        
        return vivienda;
    }
    
    /**
     * Comprueba si la instalación actual es una reforma
     * @return TRUE si es reforma, FALSE si no
     */
    public static boolean esReforma() {
        boolean reforma = false;
        BaseDatos bd = Sesion.getInstance().getBaseDatos();
        try {
            ResultSet rs = bd.ejecSelect("SELECT INMMID FROM INSTALACIONES WHERE INID=" + Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID).toString());
            if(rs.next()) {
                String motivo = rs.getString("INMMID");
                // si no es nueva instalación....
                if(!"NU".equals(motivo)) reforma = true;
            }
        } catch(Exception e) {
            Mensaje.error("UtilidadesSQL.java: "+e.getMessage(),e);
        }
        
        return reforma;
    }
}
