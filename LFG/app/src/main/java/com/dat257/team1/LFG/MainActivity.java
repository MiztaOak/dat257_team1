package com.dat257.team1.LFG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dat257.team1.LFG.view.ActivityFeedView;
import com.dat257.team1.LFG.view.loginPage.LoginPageView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button testLogin;
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
        testLogin = findViewById(R.id.testlogin);
        testLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginPage();
            }
        });


    }

    public void openFindActivity(){

        Intent intent = new Intent(this, ActivityFeedView.class
        );
        startActivity(intent);
    }


    public void openLoginPage(){

        Intent intent = new Intent(this, LoginPageView.class
        );
        startActivity(intent);
    }


}
