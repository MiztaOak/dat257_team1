package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Category;
import com.dat257.team1.LFG.repository.Repository;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.GeoPoint;

public class CreateActivityViewModel extends ViewModel {

    public void createActivity(String title, String description, Timestamp time, GeoPoint location, Boolean privateEvent, int numAttendees, Category category) {

        Repository.getInstance().createActivity(title, description, time, location, privateEvent, numAttendees, category, FirebaseAuth.getInstance().getUid());
    }


}