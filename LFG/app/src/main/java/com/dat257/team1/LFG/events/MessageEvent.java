package com.dat257.team1.LFG.events;

import com.dat257.team1.LFG.model.Message;

public class MessageEvent {



    private boolean success;

    public MessageEvent (boolean success) {
        this.success = success;
    }


    public boolean isSuccess() {
        return success;
    }
}
