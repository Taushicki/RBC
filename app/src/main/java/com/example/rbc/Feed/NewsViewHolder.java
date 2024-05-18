package com.example.rbc.Feed;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rbc.MainActivity;
import com.example.rbc.NewsPage;
import com.example.rbc.R;
import org.example.client.dto.NewsDTO;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssZ");
    private NewsDTO news;
    public TextView title;
    public TextView time;

    public NewsViewHolder(View itemView, Context parentContext){
        super(itemView);
        title = itemView.findViewById(R.id.title);
        time = itemView.findViewById(R.id.time);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentContext.startActivity(new Intent(parentContext, NewsPage.class).putExtra("news", news));
            }
        });
    }

    public void createPreview(NewsDTO news){
        this.news = news;
        title.setText(news.getTitle());
        time.setText(ZonedDateTime.parse(news.getPubDate(), formatter).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().toString().substring(11, 16));
    }
}
