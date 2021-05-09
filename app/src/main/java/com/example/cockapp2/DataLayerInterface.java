package com.example.cockapp2;

import java.util.ArrayList;
import java.util.Hashtable;

public interface DataLayerInterface {
    public void saveCity(Hashtable<String, String> city);
    public boolean isEmpty();
    public ArrayList<CityTime> loadCities(boolean allCities);
    public void updateImportance(String cityname, String val);
}
