package com.dat257.team1.LFG.view.chatList;

public class ChatListItem {
    private String title;
    private String id;
    private int amountOfParticipants;

    public ChatListItem(String title, String id, int amountOfParticipants) {
        this.title = title;
        this.id = id;
        this.amountOfParticipants = amountOfParticipants;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public int getAmountOfParticipants() {
        return amountOfParticipants;
    }
}
