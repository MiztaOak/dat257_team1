package com.dat257.team1.LFG.events;

import com.dat257.team1.LFG.view.chatList.ChatListItem;

import java.util.List;

public class ChatListEvent {
    private List<ChatListItem> chatInfoList;

    public ChatListEvent(List<ChatListItem> chatInfoList) {
        this.chatInfoList = chatInfoList;
    }

    public List<ChatListItem> getChatInfoList() {
        return chatInfoList;
    }
}
