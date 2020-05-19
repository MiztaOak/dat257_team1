package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.events.UserEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.Main;
import com.dat257.team1.LFG.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.ListenerRegistration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ProfileViewModel extends ViewModel implements LifecycleObserver {

    private MutableLiveData<User> user = new MutableLiveData<>();
    private ListenerRegistration listener;


    public ProfileViewModel() {
    }

    @Subscribe
    public void handleUserEvent(UserEvent userEvent) {
        getUserId(); //TODO
        user.postValue(userEvent.getUser());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        //listener = FireStoreHelper.getInstance().loadUserInformation(user.getValue().getId());
    }

    public void updateUserData(String userId) {
        FireStoreHelper.getInstance().loadUserInformation(userId);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        listener.remove();
    }


    public MutableLiveData<User> getUserId() {
        if (user == null)
            user = new MutableLiveData<>();
        return user;
    }
}