package com.dat257.team1.LFG.model;

import java.util.Date;

public class Message {

    private String content;
    private String senderId;
    private Date timestamp;
    private String contactImageResource;

    public Message(String content, String senderId, Date timestamp, String contactImageResource) {
        this.content = content;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.contactImageResource = contactImageResource;
    }

    public String getContactImageResource() {
        return contactImageResource;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getContent() {
        return content;
    }

    public Date getTime() {
        return timestamp;
    }
}
