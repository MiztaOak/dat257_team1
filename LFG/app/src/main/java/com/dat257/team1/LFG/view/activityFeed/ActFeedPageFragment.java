package com.dat257.team1.LFG.view.activityFeed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.view.CreateActFragment;
import com.dat257.team1.LFG.viewmodel.ActFeedViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ActFeedPageFragment extends Fragment {

    private static final String LOG_TAG = ActFeedPageFragment.class.getSimpleName();

    private ActFeedViewModel actFeedViewModel;
    private Button createActivity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActPageAdapter actPageAdapter = new ActPageAdapter(this, getParentFragmentManager());
        CustomViewPager viewPager = view.findViewById(R.id.view_pager_feed);
        viewPager.setAdapter(actPageAdapter);
        TabLayout tabs = view.findViewById(R.id.activity_feed_tab);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);

        LinearLayout tabStrip = ((LinearLayout)tabs.getChildAt(0));
        tabStrip.getChildAt(1).setOnTouchListener((view1, motionEvent) -> {
            if(FirebaseAuth.getInstance().getCurrentUser() != null){

                view1.performClick();
            }else{
                Toast.makeText(getContext(),"You must be signed in to access the map",Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        createActivity = (Button) view.findViewById(R.id.createActivity);
        createActivity.setOnClickListener(v -> {
            if(FirebaseAuth.getInstance().getCurrentUser() != null)
                launchCreateActivity(view);
            else
                Toast.makeText(getContext(),"You must be signed in to create an activity",Toast.LENGTH_SHORT).show();
        });
        actFeedViewModel = new ViewModelProvider(this).get(ActFeedViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_act_feed_page, container, false);
    }

    public void launchCreateActivity(View view) {
        Log.d(LOG_TAG, "Create activity clicked!");
        Navigation.findNavController(view).navigate(R.id.action_nav_act_feed_to_nav_createActivityFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        actFeedViewModel.updateFeed();
    }
}
