package com.example.cockapp2;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class FavCityAdapter extends ArrayAdapter<CityTime> {
    private ArrayList<CityTime> cities;

    public FavCityAdapter(Context context, ArrayList<CityTime> cities) {
        super(context, 0, cities);
        this.cities = cities;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View getView(int position, View convertView, ViewGroup parent) {
        CityTime city = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.favcityitem, parent, false);
        }

        TextView text = (TextView) convertView.findViewById(R.id.city);
        text.setText(city.getCity());

        TextView country = (TextView) convertView.findViewById((R.id.country));
        country.setText(city.getCountry());

        TextView time = (TextView) convertView.findViewById((R.id.city_time));

        time.setText(city.updateTime(position));

        return convertView;
    }
    public CityTime getItem(int position){
        return cities.get(position);
    }

    public int getCount() {
        return cities.size();
    }
}
