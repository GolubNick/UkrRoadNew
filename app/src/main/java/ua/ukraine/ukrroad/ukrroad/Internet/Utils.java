package ua.ukraine.ukrroad.ukrroad.Internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class Utils {
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Log.i("", "Internet enable");
            Toast.makeText(context, "internet enable", Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(context, "internet disable", Toast.LENGTH_SHORT).show();
        Log.i("", "Internet desable");
        return false;
    }
}
