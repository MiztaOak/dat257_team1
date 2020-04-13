package com.dat257.team1.LFG.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.viewmodel.FeedViewModel;

import java.util.List;

public class FeedView extends AppCompatActivity {

    private static final String LOG_TAG = CreateActivityView.class.getSimpleName();

    private FeedViewModel feedViewModel;
    private MutableLiveData<List<Activity>> mutableActivityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        getLifecycle().addObserver(feedViewModel);
        feedViewModel.onCreate();

        mutableActivityList = feedViewModel.getMutableActivityList();
        feedViewModel.getMutableActivityList().observe(this, new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activities) {
                //update feed
            }
        });
    }

    public void createActivityButton(View view) {
        Log.d(LOG_TAG, "Create activity clicked!");
        Intent intent = new Intent(this, CreateActivityView.class);
        startActivity(intent);
    }

}