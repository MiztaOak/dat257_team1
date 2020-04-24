package com.dat257.team1.LFG.model;

import com.dat257.team1.LFG.events.ActivityEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.google.firebase.firestore.GeoPoint;

import org.greenrobot.eventbus.EventBus;

import com.google.firebase.Timestamp;
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
    private User dummy = new User("1", "johan", "joahn", 0);
    private FireStoreHelper fireBaseObject;

    private Main() {
        activities = new ArrayList<>();
        fireBaseObject = FireStoreHelper.getInstance();
    }

    public static Main getInstance() {
        if(main == null) {
            main = new Main();
        }
        return main;
    }

    /**
     *
     * @param id the ID of the activity
     * @param owner the owner of the activity
     * @param participants the participants of the activity
     * @param title the title of the activity
     * @param description the description of the activity
     * @param time
     * @param location
     */
    public void createActivity(String id, User owner, List<User> participants, String title, String description, Timestamp time, GeoPoint location) {
        participants = new ArrayList<>();
        participants.add(dummy);

        Activity activity = new Activity(id, owner, participants, title, description, time, location);
        activities.add(activity);

        ActivityEvent activityEvent = new ActivityEvent(activity);
        EventBus.getDefault().post(activityEvent);

        fireBaseObject.addActivity(activityEvent);
    }

    public List getActivities() {
        return activities;
    }

}
