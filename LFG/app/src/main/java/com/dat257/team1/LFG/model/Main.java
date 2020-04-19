package com.dat257.team1.LFG.model;

import com.dat257.team1.LFG.events.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class that handles the creation of activities as well getting the current list of activities.
 * used by:
 * @author Jakobew
 */
public class Main {


    private static Main main;
    private List<Activity> activities;
    private User dummy = new User(1, "johan", "joahn", 0);

    private Main() {
        activities = new ArrayList<>();
    }

    public static Main getInstance() {
        if(main == null) {
            main = new Main();
        }
        return main;
    }

    /**
     * Creates an activity and posts it on the Eventbus.
     * @param description The given description.
     * String title, String description, String location, String time, User owner, List<User> participants
     */
    public void createActivity(String title, String description, String location, String time) {
        List<User> participants = new ArrayList<>();
        participants.add(dummy);

        Activity activity = new Activity(title, description, location, time, dummy, participants);  //Something something dark side
        activities.add(activity);

        ActivityEvent activityEvent = new ActivityEvent(activity);
        EventBus.getDefault().post(activityEvent);
    }

    public List getActivities() {
        return activities;
    }

}
