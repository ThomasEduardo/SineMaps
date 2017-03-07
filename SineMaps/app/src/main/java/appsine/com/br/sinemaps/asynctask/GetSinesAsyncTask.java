package appsine.com.br.sinemaps.asynctask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import appsine.com.br.sinemaps.entidades.Sine;


/**
 * Created by sarah on 07/03/2017.
 */

public class GetSinesAsyncTask extends AsyncTask<String, Void, ArrayList<Sine>> {
    @Override
    protected ArrayList<Sine> doInBackground(String strings[]) {

        ArrayList<Sine> sines = new ArrayList<>();

        try {

            Url url = new Url(strings[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            String json = inputStream.toString();
            Gson gson = new Gson();
            sines = gson.fromJson(json, new TypeToken<ArrayList<Sine>>() {
            }.getType());

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            return sines;
        }

        return sines;

    }

    @Override
    protected void onPostExecute(ArrayList<Sine> result) {
        super.onPostExecute(result);
    }
}
