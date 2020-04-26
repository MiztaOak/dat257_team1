package com.dat257.team1.LFG.firebase;

import com.dat257.team1.LFG.model.Comment;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class CommentDataHolder {
    public String commentText;
    public Timestamp postDate;
    public DocumentReference poster;

    public CommentDataHolder() {
    }

    public CommentDataHolder(String commentText, Timestamp postDate, DocumentReference poster) {
        this.commentText = commentText;
        this.postDate = postDate;
        this.poster = poster;
    }

    public String getCommentText() {
        return commentText;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public DocumentReference getPoster() {
        return poster;
    }

    public Comment toComment(){
        return new Comment(commentText,postDate.toDate(),poster.getId());
    }
}
