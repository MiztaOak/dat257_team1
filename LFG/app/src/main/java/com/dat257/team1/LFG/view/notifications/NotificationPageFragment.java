package com.dat257.team1.LFG.view.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.viewmodel.NotificationViewModel;
import com.google.android.material.tabs.TabLayout;

public class NotificationPageFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notif_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NotificationPageAdapter notificationPageAdapter = new NotificationPageAdapter(getParentFragmentManager(), getContext());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(notificationPageAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);


        NotificationViewModel notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        getLifecycle().addObserver(notificationViewModel);
    }

}
