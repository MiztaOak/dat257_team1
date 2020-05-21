package com.dat257.team1.LFG.view.activityFeed;

import android.os.Bundle;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.view.CreateActFragment;
import com.dat257.team1.LFG.view.ICardViewHolderClickListener;
import com.dat257.team1.LFG.viewmodel.ActFeedViewModel;

import java.util.List;

public class ActFeedListFragment extends Fragment implements ICardViewHolderClickListener, LifecycleObserver {

    private static final String LOG_TAG = CreateActFragment.class.getSimpleName();

    private RecyclerView.Adapter mAdapter;
    private ActFeedViewModel actFeedViewModel;
    private MutableLiveData<List<Activity>> mutableActivityList;
    private final int ITEM_MARGIN = 10;
    private final int NUM_COLUMNS = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_act_feed_list, container, false);

        actFeedViewModel = new ViewModelProvider(this).get(ActFeedViewModel.class);
        getLifecycle().addObserver(actFeedViewModel);
        actFeedViewModel.onCreate();

        mutableActivityList = actFeedViewModel.getMutableActivityList();
        mutableActivityList.observe(getViewLifecycleOwner(), new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activityList) {
                mAdapter.notifyDataSetChanged(); //TODO maybe not change everything e.g. when scrolling.
            }
        });

        RecyclerView recyclerView = rootView.findViewById(R.id.activityFeed);
        mAdapter = new ActCardRecyclerAdapter(getContext(), mutableActivityList, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ActCardRecyclerAdapter.RecyclerViewMargin decoration = new ActCardRecyclerAdapter.RecyclerViewMargin(ITEM_MARGIN, NUM_COLUMNS);
        recyclerView.addItemDecoration(decoration);

        updateActFeed();

        return rootView;
    }

    private void updateActFeed() {
        actFeedViewModel.updateFeed();
    }

    @Override
    public void onCardClicked(View view, int pos) {
        actFeedViewModel.onItemClick(pos);
        Navigation.findNavController(view).navigate(R.id.action_nav_act_feed_to_activityDescriptionView);
    }
}
