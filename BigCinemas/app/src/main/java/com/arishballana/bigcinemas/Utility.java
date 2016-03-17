package com.arishballana.bigcinemas;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by arishballana on 2/17/16.
 */
public class Utility {

    public static boolean isOnline(Context ct) {
        ConnectivityManager cm = (ConnectivityManager)ct.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
