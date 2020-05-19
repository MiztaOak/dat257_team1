package com.dat257.team1.LFG.view.myActivities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dat257.team1.LFG.R;
import com.google.android.material.tabs.TabLayout;

public class MyActPageFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        MyActPageAdapter myActPageAdapter = new MyActPageAdapter(this, getParentFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager_activities);
        viewPager.setAdapter(myActPageAdapter);
        TabLayout tabs = view.findViewById(R.id.my_activities_tab);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.my_activities_page, container, false);
        return rootView;
    }
}
