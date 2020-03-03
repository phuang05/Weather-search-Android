package com.example.penghuang.hw9_01.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.penghuang.hw9_01.Class.Eight_days_info;
import com.example.penghuang.hw9_01.R;

import java.util.List;

/**
 * Created by penghuang on 11/24/19.
 */

public class MyListAdapter extends ArrayAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int resourceId;
    public MyListAdapter(Context context,
                         int textViewResourceId,
                         List<Eight_days_info> objects){
        super(context,textViewResourceId,objects);
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        resourceId = textViewResourceId;
//        super(context,objects);
    }

//    @Override
//    public int getCount() {
//        return 8;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return super.getItem(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }

    static class ViewHolder{
        public ImageView imageView;
        public TextView tvTime,tvTemperatureHigh,tvTemperatureLow;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
//        Log.d("tags",""+getItem(i));
        Eight_days_info info = (Eight_days_info) getItem(i);
        if(view == null){
            view = mLayoutInflater.inflate(R.layout.list_item,null);
            holder = new ViewHolder();
            holder.tvTime = view.findViewById(R.id.day_time);
            holder.tvTemperatureLow = view.findViewById(R.id.day_low);
            holder.tvTemperatureHigh = view.findViewById(R.id.day_high);
            holder.imageView = view.findViewById(R.id.day_img);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        holder.tvTime.setText(info.getDate());
        holder.tvTemperatureHigh.setText(Integer.toString(info.getTemperatureHigh()));
        holder.tvTemperatureLow.setText(Integer.toString(info.getTemperatureLow()));
        holder.imageView.setImageResource(info.getIcon());


        return view;
    }
}
