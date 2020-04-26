package com.dat257.team1.LFG.events;

import com.dat257.team1.LFG.model.Comment;

public class CommentEvent {
    private boolean success;

    public CommentEvent (boolean success) {
        this.success = success;
    }


    public boolean isSuccess() {
        return success;
    }
}
