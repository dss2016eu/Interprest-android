package coop.biantik.traductor.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import coop.biantik.traductor.R;

/**
 * Created by Sergio on 6/7/15.
 */
public class NetworkUtils {

    public static boolean isNetworkAvailable(Context context) {
        boolean result = false;
        if (context != null) {
            result = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
            if (!result) {
                UIUtils.showErrorMessage(context, R.string.error_message_no_internet);
            }
        }
        return result;
    }


    public static String getWifiSSID(Context context) {
        final WifiManager wifi = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        WifiInfo wifiInfo = wifi.getConnectionInfo();
        return wifiInfo.getSSID().replace("\"", "");

    }
}
