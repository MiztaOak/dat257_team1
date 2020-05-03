package com.dat257.team1.LFG.events;

import com.dat257.team1.LFG.model.JoinNotification;

import java.util.List;

public class JoinNotificationEvent {
    List<JoinNotification> notifications;

    public JoinNotificationEvent(List<JoinNotification> notifications) {
        this.notifications = notifications;
    }

    public List<JoinNotification> getNotifications() {
        return notifications;
    }
}
