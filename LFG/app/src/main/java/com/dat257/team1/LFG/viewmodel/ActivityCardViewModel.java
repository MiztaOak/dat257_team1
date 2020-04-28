package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Main;

public class ActivityCardViewModel extends ViewModel {

   Activity activity;

    public ActivityCardViewModel() {
    }

    public void setMutableActivity(Activity activity) {
        this.activity = activity;
    }

    public void onClick() {
        Main.getInstance().setFocusedActivity(activity);
    }
}
