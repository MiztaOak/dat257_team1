package com.dat257.team1.LFG.view;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.User;
import com.dat257.team1.LFG.viewmodel.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firestore.v1.FirestoreGrpc;

public class ProfileFragment extends Fragment {

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
    private User profileOwner;


    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String userId = (String) getActivity().getIntent().getExtras().get("userId");

        initViews(view);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        getViewLifecycleOwner().getLifecycle().addObserver(profileViewModel);
        profileViewModel.onCreate();

        user = profileViewModel.getUserId();
        user.observe(getViewLifecycleOwner(), new Observer<User>() {
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
        profileOwner = new User("rIJR06a6qcSGKIJqxpF8ulgZMvK2", "Alex", "alexta@student.chalmers.se", "0702343553");

        //TODO: We need a userID from profile owner, in order to compare the currentUser with the profileUser
        if (currentUser.equals("rIJR06a6qcSGKIJqxpF8ulgZMvK2")) {
            addFriendLayout.setClickable(false);
            addFriendLayout.setVisibility(View.INVISIBLE);
            blockContactLayout.setClickable(false);
            blockContactLayout.setVisibility(View.INVISIBLE);
        } else {
            addFriendLayout.setClickable(true);
            addFriendLayout.setVisibility(View.INVISIBLE);
            blockContactLayout.setClickable(true);
            blockContactLayout.setVisibility(View.INVISIBLE);
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

        profileViewModel.updateUserData(currentUser);
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
}