package com.example.admin.plusinfobus.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.admin.plusinfobus.Constants;
import com.example.admin.plusinfobus.R;
import com.example.admin.plusinfobus.model.BusStop;
import com.example.admin.plusinfobus.service.ApiConnectionService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(9.0f);
        mMap.setMaxZoomPreference(15.0f);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(50, 20)));
        String busStopJSON = "";
        ArrayList<BusStop> arrayList = new ArrayList<>();
        try {
            String stringURL = Constants.PLUS_INFO_BUS_API+Constants.BUSSTOP;
            busStopJSON =  new ApiConnectionService().execute(stringURL).get();
            System.out.println(busStopJSON);
            arrayList =getMarkers(busStopJSON);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (arrayList!=null){
            for (int i=0;i<arrayList.size();i++){
                LatLng marker = new LatLng( arrayList.get(i).getLat(),
                                            arrayList.get(i).getLng());
                googleMap.addMarker(new MarkerOptions().position(marker).title("Przystanek "+arrayList.get(i).getStopName()));
            }
        }else{
        }
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location == null){}
            else {
                System.out.println("lattest" + location.getLatitude() + location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
                        location.getLongitude()), 12.0f));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_FINE_LOCATION);
            }
        }
    }

//    //Perm dla androida >=6.0
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case Constants.PERMISSION_FINE_LOCATION:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        mMap.setMyLocationEnabled(true);
//                    }
//                }else{
//                    Toast.makeText(getApplicationContext(),"Aplikacja wymaga lokalizacji",Toast.LENGTH_LONG).show();
//                    finish();
//                }
//                break;
//        }
//    }
    public ArrayList<BusStop> getMarkers( String stringJSON)  {
        ArrayList<BusStop> array = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(stringJSON);
            for (int i =0; i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                String stopName = obj.getString("stopName");
                long id = obj.getLong("id");
                double lat = obj.getDouble("lat");
                double lng = obj.getDouble("lng");
                BusStop busStop = new BusStop();
                busStop.setId(id);
                busStop.setStopName(stopName);
                busStop.setLat(lat);
                busStop.setLng(lng);
                array.add(busStop);
            }
        }
        catch(JSONException e){ e.printStackTrace();}
        return array;
    }
}
