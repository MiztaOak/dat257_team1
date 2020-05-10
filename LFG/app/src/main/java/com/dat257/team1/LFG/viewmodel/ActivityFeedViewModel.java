package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.events.ActivityFeedEvent;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Main;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class ActivityFeedViewModel extends ViewModel implements LifecycleObserver {

    private MutableLiveData<List<Activity>> mutableActivityList;

    public ActivityFeedViewModel() {
    }


    public MutableLiveData<List<Activity>> getMutableActivityList() {
        if (mutableActivityList == null) {
            mutableActivityList = new MutableLiveData<>();
        }
        return mutableActivityList;
    }

    public void updateFeed() {
        Main.getInstance().updateActivityFeed();
    }

    @Subscribe
    public void onUpdateActivityFeedEvent(ActivityFeedEvent event) {
        getMutableActivityList(); //TODO Ugly
        mutableActivityList.postValue(event.getActivityList());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
}