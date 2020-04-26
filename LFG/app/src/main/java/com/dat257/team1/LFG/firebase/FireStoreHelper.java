package com.dat257.team1.LFG.firebase;

import android.util.Log;


import com.dat257.team1.LFG.Events.MessageEvent;
import com.dat257.team1.LFG.model.Message;
import com.dat257.team1.LFG.model.Chat;
import com.dat257.team1.LFG.model.Main;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A helper class that handles the connection to the Firestore database, containing methods that
 * fetch data or upload it to the database.
 *
 * Author: Johan Ek
 *
 */
public class FireStoreHelper {
    private FirebaseFirestore db;
    private List<ActivityDataHolder> activities;
    private final String TAG = FirebaseFirestore.class.getSimpleName();

    public FireStoreHelper() {
        db = FirebaseFirestore.getInstance();
        activities = new ArrayList<>();
        loadActivities();
    }

    //just a dummy method will be removed later
    public void addActivity(String uId) {
        Calendar.getInstance().set(2020, 4, 30, 15, 30);
        DocumentReference owner = db.document("users/" + uId);
        List<DocumentReference> participants = new ArrayList<>();
        participants.add(owner);

        Map<String, Object> activity = new HashMap<>();

        activity.put("title", "Test activity");
        activity.put("desc", "this is a test activity meant to test the connection to the database");
        activity.put("time", new Timestamp(Calendar.getInstance().getTime()));
        activity.put("creationDate", new Timestamp(new Date()));
        activity.put("owner", owner);
        activity.put("location", new GeoPoint(30, 40));
        activity.put("participants", participants);

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

    /**
     * Method that creates uploads a new activity to the Firestore database
     * <p>
     * Author: Johan Ek
     *
     * @param uId      The user id for the owner of the activity
     * @param date     The date of the activity
     * @param title    The title of the activity
     * @param desc     The description of the activity
     * @param location The location of the activity
     */
    public void addActivity(String uId, Timestamp date, String title, String desc, GeoPoint location) {
        DocumentReference owner = db.document("users/" + uId);
        List<DocumentReference> participants = new ArrayList<>();
        participants.add(owner);

        //Chat chat = new Chat(Main.getInstance().getDummy2().getId(), Main.getInstance().getDummy2().getOwner(), Main.getInstance().getDummy2().getParticipants(), Main.getInstance().getDummy2().getMessages());
        Chat chat = new Chat();

        Map<String, Object> activity = new HashMap<>();

        
        activity.put("title", title);
        activity.put("desc", desc);
        activity.put("time", date);
        activity.put("creationDate", new Timestamp(new Date()));
        activity.put("owner", owner);
        activity.put("location", location);
        activity.put("participants", participants);



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

    /**
     * Method that attaches an event listener to the db that monitors the list of activities and
     * updates the list activities if the database were to change
     * <p>
     * Author: Johan Ek
     */
    private void loadActivities() {
        db.collection("activities").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                List<ActivityDataHolder> activityDataHolders = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    activityDataHolders.add(doc.toObject(ActivityDataHolder.class));
                }
                activities = activityDataHolders;
            }
        });


    }

    public List<ActivityDataHolder> getActivities() {
        return activities;
    }


    /**
     * Method that creates uploads a new message to the Firestore database
     */
    public void writeMessage(MessageEvent messageEvent) {

        DocumentReference owner = db.document("users/" + messageEvent.getMessage().getId());
        Map<String, Object> message = new HashMap<>();

        message.put("sender", owner);
        message.put("sent", new Timestamp(new Date()));
        message.put("text", messageEvent.getMessage().getContent());
        db.collection("message").add(message).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //TODO add code to handle sucess
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO add code that handles failure
            }
        });
    }

    private void loadChat() {
        //TODO create a load chat like the load activities by first creating a MessageDataHolder
    }

}
