package com.dat257.team1.LFG;

import androidx.appcompat.app.AppCompatActivity;

import com.dat257.team1.LFG.util.DataBase;

import android.os.Bundle;
import android.widget.Toast;

import com.dat257.team1.LFG.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBase.writeToDb();
        DataBase.readFromDb();
    }
}
