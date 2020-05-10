package com.dat257.team1.LFG.model;

public class NotificationForJoiner {

    private String activity;
    private String userName;
    private String status;
    private String nId;

    public NotificationForJoiner (String activity, String userName, String status, String nId){

        this.activity = activity;
        this.userName = userName;
        this.status = status;
        this.nId = nId;


    }

    public String getActivityID() {
        return activity;
    }

    public String getUserName() {
        return userName;
    }

    public String getStatus(){ return status;}

    public String getnId(){return nId;}

}
