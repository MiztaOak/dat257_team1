package com.dat257.team1.LFG.model;

import com.dat257.team1.LFG.events.ActivityEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;

import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.Calendar;
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

    //just a temp var should prob be changed to something else
    private Activity focusedActivity;

    private Main() {
        activities = new ArrayList<>();

        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("comment1", Calendar.getInstance().getTime(),"Me"));
        comments.add(new Comment("comment2", Calendar.getInstance().getTime(),"Me"));
        comments.add(new Comment("comment3", Calendar.getInstance().getTime(),"Me"));

        focusedActivity = new Activity("u8A4858pFvnr5IyKxOTc","Test title","Test desc","here","now",dummy,new ArrayList<User>());
    }

    public static Main getInstance() {
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
    public void createActivity(String id, String title, String description, String location, String time) {
        List<User> participants = new ArrayList<>();
        participants.add(dummy);

        Activity activity = new Activity(id, title, description, location, time, dummy, participants);
        activities.add(activity);

        ActivityEvent activityEvent = new ActivityEvent(activity);
        EventBus.getDefault().post(activityEvent);
    }

    public List getActivities() {
        return activities;
    }

    public Activity getFocusedActivity() {
        return focusedActivity;
    }

    public void addComment(Activity activity, Comment comment) {
        FireStoreHelper.getInstance().addCommentToActivity(activity,comment);
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
