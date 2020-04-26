package com.dat257.team1.LFG.model;

import com.dat257.team1.LFG.Events.MessageEvent;
import com.dat257.team1.LFG.events.ActivityEvent;
import org.greenrobot.eventbus.EventBus;

import java.sql.Timestamp;
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
    private List<Message> messages;

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
     * @param title The given name to the activity
     * @param description The given description.
     * @param location A location that is used to locate the activity.
     * @param time The time the activity is taken place.
     */
    public void createActivity(String id, String title, String description, String location, String time) {
        List<User> participants = new ArrayList<>();
        participants.add(dummy);
        Chat chat = new Chat();

        Activity activity = new Activity(id, title, description, location, time, dummy, participants, chat);
        activities.add(activity);

        ActivityEvent activityEvent = new ActivityEvent(activity);
        EventBus.getDefault().post(activityEvent);
    }

    public List getActivities() {
        return activities;
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
