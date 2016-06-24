package funciones.beans;

import funciones.ParCombo;

/**
 * Contiene los datos de una carga.
 */
public class BeanCargasInd {
    
    private ParCombo tipoCarga;
    private String denominacion;
    private String potencia;
    private String idInst;
    private String idCargInd;
         
    /** Construye una instancia de Cargas con los datos que se le pasan. */
    public BeanCargasInd(ParCombo tipoCarga, String denominacion, String potencia, String idInst, String idCargInd) 
    {
        this.tipoCarga = tipoCarga;
        this.denominacion = denominacion;
        this.potencia = potencia;
        this.idInst = idInst;
        this.idCargInd = idCargInd;
    }
    
    public BeanCargasInd()
    {
    }
    
    public ParCombo getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(ParCombo tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getPotencia() {
        return potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }

    public String getIdInst() {
        return idInst;
    }

    public void setIdInst(String idInst) {
        this.idInst = idInst;
    }

    public String getIdCargInd() {
        return idCargInd;
    }

    public void setIdCargInd(String idCargInd) {
        this.idCargInd = idCargInd;
    }
}
    
    