package com.dat257.team1.LFG.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Main;

public class CreateActivityViewModel extends ViewModel {

    public CreateActivityViewModel() {
    }

    public void createActivity(String title, String description, String address, String time) {
        if (isValid()) {
            //Dummy ID, change later!
            //String uID = "Dz0LrkQTOeefy7dqqx3E97xBHLE2";
            //User owner = new User("1213","arso","arseosoos",341);
            //List<User> participants = new ArrayList<>();
            //Main.getInstance().createActivity(uID,title, description, address, time);
            //List<User> participants = new ArrayList<>();
            //Dummy user, change later!
            //Main.getInstance().createActivity(uID, owner, participants, title, description, new GeoPoint(30,40), new Timestamp(3000,40));
            Main.getInstance().createActivity(title, description, time, address);
        }
    }

    private boolean isValid() {
        //Some code to check if the inputs for creating an activity are correct.
        return true;
    }
}