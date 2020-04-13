package com.dat257.team1.LFG.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.viewmodel.CreateActivityViewModel;

public class CreateActivityView extends AppCompatActivity {

    private static final String LOG_TAG = CreateActivityView.class.getSimpleName();

    CreateActivityViewModel createActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);
        createActivityViewModel = new ViewModelProvider(this).get(CreateActivityViewModel.class);
    }

    public void createActivity(View view) { //maybe get a confirmation instead of being returned to the feed?
        createActivityViewModel.createActivity(); //more data
        Log.d(LOG_TAG, "Create activity clicked!");
        Intent intent = new Intent(this, FeedView.class);
        startActivity(intent);
    }


    public void backButton(View view) {
        Log.d(LOG_TAG, "Back-button clicked!");
        Intent intent = new Intent(this, FeedView.class);
        startActivity(intent);
    }
}



