package ua.ukraine.ukrroad.ukrroad.maps;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CoordinatesFromAddress extends AsyncTask<String, Void, LatLng> {
    private final String POST = "POST";
    LatLng latLng;

    @Override
    protected LatLng doInBackground(String... address) {
        StringBuffer jsonAllPlaces = new StringBuffer("");
        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + address[0].replace(" ","+") + "&sensor=false");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                jsonAllPlaces.append(line + "\n");
            }
            String result = jsonAllPlaces.toString();

            JSONObject jobj = new JSONObject(result);
            JSONObject object = jobj.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location");

            latLng = new LatLng(Double.valueOf(object.getString("lat")), Double.valueOf(object.getString("lng")));
        } catch (Exception e1) {
            e1.printStackTrace();

        }
        return latLng;
    }

    @Override
    protected void onPostExecute(LatLng ll) {
        super.onPostExecute(ll);
    }
}
