package appsine.com.br.sinemaps.asynctask;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import appsine.com.br.sinemaps.entidades.Sine;


/**
 * Created by sarah on 07/03/2017.
 */

public class DetalharSinesAsyncTask extends AsyncTask<String, Void, Sine> {

    @Override
    protected Sine doInBackground(String strings[]) {

        Sine sine = new Sine();

        try {

            Url url = new Url(strings[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            String json = inputStream.toString();
            Gson gson = new Gson();
            sine = gson.fromJson(json, Sine.class);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            return sine;
        }

        return sine;

    }

    @Override
    protected void onPostExecute(Sine result) {
        super.onPostExecute(result);
    }

}
