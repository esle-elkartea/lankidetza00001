/*
 * Utilidades.java
 *
 * Created on 18 de julio de 2006, 21:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package funciones;

import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

/**
 *
 * @author Enrique
 */
public class Utilidades {
    public static boolean esCorrectoDNI(String texto) {
        // Si hay menos de 9 caract, return false;
        if(!esLongitudTexto(texto, 8)) return false;
        for(int i = 0; i < texto.length(); i++) {
            char car = texto.charAt(i);
            // Si todos sus caract no son digitos, ERROR.
            if(!esCifraCaracter(car)) return false;
        }

        return true;
    }

    // Comprueba si el NIF introucido cumple: 70234534-z ó 12345678Z
//    public static boolean esCorrectoNIF_CIF2(String texto) {
//        // Si hay menos de 9 caract, return false;
//        if(!esLongitudTexto(texto, 9)) return false;
//        char primLetra = texto.charAt(0);
//        char ultCar = texto.charAt(8);
//        // Si el primer caract es una letra es un CIF, si no, entonces es un NIF
//        if(esUnaLetra(primLetra)) {
//            for(int i = 1; i <= 7; i++) {
//                char car = texto.charAt(i);
//                // Si los 7 primeros caract son digitos, OK (el último puede ser una letra o un nº)
//                if(!esCifraCaracter(car)) return false;
//            }
//        }
//        else {
//            for(int i = 0; i <= 7; i++) {
//                char car = texto.charAt(i);
//                // Si los 8 primeros caract son digitos, OK
//                if(!esCifraCaracter(car)) return false;
//            }
//
//            // Si el ultimo caract es una letra entonces NIF es correcto.
//            if(!esUnaLetra(ultCar)) return false;
//        }
//
//        return true;
//    }
    public static boolean esCorrectoNIF_CIF(String texto) {
         if(Pattern.matches("[A-Z][0-9]{7}[A-Z]|[A-Z][0-9]{8}|[0]{9}", texto))             
         {
            return true;
         } else {
             return Utilidades.esDniNifValido(texto);
         }
    }
    
    public static boolean esCorrectoNIF(String texto) {
        // Si hay menos de 9 caract, return false;
        if(!esLongitudTexto(texto, 9)) return false;
        char primLetra = texto.charAt(0);
        char ultCar = texto.charAt(8);
        for(int i = 0; i <= 7; i++) {
            char car = texto.charAt(i);
            // Si los 8 primeros caract son digitos, OK
            if(!esCifraCaracter(car)) return false;
            // Si el ultimo caract no es una letra entonces NIF es incorrecto.
            if(!esUnaLetra(ultCar)) return false;
        }

        return true;
    }

    // Comprueba el formato del email. Si es vacío es válido
    public static boolean esCorrectoEmail(String texto) {
        int arroba = 0;
        int punto = 0;
        int espacio = 0;
        if(esLongitudTexto(texto, 0)) return true;
        else {
            char caracter = texto.charAt(0);
            if((texto.charAt(0) == '.') || (texto.charAt(0) == '@')) return false;
            for(int i = 0; i < texto.length(); i++) {
                caracter = texto.charAt(i);

                if((caracter == '@')) arroba++;
                if((caracter == '.')) punto++;
                if((caracter == ' ')) espacio++;
            }

            if((espacio != 0) || (arroba != 1) || (punto < 1)) return false;
        }

        return true;
    }

    // Comprueba si un caracter es una cifra
    public static boolean esCifraCaracter(char car) {
        if((car >= 48) && (car <= 57)) return true;

        return false;
    }

    // Comprueba si un caracter es una letra del abecedario (menos la ñ)
    public static boolean esUnaLetra(char car) {
        if(((car >= 65) && (car <= 90)) || ((car >= 97) && (car <= 122))) return true;

        return false;
    }

    // Comprueba si el tamaño del texto se correponde con el deseado.
    public static boolean esLongitudTexto(String texto, int tam) {
        if(texto.length() != tam) return false;

        return true;
    }

    /**
     * Método que cambia la coma del número introducido por un punto
     *
     * @param java.lang.String texto: El texto a ser formateado
     * @return java.lang.String Texto ya formateado
     */
    public static String cambiarComa(String texto) {
        return texto == null ? "0" : cambiarCar(texto, ",", ".");
    }
    
    /**
     * Método que cambia el punto del número introducido por una coma
     *
     * @param java.lang.String texto: El texto a ser formateado
     * @return java.lang.String Texto ya formateado
     */
    public static String cambiarPunto(String texto) {
        return cambiarCar(texto, ".", ",");
    }
    
    private static String cambiarCar(String texto, String car1, String car2) {
        if((texto == null) || texto.equals("")) return "0";
        else {
            int lng = texto.length();
            String entera;
            String decimal = "";
            int indiceCar = texto.indexOf(car1);
            if(indiceCar == (-1)) entera = texto;
            else {
                entera = texto.substring(0, indiceCar);
                decimal = texto.substring(indiceCar + 1, lng);
            }

            texto = entera + car2 + decimal;

            return texto;
        }
    }

    public static String rightBack(String source, String searchFor) {
        String resultado = source;
        int index = source.lastIndexOf(searchFor) + searchFor.length();
        if(index >= 0) resultado = source.substring(index);
        return resultado;
    }

    public static String left(String source, String searchFor) {
        String resultado = source;
        int index = source.indexOf(searchFor);
        if(index > 0) resultado = source.substring(0, index);
        return resultado;
    }

    public static void igualarFormularios(JFrame destino, JFrame origen) {
        destino.setExtendedState(origen.getExtendedState());
        if(origen.getExtendedState() == JFrame.NORMAL) {
            destino.setSize(origen.getSize());
            destino.setLocation(origen.getLocation());
        }
    }

    public static String memoriaDisponible() {
        long lFreeMemory = Runtime.getRuntime().freeMemory();
        long lTotalMemory = Runtime.getRuntime().totalMemory();

        return("MEM. TOTAL=" + lTotalMemory / 1024 + " Kb" + "  LIBRE=" + lFreeMemory / 1024 + " Kb");
    }

    /**
     * Rellena con un carácter a la izquierda hasta completar la longitud deseada
     * @param cadena Cadena a rellenar
     * @param longitud Longitud deseada
     * @param car Carácter de relleno
     * @return La cadena rellena con el carácter de relleno por la izquierda
     */
    public static String rellenarCadena(String cadena, int longitud, char car) {
        if(cadena == null) return null;
        int lngRellenar = longitud - cadena.length();
        StringBuffer s = new StringBuffer();
        for(int i = 0; i < lngRellenar; i++) s.append(car);
        s.append(cadena);
        return s.toString();
    }
    
    /**
     * Rellena con ceros a la izquierda hasta completar la longitud deseada
     * @param cadena Cadena a rellenar
     * @param longitud Longitud deseada
     * @return La cadena rellena con ceros por la izquierda
     */
    public static String rellenarCeros(String cadena, int longitud) {
        return rellenarCadena(cadena, longitud, '0');
    }
    
    public static boolean esDniNifValido(String dni){
        boolean resultado = true;
        // el valor debe tener 9 posiciones, de las cuales las primeras deben ser dígitos y la última letra
        if(dni!=null){
            if(dni.length()!=9){
                resultado = false;
            } else {
                String numero=dni.substring(0,8);
                String digitoControl = dni.substring(8,9);
                // Calculamos la letra de control
                String digitoControlCalculado = calcularLetraNif(Integer.parseInt(numero));
                if(!digitoControl.equalsIgnoreCase(digitoControlCalculado)){
                    resultado = false;
                }
            }
        } else {
            resultado = false;
        }
        return resultado;
    }
 
    /**
    * Dado un numero de dni nos devulve la letra asociada al mismo
    * @param numero dni
    * @return letra asociada al dni
    */
    private static String calcularLetraNif(int numero){
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int posicion_modulo = numero%23;
        return letras.substring(posicion_modulo,posicion_modulo+1);
    }
    
    /**
     * Elimina los decimales de una cadena que contenga un número
     * @param num Número
     * @return El número con los decimales eliminados
     */
    public static String quitarDecimales(String num) {
        String resultado = "";
        if(num != null) resultado = left(num, ".");
        return resultado;
    }
    
    /**
     * Cálculo del número de fases en función de la tensión
     * @param tension Tensión
     * @return El número de fases
     */
    public static double getFases(double tension) {
        double resultado = 4;
        if(tension==400) {
            resultado = 3;
        }
        else if(tension==230) {
            resultado = 2;
        }
        return resultado;
    }
    
    /**
     * Cálculo de la intensidad
     * @param tension Tensión (V)
     * @param potencia Potencia (W)
     * @param coseno Coseno de Phi
     * @param rendimiento Rendimiento
     * @return Intensidad calculada
     */
    public static double getIntensidad(double tension, double potencia, double coseno, double rendimiento) {
        double intensidadCalculada = 0;
        double fases = getFases(tension);
         if(fases==2) {
            intensidadCalculada = potencia / (tension * coseno * rendimiento);
        }
         else {
            intensidadCalculada = potencia / (Math.sqrt(3) * tension * coseno * rendimiento);
        }
        return intensidadCalculada;
    }
    
    /**
     * Fija el tipo de letra para las etiquetas del esquema unifiliar
     */
    public static void tipoLetra(Graphics g) {
        Font fnt = new Font("SansSerif", Font.PLAIN, 10);
        g.setFont(fnt);
    }
    
    /**
     * corta líneas largas, manteniendo palabras completas, cada 80 caracteres
     */
    private static final Pattern wrapRE = Pattern.compile(".{0,79}(?:\\S(?:-| |$)|$)");
    public static String[] wordWrap(String str) {
        List list = new LinkedList();
        Matcher m = wrapRE.matcher(str);
        while (m.find()) list.add(m.group());
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * devuelve la salida de wordWrap como una cadena separada por saltos de línea
     */
    public static String joinStringLines(String str[]) {
        StringBuffer s = new StringBuffer();
        s.append("");
        for(int i = 0; i < str.length; i++) {
            s.append(str[i] + "\n");
        }
        return s.toString();
    }
}
