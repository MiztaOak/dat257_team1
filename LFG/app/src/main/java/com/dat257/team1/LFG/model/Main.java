package com.dat257.team1.LFG.model;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;

import androidx.annotation.RequiresApi;
import com.dat257.team1.LFG.events.MessageEvent;
import com.dat257.team1.LFG.events.ActivityEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.viewmodel.ActivityFeedViewModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

import org.greenrobot.eventbus.EventBus;

import com.google.firebase.Timestamp;


import java.io.IOException;
import java.security.acl.Owner;
import java.time.LocalDateTime;

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
    private User dummy = new User("1", "johan", "joahn", 0);
    private FireStoreHelper fireBaseObject;
    private String activityID = "Dz0LrkQTOeefy7dqqx3E97xBHLE2";
    private ActivityFeedViewModel activityFeedViewModel;

    //just a temp var should prob be changed to something else
    private Activity focusedActivity;
    private List<Message> messages;

    private Main() {
        activities = new ArrayList<>();

        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("comment1", Calendar.getInstance().getTime(),"Me"));
        comments.add(new Comment("comment2", Calendar.getInstance().getTime(),"Me"));
        comments.add(new Comment("comment3", Calendar.getInstance().getTime(),"Me"));

        focusedActivity = new Activity("u8A4858pFvnr5IyKxOTc","bla",null,"tst","something",null,null);
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

    public void createActivity(String id, User owner, List<String> participants, String title, String description, Timestamp time, GeoPoint location) {
        //participants.add(dummy);
        Activity activity = new Activity(id, owner.getId(), participants, title, description, time, location);
        activities.add(activity);

        ActivityEvent activityEvent = new ActivityEvent(activity);
        EventBus.getDefault().post(activityEvent);
        fireBaseObject.addActivity(activityEvent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createActivity (String title, String description, String time, String address) {
        List<String> participants = new ArrayList<>();
        //This method invocation does not work, right now it just return a dummy value. See over the method!
        Timestamp timestamp = convertToTimestamp(time);
        createActivity(activityID, dummy, participants, title, description, timestamp, new GeoPoint(30, 20));
    }

    public List<Activity> getActivities() {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Timestamp convertToTimestamp(String time) {
        String[] hoursAndMinutes = time.split(":");
        LocalDateTime localDateTime = LocalDateTime.of(2020,4,25, Integer.parseInt(hoursAndMinutes[0]), Integer.parseInt(hoursAndMinutes[1]));
        //Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return new Timestamp(300,300);
    }

    //no android code in model
    /*public GeoPoint getLocationFromAddress(String strAddress) throws IOException {

        Geocoder coder = new Geocoder(this); //your not allowed to put context here bad programmer bad!
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new GeoPoint((double) (location.getLatitude() * 1E6),
                    (double) (location.getLongitude() * 1E6));

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p1;
    }
    /*
    Creates a message and posts it on the Eventbus.
     */

    public void writeMessage(String id, String content, User sender, Timestamp time){

        Message message = new Message(id, content, sender, time);
        messages.add(message);

        MessageEvent messageEvent = new MessageEvent(message);
        EventBus.getDefault().post(messageEvent);


    }

    public List getMessages(){return messages;}

}
