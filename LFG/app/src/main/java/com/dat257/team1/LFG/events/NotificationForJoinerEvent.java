package com.dat257.team1.LFG.events;

import com.dat257.team1.LFG.model.JoinNotification;
import com.dat257.team1.LFG.model.NotificationForJoiner;

import java.util.List;

public class NotificationForJoinerEvent {

    List<NotificationForJoiner> notifications;

    public NotificationForJoinerEvent(List<NotificationForJoiner> notifications) {
        this.notifications = notifications;
    }

    public List<NotificationForJoiner> getNotifications() {
        return notifications;
    }
}
