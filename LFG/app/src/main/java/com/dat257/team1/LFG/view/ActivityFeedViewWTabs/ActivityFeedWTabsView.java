package com.dat257.team1.LFG.view.ActivityFeedViewWTabs;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.service.LocationService;
import com.dat257.team1.LFG.view.loginPage.LoginPagerAdapter;
import com.dat257.team1.LFG.viewmodel.ActFeedWTabsViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ActivityFeedWTabsView extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2;
    public ActPageAdapter actPageAdapter;
    private List<Activity> activities;
    private LocationService locationService;
    private MapFeedFragment gm;
    ActFeedWTabsViewModel actFeedWTabsViewModel;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_w_tabs);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ActPageAdapter actPageAdapter = new ActPageAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_feed);
        viewPager.setAdapter(actPageAdapter);
        TabLayout tabs = findViewById(R.id.activity_feed_tab);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);

        tabLayoutListener();
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


    /**
     * A listener on swiping between the tabs
     */
    private void tabLayoutListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    actPageAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    actPageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //TODO: add functionality for when tab is unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //
                //TODO: add functionality for when tab is reselected
            }
        });
    }
}
