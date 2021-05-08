package com.example.cockapp2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<CityTime> cities;
    ListView list;
    FavCityAdapter adapter;
    private Button addCityButton;
    final int REQUEST_CODE = 1;
    private DataLayerInterface dao;

   // @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new MySQLite(getApplicationContext());
        if(cities == null)
            cities = new ArrayList<>();
        cities = dao.loadCities(false);
        createView();
    }

   // @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                cities = new ArrayList<>();
                cities = dao.loadCities(false);

                createView();
            }
        }
    }
        private void createView() {
            setContentView(R.layout.mainactivity);
            list = findViewById(R.id.listview);
            adapter = new FavCityAdapter(this, cities);
            list.setAdapter(adapter);
            addCityButton = findViewById(R.id.buttonid);
            addCityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AllCityActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });
        }
}