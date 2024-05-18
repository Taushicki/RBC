package com.example.rbc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.rbc.Feed.Adapter;
import com.example.rbc.Feed.ScrollListener;

import org.example.client.ApiServices.ApiNewsServices;

import com.example.rbc.background_tasks.LoadNews;
import com.example.rbc.preference.UserDataManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Adapter adapter;
    private ApiNewsServices api;
    private int countNews = 0;
    private boolean isLoadingData = false;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        api = new ApiNewsServices();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView feed = findViewById(R.id.feed);
        feed.setLayoutManager(layoutManager);
        adapter = new Adapter(new ArrayList<>(), MainActivity.this);
        feed.setAdapter(adapter);

        new LoadNews(countNews, api, adapter, MainActivity.this).execute();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (!new UserDataManager(getBaseContext()).getLoginStatus()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        ScrollListener scroll = new ScrollListener(layoutManager, new ScrollListener.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(!isLoadingData){
                    isLoadingData = true;
                    countNews += 40;
                    new LoadNews(countNews, api, adapter, MainActivity.this).execute();
                }
            }
        });
        feed.addOnScrollListener(scroll);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void refreshData() {
        countNews = 0;
        adapter.clearNewsList();
        new LoadNews(countNews, api, adapter, MainActivity.this).execute();
    }

    public void setIsLoadingData(boolean isLoadingData) {
        this.isLoadingData = isLoadingData;
        swipeRefreshLayout.setRefreshing(false);
    }
}