package com.dat257.team1.LFG.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Main;

public class CreateActivityViewModel extends ViewModel {



    public void createActivity(){
        Main.getInstance().createActivity("title", "desc", "loc", "11:00"); //pass param
    }
}
