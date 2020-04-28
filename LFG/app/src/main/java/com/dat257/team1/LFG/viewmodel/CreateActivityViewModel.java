package com.dat257.team1.LFG.viewmodel;

import android.annotation.SuppressLint;
import android.media.tv.TvView;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Main;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateActivityViewModel extends ViewModel {

    private MutableLiveData<Activity> currentActivity;

    public CreateActivityViewModel() {
    }

    public MutableLiveData<Activity> getCurrentActivity() {
        if (currentActivity == null) {
            currentActivity = new MutableLiveData<Activity>();
        }
        return currentActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi") //Work-around caused by a bug that did not allow me to create a user
    public void createActivity(String title, String description, String time, String address) {
        if (isValid()) {
            Main.getInstance().createActivity(title, description, time, address);
        }
    }

    private boolean isValid() {
        //Some code to check if the inputs for creating an activity are correct.
        return true;
    }




}