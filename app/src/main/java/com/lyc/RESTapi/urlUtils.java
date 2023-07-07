package com.lyc.RESTapi;

import android.telephony.CellSignalStrengthGsm;

public class urlUtils {
    public static final String Key = "864bdd5bfbaa5474078aa98ec94ed947";
    public static String temp_url = "http://apis.juhe.cn/simpleWeather/query";
    public static String index_url = "http://apis.juhe.cn/simpleWeather/life";

    public static String getTemp_url (String city) {
        String url = temp_url+"?city="+city+"&key="+Key;
        return url;
    }

    public static String getIndex_url (String city) {
        String url = index_url+"?city="+city+"&key="+Key;
        return url;
    }
}
