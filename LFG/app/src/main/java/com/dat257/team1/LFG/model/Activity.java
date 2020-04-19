package com.dat257.team1.LFG.model;

import com.dat257.team1.LFG.firebase.FireStoreHelper;

import java.util.List;

public class Activity {

    private String id;
    private String title;
    private String description;
    private String location;
    private String time;
    private User owner;
    private List<User> participants;

    /**
     * Constructor creating an activity with information.
      * @param title
     * @param description
     * @param location
     * @param time
     * @param owner
     * @param participants
     */
    public Activity(String id, String title, String description, String location, String time, User owner, List<User> participants) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.time = time;
        this.owner = owner;
        this.participants = participants;
    }

    /**
     * Constructor creating an activity without any information.
     */
    public Activity() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() { return time; }

    public User getOwner() {
        return owner;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

}