package com.dat257.team1.LFG.events;

import com.dat257.team1.LFG.model.User;

public class UserEvent {
    private User user;

    public UserEvent(User user) {
        this.user = user;
    }
    public User getUser(){
        return user;
    }
}
