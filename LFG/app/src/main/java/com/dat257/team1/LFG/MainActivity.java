package com.dat257.team1.LFG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dat257.team1.LFG.view.ActivityFeedView;
import com.dat257.team1.LFG.view.loginPage.LoginPageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this, ActivityFeedView.class);
            openLoginPage();
        } else{
            openFindActivity();
        }

    }

    public void openFindActivity(){
        Intent intent = new Intent(this, ActivityFeedView.class);
        startActivity(intent);
    }

    public void openLoginPage(){
        Intent intent = new Intent(this, LoginPageView.class);
        startActivity(intent);
    }
}
