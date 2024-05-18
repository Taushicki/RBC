package com.example.rbc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.example.rbc.swipe.OnSwipeTouchListener;

import org.example.client.dto.NewsDTO;

public class NewsPage extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        NewsDTO news = (NewsDTO) getIntent().getSerializableExtra("news");

        if (news.hasImg()) {
            setContentView(R.layout.news_page_with_image);
        } else {
            setContentView(R.layout.news_page_without_image);
        }

        initializeUI(news);

        View rootView = findViewById(R.id.root_view);

        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NestedScrollView scrollView = findViewById(R.id.scroll_view);
        scrollView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                onBackPressed();
            }
        });
    }

    private void initializeUI(NewsDTO news) {
        ImageView img = findViewById(R.id.news_image);
        TextView title = findViewById(R.id.news_title);
        TextView content = findViewById(R.id.news_content);

        if (img != null) {
            Glide.with(this).load(news.getImg()).into(img);
        }

        if (title != null) {
            title.setText(news.getTitle());
        }

        if (content != null) {
            content.setText(news.getText());
        }
    }
}