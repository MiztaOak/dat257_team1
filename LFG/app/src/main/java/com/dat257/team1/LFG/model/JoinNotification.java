package com.dat257.team1.LFG.model;

public class JoinNotification {
    private String activityID;
    private String activityTitle;
    private String uID;
    private String userName;

    public JoinNotification(String activityID, String activityTitle, String uID, String userName) {
        this.activityID = activityID;
        this.activityTitle = activityTitle;
        this.uID = uID;
        this.userName = userName;
    }

    public String getActivityID() {
        return activityID;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public String getuID() {
        return uID;
    }

    public String getUserName() {
        return userName;
    }
}
