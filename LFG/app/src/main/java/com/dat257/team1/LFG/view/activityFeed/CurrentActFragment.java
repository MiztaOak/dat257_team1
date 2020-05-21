package com.dat257.team1.LFG.view.activityFeed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.view.ICardViewHolderClickListener;
import com.dat257.team1.LFG.viewmodel.CurrentActivitiesViewModel;

import java.util.List;


public class CurrentActFragment extends Fragment implements ICardViewHolderClickListener {
    private static final String LOG_TAG = CurrentActFragment.class.getSimpleName();

    private CurrentActivitiesViewModel viewModel;

    private MutableLiveData<List<Activity>> mutableOwnedActivities;
    private MutableLiveData<List<Activity>> mutableParticipatingActivities;

    private RecyclerView ownedRecyclerView;
    private RecyclerView.Adapter ownedReAdapter;
    private RecyclerView.LayoutManager ownedReLayoutManager;

    private RecyclerView participatingRecyclerView;
    private RecyclerView.Adapter participatingReAdapter;
    private RecyclerView.LayoutManager participatingReLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_current_activities, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CurrentActivitiesViewModel.class);
        getViewLifecycleOwner().getLifecycle().addObserver(viewModel);

        mutableOwnedActivities = viewModel.getMutableOwnedActivities();
        mutableOwnedActivities.observe(getViewLifecycleOwner(), activities -> {
            ownedReAdapter.notifyDataSetChanged();
        });
        mutableParticipatingActivities = viewModel.getMutableParticipatingActivities();
        mutableParticipatingActivities.observe(getViewLifecycleOwner(), activities -> {
            participatingReAdapter.notifyDataSetChanged();
        });

        ownedRecyclerView = (RecyclerView) view.findViewById(R.id.currentAct_owned);
        ownedRecyclerView.setHasFixedSize(false);
        ownedReLayoutManager = new LinearLayoutManager(getContext());
        ownedRecyclerView.setLayoutManager(ownedReLayoutManager);

        ownedReAdapter = new ActCardRecyclerAdapter(getContext(), mutableOwnedActivities, this);

        ownedRecyclerView.setAdapter(ownedReAdapter);

        participatingRecyclerView = (RecyclerView) view.findViewById(R.id.currentAct_par);
        participatingRecyclerView.setHasFixedSize(false);
        participatingReLayoutManager = new LinearLayoutManager(getContext());
        participatingRecyclerView.setLayoutManager(participatingReLayoutManager);

        participatingReAdapter = new ActCardRecyclerAdapter(getContext(), mutableParticipatingActivities, this);

        participatingRecyclerView.setAdapter(participatingReAdapter);
    }

    @Override
    public void onCardClicked(View view, int pos) {
        Log.d(LOG_TAG, "Card Clicked!");
        Navigation.findNavController(view).navigate(R.id.action_nav_my_activities_to_activityDescriptionView3);
    }
}
