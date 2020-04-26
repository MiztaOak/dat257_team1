package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;


import com.dat257.team1.LFG.model.Message;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Objects;

public class MessageViewModel extends ViewModel implements LifecycleObserver{

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);

        }
    }

    private MutableLiveData<List<Message>> mutableMessage;

    public MessageViewModel() {
    }


    public MutableLiveData<List<Message>> getMutableMessage() {
        if(mutableMessage == null) {
            mutableMessage = new MutableLiveData<>();
        }
        return mutableMessage;
    }

    @Subscribe
    public void MessageEvent(com.dat257.team1.LFG.events.MessageEvent event) {
        MutableLiveData<List<Message>> mutableLiveData = getMutableMessage();
        Objects.requireNonNull(mutableLiveData.getValue()).add(event.getMessage());
    }



}
