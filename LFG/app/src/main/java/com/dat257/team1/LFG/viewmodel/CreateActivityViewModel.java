package com.dat257.team1.LFG.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Main;

public class CreateActivityViewModel extends ViewModel {

    public CreateActivityViewModel() {
    }

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