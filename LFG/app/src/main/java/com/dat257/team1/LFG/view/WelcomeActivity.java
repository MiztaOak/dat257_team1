package com.dat257.team1.LFG.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.view.loginPage.LoginActivity;
import com.dat257.team1.LFG.view.loginPage.RegisterActivity;

/**
 * A view that is responsible for holding the welcome page's components
 *
 * @author Oussama Anadani
 */
public class WelcomeActivity extends AppCompatActivity {
    private static final String LOG_TAG = WelcomeActivity.class.getSimpleName();
    private Button loginButton;
    private Button registerButton;
    private Button goButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);
        goButton = findViewById(R.id.go);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Login");
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                //   Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Register");
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Activity created!");
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

    }

}
