package mx.iteso.iturbeh.proyectointegrador.services;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iturbeh on 5/7/18.
 */

public class ContenidoEstatico {


    public static List<String> padecimientos(){

        List<String> padecimientos= new ArrayList<>();
        padecimientos.add("Hipertensión");
        padecimientos.add("Diabetes");
        padecimientos.add("Cáncer cervico");
        padecimientos.add("Cáncer mamario");

        return padecimientos;
    }

    public static List<String> estados(){
        List<String> estados= new ArrayList<>();
        estados.add("Aguascalientes");
        estados.add("Baja California");
        estados.add("Baja California Sur");
        estados.add("Campeche");
        estados.add("Coahuila");
        estados.add("Colima");
        estados.add("Chiapas");
        estados.add("Chihuahua");
        estados.add("D.F.");
        estados.add("Durango");
        estados.add("Guanajuato");
        estados.add("Guerrero");
        estados.add("Hidalgo");
        estados.add("Jalisco");
        estados.add("México");
        estados.add("Michoacán");
        estados.add("Morelos");
        estados.add("Nayarit");
        estados.add("Nuevo León");
        estados.add("Oaxaca");
        estados.add("Puebla");
        estados.add("Querétaro");
        estados.add("Quintana Roo");
        estados.add("San Luis Potosí");
        estados.add("Sinaloa");
        estados.add("Sonora");
        estados.add("Tabasco");
        estados.add("Tamaulipas");
        estados.add("Tlaxcala");
        estados.add("Veracruz");
        estados.add("Yucatán");
        estados.add("Zacatecas");
        return estados;

    }

}
