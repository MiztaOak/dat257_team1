package com.dat257.team1.LFG.Events;

import com.dat257.team1.LFG.model.Message;

public class MessageEvent {



    private Message message;

    public MessageEvent(Message message) {
        this.message = message;
    }

    public Message getMessage(){
        return message;
    }
}
