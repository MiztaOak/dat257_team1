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
            //Dummy ID, change later!
<<<<<<< HEAD
            //String uID = "Dz0LrkQTOeefy7dqqx3E97xBHLE2";
            //User owner = new User("1213","arso","arseosoos",341);
            //List<User> participants = new ArrayList<>();
=======
            String uID = "Dz0LrkQTOeefy7dqqx3E97xBHLE2";
<<<<<<< HEAD
            //Dummy participants, change later!
            ArrayList<User> participants = new ArrayList<>();
            //Dummy owner, change later!
            User owner = new User("Dz0LrkQTOeefy7dqqx3E97xBHLE2");
            Main.getInstance().createActivity(uID, owner, participants, title, description, time, address);

=======
>>>>>>> bff98d14f33f6450b9bee8be6435f9f939a34108
            //Main.getInstance().createActivity(uID,title, description, address, time);
            //List<User> participants = new ArrayList<>();
            //Dummy user, change later!
            //Main.getInstance().createActivity(uID, owner, participants, title, description, new GeoPoint(30,40), new Timestamp(3000,40));
<<<<<<< HEAD
            Main.getInstance().createActivity(title, description, time, address);
=======
            //Main.getInstance().createActivity(uID, owner, participants, title, description, time, address);
>>>>>>> 8268ff91a44774cfa48f2b8efdfb1e3e3f3511ae
>>>>>>> bff98d14f33f6450b9bee8be6435f9f939a34108
        }
    }

    private boolean isValid() {
        //Some code to check if the inputs for creating an activity are correct.
        return true;
    }
}