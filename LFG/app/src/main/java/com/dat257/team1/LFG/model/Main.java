package com.dat257.team1.LFG.model;

import com.dat257.team1.LFG.Events.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Main class that handles the creation of activities as well getting the current list of activities.
 * used by:
 * @author Jakobew
 */
public class Main {


    private List activities;
    private static Main main;

    private Main() {
    }

    public static Main getMain() {
        if(main == null) {
            main = new Main();
        }
        return main;
    }


    /**
     * Creates an activity and posts it on the Eventbus.
     * @param title The given name to the activity
     * @param description The given description.
     * @param location A location that is used to locate the activity.
     * @param time The time the activity is taken place.
     */
    public void createActivity(String title, String description, String location, String time) {
        Activity activity = new Activity(title, description, location, time);  //Something something dark side
        activities.add(activity);
        ActivityEvent activityEvent = new ActivityEvent();
        EventBus.getDefault().post(activityEvent);
    }

    public List getActivities() {
        return activities;
    }

}
