package appsine.com.br.sinemaps.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import appsine.com.br.sinemaps.R;

public class CityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
    }

    public void toCityMapsActivityCJ(View view){
        Intent intent = new Intent(this, CityMapsActivity.class);
        intent.putExtra("Latitude", -6.8897071);
        intent.putExtra("Longitude", -38.5612185);
        intent.putExtra("Cidade", "Cajazeiras");
        startService(intent);
    }

    public void toCityMapsActivityCG(View view){
        Intent intent = new Intent(this, CityMapsActivity.class);
        intent.putExtra("Latitude", -7.2290752);
        intent.putExtra("Longitude", -35.8808337);
        intent.putExtra("Cidade", "Campina Grande");
        startService(intent);
    }

    public void toCityMapsActivityJP(View view){
        Intent intent = new Intent(this, CityMapsActivity.class);
        intent.putExtra("Latitude", -7.1194958);
        intent.putExtra("Longitude", -34.8450118);
        intent.putExtra("Cidade", "João Pessoa");
        startService(intent);
    }
}
