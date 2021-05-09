package com.example.clockapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class MySQLite implements DataLayerInterface{

    private Context context;

    public MySQLite(Context c){
        this.context = c;
    }

    @Override
    public void saveCity(Hashtable<String, String> city) {
        MySQLiteHelper dbHelper = new MySQLiteHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        Enumeration<String> keys = city.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            content.put(key, city.get(key));
        }
        db.insertWithOnConflict("Clock",null,content,SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public boolean isEmpty() {

        MySQLiteHelper helper = new MySQLiteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Clock", null);
        if (c.getCount() == 0) {
            c.close();
            return true;
        }
        c.close();
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public ArrayList<CityTime> loadCities(boolean allCities) {
        MySQLiteHelper dbHelper = new MySQLiteHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query;
        if(allCities == true)
            query = "SELECT * FROM Clock";
        else
            query = "SELECT * FROM Clock WHERE Favorite = 'true'";
        Cursor cursor = db.rawQuery(query,null);

        ArrayList<Hashtable<String,String>> objects = new ArrayList<Hashtable<String, String>>();
        while(cursor.moveToNext()){
            Hashtable<String,String> obj = new Hashtable<String, String>();
            String [] columns = cursor.getColumnNames();
            for(String col : columns){
                obj.put(col,cursor.getString(cursor.getColumnIndex(col)));
            }
            objects.add(obj);
        }
        ArrayList<CityTime> citiesList = new ArrayList<CityTime>();
        for(int i  = 0; i < objects.size(); i++){
            CityTime city = new CityTime("","", this);
            city.loadCityFromHashtable(objects.get((i)));
            citiesList.add(city);
        }
        return citiesList;
    }

    @Override
    public void updateImportance(String cityname, String val) {
        MySQLiteHelper dbHelper = new MySQLiteHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "UPDATE Clock SET Favorite = '"+ val +"' WHERE City = '" + cityname + "'";
        db.execSQL(query);
    }
}
