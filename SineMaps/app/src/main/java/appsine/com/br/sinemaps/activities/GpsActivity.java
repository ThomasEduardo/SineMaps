package appsine.com.br.sinemaps.activities;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import appsine.com.br.sinemaps.R;
import appsine.com.br.sinemaps.entidades.Sine;
import appsine.com.br.sinemaps.asynctask.GetSinesAsyncTask;

public class GpsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private LocationManager locationManager;
    private Location location;

    private double latitude;
    private double longitude;
    private ArrayList<Sine> sines = new ArrayList<>();

    private Marker mMarker;
    private String codPosto;

    private final int REQUEST_LOCATION = 200;
    private final long MINIMUM_TIME = 100;
    private final float MINIMUM_DISTANCE = 2;
    private static final String TAG = "GpsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE); //instanciação do LocationManager

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) { //checagem das persmissões. Se não for permitido, então...

            String permissions[] = {android.Manifest.permission.ACCESS_COARSE_LOCATION, //array de String das permissões
                    android.Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, REQUEST_LOCATION); //requisição das permissões
            //Leia-se, esta Activity requer estas permissões sob este request code (REQUEST_LOCATION é 200)

        } else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME, MINIMUM_DISTANCE, this);
            //Leia-se, requisitar updates na localização utilizando o GPS_PROVIDER,
            // neste tempo mínimo (milisegundos),
            // nesta distância mínima (metros)
            // e utilizando este LocationListener (o contexto atual que implementa a inferface)

            if (locationManager != null) { //caso houver atualização na localização, o locationManager não será nulo, logo
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                //capturar a última localização conhecida via internet
            }

        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) { //caso o provider estiver liberado

            if (location != null) { //e caso houver localização capturada/armazenada

                latitude = location.getLatitude(); //capturar latitude
                longitude = location.getLongitude(); //capturar longitude


            }
        } else {
            showGPSDisabledAlertToUser();
        }

        GetSinesAsyncTask asyncTask = new GetSinesAsyncTask();
        try {
            sines = asyncTask.execute("http://mobile-aceite.tcu.gov.br/mapa-da-saude/rest/emprego/latitude/" + Double.toString(latitude)
                    + "/longitude/" + Double.toString(longitude) + "/raio/100").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {  //ao mudar a localização

        latitude = location.getLatitude(); //atualizar latitude;
        longitude = location.getLongitude(); //atualizar longitude;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) ;
        }
    }

    private void showGPSDisabledAlertToUser() { //notificar usuário sobre GPS desativado
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this); //instancia AlertDialogBuilder
        //construindo notificação
        alertDialogBuilder.setMessage("GPS está desabilitado. Deseja habilitar?") //impor mensagem na notificação
                .setCancelable(false) //não permitir notificação cancelável
                .setPositiveButton("Ir para Configurações", new DialogInterface.OnClickListener() {
                    @Override //setar botão positivo, implementando interface onClickListener
                    public void onClick(DialogInterface dialog, int id) {
                        Intent configuracoesIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startService(configuracoesIntent); //direcionar para as configurações
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override //setar botão negativo, implementando interface onClickListener
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel(); //cancelar diálogo
            }
        });

        AlertDialog alert = alertDialogBuilder.create(); //criar notificação
        alert.show(); //mostrar notificação

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Instancia um objeto Latitude-Longitude com a localização do usuário
        LatLng usuario = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(usuario).title("Sua Posição")); //seta o marcador do usuário
        mMap.moveCamera(CameraUpdateFactory.newLatLng(usuario)); //centraliza no usuário

        for (int i = 0; i < sines.size(); i++) { //varre o ArrayList de sines
            String latitude = sines.get(i).getLatitude(); //captura latitude
            String longitude = sines.get(i).getLongitude(); //e longitude
            LatLng coordenadas = new LatLng(Long.parseLong(latitude), Long.parseLong(longitude)); //cria objeto LatLng
            String nome = sines.get(i).getNome(); //captura nome
            codPosto = sines.get(i).getCodPosto(); //captura o código do posto
            String telefone = sines.get(i).getTelefone(); //captura o telefone
            mMarker = mMap.addMarker(new MarkerOptions() //configura o marcador
                    .position(coordenadas)
                    .title(nome)
                    .snippet(telefone)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
            );
            mMap.setOnMarkerClickListener(this);
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(mMarker)) {
            Intent intent = new Intent(this, DetalharSineActivity.class);
            intent.putExtra("codPosto", codPosto);
            startService(intent);
        }
        return false;
    }

}
