package com.dat257.team1.LFG.view.activityFeed;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dat257.team1.LFG.R;

public class MyActPageAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_5, R.string.tab_text_6};
    private final MyActPageFragment mContext;

    public MyActPageAdapter(MyActPageFragment context, @NonNull FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CurrentActFragment(); //TODO not new maybe
            case 1:
                return new PreviousActFragment(); //TODO
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        //Two tabs shown
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}

