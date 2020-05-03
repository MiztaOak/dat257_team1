package com.dat257.team1.LFG.model;

import java.util.List;

public class Chat {


    public String id;
    private User owner;
    private List<User> participants;
    private List<Message> messages;

    public Chat (String id, User owner, List<User>participants, List<Message> messages){

        this.id=id;
        this.owner=owner;
        this.participants=participants;
        this.messages=messages;
    }


    public Chat(){}

    public String getId(){return id;}

    public User getOwner(){return owner;}

    public List<User> getParticipants(){ return participants;}

    public List<Message> getMessages(){return messages;}

    public void setId (String id){this.id=id;}

    public void setOwner (User owner){this.owner=owner;}

    public void setParticipants(List<User> participants){this.participants=participants;}

    public void setMessages(List<Message> messages){this.messages=messages;}


}
