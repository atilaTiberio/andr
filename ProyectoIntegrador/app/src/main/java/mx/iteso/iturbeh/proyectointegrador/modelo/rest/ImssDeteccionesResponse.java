package mx.iteso.iturbeh.proyectointegrador.modelo.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by iturbeh on 5/7/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImssDeteccionesResponse {


    private String entidad;
    private String padecimiento;
    private Integer valor;
    private Integer year;

    public ImssDeteccionesResponse() {
    }

    public ImssDeteccionesResponse(String entidad, String padecimiento, Integer valor, Integer year) {
        this.entidad = entidad;
        this.padecimiento = padecimiento;
        this.valor = valor;
        this.year = year;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getPadecimiento() {
        return padecimiento;
    }

    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}