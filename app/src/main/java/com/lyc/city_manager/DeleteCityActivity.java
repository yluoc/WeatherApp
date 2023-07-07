package com.lyc.city_manager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.lyc.db.dbManager;
import com.lyc.weatherapp.R;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class DeleteCityActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView errorIv, rightIv;
    ListView deleteLv;
    List<String> mdatas;
    List<String>deleteCities;
    private DeleteCityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_city);
        errorIv = findViewById(R.id.delete_iv_error);
        rightIv = findViewById(R.id.delete_iv_right);
        deleteLv = findViewById(R.id.delete_lv);
        mdatas = dbManager.queryAllCityName();
        deleteCities = new ArrayList<>();

        errorIv.setOnClickListener(this);
        rightIv.setOnClickListener(this);

        adapter = new DeleteCityAdapter(this, mdatas, deleteCities);
        deleteLv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.delete_iv_error){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("info").setMessage("Are you sure？").setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setNegativeButton("cancel", null);
            builder.create().show();
        }
        if(view.getId() == R.id.delete_iv_right){
            for(int i=0; i< deleteCities.size();i++){
                String city = deleteCities.get(i);
                int i1 = dbManager.deleteinfobyCity(city);
            }
            finish();
        }
    }
}