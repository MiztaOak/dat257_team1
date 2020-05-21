package com.dat257.team1.LFG.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.User;
import com.dat257.team1.LFG.viewmodel.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
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
    private String profileOwner;
    private Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            profileOwner = bundle.getString("userId");
        }
        if (profileOwner == null) {
            profileOwner = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        initViews(view);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        getViewLifecycleOwner().getLifecycle().addObserver(profileViewModel);
        profileViewModel.onCreate();

        user = profileViewModel.getUserId();
        user.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(user.getName() + "s" + " profile");

                userName.setText(user.getName());
                profileDescription.setText(user.getId()); //sätter profile desc som id nu, då vi inte har en user desc som kan hämtas
                emailTextView.setText(user.getEmail());
                phoneTextView.setText(user.getPhoneNumber());
            }
        });

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid(); // the current user

        if (currentUser.equals(profileOwner)) {
            addFriendLayout.setClickable(false);
            addFriendLayout.setVisibility(View.INVISIBLE);
            blockContactLayout.setClickable(false);
            blockContactLayout.setVisibility(View.INVISIBLE);
        }
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add functionality to addFriendButton
            }
        });
        
        profileImageListener();

        blockContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add functionality to blockContactButton
            }
        });
        profileViewModel.updateUserData(profileOwner);
    }

    /**
     * Method that has a listener for the profile picture
     */
    private void profileImageListener() {
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }

    /**
     * A method to choose picture from user's phone gallery
     */
    private void choosePhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void initViews(View view) {
        profileImage = view.findViewById(R.id.profile_photo);
        emailImage = view.findViewById(R.id.email_image_view);
        phoneImage = view.findViewById(R.id.phone_image_view);
        addContactImage = view.findViewById(R.id.add_friend_image);
        blockContactImage = view.findViewById(R.id.block_contact);
        profileDescription = view.findViewById(R.id.profile_desc);
        userName = view.findViewById(R.id.user_name_profile);
        emailTextView = view.findViewById(R.id.email_text_view);
        phoneTextView = view.findViewById(R.id.phone_text_view);
        addFriendLayout = view.findViewById(R.id.linearLayout3);
        blockContactLayout = view.findViewById(R.id.linearLayout4);
        addFriendButton = view.findViewById(R.id.add_friend_button);
        blockContactButton = view.findViewById(R.id.block_contact_button);
    }


    @Override
    public void onPause() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        super.onPause();
    }

    @Override
    public void onStop() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        super.onStop();
    }
}