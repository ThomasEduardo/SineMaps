package appsine.com.br.sinemaps.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

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

public class CityMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private long latitude;
    private long longitude;
    private String cidade;
    private ArrayList<Sine> sines;
    private Marker mMarker;
    private String codPosto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle bundle = getIntent().getExtras();
        latitude = bundle.getLong("Latitude");
        longitude = bundle.getLong("Longitude");
        cidade = bundle.getString("Cidade");

        GetSinesAsyncTask asyncTask = new GetSinesAsyncTask();
        try {
            sines = asyncTask.execute("http://mobile-aceite.tcu.gov.br/mapa-da-saude/rest/emprego/latitude/" + Long.toString(latitude)
                    + "/longitude/" + Long.toString(longitude) + "/raio/100").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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

        // Add a marker in Sydney and move the camera
        LatLng cidade = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(cidade).title("Referência"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cidade));
        for (int i = 0; i < sines.size(); i++){
            String latitude = sines.get(i).getLatitude();
            String longitude = sines.get(i).getLongitude();
            LatLng coordenadas = new LatLng(Long.parseLong(latitude), Long.parseLong(longitude));
            String nome = sines.get(i).getNome();
            codPosto = sines.get(i).getCodPosto();
            String telefone = sines.get(i).getTelefone(); //captura o telefone
            mMarker = mMap.addMarker(new MarkerOptions()
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
        if (marker.equals(mMarker)){
            Intent intent = new Intent (this, DetalharSineActivity.class);
            intent.putExtra("codPosto", codPosto);
            startService(intent);
        }
        return false;
    }
}
