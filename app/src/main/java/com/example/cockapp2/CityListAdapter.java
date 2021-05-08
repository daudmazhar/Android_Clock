package com.example.cockapp2;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class CityListAdapter extends ArrayAdapter<CityTime> implements Filterable {
    private ArrayList<CityTime> cities;
    private ArrayList<CityTime> filteredcities;
    private Filter filter;

    public CityListAdapter(Context context, ArrayList<CityTime> cities) {
        super(context, 0, cities);
        this.cities = cities;
        this.filteredcities = cities;
    }

  //  @RequiresApi(api = Build.VERSION_CODES.O)
    public View getView(int position, View convertView, ViewGroup parent) {
        CityTime city = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cityitem, parent, false);
        }

        TextView text = (TextView) convertView.findViewById(R.id.city);
        text.setText(city.getCity());

        TextView country = (TextView) convertView.findViewById((R.id.country));
        country.setText(city.getCountry());

        TextView time = (TextView) convertView.findViewById((R.id.city_time));

        time.setText(city.updateTime(position));

        CheckBox check = (CheckBox) convertView.findViewById(R.id.city_list_item_check);
        check.setChecked(city.isImportant());
        check.setTag(city);

        return convertView;
    }
   
    public CityTime getItem(int position){
        return filteredcities.get(position);
    }

    public int getCount() {
        return filteredcities.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CityFilter();
        }
        return filter;
    }

    private class CityFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<CityTime> filteredList = new ArrayList<CityTime>();
                for (int i = 0; i < cities.size(); i++) {
                    if (cities.get(i).contains(constraint.toString())) {
                        filteredList.add(cities.get(i));
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;

            } else {
                results.count = cities.size();
                results.values = cities;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredcities = (ArrayList<CityTime>) results.values;
            notifyDataSetChanged();
        }
        
    }
}