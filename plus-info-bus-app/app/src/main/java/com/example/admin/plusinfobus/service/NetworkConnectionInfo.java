package com.example.admin.plusinfobus.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkConnectionInfo {

    public static boolean isOnline(Context context){
        boolean connection_WIFI=false;
        boolean connection_MobileData=false;
        ConnectivityManager connectivityManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos)
        {
            if(info.getTypeName().equalsIgnoreCase("WIFI"))
                if(info.isConnected()) connection_WIFI = true;
            if(info.getTypeName().equalsIgnoreCase("MOBILE"))
                if(info.isConnected())
                    connection_MobileData = true;
        }
        return connection_WIFI || connection_MobileData;
    }
}
