package com.example.cockapp2;
import android.content.pm.LauncherApps;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
public class CityTime implements Serializable {
    private String city, country;
    private LocalTime time;
    boolean isFav;

    long[] zones = {0, 30, -600, -240, -180, -180, -180, 300, 240, 240, 180, -120, -120, -180, -120, -180 };
    public CityTime(){
        city = null;
        country = null;
        time = null;
        isFav = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CityTime(String city, String country){
        this.city = city;
        this.country = country;
        this.time = LocalTime.now();
        this.isFav = false;
    }

    public String getCity(){return this.city;}

    public String getCountry(){return this.country;}

    @RequiresApi(api = Build.VERSION_CODES.O)
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
}


