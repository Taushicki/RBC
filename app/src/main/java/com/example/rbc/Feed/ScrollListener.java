package com.example.rbc.Feed;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ScrollListener extends RecyclerView.OnScrollListener{
    private final LinearLayoutManager layoutManager;
    private final LoadMoreListener loadMoreListener;

    public ScrollListener(LinearLayoutManager layoutManager, LoadMoreListener loadMoreListener){
        this.layoutManager = layoutManager;
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy){
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0) {
            loadMoreListener.onLoadMore();
        }
    }


    public interface LoadMoreListener {
        void onLoadMore();
    }
}
