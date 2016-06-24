package graph.beans;

import funciones.BaseDatos;
import funciones.Sesion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import main.Constantes;
import main.Mensaje;

public class SBean extends BaseBean{
    private String referencia;
    private String utilizacion;
    private double coseno;
    private double rendimiento;
    private double numeroReceptores;
    private double potencia;
    private String material;
    private double seccion;
    private double tension;
    private double tensionAislamiento;
    private String tipoInstalacion;
    private String aislamiento;
    private String tipoSalida;
    private double intensidadMaximaAdmisible;
    private double longitud;
    private double ccpia;

    public SBean() {}

    public SBean(
            String referencia,
            String utilizacion,
            double coseno,
            double rendimiento,
            double numeroReceptores,
            double potencia,
            String material,
            double seccion,
            double tension,
            double tensionAislamiento,
            String tipoInstalacion,
            String aislamiento,
            String tipoSalida,
            double intensidadMaximaAdmisible,
            double longitud
    ){
        this.setReferencia(referencia);
        this.setUtilizacion(utilizacion);
        this.setCoseno(coseno);
        this.setRendimiento(rendimiento);
        this.setNumeroReceptores(numeroReceptores);
        this.setPotencia(potencia);
        this.setMaterial(material);
        this.setSeccion(seccion);
        this.setTension(tension);
        this.setTensionAislamiento(tensionAislamiento);
        this.setTipoInstalacion(tipoInstalacion);
        this.setAislamiento(aislamiento);
        this.setTipoSalida(tipoSalida);
        this.setIntensidadMaximaAdmisible(intensidadMaximaAdmisible);
        this.setLongitud(longitud);
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getUtilizacion() {
        return utilizacion;
    }

    public void setUtilizacion(String utilizacion) {
        this.utilizacion = utilizacion;
    }

    public double getCoseno() {
        return coseno;
    }

    public void setCoseno(double coseno) {
        this.coseno = coseno;
    }

    public double getRendimiento() {
        return rendimiento;
    }

    public void setRendimiento(double rendimiento) {
        this.rendimiento = rendimiento;
    }

    public double getNumeroReceptores() {
        return numeroReceptores;
    }

    public void setNumeroReceptores(double numeroReceptores) {
        this.numeroReceptores = numeroReceptores;
    }
    
    public double getPotencia() {
        return potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }
    
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getSeccion() {
        return seccion;
    }

    public void setSeccion(double seccion) {
        this.seccion = seccion;
    }
    
    public double getTension() {
        return tension;
    }

    public void setTension(double tension) {
        this.tension = tension;
    }
    
    public double getTensionAislamiento() {
        return tensionAislamiento;
    }

    public void setTensionAislamiento(double tensionAislamiento) {
        this.tensionAislamiento = tensionAislamiento;
    }
    
    public String getTipoInstalacion() {
        return tipoInstalacion;
    }

    public void setTipoInstalacion(String tipoInstalacion) {
        this.tipoInstalacion = tipoInstalacion;
    }
    
    public String getAislamiento() {
        return aislamiento;
    }

    public void setAislamiento(String aislamiento) {
        this.aislamiento = aislamiento;
    }
    
    public String getTipoSalida() {
        return tipoSalida;
    }

    public void setTipoSalida(String tipoSalida) {
        this.tipoSalida = tipoSalida;
    }
    
    public double getIntensidadMaximaAdmisible() {
        return intensidadMaximaAdmisible;
    }

    public void setIntensidadMaximaAdmisible(double intensidadMaximaAdmisible) {
        this.intensidadMaximaAdmisible = intensidadMaximaAdmisible;
    }
    
    public double getIntensidad() {
        double resultado;
        double fases = getFases();
        if(fases==2){
            resultado=potencia/(tension*coseno*rendimiento);
        } else {
            resultado=potencia/(Math.sqrt(3)*tension*coseno*rendimiento);
        }
        return resultado;
    }
       
    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
    public double getCCPIA() {
        return ccpia;
    }

    public void setCCPIA(double ccpia) {
        this.ccpia = ccpia;
    }
    
    public double getCaidaTension(){
        double fases = getFases();
        double tension = getTension();
        return (((fases==2?2.0:1.0)*getPotencia()*getLongitud())/(getConductividad()*getSeccion()*tension));
    }
    
    public double getCaidaTensionPorcentual(){
        double fases = getFases();
        double tension = getTension();
        return (((fases==2?2.0:1.0)*getPotencia()*getLongitud())/(getConductividad()*getSeccion()*tension))*100/tension;
    }
    
    private double getConductividad(){
        double resultado = 0;
        if("Cu".equalsIgnoreCase(getMaterial())) resultado = 56;
        else if("Al".equalsIgnoreCase(getMaterial())) resultado = 35;
        return resultado;
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(getReferencia()+" \n");
        str.append(getUtilizacion()+" \n");
        str.append(DecimalFormat.getIntegerInstance().format(getNumeroReceptores())+" \n");
        str.append(DecimalFormat.getIntegerInstance().format(getPotencia())+" \n");
        str.append(DecimalFormat.getInstance().format(getTension())+" \n");
        str.append(DecimalFormat.getInstance().format(getIntensidad())+" \n");
        str.append(getSeccion()+" \n");
        str.append(DecimalFormat.getInstance().format(getLongitud()));
        return str.toString();
    }
           
    public double getFases(){
        double resultado = 2;
        if(tension==400){
            resultado = 3;
        }
        return resultado;
    }
    
    public Object clone() {
        return new SBean(this.referencia, this.utilizacion, this.coseno, this.rendimiento, this.numeroReceptores, this.potencia,
            this.material, this.seccion, this.tension, this.tensionAislamiento, this.tipoInstalacion, this.aislamiento,
            this.tipoSalida, this.intensidadMaximaAdmisible, this.longitud);
    }
        
}
