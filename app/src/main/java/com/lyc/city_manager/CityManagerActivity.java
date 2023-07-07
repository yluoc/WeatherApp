package com.lyc.city_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lyc.db.dbBean;
import com.lyc.db.dbManager;
import com.lyc.weatherapp.R;

import java.util.ArrayList;
import java.util.List;

public class CityManagerActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView addIv, backIv, deleteIv;
    ListView cityIv;
    List<dbBean> mdatas;
    private CityManagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_managerctivity);
        addIv = findViewById(R.id.city_iv_add);
        backIv = findViewById(R.id.city_iv_back);
        deleteIv = findViewById(R.id.city_iv_delete);
        cityIv = findViewById(R.id.city_lv);
        mdatas = new ArrayList<>();

        addIv.setOnClickListener(this);
        deleteIv.setOnClickListener(this);
        backIv.setOnClickListener(this);

        adapter = new CityManagerAdapter(this, mdatas);
        cityIv.setAdapter(adapter);

    }

    @Override
    protected void onResume(){
        super.onResume();
        List<dbBean> list = dbManager.queryAllInfo();
        mdatas.clear();
        mdatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.city_iv_add){
            int cityCount = dbManager.getCityCount();
            if(cityCount<5){
                Intent intent = new Intent(this, SearchCityActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "city number is 5, please delete one", Toast.LENGTH_SHORT).show();
            }
        }
        if(v.getId() == R.id.city_iv_back){
            finish();
        }
        if(v.getId() == R.id.city_iv_delete){
            Intent intent1 = new Intent(this, DeleteCityActivity.class);
            startActivity(intent1);
        }
    }
}