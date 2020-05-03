package com.dat257.team1.LFG.model;

import com.google.firebase.firestore.DocumentReference;

import org.w3c.dom.Document;

import java.security.acl.Owner;
import java.util.List;

public class Chat {


    private static String id;
    private DocumentReference owner;
    private List<DocumentReference> participants;
    private List<DocumentReference> messages;

    private User owner2;
    private List<User> participants2;
    private List<Message> messages2;

    public Chat (String id, DocumentReference owner, List<DocumentReference>participants, List<DocumentReference> messages){

        this.id=id;
        this.owner=owner;
        this.participants=participants;
        this.messages=messages;
    }

    public Chat (String id, User owner2, List<User> participants2, List<Message> messages2) {

        this.id = id;
        this.owner2=owner2;
        this.participants2=participants2;
        this.messages2=messages2;

    }
    public Chat(){}

    public static String getId(){return id;}

    public DocumentReference getOwner(){return owner;}

    public List<DocumentReference> getParticipants(){ return participants;}

    public List<DocumentReference> getMessages(){return messages;}

    public void setId (String id){this.id=id;}

    public void setOwner (DocumentReference owner){this.owner=owner;}

    public void setParticipants(List<DocumentReference> participants){this.participants=participants;}

    public void setMessages(List<DocumentReference> messages){this.messages=messages;}


}
