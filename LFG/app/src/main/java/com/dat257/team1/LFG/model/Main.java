package com.dat257.team1.LFG.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.dat257.team1.LFG.events.ActivityFeedEvent;
import com.dat257.team1.LFG.events.MessageEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.GeoPoint;

import org.greenrobot.eventbus.EventBus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
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
    private User dummy = new User("1", "johan", "joahn", 0);
    private FireStoreHelper fireBaseObject;
    private String activityID = "NhoBgAgfQlWGrafLlGn9";
    //just a temp var should prob be changed to something else
    private Activity focusedActivity;
    private List<Message> messages;

    private Main() {
        activities = new ArrayList<>();

        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("comment1", Calendar.getInstance().getTime(), "Me"));
        comments.add(new Comment("comment2", Calendar.getInstance().getTime(), "Me"));
        comments.add(new Comment("comment3", Calendar.getInstance().getTime(), "Me")); //TODO
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

    public void writeMessage(Chat chat, Message message) {
        FireStoreHelper.getInstance().writeMessageInChat(chat, message);
    }

    public List getMessages() {
        return messages;
    }
}
