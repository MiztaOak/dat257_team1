package com.dat257.team1.LFG.firebase;

import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Comment;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * A data holder class for the activities with the same stucture as the data in the database has.
 * The data can then be transformed into a normal using the method toActivity()
 *
 * Author: Johan Ek
 */
public class ActivityDataHolder {
    public DocumentReference owner;
    public List<DocumentReference> participants;
    public String title;
    public String desc;
    public Timestamp time;
    public Timestamp creationDate;
    public GeoPoint location;

    public ActivityDataHolder() {
    }

    public ActivityDataHolder(DocumentReference owner, List<DocumentReference> participants, String title, String desc, Timestamp time, Timestamp creationDate, GeoPoint location) {
        this.owner = owner;
        this.participants = participants;
        this.title = title;
        this.desc = desc;
        this.time = time;
        this.creationDate = creationDate;
        this.location = location;
    }

    public DocumentReference getOwner() {
        return owner;
    }

    public void setOwner(DocumentReference owner) {
        this.owner = owner;
    }

    public List<DocumentReference> getParticipants() {
        return participants;
    }

    public void setParticipants(List<DocumentReference> participants) {
        this.participants = participants;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "ActivityDataHolder{" +
                "owner=" + owner +
                ", participants=" + participants +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", time=" + time +
                ", creationDate=" + creationDate +
                ", location=" + location +
                '}';
    }

    public Activity toActivity(String id){
        List<String> participantStrings = new ArrayList<>();
        for(DocumentReference ref: participants){
            participantStrings.add(ref.toString());
        }
        List<Comment> commentList = new ArrayList<>();

        return new Activity(id,title,desc,location.toString(),time.toString(),null/*owner.toString()*/,null/*participantStrings*/);
    }

    public boolean hasValidData(){
        return owner != null && title != null && desc != null && time != null && owner != null && participants != null && creationDate != null;
    }
}
