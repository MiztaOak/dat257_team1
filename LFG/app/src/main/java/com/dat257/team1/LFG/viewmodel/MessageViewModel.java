package com.dat257.team1.LFG.viewmodel;


import androidx.lifecycle.MutableLiveData;

import androidx.lifecycle.ViewModel;



import com.dat257.team1.LFG.model.Chat;

import com.dat257.team1.LFG.model.Main;
import com.dat257.team1.LFG.model.Message;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.ListenerRegistration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MessageViewModel extends ViewModel{

    private MutableLiveData<List<Message>> messages;
    private MutableLiveData<Chat> chat;
    private ListenerRegistration listener;


    public MessageViewModel(){
        chat = new MutableLiveData<>();
        messages = new MutableLiveData<>();
        messages.setValue(new ArrayList<>());

        EventBus.getDefault().register(this);
    }

    public MutableLiveData<List<Message>> getMessages(){
        if(messages == null)
            messages = new MutableLiveData<>();
        return messages;
    }

    public MutableLiveData<Chat> getChat(){
        if(chat == null)
            chat = new MutableLiveData<>();
        return chat;
    }


    public void setChat(Chat chat){
        this.messages.setValue((List<Message>) messages);
    }

    public void sendMessage(String messageText){
        Message message = new Message(messageText, FirebaseAuth.getInstance().getCurrentUser().getUid(), Calendar.getInstance().getTime()
                );
        Main.getInstance().writeMessage(chat.getValue(),message);
    }


    @Subscribe
    public void handleChatEvent(com.dat257.team1.LFG.events.ChatEvent event){
        messages.setValue(event.getMessages());
    }


    public void cleanup(){
        listener.remove();
    }
}
