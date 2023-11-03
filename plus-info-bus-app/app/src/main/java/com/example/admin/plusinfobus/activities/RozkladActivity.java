package com.example.admin.plusinfobus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.admin.plusinfobus.R;

public class RozkladActivity extends AppCompatActivity {

    String rozkladJSON;
    int whichID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rozklad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void clickRozkladPick(View view){

        switch (view.getId()){
            case R.id.butmachajWay1:
//                Intent intentMachaj_1 = new Intent(RozkladActivity.this,MachajActivity.class);
                Intent intentMachaj_1 = new Intent(RozkladActivity.this,RozkladPickActivity.class);
                rozkladJSON = "storage/machaj/machajAll.json";
                whichID = 0;
                intentMachaj_1.putExtra("rozkladJSON",rozkladJSON);
                intentMachaj_1.putExtra("whichID",whichID);
                startActivity(intentMachaj_1);
                break;
            case R.id.butmachajWay2:
                Intent intentMachaj_2 = new Intent(RozkladActivity.this,RozkladPickActivity.class);
                rozkladJSON = "storage/machaj/machajAll.json";
                whichID = 1;
                intentMachaj_2.putExtra("rozkladJSON",rozkladJSON);
                intentMachaj_2.putExtra("whichID",whichID);
                startActivity(intentMachaj_2);
                break;
            case R.id.butefektWay1:
                Intent intentEfekt_1 = new Intent (RozkladActivity.this,RozkladPickActivity.class);
                rozkladJSON = "storage/efekt/efektAll.json";
                whichID = 0;
                intentEfekt_1.putExtra("rozkladJSON",rozkladJSON);
                intentEfekt_1.putExtra("whichID",whichID);
                startActivity(intentEfekt_1);
                break;
            case R.id.butefektWay2:
                Intent intentEfekt_2 = new Intent (RozkladActivity.this, RozkladPickActivity.class);
                rozkladJSON = "storage/efekt/efektAll.json";
                whichID = 1;
                intentEfekt_2.putExtra("rozkladJSON",rozkladJSON);
                intentEfekt_2.putExtra("whichID",whichID);
                startActivity(intentEfekt_2);
                break;
        }
    }
}
