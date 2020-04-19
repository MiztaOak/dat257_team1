package com.dat257.team1.LFG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dat257.team1.LFG.view.CreateActivityView;
import com.dat257.team1.LFG.viewmodel.FindActivity;

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
                openCreateActivityView();

            }
        });


    }

    public void openFindActivity(){

        Intent intent = new Intent(this, FindActivity.class
        );
        startActivity(intent);
    }

    public void openCreateActivityView(){

        Intent intent = new Intent(this, CreateActivityView.class
        );
        startActivity(intent);
    }
}
