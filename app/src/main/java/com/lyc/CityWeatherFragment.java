package com.lyc;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lyc.RESTapi.JHIndexBean;
import com.lyc.RESTapi.JHTempBean;
import com.lyc.RESTapi.httpUtils;
import com.lyc.RESTapi.urlUtils;
import com.lyc.base.basefragment;
import com.lyc.db.dbManager;
import com.lyc.weatherapp.R;

import java.util.List;

public class CityWeatherFragment extends basefragment implements View.OnClickListener{

    TextView tempTv, cityTv, conditionTv, windTv, tempRangeTv, dateTv, clothIndexTv, carIndexTv, coldIndexTv, sportIndexTv, raysIndexTv, airIndexTv;
    ImageView dayIv;
    LinearLayout futureLayout;
    ScrollView outlayout;

    JHIndexBean.ResultBean.LifeBean lifeBean;
    String city;
    private SharedPreferences pref;
    private int bgnum;

    public void exchangeBg(){
        pref = getActivity().getSharedPreferences("bg_pref", MODE_PRIVATE);
        bgnum = pref.getInt("bg",2);
        if(bgnum == 0){
            outlayout.setBackgroundResource(R.drawable.bg);
        }
        if(bgnum == 1){
            outlayout.setBackgroundResource(R.drawable.bg2);
        }
        if(bgnum == 2){
            outlayout.setBackgroundResource(R.drawable.bg3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);
        initView(view);
        exchangeBg();
        Bundle bundle = getArguments();
        city = bundle.getString("city");
        String url = urlUtils.getTemp_url(city);
        loadData(url);

        String index_url = urlUtils.getIndex_url(city);
        loadIndexData(index_url);
        return view;
    }

    private void loadIndexData(final String index_url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = httpUtils.getJsonContent(index_url);
                JHIndexBean jhIndexBean = new Gson().fromJson(json, JHIndexBean.class);
                if(jhIndexBean != null && jhIndexBean.getResult() != null){
                    lifeBean = jhIndexBean.getResult().getLife();
                }
            }
        }).start();
    }

    @Override
    public void onSuccess(String result) {
        parseShowdata(result);

        int i = dbManager.updateinfobyCity(city, result);
        if(i<=0){
            dbManager.addCityInfo(city, result);
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        String s = dbManager.queryInfoByCity(city);
        if(!TextUtils.isEmpty(s)){
            parseShowdata(s);
        }
    }

    private void parseShowdata(String result){
        JHTempBean jhTempBean = new Gson().fromJson(result, JHTempBean.class);
        JHTempBean.ResultBean jhResult = jhTempBean.getResult();

        dateTv.setText(jhResult.getFuture().get(0).getDate());
        cityTv.setText(jhResult.getCity());


        JHTempBean.ResultBean.FutureBean jhtodayFuture = jhResult.getFuture().get(0);
        JHTempBean.ResultBean.RealtimeBean jhRealtime = jhResult.getRealtime();

        windTv.setText(jhRealtime.getDirect()+jhRealtime.getPower());
        tempRangeTv.setText(jhtodayFuture.getTemperature());
        conditionTv.setText(jhRealtime.getInfo());

        tempTv.setText(jhRealtime.getTemperature()+"℃");

        List<JHTempBean.ResultBean.FutureBean> futurelist = jhResult.getFuture();
        futurelist.remove(0);
        for(int i = 0; i< futurelist.size(); i++){
            View itemview = LayoutInflater.from(getActivity()).inflate(R.layout.item_main_center, null);
            itemview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            futureLayout.addView(itemview);
            TextView idateTv = itemview.findViewById(R.id.item_center_tv_date);
            TextView iconTv = itemview.findViewById(R.id.item_center_tv_con);
            TextView itemperangeTv = itemview.findViewById(R.id.item_center_tv_temp);
            TextView windTv = itemview.findViewById(R.id.item_center_tv_wind);
            ImageView iIv = itemview.findViewById(R.id.item_center_iv);

            JHTempBean.ResultBean.FutureBean dataBean = futurelist.get(i);

            idateTv.setText(dataBean.getDate());
            iconTv.setText(dataBean.getWeather());
            itemperangeTv.setText(dataBean.getTemperature());
            windTv.setText(dataBean.getDirect());
        }
    }

    private void initView(View view){
        tempTv = view.findViewById(R.id.graf_tv_currenttemp);
        cityTv = view.findViewById(R.id.frag_tv_city);
        conditionTv = view.findViewById(R.id.frag_tv_condition);
        windTv = view.findViewById(R.id.frag_tv_wind);
        tempRangeTv = view.findViewById(R.id.frag_tv_temprange);
        dateTv = view.findViewById(R.id.frag_tv_date);
        clothIndexTv = view.findViewById(R.id.frag_index_tv_clothes);
        carIndexTv = view.findViewById(R.id.frag_index_tv_washcar);
        coldIndexTv = view.findViewById(R.id.frag_index_tv_cold);
        sportIndexTv = view.findViewById(R.id.frag_index_tv_sport);
        raysIndexTv = view.findViewById(R.id.frag_index_tv_rays);
        airIndexTv = view.findViewById(R.id.frag_index_tv_air);
        dayIv = view.findViewById(R.id.frag_iv_today);
        futureLayout = view.findViewById(R.id.frag_center_layout);
        outlayout = view.findViewById(R.id.out_layout);

        clothIndexTv.setOnClickListener(this);
        carIndexTv.setOnClickListener(this);
        coldIndexTv.setOnClickListener(this);
        sportIndexTv.setOnClickListener(this);
        raysIndexTv.setOnClickListener(this);
        airIndexTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        String msg;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(v.getId() == R.id.frag_index_tv_clothes){
            builder.setTitle("穿衣指数");
            msg = "no info now";
            if(lifeBean != null){
                msg = lifeBean.getChuanyi().getV()+"\n"+lifeBean.getChuanyi().getDes();
            }
            builder.setMessage(msg);
            builder.setPositiveButton("ensure", null);
        }
        if(v.getId() == R.id.frag_index_tv_washcar){
            builder.setTitle("洗车指数");
            msg = "no info now";
            if(lifeBean != null){
                msg = lifeBean.getXiche().getV()+"\n"+lifeBean.getXiche().getDes();
            }
            builder.setMessage(msg);
            builder.setPositiveButton("ensure", null);
        }
        if(v.getId() == R.id.frag_index_tv_cold){
            builder.setTitle("感冒指数");
            msg = "no info now";
            if(lifeBean != null){
                msg = lifeBean.getGanmao().getV()+"\n"+lifeBean.getGanmao().getDes();
            }
            builder.setMessage(msg);
            builder.setPositiveButton("ensure", null);
        }
        if(v.getId() == R.id.frag_index_tv_sport){
            builder.setTitle("运动指数");
            msg = "no info now";
            if(lifeBean != null){
                msg = lifeBean.getYundong().getV()+"\n"+lifeBean.getYundong().getDes();
            }
            builder.setMessage(msg);
            builder.setPositiveButton("ensure", null);
        }
        if(v.getId() == R.id.frag_index_tv_rays){
            builder.setTitle("紫外线指数");
            msg = "no info now";
            if(lifeBean != null){
                msg = lifeBean.getZiwaixian().getV()+"\n"+lifeBean.getZiwaixian().getDes();
            }
            builder.setMessage(msg);
            builder.setPositiveButton("ensure", null);
        }
        if(v.getId() == R.id.frag_index_tv_air){
            builder.setTitle("空调指数");
            msg = "no info now";
            if(lifeBean != null){
                msg = lifeBean.getKongtiao().getV()+"\n"+lifeBean.getKongtiao().getDes();
            }
            builder.setMessage(msg);
            builder.setPositiveButton("ensure", null);
        }
        builder.create().show();
    }
}