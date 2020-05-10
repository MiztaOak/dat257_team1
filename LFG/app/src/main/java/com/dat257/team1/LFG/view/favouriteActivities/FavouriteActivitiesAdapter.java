package com.dat257.team1.LFG.view.favouriteActivities;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dat257.team1.LFG.R;

public class FavouriteActivitiesAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_all_act1, R.string.tab_text_all_act2};
    private final Context mContext;

    public FavouriteActivitiesAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new AllActivitiesFragment();
                break;

            case 1:
                fragment = new FavouriteActivityFragment();
                break;
            default:
                return null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}
