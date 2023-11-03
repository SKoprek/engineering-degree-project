package com.example.admin.plusinfobus.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.admin.plusinfobus.Constants;
import com.example.admin.plusinfobus.model.BusStop;
import com.example.admin.plusinfobus.service.ApiConnectionService;
import com.example.admin.plusinfobus.service.EpochTime;
import com.example.admin.plusinfobus.R;
import com.example.admin.plusinfobus.service.NetworkConnectionInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompliteFind;
    private AutoCompleteTextView autoCompliteTo;
    private ArrayAdapter<String> autoAdapter;
    ArrayList<String> stopMark = new ArrayList<>();
    private String startTime;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private NetworkConnectionInfo networkConnectionInfo;
    TextView dateTextView;
    TextView timeTextView;


    int day;
    int month;
    int year;
    int godzina;
    int minuta;


    int dayFind;
    int monthFind;
    int yearFind;
    int godzinaFind;
    int minutaFind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        perm();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dateTextView = (TextView)findViewById(R.id.textDate);
        timeTextView = (TextView)findViewById(R.id.textTime);
        dateSet();
        timeSet();
        getJsonAutoComplite();
    }
//    Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent2 = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent2);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
//End Menu
    public void getJsonAutoComplite() {
        String jsonFrom;
        try
        {
//            InputStream is= getAssets().open("storage/suggestionsList.json");
//            int size = is.available();
//            byte[] buffer = new byte [size];
//            is.read(buffer);
//            is.close();
//            jsonFrom = new String(buffer, "UTF-8");
//            JSONArray jsonArray = new JSONArray(jsonFrom);
//            for (int i =0; i<jsonArray.length();i++){
//                JSONObject obj = jsonArray.getJSONObject(i);
//                String stopName = obj.getString("stopName");
//                stopMark.add(stopName);
//            }
            jsonFrom =  new ApiConnectionService().execute(Constants.PLUS_INFO_BUS_API+Constants.BUSSTOP).get();
            ArrayList<BusStop> array = new ArrayList<>();

                JSONArray jsonArray = new JSONArray(jsonFrom);
                for (int i =0; i<jsonArray.length();i++) {
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
                    stopMark.add(stopName);
                }
            autoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stopMark);
            autoCompliteFind = (AutoCompleteTextView) findViewById(R.id.autoFrom);
            autoCompliteTo = (AutoCompleteTextView) findViewById(R.id.autoTo);
            autoCompliteFind.setAdapter(autoAdapter);
            autoCompliteTo.setAdapter(autoAdapter);
            autoCompliteFind.setThreshold(1);
            autoCompliteTo.setThreshold(1);
        }
        catch(JSONException e){e.printStackTrace();}
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public void timeSet(){
        Calendar calendar = Calendar.getInstance();
        godzina = calendar.get(Calendar.HOUR_OF_DAY);
        minuta = calendar.get(Calendar.MINUTE);
        godzinaFind = godzina;
        minutaFind = minuta;
        String time = godzina+ " : " + minuta;
        timeTextView.setText(time);
    }
    public void timePick(){
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int godzinaS, int minutaS) {
                godzinaFind = godzinaS;
                minutaFind = minutaS;
                String time = godzinaS+ " : " + minutaS;
                timeTextView.setText(time);
            }
        };
        TimePickerDialog dailogTime = new TimePickerDialog(MainActivity.this,
                mTimeSetListener,
                godzina,
                minuta,true);
        dailogTime.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255,220,220,220)));
        dailogTime.show();
    }
    public void dateSet(){
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        int monthN = month +1;
        dayFind = day;
        monthFind = monthN;
        yearFind = year;
        String date = day + "/" +monthN+"/"+year;
        dateTextView.setText(date);
    }
    public void datePick(){
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yearS, int monthS, int dayS) {
                monthS = monthS +1;
                String date = dayS + "/" +monthS+"/"+yearS;
                dayFind = dayS;
                monthFind = monthS;
                yearFind = yearS;
                dateTextView.setText(date);
            }
        };
        DatePickerDialog dailogDate = new DatePickerDialog(MainActivity.this,
//                android.R.style.Theme_Holo_Light,
                mDateSetListener,
                year,month,day);
        dailogDate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255,220,220,220)));
        dailogDate.show();
    }
    public void clickRozklad(View view){
        switch (view.getId()){
            case R.id.butRozklad:
                Intent intent = new Intent(MainActivity.this,RozkladActivity.class);
                startActivity(intent);
                break;
            case R.id.butFind:
                Context context = MainActivity.this;
                if(networkConnectionInfo.isOnline(context)!=false) {
                    Intent intent1 = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(intent1);
                }else{
                    Toast.makeText(this, "Info: offline", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.butSave:
                Toast.makeText(this, "Error: aktywność wyłączona", Toast.LENGTH_LONG).show();
                break;
            case R.id.butTime:
                timePick();
                break;
            case R.id.butDate:
                datePick();
                break;
        }
    }
    public void clickSearch(View view) throws ParseException {
        EditText fromText = findViewById(R.id.autoFrom);
        String stringFrom = fromText.getText().toString();
        EditText toText = findViewById(R.id.autoTo);
        String stringTo = toText.getText().toString();
        boolean isEmptyFrom = stringFrom == null || stringFrom.trim().length() == 0;
        boolean isEmptyTo = stringTo == null || stringTo.trim().length() == 0;
        if(isEmptyFrom){
                Toast.makeText(this, "Error: pole Z: jest puste", Toast.LENGTH_LONG).show();
            }else{
            }
        if(isEmptyTo){
                Toast.makeText(this, "Error: pole Do: jest puste", Toast.LENGTH_LONG).show();
            }else{
            }
        if(isEmptyFrom == false && isEmptyTo == false){
            Intent intentWay = new Intent(MainActivity.this, ListWayActivity.class);
            EpochTime epochTime = new EpochTime();
            long epochTimeSet = epochTime.setEpochTime(monthFind,dayFind,yearFind,godzinaFind,minutaFind);
            startTime = Long.toString(epochTimeSet);
            intentWay.putExtra("fromDir", stringFrom);
            intentWay.putExtra("toDir", stringTo);
            intentWay.putExtra("startTime",startTime);
            Context context = MainActivity.this;
            if(networkConnectionInfo.isOnline(context)!=false) {
                startActivity(intentWay);
            }else{
                Toast.makeText(this, "Error: offline", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void perm(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_FINE_LOCATION);
            }
        }
    }

    // Perm for android 6.0 or highter
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Aplikacja wymaga lokalizacji",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }
}
