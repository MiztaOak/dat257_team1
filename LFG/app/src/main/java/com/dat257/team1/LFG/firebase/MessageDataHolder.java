package com.dat257.team1.LFG.firebase;

import com.dat257.team1.LFG.model.Message;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class MessageDataHolder {
    public String messageText;
    public Timestamp sent;
    public DocumentReference sender;

    public MessageDataHolder() {
    }

    public MessageDataHolder(String messageText, Timestamp sent, DocumentReference sender) {
        this.messageText = messageText;
        this.sent = sent;
        this.sender = sender;
    }

    public String getCommentText() { return messageText; }

    public Timestamp getPostDate() {
        return sent;
    }

    public DocumentReference getPoster() {
        return sender;
    }

    public Message toMessage(){
        return new Message(messageText, sender.getId(), sent.toDate(), null); //TODO image
    }
}
