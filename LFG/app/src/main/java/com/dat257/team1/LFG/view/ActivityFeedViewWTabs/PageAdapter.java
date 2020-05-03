package com.dat257.team1.LFG.view.ActivityFeedViewWTabs;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dat257.team1.LFG.view.ActivityDescription.ActivityDescriptionFragment;

public class PageAdapter extends FragmentPagerAdapter {
    private int nbrOfTabs;

    public PageAdapter(@NonNull FragmentManager fm, int nbrOfTabs) {
        super(fm);
        this.nbrOfTabs = nbrOfTabs;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ActivityDescriptionFragment(); //TODO: check if it's the one shown in recyclerView feed in activity_feed
            case 1:
                return new Fragment();  //TODO: add map fragment
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
