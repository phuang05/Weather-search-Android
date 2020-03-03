package com.example.penghuang.hw9_01.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.penghuang.hw9_01.R;

import java.util.List;

/**
 * Created by penghuang on 11/30/19.
 */

public class LinearAdapter  extends RecyclerView.Adapter<LinearAdapter.LinearViewHolder>{

    private Context mcontext;
    private List<String> list;
    public LinearAdapter(Context mcontext, List list){
        this.mcontext = mcontext;
        this.list = list;
    }


    @NonNull
    @Override
    public LinearAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new LinearViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.linearlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearAdapter.LinearViewHolder holder, int position) {


            Glide.with(mcontext).load(list.get(position)).into( (ImageView) holder.imageView);

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;

        public LinearViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
