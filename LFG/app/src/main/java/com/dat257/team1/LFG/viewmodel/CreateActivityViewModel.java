package com.dat257.team1.LFG.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Main;

public class CreateActivityViewModel extends ViewModel {

    private Main main;
    //Dummy ID, change later!
    private String uID = "Dz0LrkQTOeefy7dqqx3E97xBHLE2";

    public CreateActivityViewModel() {
      main = Main.getMain();
    }

    public void createActivity(String title, String desc) {
        if (isValid()) {
            main.createActivity(title, description, "Heden", "14:30");
            activity.setTitle(title);
            activity.setDescription(desc);
            main.addActivity(activity);
        }
    }

    private boolean isValid() {
        //Some code to check if the inputs for creating an activity are correct.
        return true;
    }
}