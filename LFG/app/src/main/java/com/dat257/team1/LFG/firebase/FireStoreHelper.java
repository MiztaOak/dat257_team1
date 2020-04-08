package com.dat257.team1.LFG.firebase;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class FireStoreHelper {
    public FirebaseFirestore db;

    public FireStoreHelper(){
        db = FirebaseFirestore.getInstance();
    }

    public FireStoreHelper(FirebaseFirestore db) {
        this.db = db;
    }

    public void addActivity(String uId){
        Calendar.getInstance().set(2020,4,30,15,30);
        DocumentReference owner = db.document("users/" + uId);
        List<DocumentReference> participants = new ArrayList<>();
        participants.add(owner);

        Map<String, Object> activity = new HashMap<>();

        activity.put("title","Test activity");
        activity.put("desc","this is a test activity meant to test the connection to the database");
        activity.put("time",new Timestamp(Calendar.getInstance().getTime()));
        activity.put("creationDate",new Timestamp(new Date()));
        activity.put("owner",owner);
        activity.put("location",new GeoPoint(30,40));
        activity.put("participants",participants);

        db.collection("activities").add(activity)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // add some code that handles the success
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               // add some code that handles this exception
            }
        });
    }

    public void addActivity(String uId, Timestamp date, String title, String desc, GeoPoint location){
        DocumentReference owner = db.document("users/" + uId);
        List<DocumentReference> participants = new ArrayList<>();
        participants.add(owner);

        Map<String, Object> activity = new HashMap<>();

        activity.put("title",title);
        activity.put("desc",desc);
        activity.put("time",date);
        activity.put("creationDate",new Timestamp(new Date()));
        activity.put("owner",owner);
        activity.put("location",location);
        activity.put("participants",participants);

        db.collection("activities").add(activity)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // add some code that handles the success
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // add some code that handles this exception
            }
        });
    }


    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }
}
