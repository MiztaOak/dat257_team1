package com.dat257.team1.LFG.viewmodel;

import com.dat257.team1.LFG.model.Main;

public class CreateActivityViewModel {

    public void createActivity(CharSequence title, CharSequence description) {
        Main.getMain().createActivity(title.toString(), description.toString(), "Heden", "14:30");

    }
}