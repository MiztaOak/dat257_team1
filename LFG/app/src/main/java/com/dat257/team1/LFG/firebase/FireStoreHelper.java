package com.dat257.team1.LFG.firebase;

import android.util.Log;


import com.dat257.team1.LFG.events.ChatEvent;
import com.dat257.team1.LFG.events.BatchCommentEvent;
import com.dat257.team1.LFG.events.CommentEvent;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Comment;
import com.dat257.team1.LFG.model.Main;

import com.dat257.team1.LFG.events.MessageEvent;
import com.dat257.team1.LFG.model.Chat;

import com.dat257.team1.LFG.model.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.dat257.team1.LFG.events.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

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
    private static FireStoreHelper instance;
    private FirebaseFirestore db;
    private List<Activity> activities;
    private final String TAG = FirebaseFirestore.class.getSimpleName();

    private FireStoreHelper(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        activities = new ArrayList<>();
        loadActivities();
    }

    public static FireStoreHelper getInstance() {
        if(instance == null)
            instance = new FireStoreHelper();

        return instance;
    }

    //just a dummy method will be removed later
    public void addActivity(ActivityEvent activityEvent) {
        Calendar.getInstance().set(2020,4,30,15,30);
        Activity currentActivity = activityEvent.getActivity();
        DocumentReference owner = db.document("users/" + currentActivity.getId());

        List<DocumentReference> participants = new ArrayList<>();
        participants.add(owner);

        Map<String, Object> activity = new HashMap<>();

        activity.put("id", currentActivity.getId());
        activity.put("title", currentActivity.getTitle());
        activity.put("desc", currentActivity.getDescription());
        activity.put("time", currentActivity.getTime());
        activity.put("location", currentActivity.getLocation());
        activity.put("owner", currentActivity.getOwner());
        activity.put("participants", currentActivity.getParticipants());


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
        activity.put("chat", chat);


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

                activities = new ArrayList<>();
                for(QueryDocumentSnapshot doc : value){
                    ActivityDataHolder data = doc.toObject(ActivityDataHolder.class);
                    if(data.hasValidData())
                        activities.add(data.toActivity(doc.getId()));
                }
                Main.getInstance().setActivities(activities);
            }
        });
    }

    public ListenerRegistration loadComments(String id) {
        return db.collection("activities").document(id).collection("comments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                List<Comment> comments = new ArrayList<>();
                for(QueryDocumentSnapshot doc: value){
                    CommentDataHolder data = doc.toObject(CommentDataHolder.class);
                    comments.add(data.toComment());
                }
                EventBus.getDefault().post(new BatchCommentEvent(comments));
            }
        });

    }

    /**
     * Method that adds a new comment to a given activity in the db
     * @param activity the object representing the given activity
     * @param comment the object representing the comment that should be posted
     */
    public void addCommentToActivity(Activity activity, Comment comment) {
        Map<String,Object> data = new HashMap<>();
        data.put("commentText",comment.getCommentText());
        data.put("poster",db.document("/users/"+comment.getCommenterRef()));
        data.put("postDate",new Timestamp(comment.getCommentDate()));
        db.collection("activities").document(activity.getId()).
                collection("comments").add(data).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        EventBus.getDefault().post(new CommentEvent(true));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                EventBus.getDefault().post(false);
            }
        });
    }

    public List<Activity> getActivities() {
        return activities;
    }


    /**
     * Method that creates uploads a new message to the Firestore database
     */
    public void writeMessageInChat(Chat chat, Message message) {

        Map<String, Object> data = new HashMap<>();

        data.put("messageText", message.getContent());
        data.put("sender", db.document("/users/"+message.getSender()));
        data.put("sent", message.getTime());
        db.collection("chats").document(chat.id).collection("messages").add(data).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                EventBus.getDefault().post(new MessageEvent(true));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                EventBus.getDefault().post(false);
            }
        });
    }

    public ListenerRegistration loadChat(String id) {
        return db.collection("chats").document(id).collection("messages").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                List<Message> messages = new ArrayList<>();
                for(QueryDocumentSnapshot doc: value){
                    MessageDataHolder data = doc.toObject(MessageDataHolder.class);
                    messages.add(data.toMessage());
                }
                EventBus.getDefault().post(new ChatEvent(messages));
            }
        });

    }

}
