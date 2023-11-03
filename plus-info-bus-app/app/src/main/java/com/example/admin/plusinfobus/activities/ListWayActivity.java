package com.example.admin.plusinfobus.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.admin.plusinfobus.Constants;
import com.example.admin.plusinfobus.model.BusTransit;
import com.example.admin.plusinfobus.model.TransitFullInfo;
import com.example.admin.plusinfobus.service.ApiConnectionService;
import com.example.admin.plusinfobus.service.EpochTime;
import com.example.admin.plusinfobus.service.HTTPgetJSON;
import com.example.admin.plusinfobus.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListWayActivity extends AppCompatActivity {
    String nameOrigin;
    String nameDestination;
    long timeEpoh;
    TextView setFrom;
    TextView setTo;
    ArrayList<HashMap<String, String>> wayArrayList = new ArrayList<>();
    public ListView wayListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_way);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nameOrigin = getIntent().getStringExtra("fromDir");
        nameDestination = getIntent().getStringExtra("toDir");
        timeEpoh = Long.valueOf(getIntent().getStringExtra("startTime")).longValue();
        setFrom = findViewById(R.id.textFrom);
        setTo = findViewById(R.id.textTo);
        String stringURL =  Constants.PLUS_INFO_BUS_API
                            +Constants.GOOGLE_API_TRANSIT
                            +Constants.NAME_ORIGIN
                            +nameOrigin
                            +Constants.NAME_DESTINATION
                            +nameDestination
                            +Constants.TIME
                            +timeEpoh
                            ;
        final TransitFullInfo transitFullInfo = getTransitFromApi(stringURL);
        setFrom.setText(transitFullInfo.getDeparture_stop());
        setTo.setText(transitFullInfo.getArrival_stop());
        EpochTime epochTime = new EpochTime();
                for(int i=1;i<transitFullInfo.getBusTransit().size();i++){
                    HashMap<String,String> wayInfo = new HashMap<>();
                    String busTransitJSON = "";
                    List<BusTransit> busTransitList = transitFullInfo.getBusTransit().get(i);
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        busTransitJSON= mapper.writeValueAsString(busTransitList);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                                wayInfo.put("json_data",busTransitJSON);
                                wayInfo.put("departure_stop_text",transitFullInfo.getBusTransit().get(i).get(0)
                                        .getDeparture_stop_text());
                                wayInfo.put("arrival_stop_text",transitFullInfo.getBusTransit().get(i).get(0)
                                        .getArrival_stop_text());
                                wayInfo.put("timeFromEpoch",epochTime.getEpohDate(transitFullInfo.getBusTransit().get(i).get(0)
                                        .getTimeFromEpoch()));
                                wayInfo.put("distance_text",transitFullInfo.getBusTransit().get(i).get(0)
                                        .getDistance_text());
                                wayInfo.put("duration_text",transitFullInfo.getBusTransit().get(i).get(0)
                                        .getDuration_text());
                                wayInfo.put("departure_time_text",transitFullInfo.getBusTransit().get(i).get(0)
                                        .getDeparture_time_text());
                                wayInfo.put("arrival_time_text",transitFullInfo.getBusTransit().get(i).get(0)
                                        .getArrival_time_text());
                                wayArrayList.add(wayInfo);
                }
        wayListView = (ListView) findViewById(R.id.wayListLay);
        wayListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentWayPick = new Intent(ListWayActivity.this, ListWayPickWindow.class);
                TextView jsonText = (TextView) view.findViewById(R.id.json_data_text);
                String getData =  jsonText.getText().toString();
                intentWayPick.putExtra("jsonData", getData);
                startActivity(intentWayPick);
                getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(200,220,220,220)));
            }
        });
        System.out.println(wayArrayList);
        ListAdapter wayAdapter = new SimpleAdapter(ListWayActivity.this,wayArrayList,R.layout.list_way_item,
                new String[]{"json_data",
                        "timeFromEpoch",
                        "departure_time_text",
                }
                ,new int[]{R.id.json_data_text,
                R.id.timeFromEpochTextView,
                R.id.departure_time_TextView,
        });
        wayListView.setAdapter(wayAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255,255 ,255,255)));
    }
    private TransitFullInfo getTransitFromApi(String stringURL){
        TransitFullInfo transitFullInfo = new TransitFullInfo();
        ObjectMapper mapper = new ObjectMapper();
        String jsonFrom;
        try {
            jsonFrom = new ApiConnectionService().execute(stringURL).get();
            transitFullInfo = mapper.readValue(jsonFrom, TransitFullInfo.class);
        }
        catch(IOException e){e.printStackTrace();}
        catch (InterruptedException e){e.printStackTrace();}
        catch (ExecutionException e){e.printStackTrace();}
        return transitFullInfo;
    }
}
