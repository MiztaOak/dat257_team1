package com.dat257.team1.LFG.firebase;

import com.dat257.team1.LFG.model.Comment;
import com.dat257.team1.LFG.model.Message;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.sql.Date;
import java.sql.Time;

public class MessageDataHolder {
    public String messageText;
    public Timestamp sendTime;
    public DocumentReference sender;

    public MessageDataHolder() {
    }

    public MessageDataHolder(String messageText, Timestamp sendTime, DocumentReference sender) {
        this.messageText = messageText;
        this.sendTime = sendTime;
        this.sender = sender;
    }

    public String getCommentText() { return messageText; }

    public Timestamp getPostDate() {
        return sendTime;
    }

    public DocumentReference getPoster() {
        return sender;
    }

    public Message toMessage(){
        return new Message(messageText, sender.getId(), sendTime);
    }
}
