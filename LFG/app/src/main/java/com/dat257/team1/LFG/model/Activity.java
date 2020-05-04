package com.dat257.team1.LFG.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;


import java.util.ArrayList;

import java.sql.Time;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Class that represents an activity/event that users can create and also join.
 * @author Jakobew
 */
public class Activity {

    private String id;
    private String title;
    private String description;
    private GeoPoint location;
    private Timestamp timestamp;
    private String owner;
    private List<String> participants;
    private String chatRef;
    private Boolean privateAct;
    private int numAttendees;
    private Category category;
    private List<User> joinRequestList;


    /**
     * Constructor creating an activity with information.
      * @param title
     * @param description
     * @param location
     * @param timestamp
     * @param owner
     * @param participants
     */

    public Activity(String id, String owner, List<String> participants, String title, String description, Timestamp timestamp, GeoPoint location, String chatRef, Boolean privateAct, int numAttendees, Category category, List<User> joinRequestList) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.timestamp = timestamp;
        this.owner = owner;
        this.participants = participants;
        this.chatRef = chatRef;
        this.privateAct = privateAct;
        this.numAttendees = numAttendees;
        this.category = category;
        this.joinRequestList = joinRequestList;
    }

    public String getChatRef() {
        return chatRef;
    }

    public void setChatRef(String chatRef) {
        this.chatRef = chatRef;
    }

    public Boolean getPrivateAct() {
        return privateAct;
    }

    public void setPrivateAct(Boolean privateAct) {
        this.privateAct = privateAct;
    }

    public int getNumAttendees() {
        return numAttendees;
    }

    public void setNumAttendees(int numAttendees) {
        this.numAttendees = numAttendees;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<User> getJoinRequestList() {
        return joinRequestList;
    }

    public void setJoinRequestList(ArrayList joinRequestList) {
        this.joinRequestList = joinRequestList;
    }

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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

}