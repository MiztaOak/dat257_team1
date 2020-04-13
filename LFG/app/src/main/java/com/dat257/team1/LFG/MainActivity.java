package com.dat257.team1.LFG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dat257.team1.LFG.view.ActivityFeedView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.FindActivities);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFindActivity();

            }
        });


    }

    public void openFindActivity(){

        Intent intent = new Intent(this, ActivityFeedView.class
        );
        startActivity(intent);
    }


}
