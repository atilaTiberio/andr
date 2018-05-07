package mx.iteso.iturbeh.proyectointegrador.modelo.db;

import com.orm.SugarRecord;

/**
 * Created by iturbeh on 3/25/18.
 */

public class InegiReporteIndicador extends SugarRecord{

    String indicador;
    Integer anio;
    Double valor;

    public InegiReporteIndicador() {
    }

    public InegiReporteIndicador(String indicador, Integer anio, Double valor) {
        this.indicador = indicador;
        this.anio = anio;
        this.valor = valor;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
