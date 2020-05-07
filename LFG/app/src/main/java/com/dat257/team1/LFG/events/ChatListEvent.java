package com.dat257.team1.LFG.events;

import android.util.Pair;

import java.util.List;

public class ChatListEvent {
    private List<Pair<String,String>> chatInfoList;

    public ChatListEvent(List<Pair<String,String>> chatInfoList) {
        this.chatInfoList = chatInfoList;
    }

    public List<Pair<String,String>> getChatInfoList() {
        return chatInfoList;
    }
}
