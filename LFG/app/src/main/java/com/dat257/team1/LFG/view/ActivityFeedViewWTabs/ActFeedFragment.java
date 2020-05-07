package com.dat257.team1.LFG.view.ActivityFeedViewWTabs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.view.ActivityCardRecyclerAdapter;
import com.dat257.team1.LFG.view.ActivityDescription.ActivityDescriptionView;
import com.dat257.team1.LFG.view.CreateActivityView;
import com.dat257.team1.LFG.view.ICardViewHolderClickListener;
import com.dat257.team1.LFG.view.NotificationView;
import com.dat257.team1.LFG.viewmodel.ActFeedWTabsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ActFeedFragment extends Fragment implements ICardViewHolderClickListener, LifecycleObserver {

    private static final String LOG_TAG = CreateActivityView.class.getSimpleName();

    private RecyclerView.Adapter mAdapter;

    private Button createActivity;
    private Button menu;
    private Button logOut;

    private ActFeedWTabsViewModel actFeedWTabsViewModel;
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
        actFeedWTabsViewModel = new ViewModelProvider(this).get(ActFeedWTabsViewModel.class);
        getLifecycle().addObserver(actFeedWTabsViewModel);
        actFeedWTabsViewModel.onCreate();

        mutableActivityList = actFeedWTabsViewModel.getMutableActivityList();
        mutableActivityList.observe(this, new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activities) {
                mAdapter.notifyDataSetChanged();
            }
        });

        setUpRecyclerView();
        updateFeed();
    }


    void launchGoogleMaps() {
        FragmentManager fm = getSupportFragmentManager();
        MapFragment gm = new MapFragment();
        fm.beginTransaction().replace(R.id.activityFeed, gm).commit();
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




    private void clickMenu() {
        Intent intent = new Intent(this, NotificationView.class);
        startActivity(intent);
    }

    private void updateFeed() {
        actFeedWTabsViewModel.updateFeed();
    }

    public void launchCreateActivity() {
        Log.d(LOG_TAG, "Create activity clicked!");
        Intent intent = new Intent(this, CreateActivityView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void launchActivityFeedWTabsView() {
        Log.d(LOG_TAG, "Create activity clicked!");
        Intent intent = new Intent(this, ActivityFeedWTabsView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onCardClicked(int pos) {
        Log.d(LOG_TAG, "Card Clicked!");
        actFeedWTabsViewModel.onCardClick(pos);
        Intent intent = new Intent(this, ActivityDescriptionView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
