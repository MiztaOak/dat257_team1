package com.dat257.team1.LFG.view.favouriteActivities;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.view.loginPage.LoginPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FavouriteActivityView extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Just testing whether the fragment works.
        setContentView(R.layout.all_activities_page);
        FavouriteActivitiesAdapter favouritePagerAdapter = new FavouriteActivitiesAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager2);
        viewPager.setAdapter(favouritePagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs2);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
    }

}
