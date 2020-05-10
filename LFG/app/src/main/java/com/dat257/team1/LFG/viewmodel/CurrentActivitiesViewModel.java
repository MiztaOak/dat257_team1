package com.dat257.team1.LFG.viewmodel;

import com.dat257.team1.LFG.events.ActivityFeedEvent;
import com.dat257.team1.LFG.events.CurrentActivitiesEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.ListenerRegistration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

public class CurrentActivitiesViewModel extends ViewModel implements LifecycleObserver {
    private MutableLiveData<List<Activity>> mutableOwnedActivities;
    private MutableLiveData<List<Activity>> mutableParticipatingActivities;

    private ListenerRegistration listener;

    public CurrentActivitiesViewModel() {
        mutableOwnedActivities = new MutableLiveData<>();
        mutableParticipatingActivities = new MutableLiveData<>();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        listener = FireStoreHelper.getInstance().loadCurrentActivities();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        if(listener != null) {
            listener.remove();
            listener = null;
        }
        EventBus.getDefault().unregister(this);
    }

    public MutableLiveData<List<Activity>> getMutableOwnedActivities() {
        return mutableOwnedActivities;
    }

    public MutableLiveData<List<Activity>> getMutableParticipatingActivities() {
        return mutableParticipatingActivities;
    }

    @Subscribe
    public void handleEvent(CurrentActivitiesEvent event){
        filterList(event.getActivityList());
    }

    /**
     * Method that filters a list of activities into two lists the once that the current user owns
     * and the once that they are participating in and stores them in the live data lists
     *
     * Author: Johan Ek
     * @param activities the list of activities that will be filtered
     */
    private void filterList(List<Activity> activities){
        List<Activity> ownedActivities = new ArrayList<>();
        List<Activity> participatingActivities = new ArrayList<>();
        String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        for(Activity activity: activities){
            if(activity.getOwner().equals(uID))
                ownedActivities.add(activity);
            else if(activity.getParticipants().contains(uID))
                participatingActivities.add(activity);
        }
        mutableOwnedActivities.setValue(ownedActivities);
        mutableParticipatingActivities.setValue(participatingActivities);
    }

    private boolean test(List<String> users, String uID){
        for(String user: users){
            if(user.equals(uID))
                return true;
        }
        return false;
    }
}
