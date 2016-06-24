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
    // Número máximo de caracteres que deseamos en el editor.  
    private int numeroMaximoCaracteres;
    //Indica si el campo va a contener numeros solamente y si estos son reales o enteros.
    private boolean soloNumeros, esNumReal; 

    /** 
     * Crea una instancia de LimitadorCaracteres. 
     *  
     * @param editor Editor en el que se quieren limitar los caracteres. 
     * @param numeroMaximoCaracteres Número máximo de caracteres que queremos en el editor.
     * @param soloNumeros Indica si el campo será solo numérico o no.
     * @param esNumReal Indica si el número es Real o no.  
     */ 
    public LimitadorCaracteres(JTextField editor, int numeroMaximoCaracteres, boolean soloNumeros, boolean esNumReal) 
    { 
        this.editor=editor; 
        this.numeroMaximoCaracteres=numeroMaximoCaracteres;
        this.soloNumeros=soloNumeros;
        this.esNumReal = esNumReal;
    }
     
    /** 
     * Método al que llama el editor cada vez que se intenta insertar caracteres. 
     * El método comprueba que no se sobrepasa el límite y tambien comprueba
     * si se debe de escribir en el JTextfield solo textos numéricos y si éstos
     * son números reales o enteros.
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
    
    //Evalúa si se está introduciendo caracteres que corresponan a números o comas dependiendo de si son num reales o enteros
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
