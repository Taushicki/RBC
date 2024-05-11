package com.example.rbc.background_tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.rbc.Feed.Adapter;
import com.example.rbc.MainActivity;

import org.example.client.ApiServices.ApiNewsServices;
import org.example.client.Main;
import org.example.client.dto.NewsDTO;

import java.util.List;

public class LoadNews extends AsyncTask<Void, Void, List<NewsDTO>> {
    private final int offset;
    private final ApiNewsServices api;
    private final Adapter adapter;
    private final MainActivity mainActivity;

    public LoadNews(int offset, ApiNewsServices api, Adapter adapter, MainActivity mainActivity){
        this.offset = offset;
        this.api = api;
        this.adapter = adapter;
        this.mainActivity = mainActivity;
    }

    @Override
    protected List<NewsDTO> doInBackground(Void... voids) {
        try {
            return api.getOffsetNews(offset, 40 + offset);
        } catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<NewsDTO> result) {
        super.onPostExecute(result);
        if (result != null) {
            try {
                adapter.updateData(result);
            } catch (Exception error){
                error.printStackTrace();
            }
        } else {
            Toast.makeText(adapter.getContext(), "Failed to load news", Toast.LENGTH_SHORT).show();
        }
        mainActivity.setIsLoadingData(false);
    }
}

