package com.dat257.team1.LFG.repository;

import com.dat257.team1.LFG.events.ActivityFeedEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Category;
import com.dat257.team1.LFG.model.Comment;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ListenerRegistration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Main class that handles the creation of activities as well getting the current list of activities.
 * used by:
 *
 * @author Jakobew
 */
public class Repository {

    private static Repository repository;
    private List<Activity> activities;
    private FireStoreHelper fireBaseObject;
    private String activityID = "NhoBgAgfQlWGrafLlGn9";
    //just a temp var should prob be changed to something else
    private Activity focusedActivity;

    private Repository() {
        activities = new ArrayList<>();
        fireBaseObject = FireStoreHelper.getInstance();
    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    /**
     * @param title       the title of the activity
     * @param description the description of the activity
     * @param timestamp
     * @param geoPoint
     */
    public void createActivity(String title, String description, Timestamp timestamp, GeoPoint geoPoint, Boolean privateEvent, int numAttendees, Category category, String uID) {
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
