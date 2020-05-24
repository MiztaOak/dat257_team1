package com.dat257.team1.LFG.viewmodel;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.events.MessageEvent;
import com.dat257.team1.LFG.repository.Repository;
import com.dat257.team1.LFG.model.Message;
import com.google.firebase.firestore.ListenerRegistration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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
        if (!EventBus.getDefault().isRegistered(this))
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

    public void sendMessage(String chatId, String msg) {
        Repository.getInstance().writeMessage(chatId, msg, Calendar.getInstance().getTime());
    }

    public void setMutableChatId(String chatId) {
        assert mutableChatId != null;
        mutableChatId.postValue(chatId);
    }

    public void loadChat(String chatId) {
        listener = Repository.getInstance().loadChat(chatId);
    }

    @Subscribe
    public void handleChatEvent(com.dat257.team1.LFG.events.ChatEvent event) {
        List<Message> sortedMessageList = new ArrayList<>();
        sortedMessageList.addAll(event.getMessages());
        sortedMessageList.sort(new Comparator<Message>() {
            @Override
            public int compare(Message message, Message t1) {

                return message.getTime().compareTo(t1.getTime());
            }
        });
        messages.postValue(sortedMessageList);
    }

    @Subscribe
    public void handleMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.isSuccess()) {
            isMessageSent.postValue(true);
            Repository.getInstance().loadChat(mutableChatId.getValue());
        } else {
            isMessageSent.postValue(false);
        }
    }

    public void cleanup() {
        listener.remove();
    }
}
