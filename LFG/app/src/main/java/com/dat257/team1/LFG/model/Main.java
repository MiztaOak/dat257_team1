package com.dat257.team1.LFG.model;

import com.dat257.team1.LFG.Events.ActivityEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class that handles the creation of activities as well getting the current list of activities.
 * used by:
 * @author Jakobew
 */
public class Main {


    private List activities;
    private Main main;


    private Main() {
    }

    public Main getMain() {
        if(main == null) {
            main = new Main();
        }
        return main;
    }

    /**
     * Creates an activity and posts it on the Eventbus.
     * @param name The given name to the activity
     * @param description The given description.
     * @param id an id that is used to track the activity.
     */
    public void createActivity(String name, String description, int id) {
        //Activity activity = new Activity(name, description, id);  //Something something dark side
        //activities.add(activity);
        //ActivityEvent activityEvent = new ActivityEvent();
        //EventBus.getDefault().post(activityEvent);
    }

    public List getActivities() {
        return activities;
    }

    /**
     * Method for creating an activity and storing it locally and in the database.
     * @param name The given name to the activity.
     * @param description The given description.
     * @param id An id that is used to track the activity.
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
        ActivityEvent activityEvent = new ActivityEvent(activity);
        EventBus.getDefault().post(activityEvent);
    }
}
