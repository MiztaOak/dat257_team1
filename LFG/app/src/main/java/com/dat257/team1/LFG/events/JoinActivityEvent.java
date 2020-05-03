package com.dat257.team1.LFG.events;

public class JoinActivityEvent {
    private boolean success;
    private String message;

    public JoinActivityEvent(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public JoinActivityEvent(boolean success) {
        this.success = success;
        message = "You have successfully requested to join the activity";
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
