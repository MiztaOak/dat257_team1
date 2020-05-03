package com.dat257.team1.LFG.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.view.ActivityDescription.ActivityDescriptionView;
import com.dat257.team1.LFG.viewmodel.ActivityFeedViewModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityFeedView extends AppCompatActivity implements ICardViewHolderClickListener, LifecycleObserver {

    private static final String LOG_TAG = CreateActivityView.class.getSimpleName();

    private RecyclerView.Adapter mAdapter;

    private Button createActivity;
    private Button menu;

    private ActivityFeedViewModel activityFeedViewModel;
    private MutableLiveData<List<Activity>> mutableActivityList;
    private ArrayList<Activity> cardsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        cardsList = new ArrayList<>();
        activityFeedViewModel = new ViewModelProvider(this).get(ActivityFeedViewModel.class);
        getLifecycle().addObserver(activityFeedViewModel);
        activityFeedViewModel.onCreate();

        mutableActivityList = activityFeedViewModel.getMutableActivityList();
        mutableActivityList.observe(this, new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activities) {
                mAdapter.notifyDataSetChanged();
            }
        });

        setUpRecyclerView();
        updateFeed();

        createActivity = (Button) findViewById(R.id.createActivity);
        menu = (Button) findViewById(R.id.menu);
        createActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateActivity();
            }
        });

        menu.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMenu();
            }
        }));
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_feed);
        mAdapter = new ActivityCardRecyclerAdapter(this, mutableActivityList, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateFeed() {
        activityFeedViewModel.updateFeed();
    }

    private void clickMenu() {
        Intent intent = new Intent(this, ActivityDescriptionView.class);
        startActivity(intent);
    }

    public void launchCreateActivity() {
        Log.d(LOG_TAG, "Create activity clicked!");
        Intent intent = new Intent(this, CreateActivityView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onCardClicked() {
        Log.d(LOG_TAG, "Card Clicked!");
        Intent intent = new Intent(this, ActivityDescriptionView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
