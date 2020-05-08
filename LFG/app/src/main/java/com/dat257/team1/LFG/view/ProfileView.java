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

        initViews();

        //TODO: fetch user information from database

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

    private void initViews() {
        profileImage = findViewById(R.id.profile_photo);
        emailImage = findViewById(R.id.email_image_view);
        phoneImage = findViewById(R.id.phone_image_view);
        addContactImage = findViewById(R.id.add_friend_image);
        blockContactImage = findViewById(R.id.block_contact);
        profileDescription = findViewById(R.id.profile_desc);
        userName = findViewById(R.id.user_name_profile);
    }
}