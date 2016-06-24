
/*
 * Mensaje.java
 *
 * Created on 31 de mayo de 2006, 11:09
 *
 */
package main;

import forms.DlgMensaje;
import funciones.Sesion;
import funciones.Utilidades;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sanjose
 */
public class Mensaje {
    public static void aviso(String s) {
        aviso(s, true);
    }
    public static void aviso(String s, boolean dialogo) {
        //if(dialogo) JOptionPane.showMessageDialog(null, s, "Aviso", JOptionPane.WARNING_MESSAGE);
        if(dialogo) mensaje("Aviso", s);
        System.out.println(fechaHoraActual() + " [AVISO] " + s);
    }

    public static void error(String s) {
        error(s, true);
    }
    public static void error(String s, boolean dialogo) {
        error(s, null, dialogo);
    }
    public static void error(String s, Exception e) {
        error(s, e, true);
    }
    public static void error(String s, Exception e, boolean dialogo) {
        //if(dialogo) JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
        if(dialogo) mensaje("Error", s);
        System.out.println(fechaHoraActual() + " [ERROR] " + s);
        if(e != null) e.printStackTrace();
    }
    
    public static void info(String s) {
        info(s, true);
    }
    public static void info(String s, boolean dialogo) {
        //if(dialogo) JOptionPane.showMessageDialog(null, s, "Información", JOptionPane.INFORMATION_MESSAGE);
        if(dialogo) mensaje("Información", s);
        System.out.println(fechaHoraActual() + " [INFORMACION] " + s);
    }

    private static void mensaje(String titulo, String mensaje) {
        DlgMensaje dlgMensaje = new DlgMensaje(Sesion.getInstance().getFrmEntrada(), true);
        dlgMensaje.setLocationRelativeTo(Sesion.getInstance().getFrmEntrada());
        dlgMensaje.setTitulo(titulo);
        dlgMensaje.setMensaje(Utilidades.joinStringLines(Utilidades.wordWrap(mensaje)));
        dlgMensaje.setVisible(true);
    }

    private static String fechaHoraActual() {
        String format = "dd-MM-yyyy HH:mm:ss";
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String datenewformat = formatter.format(today);
        return  datenewformat;
    }
}
