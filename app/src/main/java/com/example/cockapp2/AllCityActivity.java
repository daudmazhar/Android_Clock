package com.example.cockapp2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AllCityActivity extends AppCompatActivity {

    ArrayList<CityTime> cities;
    ArrayList<CityTime> selectedItems;
    CityTime currentCity;
    ListView list;
    EditText text;
    CityListAdapter adapter;
    private DataLayerInterface dao;
    final int REQUEST_CODE = 2;
   // @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        dao = new MySQLite(getApplicationContext());
        long[] zones = {0, 30, -600, -240, -180, -180, -180, 300, 240, 240, 180, -120, -120, -180, -120, -180 };

        if(dao.isEmpty()) {
            if (cities == null) {
                cities = new ArrayList<CityTime>();
                cities.add(new CityTime("Islamabad", "Pakistan", 0, dao));
                cities.add(new CityTime("New Delhi", "India", 30,dao));
                cities.add(new CityTime("Washington DC", "USA", -600, dao));
                cities.add(new CityTime("London", "England", -240,dao));
                cities.add(new CityTime("Paris", "France", -180,dao));
                cities.add(new CityTime("Berlin", "Germany", -180,dao));
                cities.add(new CityTime("Stockholm", "Sweden", -180,dao));
                cities.add(new CityTime("Canberra", "Australia", 300,dao));
                cities.add(new CityTime("Tokyo", "Japan", 240,dao));
                cities.add(new CityTime("Seoul", "South Korea", 240,dao));
                cities.add(new CityTime("Beijing", "China", 180,dao));
                cities.add(new CityTime("Moscow", "Russia", -120,dao));
                cities.add(new CityTime("Ankara", "Turkey", -120,dao));
                cities.add(new CityTime("Cairo", "Egypt", -180,dao));
                cities.add(new CityTime("Helsinki", "Finland", -120,dao));
                cities.add(new CityTime("Prague", "Czech Republic", -180,dao));

                for (int i = 0; i < cities.size(); i++){
                    cities.get(i).saveCityToStorage();
                }
            }
        }
        else{
            cities = new ArrayList<CityTime>();
            cities = dao.loadCities(true);
        }
        createView();

    }

    private EditText createText(){
        text = new EditText(this);
        text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        text.setHint(" Search City");
        text.setBackgroundColor((int)0xFF6200EE);
        text.setHintTextColor((int)0xFFF8F8FA);
        text.setTextColor((int)0xFFF8F8FA);

        text.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable arg0) { }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) { }

            @Override
            public void onTextChanged(CharSequence text, int start, int before,int count) {
                adapter.getFilter().filter(text.toString());
            }

        });

        return text;
    }

    private ListView createList(){
        list = new ListView(this);
        list.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        list.setBackgroundColor((int)0xFFF8F8FA);
        adapter = new CityListAdapter(this, cities);
        list.setAdapter(adapter);
        registerForContextMenu(list);

        return list;
    }

    private void createView(){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setFocusable(true);
        layout.setBackgroundColor((int)0xFFF8F8FA);
        layout.setFocusableInTouchMode(true);
        layout.addView(createText());
        layout.addView(createList());

        setContentView(layout);

    }

    public void checkboxClick(View v){

            boolean checked = ((CheckBox) v).isChecked();
            currentCity = (CityTime)((CheckBox) v).getTag();
            if(currentCity.isImportant() == false)
                dao.updateImportance(currentCity.getCity(), "true");
            else
                dao.updateImportance(currentCity.getCity(), "false");

    }

    private void prepareResult(){

            Intent intent = new Intent(this, MainActivity.class);
            setResult(RESULT_OK, intent);

    }

    @Override
    public void onBackPressed() {
        prepareResult();
        super.onBackPressed();
    }
}