package com.dat257.team1.LFG.model;


import java.sql.Timestamp;
import java.util.List;

public class Message {
    private String id;
    private String content;
    private User sender;
    private Timestamp time;


    public Message (String id, String content, User sender, Timestamp time){

        this.id=id;
        this.content=content;
        this.sender=sender;
        this.time=time;

    }

    public Message(){}

    public String getId(){return id;}

    public String getContent(){ return content; }

    public User getSender(){return sender;}

    public Timestamp getTime(){return time;}

    public void setId(String id){this.id=id;}

    public void setContent(String content){this.content=content;}

    public void setSender(User sender){this.sender=sender;}

    //TODO is it necessary for a setter for timestamp?

}
