package com.dat257.team1.LFG.view.activityFeed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.view.CreateActivityView;
import com.dat257.team1.LFG.viewmodel.ActFeedViewModel;
import com.google.android.material.tabs.TabLayout;

public class ActFeedPageFragment extends Fragment {

    private static final String LOG_TAG = ActFeedPageFragment.class.getSimpleName();

    private ActFeedViewModel actFeedViewModel;
    private Button createActivity;

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

        createActivity = (Button) view.findViewById(R.id.createActivity);
        createActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateActivity();
            }
        });
        actFeedViewModel = new ViewModelProvider(this).get(ActFeedViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_feed_w_tabs, container, false);
    }

    public void launchCreateActivity() {
        Log.d(LOG_TAG, "Create activity clicked!");
        Intent intent = new Intent(getContext(), CreateActivityView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}