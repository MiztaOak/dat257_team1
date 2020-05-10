package com.dat257.team1.LFG.view.activityFeed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.view.CreateActivityView;
import com.dat257.team1.LFG.viewmodel.ActFeedViewModel;
import com.google.android.material.tabs.TabLayout;

public class ActFeedPageView extends AppCompatActivity {

    private static final String LOG_TAG = ActFeedPageView.class.getSimpleName();
    Button createActivity;
    ActFeedViewModel actFeedViewModel;

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

        //tabLayoutListener();
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        createActivity = (Button) findViewById(R.id.createActivity);
        createActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateActivity();
            }
        });

    }

    public void launchCreateActivity() {
        Log.d(LOG_TAG, "Create activity clicked!");
        Intent intent = new Intent(this, CreateActivityView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /*
     * A listener on swiping between the tabs

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
    }*/
}
