package com.dat257.team1.LFG.Events;

public class RegisterEvent {
    private boolean success;

    public RegisterEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
