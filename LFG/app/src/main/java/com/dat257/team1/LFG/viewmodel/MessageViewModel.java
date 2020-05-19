package com.dat257.team1.LFG.viewmodel;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.events.MessageEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.Chat;
import com.dat257.team1.LFG.model.Main;
import com.dat257.team1.LFG.model.Message;
import com.google.firebase.firestore.ListenerRegistration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MessageViewModel extends ViewModel implements LifecycleObserver {

    private MutableLiveData<String> mutableChatId;
    private MutableLiveData<List<Message>> messages;
    private MutableLiveData<Boolean> isMessageSent;
    private ListenerRegistration listener;


    public MessageViewModel() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    public MutableLiveData<String> getMutableLiveDataChatId() {
        if (mutableChatId == null)
            mutableChatId = new MutableLiveData<>();
        return mutableChatId;
    }

    public MutableLiveData<List<Message>> getMutableLiveDataMessages() {
        if (messages == null)
            messages = new MutableLiveData<>();
        return messages;
    }

    public MutableLiveData<Boolean> getMutableLiveDataIsMessageSent() {
        if (isMessageSent == null)
            isMessageSent = new MutableLiveData<>();
        return isMessageSent;
    }

    public void sendMessage(String chatId,String msg) {
        Main.getInstance().writeMessage(chatId, msg);
    }

    public void setMutableChatId(String chatId) {
        assert mutableChatId != null;
        mutableChatId.postValue(chatId);
    }

    public void loadChat(String chatId) {
        listener = Main.getInstance().loadChat(chatId);
    }

    @Subscribe
    public void handleChatEvent(com.dat257.team1.LFG.events.ChatEvent event) {
        messages.postValue(event.getMessages());
    }

    @Subscribe
    public void handleMessageEvent(MessageEvent messageEvent) {
        if(messageEvent.isSuccess()) {
            Main.getInstance().loadChat(mutableChatId.getValue());
        }
    }

    public void cleanup() {
        listener.remove();
    }
}
