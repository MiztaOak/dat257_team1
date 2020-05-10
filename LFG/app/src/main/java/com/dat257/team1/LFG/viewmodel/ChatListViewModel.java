package com.dat257.team1.LFG.viewmodel;

import com.dat257.team1.LFG.events.ChatListEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.view.chatList.ChatListItem;
import com.google.firebase.firestore.ListenerRegistration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

public class ChatListViewModel extends ViewModel implements LifecycleObserver {
    private ListenerRegistration listener;
    private MutableLiveData<List<ChatListItem>> chatListItems;

    public ChatListViewModel() {
        chatListItems = new MutableLiveData<>();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        listener = FireStoreHelper.getInstance().attachChatListListener();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        if(listener != null) {
            listener.remove();
            listener = null;
        }
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void handleEvent(ChatListEvent event){
        chatListItems.setValue(event.getChatInfoList());
    }

    public MutableLiveData<List<ChatListItem>> getChatListItems() {
        return chatListItems;
    }
}
