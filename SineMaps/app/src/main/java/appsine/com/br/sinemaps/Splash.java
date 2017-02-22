
import android.app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;





import java.util.Timer;
import java.util.TimerTask;

import appsine.com.br.sinemaps.MapsActivity;
import appsine.com.br.sinemaps.R;


public class Splash extends Activity implements LocationListener,Runnable{


    // Location
    private LocationManager locationManager;
    private Location location;
    private double latitude;
    private double longitude;

    // Configuração
    private final int REQUEST_LOCATION = 200;
    private static final String TAG = "SineMaps";

    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        Handler handler = new Handler();
        handler.postDelayed (this, 60000);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

   //habilitar gps
    private void showGPSDisabledAlertToUser() {

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("O GPS está desabilitado no seu dispositivo. Deseja habilitar?")
                .setCancelable(false)
                .setPositiveButton("Direcione para as configurações para habilitar o GPS.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        android.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        startMapsActivity();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.i(TAG, "Status Changed");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.i(TAG, "enabled");

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, "Disabled");
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }
    }

    @Override
    public void run() {
        // Inicializar comunicação com o módulo de GPS do dispositivo móvel.
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);

        /*
         * Verificar permissões de acesso. Desde a API 23 o usuário também precisa conscentir expressamente
         * que permite o acesso ao GPS. A permissão do manifest também precisa ser adicionada.
         */
        if (ActivityCompat.checkSelfPermission(Splash.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Splash.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Requisitar permissão de acesso ao usuário.
            ActivityCompat.requestPermissions(Splash.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);

        } else {

            // Caso a permissão já conscentida atualizar a localização.
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, Splash.this);

            if (locationManager != null) {
                // Localização atualizada.
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            if (location != null) {

                // Posições do GPS
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                startMapsActivity();
            }else{
                showGPSDisabledAlertToUser();
            }
        }

    }


    private void startMapsActivity(){

        intent = new Intent(Splash.this, MapsActivity.class);
        bundle = new Bundle();

        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);

        intent.putExtras(bundle);
        startActivity(intent);
        finish();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            locationManager.removeUpdates(this);
            locationManager = null;
        } catch (SecurityException ex) {
            Log.e(TAG, "Erro", ex);
        }
    }


    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    // super.onCreate(savedInstanceState);
    // setContentView(R.layout.splash);

    //Thread timerThread = new Thread(){
    //    public void run(){
    //          try{
    //          sleep(5000);
    //      }catch(InterruptedException e){
    //          e.printStackTrace();
    //      }finally{
    //          Intent intent = new Intent(Splash.this,MapsActivity.class);
    //          startActivity(intent);
    //          }
    //  }
    //};
    //timerThread.start();
    // }



}
