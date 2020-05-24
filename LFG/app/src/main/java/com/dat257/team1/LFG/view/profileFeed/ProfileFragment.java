package com.dat257.team1.LFG.view.profileFeed;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.User;
import com.dat257.team1.LFG.viewmodel.ProfileViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

/**
 * A class that handles user's profile
 * @auther Oussama Anadani, Jennie Zhou, Jakob Ew, Johan Ek
 */

public class ProfileFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ProfileViewModel profileViewModel;
    private MutableLiveData<User> user;
    private ImageView profileImage;
    private ImageView addContactImage;
    private ImageView blockContactImage;
    private TextView profileDescription;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView profileLetter;

    private Button addFriendButton;
    private Button blockContactButton;
    private LinearLayout addFriendLayout;
    private LinearLayout blockContactLayout;
    private String profileOwner;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDataBaseRef;
    private ProgressBar mProgressBar;
    private Button savePhoto;
    private StorageTask mUploadTask;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigateUp();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");//todo add current user
        mDataBaseRef = FirebaseDatabase.getInstance().getReference("uploads");
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
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(user.getName() + "'s" + " profile");

                profileLetter.setText(String.valueOf(Character.toUpperCase(user.getName().trim().charAt(0))));
                profileDescription.setText("This is my profile"); //sätter profile desc som id nu, då vi inte har en user desc som kan hämtas
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

        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getContext(), "this picture has been already uploaded", Toast.LENGTH_LONG).show();
                } else {
                    uploadFile();
                }
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileOwner == FirebaseAuth.getInstance().getCurrentUser().getUid()) {
                    choosePhoto();
                }
            }
        });

        blockContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add functionality to blockContactButton
            }
        });

        profileViewModel.updateUserData(profileOwner);
        if(profileOwner != null){
            downloadPhoto(profileOwner);
        } else if(FirebaseAuth.getInstance().getCurrentUser() != null){
            downloadPhoto(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }
    }

    /**
     * A method to download currentUser's photo
     */
    private void downloadPhoto(String uID) {
        StorageReference islandRef = mStorageRef.child(uID + ".jpg");

        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity().getApplicationContext()).load(uri.toString()).into(profileImage);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        final long ONE_MEGABYTE = 1024 * 1024;
       // Glide.with(this).load(islandRef).into(profileImage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            profileImage.setImageURI(mImageUri);
            profileLetter.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * to get our file extension
     *
     * @param uri
     * @return
     */
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
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

    /**
     * To upload the photo to the database
     */
    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()
                    + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 5000);
                            Toast.makeText(getContext(), "upload successful", Toast.LENGTH_SHORT).show();
                            Upload upload = new Upload(user.getValue().getName(),
                                    taskSnapshot.getUploadSessionUri().toString());
                            String uploadId = mDataBaseRef.push().getKey();
                            mDataBaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getContext(), "no file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews(View view) {
        profileLetter = view.findViewById(R.id.profile_icon_text);
        mProgressBar = view.findViewById(R.id.progressBar2);
        savePhoto = view.findViewById(R.id.savePhoto);
        profileImage = view.findViewById(R.id.profile_photo);
        addContactImage = view.findViewById(R.id.add_friend_image);
        blockContactImage = view.findViewById(R.id.block_contact);
        profileDescription = view.findViewById(R.id.profile_desc);
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