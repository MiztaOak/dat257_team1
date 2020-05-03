package com.dat257.team1.LFG.events;

import com.dat257.team1.LFG.model.Message;

import java.util.List;

public class ChatEvent {

    private List<Message> messages;
    public ChatEvent(List<Message> messages) {
        this.messages = messages;
    }
    public List<Message> getMessages() {
        return messages;
    }
}
