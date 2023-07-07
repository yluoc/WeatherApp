package com.lyc.city_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyc.weatherapp.R;

import java.util.List;

public class DeleteCityAdapter extends BaseAdapter {
    Context context;
    List<String> mdatas;
    List<String> deleteCities;

    public DeleteCityAdapter(Context context, List<String> mdatas, List<String>deleteCities){
        this.context = context;
        this.mdatas = mdatas;
        this.deleteCities = deleteCities;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_deletecity, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        final String city = mdatas.get(i);
        holder.tv.setText(city);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdatas.remove(city);
                deleteCities.add(city);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    class ViewHolder{
        TextView tv;
        ImageView iv;

        public ViewHolder(View itemView){
            tv = itemView.findViewById(R.id.item_delete_tv);
            iv = itemView.findViewById(R.id.item_delete_iv);
        }
    }
}
