package com.example.admin.plusinfobus.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.admin.plusinfobus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class RozkladPickActivity extends AppCompatActivity {

    String rozkladJSON;
    int whichID;


    private ListView pickListView;
    ArrayList<HashMap<String, String>> pickArrayList = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255,255 ,255,255)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rozklad_pick);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton info_legend = (FloatingActionButton) findViewById(R.id.info_legendBtn);

        info_legend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RozkladPickActivity.this,LegendWindow.class));
                getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(200,220,220,220)));
            }
        }
        );


        rozkladJSON = getIntent().getStringExtra("rozkladJSON");
        whichID = getIntent().getIntExtra("whichID",0);

        System.out.println("Nazwa i ID:"+rozkladJSON + whichID);

        get_Json_Rozklad(rozkladJSON,whichID);

        pickListView = (ListView) findViewById(R.id.rozkladPickListView);

        ListAdapter pickAdapter = new SimpleAdapter(RozkladPickActivity.this, pickArrayList,R.layout.list_item,
                new String []{"main_name_from","hour","comment"},new int[]{R.id.nameWay,R.id.hour,R.id.comment});
        pickListView.setAdapter(pickAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void get_Json_Rozklad(String jsonName, int id)
    {
        String json;
        try
        {
            InputStream is= getAssets().open(jsonName);
            int size = is.available();
            byte[] buffer = new byte [size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONArray jasonArray = new JSONArray(json);
            for(int i =0; i<jasonArray.length();i++){
                JSONObject obj = jasonArray.getJSONObject(i);
                int main_id = obj.getInt("wayID");
                if (main_id == id) {
                    String data = obj.getString("data");
                    JSONArray dataArray = new JSONArray(data);
                    for (int j = 0; j < dataArray.length(); j++) {
                        JSONObject innobj = dataArray.getJSONObject(j);
                        String main_data = innobj.getString("main_data");
                        HashMap<String, String> rozkladName = new HashMap<>();
                        String main_name_from = innobj.getString("main_name_from");
                        rozkladName.put("main_name_from", main_name_from);
                        pickArrayList.add(rozkladName);

                        JSONArray infoArray = new JSONArray(main_data);
                        for (int j2 = 0; j2 < infoArray.length(); j2++) {
                            JSONObject inobj = infoArray.getJSONObject(j2);
                            String hour = inobj.getString("hour");
                            String comment = inobj.getString("comment");
                            HashMap<String, String> rozklad = new HashMap<>();
                            rozklad.put("hour", hour);
                            rozklad.put("comment", comment);
                            System.out.println("+Load course:" + rozklad);
                            pickArrayList.add(rozklad);
                        }
                    }

                }
            }
        }catch (IOException e) { e.printStackTrace(); }
        catch(JSONException e){e.printStackTrace();}
    }

}
