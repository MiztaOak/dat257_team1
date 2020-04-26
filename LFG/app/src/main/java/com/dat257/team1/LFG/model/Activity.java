package com.dat257.team1.LFG.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;


import java.util.ArrayList;

import java.sql.Time;

import java.util.List;
import java.util.Locale;

public class Activity {

    private String id;
    private String title;
    private String description;
    private GeoPoint location;

    private Timestamp time;
    private String owner;
    private List<String> participants;

    /**
     * Constructor creating an activity with information.
      * @param title
     * @param description
     * @param location
     * @param time
     * @param owner
     * @param participants
     */
    public Activity(String id, String owner, List<String> participants, String title, String description, Timestamp time, GeoPoint location) {
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
    public Activity() { }

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

    public GeoPoint getLocation() {
        return location;
    }

    public Timestamp getTime() { return time; }

    public String getOwner() {
        return owner;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

}