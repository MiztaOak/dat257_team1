package com.dat257.team1.LFG.viewmodel;

import android.location.Location;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.events.ActivityFeedEvent;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.repository.Repository;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Comparator;
import java.util.List;

/**
 *
 *
 * @author Jakobew & Johan Ek
 */
public class ActFeedViewModel extends ViewModel implements LifecycleObserver {

    private MutableLiveData<List<Activity>> mutableActivityList;
    private Location currentLocation;

    public ActFeedViewModel() {
    }

    public MutableLiveData<List<Activity>> getMutableActivityList() {
        if (mutableActivityList == null) {
            mutableActivityList = new MutableLiveData<>();
        }
        return mutableActivityList;
    }

    public void updateFeed() {
        Repository.getInstance().updateActivityFeed();
    }

    @Subscribe
    public void onUpdateActivityFeedEvent(ActivityFeedEvent event) {
        if (mutableActivityList == null) {
            mutableActivityList = new MutableLiveData<>();
        }
        List<Activity> activities = event.getActivityList();
        sortActivities(activities);
    }

    private void sortActivities(List<Activity> activities){
        if(currentLocation != null){
            activities.sort(new Comparator<Activity>() {
                @Override
                public int compare(Activity activity, Activity t1) {
                    Location locationA = new Location("locationA");
                    locationA.setLatitude(activity.getLocation().getLatitude());
                    locationA.setLongitude(activity.getLocation().getLongitude());

                    Location locationB = new Location("locationB");
                    locationB.setLatitude(t1.getLocation().getLatitude());
                    locationB.setLongitude(t1.getLocation().getLongitude());

                    double distA = currentLocation.distanceTo(locationA);
                    double distB = currentLocation.distanceTo(locationB);
                    return Double.compare(distA,distB);
                }
            });
        }
        mutableActivityList.postValue(activities);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        updateFeed();
    }

    public void onItemClick(int pos) {
        if (mutableActivityList.getValue() != null)
            Repository.getInstance().setFocusedActivity(mutableActivityList.getValue().get(pos));
    }

    public void setLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        sortActivities(mutableActivityList.getValue());
    }
}