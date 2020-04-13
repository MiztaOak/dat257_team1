package com.dat257.team1.LFG.viewmodel;

import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.model.Main;

public class CreateActivityViewModel extends ViewModel {

    public CreateActivityViewModel() {
    }

    public void createActivity(){
        Main.getInstance().createActivity("title", "desc", "loc", "11:00"); //pass param
    }
}
