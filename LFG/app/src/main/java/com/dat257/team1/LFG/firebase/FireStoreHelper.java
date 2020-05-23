package com.dat257.team1.LFG.firebase;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dat257.team1.LFG.events.ActivityFeedEvent;
import com.dat257.team1.LFG.events.BatchCommentEvent;
import com.dat257.team1.LFG.events.ChatEvent;
import com.dat257.team1.LFG.events.ChatListEvent;
import com.dat257.team1.LFG.events.CommentEvent;
import com.dat257.team1.LFG.events.CurrentActivitiesEvent;
import com.dat257.team1.LFG.events.JoinActivityEvent;
import com.dat257.team1.LFG.events.JoinNotificationEvent;
import com.dat257.team1.LFG.events.MessageEvent;
import com.dat257.team1.LFG.events.NotificationForJoinerEvent;
import com.dat257.team1.LFG.events.UserEvent;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Category;
import com.dat257.team1.LFG.model.Chat;
import com.dat257.team1.LFG.model.Comment;
import com.dat257.team1.LFG.model.JoinNotification;
import com.dat257.team1.LFG.model.Main;
import com.dat257.team1.LFG.model.Message;
import com.dat257.team1.LFG.model.NotificationForJoiner;
import com.dat257.team1.LFG.model.User;
import com.dat257.team1.LFG.view.chatList.ChatListItem;
import com.dat257.team1.LFG.viewmodel.NotificationStatusViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * A helper class that handles the connection to the Firestore database, containing methods that
 * fetch data or upload it to the database.
 * <p>
 * Author: Johan Ek
 */
public class FireStoreHelper {
    private static FireStoreHelper instance;
    private final String TAG = FirebaseFirestore.class.getSimpleName();
    private FirebaseFirestore db;
    private List<Activity> activities;
    private Map<String, String> idToNameDictionary;
    private String activityTitle;

    private FireStoreHelper() {
        db = FirebaseFirestore.getInstance();
        activities = new ArrayList<>();
        idToNameDictionary = new HashMap<>();
        loadUserNames();
        loadActivities();

    }


    public static FireStoreHelper getInstance() {
        if (instance == null)
            instance = new FireStoreHelper();

        return instance;
    }

    /**
     * Method that creates a join status in firestore
     *
     * @param user
     * @param activity
     * @param status
     */

    public void addJoinStatus(String user, Activity activity, String status) {
        WriteBatch batch = db.batch();

        DocumentReference activityRef = db.collection("activities").document(activity.getId());
        DocumentReference joiner = db.document("/users/" + user);
        DocumentReference joinStatusRef = db.collection("joinStatus").document();

        Map<String, Object> joinStatus = new HashMap<>();

        joinStatus.put("joiner", joiner);
        joinStatus.put("activity", activityRef);
        joinStatus.put("status", status);

        batch.set(joinStatusRef, joinStatus);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }


    /**
     * A method that changes the status of a joiners notification
     *
     * @param status
     * @param nId
     */

    /*
    public void updateJoinStatus(String status, String nId) {

        final DocumentReference docRef = db.collection("joinStatus").document(nId);

        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(docRef);
                transaction.update(docRef, "joinRequestList", status);
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
                EventBus.getDefault().post(new NotificationForJoinerEvent(true));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Transaction failure.", e);
            }
        });


    }
     */


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
    public void addActivity(String uId, Timestamp date, String title, String desc, GeoPoint location, Boolean privateAct, Category category, int numOfMaxAttendees) {
        WriteBatch batch = db.batch();
        DocumentReference chatRef = db.collection("chats").document();
        DocumentReference activityRef = db.collection("activities").document();
        DocumentReference owner = db.document("/users/" + uId);
        List<DocumentReference> participants = new ArrayList<>();
        participants.add(owner);

        List<DocumentReference> joinRequestList = new ArrayList<>();
        List<DocumentReference> messages = new ArrayList<>();

        Map<String, Object> activity = new HashMap<>();
        Map<String, Object> chatData = new HashMap<>();

        chatData.put("participants", participants);
        chatData.put("messages", messages);
        chatData.put("name","Chat for " + title);

        activity.put("title", title);
        activity.put("desc", desc);
        activity.put("time", date);
        activity.put("creationDate", new Timestamp(new Date()));
        activity.put("owner", owner);
        activity.put("location", location);
        activity.put("participants", participants);
        activity.put("joinRequestList", joinRequestList);
        activity.put("category", category);
        activity.put("privateEvent", privateAct);
        activity.put("numOfMaxAttendees", numOfMaxAttendees);
        activity.put("chat", chatRef);

        batch.set(chatRef, chatData);
        batch.set(activityRef, activity);


        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

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
        db.collection("activities").addSnapshotListener((value, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }

            activities = new ArrayList<>();
            for (QueryDocumentSnapshot doc : value) {
                Log.w(TAG, doc.getId());
                ActivityDataHolder data = doc.toObject(ActivityDataHolder.class);
                if (shouldActivityBeShown(data)) {
                    activities.add(data.toActivity(doc.getId()));
                }
            }

            Main.getInstance().setActivities(activities);
            EventBus.getDefault().post(new ActivityFeedEvent(activities));
        });
    }

    private boolean shouldActivityBeShown(ActivityDataHolder data) {
        boolean shouldLoad = data.hasValidData() && (data.getNumOfMaxAttendees() > data.participants.size() ||
                data.getNumOfMaxAttendees() == 0);
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            return shouldLoad &&
                !data.getOwner().getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid());
        else
            return shouldLoad;
    }

    /**
     * Attaches a listener that loads all comments for a given activity
     * <p>
     * Author: Johan Ek
     *
     * @param id the id of the activity
     * @return the listener
     */
    public ListenerRegistration loadComments(String id) {
        return db.collection("activities").document(id).collection("comments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                List<Comment> comments = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    CommentDataHolder data = doc.toObject(CommentDataHolder.class);
                    comments.add(data.toComment());
                }
                EventBus.getDefault().post(new BatchCommentEvent(comments));
            }
        });

    }

    /**
     * Method that adds a new comment to a given activity in the db
     * <p>
     * Author: Johan Ek
     *
     * @param activity the object representing the given activity
     * @param comment  the object representing the comment that should be posted
     */
    public void addCommentToActivity(Activity activity, Comment comment) {
        Map<String, Object> data = new HashMap<>();
        data.put("commentText", comment.getCommentText());
        data.put("poster", db.document("/users/" + comment.getCommenterRef()));
        data.put("postDate", new Timestamp(comment.getCommentDate()));
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
    public void writeMessageInChat(String chatId, String msg, Date date) {

        Map<String, Object> data = new HashMap<>();

        data.put("messageText", msg);
        data.put("sender", db.document("/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()));
        data.put("sent", new Timestamp(date));
        db.collection("chats").document(chatId).collection("messages").add(data).
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
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                List<Message> messages = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    MessageDataHolder data = doc.toObject(MessageDataHolder.class);
                    messages.add(data.toMessage());
                }
                EventBus.getDefault().post(new ChatEvent(messages));
            }
        });

    }

    /**
     * Method that attaches a listener to all activities that are owned by the user with id uID
     * <p>
     * Author: Johan Ek
     *
     * @param uID id of the user that is the owner
     * @return a listener that is attached to all activities that uID owns
     */
    public ListenerRegistration loadNotification(String uID) {
        return db.collection("activities").whereEqualTo("owner", db.document("/users/" + uID)).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                List<JoinNotification> notifications = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    String activityID = doc.getId();
                    String title = doc.getString("title");
                    List<DocumentReference> participants = (List<DocumentReference>) doc.get("participants"); //cursed row
                    Long max = (Long) doc.get("numOfMaxAttendees");
                    if (max != 0 && max <= participants.size())
                        continue;
                    List<DocumentReference> waitingList = (List<DocumentReference>) doc.get("joinRequestList"); //evil row
                    for (DocumentReference ref : waitingList) {
                        if (idToNameDictionary.containsKey(ref.getId()))
                            notifications.add(new JoinNotification(activityID, title, ref.getId(), idToNameDictionary.get(ref.getId()))); //what to do about name, redundant data?
                        else
                            notifications.add(new JoinNotification(activityID, title, ref.getId(), "name could not be found"));
                    }
                }
                EventBus.getDefault().post(new JoinNotificationEvent(notifications));
            }
        });
    }

    public ListenerRegistration loadNotificationStatus(String uID){

        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            return null;
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference currentUser = db.document("/users/" + uId);
        return db.collection("joinStatus").whereEqualTo("joiner", currentUser).addSnapshotListener((value, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<NotificationForJoiner> notification = new ArrayList<>();
            for (QueryDocumentSnapshot doc : value) {
                NotificationStatusDataHolder data = doc.toObject(NotificationStatusDataHolder.class);
                if (data.hasValidData()) {
                    notification.add(data.toNotificationForJoiner());
                }


            }
            EventBus.getDefault().post(new NotificationForJoinerEvent(notification));
        });






        /*return db.collection("joinStatus").whereEqualTo("joiner",db.document("/users"+uID)).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                List<NotificationForJoiner> notifications = new ArrayList<>();

                for (QueryDocumentSnapshot doc : value) {
                    String activity = doc.getString("activity");
                    String joiner = doc.getString("joiner");
                    String status = doc.getString("status");

                    notifications.add(new NotificationForJoiner(activity, joiner, status, null));



                    }
                EventBus.getDefault().post(new NotificationForJoinerEvent(notifications));




            }
        });


         */

    }

    /**
     * Method that either accepts a join request or declines it. This is handled in a atomic transaction
     * to avoid the possibility that the user is removed from the waiting list but not added to the
     * participants
     * <p>
     * Author: Johan Ek
     *
     * @param uID        the id of the user that is requesting to join
     * @param activityID id of the activity that the user is trying to join
     * @param accept     if true then the request was accepted
     */
    public void handleJoinRequest(String uID, String activityID, boolean accept) {
        final DocumentReference docRef = db.collection("activities").document(activityID);

        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(docRef);
                transaction.update(docRef, "joinRequestList", FieldValue.arrayRemove(db.document("/users/" + uID)));
                if (accept) {
                    DocumentReference chatRef  = (DocumentReference) snapshot.get("chat");
                    transaction.update(docRef, "participants", FieldValue.arrayUnion(db.document("/users/" + uID)));

                    transaction.update(chatRef,"participants",FieldValue.arrayUnion(db.document("/users/" + uID)));

                    WriteBatch batch = db.batch();

                    DocumentReference activityRef = db.collection("activities").document(activityID);
                    DocumentReference joiner = db.document("/users/" + uID);
                    DocumentReference joinStatusRef = db.collection("joinStatus").document();

                    Map<String, Object> joinStatus = new HashMap<>();

                    joinStatus.put("joiner", joiner);
                    joinStatus.put("activity", activityRef);
                    joinStatus.put("status", "accepted");

                    batch.set(joinStatusRef, joinStatus);

                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }

                else{

                    WriteBatch batch = db.batch();

                    DocumentReference activityRef = db.collection("activities").document(activityID);
                    DocumentReference joiner = db.document("/users/" + uID);
                    DocumentReference joinStatusRef = db.collection("joinStatus").document();

                    Map<String, Object> joinStatus = new HashMap<>();

                    joinStatus.put("joiner", joiner);
                    joinStatus.put("activity", activityRef);
                    joinStatus.put("status", "declined");

                    batch.set(joinStatusRef, joinStatus);

                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

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

    /**
     * Method that creates a join request for a user on a certain activity.
     * <p>
     * Author: Johan Ek
     *
     * @param uID        the user id of the user that wants to join the activity
     * @param activityID the id of the activity
     */
    public void createJoinRequest(String uID, String activityID) {
        final DocumentReference docRef = db.collection("activities").document(activityID);

        db.runTransaction(new Transaction.Function<String>() {
            @Override
            public String apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(docRef);
                String message = "An unknown error has occurred";
                if (snapshot.exists()) {
                    List<DocumentReference> participants = (List<DocumentReference>) snapshot.get("participants"); //cursed row
                    Long max = (Long) snapshot.get("numOfMaxAttendees");
                    DocumentReference userRef = db.document("/users/" + uID);

                    assert participants != null;
                    if (participants.contains(userRef)) {
                        message = "You have already joined this activity";
                    } else if (max <= participants.size() && max != 0) {
                        message = "That activity is currently full.";
                    } else {
                        message = null;
                        transaction.update(docRef, "joinRequestList", FieldValue.arrayUnion(userRef));
                    }
                }
                return message;
            }
        }).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String message) {
                if (message == null)
                    EventBus.getDefault().post(new JoinActivityEvent(true));
                else
                    EventBus.getDefault().post(new JoinActivityEvent(false, message));
                Log.d(TAG, "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                EventBus.getDefault().post(new JoinActivityEvent(false, "An unknown error has occurred"));
                Log.w(TAG, "Transaction failure.", e);
            }
        });
    }

    /**
     * Method that loads the dictionary that associates the uIDs with their user names
     * <p>
     * Author: Johan Ek
     */
    private void loadUserNames() {
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                Map<String, String> map = new HashMap<>();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    map.put(doc.getId(), (String) doc.get("name"));
                }
                idToNameDictionary = map;
            }
        });
    }



    public Map<String, String> getIdToNameDictionary() {
        return idToNameDictionary;
    }

    /**
     * Method that attaches and returns a listener that loads all activities that the current user
     * is taking part in.
     *
     * @return the listener
     */
    public ListenerRegistration loadCurrentActivities() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            return null;
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference currentUser = db.document("/users/" + uId);
        return db.collection("activities").whereArrayContains("participants", currentUser).addSnapshotListener((value, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<Activity> currentActivities = new ArrayList<>();
            for (QueryDocumentSnapshot doc : value) {
                Log.w(TAG, doc.getId());
                ActivityDataHolder data = doc.toObject(ActivityDataHolder.class);
                if (data.hasValidData())
                    currentActivities.add(data.toActivity(doc.getId()));
            }
            EventBus.getDefault().post(new CurrentActivitiesEvent(currentActivities));
        });
    }

    /**
     * Method that attaches and returns a listener that keeps track of all chats the currently
     * logged in user are a part off.
     * <p>
     * Author: Johan Ek
     *
     * @return the listener that is attached to the list of chats the user is part off
     */
    public ListenerRegistration attachChatListListener() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            return null;
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference userRef = db.document("/users/" + uId);
        return db.collection("chats").whereArrayContains("participants", userRef).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                List<ChatListItem> chatInfoList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    List<DocumentReference> participants = (List<DocumentReference>) doc.get("participants");
                    if(participants.size() <= 1)
                        continue;
                    String chatName = (String) doc.get("name");
                    if(chatName == null)
                            chatName = buildChatName(participants, FirebaseAuth.getInstance().getCurrentUser().getUid());
                    String id = doc.getId();
                    int amountOfParticipants = participants.size();
                    chatInfoList.add(new ChatListItem(chatName, id, amountOfParticipants));
                }
                EventBus.getDefault().post(new ChatListEvent(chatInfoList));
            }
        });
    }

    /**
     * Method that build a chat name out of the list of participants. If the name is longer than
     * part of the name that is build out of user names is longer than 20 characters "..." is
     * attached at the end and the string is returned
     * <p>
     * Author: Johan Ek
     *
     * @param idList the list of participants
     * @return the name of the chat
     */
    private String buildChatName(List<DocumentReference> idList, String uID) {
        StringBuilder name = new StringBuilder();
        name.append("Chat with ");
        String currentUserName = idToNameDictionary.get(uID);
        for (DocumentReference ref : idList) {
            String userName = idToNameDictionary.get(ref.getId());
            if (userName.equals(currentUserName))
                continue;
            name.append(userName);
            if (name.length() >= 20) {
                name.append("...");
                return name.toString();
            }
            name.append(", ");
        }
        return name.toString();
    }

    public String getActivityTitle(String aID){

        DocumentReference documentReference = db.collection("activities").document(aID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                activityTitle = documentSnapshot.getString("title");
            }
        });


        return activityTitle;
    }

     /**
     * Attaches a listener that loads information for a given userID
     * <p>
     * Author: Jennie Zhou
     *
     * @param id the id of the user
     * @return the listener
     */
    public ListenerRegistration loadUserInformation(String id) {
        DocumentReference docRef = db.collection("users").document(id);
        return docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                User userObj = new User("id", documentSnapshot.getString("name"), documentSnapshot.getString("email"), documentSnapshot.getString("phoneNumber"));
                EventBus.getDefault().post(new UserEvent(userObj));
            }
        });
    }

    /**
     * A method to retrieve the exciting user's data from the database
     *
     * @param currentUser The current user who's successfully logged in.
     */
    public void retrieveData(FirebaseUser currentUser) {
        if (currentUser != null) {
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            DocumentReference documentReference = firestore.collection("users").document(currentUser.getUid());
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    //todo retrieving the data
                    /*
                    fullName.setText(documentSnapshot.getString("name"));
                    email.setText(documentSnapshot.getString("email"));
                   // get the friendList, this should be in a recyclerView form
                    */

                }
            });

        }
    }
}

