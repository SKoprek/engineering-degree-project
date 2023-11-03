package com.example.admin.plusinfobus.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.admin.plusinfobus.model.BusTransit;
import com.example.admin.plusinfobus.model.TransitFullInfo;
import com.example.admin.plusinfobus.service.ApiConnectionService;
import com.example.admin.plusinfobus.service.EpochTime;
import com.example.admin.plusinfobus.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListWayPickWindow extends Activity {
    String jsonPickData;
    ArrayList<HashMap<String, String>> wayArrayListSuper = new ArrayList<>();
        private ListView listWayPickView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_way_pick);
        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*0.75));
        jsonPickData = getIntent().getStringExtra("jsonData");
        List<BusTransit> busTransitList = new ArrayList<BusTransit>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            busTransitList = mapper.readValue(jsonPickData, new TypeReference<List<BusTransit>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        listWayPickView = (ListView) findViewById(R.id.listWayPickListView);
        for(int i=0; i<busTransitList.size();i++){
            HashMap<String,String> wayInfo = new HashMap<>();
            wayInfo.put("departure_stop_text",busTransitList.get(i).getDeparture_stop_text());
            wayInfo.put("arrival_stop_text",busTransitList.get(i).getArrival_stop_text());
            wayInfo.put("distance_text",busTransitList.get(i).getDistance_text());
            wayInfo.put("duration_text",busTransitList.get(i).getDuration_text());
            wayInfo.put("departure_time_text",busTransitList.get(i).getDeparture_time_text());
            wayInfo.put("arrival_time_text",busTransitList.get(i).getArrival_time_text());
            wayInfo.put("agencies_name",busTransitList.get(i).getAgencies_name());
            wayArrayListSuper.add(wayInfo);
        }
        ListAdapter wayPickAdapter = new SimpleAdapter(ListWayPickWindow.this,wayArrayListSuper,R.layout.list_way_pick_item,
                new String[]{
                        "departure_stop_text",
                        "arrival_stop_text",
                        "departure_time_text",
                        "arrival_time_text",
                        "duration_text",
                        "distance_text",
                        "agencies_name"}
                ,new int[]{
                R.id.pick_departure_stop_TextView,
                R.id.pick_arrival_stop_TextView,
                R.id.pick_departure_time_TextView,
                R.id.pick_arrival_time_TextView,
                R.id.pick_duration_time_TextView,
                R.id.pick_distance_TextView,
                R.id.pick_line});
        listWayPickView.setAdapter(wayPickAdapter);
    }
}
