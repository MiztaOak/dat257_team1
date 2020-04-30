package com.dat257.team1.LFG.viewmodel;

import com.dat257.team1.LFG.events.JoinNotificationEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.JoinNotification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.ListenerRegistration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationViewModel extends ViewModel {
    private MutableLiveData<List<JoinNotification>> requests;
    private ListenerRegistration listener;

    public NotificationViewModel() {
        requests = new MutableLiveData<>();

        EventBus.getDefault().register(this);
    }

    public MutableLiveData<List<JoinNotification>> getRequests() {
        if(requests == null)
            requests = new MutableLiveData<>();
        return requests;
    }

    @Subscribe
    public void handleEvent(JoinNotificationEvent event){
        requests.setValue(event.getNotifications());
    }

    public void startup(){
        listener = FireStoreHelper.getInstance().loadNotification(FirebaseAuth.getInstance().getUid());
    }

    public void cleanup(){
        listener.remove();
    }
}
