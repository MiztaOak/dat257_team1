package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.events.JoinNotificationEvent;
import com.dat257.team1.LFG.events.NotificationForJoinerEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.JoinNotification;
import com.dat257.team1.LFG.model.Main;
import com.dat257.team1.LFG.model.NotificationForJoiner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.ListenerRegistration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class NotificationStatusViewModel extends ViewModel implements LifecycleObserver {

    private ListenerRegistration listener;
    private MutableLiveData<List<NotificationForJoiner>> status;

    public NotificationStatusViewModel() {
        status = new MutableLiveData<>();

        EventBus.getDefault().register(this);
    }


    public MutableLiveData<List<NotificationForJoiner>> getStatus() {
        if(status == null)
            status = new MutableLiveData<>();
        return status;
    }

    @Subscribe
    public void statusEvent(NotificationForJoinerEvent event){
        status.setValue(event.getNotifications());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void startup(){
        listener = FireStoreHelper.getInstance().loadNotificationStatus(FirebaseAuth.getInstance().getUid());
    }

    /*public void loadStatus(String uID){
        listener = FireStoreHelper.getInstance().loadNotificationStatus(uID);
    }

     */

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void cleanup(){
        listener.remove();
    }



}
