package com.dat257.team1.LFG.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Main;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class CreateActivityViewModel extends ViewModel {

    public CreateActivityViewModel() {
    }

    @SuppressLint("RestrictedApi") //Work-around caused by a bug that did not allow me to create a user
    public void createActivity(String title, String description, String time, String address) {
        if (isValid()) {
            //Dummy ID, change later!
            String uID = "Dz0LrkQTOeefy7dqqx3E97xBHLE2";
            //Dummy participants, change later!
            ArrayList<User> participants = new ArrayList<>();
            //Dummy owner, change later!
            User owner = new User("Dz0LrkQTOeefy7dqqx3E97xBHLE2");
            Main.getInstance().createActivity(uID, owner, participants, title, description, time, address);

        }
    }

    private boolean isValid() {
        //Some code to check if the inputs for creating an activity are correct.
        return true;
    }

    //Arsenije
    //Gabriel
}