package com.dat257.team1.LFG.events;

import com.dat257.team1.LFG.model.Comment;

import java.util.List;

public class BatchCommentEvent {
    private List<Comment> comments;
    public BatchCommentEvent(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
