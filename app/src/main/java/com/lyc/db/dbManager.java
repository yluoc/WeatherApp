package com.lyc.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class dbManager {
    public static SQLiteDatabase database;
    public static void initDB(Context context){
        dbhelper dbHelper = new dbhelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public static List<String> queryAllCityName(){
        Cursor cursor = database.query("info",null, null, null, null, null, null);
        List<String>cityList = new ArrayList<>();

        while(cursor.moveToNext()){
            @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex("city"));
            cityList.add(city);
        }
        return cityList;
    }

    public static int updateinfobyCity(String city, String content){
        ContentValues values = new ContentValues();
        values.put("content", content);
        return database.update("info", values, "city=?", new String[]{city});
    }

    public static long addCityInfo(String city, String content){
        ContentValues values = new ContentValues();
        values.put("city", city);
        values.put("content", content);
        return database.insert("info", null, values);
    }

    public static String queryInfoByCity(String city){
        Cursor cursor = database.query("info", null, "city=?", new String[]{city}, null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
            return content;
        }
        return null;
    }

    /*save at most 5 cities*/
    public  static int getCityCount(){
        Cursor cursor = database.query("info", null, null, null, null, null, null);
        int count = cursor.getCount();
        return count;
    }

    public static List<dbBean>queryAllInfo(){
        Cursor cursor = database.query("info",null,null,null,null,null,null);
        List<dbBean>list = new ArrayList<>();
        while (cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
            @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex("city"));
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
            dbBean bean = new dbBean(id, city, content);
            list.add(bean);
        }
        return list;
    }

    public static int deleteinfobyCity(String city){
        return database.delete("info", "city=?", new String[]{city});
    }

    public static void deleteAllInfo(){
        String sql = "delete from info";
        database.execSQL(sql);
    }

}
