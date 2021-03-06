package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.events.UserEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ProfileViewModel extends ViewModel implements LifecycleObserver {

    private MutableLiveData<User> user = new MutableLiveData<>();

    public ProfileViewModel() {
    }

    @Subscribe
    public void handleUserEvent(UserEvent userEvent) {
        getUserId();
        user.postValue(userEvent.getUser());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void updateUserData(String userId) {
        FireStoreHelper.getInstance().loadUserInformation(userId);
    }

    public MutableLiveData<User> getUserId() {
        if (user == null)
            user = new MutableLiveData<>();
        return user;
    }
}