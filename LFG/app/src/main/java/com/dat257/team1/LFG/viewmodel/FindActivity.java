package com.dat257.team1.LFG.viewmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.dat257.team1.LFG.R;

public class FindActivity extends AppCompatActivity {


    private Button createActivity;
    private Button menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        createActivity = (Button) findViewById(R.id.createActivity);
        menu = (Button) findViewById(R.id.menu);
        createActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelloWorld();
            }
        });

        menu.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openHelloWorld();

            }
        }));


    }

    public void openHelloWorld(){

        Intent intent = new Intent(this, HelloWorld.class
        );
        startActivity(intent);
    }
}
