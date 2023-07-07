package com.lyc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lyc.db.dbManager;
import com.lyc.weatherapp.R;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener{
    TextView bgTv, cacheTv, versionTv, shareTv;
    RadioGroup exbgRg;
    ImageView backIv;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        bgTv = findViewById(R.id.more_tv_exchangebg);
        cacheTv = findViewById(R.id.more_tv_cache);
        versionTv = findViewById(R.id.more_tv_verision);
        shareTv = findViewById(R.id.more_tv_share);
        backIv = findViewById(R.id.more_iv_back);
        exbgRg = findViewById(R.id.more_rg);

        bgTv.setOnClickListener(this);
        cacheTv.setOnClickListener(this);
        shareTv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        pref = getSharedPreferences("bg_pref", MODE_PRIVATE);
        String versionname = getVersionName();
        versionTv.setText("current version:     v"+versionname);
        setRGListener();
    }

    private void setRGListener(){
        exbgRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int bg = pref.getInt("bg",0);
                SharedPreferences.Editor editor = pref.edit();
                Intent intent = new Intent(MoreActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                if(i == R.id.more_rb_green){
                    if(bg == 0){
                        Toast.makeText(MoreActivity.this, "Same background",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    editor.putInt("bg",0);
                    editor.commit();
                }
                if(i == R.id.more_rb_pink){
                    if(bg == 1){
                        Toast.makeText(MoreActivity.this, "Same background",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    editor.putInt("bg",1);
                    editor.commit();
                }
                if(i == R.id.more_rb_blue){
                    if(bg == 2){
                        Toast.makeText(MoreActivity.this, "Same background",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    editor.putInt("bg",2);
                    editor.commit();
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.more_iv_back){
            finish();
        }
        if(view.getId() == R.id.more_tv_cache){
            clearCache();
        }
        if(view.getId() == R.id.more_tv_share){
            shareSoftwareInfo("talk about weather APP is great and easy");
        }
        if(view.getId() == R.id.more_tv_exchangebg){
            if(exbgRg.getVisibility() == View.VISIBLE){
                exbgRg.setVisibility(View.GONE);
            }else{
                exbgRg.setVisibility(View.VISIBLE);
            }
        }
    }

    private void shareSoftwareInfo(String s){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,s);
        startActivity(Intent.createChooser(intent,"talk about weather"));
    }

    private void clearCache(){
        AlertDialog.Builder builder = new AlertDialog.Builder((this));
        builder.setTitle("Tips").setMessage("You will clear the cache!");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbManager.deleteAllInfo();
                Toast.makeText(MoreActivity.this, "Clear all the cache！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MoreActivity.this, MoreActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).setNegativeButton("NO", null);
        builder.create().show();
    }

    public String getVersionName(){
        PackageManager manager = getPackageManager();
        String versionName = null;
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            versionName = info.versionName;
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return versionName;
    }
}