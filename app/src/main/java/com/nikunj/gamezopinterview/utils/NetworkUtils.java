package com.nikunj.gamezopinterview.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * Created by nikunj on 3/27/18.
 */

public final class NetworkUtils {

    private NetworkUtils(Context context) {

    }

    /**
     * Is wifi enabled.
     *
     * @return the boolean
     */
    public static boolean isWifiEnabled(Context context) {
        boolean wifiState = false;

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            wifiState = wifiManager.isWifiEnabled();
        }
        return wifiState;
    }

    /**
     * Is network available boolean.
     *
     * @return the boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    /**
     * Gets network type.
     *
     * @return the network type
     */
    public int getNetworkType(Context context) {
        int result = NetworkUtilConstants.UNKNOWN;
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) {
            result = NetworkUtilConstants.UNKNOWN;
        } else if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                || activeNetwork.getType() == ConnectivityManager.TYPE_WIMAX) {
            result = NetworkUtilConstants.WIFI_WIFIMAX;
        } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
            TelephonyManager manager =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (manager.getSimState() == TelephonyManager.SIM_STATE_READY) {
                switch (manager.getNetworkType()) {

                    // Unknown
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        result = NetworkUtilConstants.CELLULAR_UNKNOWN;
                        break;
                    // Cellular Data–2G
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                        result = NetworkUtilConstants.CELLULAR_2G;
                        break;
                    // Cellular Data–3G
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        result = NetworkUtilConstants.CELLULAR_3G;
                        break;
                    // Cellular Data–4G
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        result = NetworkUtilConstants.CELLULAR_4G;
                        break;
                    // Cellular Data–Unknown Generation
                    default:
                        result = NetworkUtilConstants.CELLULAR_UNIDENTIFIED_GEN;
                        break;
                }
            }
        }
        return result;
    }

}
