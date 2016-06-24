/*
 * Main.java
 *
 * Created on 26 de mayo de 2006, 9:31
 *
 */
package main;

import forms.*;
import funciones.BaseDatos;
import funciones.Sesion;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * @author sanjose
 */
public class Main {

    /**
     * Creates a new instance of Main
     */
    public Main() {}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Sesion.getInstance().setBaseDatos(new BaseDatos());
        
        try {
            Sesion.getInstance().getBaseDatos().conectar();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            FrmEntrada frm = new FrmEntrada();
            Sesion.getInstance().setFrmEntrada(frm);
            frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frm.setSize(1024, 768);
            frm.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frm.setVisible(true);
        }
        catch (Exception ex) { 
            Mensaje.error(ex.getMessage());
            System.exit(0);
        }  
    }
}
