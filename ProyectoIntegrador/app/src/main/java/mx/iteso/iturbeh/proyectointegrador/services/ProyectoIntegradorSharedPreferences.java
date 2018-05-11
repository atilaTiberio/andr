package mx.iteso.iturbeh.proyectointegrador.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Map;

public class ProyectoIntegradorSharedPreferences {

    private Activity activity;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public ProyectoIntegradorSharedPreferences(Activity activity, String keyShared) {
        this.activity = activity;
        sharedPreferences= activity.getSharedPreferences(keyShared, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

    }

    public Map<String,String> getAll(){

        Map<String,String> valores= (Map<String, String>) sharedPreferences.getAll();

        return valores;
    }

    public String getKey(String key){

        return sharedPreferences.getString(key,"");
    }
    public void savePreference(String key, String value){

        editor.putString(key,value);
        editor.commit();

    }


    public void deletePreference(String key){


        if(!sharedPreferences.getString(key,"").isEmpty())
            editor.remove(key);

        editor.commit();

    }

    public void cleanPreferences(){
        editor.clear();
        editor.commit();
    }

}
