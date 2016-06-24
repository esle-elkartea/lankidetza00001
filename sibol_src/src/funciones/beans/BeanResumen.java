/*
 * BeanResumen.java
 *
 * Created on 29 de agosto de 2006, 11:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package funciones.beans;

import funciones.ParCombo;

/**
 *
 * @author enrique
 */
public class BeanResumen {
    
    private ParCombo tipoCircuito;
    private String descCircuito;
    private String potCalculo;
    private String TensionCalculo;
    private String intCalculo;
    private String numCondSecc;
    private String aislamTension;
    private ParCombo tipoInst;
    private String intMax;
    private String ccpia;
    private String longitud;
    private String caidaTension;
    private String idDatosCircuito;
    private String idInst;

    public ParCombo getTipoCircuito() {
        return tipoCircuito;
    }

    public void setTipoCircuito(ParCombo tipoCircuito) {
        this.tipoCircuito = tipoCircuito;
    }

    public String getDescCircuito() {
        return descCircuito;
    }

    public void setDescCircuito(String descCircuito) {
        this.descCircuito = descCircuito;
    }

    public String getPotCalculo() {
        return potCalculo;
    }

    public void setPotCalculo(String potCalculo) {
        this.potCalculo = potCalculo;
    }

    public String getTensionCalculo() {
        return TensionCalculo;
    }

    public void setTensionCalculo(String TensionCalculo) {
        this.TensionCalculo = TensionCalculo;
    }

    public String getIntCalculo() {
        return intCalculo;
    }

    public void setIntCalculo(String intCalculo) {
        this.intCalculo = intCalculo;
    }

    public String getNumCondSecc() {
        return numCondSecc;
    }

    public void setNumCondSecc(String numCondSecc) {
        this.numCondSecc = numCondSecc;
    }

    public String getAislamTension() {
        return aislamTension;
    }

    public void setAislamTension(String aislamTension) {
        this.aislamTension = aislamTension;
    }

    public ParCombo getTipoInst() {
        return tipoInst;
    }

    public void setTipoInst(ParCombo tipoInst) {
        this.tipoInst = tipoInst;
    }

    public String getIntMax() {
        return intMax;
    }

    public void setIntMax(String intMax) {
        this.intMax = intMax;
    }

    public String getCcpia() {
        return ccpia;
    }

    public void setCcpia(String ccpia) {
        this.ccpia = ccpia;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getCaidaTension() {
        return caidaTension;
    }

    public void setCaidaTension(String caidaTension) {
        this.caidaTension = caidaTension;
    }   

    public String getIdDatosCircuito() {
        return idDatosCircuito;
    }

    public void setIdDatosCircuito(String idDatosCircuito) {
        this.idDatosCircuito = idDatosCircuito;
    }

    public String getIdInst() {
        return idInst;
    }

    public void setIdInst(String idInst) {
        this.idInst = idInst;
    }
}