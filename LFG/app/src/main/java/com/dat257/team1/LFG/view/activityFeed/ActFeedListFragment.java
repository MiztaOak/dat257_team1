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
    private final int ITEM_MARGIN = 2;
    private final int NUM_COLUMNS = 1;
    private ActCardRecyclerAdapter actCardRecyclerAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private View rootView;
    private ActFeedViewModel actFeedViewModel;
    private MutableLiveData<List<Activity>> mutableActivityList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_act_feed_list, container, false);
        }

        actFeedViewModel = new ViewModelProvider(this).get(ActFeedViewModel.class);
        getLifecycle().addObserver(actFeedViewModel);

        mutableActivityList = actFeedViewModel.getMutableActivityList();
        mutableActivityList.observe(getViewLifecycleOwner(), new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activityList) {
                actCardRecyclerAdapter.notifyDataSetChanged();
            }
        });

        recyclerView = rootView.findViewById(R.id.activityFeed);
        actCardRecyclerAdapter = new ActCardRecyclerAdapter(getContext(), mutableActivityList, this);
        recyclerView.setAdapter(actCardRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ActCardRecyclerAdapter.RecyclerViewMargin decoration = new ActCardRecyclerAdapter.RecyclerViewMargin(ITEM_MARGIN, NUM_COLUMNS);
        recyclerView.addItemDecoration(decoration);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCardClicked(View view, int pos) {
        actFeedViewModel.onItemClick(pos);
        Navigation.findNavController(view).navigate(R.id.action_nav_act_feed_to_activityDescriptionView);
    }
}
