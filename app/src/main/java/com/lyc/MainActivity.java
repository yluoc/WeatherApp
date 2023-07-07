package com.lyc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lyc.city_manager.CityManagerActivity;
import com.lyc.db.dbManager;
import com.lyc.weatherapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView addCityIv, moreIv;
    LinearLayout pointLayout;
    ViewPager mainVp;
    List<Fragment> fragmentList;
    List<String> cityList;
    List<ImageView> imgList;
    RelativeLayout outlayout;
    private CityFragmentPagerAdapter adapter;
    private SharedPreferences pref;
    private int bgNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCityIv = findViewById(R.id.main_iv_add);
        moreIv = findViewById(R.id.main_iv_more);
        pointLayout = findViewById(R.id.main_layout_point);
        //exchangebg();
        outlayout = findViewById(R.id.main_out_layout);
        mainVp = findViewById(R.id.main_vp);

        addCityIv.setOnClickListener(this);
        moreIv.setOnClickListener(this);

        fragmentList = new ArrayList<>();
        cityList = dbManager.queryAllCityName();
        imgList = new ArrayList<>();

        if(cityList.size() == 0){
            cityList.add("沈阳");
        }

        try{
            Intent intent = getIntent();
            String city = intent.getStringExtra("city");
            if(!cityList.contains(city) && !TextUtils.isEmpty(city)){
                cityList.add((city));
            }
        }catch(Exception e){
            Log.i("lyc","something's wrong");
        }

        initPager();
        adapter = new CityFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mainVp.setAdapter(adapter);
        initPoint();
        mainVp.setCurrentItem(fragmentList.size()-1);

        setPagerListener();
    }

    public void exchangeBg(){
        pref = getSharedPreferences("bg_pref", MODE_PRIVATE);
        bgNum = pref.getInt("bg", 2);
        switch (bgNum) {
            case 0:
                outlayout.setBackgroundResource(R.drawable.bg);
                break;
            case 1:
                outlayout.setBackgroundResource(R.drawable.bg2);
                break;
            case 2:
                outlayout.setBackgroundResource(R.drawable.bg3);
                break;
        }

    }

    private void setPagerListener(){
        mainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i<imgList.size();i++){
                    imgList.get(i).setImageResource(R.drawable.a1);
                }
                imgList.get(position).setImageResource(R.drawable.a2);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initPager(){
        for(int i=0; i< cityList.size();i++){
            CityWeatherFragment cwFrag = new CityWeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city", cityList.get(i));
            cwFrag.setArguments(bundle);
            fragmentList.add(cwFrag);
        }
    }

    private void initPoint(){
        for(int i=0; i< fragmentList.size();i++){
            ImageView piv = new ImageView(this);
            piv.setImageResource(R.drawable.a1);
            piv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams ip = (LinearLayout.LayoutParams) piv.getLayoutParams();
            ip.setMargins(0,0,20,0);
            imgList.add(piv);
            pointLayout.addView(piv);
        }
        imgList.get(imgList.size()-1).setImageResource(R.drawable.a2);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent();
        if (v.getId() == R.id.main_iv_add){
            intent.setClass(this, CityManagerActivity.class);
        }
        if(v.getId() == R.id.main_iv_more){
            intent.setClass(this, MoreActivity.class);
        }
        startActivity(intent);
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        List<String> list = dbManager.queryAllCityName();
        if(list.size() == 0){
            list.add("北京");
        }
        cityList.clear();
        cityList.addAll(list);

        fragmentList.clear();
        initPager();

        adapter.notifyDataSetChanged();
        imgList.clear();
        pointLayout.removeAllViews();
        initPoint();
        mainVp.setCurrentItem(fragmentList.size()-1);
    }
}

