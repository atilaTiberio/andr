package mx.iteso.iturbeh.proyectointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import mx.iteso.iturbeh.proyectointegrador.modelo.db.ImssDeteccionModel;


/**
 * Created by iturbeh on 3/2/18.
 */

public class LoginActivity extends BaseActivity {


    EditText username;
    EditText password;
    Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username= findViewById(R.id.txt_user);
        password= findViewById(R.id.txt_password);
        validaDB();

        String userName= proyectoIntegradorSharedPreferences.getKey("userName");



        if(!userName.isEmpty() ){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

        }




    }


    public void loginFlow(View v) {

        String user=username.getText().toString();
        String pwd= password.getText().toString();


            if(!user.equals("") && !pwd.equals("")){

                if(user.equals("ms710905")&& pwd.equals("ms710905")) {
                    proyectoIntegradorSharedPreferences.savePreference("userName",user);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(LoginActivity.this,"Usuario sin permisos suficientes",Toast.LENGTH_LONG).show();
                }

            }
            else{
                Toast.makeText(LoginActivity.this,"Usuario y password son requeridos",Toast.LENGTH_LONG).show();

            }

    }

    private void validaDB(){
        int total=imssDeteccionesDBHelper.getAllDetecciones().size();

        /*
        Si no hay datos en la base, se carga el dataset
        inicialmente esto se hacia con una llamada rest
        entidad, padecimiento,valor, year
         */
        if(total==0)
        {
            InputStream stream=getResources().openRawResource(R.raw.detecciones);
            InputStreamReader isr= new InputStreamReader(stream);
            BufferedReader reader= new BufferedReader(isr);
            String temp=null;
            ImssDeteccionModel idm;
            try {
                while ((temp = reader.readLine()) != null) {
                    String[] campos= temp.split(",");
                    idm= new ImssDeteccionModel(campos[0],campos[1], Integer.parseInt(campos[2]),Integer.parseInt(campos[3]));
                    imssDeteccionesDBHelper.addDeteccion(idm);
                }
            }
            catch(Exception e){
                System.out.println(e);

            }




        }
    }




}
