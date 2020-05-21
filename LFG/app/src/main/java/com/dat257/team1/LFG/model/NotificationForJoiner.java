package com.dat257.team1.LFG.model;

public class NotificationForJoiner {

    private String activity;
    private String userName;
    private String status;




    public NotificationForJoiner (String activity, String userName, String status){



        this.activity = activity;


        this.userName = userName;
        this.status = status;

    }



    public String getActivity(){return activity;}



    public String getUserName() {
        return userName;
    }

    public String getStatus(){ return status;}



}
