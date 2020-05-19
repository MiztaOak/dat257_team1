package com.dat257.team1.LFG.events;

public class MessageEvent {

    private boolean success;

    public MessageEvent (boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
