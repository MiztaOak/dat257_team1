package com.dat257.team1.LFG.view;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Timestamp;

public class MessageCard extends AppCompatActivity {
    private String contactImageResource;
    private String messageContent;
    private Timestamp timeStamp;

    public MessageCard(String contactImage, String content, Timestamp time) {
        contactImageResource = contactImage;
        messageContent = content;
        timeStamp = time;
    }

    public String getContactImageResource() {
        return contactImageResource;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }
}
