package com.dat257.team1.LFG.events;

import com.dat257.team1.LFG.model.Activity;

import java.util.List;

public class ActivityFeedEvent {

    private List<Activity> activityList;

    public ActivityFeedEvent(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }
}
