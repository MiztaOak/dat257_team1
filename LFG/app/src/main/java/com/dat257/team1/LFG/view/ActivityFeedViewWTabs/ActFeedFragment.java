package com.dat257.team1.LFG.view.ActivityFeedViewWTabs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.view.ActivityDescription.ActivityDescriptionView;
import com.dat257.team1.LFG.view.CreateActivityView;
import com.dat257.team1.LFG.viewmodel.ActFeedWTabsViewModel;

import java.util.List;

public class ActFeedFragment extends Fragment implements ICardViewHolderClickListener, LifecycleObserver {

    private static final String LOG_TAG = CreateActivityView.class.getSimpleName();

    private RecyclerView.Adapter mAdapter;
    private ActFeedWTabsViewModel actFeedWTabsViewModel;
    private MutableLiveData<List<Activity>> mutableActivityList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_feed, container, false);
        setUpRecyclerView(rootView);
        actFeedWTabsViewModel = new ViewModelProvider(this).get(ActFeedWTabsViewModel.class);
        //getLifecycle().addObserver(actFeedWTabsViewModel); //TODO
        //actFeedWTabsViewModel.onCreate();
        mutableActivityList = actFeedWTabsViewModel.getMutableActivityList();
        mutableActivityList.observe(getViewLifecycleOwner(), new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activityList) {
                mAdapter.notifyDataSetChanged(); //TODO maybe not change everything e.g. when scrolling.
            }
        });
        updateFeed();

        return rootView;
    }

    private void setUpRecyclerView(View rootView) {
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_feed);
        mAdapter = new ActivityCardRecyclerAdapter(getContext(), mutableActivityList, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void updateFeed() {
        actFeedWTabsViewModel.updateFeed();
    }

    @Override
    public void onCardClicked(int pos) {
        Log.d(LOG_TAG, "Card Clicked!");
        actFeedWTabsViewModel.onItemClick(pos);
        Intent intent = new Intent(getContext(), ActivityDescriptionView.class); //TODO maybe not call directly here do from parent, not sure.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
