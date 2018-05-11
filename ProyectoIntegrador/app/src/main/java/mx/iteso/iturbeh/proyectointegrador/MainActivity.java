package mx.iteso.iturbeh.proyectointegrador;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mx.iteso.iturbeh.proyectointegrador.adapter.ItemDeteccionAdapter;
import mx.iteso.iturbeh.proyectointegrador.modelo.db.ImssDeteccionModel;
import mx.iteso.iturbeh.proyectointegrador.modelo.db.ImssDeteccionesDBHelper;
import mx.iteso.iturbeh.proyectointegrador.modelo.rest.ImssDeteccionesResponse;
import mx.iteso.iturbeh.proyectointegrador.restclient.BaseRestCall;
import mx.iteso.iturbeh.proyectointegrador.services.ContenidoEstatico;

/**
 * Created by iturbeh on 5/7/18.
 * Carga el recyclerView de la lista de detecciones
 *
 */

public class MainActivity extends BaseGraphActivity<List<ImssDeteccionesResponse>> implements SensorEventListener {

    ListView listView;
    TextView tituloPrincipal;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    String lightSensorReading;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.main_list_view);
        tituloPrincipal=findViewById(R.id.main_title);

        int total=imssDeteccionesDBHelper.getAllDetecciones().size();
        Settings.System.putInt(this.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if(lightSensor != null){
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }


        /*
        El servicio ya no esta disponible,
        if(total==0) {
            String url = "http://stg.nodo_android:28017/salud/padecimientos/find";
            BaseRestCall baseRestCall = new BaseRestCall(this, ImssDeteccionesResponse.class);
            baseRestCall.execute(url, "/rows");
        }
        */


        loadListView();


    }

    private void loadListView(){
        ItemDeteccionAdapter itemDeteccionAdapter;


        String estado=proyectoIntegradorSharedPreferences.getKey("estadoActual");

        int total=imssDeteccionesDBHelper.getAllDetecciones().size();
        /*
            Si la data existe en la base ya no se hará la llamada rest
         */

       if(total>0){
            List<ImssDeteccionModel> groupPadecimientos;

            if(estado!=null && estado.length()>0){
                groupPadecimientos=imssDeteccionesDBHelper.groupByPadecimiento(estado);
                tituloPrincipal.setText(String.format("Detecciones en %s 2000-20015",estado));
            }
            else{
                groupPadecimientos=imssDeteccionesDBHelper.groupByPadecimiento();
                tituloPrincipal.setText(String.format("Detecciones en 2000-20015",estado));
            }

            itemDeteccionAdapter= new ItemDeteccionAdapter(this,GraphPadecimientoYearActivity.class,0,groupPadecimientos);
            listView.setAdapter(itemDeteccionAdapter);

        }




    }


    @Override
    public void onRestSuccess(List<ImssDeteccionesResponse> imssDeteccionesResponseList) {
        ImssDeteccionModel imssDetecciones=null;

        for(ImssDeteccionesResponse ite: imssDeteccionesResponseList){
            imssDetecciones= new ImssDeteccionModel(ite.getEntidad(),ite.getPadecimiento(),ite.getValor(),ite.getYear());
            imssDeteccionesDBHelper.addDeteccion(imssDetecciones);

        }







    }

    @Override
    public void onRestFail() {
        Toast.makeText(this,"Erro al ejecutar llamada Rest",Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Resume");
        setPermission();
        loadListView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("Restart");
        setPermission();
        loadListView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("start");
        setPermission();
        loadListView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("pause");
        setPermission();
        loadListView();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            lightSensorReading = String.valueOf(event.values[0]);
          //  System.out.println("------ "+lightSensorReading);

            int value= 0;
            try {
                value = Settings.System.getInt(this.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS);

                if(!lightSensorReading.contains("0.0"))
                    Settings.System.putInt(this.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,((int)event.values[0])%100);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }


        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }



}
