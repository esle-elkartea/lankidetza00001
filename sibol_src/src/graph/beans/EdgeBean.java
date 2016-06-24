package graph.beans;

import funciones.BaseDatos;
import funciones.Sesion;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.Mensaje;

public class EdgeBean extends BaseBean{
    private String tipo;
    private String otroDescripcion;
    //private String lgaPuntoConexion;
    private double lgaTension;
    private String lgaTipoInstalacion;
    private String lgaTipo;
    private double lgaSeccion;
    private String lgaMaterial;
    private String lgaAislamiento;
    private double lgaTensionAislamiento;
    private double lgaLongitud;
    private String diTipo;
    private String diTipoInstalacion;
    private double diSeccion;
    private String diMaterial;
    private String diComposicion;
    private String diAislamiento;
    private double diTensionAislamiento;
    private double diLongitud;
    private double diCoseno;
    private double diRendimiento;
    private String diTipoDerivacion;
    private String diDescripcion;
    private String tipoCable;
    private double proteccion;
    private double potencia;
    private String tension1;
    private String tension2;

    public EdgeBean() {}

    public EdgeBean(
            String tipo,
            String otroDescripcion,
            //String lgaPuntoConexion,
            String lgaTipo,
            double lgaSeccion,
            String lgaMaterial,
            double lgaLongitud,
            String diTipo,
            double diSeccion,
            String diMaterial,
            String diComposicion,
            String diAislamiento,
            double diTensionAislamiento,
            double diLongitud,
            double diCoseno,
            double diRendimiento,
            String diTipoDerivacion,
            String diDescripcion,
            String tipoCable,
            double proteccion,
            double potencia,
            String tension1,
            String tension2
    ){
        this.setTipo(tipo);
        this.setOtroDescripcion(otroDescripcion);
        //this.setLgaPuntoConexion(lgaPuntoConexion);
        this.setLgaTipo(lgaTipo);
        this.setLgaSeccion(lgaSeccion);
        this.setLgaMaterial(lgaMaterial);
        this.setLgaLongitud(lgaLongitud);
        this.setDiTipo(diTipo);
        this.setDiSeccion(diSeccion);
        this.setDiMaterial(diMaterial);
        this.setDiComposicion(diComposicion);
        this.setDiAislamiento(diAislamiento);
        this.setDiTensionAislamiento(diTensionAislamiento);
        this.setDiLongitud(diLongitud);
        this.setDiCoseno(diCoseno);
        this.setDiRendimiento(diRendimiento);
        this.setDiTipoDerivacion(diTipoDerivacion);
        this.setDiDescripcion(diDescripcion);
        this.setTipoCable(tipoCable);
        this.setProteccion(proteccion);
        this.setPotencia(potencia);
        this.setTension1(tension1);
        this.setTension2(tension2);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getOtroDescripcion() {
        return otroDescripcion;
    }

    public void setOtroDescripcion(String otroDescripcion) {
        this.otroDescripcion = otroDescripcion;
    }

    /*public String getLgaPuntoConexion() {
        return lgaPuntoConexion;
    }*/

    /*public void setLgaPuntoConexion(String lgaPuntoConexion) {
        this.lgaPuntoConexion = lgaPuntoConexion;
    }*/

    public double getLgaTension() {
        return lgaTension;
    }

    public void setLgaTension(double lgaTension) {
        this.lgaTension = lgaTension;
    }
    
    public String getLgaTipoInstalacion() {
        return lgaTipoInstalacion;
    }

    public void setLgaTipoInstalacion(String lgaTipoInstalacion) {
        this.lgaTipoInstalacion = lgaTipoInstalacion;
    }
    
    public String getLgaTipo() {
        return lgaTipo;
    }

    public void setLgaTipo(String lgaTipo) {
        this.lgaTipo = lgaTipo;
    }

    public double getLgaSeccion() {
        return lgaSeccion;
    }

    public void setLgaSeccion(double lgaSeccion) {
        this.lgaSeccion = lgaSeccion;
    }

    public String getLgaMaterial() {
        return lgaMaterial;
    }

    public void setLgaMaterial(String lgaMaterial) {
        this.lgaMaterial = lgaMaterial;
    }
    
    public String getLgaAislamiento() {
        return lgaAislamiento;
    }
    
    public void setLgaAislamiento(String lgaAislamiento) {
        this.lgaAislamiento = lgaAislamiento;
    }
    
    public double getLgaTensionAislamiento() {
        return lgaTensionAislamiento;
    }
    
    public void setLgaTensionAislamiento(double lgaTensionAislamiento) {
        this.lgaTensionAislamiento = lgaTensionAislamiento;
    }

    public double getLgaLongitud() {
        return lgaLongitud;
    }

    public void setLgaLongitud(double lgaLongitud) {
        this.lgaLongitud = lgaLongitud;
    }
    
    public String getDiTipoInstalacion() {
        return diTipoInstalacion;
    }

    public void setDiTipoInstalacion(String diTipoInstalacion) {
        this.diTipoInstalacion = diTipoInstalacion;
    }

    public String getDiTipo() {
        return diTipo;
    }

    public void setDiTipo(String diTipo) {
        this.diTipo = diTipo;
    }

    public double getDiSeccion() {
        return diSeccion;
    }

    public void setDiSeccion(double diSeccion) {
        this.diSeccion = diSeccion;
    }

    public String getDiMaterial() {
        return diMaterial;
    }

    public void setDiMaterial(String diMaterial) {
        this.diMaterial = diMaterial;
    }

    public String getDiComposicion() {
        return diComposicion;
    }

    public void setDiComposicion(String diComposicion) {
        this.diComposicion = diComposicion;
    }

    public String getDiAislamiento() {
        return diAislamiento;
    }
    
    public void setDiAislamiento(String diAislamiento) {
        this.diAislamiento = diAislamiento;
    }
    
    public double getDiTensionAislamiento() {
        return diTensionAislamiento;
    }
    
    public void setDiTensionAislamiento(double diTensionAislamiento) {
        this.diTensionAislamiento = diTensionAislamiento;
    }

    public double getDiLongitud() {
        return diLongitud;
    }

    public void setDiLongitud(double diLongitud) {
        this.diLongitud = diLongitud;
    }

    public double getDiCoseno() {
        return diCoseno;
    }

    public void setDiCoseno(double diCoseno) {
        this.diCoseno = diCoseno;
    }

    public double getDiRendimiento() {
        return diRendimiento;
    }

    public void setDiRendimiento(double diRendimiento) {
        this.diRendimiento = diRendimiento;
    }
    
    public String getDiTipoDerivacion() {
        return diTipoDerivacion;
    }

    public void setDiTipoDerivacion(String diTipoDerivacion) {
        this.diTipoDerivacion = diTipoDerivacion;
    }
    
    public String getDiDescripcion() {
        return diDescripcion;
    }

    public void setDiDescripcion(String diDescripcion) {
        this.diDescripcion = diDescripcion;
    }
    
    public String getTipoCable() {
        return tipoCable;
    }
    
    public void setTipoCable(String tipoCable) {
        this.tipoCable = tipoCable;
    }

    public double getProteccion() {
        return proteccion;
    }
    
    public void setProteccion(double proteccion) {
        this.proteccion = proteccion;
    }
    
    public double getPotencia() {
        return potencia;
    }
    
    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }
    
    public String getTension1() {
        return tension1;
    }
    
    public void setTension1(String tension1) {
        this.tension1 = tension1;
    }
    
    public String getTension2() {
        return tension2;
    }
    
    public void setTension2(String tension2) {
        this.tension2 = tension2;
    }
    
    public String getTension() {
        return this.tension1;
    }
       
    @Override
    public String toString(){
        StringBuffer str = new StringBuffer();
        if("Otro".equals(getTipo())){
            str.append(getOtroDescripcion());
        } else if("DI".equals(getTipo())){
            str.append("DI\n");
            str.append(getDiSeccion()+" mm\u00B2 ("+recuperaDescripcionMaterial(getDiMaterial())+")\n");
            str.append(getDiAislamiento()+" "+getDiTensionAislamiento());
        } else if("LA".equals(getTipo())){
            str.append("LGA\n");
            str.append(getLgaSeccion()+" mm\u00B2 ("+recuperaDescripcionMaterial(getLgaMaterial())+")\n");
            str.append(getLgaAislamiento()+" "+getLgaTensionAislamiento());
        }
        return str.toString();
    }

    private String recuperaDescripcionMaterial(String codigo) {
        String resultado = codigo;
        ResultSet rs = null;
        BaseDatos bd = null;
        try {
            bd = Sesion.getInstance().getBaseDatos();
            rs = bd.ejecSelect("SELECT MADESC FROM MATERIALES WHERE MAID='"+codigo+"'");
            if(rs.next()) {
                resultado = rs.getString("MADESC");
            }
        } catch(SQLException e){
            Mensaje.error("EdgeBean.java. "+e.getMessage(), e, false);
        }
        return resultado;
    }
    
    @Override
    public Object clone() {
        return new EdgeBean(this.tipo, this.otroDescripcion, this.lgaTipo, this.lgaSeccion, this.lgaMaterial, this.lgaLongitud, this.diTipo,
           this.diSeccion, this.diMaterial, this.diComposicion, this.diAislamiento, this.diTensionAislamiento, this.diLongitud, this.diCoseno,
           this.diRendimiento, this.diTipoDerivacion, this.diDescripcion, this.tipoCable, this.proteccion, this.potencia, this.tension1, this.tension2);
    }
}
