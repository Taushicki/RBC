package com.example.rbc.Feed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.rbc.R;
import org.example.client.dto.NewsDTO;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_WITH_IMAGE = 1;
    private static final int VIEW_TYPE_WITHOUT_IMAGE = 2;
    private final Context context;
    private final List<NewsDTO> newsList;

    public Adapter(List<NewsDTO> newsList, Context context){
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_WITH_IMAGE) {
            View itemView = inflater.inflate(R.layout.news_item_with_img, parent, false);
            return new NewsWithImageViewHolder(itemView, parent.getContext());
        } else {
            View itemView = inflater.inflate(R.layout.news_item_without_img, parent, false);
            return new NewsViewHolder(itemView, parent.getContext());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsDTO news = newsList.get(position);

        if (holder instanceof NewsWithImageViewHolder) {
            NewsWithImageViewHolder viewHolder = (NewsWithImageViewHolder) holder;
            viewHolder.createPreview(news);
        } else if (holder instanceof NewsViewHolder) {
            NewsViewHolder viewHolder = (NewsViewHolder) holder;
            viewHolder.createPreview(news);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        NewsDTO news = newsList.get(position);
        return news.hasImg() ? VIEW_TYPE_WITH_IMAGE : VIEW_TYPE_WITHOUT_IMAGE;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<NewsDTO> newData) {
        newsList.addAll(newData);
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }
}
