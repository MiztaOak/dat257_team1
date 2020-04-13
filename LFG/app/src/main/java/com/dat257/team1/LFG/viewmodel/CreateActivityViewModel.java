package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Main;

public class CreateActivityViewModel extends ViewModel {

    public CreateActivityViewModel() {
    }

    public void createActivity(CharSequence title, CharSequence description){
        Main.getInstance().createActivity(title.toString(), description.toString(), "loc", "11:00"); //pass param
    }
}
