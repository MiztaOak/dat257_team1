package com.dat257.team1.LFG.Events;

import com.dat257.team1.LFG.model.Activity;

public class ActivityEvent {

    Activity activity;
    public ActivityEvent(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }
}

