
//package appsine.com.br.sinemaps.activities;

import appsine.com.br.sinemaps.ServiceGenerator;

// public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Callback<List<Sine>> {

/*
    private  GoogleMap mMap;
    double longitude;
    double latitude;
    private List<Sine> sines;


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

        Call<List<Sine>> call = ServiceGenerator.getInstance().getService().getSinesComRaio(latitude,longitude);
        Log.i(this.getClass().getName(), "Buscando Sines");
        call.enqueue(this);

    }
    @Override
    public void onResponse(Call<List<Sine>> call, Response<List<Sine>> response) {
        if (response.isSuccessful()) {
            sines = response.body();
            for (Sine sine : sines) {
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
    public void onFailure(Call<List<Sine>> call, Throwable t) {

    }
}