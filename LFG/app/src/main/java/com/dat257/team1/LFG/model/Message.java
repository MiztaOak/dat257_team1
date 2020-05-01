package com.dat257.team1.LFG.model;



import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.List;

public class Message {
    private String content;
    private String sender;
    private Date time;


    public Message (String content, String sender, Date time){
        this.content=content;
        this.sender=sender;
        this.time=time;

    }

    public Message(){}

    public String getContent(){ return content; }

    public String getSender(){return sender;}

    public Date getTime(){return time;}

    public void setContent(String content){this.content=content;}

    public void setSender(String sender){this.sender=sender;}

    //TODO is it necessary for a setter for timestamp?

}
