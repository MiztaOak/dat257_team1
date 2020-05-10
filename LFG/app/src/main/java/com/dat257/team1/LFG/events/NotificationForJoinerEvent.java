package com.dat257.team1.LFG.events;

import com.dat257.team1.LFG.model.JoinNotification;
import com.dat257.team1.LFG.model.NotificationForJoiner;

import java.util.List;

public class NotificationForJoinerEvent {

    private boolean success;

    public NotificationForJoinerEvent (boolean success) {
        this.success = success;
    }


    public boolean isSuccess() {
        return success;
    }


}
