package mx.iteso.iturbeh.proyectointegrador;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import mx.iteso.iturbeh.proyectointegrador.modelo.db.ImssDeteccionesDBHelper;
import mx.iteso.iturbeh.proyectointegrador.services.ProyectoIntegradorSharedPreferences;

/**
 * Created by iturbeh on 5/9/18.
 *
 */

public class BaseActivity extends AppCompatActivity{

    Location location;
    LocationManager locationManager;
    Geocoder geocoder;
    private List<Address> addressList;
    ProyectoIntegradorSharedPreferences proyectoIntegradorSharedPreferences;
    ImssDeteccionesDBHelper imssDeteccionesDBHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        proyectoIntegradorSharedPreferences =new ProyectoIntegradorSharedPreferences(this, "CURRENT_USER");
        imssDeteccionesDBHelper= new ImssDeteccionesDBHelper(this);
        setPermission();

    }

    private void getLocation(double latitude, double longitude){
        geocoder= new Geocoder(this, Locale.getDefault());
        try {

            addressList = geocoder.getFromLocation(latitude, longitude, 5);
            if(addressList!=null&&addressList.size()>0) {
                proyectoIntegradorSharedPreferences.savePreference("estadoActual", addressList.get(0).getAdminArea());
            }


        } catch (IOException e) {
            e.printStackTrace();
            proyectoIntegradorSharedPreferences.deletePreference("estadoActual");
        }

    }

    protected void setPermission(){

        /*
        Si no tiene el permiso de localización
         */
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},1
            );
            return;
        }
        locationManager= (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }
        else{
            proyectoIntegradorSharedPreferences.deletePreference("estadoActual");
        }


    }

    LocationListener locationListener= new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            getLocation(location.getLatitude(),location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
            if(location!=null)
                getLocation(location.getLatitude(),location.getLongitude());
        }

        @Override
        public void onProviderDisabled(String provider) {

            proyectoIntegradorSharedPreferences.deletePreference("estadoActual");
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1)
            if(grantResults!=null && grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED&&location!=null) {
                getLocation(location.getLatitude(), location.getLongitude());
            }
            else{
            proyectoIntegradorSharedPreferences.deletePreference("estadoActual");
            }
    }


}
