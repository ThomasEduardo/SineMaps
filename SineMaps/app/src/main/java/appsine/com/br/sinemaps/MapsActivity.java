package appsine.com.br.sinemaps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import appsine.com.br.sinemaps.DataReceive;
import appsine.com.br.sinemaps.R;
import appsine.com.br.sinemaps.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Callback<List<DataReceive>> {


    private  GoogleMap mMap;
    double longitude;
    double latitude;
    private List<DataReceive> sines;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();
        latitude = extras.getDouble("latitude");
        longitude = extras.getDouble("longitude");
        sines = new ArrayList<>();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);


        LatLng actualArea = new LatLng(latitude,  longitude);
        MarkerOptions Mo = new MarkerOptions()
                .position(actualArea)
                .title("Posição atual");

        mMap.addMarker(Mo);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(actualArea, 8));

        Call<List<DataReceive>> call = ServiceGenerator.getInstance().getService().getSinesComRaio(latitude,longitude);
        Log.i(this.getClass().getName(), "Buscando Sines");
        call.enqueue(this);

    }
    @Override
    public void onResponse(Call<List<DataReceive>> call, Response<List<DataReceive>> response) {
        if (response.isSuccessful()) {
            sines = response.body();
            for (DataReceive sine : sines) {
                double lati = Double.parseDouble(sine.getLatitude());
                double longLat = Double.parseDouble(sine.getLongitude());
                mMap.addMarker(new MarkerOptions().position(
                        new LatLng(lati,longLat))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        .title(sine.getNome()));
            }
        } else {
            Log.e(this.getClass().toString(), "Erro na Busca" + response.code());         }

    }
    @Override
    public void onFailure(Call<List<DataReceive>> call, Throwable t) {

    }
}