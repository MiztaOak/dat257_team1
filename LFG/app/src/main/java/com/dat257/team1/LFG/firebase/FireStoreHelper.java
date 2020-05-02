package com.dat257.team1.LFG.firebase;

import android.util.Log;



import com.dat257.team1.LFG.events.BatchCommentEvent;
import com.dat257.team1.LFG.events.CommentEvent;
import com.dat257.team1.LFG.events.JoinNotificationEvent;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Comment;
import com.dat257.team1.LFG.model.JoinNotification;
import com.dat257.team1.LFG.model.Main;

import com.dat257.team1.LFG.events.MessageEvent;
import com.dat257.team1.LFG.model.Chat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.dat257.team1.LFG.events.ActivityEvent;
import com.google.firebase.firestore.Transaction;

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
                    Log.w(TAG,doc.getId());
                    ActivityDataHolder data = doc.toObject(ActivityDataHolder.class);
                    if(data.hasValidData())
                        activities.add(data.toActivity(doc.getId()));
                }
                Main.getInstance().setActivities(activities);
            }
        });
    }

    /**
     * Attaches a listener that loads all comments for a given activity
     *
     * Author: Johan Ek
     * @param id the id of the activity
     * @return the listener
     */
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
     *
     * Author: Johan Ek
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

    /**
     * Method that attaches a listener to all activities that are owned by the user with id uID
     *
     * Author: Johan Ek
     * @param uID id of the user that is the owner
     * @return a listener that is attached to all activities that uID owns
     */
    public ListenerRegistration loadNotification(String uID){
        return db.collection("activities").whereEqualTo("owner",db.document("/users/"+uID)).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                List<JoinNotification> notifications = new ArrayList<>();
                for(QueryDocumentSnapshot doc : value){
                    String activityID = doc.getId();
                    String title = doc.getString("title");
                    List<DocumentReference> waitingList = (List<DocumentReference>) doc.get("joinRequestList"); //evil row
                    for(DocumentReference ref: waitingList){
                        notifications.add(new JoinNotification(activityID,title,ref.getId(),"help")); //what to do about name, redundant data?
                    }
                }
                EventBus.getDefault().post(new JoinNotificationEvent(notifications));
            }
        });
    }

    /**
     * Method that either accepts a join request or declines it. This is handled in a atomic transaction
     * to avoid the possibility that the user is removed from the waiting list but not added to the
     * participants
     *
     * Author: Johan Ek
     * @param uID the id of the user that is requesting to join
     * @param activityID id of the activity that the user is trying to join
     * @param accept if true then the request was accepted
     */
    public void handleJoinRequest(String uID, String activityID, boolean accept){
        final DocumentReference docRef = db.collection("activities").document(activityID);

        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException{
                //DocumentSnapshot snapshot = transaction.get(docRef);
                transaction.update(docRef,"joinRequestList", FieldValue.arrayRemove(db.document("/users/"+uID)));
                if(accept)
                    transaction.update(docRef,"participants", FieldValue.arrayUnion(db.document("/users/"+uID)));
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Transaction failure.", e);
            }
        });
    }

    /**
     * Method that creates a join request for a user on a certain activity.
     *
     * Author: Johan Ek
     * @param uID the user id of the user that wants to join the activity
     * @param activityID the id of the activity
     */
    public void createJoinRequest(String uID, String activityID){
        final DocumentReference docRef = db.collection("activities").document(activityID);

        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException{
                DocumentSnapshot snapshot = transaction.get(docRef);
                if(snapshot.exists()){
                    List<DocumentReference> participants = (List<DocumentReference>) snapshot.get("participants"); //cursed row
                    DocumentReference userRef = db.document("/users/"+uID);
                    assert participants != null;
                    if(participants.contains(userRef)){
                       //send some sort of notify to the user that they have already joined
                    }else{
                        transaction.update(docRef,"joinRequestList", FieldValue.arrayUnion(userRef));
                    }
                }
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Transaction failure.", e);
            }
        });
    }
}
