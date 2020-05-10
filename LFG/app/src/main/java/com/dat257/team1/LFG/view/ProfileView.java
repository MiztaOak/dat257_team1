package com.dat257.team1.LFG.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.User;
import com.dat257.team1.LFG.viewmodel.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileView extends AppCompatActivity {

    private ProfileViewModel profileViewModel;
    private MutableLiveData<User> user;
    private ImageView profileImage;
    private ImageView emailImage;
    private ImageView phoneImage;
    private ImageView addContactImage;
    private ImageView blockContactImage;

    private TextView userName;
    private TextView profileDescription;
    private TextView emailTextView;
    private TextView phoneTextView;

    private Button addFriendButton;
    private Button blockContactButton;

    private LinearLayout addFriendLayout;
    private LinearLayout blockContactLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        getLifecycle().addObserver(profileViewModel);
        profileViewModel.onCreate();

        user = profileViewModel.getUserId();
        profileViewModel.getUserId().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                userName.setText(user.getName());
                profileDescription.setText(user.getId()); //sätter profile desc som id nu, då vi inte har en user desc som kan hämtas
                emailTextView.setText(user.getEmail());
                phoneTextView.setText(user.getPhoneNumber());
            }
        });

        FireStoreHelper.getInstance();
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid(); // the current user
        User profileOwner = null;

        //TODO: We need a userID from profile owner, in order to compare the currentUser with the profileUser
        if (currentUser.equals(profileOwner.getId())) {
            addFriendLayout.setVisibility(View.INVISIBLE);
            blockContactLayout.setVisibility(View.INVISIBLE);
        } else {
            addFriendLayout.setVisibility(View.VISIBLE);
            blockContactLayout.setVisibility(View.VISIBLE);
        }

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
        emailTextView = findViewById(R.id.email_text_view);
        phoneTextView = findViewById(R.id.phone_text_view);
        addFriendLayout = findViewById(R.id.linearLayout3);
        blockContactLayout = findViewById(R.id.linearLayout4);
    }
}