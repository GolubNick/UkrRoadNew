package ua.ukraine.ukrroad.ukrroad.maps;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class AddressFromCoordinates extends AsyncTask<LatLng, Void, String> {
    private final String POST = "POST";

    String nameAddress1 = "";
    String mCountryName = "";
    String mLocationName = "";
    String mRouteName = "";
    String mStreetNumber = "";

    @Override
    protected String doInBackground(LatLng... latLng) {
        StringBuffer jsonAllPlaces = new StringBuffer("");
        try {
            URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng="
                    + latLng[0].latitude + "," + latLng[0].longitude + "&sensor=true&language=ru");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = bufferedReader.readLine();
            if (line != null) {
                jsonAllPlaces.append(line);
            }
            String result = jsonAllPlaces.toString();

            JSONObject jobj = new JSONObject(result);
            JSONArray array = jobj.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONArray("address_components");
            int size = array.length();
            for (int i = 0; i < size; i++) {
                JSONArray typearray = array.getJSONObject(i)
                        .getJSONArray("types");

                if (typearray.getString(0).equals("country")) {
                    mCountryName = array.getJSONObject(i).getString(
                            "long_name");

                }
                if (typearray.getString(0).equals("locality")) {
                    mLocationName = array.getJSONObject(i).getString(
                            "long_name");

                }
                if (typearray.getString(0).equals("route")) {
                    mRouteName = array.getJSONObject(i).getString(
                            "short_name");

                }
                if (typearray.getString(0).equals("street_number")) {
                    mStreetNumber = array.getJSONObject(i).getString(
                            "long_name");

                }

            }
            nameAddress1 = mCountryName + " " + mLocationName + " " + mRouteName + " " + mStreetNumber;
        } catch (Exception e1) {
            e1.printStackTrace();

        }
        return nameAddress1;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

}
