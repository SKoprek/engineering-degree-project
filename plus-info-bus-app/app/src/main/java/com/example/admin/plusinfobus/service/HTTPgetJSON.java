package com.example.admin.plusinfobus.service;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPgetJSON extends AsyncTask<String, String, String> {
    String jsonData = "" ;
    @Override
    protected String doInBackground(String... urls) {

        try{
            for (int i =0; i<urls.length; i++){
            URL url = new URL(urls[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line= "";
            while(line !=null){
                jsonData = jsonData+line;
                line = bufferedReader.readLine();
            }}
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "["+jsonData+"]";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

}
