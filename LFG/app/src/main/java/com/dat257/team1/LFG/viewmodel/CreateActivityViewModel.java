package com.dat257.team1.LFG.viewmodel;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Category;
import com.dat257.team1.LFG.model.Main;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CreateActivityViewModel extends ViewModel {

    private final int MIN_TITLE_LENGTH = 4;

    private final long MAX_ATTENDEES = 999999999;

    //Error codes
    private final int TITLE_MISSING = 1;
    private final int DESC_MISSING = 2;
    private final int CAT_MISSING = 3;
    private final int LOC_MISSING = 4;
    private final int TIME_MISSING = 5;

    private MutableLiveData<Activity> currentActivity;

    public CreateActivityViewModel() {
    }

    public MutableLiveData<Activity> getCurrentActivity() {
        if (currentActivity == null) {
            currentActivity = new MutableLiveData<Activity>();
        }
        return currentActivity;
    }

    public void createActivity(String title, String description, Long time, Location location, Boolean privateEvent, int numAttendees, Category category) {
        GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
        Timestamp timestamp = new Timestamp(time,0);
        if (checkFields(title, description, timestamp, geoPoint, category) == 0) {
            Main.getInstance().createActivity(title, description, timestamp, geoPoint, privateEvent, numAttendees, category);
        }
    }

    private int checkFields(String title, String description, Timestamp time, GeoPoint geoPoint, Category category) { //TODO more checks
        if(!(title.length() >= MIN_TITLE_LENGTH)) {
            return TITLE_MISSING;
        }
        if(description.length() == 0) {
            return DESC_MISSING;
        }
        Date currentTime = Calendar.getInstance().getTime();
        Date date = new Date(time.getSeconds());
        if(date.before(currentTime)) {
            return TIME_MISSING;
        }
        if(geoPoint == null) {
            return LOC_MISSING;
        }
        if(category == null) {
            return CAT_MISSING;
        }
        return 0;
    }
}