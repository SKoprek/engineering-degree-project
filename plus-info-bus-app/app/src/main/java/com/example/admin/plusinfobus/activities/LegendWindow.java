package com.example.admin.plusinfobus.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
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

public class LegendWindow extends Activity{

    ArrayList<HashMap<String, String>> legendMain;
    private ListView legendView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.legend_window);

        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*0.75));


        legendView = (ListView) findViewById(R.id.legendListWiew);
        legendMain = new ArrayList<>();
        getJSONlegend();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//TODO Visable null
    }

    public void getJSONlegend()
    {
        String json;
        try
        {
            InputStream is= getAssets().open("storage/legend.json");
            int size = is.available();
            byte[] buffer = new byte [size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONArray jasonArray = new JSONArray(json);
            for(int i =0; i<jasonArray.length();i++){
                JSONObject obj = jasonArray.getJSONObject(i);

                String legendShort = obj.getString("short");
                String legendFull = obj.getString("full");
                HashMap<String, String> legend = new HashMap<>();

                legend.put("short",legendShort);
                legend.put("full",legendFull);

                legendMain.add(legend);
                ListAdapter adapter = new SimpleAdapter(LegendWindow.this,legendMain,R.layout.legend_list,
                        new String[]{"short","full"},new int[]{R.id.legend_short,R.id.legend_full});
                legendView.setAdapter(adapter);
            }
        }catch (IOException e) { e.printStackTrace(); }
        catch(JSONException e){e.printStackTrace();}
    }


}
