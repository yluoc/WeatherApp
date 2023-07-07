package com.lyc.city_manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lyc.RESTapi.JHTempBean;
import com.lyc.RESTapi.urlUtils;
import com.lyc.base.baseActivity;
import com.lyc.MainActivity;
import com.lyc.weatherapp.R;

public class SearchCityActivity extends baseActivity implements View.OnClickListener{
    EditText searchEt;
    ImageView submitIv;
    GridView searchGv;
    String city;
    String[]hotCities = {"北京","上海","广州","深圳","珠海","佛山","南京","苏州","厦门","长沙","成都","福州","杭州"
            ,"武汉","青岛","西安","太原","沈阳","重庆","天津","南宁"};
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        searchEt = findViewById(R.id.search_et);
        submitIv = findViewById(R.id.search_iv_submit);
        searchGv = findViewById(R.id.search_gv);
        submitIv.setOnClickListener(this);

        adapter = new ArrayAdapter<>(this, R.layout.item_popularcity, hotCities);
        searchGv.setAdapter(adapter);
        setListener();
    }

    private void setListener(){
        searchGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                city = hotCities[i];
                String url = urlUtils.getTemp_url(city);
                loadData(url);
            }
        });
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.search_iv_submit){
            city = searchEt.getText().toString();
            if(!TextUtils.isEmpty(city)){
                String url = urlUtils.getTemp_url(city);
                loadData(url);
            }else{
                Toast.makeText(this, "can not enter empty", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccess(String result){
        JHTempBean weatherBean = new Gson().fromJson(result, JHTempBean.class);
        if(weatherBean.getError_code() == 0){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("city",city);
            startActivity(intent);
        }else{
            Toast.makeText(this, "no info", Toast.LENGTH_SHORT).show();
        }
    }
}