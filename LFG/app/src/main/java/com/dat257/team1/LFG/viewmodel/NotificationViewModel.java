package com.dat257.team1.LFG.viewmodel;

import com.dat257.team1.LFG.events.JoinNotificationEvent;
import com.dat257.team1.LFG.events.NotificationForJoinerEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.JoinNotification;
import com.dat257.team1.LFG.model.NotificationForJoiner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.ListenerRegistration;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

public class NotificationViewModel extends ViewModel implements LifecycleObserver {
    private MutableLiveData<List<JoinNotification>> requests;
    private ListenerRegistration listener;
    private MutableLiveData<List<NotificationForJoiner>> status;

    public NotificationViewModel() {
        requests = new MutableLiveData<>();
        status = new MutableLiveData<>();

        EventBus.getDefault().register(this);
    }

    public MutableLiveData<List<JoinNotification>> getRequests() {
        if(requests == null)
            requests = new MutableLiveData<>();
        return requests;
    }

    public MutableLiveData<List<NotificationForJoiner>> getStatus() {
        if(status == null)
            status = new MutableLiveData<>();
        return status;
    }

    @Subscribe
    public void handleEvent(JoinNotificationEvent event){
        requests.setValue(event.getNotifications());
    }


    @Subscribe
    public void statusEvent(NotificationForJoinerEvent event){
        status.setValue(event.getNotifications());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void startup(){
        listener = FireStoreHelper.getInstance().loadNotification(FirebaseAuth.getInstance().getUid());
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void cleanup(){
        listener.remove();
    }

}
