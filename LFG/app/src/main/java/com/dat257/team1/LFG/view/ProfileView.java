package com.dat257.team1.LFG.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dat257.team1.LFG.R;

public class ProfileView extends AppCompatActivity {
    private ImageView profileImage;
    private ImageView emailImage;
    private ImageView phoneImage;
    private ImageView addContactImage;
    private ImageView blockContactImage;

    private TextView userName;
    private TextView profileDescription;
    private Button addFriendButton;
    private Button blockContactButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add functionality to addFriendButton
            }
        });

        blockContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add functionality to blockContactButton
            }
        });
    }
}