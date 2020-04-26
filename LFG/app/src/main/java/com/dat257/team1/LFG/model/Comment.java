package com.dat257.team1.LFG.model;

import java.util.Date;

public class Comment {
    private String commentText;
    private Date commentDate;
    private String commenterRef;

    public Comment(String commentText, Date commentDate, String commenterRef) {
        this.commentText = commentText;
        this.commentDate = commentDate;
        this.commenterRef = commenterRef;
    }

    public String getCommentText() {
        return commentText;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public String getCommenterRef() {
        return commenterRef;
    }
}
