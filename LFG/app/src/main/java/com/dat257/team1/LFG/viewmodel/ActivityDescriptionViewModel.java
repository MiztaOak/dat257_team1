package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Activity;

public class ActivityDescriptionViewModel extends ViewModel {

    private MutableLiveData<Activity> liveDataActivity;

    public ActivityDescriptionViewModel() {
    }

    public MutableLiveData<Activity> getMutableActivityList() {
        if (liveDataActivity == null) {
            liveDataActivity = new MutableLiveData<>();
        }
        return liveDataActivity;
    }


}

