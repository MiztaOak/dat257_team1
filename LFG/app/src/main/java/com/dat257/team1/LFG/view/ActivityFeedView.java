package com.dat257.team1.LFG.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.viewmodel.ActivityFeedViewModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityFeedView extends AppCompatActivity {

    private static final String LOG_TAG = CreateActivityView.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button createActivity;
    private Button menu;

    private ActivityFeedViewModel activityFeedViewModel;
    private MutableLiveData<List<Activity>> mutableActivityList;
    private ArrayList<CardsView> cardsList = new ArrayList<>();
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        activityFeedViewModel = new ViewModelProvider(this).get(ActivityFeedViewModel.class);
        getLifecycle().addObserver(activityFeedViewModel);
        activityFeedViewModel.onCreate();

        mutableActivityList = activityFeedViewModel.getMutableActivityList();
        mutableActivityList.observe(this, new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activities) {
                //update feed
            }
        });

        /*ArrayList<CardsView> cardsList = new ArrayList<>();
        cardsList.add(new CardsView(R.drawable.ic_android_black_24dp, "Fotboll", "fotboll på heden kl 13:00"));
        cardsList.add(new CardsView(R.drawable.ic_radio_button_unchecked_black_24dp, "Basketspelare sökes", "söker basketspelare till match 14:00"));
        cardsList.add(new CardsView(R.drawable.ic_mood_black_24dp, "tennis", "tennis på heden kl 13:00"));
        cardsList.add(new CardsView(R.drawable.ic_watch_later_black_24dp, "padel", "padel på heden kl 13:00"));
        */

        mRecyclerView = findViewById(R.id.recyclerView_feed);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CardAdapter(cardsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        createActivity = (Button) findViewById(R.id.createActivity);
        createActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateActivity();
            }
        });

        /*menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //TODO create method invocation
            }
        }));*/
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public RecyclerView.Adapter getmAdapter() {
        return mAdapter;
    }

    public ArrayList<CardsView> getCardsList(){
        return cardsList;
    }

    public void launchCreateActivity() {
        Log.d(LOG_TAG, "Create activity clicked!");
        Intent intent = new Intent(this, CreateActivityView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
