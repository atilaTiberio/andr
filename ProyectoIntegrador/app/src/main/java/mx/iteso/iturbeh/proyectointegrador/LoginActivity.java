package mx.iteso.iturbeh.proyectointegrador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import mx.iteso.iturbeh.proyectointegrador.modelo.db.UserSession;
import mx.iteso.iturbeh.proyectointegrador.services.ProyectoIntegradorSharedPreferences;


/**
 * Created by iturbeh on 3/2/18.
 */

public class LoginActivity extends Activity {


    EditText username;
    EditText password;
    Button login;
    ProyectoIntegradorSharedPreferences proyectoIntegradorSharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username= findViewById(R.id.txt_user);
        password= findViewById(R.id.txt_password);


        proyectoIntegradorSharedPreferences= new ProyectoIntegradorSharedPreferences(this, "CURRENT_USER");
        String userName= proyectoIntegradorSharedPreferences.getKey("userName");

        if(!userName.isEmpty() ){
            /*
            Se busca la sesion activa y su access_token en la db
             */

            List<UserSession> userSessionList=UserSession.find(UserSession.class,"user_name=?",userName);
            //Si ya existe la sesion de usuario se inicia la actividad de mainPage
            if(!userSessionList.isEmpty()){

                Intent intent = new Intent(LoginActivity.this, RestExampleActivity.class);
                startActivity(intent);
            }
        }



    }


    public void loginFlow(View v) {

        String user=username.getText().toString();
        String pwd= password.getText().toString();




            if(!user.equals("") && !pwd.equals("")){

                if(user.equals("ms710905")&& pwd.equals("ms710905")) {
                    /*
                           Se crea la sharedPreferences y se inserta el registro en db
                     */
                    proyectoIntegradorSharedPreferences.savePreference("userName",user);
                    UserSession userSession= new UserSession(user,"ACCESS_TOKEN");
                    userSession.save();
                    Intent intent = new Intent(LoginActivity.this, RestExampleActivity.class);
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




}
