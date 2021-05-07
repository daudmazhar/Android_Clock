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
    final int REQUEST_CODE = 2;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(selectedItems == null)
            selectedItems = new ArrayList<CityTime>();
        selectedItems = (ArrayList<CityTime>) intent.getSerializableExtra("list");

        if (cities == null) {
            cities = new ArrayList<CityTime>();
            cities.add(new CityTime("Islamabad", "Pakistan"));
            cities.add(new CityTime("New Delhi", "India"));
            cities.add(new CityTime("Washington DC", "USA"));
            cities.add(new CityTime("London", "England"));
            cities.add(new CityTime("Paris", "France"));
            cities.add(new CityTime("Berlin", "Germany"));
            cities.add(new CityTime("Stockholm", "Sweden"));
            cities.add(new CityTime("Canberra", "Australia"));
            cities.add(new CityTime("Tokyo", "Japan"));
            cities.add(new CityTime("Seoul", "South Korea"));
            cities.add(new CityTime("Beijing", "China"));
            cities.add(new CityTime("Moscow", "Russia"));
            cities.add(new CityTime("Ankara", "Turkey"));
            cities.add(new CityTime("Cairo", "Egypt"));
            cities.add(new CityTime("Helsinki", "Finland"));
            cities.add(new CityTime("Prague", "Czech Republic"));
        }
        setCheckedCities();
        createView();

    }

    private void setCheckedCities(){
        for (int i = 0; i < cities.size(); i++){
            for(int j = 0; j < selectedItems.size(); j++){
                if(cities.get(i).getCity().equals(selectedItems.get(j).getCity())){
                    cities.get(i).setImportance(true);
                }
            }
        }
        selectedItems.clear();
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
                currentCity.setImportance(true);
            else
                currentCity.setImportance(false);

    }

    private void prepareResult(){

            Intent intent = new Intent(this, MainActivity.class);
            selectedItems = new ArrayList<CityTime>();
            for(int i = 0; i < cities.size(); i++){
                if(cities.get(i).isImportant()){
                    selectedItems.add(cities.get(i));
                }
            }
            intent.putExtra("fav", selectedItems);
            setResult(RESULT_OK, intent);

    }

    @Override
    public void onBackPressed() {
        prepareResult();
        super.onBackPressed();
    }
}