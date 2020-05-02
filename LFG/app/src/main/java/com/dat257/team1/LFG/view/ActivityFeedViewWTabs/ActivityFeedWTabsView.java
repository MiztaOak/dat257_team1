package com.dat257.team1.LFG.view.ActivityFeedViewWTabs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dat257.team1.LFG.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class ActivityFeedWTabsView extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2;
    public PageAdapter pageAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_w_tabs);

        tabLayout = findViewById(R.id.activity_feed_tab);
        tab1 = findViewById(R.id.recyclerView_feed);
        tab2 = findViewById(R.id.map);
        viewPager = findViewById(R.id.view_pager_feed);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pageAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1){
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

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }
}
