package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.events.UserEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.ListenerRegistration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<List<User>> requests;
    private ListenerRegistration listener;

    public ProfileViewModel() {
        requests = new MutableLiveData<>();

        EventBus.getDefault().register(this);
    }

    public MutableLiveData<List<User>> getRequests() {
        if(requests == null)
            requests = new MutableLiveData<>();
        return requests;
    }

    @Subscribe
    public void handleEvent(UserEvent event){
        requests.setValue((List<User>) event.getUser());
    }

    public void startup(){
        listener = FireStoreHelper.getInstance().loadUserInformation(FirebaseAuth.getInstance().getUid());
    }

    public void cleanup(){
        listener.remove();
    }
}

