package com.dat257.team1.LFG.model;

import com.dat257.team1.LFG.events.ActivityFeedEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ListenerRegistration;

import org.greenrobot.eventbus.EventBus;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Main class that handles the creation of activities as well getting the current list of activities.
 * used by:
 *
 * @author Jakobew
 */
public class Main {

    private static Main main;
    private List<Activity> activities;
    private FireStoreHelper fireBaseObject;
    private String activityID = "NhoBgAgfQlWGrafLlGn9";
    //just a temp var should prob be changed to something else
    private Activity focusedActivity;

    private Main() {
        activities = new ArrayList<>();
        fireBaseObject = FireStoreHelper.getInstance();
    }

    public static Main getInstance() {
        if (main == null) {
            main = new Main();
        }
        return main;
    }

    /**
     * @param title       the title of the activity
     * @param description the description of the activity
     * @param timestamp
     * @param geoPoint
     */
    public void createActivity(String title, String description, Timestamp timestamp, GeoPoint geoPoint, Boolean privateEvent, int numAttendees, Category category, String uID) {
        //activities.add(activity);
        //ActivityEvent activityEvent = new ActivityEvent(activity);
        //EventBus.getDefault().post(activityEvent);
        fireBaseObject.addActivity(uID, timestamp, title, description, geoPoint, privateEvent, category, numAttendees);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public void updateActivityFeed() {
        activities = fireBaseObject.getActivities(); //TODO don't get all activities
        ActivityFeedEvent activityFeedEvent = new ActivityFeedEvent(activities);
        EventBus.getDefault().post(activityFeedEvent);
    }

    public Activity getFocusedActivity() {
        return focusedActivity;
    }

    public void setFocusedActivity(Activity activity) {
        this.focusedActivity = activity;
    }

    public void addComment(Activity activity, Comment comment) {
        FireStoreHelper.getInstance().addCommentToActivity(activity, comment);
    }

    public void writeMessage(String chatId, String msg, Date date) {
        FireStoreHelper.getInstance().writeMessageInChat(chatId, msg, date);
    }

    public ListenerRegistration loadChat(String chatId) {
       return FireStoreHelper.getInstance().loadChat(chatId);
    }
}
