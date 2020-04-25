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

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActivityDescriptionViewModel extends ViewModel {
    private MutableLiveData<List<Comment>> comments;
    private MutableLiveData<Activity> activity;

    public ActivityDescriptionViewModel(){
        activity = new MutableLiveData<>();
        comments = new MutableLiveData<>();
        comments.setValue(new ArrayList<>());
        setActivity(Main.getInstance().getFocusedActivity());
        FireStoreHelper.getInstance().loadComments(activity.getValue().getId());

        EventBus.getDefault().register(this);
    }

    public MutableLiveData<List<Comment>> getComments(){
        if(comments == null)
            comments = new MutableLiveData<>();
        return comments;
    }

    public MutableLiveData<Activity> getActivity(){
        if(activity == null)
            activity = new MutableLiveData<>();
        return activity;
    }

    public void setActivity(Activity activity){
        this.activity.setValue(activity);
    }

    public void addComment(String commentText){
        Comment comment = new Comment(commentText, Calendar.getInstance().getTime(),
                FirebaseAuth.getInstance().getCurrentUser().getUid());
        Main.getInstance().addComment(activity.getValue(),comment);
    }

    @Subscribe
    public void handleBatchCommentEvent(BatchCommentEvent event){
        comments.setValue(event.getComments());
    }
}

