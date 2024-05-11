package com.example.rbc.Feed;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rbc.R;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView time;
    public NewsViewHolder(View itemView){
        super(itemView);
        title = itemView.findViewById(R.id.title);
        time = itemView.findViewById(R.id.time);

    }
}
