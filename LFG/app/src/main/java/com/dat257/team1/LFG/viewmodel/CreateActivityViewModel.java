package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Main;

public class CreateActivityViewModel extends ViewModel {

    private Main main;
    //Dummy ID, change later!
    private String uID = "Dz0LrkQTOeefy7dqqx3E97xBHLE2";

    public CreateActivityViewModel() {
      main = Main.getMain();
    }

    public void createActivity(String title, String description) {
        if (isValid()) {
            main.createActivity(uID,title, description, "Heden", "14:30");
        }
    }

    private boolean isValid() {
        //Some code to check if the inputs for creating an activity are correct.
        return true;
    }
}