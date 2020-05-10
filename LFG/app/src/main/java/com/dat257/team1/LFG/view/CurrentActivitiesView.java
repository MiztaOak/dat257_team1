package com.dat257.team1.LFG.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.view.ActivityDescription.ActivityDescriptionView;
import com.dat257.team1.LFG.viewmodel.CurrentActivitiesViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CurrentActivitiesView extends AppCompatActivity implements ICardViewHolderClickListener {
    private static final String LOG_TAG = CurrentActivitiesView.class.getSimpleName();

    private CurrentActivitiesViewModel viewModel;

    private MutableLiveData<List<Activity>> mutableOwnedActivities;
    private MutableLiveData<List<Activity>> mutableParticipatingActivities;

    private RecyclerView ownedRecyclerView;
    private RecyclerView.Adapter ownedReAdapter;
    private RecyclerView.LayoutManager ownedReLayoutManager;

    private RecyclerView participatingRecyclerView;
    private RecyclerView.Adapter participatingReAdapter;
    private RecyclerView.LayoutManager participatingReLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_activities);

        viewModel = new ViewModelProvider(this).get(CurrentActivitiesViewModel.class);
        getLifecycle().addObserver(viewModel);

        mutableOwnedActivities = viewModel.getMutableOwnedActivities();
        mutableOwnedActivities.observe(this, activities -> {
            ownedReAdapter.notifyDataSetChanged();
        });
        mutableParticipatingActivities = viewModel.getMutableParticipatingActivities();
        mutableParticipatingActivities.observe(this, activities -> {
            participatingReAdapter.notifyDataSetChanged();
        });

        ownedRecyclerView = (RecyclerView) findViewById(R.id.currentAct_owned);
        ownedRecyclerView.setHasFixedSize(false);
        ownedReLayoutManager = new LinearLayoutManager(this);
        ownedRecyclerView.setLayoutManager(ownedReLayoutManager);
        ownedReAdapter = new ActivityCardRecyclerAdapter(getApplicationContext(),mutableOwnedActivities,this);
        ownedRecyclerView.setAdapter(ownedReAdapter);

        participatingRecyclerView = (RecyclerView) findViewById(R.id.currentAct_par);
        participatingRecyclerView.setHasFixedSize(false);
        participatingReLayoutManager = new LinearLayoutManager(this);
        participatingRecyclerView.setLayoutManager(participatingReLayoutManager);
        participatingReAdapter = new ActivityCardRecyclerAdapter(getApplicationContext(),mutableParticipatingActivities,this);
        participatingRecyclerView.setAdapter(participatingReAdapter);
    }

    @Override
    public void onCardClicked(int pos) {
        Log.d(LOG_TAG, "Card Clicked!");
        Intent intent = new Intent(this, ActivityDescriptionView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
