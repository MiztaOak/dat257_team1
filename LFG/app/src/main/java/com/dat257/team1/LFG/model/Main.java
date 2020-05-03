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
    FirebaseAuth mAuth;
    private List<Activity> activities;
    private User dummy = new User("1", "johan", "joahn", 0);
    private FireStoreHelper fireBaseObject;
    private String activityID = "Dz0LrkQTOeefy7dqqx3E97xBHLE2";
    //just a temp var should prob be changed to something else
    private Activity focusedActivity;
    private List<Message> messages;

    private Main() {
        activities = new ArrayList<>();

        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("comment1", Calendar.getInstance().getTime(), "Me"));
        comments.add(new Comment("comment2", Calendar.getInstance().getTime(), "Me"));
        comments.add(new Comment("comment3", Calendar.getInstance().getTime(), "Me")); //TODO

        mAuth = FirebaseAuth.getInstance();
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
    public void createActivity(String title, String description, Timestamp timestamp, GeoPoint geoPoint, Boolean privateEvent, int numAttendees, Category category) {
        //activities.add(activity);
        //ActivityEvent activityEvent = new ActivityEvent(activity);
        //EventBus.getDefault().post(activityEvent);
        fireBaseObject.addActivity(mAuth.getCurrentUser().getUid(), timestamp, title, description, geoPoint, privateEvent, category, numAttendees);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public void updateActivityFeed() {
        activities = fireBaseObject.getActivities(); //TODO don't get all activities
        List<String> lll = new ArrayList<>();
        List<User> ll1l = new ArrayList<>();
        activities.add(new Activity("w","d",lll,"ss","ss",new Timestamp(1,1), new GeoPoint(1,1),new Chat(),false,3,Category.Hiking, ll1l));
        ActivityFeedEvent activityFeedEvent = new ActivityFeedEvent(getActivities());
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Timestamp convertToTimestamp(String time) {
        String[] hoursAndMinutes = time.split(":");
        LocalDateTime localDateTime = LocalDateTime.of(2020, 4, 25, Integer.parseInt(hoursAndMinutes[0]), Integer.parseInt(hoursAndMinutes[1]));
        //Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return new Timestamp(300, 300);
    }

    public void writeMessage(String id, String content, User sender, Timestamp time) {

        Message message = new Message(id, content, sender, time);
        messages.add(message);

        MessageEvent messageEvent = new MessageEvent(message);
        EventBus.getDefault().post(messageEvent);
    }

    public List getMessages() {
        return messages;
    }
}
