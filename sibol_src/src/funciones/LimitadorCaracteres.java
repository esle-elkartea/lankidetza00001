/*
 * LimitadorCaracteres.java
 *
 * Created on 17 de julio de 2006, 21:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package funciones;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitadorCaracteres extends PlainDocument 
{ 
    //El editor del que estamos limitando caracteres. 
    private JTextField editor;  
    // N�mero m�ximo de caracteres que deseamos en el editor.  
    private int numeroMaximoCaracteres;
    //Indica si el campo va a contener numeros solamente y si estos son reales o enteros.
    private boolean soloNumeros, esNumReal; 

    /** 
     * Crea una instancia de LimitadorCaracteres. 
     *  
     * @param editor Editor en el que se quieren limitar los caracteres. 
     * @param numeroMaximoCaracteres N�mero m�ximo de caracteres que queremos en el editor.
     * @param soloNumeros Indica si el campo ser� solo num�rico o no.
     * @param esNumReal Indica si el n�mero es Real o no.  
     */ 
    public LimitadorCaracteres(JTextField editor, int numeroMaximoCaracteres, boolean soloNumeros, boolean esNumReal) 
    { 
        this.editor=editor; 
        this.numeroMaximoCaracteres=numeroMaximoCaracteres;
        this.soloNumeros=soloNumeros;
        this.esNumReal = esNumReal;
    }
     
    /** 
     * M�todo al que llama el editor cada vez que se intenta insertar caracteres. 
     * El m�todo comprueba que no se sobrepasa el l�mite y tambien comprueba
     * si se debe de escribir en el JTextfield solo textos num�ricos y si �stos
     * son n�meros reales o enteros.
     */ 
    public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException 
    { 
        if(soloNumeros && !(editor.getText().length()+arg1.length()>this.numeroMaximoCaracteres))
        {    
            if(esCifraValida(arg1, esNumReal)) 
                super.insertString(arg0, arg1, arg2);
             else
                 return;
        }
        else if (editor.getText().length()+arg1.length()>this.numeroMaximoCaracteres)
            return;        
        else
            super.insertString(arg0, arg1, arg2); 
    }
    
    //Eval�a si se est� introduciendo caracteres que corresponan a n�meros o comas dependiendo de si son num reales o enteros
    private boolean esCifraValida(String arg, boolean esNumReal)
    {
        char letra = arg.charAt(0);
        
        if(esNumReal)
        {
            if(letra>=48 && letra<=57 || letra==',')
                return true;
        }        
        else
        {    
            if(letra>=48 && letra<=57)
                return true;
        }
        return false;
    }
         
}
