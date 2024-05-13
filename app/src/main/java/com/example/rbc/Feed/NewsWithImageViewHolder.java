package com.example.rbc.Feed;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rbc.NewsPage;
import com.example.rbc.R;

import org.example.client.dto.NewsDTO;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class NewsWithImageViewHolder extends RecyclerView.ViewHolder {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssZ");
    private NewsDTO news;
    public ImageView image;
    public TextView title;
    public TextView time;

    public NewsWithImageViewHolder(View itemView, Context parentContext){
        super(itemView);
        image = itemView.findViewById(R.id.image);
        title = itemView.findViewById(R.id.title);
        time = itemView.findViewById(R.id.time);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentContext.startActivity(new Intent(parentContext, NewsPage.class));
            }
        });
    }

    public void createPreview(NewsDTO news){
        this.news = news;
        Glide.with(itemView.getContext()).load(news.getImg()).into(image);
        title.setText(news.getTitle());
        time.setText(ZonedDateTime.parse(news.getPubDate(), formatter).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().toString().substring(11, 16));
    }


}
