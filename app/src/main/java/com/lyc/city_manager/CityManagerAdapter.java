package com.lyc.city_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lyc.RESTapi.JHIndexBean;
import com.lyc.RESTapi.JHTempBean;
import com.lyc.db.dbBean;
import com.lyc.weatherap.bean.WeatherBean;
import com.lyc.weatherapp.R;

import java.util.List;

public class CityManagerAdapter extends BaseAdapter {
    Context context;
    List<dbBean> mdatas;

    public CityManagerAdapter(Context context, List<dbBean>mdatas){
        this.context = context;
        this.mdatas = mdatas;
    }

    @Override
    public int getCount() {
        return mdatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mdatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_city_manager, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        dbBean bean = mdatas.get(i);
        holder.cityTv.setText(bean.getCity());

        JHTempBean jhTempBean = new Gson().fromJson(bean.getContent(), JHTempBean.class);
        JHTempBean.ResultBean jhResult = jhTempBean.getResult();
        JHTempBean.ResultBean.RealtimeBean jhRealtime = jhResult.getRealtime();
        JHTempBean.ResultBean.FutureBean jhTodayBean = jhResult.getFuture().get(0);

        holder.conTv.setText("weather:"+ jhRealtime.getInfo());

        holder.currentTempTv.setText(jhRealtime.getTemperature()+"℃");
        holder.windTv.setText(jhRealtime.getDirect()+jhRealtime.getPower());
        holder.tempRangeTv.setText(jhTodayBean.getTemperature());
        return view;
    }

    class ViewHolder{
        TextView cityTv, conTv, currentTempTv, windTv, tempRangeTv;

        public ViewHolder(View itemView){
            cityTv = itemView.findViewById(R.id.item_city_tv_city);
            currentTempTv = itemView.findViewById(R.id.item_center_tv_temp);
            conTv = itemView.findViewById(R.id.item_city_tv_condition);
            windTv = itemView.findViewById(R.id.item_city_wind);
            tempRangeTv = itemView.findViewById(R.id.item_city_temprange);
        }

    }
}
