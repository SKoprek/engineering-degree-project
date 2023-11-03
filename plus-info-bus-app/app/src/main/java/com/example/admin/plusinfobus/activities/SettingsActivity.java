package com.example.admin.plusinfobus.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.admin.plusinfobus.Constants;
import com.example.admin.plusinfobus.R;
import com.example.admin.plusinfobus.service.NetworkConnectionInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

public class SettingsActivity extends AppCompatActivity {

    private NetworkConnectionInfo networkConnectionInfo;

    TextView connectionStatus;
    TextView locationStatus;
    LocationManager locationManager;
    Context context = SettingsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        connectionStatus = (TextView) findViewById(R.id.connectionStatus);
        connectionStatus.setText(Boolean.toString(networkConnectionInfo.isOnline(context)));

        //Check Permission for location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationStatus = (TextView) findViewById(R.id.locationStatus);
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location == null){
                locationStatus.setText("NaN");
            }
            else {
                String myLatLon = "Lat : " + location.getLatitude() + " | " + "Lng : " + location.getLongitude();
                locationStatus.setText(myLatLon);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_FINE_LOCATION);
            }
        }
    }
}
