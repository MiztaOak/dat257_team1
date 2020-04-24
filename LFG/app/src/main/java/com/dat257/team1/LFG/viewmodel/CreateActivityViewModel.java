package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Main;

public class CreateActivityViewModel extends ViewModel {

    public CreateActivityViewModel() {
    }

    public void createActivity(String title, String description, String address, String time) {
        if (isValid()) {
            //Dummy ID, change later!
            String uID = "Dz0LrkQTOeefy7dqqx3E97xBHLE2";
            Main.getInstance().createActivity(uID,title, description, address, time);
        }
    }

    private boolean isValid() {
        //Some code to check if the inputs for creating an activity are correct.
        return true;
    }
}