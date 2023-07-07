package com.lyc.base;

import android.app.Application;

import com.lyc.db.dbManager;

import org.xutils.x;

public class UnitApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        x.Ext.init(this);
        dbManager.initDB(this);
    }
}
