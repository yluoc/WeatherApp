package com.lyc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

public class dbhelper extends SQLiteOpenHelper {

    public dbhelper(Context context){
        super(context, "lyc.db",null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table info(_id integer primary key autoincrement, city varchar(20) unique not null, content text not null)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
