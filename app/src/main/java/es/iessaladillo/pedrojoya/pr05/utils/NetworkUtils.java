package es.iessaladillo.pedrojoya.pr05.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    private NetworkUtils(){}

    public static boolean isConnectionAvailable(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo infoRed = null;

        if(connectivityManager != null)
            infoRed = connectivityManager.getActiveNetworkInfo();

        return infoRed != null && infoRed.isConnected();
    }
}
