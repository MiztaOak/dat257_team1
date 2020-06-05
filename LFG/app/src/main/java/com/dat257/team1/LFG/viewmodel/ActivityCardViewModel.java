package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.repository.Repository;

public class ActivityCardViewModel extends ViewModel {

   Activity activity;

    public void setMutableActivity(Activity activity) {
        this.activity = activity;
    }

    public void onClick() {
        Repository.getInstance().setFocusedActivity(activity);
    }
}
