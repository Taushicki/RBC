package com.example.rbc.Feed;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rbc.R;

public class NewsWithImageViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView title;
    public TextView time;

    public NewsWithImageViewHolder(View itemView){
        super(itemView);
        image = itemView.findViewById(R.id.image);
        title = itemView.findViewById(R.id.title);
        time = itemView.findViewById(R.id.time);
    }


}
