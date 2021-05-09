package com.example.clockapp2;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.Hashtable;

public class CityTime implements Serializable {
    private String city, country;
    private LocalTime time;
    boolean isFav;
    private DataLayerInterface dao = null;
    long timezone;
    long[] zones = {0, 30, -600, -240, -180, -180, -180, 300, 240, 240, 180, -120, -120, -180, -120, -180 };
    public CityTime(){
        city = null;
        country = null;
        time = null;
        isFav = false;
    }

    public CityTime(String city, String country, long timezone, DataLayerInterface dao){
        this.city = city;
        this.country = country;
        this.time = LocalTime.now();
        this.isFav = false;
        this.dao = dao;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public CityTime(String city, String country, DataLayerInterface dao){
        this.city = city;
        this.country = country;
        this.time = LocalTime.now();
        this.isFav = false;
        this.dao = dao;
    }

    public String getCity(){return this.city;}

    public String getCountry(){return this.country;}

   // @RequiresApi(api = Build.VERSION_CODES.O)
    public String updateTime(int pos) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        time = LocalTime.now().plusMinutes(zones[pos]);
        String TimeString = time.format(formatter);
        return TimeString;
    }

    public void setImportance(boolean value){
        isFav = value;
    }

    public boolean isImportant(){
        return isFav;
    }

    public boolean contains(String text){
        return getCity().contains(text) || getCountry().contains(text);
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    public void loadCityFromHashtable(Hashtable<String, String> hashed){
        this.city = hashed.get("City");
        this.country = hashed.get("Country");
        if(hashed.get("Favorite").equals("true"))
            this.isFav = true;
        else
            this.isFav = false;
    }


    public void saveCityToStorage(){
       if(dao != null){
            Hashtable<String, String> hashedCity = new Hashtable<String, String>();
            hashedCity.put("City", this.city);
            hashedCity.put("Country", this.country);
            if(this.isImportant() == true){
                hashedCity.put("Favorite", "true");
            }
            else
                hashedCity.put("Favorite", "false");

            dao.saveCity(hashedCity);
        }
    }
}


