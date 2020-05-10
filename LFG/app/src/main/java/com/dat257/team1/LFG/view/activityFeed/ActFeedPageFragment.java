package com.dat257.team1.LFG.view.activityFeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.viewmodel.ActFeedViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class ActFeedPageFragment extends Fragment {

    ActFeedViewModel actFeedViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActPageAdapter actPageAdapter = new ActPageAdapter(this, getParentFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager_feed);
        viewPager.setAdapter(actPageAdapter);
        TabLayout tabs = view.findViewById(R.id.activity_feed_tab);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_feed_w_tabs, container, false);
        //Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return rootView;
    }
}
