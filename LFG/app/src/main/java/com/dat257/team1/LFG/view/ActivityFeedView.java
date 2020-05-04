package com.dat257.team1.LFG.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
    private Button logOut;

    private ActivityFeedViewModel activityFeedViewModel;
    private MutableLiveData<List<Activity>> mutableActivityList;
    private DrawerLayout drawerLayout;
    private ArrayList<Activity> cardsList;

    Button button;

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
/*
       button = (Button) findViewById(R.id.go_to_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                Map gm = new Map();
                fm.beginTransaction().replace(R.id.activityFeed, gm).commit();

            }
        });
 */

        setUpRecyclerView();
        updateFeed();


        //Move this to the menu fragment instead of having it here. Change findview to logout
        //button instead, menu was a temporary hold.


        /*logOut = (Button) findViewById(R.id.menu);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLoginPage();
                LocalUser.signOut();
            }
        });

    */
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_feed);
        mAdapter = new ActivityCardRecyclerAdapter(this, mutableActivityList, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    //Move this to menu fragment.
    /*
    private void launchLoginPage(){
        Intent intent = new Intent(this, LoginPageView.class);
        startActivity(intent);
    }


     */


    private void clickMenu() {
        Intent intent = new Intent(this, NotificationView.class);
        startActivity(intent);
    }

    private void updateFeed() {
        activityFeedViewModel.updateFeed();
    }

    public void launchCreateActivity() {
        Log.d(LOG_TAG, "Create activity clicked!");
        Intent intent = new Intent(this, CreateActivityView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onCardClicked(int pos) {
        Log.d(LOG_TAG, "Card Clicked!");
        activityFeedViewModel.onCardClick(pos);
        Intent intent = new Intent(this, ActivityDescriptionView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
