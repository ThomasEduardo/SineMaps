package appsine.com.br.sinemaps.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import appsine.com.br.sinemaps.R;
import appsine.com.br.sinemaps.entidades.Sine;
import appsine.com.br.sinemaps.asynctask.DetalharSinesAsyncTask;

public class DetalharSineActivity extends Activity {

    TextView nome = (TextView) findViewById(R.id.nome);
    TextView telefone = (TextView) findViewById(R.id.telefone);
    TextView entidadeConveniada = (TextView) findViewById(R.id.entConv);
    TextView bairro = (TextView) findViewById(R.id.bairro);
    TextView cep = (TextView) findViewById(R.id.cep);
    TextView uf = (TextView) findViewById(R.id.uf);
    TextView endereco = (TextView) findViewById(R.id.endereco);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar_sine);

        Bundle bundle = getIntent().getExtras();
        String codPosto = bundle.getString("codPosto");
        DetalharSinesAsyncTask asyncTask = new DetalharSinesAsyncTask();

        Sine sine = new Sine();
        try {
            sine = asyncTask.execute("/rest/emprego/cod/" + codPosto).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        nome.setText(sine.getNome());
        telefone.setText(sine.getTelefone());
        entidadeConveniada.setText(sine.getEntidadeConveniada());
        bairro.setText(sine.getBairro());
        cep.setText(sine.getCep());
        uf.setText(sine.getUf());
        endereco.setText(sine.getEndereco());
    }
}
