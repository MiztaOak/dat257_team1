package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.User;
import com.google.firebase.firestore.ListenerRegistration;

import org.greenrobot.eventbus.EventBus;

public class ProfileViewModel extends ViewModel implements LifecycleObserver {

    private MutableLiveData<User> user = new MutableLiveData<>();
    private ListenerRegistration listener;


    public ProfileViewModel() {
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        user.setValue(getUserId().getValue()); //kan vara fel
        listener = FireStoreHelper.getInstance().loadUserInformation(user.getValue().getId());
    }


    public MutableLiveData<User> getUserId() {
        if (user == null)
            user = new MutableLiveData<>();
        return user;
    }
}