package com.dat257.team1.LFG.model;

import java.util.Date;

public class Comment {
    private String commentText;
    private Date commentData;
    private String commenterRef;

    public Comment(String commentText, Date commentData, String commenterRef) {
        this.commentText = commentText;
        this.commentData = commentData;
        this.commenterRef = commenterRef;
    }

    public String getCommentText() {
        return commentText;
    }

    public Date getCommentData() {
        return commentData;
    }

    public String getCommenterRef() {
        return commenterRef;
    }
}
