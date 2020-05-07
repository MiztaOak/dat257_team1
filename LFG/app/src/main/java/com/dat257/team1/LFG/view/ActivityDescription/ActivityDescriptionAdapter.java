package com.dat257.team1.LFG.view.activityDescription;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivityDescriptionAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitle = new ArrayList<>();


    public ActivityDescriptionAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    public void addFragment (Fragment fragment, String title){

        mFragmentList.add(fragment);
        mFragmentTitle.add(title);


    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
