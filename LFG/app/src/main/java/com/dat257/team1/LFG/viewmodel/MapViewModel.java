package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Activity;

import java.util.List;

public class MapViewModel extends ViewModel {

    private MutableLiveData<List<Activity>> activities = new MutableLiveData<>();

    public MutableLiveData<List<Activity>> getActivities() {
        if (activities == null)
            activities = new MutableLiveData<>();
        return activities;
    }
}
