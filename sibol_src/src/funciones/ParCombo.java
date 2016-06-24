/*
 * ParCombo.java
 *
 * Created on 7 de agosto de 2006, 8:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package funciones;

/**
 *
 * @author enrique
 */
public class ParCombo {
    
private int keyInt, keyInt2; 
private String keyString, keyString2, keyString3;
private String description; 

public ParCombo() { 
super(); 
} 

public ParCombo(int aKey, String aDesc) { 
    setKeyInt(aKey); 
    setDescription(aDesc); 
}
public ParCombo(int aKey , int aKey2 , String aDesc) { 
    setKeyInt(aKey);
    setKeyInt2(aKey2);
    setDescription(aDesc); 
}
public ParCombo(String keyString, String aDesc) { 
    setKeyString(keyString); 
    setDescription(aDesc); 
}
public ParCombo(String keyString, int keyInt, String aDesc) { 
    setKeyString(keyString);
    setKeyInt(keyInt);
    setDescription(aDesc); 
}
public ParCombo(String keyString1, String keyString2, String aDesc) { 
    setKeyString(keyString);
    setKeyString2(keyString2);
    setDescription(aDesc); 
}

public ParCombo(String keyString1, String keyString2, String keyString3, String aDesc) { 
    setKeyString(keyString);
    setKeyString2(keyString2);
    setKeyString2(keyString3);
    setDescription(aDesc); 
} 

    public int getKeyInt() { 
        return keyInt; 
    } 
    public void setKeyInt(int aKey) { 
        keyInt = aKey; 
    }
    public int getKeyInt2() { 
        return keyInt2; 
    } 
    public void setKeyInt2(int aKey) { 
        keyInt2 = aKey; 
    } 
    public String getDescription() { 
        return description; 
    } 
    public void setDescription(String aDesc) { 
        description = aDesc; 
    } 
    public String getKeyString() {
        return keyString;
    }
    public String getKeyString2() {
        return keyString2;
    }
    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }
    private void setKeyString2(String keyString2) {
        this.keyString2 = keyString2;
    }
    
    public String toString() { 
        return description; 
    } 
}