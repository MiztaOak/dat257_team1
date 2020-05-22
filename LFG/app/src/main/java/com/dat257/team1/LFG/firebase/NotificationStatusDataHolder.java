package com.dat257.team1.LFG.firebase;

import com.dat257.team1.LFG.model.NotificationForJoiner;
import com.google.firebase.firestore.DocumentReference;

public class NotificationStatusDataHolder {
    public String status;
    public DocumentReference activity;
    public DocumentReference joiner;

    public NotificationStatusDataHolder() {
    }

    public NotificationStatusDataHolder(DocumentReference activity, DocumentReference joiner, String status) {
        this.status = status;
        this.activity = activity;
        this.joiner = joiner;
    }

    public String getStatus() { return status; }

    public DocumentReference getActivity() {
        return activity;
    }

    public DocumentReference getJoiner() {
        return joiner;
    }



    public NotificationForJoiner toNotificationForJoiner(){
        return new NotificationForJoiner(activity.getId(), joiner.getId(), status);
    }

    boolean hasValidData() {
        return activity != null && joiner != null && status != null;
    }
}
