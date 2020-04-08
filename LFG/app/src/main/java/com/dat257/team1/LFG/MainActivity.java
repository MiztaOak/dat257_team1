package com.dat257.team1.LFG;

import androidx.appcompat.app.AppCompatActivity;

import com.dat257.team1.LFG.firebase.ActivityDataHolder;
import com.dat257.team1.LFG.util.DataBase;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;

public class MainActivity extends AppCompatActivity {
    private FireStoreHelper fireStoreHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fireStoreHelper = new FireStoreHelper();
        //fireStoreHelper.addActivity("wZCl4ABLmASoWSyy9un7sWFNHG22");

    }
}
