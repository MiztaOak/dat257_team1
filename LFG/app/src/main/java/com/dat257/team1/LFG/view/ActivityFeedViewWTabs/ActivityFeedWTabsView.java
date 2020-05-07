package com.dat257.team1.LFG.view.ActivityFeedViewWTabs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.service.LocationService;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ActivityFeedWTabsView extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2;
    public PageAdapter pageAdapter;
    private List<Activity> activities;
    private LocationService locationService;
    private MapFragment gm;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVar();
        fetchActivityLoc();
        fetchCurrentLocation();
        tabLayoutListener();
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    /**
     * A method to initiate all instance variables
     */
    private void initVar() {
        activities = FireStoreHelper.getInstance().getActivities();
        //  locationService = new LocationService(this);
        setContentView(R.layout.activity_feed_w_tabs);
        tabLayout = findViewById(R.id.activity_feed_tab);
        tab1 = findViewById(R.id.recyclerView_feed);
        //tab2 = findViewById(R.id.mapFragment);
        viewPager = findViewById(R.id.view_pager_feed);
        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

    }

    /**
     * A listener on swiping between the tabs
     */
    private void tabLayoutListener() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pageAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //TODO: add functionality for when tab is unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //TODO: add functionality for when tab is reselected
            }
        });
    }

    /**
     * A method that fetches the current location and sends it to the map
     */
    void fetchCurrentLocation() {
        LatLng location = new LatLng(57.60, 11.97456000);

        gm = new MapFragment(location, this);
    }

    /**
     * A method that sets all activities to the map
     */
    private void fetchActivityLoc() {
        // gm.setActivityList(activities);
    }

    /**
     * To add a new activity to the map
     *
     * @param activity the new activity
     */
    void addActivityToMap(Activity activity) {
        gm.addActivity(activity);
    }


}
