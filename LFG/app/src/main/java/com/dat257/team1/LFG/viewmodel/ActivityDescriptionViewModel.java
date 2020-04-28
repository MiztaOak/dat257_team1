package com.dat257.team1.LFG.viewmodel;
import com.dat257.team1.LFG.events.BatchCommentEvent;
import com.dat257.team1.LFG.events.CommentEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Comment;
import com.dat257.team1.LFG.model.Main;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Activity;
import com.google.firebase.firestore.ListenerRegistration;

public class ActivityDescriptionViewModel extends ViewModel implements LifecycleObserver {
    private MutableLiveData<List<Comment>> comments;
    private MutableLiveData<Activity> mutableActivity;
    private ListenerRegistration listener;

    public ActivityDescriptionViewModel(){
        mutableActivity = new MutableLiveData<>();
        comments = new MutableLiveData<>();
        comments.setValue(new ArrayList<>());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public MutableLiveData<List<Comment>> getMutableComments() {
        if (comments == null) {
            comments = new MutableLiveData<>();
        }
        return comments;
    }

    public MutableLiveData<Activity> getMutableActivity() {
        if(mutableActivity == null) {
            mutableActivity = new MutableLiveData<>();
        }
        return mutableActivity;
    }


    private void populateMutableActivity(){
        mutableActivity.postValue(Main.getInstance().getFocusedActivity());
    }

    public void addComment(String commentText){
        Comment comment = new Comment(commentText, Calendar.getInstance().getTime(),
                FirebaseAuth.getInstance().getCurrentUser().getUid());
        Main.getInstance().addComment(mutableActivity.getValue(),comment);
    }

    @Subscribe
    public void handleBatchCommentEvent(BatchCommentEvent event){
        comments.setValue(event.getComments());
    }

    public void startup(){
        populateMutableActivity();
        listener = FireStoreHelper.getInstance().loadComments(mutableActivity.getValue().getId());
    }

    public void cleanup(){
        listener.remove();
    }
}

